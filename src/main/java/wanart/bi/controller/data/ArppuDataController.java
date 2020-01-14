package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.ArppuDataService;

import javax.annotation.Resource;

@RestController
public class ArppuDataController {
    @Resource
    private ArppuDataService arppuDataService;
    @GetMapping("/data/arppu")

    public CommonDataResponse queryDau( @RequestParam("project") String project, @RequestParam("distinct") String distinct, @RequestParam("condition") String condition, @RequestParam("group") String group,
                                       @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
        return arppuDataService.getData(project, distinct, condition, group, startTime, endTime);
    }
}