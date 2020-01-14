package wanart.bi.dao.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.entity.event.EventColumnEntity;
import wanart.bi.entity.event.EventEntity;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class EventDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    public List<String> getEventNames(String projectName){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String sql = "select `eventName` from event_all";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<EventColumnEntity> getEventDetailByName(String projectName, String eventName) {
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventName);
        String tableName = "event_" + eventName;
        String sql = String.format("select COLUMN_NAME as columnName, DATA_TYPE as columnType from information_schema.`COLUMNS` where table_name='%s' and table_schema='%s'",
                tableName, projectName);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(EventColumnEntity.class));
    }

    // 参数举例
    // distinct = "accountId"
    // condition = "where serverId = '4001' or channel = '1'"
    // separationTime = "DATE_FORMAT(timeColumn ,"%Y-%M-%d %H:00:00") as time;"
    // group = "group by serverId"
    public List<CommonDataEntity> getData(String projectName, String tableName, String distinct, String condition, String separationTime){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        // 如果condition为空 则通过常量+命中索引 规避全表扫描
        String sql = String.format("select count(DISTINCT %s) as value, 0 as groupId, %s from %s %s group by time order by time",
                distinct, separationTime, tableName, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }

    public List<CommonDataEntity> getDataByGroup(String projectName, String tableName, String distinct, String condition, String separationTime, String groupColumn, String byGroup){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String sql = String.format("select count(DISTINCT %s) as value, %s as groupId, %s from %s %s %s, time order by groupId, time",
                distinct, groupColumn, separationTime, tableName, condition, byGroup);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }

}
