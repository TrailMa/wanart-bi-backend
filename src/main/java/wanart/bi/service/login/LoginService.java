package wanart.bi.service.login;

import wanart.bi.response.login.LoginResponse;

import javax.servlet.http.HttpSession;

public interface LoginService {
    LoginResponse login(String name, String password, HttpSession session);
}
