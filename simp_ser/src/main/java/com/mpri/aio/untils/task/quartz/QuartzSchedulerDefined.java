package com.mpri.aio.untils.task.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.mpri.aio.base.utils.SpringUtil;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.untils.task.model.TaskJobs;
import com.mpri.aio.untils.task.service.TaskJobsService;

/**
 * 任务调度处理
 * <p>
 * Title: QuartzScheduler
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2019年3月5日
 */
@Configuration
public class QuartzSchedulerDefined {

	@Autowired
	private Scheduler scheduler;
	
	@Autowired
	private TaskJobsService taskJobsService;


	public void startJob() throws SchedulerException, ClassNotFoundException {
		TaskJobs entity = new TaskJobs();
		List<TaskJobs> taskJobses = taskJobsService.loadAllListBy(entity);
		
//    	startJob1(scheduler); 
//    	startJob2(scheduler); 
		
		for (TaskJobs job : taskJobses) {
			System.err.println(job.getClassName());
			startJobs(scheduler, 
					SpringUtil.getBean(job.getClassName()).getClass(),
					job.getJobName(),
					job.getJobGroup(),
					job.getCronExcute());
			if(GlobalStr.JOB_STOP.equals(job.getStatus())) {
				pauseJob(job.getJobName(), job.getJobGroup());
			}
		}
//		startJobs(scheduler, SchedulerQuartzJob1.class, "job1", "group1", "0 */1 * * * ?");
//		startJobs(scheduler, SchedulerQuartzJob2.class, "job2", "group2", "0 */2 * * * ?");
		scheduler.start();
	}

	/**
	 * 获取Job信息
	 * <p>
	 * Title: getJobInfo
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param name
	 * @param group
	 * @return
	 * @throws SchedulerException
	 */
	public String getJobInfo(String name, String group) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(name, group);
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
				scheduler.getTriggerState(triggerKey).name());
	}

	/**
	 * 修改某个任务的执行时间
	 * <p>
	 * Title: modifyJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param name
	 * @param group
	 * @param time
	 * @return
	 * @throws SchedulerException
	 */
	public boolean modifyJob(String name, String group, String time) throws SchedulerException {
		Date date = null;
		TriggerKey triggerKey = new TriggerKey(name, group);
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(cronTrigger == null)
			return false;
		String oldTime = cronTrigger.getCronExpression();
		if (!oldTime.equalsIgnoreCase(time)) {
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
					.withSchedule(cronScheduleBuilder).build();
			date = scheduler.rescheduleJob(triggerKey, trigger);
		}
		return date != null;
	}

	/**
	 * 暂停所有任务
	 * <p>
	 * Title: pauseAllJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws SchedulerException
	 */
	public void pauseAllJob() throws SchedulerException {
		scheduler.pauseAll();
	}

	/**
	 * 暂停某个任务
	 * <p>
	 * Title: pauseJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param name
	 * @param group
	 * @throws SchedulerException
	 */
	public void pauseJob(String name, String group) throws SchedulerException {
		JobKey jobKey = new JobKey(name, group);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null)
			return;
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复所有任务
	 * <p>
	 * Title: resumeAllJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @throws SchedulerException
	 */
	public void resumeAllJob() throws SchedulerException {
		scheduler.resumeAll();
	}

	/**
	 * 恢复某个任务
	 * <p>
	 * Title: resumeJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param name
	 * @param group
	 * @throws SchedulerException
	 */
	public void resumeJob(String name, String group) throws SchedulerException {
		JobKey jobKey = new JobKey(name, group);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null)
			return;
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 刪除某個任務
	 * <p>
	 * Title: deleteJob
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param name
	 * @param group
	 * @throws SchedulerException
	 */
	public void deleteJob(String name, String group) throws SchedulerException {
		JobKey jobKey = new JobKey(name, group);
		JobDetail jobDetail = scheduler.getJobDetail(jobKey);
		if (jobDetail == null)
			return;
		scheduler.deleteJob(jobKey);
	}
	
	private void startJobs(Scheduler scheduler, Class clazz ,String jobName,String groupName,String cron) throws SchedulerException {
		 JobDetail jobDetail = JobBuilder.newJob(clazz)
				 .withIdentity(jobName, groupName).build();
		 CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron); 
		 CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, groupName) 
				 .withSchedule(cronScheduleBuilder).build(); 
		 scheduler.scheduleJob(jobDetail, cronTrigger); 		
	}
	
    private String lowerFirstChar(String str){
        char [] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
