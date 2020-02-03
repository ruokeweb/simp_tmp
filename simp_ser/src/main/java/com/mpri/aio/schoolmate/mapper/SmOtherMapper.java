package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmOther;


 /**   
 *  
 * @Description:  校友其他信息类型表——DAO
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Sat Mar 02 16:01:26 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface SmOtherMapper extends CrudMapper<SmOther>{


	
	/**
	 * 是否被使用
	* <p>Title: isUseAble</p>  
	* <p>Description: </p>  
	* @param id
	* @return
	 */
	Boolean isUseAble(String id);
}