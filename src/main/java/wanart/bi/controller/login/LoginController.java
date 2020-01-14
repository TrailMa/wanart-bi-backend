package wanart.bi.controller.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.CommonResponse;
import wanart.bi.service.login.LoginService;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping(value = "/login")
    public CommonResponse login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
      return loginService.login(userName, password);
    }

}
