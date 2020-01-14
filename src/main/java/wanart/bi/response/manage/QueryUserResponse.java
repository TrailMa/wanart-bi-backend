package wanart.bi.response.manage;

import wanart.bi.entity.manage.UserEntity;
import wanart.bi.response.CommonResponse;

import java.util.ArrayList;
import java.util.List;

public class QueryUserResponse extends CommonResponse {
    private List<UserEntity> userList;

    public List<UserEntity> getUserList(){
        return userList;
    }
    public void setUserList(List<UserEntity> copyList){
       this.userList = new ArrayList<>();
       for(UserEntity user : copyList){
          userList.add(user.copy());
       }
    }
}
