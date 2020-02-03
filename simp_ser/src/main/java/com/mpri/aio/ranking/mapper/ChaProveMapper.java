package com.mpri.aio.ranking.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.ranking.model.ChaProve;


 /**   
 *  
 * @Description:  校友认证归纳表——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:17:07 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface ChaProveMapper extends CrudMapper<ChaProve>{

	/**
	 * 批量插入
	 * @param chaDonMoneys
	 */
	void insertBatch(List<ChaProve> chaProve);
}