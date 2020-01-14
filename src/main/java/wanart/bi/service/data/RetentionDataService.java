package wanart.bi.service.data;

import wanart.bi.response.data.RetentionDataResponse;

public interface RetentionDataService {
    RetentionDataResponse getData (String projectName, String distinct, String condition, String group, String startTime, String endTime, int steps);
}
