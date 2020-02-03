package com.mpri.aio.system.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.model.SysHistoryPassword;
import com.mpri.aio.system.mapper.SysHistoryPasswordMapper;

 /**   
 *  
 * @Description:  用户密码记录——Service
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Dec 26 11:02:28 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SysHistoryPasswordService extends CrudService<SysHistoryPasswordMapper, SysHistoryPassword>  {


     public SysHistoryPassword getByUserId(SysHistoryPassword password) {

         return this.mapper.getByUserId(password);
     }
 }