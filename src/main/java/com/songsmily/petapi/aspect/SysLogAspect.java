package com.songsmily.petapi.aspect;

import com.alibaba.fastjson.JSON;

import com.songsmily.petapi.entity.AdminUser;
import com.songsmily.petapi.entity.Log;
import com.songsmily.petapi.service.LogService;
import com.songsmily.petapi.utils.ShiroUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
 
/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class SysLogAspect {
 
    @Resource
    private LogService logService;
 
    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.songsmily.petapi.aspect.MyLog)")
    public void logPoinCut() {

    }
 
    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        System.out.println("切面。。。。。");
        Log log =  new Log();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            log.setLogOperate(value);//保存获取的操作
        }
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        log.setLogMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        log.setLogParams(params);

        log.setLogGmtCreate(System.currentTimeMillis());
        //获取用户名
        log.setLogAdminId(ShiroUtil.getUser(new AdminUser()).getId());
        log.setLogUsername(ShiroUtil.getUser(new AdminUser()).getUsername());

        //获取用户ip地址
        log.setLogIp(request.getRemoteAddr());
        logService.save(log);
    }
 
}