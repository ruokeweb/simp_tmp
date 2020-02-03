package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmFamilyMapper;
import com.mpri.aio.schoolmate.model.SmFamily;

 /**   
 *  
 * @Description:  校友家庭成员——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:35:41 CST 2019
 * @Version:      v_1.0
 *    
 */
@Service
public class SmFamilyService extends CrudService<SmFamilyMapper, SmFamily>  {

	public void deleteByUserId(SmFamily family) {
		mapper.deleteByUserId(family);
	}
}