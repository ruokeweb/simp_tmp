package com.mpri.aio.app.wxdata.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import org.apache.ibatis.annotations.Param;


/**
 *  
 * @Description:  模板消息——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Fri Jul 19 14:05:13 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface WxdataTemplateSendMapper extends CrudMapper<WxdataTemplateSend>{

  WxdataTemplateSend getOne(@Param("entity") WxdataTemplateSend entity);
}