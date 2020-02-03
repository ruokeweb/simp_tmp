package com.mpri.aio.schoolmate.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;

import java.util.List;

/**
 * 
 * @Description: 校友通讯地址——DAO
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:31:22 CST 2019
 * @Version: v_1.0
 * 
 */
@Mapper
public interface SmAddressMapper extends CrudMapper<SmAddress> {

	/**
	 * 查询籍贯
	 */
	SmAddress getNationBySysUser(@Param("entity") SmAddress smAddress);

	List<SmAddress> getUserId();
	
	void deleteByUserId(SmAddress address);

}