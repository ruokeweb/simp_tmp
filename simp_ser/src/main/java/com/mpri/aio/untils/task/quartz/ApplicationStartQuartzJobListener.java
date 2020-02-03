package com.mpri.aio.untils.task.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private QuartzSchedulerDefined quartzScheduler;
	
//	/**
//	 * 初始加载 
//	 */
//	@Bean
//	public Scheduler scheduler() throws SchedulerException{
//		SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
//		return schedulerFactoryBean.getScheduler();
//	}
    
    //注入SchedulerFactoryBean
//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        return schedulerFactoryBean;
//    }
	
	/**
	 * 初始注入
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			quartzScheduler.startJob();
			System.out.println("任务已经启动...");

		} catch (SchedulerException | ClassNotFoundException e) {
			//// ex.printStackTrace();;
		}
	}
}
