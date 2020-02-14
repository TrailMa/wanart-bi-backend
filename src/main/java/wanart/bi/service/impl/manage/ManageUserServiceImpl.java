package wanart.bi.service.impl.manage;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import wanart.bi.common.ManageActionEnum;
import wanart.bi.common.ManageGroupEnum;
import wanart.bi.common.ManageUserAuth;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.manage.UserDao;
import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryUserResponse;
import wanart.bi.entity.manage.UserEntity;
import wanart.bi.service.manage.ManageUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManageUserServiceImpl implements ManageUserService {
    @Resource
    private UserDao userDao;

    public QueryUserResponse query(int userId){
        QueryUserResponse response = new QueryUserResponse();
        UserEntity userEntity = userDao.getUserById(userId);
       if(userEntity == null) {
           response.setResult(ResponseResult.Failed);
           response.setMsg("user not eixst");
           return response;
       }
       if(!ManageUserAuth.checkManageUserAuth(userEntity, ManageActionEnum.QUERY)) {
           response.setResult(ResponseResult.Failed);
           response.setMsg("check auth failed");
           return response;
       }
        List<UserEntity> userList = userDao.getAllUsers();
        response.setResult(ResponseResult.Success);
        response.setMsg("success");
        response.setUserList(userList);
        return response;
    }

    public QueryUserResponse queryByProject(int userId, int projectId){
        QueryUserResponse response = new QueryUserResponse();
        UserEntity userEntity = userDao.getUserById(userId);
        if(userEntity == null) {
            response.setResult(ResponseResult.Failed);
            response.setMsg("user not eixst");
            return response;
        }
        if(!ManageUserAuth.checkManageUserAuth(userEntity, ManageActionEnum.QUERY)) {
            response.setResult(ResponseResult.Failed);
            response.setMsg("check auth failed");
            return response;
        }
        if(!(userEntity.isSuperAdmin() || userEntity.containsProject(projectId))){
            response.setResult(ResponseResult.Failed);
            response.setMsg("not contains project");
            return response;
        }
        response.setSuccess();
        List<UserEntity> userList = userDao.getUsersByProjectId(projectId);
        if(userList == null) {
            return response;
        }
        ArrayList<UserEntity> finalUsers = new ArrayList<>();
        for(UserEntity u : userList){
           if(u.containsProject(projectId)) {
               finalUsers.add(u);
           }
        }
        response.setUserList(finalUsers);
        return response;
    }

    public CommonResponse update(int userId, int targetUserId, int group){
        CommonResponse response = new CommonResponse();
        UserEntity userEntity = userDao.getUserById(userId);
        if(userEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user id not exists");
            return response;
        }
        if(!ManageUserAuth.checkManageUserAuth(userEntity, ManageActionEnum.UPDATE)){
            response.setResult(ResponseResult.Failed);
            response.setMsg("auth check failed");
            return response;
        }
        UserEntity targetUserEntity = userDao.getUserById(targetUserId);
        if(targetUserEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("target user id not exists");
            return response;
        }
        response.setSuccess();
        userDao.updateUserGroup(targetUserId, group);
        return response;
    }

    public CommonResponse delete(int userId, int targetUserId){
        CommonResponse response = new CommonResponse();
        if(userId == targetUserId){
            response.setResult(ResponseResult.Failed);
            response.setMsg("can not delete self");
            return response;
        }
        UserEntity userEntity = userDao.getUserById(userId);
        if(userEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user id not exists");
            return response;
        }
        if(!ManageUserAuth.checkManageUserAuth(userEntity, ManageActionEnum.DELETE)){
            response.setResult(ResponseResult.Failed);
            response.setMsg("auth check failed");
            return response;
        }
        UserEntity targetUserEntity = userDao.getUserById(targetUserId);
        if(targetUserEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("target user id not exists");
            return response;
        }
        if(ManageGroupEnum.valueOf(targetUserEntity.getGroup()) == ManageGroupEnum.SUPER_ADMIN){
            response.setResult(ResponseResult.Failed);
            response.setMsg("can not delete super admin");
            return response;
        }

        response.setSuccess();
        userDao.deleteUserById(targetUserId);
        return response;
    }

    public CommonResponse create(int userId, String name, String password, int group){
        CommonResponse response = new CommonResponse();
       if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)) {
          response.setResult(ResponseResult.Failed);
          response.setMsg("invalid param");
          return response;
       }
       if(ManageGroupEnum.valueOf(group) == ManageGroupEnum.SUPER_ADMIN){
           response.setResult(ResponseResult.Failed);
           response.setMsg("can not create super admin");
           return response;
       }
       UserEntity userEntity = userDao.getUserById(userId);
       if(userEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user id not exists");
            return response;
        }
        if(!ManageUserAuth.checkManageUserAuth(userEntity, ManageActionEnum.CREATE)){
            response.setResult(ResponseResult.Failed);
            response.setMsg("auth check failed");
            return response;
        }
        UserEntity targetUserEntity = userDao.getUserByName(name);
        if(targetUserEntity != null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user name already exists");
            return response;
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        boolean result = userDao.createUser(name, md5Password, group);
        if(!result){
            response.setResult(ResponseResult.Failed);
            response.setMsg("create user failed");
            return response;
        }
        response.setSuccess();
        return response;
    }

    public CommonResponse updateCurProjectId(int userId, int curProjectId){
        CommonResponse response = new CommonResponse();
        UserEntity userEntity = userDao.getUserById(userId);
        if(userEntity == null) {
            response.setResult(ResponseResult.Failed);
            response.setMsg("user not eixst");
            return response;
        }
        userDao.updateCurProjectId(userId, curProjectId);
        response.setSuccess();
        return  response;
    }
}
