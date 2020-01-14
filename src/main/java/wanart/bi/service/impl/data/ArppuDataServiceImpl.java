package wanart.bi.service.impl.data;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wanart.bi.common.ResponseResult;
import wanart.bi.dao.data.ArppuDataDao;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.response.data.CommonDataResponse;
import wanart.bi.service.data.ArppuDataService;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArppuDataServiceImpl implements ArppuDataService {
    @Resource
    private ArppuDataDao arppuDataDao;

    public CommonDataResponse getData(String projectName, String distinct, String condition, String group, String startTime, String endTime){
        CommonDataResponse response = new CommonDataResponse();
        String incomeTimeColumn = "DateTime";
        boolean byGroup = !StringUtils.isEmpty(group);
        ArrayList<String> timeSpan = SqlParamUtil.parseTimeSpan(startTime, endTime);
        List<CommonDataEntity> resultList = null;
        for(String timeSuffix: timeSpan){
            try {
                String incomeTableName = "recharge_" + timeSuffix;
                List<CommonDataEntity> tmpResult;
                if(byGroup){
                    tmpResult = arppuDataDao.getArppuDataByGroup(projectName, incomeTableName, distinct, condition, group, incomeTimeColumn);
                }else{
                    tmpResult = arppuDataDao.getArppuData(projectName, incomeTableName, distinct, condition, incomeTimeColumn);
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
        return  response;
    }

}
