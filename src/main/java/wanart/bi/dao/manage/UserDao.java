package wanart.bi.dao.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import wanart.bi.component.DataSourceRouterComponent;
import wanart.bi.entity.manage.UserEntity;
import wanart.bi.util.TimeUtil;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UserDao {
    @Resource
    private DataSourceRouterComponent dataSourceRouterComponent;

    public UserEntity getUserByPassword(String name, String password){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("select id, `name`, `group`, projects, createdTime, lastLoginTime, curProjectId from `user` where `name`=\"%s\" and `password`=\"%s\"",
                name, password);
        List<UserEntity> userEntityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class));
        if(userEntityList.size() == 0){
            return null;
        }
        return userEntityList.get(0);
    }

    public UserEntity getUserById(int id){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("select id, `name`, `group`, projects, createdTime, lastLoginTime from `user` where id=%s", id);
        List<UserEntity> userEntityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class));
        if(userEntityList.size() == 0){
            return null;
        }
        return userEntityList.get(0);
    }
    public UserEntity getUserByName(String name){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("select id, `name`, `group`, projects, createdTime, lastLoginTime from `user` where `name`=\"%s\"", name);
        List<UserEntity> userEntityList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class));
        if(userEntityList.size() == 0){
            return null;
        }
        return userEntityList.get(0);
    }

    public void updateLastLoginTime(int id){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("update `user` set lastLoginTime=\"%s\" where id=%s", TimeUtil.getTimeNowString(), id);
        jdbcTemplate.execute(sql);
    }

    public List<UserEntity> getAllUsers(){
       JdbcTemplate jdbcTemplate = getJdbcTemplate();
       String sql = "select * from `user`";
       return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    public List<UserEntity> getUsersByProjectId(int projectId){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("select * from `user` where projects like '%s%s%s'", "%", projectId, "%");
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserEntity.class));
    }

    public void updateUserGroup(int userId, int group){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("update `user` set `group`=%s where id=%s", group, userId);
        jdbcTemplate.execute(sql);
    }

    public void deleteUserById(int userId){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("delete from `user` where id=%s", userId);
        jdbcTemplate.execute(sql);
    }

    public boolean createUser(String name, String password, int group){
        String curTime = TimeUtil.getTimeNowString();
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("INSERT INTO `user` (`name`,`group`,`createdTime`,`lastLoginTime`,`password`) VALUES (\"%s\", %s, \"%s\", \"%s\", \"%s\")",
                name, group, curTime, curTime, password);
        try{
            jdbcTemplate.execute(sql);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public void updateUserProjects(int userId, String projects){
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        String sql = String.format("update `user` set `projects`=\"%s\" where `id`=%s", projects, userId);
        jdbcTemplate.execute(sql);
    }

    private JdbcTemplate getJdbcTemplate(){
        return dataSourceRouterComponent.getJdbcTemplateByProjectName("manage");
    }
}
