package com.mpri.aio.system.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.system.model.SysSetting;


 /**   
 *  
 * @Description:  配置信息——DAO
 * @Author:       Carry
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 13 10:22:06 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface SysSettingMapper extends CrudMapper<SysSetting>{

	
}