package run.zykj.app.subject7.service;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import run.zykj.app.subject7.constants.Constants;
import run.zykj.app.subject7.mapper.ArticleMapper;

/**
 * @author xiaolin
 * @date 2021/1/5 10:59
 */
@Slf4j
@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 点赞文章
     * @param articleId
     * @param userId
     */
    public void likeArticle(Long articleId, Long userId){
        // 校验参数
        Assert.notNull(articleId, "article Id must not be null");
        Assert.notNull(userId, "user Id must not be null");

        // 点赞的用户不能点赞
        validateLike(articleId, userId);
        // 点赞
        redisTemplate.opsForSet().add(Constants.LIKE_BOLG + articleId, userId);
    }

    /**
     * 取消点赞
     * @param articleId
     * @param userId
     */
    public void unlikeArticle(Long articleId, Long userId){
        // 校验参数
        Assert.notNull(articleId, "article Id must not be null");
        Assert.notNull(userId, "user Id must not be null");

        // TODO 是否点赞 懒得
        // 取消点赞
        redisTemplate.opsForSet().remove(Constants.LIKE_BOLG + articleId, userId);
    }

    /**
     * 统计点赞人数
     *
     * @param articleId
     */
    public int countLikeNumber(Long articleId){
        return redisTemplate.opsForSet().members(Constants.LIKE_BOLG+articleId).size();
    }

    /**
     * 校验参数
     *
     * @param articleId
     * @param userId
     */
    private void validateParam(Long articleId, Long userId){
       // 因为我没有写用户的就算了
        int count = articleMapper.findArtcileIsNotNull(articleId);
        if(count < 1){
            log.error("点赞的文章{}不存在!", articleId);
            throw new RuntimeException("点赞的文章不存在!");
        }
    }

    /**
     * 校验点赞
     *
     * @param articleId
     * @param userId
     */
    private void validateLike(Long articleId, Long userId){
        // 如果成员元素是集合的成员，返回 1 。 如果成员元素不是集合的成员，或 key 不存在，返回 0
        Boolean member = redisTemplate.opsForSet().isMember(Constants.LIKE_BOLG + articleId, userId);
        if(member){
            throw new RuntimeException("您已经点赞，请不要重复点赞!");
        }
    }


}
