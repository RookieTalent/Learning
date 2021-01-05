package run.zykj.app.utils;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @author xiaolin
 * @date 2021/1/5 9:42
 */
public class SeplUtil {

    public static String parse(String spel, Method method, Object[] args){
        // 获取被拦截方法参数名列表 使用spring支持类库
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        // 使用SPEL进行key的解析
        SpelExpressionParser parser = new SpelExpressionParser();
        // SEPL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放到SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++){
            context.setVariable(paraNameArr[i], args[i]);
        }

        return parser.parseExpression(spel).getValue(context,String.class);
    }

    /**
     * 支持 #p0 参数索引的表达式解析
     *
     * @param rootObject
     * @param spel
     * @param method
     * @param args
     * @return
     */
    public static String parse(Object rootObject, String spel, Method method, Object[] args) {
        //获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new MethodBasedEvaluationContext(rootObject,method,args,u);
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(spel).getValue(context, String.class);
    }
}
