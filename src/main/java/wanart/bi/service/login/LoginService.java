package wanart.bi.service.login;

import wanart.bi.response.CommonResponse;

public interface LoginService {
    CommonResponse login(String name, String password);
}
