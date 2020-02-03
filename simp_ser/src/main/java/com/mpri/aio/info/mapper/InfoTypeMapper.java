package com.mpri.aio.info.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.info.model.InfoType;


 /**   
 *  
 * @Description:  信息类型表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Dec 10 09:52:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface InfoTypeMapper extends CrudMapper<InfoType>{

	
}