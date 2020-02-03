package com.mpri.aio.donation.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.service.DonProjectService;
import com.mpri.aio.donation.task.UpdateDonProQuartz;

 /**   
 *  
 * @Description:  捐赠项目管理——Controller
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Wed Aug 29 15:22:22 CST 2018
 * @Version:      v_1.0
 *    
 */
@RestController
@RequestMapping("/don/donProject")
public class DonProjectController extends BaseController {
	
	@Autowired
	private DonProjectService donProjectService;
	
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	
	private static String DONFILE  = "donfile";
		
	/**
	 * 获取捐赠项目管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param donProject
	* @return PageInfo<DonProject>
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<DonProject> list(int pageNo,int pageSize,DonProject donProject) {
		PageIo<DonProject> pageInfo =  donProjectService.loadByPage(pageNo,pageSize,donProject);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新捐赠项目管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param donProject
	* @return RestResponse<String>
	 */
	@Logs(value = "捐赠项目编辑",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(DonProject donProject){

		DonProject donProject1 = new DonProject();
		donProject1.setName(donProject.getName());
		List<DonProject> list =donProjectService.loadAllListBy(donProject1);
		if((null ==donProject.getId() ||"".equals(donProject.getId()) ) && null !=list && list.size()>0)
		{
			return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在此项目！","");
		}
		String status = UpdateDonProQuartz.judgeDate(donProject);
		donProject.setStatus(status);
		donProjectService.save(donProject);
//		donDonationFileService.deleteByProject(donProject);
//		if(files.length()>0)
//		{
//			donDonationFileService.saveProjectFiles(donProject.getId(), files.split(";"));
//		}
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！","");
	}	
	
	/**
	 * 删除捐赠项目管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param donProject
	* @return RestResponse<String>
	 */
	@Logs(value = "捐赠项目删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(DonProject donProject) {
		donProjectService.delete(donProject);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取捐赠项目
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param donProject
	* @return RestResponse<DonProject>
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<DonProject> get(DonProject donProject) {
		return new RestResponse<DonProject>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", donProjectService.get(donProject));
	}
	
	
	/**
	 * 文件图上传     
	* <p>Title: uploadImg</p>  
	* <p>Description: </p>  
	* @param file
	* @param request
	* @return RestResponse<String>
	 */
    @CrossOrigin
    @PostMapping(value = "/uploadimg")
    public RestResponse<String> uploadImg(@RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        String newFilName = String.valueOf(new Date().getTime())+"."+fileName.substring(fileName.lastIndexOf(".") + 1); /*更改文件名*/
        String resfillPath  = DONFILE+"/"+DateUtils.getDate();      
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