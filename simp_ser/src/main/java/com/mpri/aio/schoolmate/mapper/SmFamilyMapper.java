package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmExperience;
import com.mpri.aio.schoolmate.model.SmFamily;


 /**   
 *  
 * @Description:  校友家庭成员——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:35:41 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmFamilyMapper extends CrudMapper<SmFamily>{

	void deleteByUserId(SmFamily family);
}