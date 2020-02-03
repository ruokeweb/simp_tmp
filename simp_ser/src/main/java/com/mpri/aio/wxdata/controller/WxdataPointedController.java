package com.mpri.aio.wxdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.wxdata.model.WxdataPointed;
import com.mpri.aio.wxdata.service.WxdataPointedService;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 *  
 * @Description:  小程序埋点数据表——Controller
 * @Author:       zdl
 * @project 	  simp 
 * @CreateDate:   Thu Jul 18 17:08:03 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/wxdata/wxdataPointed")
public class WxdataPointedController extends BaseController {
	
	@Autowired
	private WxdataPointedService wxdataPointedService;
		
	/**
	 * 获取小程序埋点数据表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param wxdataPointed
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<WxdataPointed> list(int pageNo,int pageSize,WxdataPointed wxdataPointed) {
		PageIo<WxdataPointed> pageInfo =  wxdataPointedService.loadByPage(pageNo,pageSize,wxdataPointed);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新小程序埋点数据表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param wxdataPointed
	* @return
	 */
	@CrossOrigin
	@GetMapping(value = "/save")
	public RestResponse<String> save( HttpServletRequest request){
		String data = request.getParameter("data");
		String decode = decode(data);
		wxdataPointedService.save(decode);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除小程序埋点数据表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param wxdataPointed
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(WxdataPointed wxdataPointed) {
		wxdataPointedService.delete(wxdataPointed);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取小程序埋点数据表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param wxdataPointed
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<WxdataPointed> get(WxdataPointed wxdataPointed) {
		return new RestResponse<WxdataPointed>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				wxdataPointedService.get(wxdataPointed));	
	}
	/**
	 * base64解码
	 * @param base64Str
	 * @return
	 */
	public static String decode(String base64Str){
		String str="";
		byte[] base64Data = Base64.getDecoder().decode(base64Str);
		try {
			str = new String(base64Data,"utf-8");
		}catch (Exception e){
		}
		return str;
	}
		
}