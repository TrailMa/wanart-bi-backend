package wanart.bi.dao.funnel;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.util.SqlParamUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class FunnelDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    // 筛选 符合漏斗条件的uid集合
    public List<Integer> getHeadFunnelUids(String projectName, String tableName, String condition){
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        String sql = String.format("select distinct(uid) from %s %s", tableName, condition);
        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    public List<Integer> getTailFunnelUids(String projectName, String tableName, String condition, List<Integer> ids){
        if(ids == null || ids.size() == 0){
            return new ArrayList<>();
        }
        JdbcTemplate jdbcTemplate = dataSourceRouterComponent.getJdbcTemplateByProjectName(projectName);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        HashMap<String, Object> params = new HashMap<>();
        params.put(SqlParamUtil.getInUidsStr(), ids);
        String sql = String.format("select distinct(uid) from %s %s", tableName, condition);
        return namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);
    }

}
