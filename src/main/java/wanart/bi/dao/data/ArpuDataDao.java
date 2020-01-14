package wanart.bi.dao.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.util.CommonDataCalcUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ArpuDataDao {
    @Resource
    private IncomeDataDao incomeDataDao;
    @Resource
    private CommonDataDao commonDataDao;
    public List<CommonDataEntity> getArpuData(String projectName, String incomeTableName, String dauTableName, String distinct, String condition, String incomeTimeColumn, String dauTimeColumn){
        List<CommonDataEntity> incomeList = incomeDataDao.getIncomeData(projectName, incomeTableName, condition, "day", incomeTimeColumn) ;
        List<CommonDataEntity> dauList = commonDataDao.getCommonData(projectName, dauTableName, distinct, condition, "day", dauTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, dauList);
    }

    public List<CommonDataEntity> getArpuDataByGroup(String projectName, String incomeTableName, String dauTableName, String distinct, String condition, String group, String incomeTimeColumn, String dauTimeColumn){
        List<CommonDataEntity> incomeList = incomeDataDao.getInComeDataByGroup(projectName, incomeTableName, condition, "day", group, incomeTimeColumn) ;
        List<CommonDataEntity> dauList = commonDataDao.getCommonDataByGroup(projectName, dauTableName, distinct, condition, "day", group, dauTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, dauList);
    }

}
