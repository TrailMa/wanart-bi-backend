package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.response.data.RetentionDataResponse;
import wanart.bi.service.data.RetentionDataService;

import javax.annotation.Resource;

@RestController
public class RetentionDataController {
    @Resource
    private RetentionDataService retentionDataService;

    @GetMapping("/data/retention")
    public RetentionDataResponse queryRetention(@RequestParam("project") String project, @RequestParam("distinct") String distinct, @RequestParam("condition") String condition, @RequestParam("group") String group,
                                                @RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime, @RequestParam("steps") int steps) {
        return retentionDataService.getData(project, distinct, condition, group, startTime, endTime, steps);
    }
}
