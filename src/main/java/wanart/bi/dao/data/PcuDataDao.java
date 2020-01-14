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
public class PcuDataDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    // 查询所有server的总计在线情
    public List<CommonDataEntity> getAllServersData(String projectName, String tableName){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String sql = String.format("SELECT sum(OnlineCount) as value , 0 as groupId, CurDate as time FROM %s group by time order by time",
                 tableName);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }

    // 查询某些ServerId的PCU
    public List<CommonDataEntity> getDataByCondition(String projectName, String tableName, String condition){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        condition = SqlParamUtil.parseCondition(condition);
        String sql = String.format( "SELECT OnlineCount as value, ServerID as groupId, CurDate as time FROM %s %s order by time",
                tableName, condition);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CommonDataEntity.class));
    }

}