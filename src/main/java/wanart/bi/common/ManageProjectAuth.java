package wanart.bi.common;

import wanart.bi.entity.manage.UserEntity;

import static wanart.bi.common.ManageGroupEnum.SUPER_ADMIN;

public class ManageProjectAuth {
    public static boolean checkManageProjectAuth(UserEntity user, int projectId, ManageActionEnum action){
        switch (action){
            case QUERY:
                return checkQueryAuth(user, projectId);
            case CREATE:
                return checkCreateAuth(user);
            case UPDATE:
                return checkUpdateAuth(user, projectId);
            case DELETE:
                return checkDeleteAuth(user);
            default:
                return false;
        }
    }

    // 查询项目 权限检查
    private static boolean checkQueryAuth(UserEntity user, int projectId){
        ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
       switch (groupEnum){
           case SUPER_ADMIN:
               return true;
           case PROJECT_ADMIN:
           case ANALYST:
               return user.containsProject(projectId);
           default:
               return false;
       }
    }
    // 创建项目 权限检查
    private static boolean checkCreateAuth(UserEntity user){
        ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
        // 只有SUPER_ADMIN可以创建项目
        return groupEnum == SUPER_ADMIN;
    }
    // 更改项目 权限检查
    private static boolean checkUpdateAuth(UserEntity user, int projectId){
        ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
        switch (groupEnum){
            case SUPER_ADMIN:
                return true;
            case PROJECT_ADMIN:
                // 项目管理员可以 比如对项目增加成员 删除成员
                return user.containsProject(projectId);
            default:
                return false;
        }
    }
    // 删除项目
    private static boolean checkDeleteAuth(UserEntity user){
        ManageGroupEnum groupEnum = ManageGroupEnum.valueOf(user.getGroup());
        // 只有SUPER_ADMIN可以创建项目
        return groupEnum == SUPER_ADMIN;
    }
}
