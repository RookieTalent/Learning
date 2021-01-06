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
import run.zykj.app.subject14.utils.VerityCodeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lingSong
 * @date 2021/1/5 20:45
 */
@Slf4j
@Component
public class RegisterListener implements MessageListenerConcurrently {

    private final static Integer CODE_NUMER = 6;
    public final static String EMAIL_ACTIVE = "email_active:";

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
            String userId = protcol.getUserId();

            // 编写content内容
            StringBuilder sb = new StringBuilder();
            // 生成code
            String code = VerityCodeUtils.generateVerifyCode(6);
            // 需要生成短链接 放入redis 可能还需要code
            sb.append("<h1>此邮件为官方激活邮件!</h1>");
            sb.append("<h3><a href='http://localhost:8085/user/activation/");
            sb.append(code);sb.append("/");
            sb.append(userId);
            sb.append("'>点击激活</a></h3>");

            // 存入redis
            Map<String, String> map = new HashMap<>();
            map.put("userId", String.valueOf(userId));
            map.put("code", code);
            redisTemplate.opsForHash().putAll(EMAIL_ACTIVE+email, map);

            mailService.sendHtmlMail(email, subject, sb.toString());
        }

        /*无论如何，都不要抛出异常，如果需要重新消费，可以返回RECONSUME_LATER主动要求重新消费。
        要加入catch Exception根据异常来捕获业务处理的异常。*/
        // 重复消费的问题的一个可能的问题：消费者消费消息时产生了异常，并没有返回CONSUME_SUCCESS标志。

        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
