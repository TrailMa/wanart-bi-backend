package wanart.bi.entity.manage;

public class ProjectEntity {
    private int id;
    private String name;
    private String timeCreated;
    private String desc;

    public int getId(){
       return id;
    }
    public void setId(int id){
       this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getTimeCreated(){
        return timeCreated;
    }
    public void setTimeCreated(String timeCreated){
       this.timeCreated = timeCreated;
    }

    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }
}
