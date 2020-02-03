package com.mpri.aio.enterprise.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.enterprise.model.EntSchoolmate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *  
 * @Description:  校友企业校友任职表——DAO
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Thu Feb 14 15:59:00 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface EntSchoolmateMapper extends CrudMapper<EntSchoolmate>{

  List<EntSchoolmate> loadByPageSchool(@Param("entity")EntSchoolmate entSchoolmate);
	
}