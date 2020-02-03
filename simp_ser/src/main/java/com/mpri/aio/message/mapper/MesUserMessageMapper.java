package com.mpri.aio.message.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesUserMessage;


 /**   
 *  
 * @Description:  用户消息标记表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:32:26 CST 2018
 * @Version:      v_1.02
 *    
 */
@Mapper
public interface MesUserMessageMapper extends CrudMapper<MesUserMessage>{

	/**
	 * 根据消息id 删除
	 */
	void delOverMsg (MesUserMessage mesUserMessage);
}