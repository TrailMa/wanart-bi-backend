package wanart.bi.service.funnel;

import wanart.bi.request.QueryFunnelRequest;
import wanart.bi.response.funnel.QueryFunnelResponse;

public interface FunnelService {
    QueryFunnelResponse queryFunnel(QueryFunnelRequest request);
}
