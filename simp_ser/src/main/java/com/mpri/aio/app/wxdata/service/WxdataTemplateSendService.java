package com.mpri.aio.app.wxdata.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.app.wxdata.mapper.WxdataTemplateSendMapper;

 /**   
 *  
 * @Description:  模板消息——Service
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Fri Jul 19 14:05:13 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class WxdataTemplateSendService extends CrudService<WxdataTemplateSendMapper, WxdataTemplateSend>  {


  public WxdataTemplateSend getOne(WxdataTemplateSend wxdataTemplateSend) {
   return this.mapper.getOne(wxdataTemplateSend);
  }
 }