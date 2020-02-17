package wanart.bi.service.impl.funnel;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.dao.event.EventDao;
import wanart.bi.dao.funnel.FunnelDao;
import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.entity.funnel.FunnelResultEntity;
import wanart.bi.request.FunnelQuery;
import wanart.bi.request.QueryConditionRequest;
import wanart.bi.request.QueryFunnelRequest;
import wanart.bi.response.funnel.QueryFunnelResponse;
import wanart.bi.service.funnel.FunnelService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class FunnelServiceImpl implements FunnelService {
    @Resource
    private FunnelDao funnelDao;
    @Resource
    private EventDao eventDao;

    public QueryFunnelResponse queryFunnel(QueryFunnelRequest request){
        QueryFunnelResponse response = new QueryFunnelResponse();

        List<Integer> uidList = QueryFunnelHead(request, response);
        if(uidList == null){
            return response;
        }
        QueryFunnelTails(request, response, uidList);
        return response;
    }

    private List<Integer> QueryFunnelHead(QueryFunnelRequest request, QueryFunnelResponse response){
        FunnelQuery headQuery = request.fetchHeadQuery();
        if(headQuery == null){
            response.setCommonFailure("funnel head query invalid");
            return null;
        }
        List<EventColumnEntity> eventColumnEntityList = eventDao.getEventDetailByName(request.getProject(), headQuery.getEventName());
        if(eventColumnEntityList == null || eventColumnEntityList.size() == 0){
            response.setCommonFailure(String.format("no such project or event %s", headQuery.getEventName()));
            return null;
        }
        if(StringUtils.isEmpty(request.getDistinct()) || !SqlParamUtil.checkColumnExist(request.getDistinct(), eventColumnEntityList)){
            response.setCommonFailure("invalid distinct column");
            return null;
        }

        String conditionStr = SqlParamUtil.parseCondition(headQuery.getConditionList(), eventColumnEntityList);
        String startTime = request.getStartTime();
        String timeSuffix = SqlParamUtil.getTimeSuffixFromTime(startTime);
        String tableName = SqlParamUtil.getTableName(headQuery.getEventName(), timeSuffix);

        List<Integer> uidList =   funnelDao.getHeadFunnelUids(request.getProject(), tableName, conditionStr);

        FunnelResultEntity headResult = new FunnelResultEntity();
        headResult.setEventName(request.fetchHeadQuery().getEventName());
        headResult.setValue(uidList.size());
        response.addFunnelResult(headResult);
        return uidList;
    }

    private boolean QueryFunnelTails(QueryFunnelRequest request, QueryFunnelResponse response, List<Integer> preUidList){
        List<FunnelQuery> queryList = request.fetchFunnelTailQuerys();
        if(queryList == null){
            response.setCommonFailure("invalid tail funnel query");
            return false;
        }
        for(FunnelQuery query : queryList){
            List<EventColumnEntity> eventColumnEntityList = eventDao.getEventDetailByName(request.getProject(), query.getEventName());
            if(eventColumnEntityList == null || eventColumnEntityList.size() == 0){
                response.setCommonFailure(String.format("no such project or event %s", query.getEventName()));
                return false;
            }
            if(StringUtils.isEmpty(request.getDistinct()) || !SqlParamUtil.checkColumnExist(request.getDistinct(), eventColumnEntityList)){
                response.setCommonFailure("invalid distinct column");
                return false;
            }
            response.setSuccess();
            QueryConditionRequest inUidCondition = new QueryConditionRequest();
            inUidCondition.setColumnName(request.getDistinct());
            inUidCondition.setCombineType("none");
            inUidCondition.setConditionOperator("IN");
            inUidCondition.setConditionParam(SqlParamUtil.getInUidsStr());
            query.addTailCondition(inUidCondition);
            String conditionStr = SqlParamUtil.parseCondition(query.getConditionList(), eventColumnEntityList);
            preUidList = queryOneTailFunnel(request.getProject(), query.getEventName(), conditionStr, request.getStartTime(), request.getSteps(), preUidList);

            FunnelResultEntity tailResult = new FunnelResultEntity();
            tailResult.setEventName(query.getEventName());
            tailResult.setValue(preUidList.size());
            response.getFunnelResultList().add(tailResult);
        }
        return true;
    }

    private List<Integer> queryOneTailFunnel(String projectName, String eventName, String condition, String startTime, int step, List<Integer> preUidList){
        List<String> timeSuffixList = SqlParamUtil.calcTimeSuffix(startTime, step);
        HashSet<Integer> resultHashSet = new HashSet<>(1024);
        for(String timeSuffix : timeSuffixList){
            String tableName = SqlParamUtil.getTableName(eventName, timeSuffix);
            try {
                List<Integer> tmpResult = funnelDao.getTailFunnelUids(projectName, tableName, condition, preUidList);
                resultHashSet.addAll(tmpResult);
            }catch (Exception e){

            }
        }
        return new ArrayList<>(resultHashSet);
    }
}
