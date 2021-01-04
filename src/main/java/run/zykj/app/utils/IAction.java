package run.zykj.app.utils;

/**
 * @author lingSong
 * @date 2020/12/30 21:55
 */
@FunctionalInterface
public interface IAction<T> {
    void run(T param);
}
