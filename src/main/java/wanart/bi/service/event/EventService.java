package wanart.bi.service.event;

import wanart.bi.request.QueryDataRequest;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.response.event.AllEventsResponse;
import wanart.bi.response.event.EventDetailResponse;

public interface EventService {
   AllEventsResponse queryAllEvents(String projectName);
   EventDetailResponse queryEventDetail(String projectName, String eventName);
   CommonDataResponse queryEventData(QueryDataRequest request);
}
