package run.zykj.app.subject14.service.impl;

import cn.hutool.core.lang.Assert;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import run.zykj.app.subject14.entity.EmailUser;
import run.zykj.app.subject14.entity.dto.RegisterInfo;
import run.zykj.app.subject14.mapper.EmailUserMapper;
import run.zykj.app.subject14.mq.constants.MessageProtocolConst;
import run.zykj.app.subject14.mq.producer.RegisterProducer;
import run.zykj.app.subject14.mq.protocol.RegisterProtcol;
import run.zykj.app.subject14.service.UserService;
import run.zykj.app.utils.CGlibUtils;

/**
 * @author lingSong
 * @date 2021/1/5 20:55
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    // 未激活用户状态
    private final static String USER_STATUS = "init";
    // 表明数据库已经存在该信息
    public static final Integer ALREADY_EXIST = 1;

    @Autowired
    private EmailUserMapper mapper;

    @Autowired
    private RegisterProducer registerProducer;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void register(@NotNull RegisterInfo info) {
        Assert.notNull(info, "user register info must be not null");

        // 转换对象
        EmailUser user = CGlibUtils.mapper(info, EmailUser.class);
        // 校验
        checkRegisterInfo(user);
        // TODO 加密密码
        // 用户落库 status为init
        mapper.insert(user);
        // TODO 注册时其他关联信息 比如进入APP内用户选择关注了某些话题点之类的
        // 发送激活邮件当用户点击时修改当前用户状态为已激活
        sendEmailEnqueue(user);
    }

    /**
     * 发送邮件入队
     *
     * @param user
     */
    private void sendEmailEnqueue(EmailUser user){
        RegisterProtcol msgProtcol = new RegisterProtcol();
        msgProtcol.setUserId(user.getId()).setEmail(user.getEmail());
        String msgBody = msgProtcol.encode();
        log.info("发送邮件消息入队， 消息协议={}",  msgBody);

        DefaultMQProducer mqProducer = registerProducer.getProducer();
        // 组装rocketmq消息体
        Message message = new Message(MessageProtocolConst.REGISTER_TOPIC.getTopic(),
                msgBody.getBytes());

        try {
            SendResult sendResult = mqProducer.send(message);
            // 判断
            if(sendResult == null){
                log.error("发送邮件消息投递异常， 注册失败, msgBody={}", msgBody);
            }
            if(sendResult.getSendStatus() != SendStatus.SEND_OK){
                log.error("发送邮件消息投递异常， 注册失败, msgBody={}", msgBody);
            }
        }catch (Exception e){
            int sendRetryTimes = mqProducer.getRetryTimesWhenSendAsyncFailed();
            log.error("发送邮件消息投递异常， 注册失败, msgBody={}, sendRetryTimes={}", msgBody, sendRetryTimes);
            throw new RuntimeException("发送邮件消息投递异常， 注册失败");
        }
    }

    /**
     * 校验
     *
     * @param user
     */
    private void checkRegisterInfo(EmailUser user){
        // 提示：count(1)与count(*) 是一致的，不要相信网上说的count(1)较快
        // 提示： 因为在InnoDB中, count()函数是从表中一行一行的读取, 然后累计加数，这样自然就慢了
        // 建议： 建一个字段计数
        // 不过我这里还是直接走count(*)
        if(ALREADY_EXIST == mapper.checkNameUnique(user.getName())){
            log.info("[注册用户]-----当前用户昵称已经存在!");
            throw new RuntimeException("[注册用户]-----当前用户昵称已经存在!");
        }else if(ALREADY_EXIST == mapper.checkEmailUnique(user.getEmail())){
            log.info("[注册用户]-----当前用户邮箱已经存在!");
            throw new RuntimeException("[注册用户]-----当前用户邮箱已经存在!");
        }
    }

}
