package com.mpri.aio.settings.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.mapper.SmPointFunctionMapper;
import com.mpri.aio.settings.model.SmPointFunction;

 /**   
 *  
 * @Description:  校友积分场景——Service
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Fri Sep 14 14:34:30 CST 2018
 * @Version:      v_1.0
 *    
 */
@Service
public class SmPointFunctionService extends CrudService<SmPointFunctionMapper, SmPointFunction>  {

	/**
	 * 根据code获取
	* <p>Title: getByCode</p>  
	* <p>Description: </p>  
	* @param smPointFunction
	* @return
	 */
	public SmPointFunction getByCode(SmPointFunction smPointFunction) {
		return mapper.getByCode(smPointFunction);
	}
}