package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmPoliticsMapper;
import com.mpri.aio.schoolmate.model.SmPolitics;

 /**   
 *  
 * @Description:  校友政治面貌——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:56:39 CST 2019
 * @Version:      v_1.0
 *    
 */
@Service
public class SmPoliticsService extends CrudService<SmPoliticsMapper, SmPolitics>  {
	
	public void deleteByUserId(SmPolitics politics) {
		mapper.deleteByUserId(politics);
	}
}