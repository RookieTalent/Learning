package run.zykj.app.subject2.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import run.zykj.app.subject2.annotation.AddLock;
import run.zykj.app.subject2.service.AddLockService;
import run.zykj.app.utils.SeplUtil;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author xiaolin
 * @date 2021/1/5 9:49
 */
@Slf4j
@Aspect
@Component
public class AddLockAspect {

    @Autowired
    private AddLockService addLockService;

    @Pointcut("@annotation(run.zykj.app.subject2.annotation.AddLock)")
    public void aroundPoint(){

    }

    @Around(value = "aroundPoint()")
    public Object addKeyMethod(ProceedingJoinPoint point) throws Throwable{
        // 定义返回值
        Object proceed;
        // 获取方法名称
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        AddLock annotation = AnnotationUtils.findAnnotation(method, AddLock.class);
        String logInfo = getLogInfo(point);
        // 前置方法
        String redisKey = getRedisKey(point);
        log.info("{}获取锁={}", logInfo, redisKey);
        // 获取请求ID 保证加锁者和请求者是同一个
        String requestId = UUID.randomUUID().toString();
        boolean lockReleased = false;
        try {
            boolean lock = addLockService.lock(requestId, redisKey, annotation.expireTime());
            if (!lock ) {
                throw new RuntimeException(logInfo+":加锁失败");
            }
            // 目标方法执行
            proceed = point.proceed();
            boolean releaseLock = addLockService.releaseLock(redisKey, requestId);
            lockReleased = true;
            if(releaseLock){
                throw new RuntimeException(logInfo+":释放锁失败");
            }
            return proceed;
        }catch (Exception e){
            log.error("{}执行异常,key = {},message={}, exception = {}", logInfo,redisKey, e.getMessage(), e);
            throw new RuntimeException("执行异常", e);
        }finally {
            if(!lockReleased){
                log.info("{}异常终止释放锁={}",logInfo,redisKey);
                boolean releaseLock = addLockService.releaseLock(redisKey, requestId);
                log.info("releaseLock="+releaseLock);
            }
        }
    }

    /**
     * 获取到指定的Log info
     * @param point
     * @return
     */
    private String getLogInfo(ProceedingJoinPoint point){
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        AddLock annotation = AnnotationUtils.findAnnotation(method, AddLock.class);
        if(annotation == null){
            return methodSignature.getName();
        }
        return annotation.logInfo();
    }

    /**
     * 获取拦截到的请求方法
     * @param point
     * @return
     */
    private String getRedisKey(ProceedingJoinPoint point){
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Object target = point.getTarget();
        Object[] args = point.getArgs();
        AddLock annotation = AnnotationUtils.findAnnotation(method, AddLock.class);
        String spel = null;
        if(annotation != null){
            spel = annotation.spel();
        }

        return SeplUtil.parse(target, spel, method, args);
    }
}

