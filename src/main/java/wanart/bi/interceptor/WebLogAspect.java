package wanart.bi.interceptor;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;



@Aspect
@Component
public class WebLogAspect {
    @Pointcut("execution(public * wanart.bi.controller..*.*(..))")
    public void controllerLog(){}
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Order(1)
    @Before("controllerLog()")
    public void logBeforeController(JoinPoint joinPoint){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        Object[] args = joinPoint.getArgs();
        String params = "";
        //logger.info("request url %s, method %s", uri, method);
        if(args.length > 0){
            if("POST".equals(method)){
                // 约定 第一个参数为POST请求的body内容
                params = JSON.toJSONString(args[0]);
            }else{
                params = request.getQueryString();
            }
        }
        logger.info("request uri {}, params {}", uri, params);
    }

    //private Map<String, Object>
}
