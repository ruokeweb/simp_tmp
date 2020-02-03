package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.schoolmate.mapper.SmWishMapper;

 /**   
 *  
 * @Description:  校友祝福——Service
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Tue May 28 17:57:43 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SmWishService extends CrudService<SmWishMapper, SmWish>  {

	public void deleteByUserId(SmWish smWish) {
		mapper.deleteByUserId(smWish);
	}
}