package wanart.bi.common;

import wanart.bi.entity.manage.UserEntity;

public class ManageUserAuth {
    public static boolean checkManageUserAuth(UserEntity user, ManageActionEnum action){
        switch (action){
            case QUERY:
                return checkQueryAuth(user);
            case UPDATE:
                return checkUpdateAuth(user);
            case CREATE:
                return checkCreateAuth(user);
            case DELETE:
                return checkDeleteAuth(user);
            default:
                return false;
        }
    }

    // 查询权限 超管和管理员可以查询用户列表
    private static boolean checkQueryAuth(UserEntity user){
     ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
       switch (groupEnum){
           case SUPER_ADMIN:
           case PROJECT_ADMIN:
               return true;
           default:
               return false;
       }
    }

    private static boolean checkCreateAuth(UserEntity user){
       ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
       switch (groupEnum){
           case SUPER_ADMIN:
               return true;
           default:
               return false;
       }
    }

    // 更改权限 只有超管可以修改其他用户的管理组数据
    private static boolean checkUpdateAuth(UserEntity user){
      ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
       switch (groupEnum){
           case SUPER_ADMIN:
               return true;
           default:
               return false;
       }
    }

    // 删除权限 只有超管可以删除
    private static boolean checkDeleteAuth(UserEntity user){
       ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
       switch (groupEnum){
           case SUPER_ADMIN:
               return true;
           default:
               return false;
       }
    }
}
