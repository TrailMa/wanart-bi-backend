package wanart.bi.service.data;

import wanart.bi.response.data.CommonDataResponse;

public interface PcuDataService {
    CommonDataResponse getData(String projectName, String condition, String startTime, String endTime);
}
