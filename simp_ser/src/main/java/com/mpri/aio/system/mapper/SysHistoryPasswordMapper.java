package com.mpri.aio.system.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.system.model.SysHistoryPassword;


 /**   
 *  
 * @Description:  用户密码记录——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Dec 26 11:02:28 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SysHistoryPasswordMapper extends CrudMapper<SysHistoryPassword>{


     SysHistoryPassword getByUserId(SysHistoryPassword sysHistoryPassword);
 }