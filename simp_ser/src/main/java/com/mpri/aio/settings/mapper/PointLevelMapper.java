package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.PointLevel;


 /**   
 *  
 * @Description:  等级积分设置——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Mon Feb 25 16:11:15 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface PointLevelMapper extends CrudMapper<PointLevel>{

	
}