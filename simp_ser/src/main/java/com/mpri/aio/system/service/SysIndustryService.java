package com.mpri.aio.system.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.mapper.SysIndustryMapper;

 /**   
 *  
 * @Description:  行业管理——Service
 * @Author:       Carry
 * @project 	  simp 
 * @CreateDate:   Thu Feb 21 15:52:16 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class SysIndustryService extends CrudService<SysIndustryMapper, SysIndustry>  {


     public int loadChildsBy(SysIndustry sysIndustry) {
         return this.mapper.loadChildsBy(sysIndustry);
     }
 }