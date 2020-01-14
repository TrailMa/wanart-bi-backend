package wanart.bi.service.data;

import wanart.bi.response.data.CommonDataResponse;

public interface ArpuDataService {
    CommonDataResponse getData(String projectName, String distinct, String condition, String group, String startTime, String endTime);
}
