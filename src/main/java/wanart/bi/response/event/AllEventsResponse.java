package wanart.bi.response.event;

import wanart.bi.response.CommonResponse;

import java.util.List;

public class AllEventsResponse extends CommonResponse {
    private List<String> eventList;

    public List<String> getEventList(){
        return eventList;
    }
    public void setEventList(List<String> eventList) {
        this.eventList = eventList;
    }
}
