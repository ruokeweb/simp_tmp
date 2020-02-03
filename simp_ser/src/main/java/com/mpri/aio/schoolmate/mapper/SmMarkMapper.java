package com.mpri.aio.schoolmate.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.model.SmSchoolmate;


 /**   
 *  
 * @Description:  校友标签管理——DAO
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Fri Aug 24 11:05:42 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmMarkMapper extends CrudMapper<SmMark>{
	/**
	 * 获取当前用户的标签列表
	* <p>Title: loadMarkByUser</p>  
	* <p>Description: </p>  
	* @param schoolmate
	* @return
	 */
	List<SmMark> loadMarkByUser(SmSchoolmate schoolmate);
	
	/**
	 * 删除中间表数据
	 */
	void deleteSmMarks(SmMark smMark);
	
	/**
	 * 更改标签状态
	* <p>Title: updateUnable</p>  
	* <p>Description: </p>  
	* @param smMark
	 */
	void updateUnable(SmMark smMark);
	
	/**
	 * 是否可以删除
	* <p>Title: EXPERIENCEINFO</p>  
	* <p>Description: </p>  
	* @param sysUserId
	* @return
	 */
	Boolean isUseAble(String id);
	
	
	/**
	 * 通过标签父id获取所有的子标签
	 */
	List<SmMark> loadByParent(@Param("entity") SmMark smMark);

}