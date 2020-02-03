 package com.mpri.aio.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.message.model.MesGroupUser;
import com.mpri.aio.message.service.MesGroupUserService;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

 /**   
 *  
 * @Description:  信息组用户关系表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:28:51 CST 2018
 * @Version:      v_1.02
 *    
 */
@RestController
@RequestMapping("/mes/mesGroupUser")
public class MesGroupUserController extends BaseController {
	
	@Autowired
	private MesGroupUserService mesGroupUserService;
		
	/**
	 * 获取信息组用户关系表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesGroupUser
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<MesGroupUser> list(int pageNo,int pageSize,MesGroupUser mesGroupUser) {
		PageIo<MesGroupUser> pageInfo =  mesGroupUserService.loadByPage(pageNo,pageSize,mesGroupUser);							
		return pageInfo;
	}
	
	
	/**
	 * 获取信息组用户关系表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesGroupUser
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/listAll")
	public RestResponse<List<MesGroupUser>> listAll(MesGroupUser mesGroupUser) {
		List<MesGroupUser> list =  mesGroupUserService.loadAllListBy(mesGroupUser);
		return new RestResponse<List<MesGroupUser>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", list);		
	}
	
	/**
	 * 获取信息组用户关系表（校友）列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param mesGroupUser
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadSmByPage")
	public PageInfo<SmSchoolmate> loadSmByPage(int pageNo,int pageSize,SmSchoolmate smSchoolmate) {
		PageIo<SmSchoolmate> pageInfo =  mesGroupUserService.loadSmByPage(pageNo,pageSize,smSchoolmate);							
		return pageInfo;
	}
	
	/**
	 * 增加或者更新信息组用户关系表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param mesGroupUser
	* @return
	 */
	@Logs(value = "信息组用户关系编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated MesGroupUser mesGroupUser){
		mesGroupUserService.save(mesGroupUser);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息组用户关系表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param mesGroupUser
	* @return
	 */
	@Logs(value = "信息组用户关系删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(MesGroupUser mesGroupUser) {
		mesGroupUserService.delete(mesGroupUser);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息组用户关系表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param mesGroupUser
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<MesGroupUser> get(MesGroupUser mesGroupUser) {
		return new RestResponse<MesGroupUser>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				mesGroupUserService.get(mesGroupUser));	
	}
		
}