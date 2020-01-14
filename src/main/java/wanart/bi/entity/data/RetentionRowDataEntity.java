package wanart.bi.entity.data;

import java.util.List;

public class RetentionRowDataEntity {
    private String createdTime;
    private List<RetentionDataEntity> dataList;

    public String getCreatedTime(){
        return createdTime;
    }
    public void setCreatedTime(String createdTime){
        this.createdTime = createdTime;
    }

    public List<RetentionDataEntity> getDataList(){
        return dataList;
    }
    public void setDataList(List<RetentionDataEntity> dataList){
        this.dataList = dataList;
    }
    public void addDataList(List<RetentionDataEntity> dataList){
        if(this.dataList == null ){
            this.dataList = dataList;
            return;
        }
        this.dataList.addAll(dataList);
    }
}
