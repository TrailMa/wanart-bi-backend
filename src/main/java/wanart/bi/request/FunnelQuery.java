package wanart.bi.request;

import java.util.ArrayList;
import java.util.List;

public class FunnelQuery {
    private String eventName;
    private List<QueryConditionRequest> conditionList;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<QueryConditionRequest> getConditionList() {
        return conditionList;
    }

    // 在原有的conditionList下 添加新的condition
    public void addTailCondition(QueryConditionRequest condition){
        if(conditionList == null){
            conditionList = new ArrayList<>();
        }
        if(!conditionList.isEmpty()){
           QueryConditionRequest tailCondition = conditionList.get(conditionList.size() - 1);
           tailCondition.setCombineType("and");
        }
        conditionList.add(condition);
    }

    public void setConditionList(List<QueryConditionRequest> conditionList) {
        this.conditionList = conditionList;
    }
}
