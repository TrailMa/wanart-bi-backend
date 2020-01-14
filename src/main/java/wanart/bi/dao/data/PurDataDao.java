package wanart.bi.dao.data;

import org.springframework.stereotype.Repository;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.util.CommonDataCalcUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository

// 付费率 = 付费人数 / 活跃人数
public class PurDataDao {
    @Resource
    private CommonDataDao commonDataDao;

    public List<CommonDataEntity> getPurData(String projectName, String incomeTableName, String dauTableName, String distinct, String condition, String incomeTimeColumn, String dauTimeColumn){
        List<CommonDataEntity> incomeList = commonDataDao.getCommonData(projectName, incomeTableName, distinct, condition, "day", incomeTimeColumn) ;
        List<CommonDataEntity> dauList = commonDataDao.getCommonData(projectName, dauTableName, distinct, condition, "day", dauTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, dauList);
    }

    public List<CommonDataEntity> getPurDataByGroup(String projectName, String incomeTableName, String dauTableName, String distinct, String condition, String group, String incomeTimeColumn, String dauTimeColumn){
        List<CommonDataEntity> incomeList = commonDataDao.getCommonDataByGroup(projectName, incomeTableName, distinct, condition, "day", group, incomeTimeColumn) ;
        List<CommonDataEntity> dauList = commonDataDao.getCommonDataByGroup(projectName, dauTableName, distinct, condition, "day", group, dauTimeColumn);
        return CommonDataCalcUtil.CalcDivision(incomeList, dauList);
    }
}
