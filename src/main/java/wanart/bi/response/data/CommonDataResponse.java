package wanart.bi.response.data;

import wanart.bi.entity.data.CommonDataEntity;
import wanart.bi.response.CommonResponse;

import java.util.List;

public class CommonDataResponse extends CommonResponse {
   private List<CommonDataEntity> dataList;

   public List<CommonDataEntity> getDataList(){
      return  dataList;
   }
   public void setDataList(List<CommonDataEntity> dataList){
      this.dataList = dataList;
   }
}
