package wanart.bi.service.data;

import wanart.bi.response.data.CommonDataResponse;

public interface IncomeDataService {
    CommonDataResponse getData(String projectName, String condition,  String group, String separationTime, String startTime, String endTime);
}
