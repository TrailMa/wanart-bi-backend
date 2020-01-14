package wanart.bi.service.manage;

import wanart.bi.response.CommonResponse;
import wanart.bi.response.manage.QueryUserResponse;

public interface ManageUserService {
    QueryUserResponse query(int userId);
    QueryUserResponse queryByProject(int userId, int projectId);
    CommonResponse update(int userId, int targetUserId, int group);
    CommonResponse delete(int userId, int targetUserId);
    CommonResponse create(int userId, String name, String password, int group);
}
