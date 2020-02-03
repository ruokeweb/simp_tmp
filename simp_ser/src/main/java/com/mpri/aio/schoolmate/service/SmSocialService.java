package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmSocialMapper;
import com.mpri.aio.schoolmate.model.SmSocial;

import java.util.List;

/**
 *  
 * @Description:  校友社会兼职——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:48 CST 2019
 * @Version:      v_1.0
 *    
 */

@Service
public class SmSocialService extends CrudService<SmSocialMapper, SmSocial>  {


     public List<SmSocial> getUserId() {
         return this.mapper.getUserId();
     }
     
     public void deleteByUserId(SmSocial smSocial) {
    	 mapper.deleteByUserId(smSocial);
     }
 }