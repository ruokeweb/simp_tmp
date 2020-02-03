package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmProfessionMapper;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.system.common.GlobalStr;

import java.util.List;

/**
 *  
 * @Description:  校友职业经历——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:24 CST 2019
 * @Version:      v_1.0
 *    
 */
@Service
public class SmProfessionService extends CrudService<SmProfessionMapper, SmProfession>  {

     public List<SmProfession> getUserId() {
         return this.mapper.getUserId();
     }
     
     public void deleteByUserId(SmProfession profession) {
    	 mapper.deleteByUserId(profession);
     }
     
     /**
      * 更新其他为曾任职
      */
     public void updateProStatus(SmProfession profession) {
    	 profession.setStatus(GlobalStr.PROFESSION_STATUS_JOIN_OUT);
    	 mapper.updateProStatus(profession);
     }
 }