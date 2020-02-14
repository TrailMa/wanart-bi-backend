package wanart.bi.controller.manage;

import org.springframework.web.bind.annotation.*;
import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryProjectResponse;
import wanart.bi.service.manage.ManageProjectService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/manage/project")
public class ManageProjectController {
    @Resource
    private ManageProjectService manageProjectService;

    // 查询该user所拥有的的所有项目
    @GetMapping("/queryAll")
    public QueryProjectResponse queryAll(){
        //todo 根据session获取user id
        return manageProjectService.getProjectsByUserId(1);
    }

    // 查询某个项目
    @GetMapping("/queryById")
    public QueryProjectResponse queryById(@RequestParam("projectId") int projectId){
        return manageProjectService.getProjectById(2, projectId);
    }

    // 添加项目组成员
    @GetMapping("/addProjectUser")
    public CommonResponse addProjectUser(@RequestParam("projectId") int projectId, @RequestParam("targetId") int targetId){
        return manageProjectService.addProjectUser(1, projectId, targetId);
    }
    // 删除项目组成员
    @GetMapping("/deleteProjectUser")
    public CommonResponse deleteProjectUser(@RequestParam("projectId") int projectId, @RequestParam("targetId") int targetId){
        return manageProjectService.deleteProjectUser(1, projectId, targetId);
    }

    // 删除项目
    @GetMapping("/delete")
    public CommonResponse delete(@RequestParam("projectId") int projectId){
        return manageProjectService.deleteProject(1, projectId);
    }

    @PostMapping("/create")
    public CommonResponse create(@RequestParam("projectName") String projectName, @RequestParam("desc") String desc){
        return manageProjectService.createProject(1, projectName, desc);
    }
}

