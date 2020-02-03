package com.mpri.aio.untils.task.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.untils.task.model.TaskJobs;


 /**   
 *  
 * @Description:  定时任务管理——DAO
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Wed Mar 06 13:20:15 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface TaskJobsMapper extends CrudMapper<TaskJobs>{

	
}