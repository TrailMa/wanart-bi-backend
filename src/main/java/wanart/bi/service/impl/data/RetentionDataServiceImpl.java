package wanart.bi.service.impl.data;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.data.RetentionDataDao;
import wanart.bi.dao.event.EventDao;
import wanart.bi.entity.data.RetentionDataEntity;
import wanart.bi.entity.data.RetentionRowDataEntity;
import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.request.RetentionRequest;
import wanart.bi.response.data.RetentionDataResponse;
import wanart.bi.service.data.RetentionDataService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetentionDataServiceImpl implements RetentionDataService {
    @Resource
    private RetentionDataDao retentionDataDao;
    @Resource
    private EventDao eventDao;

    public RetentionDataResponse getData(RetentionRequest request){

        RetentionDataResponse response = new RetentionDataResponse();
        response.setSteps(request.getSteps());
        if(request.getSteps() <= 0) {
           response.setResult(ResponseResult.Failed);
           response.setMsg("invalid param steps");
           return response;
        }

        List<EventColumnEntity> firstEventColumnEntityList = eventDao.getEventDetailByName(request.getProject(), request.getFirstEventName());
        if(firstEventColumnEntityList == null || firstEventColumnEntityList.size() == 0){
            response.setCommonFailure("no such project or first event");
            return response;
        }

        String conditionStr = SqlParamUtil.parseCondition(request.getConditionList(), firstEventColumnEntityList, "a.");

        response.setResult(ResponseResult.Success);
        response.setMsg("success");
        boolean byGroup = !StringUtils.isEmpty(request.getGroup());
        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(request.getStartTime(), request.getEndTime());
        for(String timeSuffix: timeSpan){
            try {
                RetentionRowDataEntity retentionRowDataEntity = calcRetionData(request.getProject(),
                        request.getFirstEventName(), request.getSecondEventName(), request.getDistinct(),
                        request.getGroup(), conditionStr, timeSuffix, request.getSteps(), byGroup);
                response.addRetentionDataEntity(retentionRowDataEntity);
            }
            catch (Exception e){
                System.out.print(e.toString());
            }
        }
        return response;
    }

    private RetentionRowDataEntity calcRetionData(String projectName,  String firstEventName, String secondEventName,
                                                  String distinct, String group, String condition, String startTimeSuffix,
                                                  int steps, boolean byGroup) {
        RetentionRowDataEntity retentionRowDataEntity = new RetentionRowDataEntity();
        String createdTime = SqlParamUtil.getDayTimeFromTableNameSuffix(startTimeSuffix);
        retentionRowDataEntity.setCreatedTime(createdTime);
        if(steps <= 1){
           return retentionRowDataEntity;
        }

        String createdTableName = firstEventName + "_" + startTimeSuffix;
        ArrayList<String> timeSuffixList = SqlParamUtil.calcTimeSuffix(createdTime, steps);
        for(int i=1; i<steps; i++){
            try{
                String loginTableTimeSuffix = timeSuffixList.get(i);
                String loginTableName = secondEventName + "_" + loginTableTimeSuffix;
                List<RetentionDataEntity> dataEntityList;
                // by group时 也需要计算 group=0时的统计留存
                dataEntityList = retentionDataDao.getRetentionData(projectName, createdTableName, loginTableName, distinct, condition);
                retentionRowDataEntity.addDataList(dataEntityList);
                if(byGroup){
                    dataEntityList = retentionDataDao.getRetentionDataByGroup(projectName, createdTableName, loginTableName, distinct, condition, group);
                    retentionRowDataEntity.addDataList(dataEntityList);
                }
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return retentionRowDataEntity;
    }
}
