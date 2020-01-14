package wanart.bi.service.impl.event;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.event.EventDao;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.request.QueryDataRequest;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.response.event.AllEventsResponse;
import wanart.bi.response.event.EventDetailResponse;
import wanart.bi.service.event.EventService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Resource
    private  EventDao eventDao;
    public AllEventsResponse queryAllEvents(String projectName) {
        AllEventsResponse response = new AllEventsResponse();
        response.setSuccess();
        List<String> eventList = eventDao.getEventNames(projectName);
        response.setEventList(eventList);
        return response;
    }

    public EventDetailResponse queryEventDetail(String projectName, String eventName){
        EventDetailResponse response = new EventDetailResponse();
        response.setSuccess();
        List<EventColumnEntity> entityList = eventDao.getEventDetailByName(projectName, eventName);
        response.setEventColumnList(entityList);
        return response;
    }

    public CommonDataResponse queryEventData(QueryDataRequest request){
        CommonDataResponse response = new CommonDataResponse();
        List<EventColumnEntity> eventColumnEntityList = eventDao.getEventDetailByName(request.getProject(), request.getEventName());
        if(eventColumnEntityList == null || eventColumnEntityList.size() == 0){
            response.setCommonFailure("no such project or event");
            return response;
        }
        if(!request.getGroup().equals("") && !SqlParamUtil.checkColumnExist(request.getGroup(), eventColumnEntityList)){
            response.setCommonFailure("invalid group column");
            return response;
        }
        if(StringUtils.isEmpty(request.getDistinct()) || !SqlParamUtil.checkColumnExist(request.getDistinct(), eventColumnEntityList)){
            response.setCommonFailure("invalid distinct column");
            return response;
        }

        String groupStr = SqlParamUtil.parseGroup(request.getGroup());
        String conditionStr = SqlParamUtil.parseCondition(request.getConditionList(), eventColumnEntityList);

        List<CommonDataEntity> resultList = null;

        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(request.getStartTime(), request.getEndTime());
        for(String timeSuffix: timeSpan){
            String tableName = request.getEventName() + "_" + timeSuffix;
            String separationStr = SqlParamUtil.parseSeparationTime(tableName, request.getSeparationTime(), eventColumnEntityList);
            List<CommonDataEntity> tmpResult;
            if(!groupStr.equals("")){
                tmpResult = eventDao.getDataByGroup(request.getProject(), tableName, request.getDistinct(), conditionStr, separationStr, request.getGroup(), groupStr );
            }else{
                tmpResult = eventDao.getData(request.getProject(), tableName, request.getDistinct(), conditionStr, separationStr);
            }
            if(resultList == null){
                resultList = tmpResult;
            }else{
                resultList.addAll(tmpResult);
            }
        }
        response.setResult(ResponseResult.Success);
        response.setMsg("success");
        response.setDataList(resultList);
        return response;
    }
}
