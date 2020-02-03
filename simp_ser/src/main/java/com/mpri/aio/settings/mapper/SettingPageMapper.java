package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingPage;


 /**   
 *  
 * @Description:  页面信息配置——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Tue May 28 14:54:02 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SettingPageMapper extends CrudMapper<SettingPage>{

	
}