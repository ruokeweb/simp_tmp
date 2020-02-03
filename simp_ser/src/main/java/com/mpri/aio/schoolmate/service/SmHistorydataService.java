package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmHistorydataMapper;
import com.mpri.aio.schoolmate.model.SmHistorydata;

import java.util.List;

/**   
 *  
 * @Description:  校友历史数据表——Service
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:54:51 CST 2019
 * @Version:      v_1.0
 *    
 */

@Service
public class SmHistorydataService extends CrudService<SmHistorydataMapper, SmHistorydata>  {


     public List<SmHistorydata> getUserId() {
         return mapper.getUserId();
     }
     
     public void deleteByUserId(SmHistorydata historydata) {
    	 mapper.deleteByUserId(historydata);
     }
 }