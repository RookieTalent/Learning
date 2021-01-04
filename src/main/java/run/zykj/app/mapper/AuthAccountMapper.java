package run.zykj.app.mapper;

import org.springframework.stereotype.Repository;
import run.zykj.app.entity.pojo.AuthAccount;

import java.util.List;

/**
 * @author xiaolin
 * @date 2020/12/30 16:30
 */
@Repository
public interface AuthAccountMapper {

    List<AuthAccount> all();
}
