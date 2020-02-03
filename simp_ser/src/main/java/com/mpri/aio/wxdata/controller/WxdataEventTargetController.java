package com.mpri.aio.wxdata.controller;

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
import com.mpri.aio.wxdata.model.WxdataEventTarget;
import com.mpri.aio.wxdata.service.WxdataEventTargetService;

 /**   
 *  
 * @Description:  自定义事件属性表——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Jul 18 17:15:51 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/wxdata/wxdataEventTarget")
public class WxdataEventTargetController extends BaseController {
	
	@Autowired
	private WxdataEventTargetService wxdataEventTargetService;
		
	/**
	 * 获取自定义事件属性表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param wxdataEventTarget
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<WxdataEventTarget> list(int pageNo,int pageSize,WxdataEventTarget wxdataEventTarget) {
		PageIo<WxdataEventTarget> pageInfo =  wxdataEventTargetService.loadByPage(pageNo,pageSize,wxdataEventTarget);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新自定义事件属性表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param wxdataEventTarget
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated WxdataEventTarget wxdataEventTarget){
		wxdataEventTargetService.save(wxdataEventTarget);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除自定义事件属性表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param wxdataEventTarget
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(WxdataEventTarget wxdataEventTarget) {
		wxdataEventTargetService.delete(wxdataEventTarget);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取自定义事件属性表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param wxdataEventTarget
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<WxdataEventTarget> get(WxdataEventTarget wxdataEventTarget) {
		return new RestResponse<WxdataEventTarget>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				wxdataEventTargetService.get(wxdataEventTarget));	
	}
		
}