package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SmPointLogs;


 /**   
 *  
 * @Description:  校友积分记录——DAO
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Fri Sep 14 15:17:48 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmPointLogsMapper extends CrudMapper<SmPointLogs>{

	
}