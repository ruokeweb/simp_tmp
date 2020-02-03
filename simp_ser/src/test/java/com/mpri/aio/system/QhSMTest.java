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

public class QhSMTest extends ApplicationTests {

    @Autowired
    private QhSMUtil qhSMUtil;

//    @Test
    public void sengSmeTest(){
        List outAddresses = new ArrayList();
        outAddresses.add("15921762873");

        String result = qhSMUtil.sendSms("测试", "会议内容如下: 1111.");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
    }



}
