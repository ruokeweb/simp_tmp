package com.mpri.aio.message.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.mapper.MesTemplateMapper;

 /**   
 *  
 * @Description:  消息模板表——Service
 * @Author:       Clown
 * @project 	  simp 
 * @CreateDate:   Thu Mar 07 18:43:59 CST 2019
 * @Version:      v_2.0
 *    
 */
@Service
public class MesTemplateService extends CrudService<MesTemplateMapper, MesTemplate>  {


}