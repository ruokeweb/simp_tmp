package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmPolitics;


 /**   
 *  
 * @Description:  校友政治面貌——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:56:39 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmPoliticsMapper extends CrudMapper<SmPolitics>{

	void deleteByUserId(SmPolitics politics);
}