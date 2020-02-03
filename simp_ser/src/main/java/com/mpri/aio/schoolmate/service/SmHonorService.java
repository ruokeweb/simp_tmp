package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmHonorMapper;
import com.mpri.aio.schoolmate.model.SmHonor;

 /**   
 *  
 * @Description:  校友荣誉成果表——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:53:35 CST 2019
 * @Version:      v_1.0
 *    
 */
@Service
public class SmHonorService extends CrudService<SmHonorMapper, SmHonor>  {

	public void deleteByUserId(SmHonor honor) {
		mapper.deleteByUserId(honor);
	}
}