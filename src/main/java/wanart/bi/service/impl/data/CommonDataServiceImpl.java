package wanart.bi.service.impl.data;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.data.CommonDataDao;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.CommonDataService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonDataServiceImpl implements CommonDataService {
    @Resource
    private CommonDataDao commonDataDao;
    public CommonDataResponse getData(String projectName, String tableNamePrefix, String distinct,  String condition,  String group, String separationTime, String startTime, String endTime, String timeColumn){
        CommonDataResponse response = new CommonDataResponse();

        boolean byGroup = !StringUtils.isEmpty(group);
        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(startTime, endTime);
        List<CommonDataEntity> resultList = null;
        for(String timeSuffix: timeSpan){
            try {
                String tableName = tableNamePrefix + timeSuffix;
                List<CommonDataEntity> tmpResult;
                if(byGroup){
                    tmpResult = commonDataDao.getCommonDataByGroup(projectName, tableName, distinct, condition, separationTime, group, timeColumn);
                }else{
                    tmpResult = commonDataDao.getCommonData(projectName, tableName, distinct, condition, separationTime, timeColumn);
                }

                if(resultList == null){
                    resultList = tmpResult;
                }else{
                    resultList.addAll(tmpResult);
                }
            }
            catch (Exception e){
                System.out.print(e.toString());
            }
        }
        response.setResult(ResponseResult.Success);
        response.setMsg("success");
        response.setDataList(resultList);
        return response;
    }
}
