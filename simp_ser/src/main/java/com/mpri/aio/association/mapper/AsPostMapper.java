package com.mpri.aio.association.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.association.model.AsPost;
import com.mpri.aio.base.mapper.CrudMapper;


/**
 *  
 * @Description:  校友会任职信息——DAO
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:  Thu Feb 21 13:26:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface AsPostMapper extends CrudMapper<AsPost>{

 List<AsPost> loadSchoolByPage(@Param("entity") AsPost asPost);

}