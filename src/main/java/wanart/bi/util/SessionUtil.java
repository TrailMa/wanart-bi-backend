package wanart.bi.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static int getUserId(){
        int userId = 0;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(null != requestAttributes) {
            HttpServletResponse response = requestAttributes.getResponse();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession();
            Object userIdObj = session.getAttribute("userId");
            if(userIdObj == null){
                return  userId;
            }
            userId = Integer.parseInt(userIdObj.toString());
        }
        return userId;
    }
}
