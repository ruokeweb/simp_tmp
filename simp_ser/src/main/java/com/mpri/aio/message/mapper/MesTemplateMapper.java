package com.mpri.aio.message.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesTemplate;


 /**   
 *  
 * @Description:  消息模板表——DAO
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Thu Mar 07 11:15:15 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface MesTemplateMapper extends CrudMapper<MesTemplate>{

	
}