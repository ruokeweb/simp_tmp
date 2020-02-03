package com.mpri.aio.ranking.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.ranking.model.ChaDonMoney;


 /**   
 *  
 * @Description:  校友捐赠钱数归纳表——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:14:20 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface ChaDonMoneyMapper extends CrudMapper<ChaDonMoney>{
	/**
	 * 批量插入
	 * @param chaDonMoneys
	 */
	void insertBatch(List<ChaDonMoney> chaDonMoneys);
	
//	/**
//	 * 批量更新
//	 */
//	void updateBatch(List<ChaDonMoney> chaDonMoneys);
}