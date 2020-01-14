package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.PcuDataService;

import javax.annotation.Resource;

@RestController
public class PcuDataController {
    @Resource
    private PcuDataService pcuDataService;
    @GetMapping("/data/pcu")
    public CommonDataResponse queryPcu(@RequestParam("project") String project, @RequestParam("condition") String condition, @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return pcuDataService.getData(project, condition, startTime, endTime);
    }
}
