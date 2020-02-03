package com.mpri.aio.settings.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.PointLoginSign;
import org.apache.ibatis.annotations.Param;


/**
 *  
 * @Description:  校友登陆签到表——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 26 10:45:42 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface PointLoginSignMapper extends CrudMapper<PointLoginSign>{
     /**
      * 判断是否是当天首次登陆
      * <p>Title: isFirstLogin</p>
      * <p>Description: </p>
      * @param loginSign
      * @return
      */

     PointLoginSign isFirstLogin(@Param("entity")PointLoginSign loginSign);
 }