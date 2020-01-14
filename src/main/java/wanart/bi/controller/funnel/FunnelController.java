package wanart.bi.controller.funnel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wanart.bi.request.QueryFunnelRequest;
import wanart.bi.response.funnel.QueryFunnelResponse;
import wanart.bi.service.funnel.FunnelService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/funnel")
public class FunnelController {
    @Resource
    private FunnelService funnelService;
    @PostMapping("/query")
    public QueryFunnelResponse queryFunnel(@RequestBody QueryFunnelRequest queryFunnelRequest){
       return funnelService.queryFunnel(queryFunnelRequest);
    }
}
