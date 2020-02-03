package com.mpri.aio.untils.task.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.untils.task.model.TaskJobs;
import com.mpri.aio.untils.task.quartz.QuartzSchedulerDefined;
import com.mpri.aio.untils.task.service.TaskJobsService;

 /**   
 *  
 * @Description:  定时任务管理——Controller
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Wed Mar 06 13:20:15 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/task/taskJobs")
public class TaskJobsController extends BaseController {
	
	@Autowired
	private TaskJobsService taskJobsService;
	
    @Autowired
    private QuartzSchedulerDefined quartzScheduler;
		
	/**
	 * 获取定时任务管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param taskJobs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<TaskJobs> list(int pageNo,int pageSize,TaskJobs taskJobs) {
		PageIo<TaskJobs> pageInfo =  taskJobsService.loadByPage(pageNo,pageSize,taskJobs);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新定时任务管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param taskJobs
	* @return
	 */
	@Logs(value = "定时任务编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(TaskJobs taskJobs){
		try {
			taskJobsService.save(taskJobs);
			quartzScheduler.modifyJob(taskJobs.getJobName(), taskJobs.getJobGroup(), taskJobs.getCronExcute());
			if(StringUtil.isNotEmpty(taskJobs.getId())) {
				if(GlobalStr.JOB_NORMAL.equals(taskJobs.getStatus())) { //重启此任务
					quartzScheduler.resumeJob(taskJobs.getJobName(), taskJobs.getJobGroup());
				}else { //停止该任务
					quartzScheduler.pauseJob(taskJobs.getJobName(), taskJobs.getJobGroup());
				}				
			}
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");			
		}catch(SchedulerException e) {
			//// ex.printStackTrace();;
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "更新状态失败！", "");					
		}					
	}	
	
	
	/**
	 * 更新job状态
	 */
	@Logs(value = "定时任务状态修改",type ="OTHER")
	@CrossOrigin
	@PostMapping(value = "/updateStatus")
	public RestResponse<String> updateStatus(TaskJobs taskJobs){		
		try {	
			taskJobsService.save(taskJobs);
			TaskJobs job = new TaskJobs();
			job.setId(taskJobs.getId());
			job = taskJobsService.get(job);
			if(GlobalStr.JOB_NORMAL.equals(taskJobs.getStatus())) { //重启此任务
				quartzScheduler.resumeJob(job.getJobName(), job.getJobGroup());
			}else { //停止该任务
				quartzScheduler.pauseJob(job.getJobName(), job.getJobGroup());
			}
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "更新状态成功！", "");		
		} catch (SchedulerException e) {
			//// ex.printStackTrace();;
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "更新状态失败！", "");		
		}					
	}
	
	
	/**
	 * 删除定时任务管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param taskJobs
	* @return
	 */
	@Logs(value = "定时任务删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(TaskJobs taskJobs) {
		taskJobsService.delete(taskJobs);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取定时任务管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param taskJobs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<TaskJobs> get(TaskJobs taskJobs) {
		return new RestResponse<TaskJobs>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				taskJobsService.get(taskJobs));	
	}
		
}