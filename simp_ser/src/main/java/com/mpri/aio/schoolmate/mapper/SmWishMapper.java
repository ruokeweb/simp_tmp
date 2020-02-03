package com.mpri.aio.schoolmate.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmWish;


 /**   
 *  
 * @Description:  校友祝福——DAO
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Tue May 28 17:57:43 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SmWishMapper extends CrudMapper<SmWish>{

	void deleteByUserId(SmWish smWish);
}