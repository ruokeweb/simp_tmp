package com.mpri.aio.system.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.system.model.SysIndustry;


 /**   
 *  
 * @Description:  行业管理——DAO
 * @Author:       Carry
 * @project 	  simp 
 * @CreateDate:   Thu Feb 21 15:52:16 CST 2019
 * @Version:      v_2.0
 *    
 */
@Mapper
public interface SysIndustryMapper extends CrudMapper<SysIndustry>{


     int loadChildsBy(@Param("entity") SysIndustry sysIndustry);
 }