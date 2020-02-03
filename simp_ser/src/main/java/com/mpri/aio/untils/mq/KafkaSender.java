package com.mpri.aio.untils.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.alibaba.fastjson.JSON;

/**
 * 消息生产者
 *
 * @author Jarvis
 * @date 2018/8/3
 */
@Component
public class KafkaSender<T> {

    private Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * kafka 发送消息
     *
     * @param obj 消息对象
     */
    public void send(String topic,T obj) {
        String jsonObj = JSON.toJSONString(obj);

        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, jsonObj); 
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
        	
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("Produce: 消息向队列内发送失败:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                //TODO 业务处理
                logger.info("Produce: 消息已发送至队列内:");
            }
        });
    }
    
   
}