package run.zykj.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaolin
 * @date 2021/1/5 16:07
 */
@Component
public class MQnamesrvConfig {

    @Value("${rocketmq.nameServer.offline}")
    private String offlineNamesrv;

    /**
     * 根据环境选择配置nameServerAddress
     * 我单节点
     *
     * @return
     */
    public String nameSrvAddr(){
        return offlineNamesrv;
    }
}
