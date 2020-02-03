package com.mpri.aio.schoolmate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.service.SmEducationService;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.settings.service.SettingSubjectService;

import tk.mybatis.mapper.util.StringUtil;

 /**   
 *  
 * @Description:  校友卡教育经历——Controller
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Feb 19 15:45:55 CST 2019
 * @Version:      v_2.0
 *    
 */
@RestController
@RequestMapping("/sm/smEducation")
public class SmEducationController extends BaseController {
	
	@Autowired
	private SmEducationService smEducationService;
	
	@Autowired
	private SettingSubjectService settingSubjectService;
	/**
	 * 获取校友卡教育经历列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param smEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmEducation> list(int pageNo,int pageSize,SmEducation smEducation) {
		PageIo<SmEducation> pageInfo =  smEducationService.loadByPage(pageNo,pageSize,smEducation);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新校友卡教育经历
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param smEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmEducation> save(SmEducation smEducation){
//		smEducationService.saveDepartment(smEducation);
		smEducationService.saveDepartByLevel(smEducation);
		return new RestResponse<SmEducation>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smEducation);							
	}	
	
	/**
	 * 删除校友卡教育经历（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param smEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmEducation smEducation) {
		smEducationService.delete(smEducation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取校友卡教育经历
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param smEducation
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmEducation> get(SmEducation smEducation) {
		return new RestResponse<SmEducation>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smEducationService.get(smEducation));	
	}
	
	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmEducation>> loadAllListBy(SmEducation smEducation) {
        List<SmEducation> smEducations = smEducationService.loadAllListBy(smEducation);
        for(SmEducation edu : smEducations) {
    		if(StringUtil.isNotEmpty(edu.getDegreeType())) {
    			SettingSubject subject = new SettingSubject();
    			subject.setId(edu.getDegreeType());
    			SettingSubject res = settingSubjectService.get(subject);
    			if(res != null) {
    				edu.setSubjectName(res.getName());
    			}
    		}	
        }
//        smEducations = smEducationService.setAllOrgId(smEducations);
        return new RestResponse<List<SmEducation>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smEducations);
	}
	
    /**
     * 设置默认教育经历
    * <p>Title: setDefEdu</p>  
    * <p>Description: </p>  
    * @param smEducation
    * @return
     */
    @CrossOrigin
    @PostMapping(value = "/setDefEdu")
    public RestResponse<String> setDefEdu(SmEducation smEducation){
    	smEducationService.setDefault(smEducation);
    	return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "设置成功！", "");
    }

}