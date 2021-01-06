package run.zykj.app.subject19;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaolin
 * @date 2021/1/6 11:21
 */
@Slf4j
public class Jd {

    public  static final String PRIZE_KEY="jd:prize";
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
            List<String> crowds=this.prize();
            crowds.forEach(t->this.redisTemplate.opsForSet().add(PRIZE_KEY,t));
        }
    }

    /**
     *按一定的概率初始化奖品
     */
    public List<String> prize() {
        List<String> list=new ArrayList<>();
        //10个京豆，概率10%
        for (int i = 0; i < 10; i++) {
            list.add("10-"+i);
        }
        //5个京豆，概率20%
        for (int i = 0; i < 20; i++) {
            list.add("5-"+i);
        }
        //1个京豆，概率60%
        for (int i = 0; i < 60; i++) {
            list.add("1-"+i);
        }
        //0个京豆，概率10%
        for (int i = 0; i < 10; i++) {
            list.add("0-"+i);
        }
        return list;
    }

    // 抽奖实现
    public String Jdprize() {
        String result="";
        try {
            //随机取1次。
            String object = (String)this.redisTemplate.opsForSet().randomMember(PRIZE_KEY);
            if (!StringUtils.isEmpty(object)){
                //截取序列号 例如10-1
                int temp=object.indexOf('-');
                int no=Integer.valueOf(object.substring(0,temp));
                switch (no){
                    case 0:
                        result="谢谢参与";
                        break;
                    case 1:
                        result="获得1个京豆";
                        break;
                    case 5:
                        result="获得5个京豆";
                        break;
                    case 10:
                        result="获得10个京豆";
                        break;
                    default:
                        result="谢谢参与";
                }
            }
            log.info("查询结果：{}", object);
        } catch (Exception ex) {
            log.error("exception:", ex);
        }
        return result;
    }


}
