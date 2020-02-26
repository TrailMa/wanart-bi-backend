package wanart.bi.entity.data;

public class RetentionDataEntity {
    private int firstCount;
    private int secondCount;
    private int groupId;
    private String time;

    public int getFirstCount(){
        return firstCount;
    }
    public void setFirstCount(int firstCount){
        this.firstCount = firstCount;
    }

    public int getSecondCount(){
        return secondCount;
    }
    public void setSecondCount(int secondCount){
        this.secondCount = secondCount;
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
