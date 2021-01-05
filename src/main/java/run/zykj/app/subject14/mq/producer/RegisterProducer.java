package run.zykj.app.subject14.mq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.zykj.app.config.MQnamesrvConfig;
import run.zykj.app.subject14.mq.constants.MessageProtocolConst;

import javax.annotation.PostConstruct;

/**
 * @author lingSong
 * @date 2021/1/5 20:45
 */
@Slf4j
@Component
public class RegisterProducer {

    @Autowired
    private MQnamesrvConfig mQnamesrvConfig;

    private DefaultMQProducer defaultMQProducer;

    @PostConstruct
    public void init(){
        defaultMQProducer =
                new DefaultMQProducer(MessageProtocolConst.REGISTER_TOPIC.getProducerGroup());
        defaultMQProducer.setNamesrvAddr(mQnamesrvConfig.nameSrvAddr());
        // 设置发送失败次数
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(1);

        try {
            defaultMQProducer.start();
        }catch (MQClientException e){
            log.error("[用户注册生产者]--RegisterProducer加载异常!e={}", e);
            throw new RuntimeException("[用户注册生产者]--RegisterProducer加载异常!", e);
        }
        log.info("[用户注册生产者]--RegisterProducer加载正常!");
    }

    public DefaultMQProducer getProducer() {
        return defaultMQProducer;
    }
}
