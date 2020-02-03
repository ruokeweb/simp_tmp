package com.mpri.aio.message.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.message.model.MesGroupUser;
import com.mpri.aio.schoolmate.model.SmSchoolmate;


 /**   
 *  
 * @Description:  信息组用户关系表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:28:51 CST 2018
 * @Version:      v_1.02
 *    
 */
@Mapper
public interface MesGroupUserMapper extends CrudMapper<MesGroupUser>{
	/**
	 *  分页查询校友
	 */
	Page<SmSchoolmate> loadSmByPage(SmSchoolmate schoolmate);

	
}