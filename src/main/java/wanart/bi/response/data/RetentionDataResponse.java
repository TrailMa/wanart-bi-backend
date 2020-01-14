package wanart.bi.response.data;

import wanart.bi.entity.data.RetentionRowDataEntity;
import wanart.bi.response.CommonResponse;

import java.util.ArrayList;
import java.util.List;

public class RetentionDataResponse extends CommonResponse {
    private ArrayList<RetentionRowDataEntity> dataList;

    public List<RetentionRowDataEntity> getDataList(){
        return dataList;
    }
    public void setDataList(ArrayList<RetentionRowDataEntity> dataList){
        this.dataList = dataList;
    }

    public RetentionDataResponse(){
        dataList = new ArrayList<>();
    }
    public void addRetentionDataEntity(RetentionRowDataEntity entity){
        dataList.add(entity);
    }
}
