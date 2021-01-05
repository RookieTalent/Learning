package run.zykj.app.subject14.mq.protocol.base;

import lombok.Data;

/**
 * @author xiaolin
 * @date 2021/1/5 15:26
 */
@Data
public abstract class BaseMsg {

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 主题名
     */
    private String topicName;

    public abstract String encode();

    public abstract void decode(String msg);

}
