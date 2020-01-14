package wanart.bi.dao.data;

import org.springframework.stereotype.Repository;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.util.CommonDataCalcUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ArppuDataDao {

    @Resource
    private IncomeDataDao incomeDataDao;
    @Resource
    private CommonDataDao commonDataDao;

    public List<CommonDataEntity> getArppuData(String projectName, String incomeTableName, String distinct, String condition, String incomeTimeColumn) {
        List<CommonDataEntity> incomeList = incomeDataDao.getIncomeData(projectName, incomeTableName, condition, "day",  incomeTimeColumn);
        List<CommonDataEntity> rechargeDauList = commonDataDao.getCommonData(projectName, incomeTableName, distinct, condition, "day",  incomeTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, rechargeDauList);
    }

    public List<CommonDataEntity> getArppuDataByGroup(String projectName, String incomeTableName, String distinct, String condition, String group, String incomeTimeColumn) {
        List<CommonDataEntity> incomeList = incomeDataDao.getInComeDataByGroup(projectName, incomeTableName, condition, "day", group, incomeTimeColumn);
        List<CommonDataEntity> rechargeDauList = commonDataDao.getCommonDataByGroup(projectName, incomeTableName, distinct, condition, "day", group, incomeTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, rechargeDauList);
    }
}
