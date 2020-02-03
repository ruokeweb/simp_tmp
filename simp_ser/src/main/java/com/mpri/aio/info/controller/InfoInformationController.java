package com.mpri.aio.info.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.FileUtils;
import com.mpri.aio.info.model.InfoInformation;
import com.mpri.aio.info.service.InfoInformationService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.UserInfoUtil;

 /**   
 *  
 * @Description:  信息详情表——Controller
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Wed Dec 12 09:24:19 CST 2018
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/info/infoInformation")
public class InfoInformationController extends BaseController {
	
	@Autowired
	private InfoInformationService infoInformationService;
	
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	    
	@Value("${file.uploadFolder}")
	private String uploadFolder;
		
	/**
	 * 获取信息详情表列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param infoInformation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<InfoInformation> list(int pageNo,int pageSize,InfoInformation infoInformation) {
		PageIo<InfoInformation> pageInfo =  infoInformationService.loadByPage(pageNo,pageSize,infoInformation);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新信息详情表
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param infoInformation
	* @return
	 */
	@Logs(value = "信息详情编辑",type ="UPDATE")	
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(InfoInformation infoInformation,HttpServletRequest request){
		String userName = UserInfoUtil.getUsername(request);
		//获取发布人
		if(GlobalStr.INFO_PUB.equalsIgnoreCase(infoInformation.getPubStatus())) {
			infoInformation.setPubUser(userName);
			infoInformation.setPubTime(new Date());
		}
		infoInformationService.save(infoInformation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除信息详情表（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param infoInformation
	* @return
	 */
	@Logs(value = "信息详情删除",type ="DELETE")	
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(InfoInformation infoInformation) {
		infoInformationService.delete(infoInformation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取信息详情表
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param infoInformation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<InfoInformation> get(InfoInformation infoInformation) {
		return new RestResponse<InfoInformation>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				infoInformationService.get(infoInformation));	
	}
	
	
	/**
	 * 上传信息图片
	* <p>Title: loadImage</p>  
	* <p>Description: </p>  
	* @param file
	* @param request
	* @return
	 */
    @CrossOrigin
    @PostMapping(value = "/loadImage")
    public RestResponse<String> loadImage(@RequestParam("upload") MultipartFile file,
            HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String newFilName = String.valueOf(new Date().getTime())+"."+fileName.substring(fileName.lastIndexOf(".") + 1); /*更改文件名*/
        String resfillPath  = "info" + "/"+ DateUtils.getDate();
        
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