package com.mpri.aio.act.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.act.model.ActSetting;
import org.apache.ibatis.annotations.Param;


/**
 *  
 * @Description:  活动——DAO
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:28:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface ActSettingMapper extends CrudMapper<ActSetting>{


     int getNumByActId(@Param("actId") String actId);
 }