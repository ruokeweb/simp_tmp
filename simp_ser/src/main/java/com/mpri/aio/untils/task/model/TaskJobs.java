package com.mpri.aio.untils.task.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  定时任务管理
 * @Author:       Clown
 * @project       simp   
 * @CreateDate:   Wed Mar 06 13:20:15 CST 2019
 * @Version:      v_2.0
 *    
 */
public class TaskJobs extends DataEntity<TaskJobs> {

	private static final long serialVersionUID = 1551849597698L;
	
	private String jobGroup;
	private String jobName;
	private String className;
	private String status;
	private String cronExcute;

	
	public String getJobGroup() {
		return this.jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}	
	public String getJobName() {
		return this.jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}	
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String className) {
		this.className = className;
	}	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}	
	public String getCronExcute() {
		return this.cronExcute;
	}
	public void setCronExcute(String cronExcute) {
		this.cronExcute = cronExcute;
	}	

}
