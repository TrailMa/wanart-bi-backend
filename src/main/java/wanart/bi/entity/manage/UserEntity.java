package wanart.bi.entity.manage;

import org.springframework.util.StringUtils;
import wanart.bi.common.ManageGroupEnum;

import java.util.ArrayList;

public class UserEntity {
    private int id;
    private String name;
    private int group;
    private ArrayList<Integer> projects;
    private String createdTime;
    private String lastLoginTime;
    private String password;

    public int getId(){
        return id;
    }
    public void setId(int id){
       this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getGroup(){
        return group;
    }
    public void setGroup(int group){
        this.group = group;
    }

    public ManageGroupEnum getGroupEnum(){
        return ManageGroupEnum.valueOf(group);
    }
    public boolean isSuperAdmin(){
        return getGroupEnum() == ManageGroupEnum.SUPER_ADMIN;
    }

    public ArrayList<Integer> getProjests(){
        return projects;
    }
    public void setProjects(String projectsStr){
        projects = new ArrayList<>();
        if(StringUtils.isEmpty(projectsStr)){
            return;
        }
        String[] projectsStrArr = projectsStr.split(":");
        for(String str : projectsStrArr){
            projects.add(Integer.parseInt(str));
        }
    }

    public boolean containsProject(Integer projectId){
        return projects != null && projects.contains(projectId);
    }
    public void addProject(Integer projectId){
        if(projects == null){
            projects = new ArrayList<>();
        }
        if(projects.contains(projectId)){
            return;
        }
        projects.add(projectId);
    }
    public void deleteProject(Integer projectId){
        if(projects == null){
            return;
        }
        projects.remove(projectId);
    }

    public String projectsToString(){
        if(projects == null || projects.size() == 0){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for(Integer id : projects){
            sb.append(id.toString());
            sb.append(":");
        }
        String projectStr = sb.toString();
        projectStr = projectStr.substring(0, projectStr.length() - 1);
        return projectStr;
    }

    public String getCreatedTime(){
        return createdTime;
    }
    public void setCreatedTime(String createdTime){
        this.createdTime = createdTime;
    }

    public String getLastLoginTime(){
        return lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime){
        this.lastLoginTime = lastLoginTime;
    }

    public String getPassword(){
        return  password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public UserEntity copy(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(getId());
        userEntity.setName(getName());
        userEntity.setGroup(getGroup());
        userEntity.setCreatedTime(getCreatedTime());
        userEntity.setLastLoginTime(getLastLoginTime());
        userEntity.setPassword("");
        // 不要用setProject 会导致jdbcTemplate优先使用setProjects(ArrayList<>), 导致String无法转换的报错
        userEntity.projects = getProjests();
        return userEntity;
    }
}
