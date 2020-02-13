package wanart.bi.service.login;

import wanart.bi.response.CommonResponse;
import wanart.bi.response.login.LoginResponse;

public interface LoginService {
    LoginResponse login(String name, String password);
}
