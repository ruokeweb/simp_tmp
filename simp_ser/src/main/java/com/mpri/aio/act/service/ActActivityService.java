package com.mpri.aio.act.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.mapper.ActActivityMapper;

import java.util.List;

/**   
 *  
 * @Description:  活动——Service
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:25:30 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class ActActivityService extends CrudService<ActActivityMapper, ActActivity>  {


     public List<ActActivity> loadAllListByNotDone(ActActivity actActivity) {
         return this.mapper.loadAllListByNotDone(actActivity);
     }
 }