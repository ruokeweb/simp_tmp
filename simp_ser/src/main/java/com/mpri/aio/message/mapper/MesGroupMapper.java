package com.mpri.aio.message.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesGroup;



 /**   
 *  
 * @Description:  信息群组——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:33:03 CST 2018
 * @Version:      v_1.02
 *    
 */
@Mapper
public interface MesGroupMapper extends CrudMapper<MesGroup>{

	List<MesGroup> loadConditionListBy(@Param("entity") MesGroup mesGroup);
	
}