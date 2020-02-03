package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.PointLogs;


 /**   
 *  
 * @Description:  校友积分记录表——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:44:18 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface PointLogsMapper extends CrudMapper<PointLogs>{

	
}