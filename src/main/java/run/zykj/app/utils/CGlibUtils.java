package run.zykj.app.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lingSong
 * @date 2020/12/30 21:44
 */
@Slf4j
public class CGlibUtils {
    // 使用缓存提高效率
    private static final ConcurrentHashMap<String, BeanCopier> mapCaches = new ConcurrentHashMap<>();

    public static <O, T> T mapper(O source, Class<T> target){
        return baseMapper(source, target);
    }

    public static <O, T> T mapper(O source, Class<T> target, IAction<T> action) {
        T instance = baseMapper(source, target);
        action.run(instance);
        return instance;
    }

    public static <O, T> T mapperObject(O source, T target){
        String generateKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if(!mapCaches.containsKey(generateKey)){
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            mapCaches.put(generateKey, copier);
        }else{
            copier = mapCaches.get(generateKey);
        }
        copier.copy(source, target, null);
        return target;
    }

    public static <O, T> T mapperObject(O source, T target,  IAction<T> action){
        String baseKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier;
        if (!mapCaches.containsKey(baseKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            mapCaches.put(baseKey, copier);
        } else {
            copier = mapCaches.get(baseKey);
        }
        copier.copy(source, target, null);
        action.run(target);
        return target;
    }

    private static <O, T> T baseMapper(O source, Class<T> target){
        String baseKey = generateKey(source.getClass(), target);
        BeanCopier copier = null;
        if(!mapCaches.containsKey(baseKey)){
            copier = BeanCopier.create(source.getClass(), target, false);
            mapCaches.put(baseKey, copier);
        }else {
            copier = mapCaches.get(baseKey);
        }

        T instance = null;
        try {
            instance = target.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            log.error("[CGlibUtils]-----创建对象异常!");
            throw new RuntimeException("[CGlibUtils]-----创建对象异常!");
        }
        copier.copy(source, instance, null);
        return instance;
    }

    private static String generateKey(Class<?> class1, Class<?> class2){
        return class1.toString() + class2.toString();
    }
}
