package wanart.bi.dao.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class IncomeDataDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    public List<CommonDataEntity> getIncomeData(String projectName, String tableName, String condition, String separationTime, String timeColumn){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        separationTime = SqlParamUtil.parseSeparationTime(tableName, separationTime, timeColumn);
        condition = SqlParamUtil.parseCondition(condition);
        String sql = String.format("select sum(money) as value, 0 as groupId, %s as time from %s %s group by time order by time",
                separationTime, tableName, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }

    public List<CommonDataEntity> getInComeDataByGroup(String projectName, String tableName, String condition, String separationTime, String group, String timeColumn){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        separationTime = SqlParamUtil.parseSeparationTime(tableName, separationTime, timeColumn);
        condition = SqlParamUtil.parseCondition(condition);
        String sql = String.format("select sum(money) as value, %s as groupId, %s as time from %s %s group by groupId, time order by groupId, time",
                group, separationTime, tableName, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }
}
