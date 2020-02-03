package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmHisEducation;
import com.mpri.aio.schoolmate.mapper.SmHisEducationMapper;

 /**   
 *  
 * @Description:  校友其他教育经历表——Service
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Jul 30 15:06:53 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Service
public class SmHisEducationService extends CrudService<SmHisEducationMapper, SmHisEducation>  {

	public void deleteByUserId(SmHisEducation education) {
		mapper.deleteByUserId(education);
	}
}