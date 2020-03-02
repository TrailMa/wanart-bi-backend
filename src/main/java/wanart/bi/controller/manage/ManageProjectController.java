package wanart.bi.controller.manage;

import org.springframework.web.bind.annotation.*;
import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryProjectResponse;
import wanart.bi.service.manage.ManageProjectService;
import wanart.bi.util.SessionUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/project")
public class ManageProjectController {
    @Resource
    private ManageProjectService manageProjectService;

    // 查询该user所拥有的的所有项目
    @GetMapping("/queryAll")
    public QueryProjectResponse queryAll(HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.getProjectsByUserId(userId);
    }

    // 查询某个项目
    @GetMapping("/queryById")
    public QueryProjectResponse queryById(@RequestParam("projectId") int projectId, HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.getProjectById(userId, projectId);
    }

    // 添加项目组成员
    @GetMapping("/addProjectUser")
    public CommonResponse addProjectUser(@RequestParam("projectId") int projectId, @RequestParam("targetId") int targetId, HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.addProjectUser(userId, projectId, targetId);
    }
    // 删除项目组成员
    @GetMapping("/deleteProjectUser")
    public CommonResponse deleteProjectUser(@RequestParam("projectId") int projectId, @RequestParam("targetId") int targetId, HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.deleteProjectUser(userId, projectId, targetId);
    }

    // 删除项目
    @GetMapping("/delete")
    public CommonResponse delete(@RequestParam("projectId") int projectId, HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.deleteProject(userId, projectId);
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestParam("projectName") String projectName, @RequestParam("desc") String desc, HttpSession session){
        int userId = SessionUtil.getUserId();
        return manageProjectService.createProject(userId, projectName, desc);
    }

}

