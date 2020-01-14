package wanart.bi.response.event;

import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.response.CommonResponse;

import java.util.List;

public class EventDetailResponse extends CommonResponse {
    private List<EventColumnEntity> eventColumnList;

    public List<EventColumnEntity> getEventColumnList(){
        return eventColumnList;
    }

    public void setEventColumnList(List<EventColumnEntity> eventColumnList) {
        this.eventColumnList = eventColumnList;
    }
}
