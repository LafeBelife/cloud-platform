package cn.hba.syslog.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * syslog 消费者
 *
 * @author wbw
 * @date 2019/11/6 10:50
 */
@Component
@Slf4j
public class SyslogConsumerMq {

    @KafkaListener(topics = "message_topic")
    public void messageTopic(ConsumerRecord<?, ?> record) {
        log.info("消费 topic = {}, offset = {}, value = {} ", record.topic(), record.offset(), record.value());
    }
}
