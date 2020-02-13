package wanart.bi.response.login;

import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.response.CommonResponse;

import java.util.List;

public class LoginResponse extends CommonResponse {
    private List<ProjectEntity> projectList;
    private int curProjectId;

    public List<ProjectEntity> getProjectList(){
        return projectList;
    }

    public void setProjectList(List<ProjectEntity> projectList) {
        this.projectList = projectList;
    }

    public int getCurProjectId() {
        return curProjectId;
    }

    public void setCurProjectId(int curProjectId) {
        this.curProjectId = curProjectId;
    }
}
