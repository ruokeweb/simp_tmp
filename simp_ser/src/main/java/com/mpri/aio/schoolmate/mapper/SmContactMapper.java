package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmContact;

import java.util.List;


/**   
 *  
 * @Description:  校友联系方式——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:30:05 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmContactMapper extends CrudMapper<SmContact>{


     List<SmContact> getUserId();
     
     void deleteByUserId(SmContact contact);
     
     /*
      * 获取联系方式重复的人
      */
     List<SmContact> getMergeUserId();
 }