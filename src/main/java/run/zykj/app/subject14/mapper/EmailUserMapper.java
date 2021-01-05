package run.zykj.app.subject14.mapper;

import org.springframework.stereotype.Repository;
import run.zykj.app.subject14.entity.EmailUser;

/**
 * @author xiaolin
 * @date 2021/1/5 15:05
 */
@Repository
public interface EmailUserMapper {

    /**
     * 检测用户名在数据库中的唯一性
     *
     * @param name
     * @return
     */
    Integer checkNameUnique(String name);

    /**
     * 检测邮箱在数据库中的唯一性
     *
     * @param email
     * @return
     */
    Integer checkEmailUnique(String email);

    /**
     * 新增一条数据
     *
     * @param user
     */
    void insert(EmailUser user);
}
