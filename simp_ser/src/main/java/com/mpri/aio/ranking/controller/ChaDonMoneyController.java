package com.mpri.aio.ranking.controller;

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
import com.mpri.aio.ranking.model.ChaDonMoney;
import com.mpri.aio.ranking.service.ChaDonMoneyService;

 /**   
 *  
 * @Description:  校友捐赠钱数归纳表——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Mon Nov 18 11:14:20 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/ranking/chaDonMoney")
public class ChaDonMoneyController extends BaseController {
	
	@Autowired
	private ChaDonMoneyService chaDonMoneyService;
		
	/**
	 * 获取校友捐赠钱数归纳表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param chaDonMoney
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ChaDonMoney> list(int pageNo,int pageSize,ChaDonMoney chaDonMoney) {
		PageIo<ChaDonMoney> pageInfo =  chaDonMoneyService.loadByPage(pageNo,pageSize,chaDonMoney);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友捐赠钱数归纳表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param chaDonMoney
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated ChaDonMoney chaDonMoney){
		chaDonMoneyService.save(chaDonMoney);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友捐赠钱数归纳表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param chaDonMoney
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ChaDonMoney chaDonMoney) {
		chaDonMoneyService.delete(chaDonMoney);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友捐赠钱数归纳表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param chaDonMoney
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ChaDonMoney> get(ChaDonMoney chaDonMoney) {
		return new RestResponse<ChaDonMoney>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				chaDonMoneyService.get(chaDonMoney));	
	}
		
}