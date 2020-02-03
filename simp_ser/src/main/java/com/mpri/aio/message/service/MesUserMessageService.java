package com.mpri.aio.message.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.model.MesUserMessage;
import com.mpri.aio.message.mapper.MesUserMessageMapper;

 /**   
 *  
 * @Description:  用户消息标记表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:32:26 CST 2018
 * @Version:      v_1.02
 *    
 */
@Service
public class MesUserMessageService extends CrudService<MesUserMessageMapper, MesUserMessage>  {


}