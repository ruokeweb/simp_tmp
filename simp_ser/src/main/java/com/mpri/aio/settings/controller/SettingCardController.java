package com.mpri.aio.settings.controller;

import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.settings.model.SettingCard;
import com.mpri.aio.settings.service.SettingCardService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *  
 * @Description:  校友卡管理——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Feb 15 10:15:16 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/settingCard")
public class SettingCardController extends BaseController {
	
	@Autowired
	private SettingCardService settingCardService;

	@Value("${file.staticAccessPath}")
	private String staticAccessPath;

	@Value("${file.uploadFolder}")
	private String uploadFolder;
	/**
	 * 获取校友卡管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingCard> list(int pageNo,int pageSize,SettingCard settingCard) {
		PageIo<SettingCard> pageInfo =  settingCardService.loadByPage(pageNo,pageSize,settingCard);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友卡管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingCard
	* @return
	 */
	@Logs(value = "校友卡修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingCard settingCard){
		SettingCard save = settingCardService.save(settingCard);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", save.getId());
	}	
	
	/**
	 * 删除校友卡管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingCard
	* @return
	 */
	@Logs(value = "校友卡删除",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingCard settingCard) {
		settingCardService.delete(settingCard);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友卡管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingCard
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingCard> get(SettingCard settingCard) {
		return new RestResponse<SettingCard>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				settingCardService.get(settingCard));	
	}

	 /**
	  * 文件图上传----现在不使用这种上传，使用mongo
	  * <p>Title: uploadImg</p>
	  * <p>Description: </p>
	  * @param file
	  * @param request
	  * @return
	  */
	 @CrossOrigin
	 @PostMapping(value = "/uploadimg")
	 public RestResponse<String> uploadImg(@RequestParam("file") MultipartFile file,
										   HttpServletRequest request) {
		 String fileName = file.getOriginalFilename();
		 String newFilName = String.valueOf(new Date().getTime())+"."+fileName.substring(fileName.lastIndexOf(".") + 1); /*更改文件名*/
		 String resfillPath  = "stCard/"+ DateUtils.getDate();
		 String filePath = uploadFolder +resfillPath+"/";
		 try {
			 FileUtils.uploadFile(file.getBytes(), filePath, newFilName);
			 return RestResponse.getInstance(200, "上传成功", staticAccessPath.replaceAll("\\*", "")+resfillPath +"/"+newFilName);
		 } catch (Exception e) {
			 // TODO: handle exception
		 }
		 return RestResponse.getInstance(-1, "上传失败", resfillPath+fileName);
	 }
		
}