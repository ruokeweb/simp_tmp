package com.mpri.aio.act.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.act.mapper.ActSettingMapper;

 /**   
 *  
 * @Description:  活动——Service
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:28:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class ActSettingService extends CrudService<ActSettingMapper, ActSetting>  {


     public int getNumByActId(String actId) {
        return this.mapper.getNumByActId(actId);
     }
 }