package wanart.bi.controller.manage;

import org.springframework.web.bind.annotation.*;
import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryUserResponse;
import wanart.bi.service.manage.ManageUserService;
import wanart.bi.util.SessionUtil;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {
    @Resource
    private ManageUserService manageUserService;

    @GetMapping("/query")
    public QueryUserResponse query(){
        int userId = SessionUtil.getUserId();
        return manageUserService.query(userId);
    }
    @GetMapping("/queryByProject")
    public QueryUserResponse queryByProject(@RequestParam("projectId") int projectId){
        int userId = SessionUtil.getUserId();
        return manageUserService.queryByProject(userId, projectId);
    }

    @GetMapping("/update")
    public CommonResponse update(@RequestParam("targetUserId") int targetUserId, @RequestParam("group") int group){
        int userId = SessionUtil.getUserId();
        return manageUserService.update(userId, targetUserId, group);
    }

    @GetMapping("/delete")
    public CommonResponse delete(@RequestParam("targetUserId") int targetUserId){
        int userId = SessionUtil.getUserId();
       return manageUserService.delete(userId, targetUserId) ;
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("group") int group){
        int userId = SessionUtil.getUserId();
        return manageUserService.create(userId, userName, password, group);
    }


    @GetMapping("/updateCurProjectId")
    public CommonResponse updateCurProjectId(@RequestParam("curProjectId") int curProjectId){
        int userId = SessionUtil.getUserId();
        return manageUserService.updateCurProjectId(userId, curProjectId);
    }
}