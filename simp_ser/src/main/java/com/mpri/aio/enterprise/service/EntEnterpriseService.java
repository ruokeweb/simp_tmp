package com.mpri.aio.enterprise.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.enterprise.model.EntEnterprise;
import com.mpri.aio.enterprise.mapper.EntEnterpriseMapper;

 /**   
 *  
 * @Description:  校友会信息表——Service
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 12 08:41:49 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class EntEnterpriseService extends CrudService<EntEnterpriseMapper, EntEnterprise>  {

  public EntEnterprise getname(EntEnterprise entEnterprisent){
    return this.mapper.getname( entEnterprisent);
  }
}