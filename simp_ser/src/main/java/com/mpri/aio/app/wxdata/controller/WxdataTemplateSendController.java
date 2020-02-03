package com.mpri.aio.app.wxdata.controller;

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
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.app.wxdata.service.WxdataTemplateSendService;

 /**   
 *  
 * @Description:  模板消息——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Fri Jul 19 14:05:13 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/wxdata/wxdataTemplateSend")
public class WxdataTemplateSendController extends BaseController {
	
	@Autowired
	private WxdataTemplateSendService wxdataTemplateSendService;
		
	/**
	 * 获取模板消息列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param wxdataTemplateSend
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<WxdataTemplateSend> list(int pageNo,int pageSize,WxdataTemplateSend wxdataTemplateSend) {
		PageIo<WxdataTemplateSend> pageInfo =  wxdataTemplateSendService.loadByPage(pageNo,pageSize,wxdataTemplateSend);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新模板消息
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param wxdataTemplateSend
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated WxdataTemplateSend wxdataTemplateSend){
		wxdataTemplateSendService.save(wxdataTemplateSend);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除模板消息（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param wxdataTemplateSend
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(WxdataTemplateSend wxdataTemplateSend) {
		wxdataTemplateSendService.delete(wxdataTemplateSend);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取模板消息
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param wxdataTemplateSend
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<WxdataTemplateSend> get(WxdataTemplateSend wxdataTemplateSend) {
		return new RestResponse<WxdataTemplateSend>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				wxdataTemplateSendService.get(wxdataTemplateSend));	
	}
	 /**
	  * 获取模板消息
	  * <p>Title: get</p>
	  * <p>Description: </p>
	  * @param wxdataTemplateSend
	  * @return
	  */
	 @CrossOrigin
	 @PostMapping(value = "/getOne")
	 public RestResponse<WxdataTemplateSend> getOne(WxdataTemplateSend wxdataTemplateSend) {
		 return new RestResponse<WxdataTemplateSend>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				 wxdataTemplateSendService.getOne(wxdataTemplateSend));
	 }
}