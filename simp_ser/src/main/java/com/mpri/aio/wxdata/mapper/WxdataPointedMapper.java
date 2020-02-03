package com.mpri.aio.wxdata.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.wxdata.model.WxdataPointed;


 /**   
 *  
 * @Description:  小程序埋点数据表——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Jul 18 17:08:03 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface WxdataPointedMapper extends CrudMapper<WxdataPointed>{

	
}