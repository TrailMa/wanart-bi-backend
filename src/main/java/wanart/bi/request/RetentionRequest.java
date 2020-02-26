package wanart.bi.request;

import java.util.List;

public class RetentionRequest {
        private String project;
        private String distinct;

        private String firstEventName;
        private List<QueryConditionRequest> conditionList;

        private String secondEventName;

        private String group;
        private String startTime;
        private String endTime;
        private int steps;

        public String getProject() {
                return project;
        }
        public void setProject(String project) {
                this.project = project;
        }

        public String getDistinct() {
                return distinct;
        }
        public void setDistinct(String distinct) {
                this.distinct = distinct;
        }

        public String getFirstEventName() {
                return firstEventName;
        }

        public void setFirstEvent(String firstEventName) {
                this.firstEventName = firstEventName;
        }

        public List<QueryConditionRequest> getConditionList() {
                return conditionList;
        }

        public void setConditionList(List<QueryConditionRequest> conditionList) {
                this.conditionList = conditionList;
        }

        public String getSecondEventName() {
                return secondEventName;
        }

        public void setSecondEventName(String secondEventName) {
                this.secondEventName = secondEventName;
        }

        public int getSteps() {
                return steps;
        }

        public void setSteps(int steps) {
                this.steps = steps;
        }

        public String getGroup() {
                return group;
        }

        public void setGroup(String group) {
                this.group = group;
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
}
