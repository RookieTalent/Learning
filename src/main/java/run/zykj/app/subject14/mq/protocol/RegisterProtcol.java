package run.zykj.app.subject14.mq.protocol;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import run.zykj.app.subject14.mq.constants.MessageProtocolConst;
import run.zykj.app.subject14.mq.protocol.base.BaseMsg;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xiaolin
 * @date 2021/1/5 15:26
 */
@Slf4j
@NoArgsConstructor
public class RegisterProtcol extends BaseMsg implements Serializable {

    // 用户id
    private Long userId;

    // 用户邮箱
    private String email;

    private Map<String, String> header;
    private Map<String, String> body;


    @Override
    public String encode() {
        // 组装消息头
        ImmutableMap.Builder<String, String> headerBuilder = new ImmutableMap.Builder<String, String>()
                .put("version", this.getVersion())
                .put("topicName", MessageProtocolConst.REGISTER_TOPIC.getTopic());
        header = headerBuilder.build();

        body = new ImmutableMap.Builder<String, String>()
                .put("userId", String.valueOf(this.userId))
                .put("email", this.email)
                .build();

        ImmutableMap<String, Object> map = new ImmutableMap.Builder<String, Object>()
                .put("header", header)
                .put("body", body)
                .build();

        // 返回消息队列
        String jsonString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object value;
            jsonString = objectMapper.writeValueAsString(map);
        }catch (Exception e){
            log.error("RegisterProtocol消息序列化json异常:{}", e);
            throw new RuntimeException("RegisterProtocol消息序列化json异常");
        }
        return jsonString;
    }

    @Override
    public void decode(String msg) {
        Preconditions.checkNotNull(msg);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(msg);
            // header
            this.setVersion(root.get("header").get("version").asText());
            this.setTopicName(root.get("header").get("topicName").asText());
            //body
            this.setUserId(Long.getLong(root.get("body").get("userId").asText()));
            this.setEmail(root.get("body").get("email").asText());
        }catch (Exception e){
            log.error("RegisterProtocol消息反序列化json异常:{}", e);
            throw new RuntimeException("RegisterProtocol消息反序列化json异常");
        }
    }

    public RegisterProtcol setUserId(Long userId) {
        this.userId = userId;
        return this;
    }


    public RegisterProtcol setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
