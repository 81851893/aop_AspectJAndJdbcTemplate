package com.atguigu.aopAspectJ;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component  //标识当前类是一个普通组件
@Aspect     //标识当前类是一个切面类
public class Mylogging {

    /**
     * 定义可重用的切入点表达式
     */
    @Pointcut(value = "execution(* com.atguigu.aopAspectJ.Calc.*(..))")
    public void myPointCut(){}

    /**
     * 执行方法前代码 [前置通知:在 [目标方法] 执行之前执行当前方法 ]
     */
    @Before(value = "myPointCut()")
    public void methodBefore(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("==>执行方法前" + methodName + "方法,参数:" + Arrays.toString(args));
    }

    /**
     * 执行方法后代码  后置通知
     * @param joinPoint
     */
//    @After(value = "execution(* com.atguigu.aopAspectJ.Calc.*(..))")
    public void methodAfter(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        System.out.println("==>执行方法后" + methodName + "方法,参数:" + Arrays.toString(args));
    }

    /**
     * 返回通知
     * @param joinPoint
     * @param rs
     */
//    @AfterReturning(value = "execution(* com.atguigu.aopAspectJ.Calc.*(..))",returning = "rs")
//    public void afterReturning(JoinPoint joinPoint,Object rs){
//        String methodName = joinPoint.getSignature().getName();
//        System.out.println("返回通知,执行:" + methodName + "方法,结果:" + rs);
//    }

//    @AfterReturning(value = "myPointCut()",returning = "rs")
    public void afterReturning(JoinPoint joinPoint , Object rs){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("==>返回通知!执行:" + methodName + "方法,结果为:" + rs);
    }
    /**
     * 异常通知
     * @param joinPoint
     * @param throwable
     */
//    @AfterThrowing(value = "execution(* com.atguigu.aopAspectJ.Calc.*(..))",throwing = "throwable")
//    public void afterThrowing(JoinPoint joinPoint,Exception throwable){
//        String methodName = joinPoint.getSignature().getName();
//        System.out.println("返回通知!执行:" + methodName + "方法,发生异常:" + throwable);
//    }


    /**
     * 复习 异常通知
     */
//    @AfterThrowing(value = "myPointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("返回通知!执行:" + methodName + "方法,发生异常:" + ex);
    }



    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * @return
     */
    @Around(value ="execution(* com.atguigu.aopAspectJ.Calc.*(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){
        Object rs = null;
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        try {
//            前置通知
            System.out.println("==>执行方法前" + methodName + "方法,参数:" + Arrays.toString(args));
//            触发目标对象的相应方法[加减乘除方法]
            rs = proceedingJoinPoint.proceed();
//            返回通知
            System.out.println("返回通知,执行:" + methodName + "方法,结果:" + rs);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
//            异常通知
            System.out.println("返回通知!执行:" + methodName + "方法,发生异常:" + throwable);
        } finally {
//            后置通知
            System.out.println("==>执行方法后" + methodName + "方法,参数:" + Arrays.toString(args));
        }
        return rs;
    }
}
