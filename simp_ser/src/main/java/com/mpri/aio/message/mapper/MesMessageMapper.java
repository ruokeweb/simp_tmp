package com.mpri.aio.message.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesMessage;


 /**   
 *  
 * @Description:  通知消息表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:31:22 CST 2018
 * @Version:      v_1.02
 *    
 */
@Mapper
public interface MesMessageMapper extends CrudMapper<MesMessage>{

	/**
	 * 通过接受人或者接受组获取消息列表
	 */
	public List<MesMessage> getMesListBy(@Param("entity")MesMessage mesMessage);
	
	
	/**
	 * 通过接受人或者接受组获取消息条数
	 */
	public int getMesCountBy(@Param("entity")MesMessage mesMessage);
	
	/**
	 * 获取首页单条信息
	 */
	public MesMessage getFirstMesBy(@Param("entity")MesMessage mesMessage);
	
	/**
	 * 通过接受人或者接受组获取消息列表(已读)
	 */
	public List<MesMessage> getMesLisReadtBy(@Param("entity")MesMessage mesMessage);
	
	/**
	 * 获取消息内容
	 */
	public MesMessage getMesContent(MesMessage mesMessage);
	
}