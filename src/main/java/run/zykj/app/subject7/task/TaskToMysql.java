package run.zykj.app.subject7.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDateTime;

/**
 * @author xiaolin
 * @date 2021/1/5 11:05
 */
@Slf4j
@Component
public class TaskToMysql {


    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void redisDataToMysql(){
        log.info("time:{}，开始执行Redis数据持久化到MySQL任务", LocalDateTime.now());

        // TODO 同步某篇文章总的点赞数到MYSQL update 某字段

        // TODO 同步到用户喜欢的文章 但是我没写用户

        // 用set落库计算总点击数好像很划不来？
        // 对于每一条博客返回的都是set集合，如果博客数量够多的话（当然）， 这样可能会导致频繁GC，导致工作线程停止
        // 说不定还是hash好= =

        log.info("time:{}，结束执行Redis数据持久化到MySQL任务", LocalDateTime.now());
    }

}
