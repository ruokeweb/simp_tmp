package com.mpri.aio.enterprise.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.enterprise.model.EntEnterprise;


 /**   
 *  
 * @Description:  校友会信息表——DAO
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 12 08:41:49 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface EntEnterpriseMapper extends CrudMapper<EntEnterprise>{

  EntEnterprise getname(EntEnterprise entEnterprisent);
}