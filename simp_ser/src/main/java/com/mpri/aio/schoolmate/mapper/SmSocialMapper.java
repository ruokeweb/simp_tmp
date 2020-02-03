package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmSocial;

import java.util.List;


/**   
 *  
 * @Description:  校友社会兼职——DAO
 * @Author:       syp
 * @project 	  exchange_datasource 
 * @CreateDate:   Mon Jan 28 15:55:48 CST 2019
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmSocialMapper extends CrudMapper<SmSocial>{


     List<SmSocial> getUserId();
     
     void deleteByUserId(SmSocial smSocial);
 }