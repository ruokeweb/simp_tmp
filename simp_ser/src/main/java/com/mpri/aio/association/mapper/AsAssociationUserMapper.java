package com.mpri.aio.association.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.association.model.AsAssociationUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *  
 * @Description:  校友会对应关系表——DAO
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Thu Feb 21 13:24:13 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface AsAssociationUserMapper extends CrudMapper<AsAssociationUser>{

  List<AsAssociationUser> loadSchoolByPage(@Param("entity") AsAssociationUser asAssociationUser);
}