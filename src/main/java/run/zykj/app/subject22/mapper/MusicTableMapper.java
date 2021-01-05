package run.zykj.app.subject22.mapper;

import org.springframework.stereotype.Repository;
import run.zykj.app.subject22.entity.MusicTable;

import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/5 16:31
 */
@Repository
public interface MusicTableMapper {

    /**
     * 查询排名为前Limit的音乐网站信息
     *
     * @param limit
     * @return
     */
    List<MusicTable> selectOnLimit(Integer limit);

}
