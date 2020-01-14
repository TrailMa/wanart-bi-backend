package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.CommonDataService;

import javax.annotation.Resource;

@RestController
public class NewlyIncomeDataController {
    @Resource
    private CommonDataService commonDataService;
    @GetMapping("/data/newlyIncome")
    public CommonDataResponse queryNewlyIncome(@RequestParam("project") String project, @RequestParam("distinct") String distinct, @RequestParam("condition") String condition, @RequestParam("group") String group,
                                       @RequestParam("separationTime") String separationTime, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return commonDataService.getData(project, "createchar_", distinct, condition, group, separationTime, startTime, endTime, "RoleCreatedDate");
    }
}
