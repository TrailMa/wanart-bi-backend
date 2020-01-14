package wanart.bi.entity.data;

public class CommonDataEntity {
    private Integer groupId;
    private Integer value;
    private String time;

    public Integer getGroupId(){
        return groupId;
    }
    public void setGroupId(Integer serverId){
        this.groupId = serverId;
    }

    public Integer getValue(){
        return value;
    }
    public void setValue(Integer value){
       this.value = value;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
}
