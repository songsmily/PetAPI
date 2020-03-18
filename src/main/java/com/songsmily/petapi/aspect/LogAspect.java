package com.songsmily.petapi.aspect;
import com.alibaba.fastjson.JSON;
import com.songsmily.petapi.dto.Result;
import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.entity.Log;
import com.songsmily.petapi.service.LogService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 切面 打印请求、返回参数信息
 */
@Aspect // 定义一个切面
@Configuration
public class LogAspect {
    @Resource
    LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* com.songsmily.petapi.controller.Admin*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String paraString = JSON.toJSONString(request.getParameterMap());
//        logger.info("***************************************************");
//        logger.info("请求开始, URI: {}, method: {}, params: {}", uri, method, paraString);

        Log log = new Log();
        log.setLogIp(request.getRemoteAddr());
        log.setLogUrl(uri);
        log.setLogMethod(method);
        log.setLogGmtCreate(System.currentTimeMillis());
        log.setLogParams(paraString);
        // result的值就是被拦截方法的返回值
        Result result = (Result) pjp.proceed();
        log.setLogResult(JSON.toJSONString(result));
        if (result.getCode() == 100){
            log.setLogStatus(1);
        }else{
            log.setLogStatus(0);
        }
        if (uri.equals("/login/adminLogin") && result.getCode() != 100){
            log.setLogAdminId(-1);
            log.setLogAdminName("登录失败，信息填写错误！");
        }else{
            AdminUser adminuser = ShiroUtil.getUser(new AdminUser());
            log.setLogAdminId(adminuser.getId());
            log.setLogAdminName(adminuser.getRealName());
        }
        logService.save(log);
        return result;
    }
}
