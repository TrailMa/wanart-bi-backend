package wanart.bi.dao.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.manage.ProjectEntity;
import wanart.bi.util.SqlParamUtil;
import wanart.bi.util.TimeUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProjectDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    public List<ProjectEntity> getProjectsByIds(ArrayList<Integer> ids){
        if(ids == null || ids.size() == 0){
            return null;
        }
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        HashMap<String, Object> params = new HashMap<>();
        String sql = "select * from project where `id` in (:ids)";
        params.put("ids", ids);
        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(ProjectEntity.class));
    }

    public List<ProjectEntity> getAllProjects(){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = "select * from project";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectEntity.class));
    }

    public ProjectEntity getProjectById(int id){
       JdbcTemplate jdbcTemplate = getJdbcTemplate();
       String sql = String.format("select * from project where `id`= %s", id);
       List<ProjectEntity> projectList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectEntity.class));
       if(projectList.size() == 0){
           return  null;
       }
       return projectList.get(0);
    }
    public ProjectEntity getProjectByName(String name){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("select * from project where `name`=\"%s\"", name);
        List<ProjectEntity> projectList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProjectEntity.class));
        if(projectList.size() == 0){
            return null;
        }
        return projectList.get(0);
    }

    public void deleteProjectById(int id){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("delete from project where `id`=%s", id);
        jdbcTemplate.execute(sql);
    }

    public boolean createProject(String name, String desc){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String curTime = TimeUtil.getTimeNowString();
        String sql = String.format("insert into project (`name`, timeCreated, `desc`) VALUES (\"%s\", \"%s\", \"%s\")",
                name, curTime, desc);
        try{
            jdbcTemplate.execute(sql);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private JdbcTemplate getJdbcTemplate(){
        return dataSourceRouterComponent.getJdbcTemplateByProjectName("manage");
    }
}
