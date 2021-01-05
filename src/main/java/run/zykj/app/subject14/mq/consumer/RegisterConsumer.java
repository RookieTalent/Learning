package run.zykj.app.subject14.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.zykj.app.config.MQnamesrvConfig;
import run.zykj.app.subject14.mq.constants.MessageProtocolConst;
import run.zykj.app.subject14.mq.listener.RegisterListener;

import javax.annotation.PostConstruct;

/**
 * @author lingSong
 * @date 2021/1/5 20:45
 */
@Slf4j
@Component
public class RegisterConsumer {

    @Autowired
    private MQnamesrvConfig mQnamesrvConfig;

    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Autowired
    private RegisterListener registerListener;

    @PostConstruct
    public void init(){
        defaultMQPushConsumer =
                new DefaultMQPushConsumer(
                        MessageProtocolConst.REGISTER_TOPIC.getConsumerGroup());
        defaultMQPushConsumer.setNamesrvAddr(mQnamesrvConfig.nameSrvAddr());
        // 从头消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 消费模式： 集群模式
        // 集群： 同一条消息 只会被一个消费节点消费到
        // 广播： 同一条消息 每个消费者都会消费到
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册监听器
        defaultMQPushConsumer.registerMessageListener(registerListener);
        // 设置监听器个数
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        // 订阅topic下所有消息
        try {
            defaultMQPushConsumer.subscribe(MessageProtocolConst.REGISTER_TOPIC.getTopic(), "*");
            // 启动消费者
            defaultMQPushConsumer.start();
        }catch (MQClientException e){
            log.error("[注册消费者]--------RegisterConsumer启动异常! e={}", e);
            throw new RuntimeException("[注册消费者]--------RegisterConsumer启动异常!", e);
        }
        log.info("[注册消费者]--------RegisterConsumer启动正常 ");
    }

    public DefaultMQPushConsumer getDefaultMQPushConsumer() {
        return defaultMQPushConsumer;
    }
}
