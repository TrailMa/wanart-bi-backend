package wanart.bi.response;

import wanart.bi.common.ResponseResult;
import wanart.bi.entity.manage.ProjectEntity;

import javax.xml.ws.Response;
import java.util.List;

public class CommonResponse {
    private int result;
    private String msg;

    public int getResult(){
        return result;
    }
    public void setResult(int result){
        this.result = result;
    }

    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setSuccess(){
        result = ResponseResult.Success;
        msg = "success";
    }

    public void setCommonFailure(String msg){
       result = ResponseResult.Failed;
       this.msg = msg;
    }

}
