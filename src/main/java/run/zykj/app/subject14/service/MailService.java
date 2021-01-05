package run.zykj.app.subject14.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * @author xiaolin
 * @date 2021/1/5 15:18
 */
@Slf4j
@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void sendSimpleMail(String to, String subject, String content) {
        // 创建simpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送人
        message.setFrom(from);
        // 收件人
        message.setTo(to);
        // 邮件主题
        message.setSubject(subject);
        // 邮件内容
        message.setText(content);
        // 发送
        try {
            mailSender.send(message);
        }catch (Exception e){
            log.error("发送文本邮件失败, 发送人:{}, 收件人:{}", from, to);
            throw new RuntimeException("发送文本邮件失败!", e);
        }
        log.info("发送文本邮件成功! 发送人:{}, 收件人:{}", from, to);
    }

    public void sendHtmlMail(String to, String subject, String content) {
        // 获取消息对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            // 邮件发送人
            messageHelper.setFrom(from);
            // 收件人
            messageHelper.setTo(to);
            // 邮件主题
            messageHelper.setSubject(subject);
            // 邮件内容
            messageHelper.setText(content, true);
            // 发送
            mailSender.send(message);
        }catch (Exception e){
            log.error("发送HTML邮件失败, 发送人:{}, 收件人:{}", from, to);
            throw new RuntimeException("发送HTML邮件失败!", e);
        }
        log.info("发送HTML邮件成功! 发送人:{}, 收件人:{}", from, to);
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
        }catch (Exception e){
            log.error("发送附件邮件失败, 发送人:{}, 收件人:{}", from, to);
            throw new RuntimeException("发送附件邮件失败!", e);
        }
        log.info("发送附件邮件成功! 发送人:{}, 收件人:{}", from, to);
    }


}
