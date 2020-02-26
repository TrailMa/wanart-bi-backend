package wanart.bi.service.data;

import wanart.bi.request.RetentionRequest;
import wanart.bi.response.data.RetentionDataResponse;

public interface RetentionDataService {
    RetentionDataResponse getData (RetentionRequest retentionRequest);
}
