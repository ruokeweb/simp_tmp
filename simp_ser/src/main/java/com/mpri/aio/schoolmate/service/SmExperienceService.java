package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmExperienceMapper;
import com.mpri.aio.schoolmate.model.SmExperience;

 /**   
 *  
 * @Description:  校友校园经历——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:38:47 CST 2019
 * @Version:      v_1.0
 *    
 */
@Service
public class SmExperienceService extends CrudService<SmExperienceMapper, SmExperience>  {

	public void deleteByUserId(SmExperience experience) {
		mapper.deleteByUserId(experience);
	}
}