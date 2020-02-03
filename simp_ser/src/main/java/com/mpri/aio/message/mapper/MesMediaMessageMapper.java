package com.mpri.aio.message.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesMediaMessage;


 /**   
 *  
 * @Description:  每日多媒体通知——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Nov 05 14:18:55 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface MesMediaMessageMapper extends CrudMapper<MesMediaMessage>{
	
	/**
	 * 获取首页展示的通知
	 * @param media
	 * @return
	 */
	MesMediaMessage getIndexMesMediaMessage(@Param("entity") MesMediaMessage media);

	
}