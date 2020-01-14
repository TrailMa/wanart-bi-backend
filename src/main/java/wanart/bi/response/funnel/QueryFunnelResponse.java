package wanart.bi.response.funnel;

import wanart.bi.entity.funnel.FunnelResultEntity;
import wanart.bi.response.CommonResponse;

import java.util.ArrayList;
import java.util.List;

public class QueryFunnelResponse extends CommonResponse {
    private List<FunnelResultEntity> funnelResultList;

    public void setFunnelResultList(List<FunnelResultEntity> funnelResultList) {
        this.funnelResultList = funnelResultList;
    }

    public List<FunnelResultEntity> getFunnelResultList() {
        return funnelResultList;
    }

    public void addFunnelResult(FunnelResultEntity result){
        if(result == null){
            return;
        }
        if(funnelResultList == null){
            funnelResultList = new ArrayList<>();
        }
       funnelResultList.add(result);
    }
}
