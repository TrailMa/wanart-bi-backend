package wanart.bi.controller.data;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.request.RetentionRequest;
import wanart.bi.response.data.RetentionDataResponse;
import wanart.bi.service.data.RetentionDataService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class RetentionDataController {
    @Resource
    private RetentionDataService retentionDataService;

    @RequestMapping("/data/retention")
    public RetentionDataResponse queryRetention(@RequestBody RetentionRequest retentionRequest, HttpSession session) {
        return retentionDataService.getData(retentionRequest);
    }
}
