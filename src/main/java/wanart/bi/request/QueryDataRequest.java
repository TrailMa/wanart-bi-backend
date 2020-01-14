package wanart.bi.request;

import java.util.List;

public class QueryDataRequest {

    private String project;
    private String eventName;
    private String distinct;
    private String group;
    private String separationTime;
    private String startTime;
    private String endTime;
    private List<QueryConditionRequest> conditionList;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDistinct() {
        return distinct;
    }

    public void setDistinct(String distinct) {
        this.distinct = distinct;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSeparationTime() {
        return separationTime;
    }

    public void setSeparationTime(String separationTime) {
        this.separationTime = separationTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<QueryConditionRequest> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<QueryConditionRequest> conditionList) {
        this.conditionList = conditionList;
    }
}
