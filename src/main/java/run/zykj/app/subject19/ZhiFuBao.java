package run.zykj.app.subject19;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/6 11:23
 */
@Slf4j
public class ZhiFuBao {

    public static final String PRIZE_KEY="taobao:prize";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     *提前先把数据刷新到redis缓存中。
     */
    @PostConstruct
    public void init(){
        log.info("启动初始化..........");

        boolean bo=this.redisTemplate.hasKey(PRIZE_KEY);
        if(!bo){
            List<Integer> crowds=this.prize();
            crowds.forEach(t->this.redisTemplate.opsForSet().add(PRIZE_KEY,t));
        }
    }

    /**
     * 模拟10个用户来抽奖 list存放的是用户id
     * 例如支付宝参与抽奖，就把用户id加入set集合中
     * 例如公司抽奖，把公司所有的员工，工号都加入到set集合中
     */
    public List<Integer> prize() {
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=10;i++){
            list.add(i);
        }
        return list;
    }

    public List<Object> Zhiprize(int num) {
        try {
            SetOperations<String, Object> setOperations= redisTemplate.opsForSet();
            //spop命令，即随机返回并删除set中一个元素
            List<Object> objs = setOperations.pop(PRIZE_KEY,num);
            log.info("查询结果：{}", objs);
            return objs;
        } catch (Exception ex) {
            log.error("exception:", ex);
        }
        return null;
    }


}
