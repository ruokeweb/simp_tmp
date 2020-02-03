package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingSubject;


 /**   
 *  
 * @Description:  学科管理——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 19 16:23:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SettingSubjectMapper extends CrudMapper<SettingSubject>{

	
}