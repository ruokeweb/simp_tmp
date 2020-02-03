package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.PointFunction;


 /**   
 *  
 * @Description:  校友积分场景表——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:46:44 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface PointFunctionMapper extends CrudMapper<PointFunction>{

	
}