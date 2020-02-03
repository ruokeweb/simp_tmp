 package com.mpri.aio;

import java.util.concurrent.Executor;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mpri.aio.untils.task.quartz.MyJobFactory;

/**
 * .主函数
 * 
 * @author Cary
 * @date 2018年7月18日
 */
@SpringBootApplication(exclude=SecurityAutoConfiguration.class)
@EnableTransactionManagement
@EnableCaching // 开启缓存，需要显示的指定
@EnableAsync // 开启异步
@EnableScheduling //开启定时任务
@Configuration
//@PropertySource({"application.properties","schoolmate.properties"})
public class Application {
	
	@Autowired
	private MyJobFactory myJobFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Primary
    @Bean
    public TaskExecutor primaryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // add necessary properties to the executor
        return executor;
    }

	
	/**
	 * 开启异步线程(发送邮件)暂时未开启异步。 异步时异常暂时未捕获
	* <p>Title: threadPoolTaskExecutor</p>  
	* <p>Description: </p>  
	* @return
	 */
	@Bean(name = "mailTaskExecutor") 
	public Executor threadPoolTaskExecutor() {
		return new ThreadPoolTaskExecutor();
	}
	
	/**
	 * 注入 Scheduler 和 JobFactory
	 */
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setJobFactory(myJobFactory);
		System.out.println("myJobFactory:"+myJobFactory);
		return schedulerFactoryBean;
	}
	@Bean
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}
}
