package com.mpri.aio.system;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.utils.QhSMUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SmsUtilTest extends ApplicationTests {

    @Autowired
    private QhSMUtil qhSMUtil;

    @Test
    public void qhRegistCaptChaTest(){
  //青海大学短信发送
        String result = qhSMUtil.sendSms("15921762873", "会议内容如下: 1111.");
        if (result.equalsIgnoreCase(GlobalStr.SUCCESS)) {
            System.out.println(result);
        }else{
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
        }
    }


//    @Test
    public void qhSMUtilTest(){
        //青海大学短信发送
        String result = qhSMUtil.sendSms("15921762873", "会议内容如下: 1111.");
        System.out.println(result);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
    }

    public static void main(String[] args) {
        String res ="{\"msgid\": \"20436ac759acada06bc71c73f3bfd572\", \"success\": \"0\"}";
        System.out.println(GlobalStr.SUCCESS);
        System.out.println(res.equalsIgnoreCase(GlobalStr.SUCCESS));

    }
}
