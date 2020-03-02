package wanart.bi.service.impl.login;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.manage.ProjectDao;
import wanart.bi.dao.manage.UserDao;
import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.entity.manage.UserEntity;
import wanart.bi.response.login.LoginResponse;
import wanart.bi.service.login.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserDao userDao;
    @Resource
    private ProjectDao projectDao;
    public LoginResponse login(String name, String password, HttpSession session){
        LoginResponse response = new LoginResponse();

        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            response.setResult(ResponseResult.Failed);
            response.setMsg("invalid param");
            return response;
        }
        //String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserEntity userEntity = userDao.getUserByPassword(name, password);
        if(userEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user not exist or invalid password");
            return response;
        }
        // save userId in session
        session.setAttribute("userId", userEntity.getId());

        List<ProjectEntity> projectList;
        if(userEntity.isSuperAdmin() ){
            projectList = projectDao.getAllProjects();
        }else{
            projectList = projectDao.getProjectsByIds(userEntity.getProjests());
        }
        response.setCurProjectId(userEntity.getCurProjectId());
        response.setProjectList(projectList);
        response.setSuccess();
        userDao.updateLastLoginTime(userEntity.getId());
        return response;
    }
}
