package com.mpri.aio.act.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.act.model.ActActivity;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**   
 *  
 * @Description:  活动——DAO
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:25:30 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface ActActivityMapper extends CrudMapper<ActActivity>{
     List<ActActivity> loadAllListByNotDone(@Param("entity") ActActivity actActivity);
 }