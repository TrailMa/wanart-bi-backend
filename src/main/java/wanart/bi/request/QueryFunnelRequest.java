package wanart.bi.request;
import java.util.List;

public class QueryFunnelRequest {
    private String project;
    private String startTime;
    private int steps;
    private List<FunnelQuery> funnelQueryList;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getSeparationTime() {
        return "day";
    }
    public String getDistinct(){
        return "uid";
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public FunnelQuery fetchHeadQuery() {
        if(funnelQueryList == null || funnelQueryList.size() == 0){
            return null;
        }
        return funnelQueryList.get(0);
    }

    public List<FunnelQuery> fetchFunnelTailQuerys() {
        if(funnelQueryList == null || funnelQueryList.size() <= 1){
            return null;
        }
        return funnelQueryList.subList(1, funnelQueryList.size());
    }

    public List<FunnelQuery> getFunnelQueryList(){
        return funnelQueryList;
    }
    public void setFunnelQueryList(List<FunnelQuery> tailQueryList){
        this.funnelQueryList = tailQueryList;
    }

}
