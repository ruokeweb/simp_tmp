package com.mpri.aio.schoolmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.schoolmate.service.SmWishService;

 /**   
 *  
 * @Description:  校友祝福——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Tue May 28 17:57:43 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/sm/smWish")
public class SmWishController extends BaseController {
	
	@Autowired
	private SmWishService smWishService;
		
	/**
	 * 获取校友祝福列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smWish
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmWish> list(int pageNo,int pageSize,SmWish smWish) {
		PageIo<SmWish> pageInfo =  smWishService.loadByPage(pageNo,pageSize,smWish);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友祝福
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smWish
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SmWish smWish){
		smWishService.save(smWish);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友祝福（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smWish
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmWish smWish) {
		smWishService.delete(smWish);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友祝福
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smWish
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmWish> get(SmWish smWish) {
		return new RestResponse<SmWish>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smWishService.get(smWish));	
	}
		
}