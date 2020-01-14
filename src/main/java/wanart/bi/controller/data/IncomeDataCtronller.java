package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.IncomeDataService;

import javax.annotation.Resource;

@RestController
public class IncomeDataCtronller {
    @Resource
    private IncomeDataService incomeDataService;
    @GetMapping("/data/income")
    public CommonDataResponse queryIncome(@RequestParam("project") String project, @RequestParam("condition") String condition, @RequestParam("group") String group, @RequestParam("separationTime") String separationTime,
                                          @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return incomeDataService.getData(project, condition, group, separationTime, startTime, endTime);
    }
}
