package run.zykj.app.subject14.mq.constants;

import lombok.NoArgsConstructor;

/**
 * @author lingSong
 * @date 2021/1/5 20:36
 */
@NoArgsConstructor
public enum MessageProtocolConst {

    /**SECKILL_CHARGE_ORDER_TOPIC 消息协议*/
    REGISTER_TOPIC("REGISTER_TOPIC", "GID_SNOWALKE_PRODUCT_GROUP", "GID_SNOWALKE_CONSUMER_GROUP", "注册邮件消息协议"),
    ;
    /**消息主题*/
    private String topic;
    /**生产者组*/
    private String producerGroup;
    /**消费者组*/
    private String consumerGroup;
    /**消息描述*/
    private String desc;

    MessageProtocolConst(String topic, String producerGroup, String consumerGroup, String desc) {
        this.topic = topic;
        this.producerGroup = producerGroup;
        this.consumerGroup = consumerGroup;
        this.desc = desc;
    }

    public String getTopic() {
        return topic;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public String getDesc() {
        return desc;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }
}
