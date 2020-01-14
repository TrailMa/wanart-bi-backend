package wanart.bi.common;

public enum ManageGroupEnum {
    // 超管
    SUPER_ADMIN,

    // 项目管理员
    PROJECT_ADMIN,

    // 分析师
    ANALYST,

    // 游客 无任何权限
    GUEST;

    public static ManageGroupEnum valueOf(int group){
        switch (group)     {
            case 1:
               return SUPER_ADMIN;
            case 2:
                return PROJECT_ADMIN;
            case 3:
                return ANALYST;
            default:
                return GUEST;
        }
    }
}
