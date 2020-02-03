package com.mpri.aio.info.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.info.model.InfoType;
import com.mpri.aio.info.mapper.InfoTypeMapper;

 /**   
 *  
 * @Description:  信息类型表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Dec 10 09:52:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@Service
public class InfoTypeService extends CrudService<InfoTypeMapper, InfoType>  {


}