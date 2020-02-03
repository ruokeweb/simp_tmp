package com.mpri.aio.settings.controller;

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
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.settings.service.SettingSubjectService;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;

/**
 *  
 * @Description:  学科管理——Controller
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 19 16:23:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/settings/settingSubject")
public class SettingSubjectController extends BaseController {
	
	@Autowired
	private SettingSubjectService settingSubjectService;
	
	@Autowired 
	private RedisCacheService redisCacheService;
	/**
	 * 获取学科
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public ResJson<SettingSubject> tree(SettingSubject settingSubject) {
		List<SettingSubject> list = settingSubjectService.loadAllListBy(settingSubject);
		ResJson<SettingSubject> rj = new ResJson<SettingSubject>();
		rj.setData(list);
		return rj;
	}
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public List<SettingSubject> loadAllListBy(SettingSubject settingSubject) {
		List<SettingSubject> list = settingSubjectService.loadAllListBy(settingSubject);
		return list;
	}

	/**
	 * 获取学科管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param settingSubject
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingSubject> list(int pageNo,int pageSize,SettingSubject settingSubject) {
		PageIo<SettingSubject> pageInfo =  settingSubjectService.loadByPage(pageNo,pageSize,settingSubject);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新学科管理
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param settingSubject
	* @return
	 */
	@Logs(value = "学科管理修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingSubject settingSubject){
        SettingSubject subject = new SettingSubject();
        subject.setId(settingSubject.getParentId());
        subject = settingSubjectService.get(subject);
        settingSubject.setParentIds(subject.getParentIds()+","+subject.getId());
        settingSubjectService.save(settingSubject);
        //更新初始化缓存
        redisCacheService.putCache(settingSubjectService, new SettingSubject() ,InitCache.SETTING_BASE_SUBJECT,InitCache.SETTING_SUBJECT);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}	

	/**
	 * 删除学科管理（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param settingSubject
	* @return
	 */
	@Logs(value = "学科管理删除",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingSubject settingSubject) {
		settingSubjectService.delete(settingSubject);
        //更新初始化缓存
        redisCacheService.putCache(settingSubjectService, new SettingSubject() ,InitCache.SETTING_BASE_SUBJECT,InitCache.SETTING_SUBJECT);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	/**
	 * 删除学科管理（逻辑删除）--根据父节点id删除自己和自己所有的子节点
	 * <p>Title: delete</p>
	 * <p>Description: </p>
	 * @param settingSubject
	 * @return
	 */
	@Logs(value = "学科管理删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/deleteAllChildrenByParentId")
	public RestResponse<String> deleteAllChildrenByParentId(SettingSubject settingSubject) {
		//删除

		settingSubjectService.deleteAllChildrenByParentId(settingSubject);

		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	/**
	 * 获取学科管理
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param settingSubject
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingSubject> get(SettingSubject settingSubject) {
        SettingSubject resStSb = settingSubjectService.get(settingSubject);
        SettingSubject parentStSb = new SettingSubject();
        parentStSb.setId(resStSb.getParentId());
        resStSb.setParentStSb(settingSubjectService.get(parentStSb));
		return new RestResponse<SettingSubject>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                resStSb);
	}
		
}