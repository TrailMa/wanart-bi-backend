package wanart.bi.service.impl.data;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.data.PcuDataDao;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.PcuDataService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PcuDataServiceImpl implements PcuDataService {
    @Resource
    private PcuDataDao pcuDataDao;

    public CommonDataResponse getData(String projectName, String condition, String startTime, String endTime){
        String tableNamePrefix = "online_";
        CommonDataResponse response = new CommonDataResponse();
        boolean byCondition = !StringUtils.isEmpty(condition);
        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(startTime, endTime);
        List<CommonDataEntity> resultList = null;
        for(String timeSuffix: timeSpan){
            try {
                String tableName = tableNamePrefix + timeSuffix;
                List<CommonDataEntity> tmpResult;
                if(byCondition){
                    tmpResult = pcuDataDao.getDataByCondition(projectName, tableName, condition);
                }else{
                    tmpResult = pcuDataDao.getAllServersData(projectName, tableName);
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
