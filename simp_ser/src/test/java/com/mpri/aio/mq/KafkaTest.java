package com.mpri.aio.mq;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.untils.mq.KafkaSender;

public class KafkaTest extends ApplicationTests {

	 @Autowired
     private KafkaSender<User> kafkaSender;
 
     @Test
     public void kafkaSend() throws InterruptedException {
    	 
         //模拟发消息
         for (int i = 0; i < 5; i++) {
 
             User user = new User();
             user.setId(UUID.randomUUID().toString());
             user.setMsg(UUID.randomUUID().toString());
             user.setSendTime(new Date());

             kafkaSender.send("kafka.test",user);
             Thread.sleep(3000);
 
         }
     }
}
