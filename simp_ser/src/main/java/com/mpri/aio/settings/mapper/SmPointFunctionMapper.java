package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SmPointFunction;


 /**   
 *  
 * @Description:  校友积分场景——DAO
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Fri Sep 14 14:34:30 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmPointFunctionMapper extends CrudMapper<SmPointFunction>{

	/**
	 * 根据code获取对象
	 */
	SmPointFunction getByCode(SmPointFunction smPointFunction);
}