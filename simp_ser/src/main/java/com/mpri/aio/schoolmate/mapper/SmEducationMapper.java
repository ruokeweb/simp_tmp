package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmEducation;

import java.util.List;


/**   
 *  
 * @Description:  校友卡教育经历——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Feb 19 15:45:55 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface SmEducationMapper extends CrudMapper<SmEducation>{

	/**
	 * 设置默认 
	 */
	void setDefault(SmEducation smEducation);
	/**
	 * 清除默认
	 */
	void clearDefault(SmEducation smEducation);
	
	/**
	 * 获取有效的记录数
	 */
	int getCount(SmEducation smEducation);

	/**
	 * 获取教育经历大学2条的userid
	 * @return
	 */
     List<SmEducation> getUserId();
     
     void deleteByUserId(SmEducation smEducation);
 }