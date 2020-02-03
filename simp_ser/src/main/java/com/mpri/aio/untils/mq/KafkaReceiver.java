package com.mpri.aio.untils.mq;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaReceiver {
	private Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);
	 /**
     * 监听kafka.tut 的 topic
     *
     * @param record
     * @param topic  topic
     */
    @KafkaListener(id = "test", topics = "kafka.test")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        
        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get(); 
            System.out.println("Receive： +++++++++++++++ Topic:" + topic);
            System.out.println("Receive： +++++++++++++++ Record:" + record.value());
            System.out.println("Receive： +++++++++++++++ Message:" + message);
            logger.info("Receive： +++++++++++++++ Topic:" + topic);
            logger.info("Receive： +++++++++++++++ Record:" + record);
            logger.info("Receive： +++++++++++++++ Message:" + message);
        }
    }
}
 