package wanart.bi.service.impl.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ManageActionEnum;
import wanart.bi.common.ManageProjectAuth;
import wanart.bi.dao.manage.ProjectDao;
import wanart.bi.dao.manage.UserDao;
import wanart.bi.response.CommonResponse;
import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.response.manage.QueryProjectResponse;
import wanart.bi.entity.manage.UserEntity;
import wanart.bi.service.manage.ManageProjectService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerProjectServiceImpl implements ManageProjectService {
    @Resource
    private UserDao userDao;
    @Resource
    private ProjectDao projectDao;

    public QueryProjectResponse getProjectsByUserId(int userId){
        QueryProjectResponse response = new QueryProjectResponse();
        UserEntity user = userDao.getUserById(userId);
        if(user == null){
            response.setCommonFailure("user not exist");
            return response;
        }

        response.setSuccess();
        if(user.isSuperAdmin()){
           response.setProjectList(projectDao.getAllProjects());
        }
        else{
            response.setProjectList(projectDao.getAllProjects());
        }
        return response;
    }

    public QueryProjectResponse getProjectById(int userId, int projectId){
        QueryProjectResponse response = new QueryProjectResponse();
        UserEntity user = userDao.getUserById(userId);
        if(user == null){
            response.setCommonFailure("user not exist");
        }

        if(!ManageProjectAuth.checkManageProjectAuth(user, projectId, ManageActionEnum.QUERY)){
            response.setCommonFailure("check auth failed");
            return response;
        }

        response.setSuccess();
        ArrayList<ProjectEntity> projectList = new ArrayList<>();
        ProjectEntity projectEntity = projectDao.getProjectById(projectId);
        if(projectEntity != null){
            projectList.add(projectEntity);
        }
        response.setProjectList( projectList);
        return response;
    }

    public CommonResponse addProjectUser(int userId, int projectId, int targetId){
        CommonResponse response = new CommonResponse();
        UserEntity user = userDao.getUserById(userId);
        if(user == null) {
            response.setCommonFailure("user not exist");
            return response;
        }
        if(!ManageProjectAuth.checkManageProjectAuth(user, projectId, ManageActionEnum.UPDATE)){
            response.setCommonFailure("check auth failed");
            return response;
        }

        ProjectEntity projectEntity = projectDao.getProjectById(projectId);
        if(projectEntity == null){
            response.setCommonFailure("project not exist");
            return response;
        }

        UserEntity targetUser = userDao.getUserById(targetId);
        if(targetUser == null) {
            response.setCommonFailure("target user not exist");
            return response;
        }
        if(targetUser.isSuperAdmin()){
            response.setCommonFailure("can not add super admin to project");
            return response;
        }

        response.setSuccess();
        if(!targetUser.containsProject(projectId)) {
            targetUser.addProject(projectId);
            String projectsString = targetUser.projectsToString();
            userDao.updateUserProjects(targetId, projectsString);
        }
        return response;
    }

    public CommonResponse deleteProjectUser(int userId, int projectId, int targetId){
        CommonResponse response = new CommonResponse();
        UserEntity user = userDao.getUserById(userId);
        if(user == null) {
            response.setCommonFailure("user not exist");
            return response;
        }
        if(!ManageProjectAuth.checkManageProjectAuth(user, projectId, ManageActionEnum.UPDATE)){
            response.setCommonFailure("check auth failed");
            return response;
        }

        ProjectEntity projectEntity = projectDao.getProjectById(projectId);
        if(projectEntity == null){
            response.setCommonFailure("project not exist");
            return response;
        }

        UserEntity targetUser = userDao.getUserById(targetId);
        if(targetUser == null) {
            response.setCommonFailure("target user not exist");
            return response;
        }
        if(targetUser.isSuperAdmin()){
            response.setCommonFailure("can not delete super admin to project");
            return response;
        }
        if(!targetUser.containsProject(projectId)){
            response.setCommonFailure("target user not contains project");
            return response;
        }

        // targetUser 删除项目
        targetUser.deleteProject(projectId);
        String projectsString = targetUser.projectsToString();
        userDao.updateUserProjects(targetId, projectsString);

        response.setSuccess();
        return response;
    }


    public CommonResponse deleteProject(int userId, int projectId){
        CommonResponse response = new CommonResponse();
        UserEntity user = userDao.getUserById(userId);
        if(user == null) {
            response.setCommonFailure("user not exist");
            return response;
        }
        if(!ManageProjectAuth.checkManageProjectAuth(user, projectId, ManageActionEnum.DELETE)){
            response.setCommonFailure("check auth failed");
            return response;
        }
        ProjectEntity projectEntity = projectDao.getProjectById(projectId);
        if(projectEntity == null){
            response.setCommonFailure("project not exist");
            return response;
        }

        projectDao.deleteProjectById(projectId);
        List<UserEntity> userList = userDao.getUsersByProjectId(projectId);
        if(userList != null) {
           for(UserEntity u : userList) {
               if(!u.containsProject(projectId)){
                  continue;
               }
               u.deleteProject(projectId);
               userDao.updateUserProjects(u.getId(), u.projectsToString());
           }
        }

        response.setSuccess();
        return response;
    }

    public CommonResponse createProject(int userId, String projectName, String desc){
        CommonResponse response = new CommonResponse();
        if(StringUtils.isEmpty(projectName)){
            response.setCommonFailure("project name can not be empty");
            return response;
        }
        UserEntity user = userDao.getUserById(userId);
        if(user == null) {
            response.setCommonFailure("user not exist");
            return response;
        }
        if(!ManageProjectAuth.checkManageProjectAuth(user, 0, ManageActionEnum.CREATE)){
            response.setCommonFailure("check auth failed");
            return response;
        }
        ProjectEntity projectEntity = projectDao.getProjectByName(projectName);
        if(projectEntity != null){
            response.setCommonFailure("project already exist");
            return response;
        }
        boolean result = projectDao.createProject(projectName, desc);
        if(!result){
            response.setCommonFailure("create project failed");
            return response;
        }
        response.setSuccess();
        return response;
    }
}

