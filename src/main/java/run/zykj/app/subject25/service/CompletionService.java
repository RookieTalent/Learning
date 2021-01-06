package run.zykj.app.subject25.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import run.zykj.app.subject25.utils.StbStringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author xiaolin
 * @date 2021/1/6 16:23
 */
@Slf4j
@Service
public class CompletionService {

    private final static String COMPLETION = "completion";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init(){
        // TODO 先从数据库中取出数据库录入redis
        // 这只是处理单条数据就要一个list？ 频繁GC可能会导致工作线程停止
        // 算了 反正不是我现在要关心的
        String mockData = "nba";
        List<String> list = StbStringUtils.subStringList(mockData);
        for (String s : list) {
            redisTemplate.opsForZSet().add(COMPLETION, s, 0);
        }
    }

    public Set<Object> search(String word){
        // 起始位置
        Long start = redisTemplate.opsForZSet().rank(COMPLETION, word);
        return redisTemplate.opsForZSet().range(COMPLETION, start, -1);
    }


}
