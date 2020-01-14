package wanart.bi.service.impl.data;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.data.RetentionDataDao;
import wanart.bi.entity.data.RetentionDataEntity;
import wanart.bi.entity.data.RetentionRowDataEntity;
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

    public RetentionDataResponse getData(String projectName, String distinct, String condition, String group, String startTime, String endTime, int steps){
        RetentionDataResponse response = new RetentionDataResponse();
        if(steps <= 0) {
           response.setResult(ResponseResult.Failed);
           response.setMsg("invalid param steps");
           return response;
        }
        response.setResult(ResponseResult.Success);
        response.setMsg("success");
        boolean byGroup = !StringUtils.isEmpty(group);
        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(startTime, endTime);
        for(String timeSuffix: timeSpan){
            try {
                RetentionRowDataEntity retentionRowDataEntity = calcRetionData(projectName, distinct, condition, group, timeSuffix, steps, byGroup);
                response.addRetentionDataEntity(retentionRowDataEntity);
            }
            catch (Exception e){
                System.out.print(e.toString());
            }
        }
        return response;
    }

    private RetentionRowDataEntity calcRetionData(String projectName, String distinct, String condition, String group, String startTimeSuffix, int steps, boolean byGroup) {
        RetentionRowDataEntity retentionRowDataEntity = new RetentionRowDataEntity();
        String createdTime = SqlParamUtil.getDayTimeFromTableNameSuffix(startTimeSuffix);
        retentionRowDataEntity.setCreatedTime(createdTime);
        if(steps <= 1){
           return retentionRowDataEntity;
        }

        String createdTableName = "createchar_" + startTimeSuffix;
        ArrayList<String> timeSuffixList = SqlParamUtil.calcTimeSuffix(startTimeSuffix, steps);
        for(int i=1; i<steps; i++){
            try{
                String loginTableTimeSuffix = timeSuffixList.get(i);
                String loginTableName = "login_" + loginTableTimeSuffix;
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
