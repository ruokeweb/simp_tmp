package com.mpri.aio.untils.task.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.untils.task.mapper.TaskJobsMapper;
import com.mpri.aio.untils.task.model.TaskJobs;

 /**   
 *  
 * @Description:  定时任务管理——Service
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Wed Mar 06 13:20:15 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class TaskJobsService extends CrudService<TaskJobsMapper, TaskJobs>  {

}