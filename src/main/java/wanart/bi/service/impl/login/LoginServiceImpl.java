package wanart.bi.service.impl.login;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.manage.UserDao;
import wanart.bi.response.CommonResponse;
import wanart.bi.entity.manage.UserEntity;
import wanart.bi.service.login.LoginService;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private UserDao userDao;
    public CommonResponse login(String name, String password){
        CommonResponse response = new CommonResponse();

        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            response.setResult(ResponseResult.Failed);
            response.setMsg("invalid param");
            return response;
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserEntity userEntity = userDao.getUserByPassword(name, md5Password);
        if(userEntity == null){
            response.setResult(ResponseResult.Failed);
            response.setMsg("user not exist or invalid password");
            return response;
        }
        response.setSuccess();
        userDao.updateLastLoginTime(userEntity.getId());
        return response;
    }
}
