package com.mpri.aio.enterprise.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.enterprise.model.EntIntention;


/**
 *  
 * @Description:  校友企业校友意向表——DAO
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Thu Feb 14 16:35:23 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface EntIntentionMapper extends CrudMapper<EntIntention>{

    List<EntIntention> loadEntAndSchoolByPage(@Param("entity")EntIntention entIntention);
 }