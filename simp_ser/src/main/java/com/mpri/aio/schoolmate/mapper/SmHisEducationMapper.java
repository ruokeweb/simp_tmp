package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmExperience;
import com.mpri.aio.schoolmate.model.SmHisEducation;


 /**   
 *  
 * @Description:  校友其他教育经历表——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Jul 30 15:06:53 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface SmHisEducationMapper extends CrudMapper<SmHisEducation>{

	void deleteByUserId(SmHisEducation education);
}