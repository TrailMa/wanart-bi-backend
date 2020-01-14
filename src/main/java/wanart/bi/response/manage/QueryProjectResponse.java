package wanart.bi.response.manage;

import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.response.CommonResponse;

import java.util.List;

public class QueryProjectResponse extends CommonResponse {
    private List<ProjectEntity> projectList;

    public List<ProjectEntity> getProjectList(){
        return projectList;
    }
    public void setProjectList(List<ProjectEntity> projectList){
        this.projectList = projectList;
    }
}
