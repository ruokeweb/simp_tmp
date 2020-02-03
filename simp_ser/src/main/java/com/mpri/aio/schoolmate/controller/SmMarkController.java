package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.service.SmMarkService;
import com.mpri.aio.system.common.GlobalStr;

 /**   
 *  
 * @Description:  校友标签管理——Controller
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Fri Aug 24 11:05:42 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/sm/smMark")
public class SmMarkController extends BaseController {
	
	@Autowired
	private SmMarkService smMarkService;
		
	/**
	 * 获取校友标签管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smMark
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public ResJson<SmMark> list(int pageNo,int pageSize,SmMark smMark) {
		ResJson<SmMark>rj = new ResJson<SmMark>();
		List<SmMark> list =  smMarkService.loadAllListBy(smMark);	
		rj.setData(list);
		return rj;
	}

	 /**
	  * 获取校友标签管理列表
	  * @param smMark
	  * @return
	  */
	 @CrossOrigin
	 @PostMapping(value = "/schoolMarkList")
	 public ResJson<SmMark> schoolMarkList(SmMark smMark) {
		 ResJson<SmMark>rj = new ResJson<SmMark>();
		 List<SmMark> list =  smMarkService.loadAllListBy(smMark);
		 rj.setData(list);
		 return rj;
	 }
	
	/**
	 * 获取校友标签管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smMark 
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public List<SmMark> tree(SmMark smMark) {
//		smMark.setUseable(GlobalStr.ABLE);
		List<SmMark> list =  smMarkService.loadAllListBy(smMark);	
		return list;
	}
	
	/**
	 * 增加或者更新校友标签管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smMark
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(SmMark smMark){
		if(StringUtil.isEmpty(smMark.getId())) {
			smMark.setUseable(GlobalStr.ABLE);
		}
		SmMark parentSmMark = new SmMark();
		parentSmMark.setId(smMark.getParentId());
		parentSmMark = smMarkService.get(parentSmMark);
		smMark.setParentIds(parentSmMark.getParentIds()+","+parentSmMark.getId());
		smMarkService.save(smMark);
		
		if(StringUtil.isNotEmpty(smMark.getId())) {
			smMarkService.updateMark(smMark);
		}
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	
	
	/**
	 * 删除校友标签管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smMark
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmMark smMark) {
		
		if(smMark.getId().equalsIgnoreCase(GlobalStr.MENU_ROOT_ID)) {
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "标签根节点不允许删除！", "");
		}else {
			Boolean flag =smMarkService.deleteMark(smMark);
			if(flag) {
				return new RestResponse<String>(ExceptionResult.DATA_USED, "此标签或者子标签已被使用，无法删除！", "");	
			}
		}
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友标签管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smMark
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmMark> get(SmMark smMark) {
		return new RestResponse<SmMark>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smMarkService.get(smMark));
	}
	
	
	/**
	 * 更改标签zhuangtai
	* <p>Title: updateUnable</p>  
	* <p>Description: </p>  
	* @param smMark
	 */
	@CrossOrigin
	@PostMapping(value = "/updateUnable")
	public RestResponse<String> updateUnable(SmMark smMark) {
		if(GlobalStr.UNABLE.equalsIgnoreCase(smMark.getUseable())) {
			smMark.setUseable(GlobalStr.ABLE);
		}else {
			smMark.setUseable(GlobalStr.UNABLE);
		}
		smMarkService.updateUnable(smMark);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
}