package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmOther;
import com.mpri.aio.schoolmate.mapper.SmOtherMapper;

 /**   
 *  
 * @Description:  校友其他信息类型表——Service
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Sat Mar 02 16:01:26 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class SmOtherService extends CrudService<SmOtherMapper, SmOther>  {

	
	/**
	 * 是否被使用
	 */
	public Boolean isUseAble(String id) {
		return mapper.isUseAble(id);
	}

}