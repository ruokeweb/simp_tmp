package com.mpri.aio.system.service;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.mapper.SysSettingMapper;

 /**   
 *  
 * @Description:  配置信息——Service
 * @Author:       Carry
 * @project 	  smmp 
 * @CreateDate:   Wed Feb 13 10:22:06 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
@Order(value=2)
public class SysSettingService extends CrudService<SysSettingMapper, SysSetting>  {


}