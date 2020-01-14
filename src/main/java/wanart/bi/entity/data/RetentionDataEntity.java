package wanart.bi.entity.data;

public class RetentionDataEntity {
    private int createdCount;
    private int loginCount;
    private int groupId;
    private String time;

    public int getCreatedCount(){
        return createdCount;
    }
    public void setCreatedCount(int createdCount){
        this.createdCount = createdCount;
    }

    public int getLoginCount(){
        return loginCount;
    }
    public void setLoginCount(int loginCount){
        this.loginCount = loginCount;
    }

    public int getGroupId(){
        return groupId;
    }
    public void setGroupId(int groupId){
        this.groupId = groupId;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
       this.time = time;
    }
}
