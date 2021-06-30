package com.common.aspects;

import com.common.exception.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author A jun
 * @ClassName com.com.member.member.common.aspects
 * @Description 统一返回结果切面
 * @Date 2018/4/9 20:45
 * @Version 1.0
 */
@Aspect
@Component
public class BaseResultAspect {

    /**
     * @Author A Jun
     * @Description 拦截所有controller
     * @DateTime 2018/4/9 20:54
     * @param
     * @return
     */
    @Around("execution(* com.*..controller.*.*(..))&&(!@annotation(com.common.annotation.OtherReturn))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取目标方法
        Method proxyMethod = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        Method targetMethod = proceedingJoinPoint.getTarget().getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        boolean isPublic = Modifier.isPublic(targetMethod.getModifiers());
        if(!isPublic){
            return proceedingJoinPoint.proceed();
        }
        // 获取返回信息
        Object result = proceedingJoinPoint.proceed();
        //如果返回值 带有ERROR:前缀 则认为是错误信息
        if(result instanceof String && ((String) result).startsWith("ERROR:")){
            String[] resultStr = ((String) result).split(":");
            return ResultUtil.error(-1,resultStr[1]);
        }else{
            return ResultUtil.success(result);
        }
    }
}
