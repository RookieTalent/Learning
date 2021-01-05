package run.zykj.app.subject14.mq.protocol;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return null;
    }

    @Override
    public void decode(String msg) {

    }

    public RegisterProtcol setUserId(Long userId) {
        this.userId = userId;
        return this;
    }


    public RegisterProtcol setEmail(String email) {
        this.email = email;
        return this;
    }
}
