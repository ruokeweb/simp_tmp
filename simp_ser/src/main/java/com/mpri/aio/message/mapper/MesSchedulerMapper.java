package com.mpri.aio.message.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesScheduler;


 /**   
 *  
 * @Description:  定时通知消息——DAO
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Tue Mar 12 15:51:03 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface MesSchedulerMapper extends CrudMapper<MesScheduler>{
	
	/**
	 * 获取当日定时通知消息列表
	 */
	List<MesScheduler> loadListByDate (@Param("entity") MesScheduler mesScheduler);
}