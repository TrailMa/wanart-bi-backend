package wanart.bi.controller.manage;

import org.springframework.web.bind.annotation.*;
import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryUserResponse;
import wanart.bi.service.manage.ManageUserService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manage/user")
public class ManageUserController {
    @Resource
    private ManageUserService manageUserService;

    @GetMapping("/query")
    public QueryUserResponse query(){
        // todo 通过session 获取userId
        return manageUserService.query(1);
    }
    @GetMapping("/queryByProject")
    public QueryUserResponse queryByProject(@RequestParam("projectId") int projectId){
        return manageUserService.queryByProject(1, projectId);
    }

    @GetMapping("/update")
    public CommonResponse update(@RequestParam("targetUserId") int targetUserId, @RequestParam("group") int group){
        return manageUserService.update(1, targetUserId, group);
    }

    @GetMapping("/delete")
    public CommonResponse delete(@RequestParam("targetUserId") int targetUserId){
       return manageUserService.delete(2, targetUserId) ;
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("group") int group){
        return manageUserService.create(1, userName, password, group);
    }


    @GetMapping("/updateCurProjectId")
    public CommonResponse updateCurProjectId(@RequestParam("curProjectId") int curProjectId){
        return manageUserService.updateCurProjectId(1, curProjectId);
    }
}