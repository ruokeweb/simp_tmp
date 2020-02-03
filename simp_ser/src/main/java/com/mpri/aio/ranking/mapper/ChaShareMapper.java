package com.mpri.aio.ranking.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.ranking.model.ChaShare;


 /**   
 *  
 * @Description:  校友分享归纳表——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:19:42 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface ChaShareMapper extends CrudMapper<ChaShare>{

	/**
	 * 批量插入
	 * @param chaDonMoneys
	 */
	void insertBatch(List<ChaShare> chaShare);	
}