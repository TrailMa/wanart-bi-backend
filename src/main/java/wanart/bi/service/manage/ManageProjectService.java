package wanart.bi.service.manage;

import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryProjectResponse;

public interface ManageProjectService {
    QueryProjectResponse getProjectsByUserId(int userId);
    QueryProjectResponse getProjectById(int userId, int projectId);

    CommonResponse addProjectUser(int userId, int projectId, int targetId);
    CommonResponse deleteProjectUser(int userId, int projectId, int targetId);

    CommonResponse deleteProject(int userId, int projectId);

    CommonResponse createProject(int userId, String projectName, String desc);
}
