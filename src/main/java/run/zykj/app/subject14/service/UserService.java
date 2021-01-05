package run.zykj.app.subject14.service;

import com.sun.istack.NotNull;
import run.zykj.app.subject14.entity.dto.RegisterInfo;

/**
 * @author lingSong
 * @date 2021/1/5 20:54
 */
public interface UserService {

    /**
     * 简单模拟注册
     *
     * @param info
     */
    void register(@NotNull RegisterInfo info);
}
