package com.mpri.aio.wxdata.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.wxdata.model.WxdataEventTarget;


 /**   
 *  
 * @Description:  自定义事件属性表——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Jul 18 17:15:51 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface WxdataEventTargetMapper extends CrudMapper<WxdataEventTarget>{

	
}