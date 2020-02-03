package com.mpri.aio.donation.controller;

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
import com.mpri.aio.donation.model.DonThanks;
import com.mpri.aio.donation.service.DonThanksService;

 /**   
 *  
 * @Description:  捐赠致谢——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Wed Mar 27 16:42:55 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/don/donThanks")
public class DonThanksController extends BaseController {
	
	@Autowired
	private DonThanksService donThanksService;
		
	/**
	 * 获取捐赠致谢列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param donThanks
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<DonThanks> list(int pageNo,int pageSize,DonThanks donThanks) {
		PageIo<DonThanks> pageInfo =  donThanksService.loadByPage(pageNo,pageSize,donThanks);							
		return pageInfo;
	}


	/**
	 * 增加或者更新捐赠致谢
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param donThanks
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated DonThanks donThanks){
		donThanksService.save(donThanks);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除捐赠致谢（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param donThanks
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(DonThanks donThanks) {
		donThanksService.delete(donThanks);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取捐赠致谢
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param donThanks
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<DonThanks> get(DonThanks donThanks) {
		return new RestResponse<DonThanks>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				donThanksService.get(donThanks));	
	}
		
}