package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmHonor;


 /**   
 *  
 * @Description:  校友荣誉成果表——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:53:35 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmHonorMapper extends CrudMapper<SmHonor>{

	void deleteByUserId(SmHonor honor);
}