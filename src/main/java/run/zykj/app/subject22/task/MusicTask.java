package run.zykj.app.subject22.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import run.zykj.app.subject22.entity.MusicTable;
import run.zykj.app.subject22.mapper.MusicTableMapper;

import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/5 16:44
 */
@Component
public class MusicTask {

    private final static String MUSIC_RANK = "music_rank";
    private final static Integer limit = 3;

    @Autowired
    private MusicTableMapper musicTableMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 取值时
     * redisTemplate.opsForZSet().range(MUSIC_RANK, start, stop);
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void musicRanking(){
        List<MusicTable> musicTables = musicTableMapper.selectOnLimit(limit);
        // 装填排行表到redis
        for (MusicTable obj : musicTables) {
            // 我这里只存了音乐网站的名称
            redisTemplate.opsForZSet().add(MUSIC_RANK, obj.getName(), obj.getClickNumber());
        }
    }

}
