package com.mpri.aio.untils.task.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 
* <p>Title: MyJobFactory</p>  
* <p>Description: </p>  
* @author syp  
* @date 2019年3月13日
 */
@Component
public class MyJobFactory extends AdaptableJobFactory{
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;
 
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}

