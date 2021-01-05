package run.zykj.app.subject7.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author xiaolin
 * @date 2021/1/5 10:51
 */
@Repository
public interface ArticleMapper {

    /**
     * 根据id判断文章是否存在
     *
     * @param articleId
     * @return
     */
    int findArtcileIsNotNull(Long articleId);
}
