package com.mpri.aio.message.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.model.MesScheduler;
import com.mpri.aio.message.mapper.MesSchedulerMapper;

 /**   
 *  
 * @Description:  定时通知消息——Service
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Tue Mar 12 15:51:03 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class MesSchedulerService extends CrudService<MesSchedulerMapper, MesScheduler>  {

	/**
	 * 获取当日定时通知消息列表
	 */
	public List<MesScheduler> loadListByDate(@Param("entity") MesScheduler mesScheduler){
		return mapper.loadListByDate(mesScheduler);
	}
}