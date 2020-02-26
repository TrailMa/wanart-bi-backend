package wanart.bi.dao.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.data.RetentionDataEntity;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class RetentionDataDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    public List<RetentionDataEntity> getRetentionData(String projectName, String createdTableName, String loginTableName, String distinct, String condition){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String separationTime = SqlParamUtil.parseSeparationTime(loginTableName, "day", "");
        condition = SqlParamUtil.parseCondition(condition);
        condition = condition.replace("serverid", "a.serverid");
        condition = condition.replace("channel", "a.channel");
        String sql = String.format("SELECT count(DISTINCT(a.%s)) as firstCount, count(DISTINCT(b.%s)) as secondCount, 0 as groupId, %s as time from %s as a left JOIN %s as b on a.%s = b.%s %s",
                distinct, distinct, separationTime, createdTableName, loginTableName, distinct, distinct, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RetentionDataEntity.class));
    }

    public List<RetentionDataEntity> getRetentionDataByGroup(String projectName, String createdTableName, String loginTableName, String distinct, String condition, String group){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String separationTime = SqlParamUtil.parseSeparationTime(loginTableName, "day", "");
        condition = SqlParamUtil.parseCondition(condition);
        condition = condition.replace("serverid", "a.serverid");
        condition = condition.replace("channel", "a.channel");

        group = group.trim().toLowerCase();
        group = group.replace("serverid", "a.serverid");
        group = group.replace("channel", "a.serverid");

        String sql = String.format("SELECT count(DISTINCT(a.%s)) as firstCount, count(DISTINCT(b.%s)) as secondCount, %s as groupId, %s as time from %s as a left JOIN %s as b on a.%s = b.%s %s group by groupId",
                distinct, distinct, group, separationTime, createdTableName, loginTableName, distinct, distinct, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RetentionDataEntity.class));
    }
}
