package com.mpri.aio.schoolmate.mapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmFamous;
import org.apache.ibatis.annotations.Param;


/**
 *  
 * @Description:  知名校友管理——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Mar 01 13:28:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SmFamousMapper extends CrudMapper<SmFamous>{


     Page<SmFamous> getRandList(@Param("entity") SmFamous smFamous);
 }