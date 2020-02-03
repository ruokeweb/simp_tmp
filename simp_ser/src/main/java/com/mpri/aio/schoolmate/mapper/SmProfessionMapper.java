package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmProfession;

import java.util.List;


/**   
 *  
 * @Description:  校友职业经历——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:24 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmProfessionMapper extends CrudMapper<SmProfession>{


     List<SmProfession> getUserId();
     
     void deleteByUserId(SmProfession profession);
     
     void updateProStatus(SmProfession profession);
 }