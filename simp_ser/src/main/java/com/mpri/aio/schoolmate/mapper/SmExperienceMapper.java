package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmExperience;


 /**   
 *  
 * @Description:  校友校园经历——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:38:47 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmExperienceMapper extends CrudMapper<SmExperience>{

	void deleteByUserId(SmExperience experience);
}