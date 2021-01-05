package run.zykj.app.subject14.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import run.zykj.app.subject14.mq.protocol.RegisterProtcol;
import run.zykj.app.subject14.service.MailService;

import java.util.List;

/**
 * @author lingSong
 * @date 2021/1/5 20:45
 */
@Slf4j
@Component
public class RegisterListener implements MessageListenerConcurrently {

    private final static Integer CODE_NUMER = 6;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final static String subject = "注册通知";

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt msg : msgs) {
            // 消息解码
            String message = new String(msg.getBody());
            int reconsumeTimes = msg.getReconsumeTimes();
            String msgId = msg.getMsgId();
            String logSuffix = ",msgId=" + msgId + ",reconsumeTimes=" + reconsumeTimes;
            log.info("[发送邮件消费者]-consumer-接收到消息,message={},{}", message, logSuffix);

            // 消息反序列化
            RegisterProtcol protcol = new RegisterProtcol();
            protcol.decode(message);
            log.info("[发送邮件消费者]-consumer-消息实体, msgbody={}", protcol.toString());

            String email = protcol.getEmail();

            // 编写content内容
            StringBuilder sb = new StringBuilder();
            // 需要生成短链接 放入redis 可能还需要code
            sb.append("<h1>此邮件为官方激活邮件!</h1>");

        }


        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }
}
