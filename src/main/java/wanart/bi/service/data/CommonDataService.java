package wanart.bi.service.data;

import wanart.bi.response.data.CommonDataResponse;

public interface CommonDataService {
    CommonDataResponse getData(String projectName, String tableNamePrefix, String distinct,  String condition,  String group, String separationTime, String startTime, String endTime, String timeColumn);
}
