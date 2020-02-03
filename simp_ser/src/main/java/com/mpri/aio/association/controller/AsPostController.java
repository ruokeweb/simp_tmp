package com.mpri.aio.association.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.association.model.AsPost;
import com.mpri.aio.association.service.AsPostService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;

/**
 *  
 * @Description:  校友会任职信息——Controller
 * @Author:       lzq
 * @project 	  smmp 
 * @CreateDate:   Thu Feb 21 13:26:24 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/as/asPost")
public class AsPostController extends BaseController {
	
	@Autowired
	private AsPostService asPostService;
		
	/**
	 * 获取校友会任职信息列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param asPost
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<AsPost> list(int pageNo,int pageSize,AsPost asPost) {
		PageIo<AsPost> pageInfo =  asPostService.loadByPage(pageNo,pageSize,asPost);
		return pageInfo;
	}
	
	/**
			* 获取校友会任职信息列表
	* <p>Title: list</p>
			 * <p>Description: </p>
			 * @param pageNo
	* @param pageSize
	* @param asPost
	* @return
			*/
	 @CrossOrigin
	 @PostMapping(value = "/listschool")
	 public PageInfo<AsPost> listschool(int pageNo,int pageSize,AsPost asPost) {
	 	if(null  ==asPost){
			asPost = new AsPost();
		}
//	 	if(null  ==asPost.getAssociationId() || "root".equals(asPost.getAssociationId().toLowerCase()) || "ROOT".equals(asPost.getAssociationId())){
//			asPost.setAssociationId("");
//		}

		 PageIo<AsPost> pageInfo =  asPostService.loadSchoolByPage(pageNo,pageSize,asPost);
		 return pageInfo;
	 }
	/**
	 * 增加或者更新校友会任职信息
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param asPost
	* @return
	 */
	@Logs(value = "校友会任职编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(AsPost asPost){
//		AsPost asPostsc = new AsPost();
//		asPostsc.setAssociationId(asPost.getAssociationId());
//		asPostsc.setUserId(asPost.getUserId());
//		List list = asPostService.loadAllListBy(asPostsc);
//		if((null== asPost.getId() || "".equals(asPost.getId()) )&& null!=list && list.size()>0)
//		{
//			return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在对应关系！", "");
//		}
		asPostService.save(asPost);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除校友会任职信息（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param asPost
	* @return
	 */
	@Logs(value = "校友会任职删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(AsPost asPost) {
		asPostService.delete(asPost);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友会任职信息
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param asPost
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<AsPost> get(AsPost asPost) {
		return new RestResponse<AsPost>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				asPostService.get(asPost));	
	}
		
}