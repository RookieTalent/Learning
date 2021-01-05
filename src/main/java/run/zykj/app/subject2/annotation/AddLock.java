package run.zykj.app.subject2.annotation;

import java.lang.annotation.*;

/**
 * @author xiaolin
 * @date 2021/1/5 9:40
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AddLock {
    // spel表达式
    String spel();

    //失效时间 单位秒
    int expireTime() default 10;

    // log信息
    String logInfo() default "";
}
