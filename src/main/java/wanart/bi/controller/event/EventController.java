package wanart.bi.controller.event;

import org.springframework.web.bind.annotation.*;
import wanart.bi.request.QueryDataRequest;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.response.event.AllEventsResponse;
import wanart.bi.response.event.EventDetailResponse;
import wanart.bi.service.event.EventService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/event")
public class EventController {
    @Resource
    private EventService eventService;

    @GetMapping("/queryAll")
    public AllEventsResponse queryAllEvents(@RequestParam("project") String project) {
        return eventService.queryAllEvents(project);
    }

    @GetMapping("/queryDetail")
    public EventDetailResponse queryEventDetail(@RequestParam("project") String project, @RequestParam("eventName") String eventName){
       return eventService.queryEventDetail(project, eventName);
    }

    @PostMapping("/queryData")
    public CommonDataResponse queryData(@RequestBody QueryDataRequest queryDataRequest){
       return eventService.queryEventData(queryDataRequest);
    }
}
