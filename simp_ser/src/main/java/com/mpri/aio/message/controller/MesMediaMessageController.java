package com.mpri.aio.message.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.message.model.MesMediaMessage;
import com.mpri.aio.message.service.MesMediaMessageService;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

 /**   
 *  
 * @Description:  每日多媒体通知——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Nov 05 14:18:55 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@RestController
@RequestMapping("/mes/mediaMessage")
public class MesMediaMessageController extends BaseController {
	
	@Autowired
	private MesMediaMessageService mesMediaMessageService;
	
	@Autowired
	private FileService fileService;
	
		
	/**
	 * 获取每日多媒体通知列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesMediaMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesMediaMessage> list(int pageNo,int pageSize,MesMediaMessage mesMediaMessage) {
		PageIo<MesMediaMessage> pageInfo =  mesMediaMessageService.loadByPage(pageNo,pageSize,mesMediaMessage);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新每日多媒体通知
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesMediaMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(MesMediaMessage mesMediaMessage){
		String status = mesMediaMessageService.judgeDate(mesMediaMessage);
		mesMediaMessage.setStatus(status);
		mesMediaMessageService.save(mesMediaMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除每日多媒体通知（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesMediaMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesMediaMessage mesMediaMessage) {
		mesMediaMessageService.delete(mesMediaMessage);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取每日多媒体通知
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesMediaMessage
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesMediaMessage> get(MesMediaMessage mesMediaMessage) {
		MesMediaMessage mediaMessage = mesMediaMessageService.get(mesMediaMessage);
		if(null !=mediaMessage && StringUtil.isNotEmpty(mediaMessage.getAppendix())) {
			Optional<File> file = fileService.getFileById(mediaMessage.getAppendix());
			if(null != file) {
				mediaMessage.setAppendixName(file.get().getName());
			}
		}
		return new RestResponse<MesMediaMessage>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mediaMessage);	
	}
		
}