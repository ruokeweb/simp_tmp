package com.mpri.aio.settings.controller;

import java.util.List;
import java.util.Map;

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
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.service.SettingDepartmentService;
import com.mpri.aio.settings.vo.DepartmentMerge;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;

/**
 *
 * @Description:  院系专业——Controller
 * @Author:       zdl
 * @project 	  smmp
 * @CreateDate:   Thu Jan 31 11:33:46 CST 2019
 * @Version:      v_1.2
 *
 */
@RestController
@RequestMapping("/settings/settingDepartment")
public class SettingDepartmentController extends BaseController {

	@Autowired
	private SettingDepartmentService settingDepartmentService;
	@Autowired
	private RedisCacheService redisCacheService;
	
	private Map<String,SettingDepartment> departBaseCache;
	private void InitMaps() {
		departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
	}
	/**
	 * 获取院系专业
	 * <p>Title: list</p>
	 * <p>Description: </p>
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public ResJson<SettingDepartment> tree(SettingDepartment settingDepartment) {
		List<SettingDepartment> list = settingDepartmentService.loadAllListBy(settingDepartment);
		ResJson<SettingDepartment> rj = new ResJson<SettingDepartment>();
		rj.setData(list);
		return rj;
	}

	/**
	 * 获取院系专业列表
	* <p>Title: list</p>
	* <p>Description: </p>
	* @param pageNo
	* @param pageSize
	* @param settingDepartment
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SettingDepartment> list(int pageNo,int pageSize,SettingDepartment settingDepartment) {
		PageIo<SettingDepartment> pageInfo =  settingDepartmentService.loadByPage(pageNo,pageSize,settingDepartment);
		return pageInfo;
	}

	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")	
	public RestResponse<List<SettingDepartment>> loadAllListBy(SettingDepartment settingDepartment){
		return new RestResponse<List<SettingDepartment>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", settingDepartmentService.loadAllListBy(settingDepartment));
	}
	
	/**
	 * 增加或者更新院系专业
	* <p>Title: add</p>
	* <p>Description: </p>
	* @param settingDepartment
	* @return
	 */
	@Logs(value = "院系专业修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(@Validated SettingDepartment settingDepartment){
		SettingDepartment settingDepartment1 = new SettingDepartment();
		settingDepartment1.setId(settingDepartment.getParentId());
		settingDepartment1 = settingDepartmentService.get(settingDepartment1);
		settingDepartment.setParentIds(settingDepartment1.getParentIds()+","+settingDepartment1.getId());
		settingDepartmentService.save(settingDepartment);
		//院系专业数据缓存
		redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,InitCache.DEPART_BASE_CACHE,InitCache.DEPART_CACHE);		
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}

	/**
	 * 删除院系专业（逻辑删除）
	* <p>Title: delete</p>
	* <p>Description: </p>
	* @param settingDepartment
	* @return
	 */
	@Logs(value = "院系专业删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SettingDepartment settingDepartment) {
		//删除

		settingDepartmentService.delete(settingDepartment);
		//院系专业数据缓存
		redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,InitCache.DEPART_BASE_CACHE,InitCache.DEPART_CACHE);		
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	/**
	 * 删除院系专业（逻辑删除）--根据父节点id删除自己和自己所有的子节点
	* <p>Title: delete</p>
	* <p>Description: </p>
	* @param settingDepartment
	* @return
	 */
	@Logs(value = "院系专业删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/deleteAllChildrenByParentId")
	public RestResponse<String> deleteAllChildrenByParentId(SettingDepartment settingDepartment) {
		//删除

		settingDepartmentService.deleteAllChildrenByParentId(settingDepartment);
		//院系专业数据缓存
		redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,InitCache.DEPART_BASE_CACHE,InitCache.DEPART_CACHE);		
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}


	/**
	 * 获取院系专业
	* <p>Title: get</p>
	* <p>Description: </p>
	* @param settingDepartment
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SettingDepartment> get(SettingDepartment settingDepartment) {
        SettingDepartment resStDt = settingDepartmentService.get(settingDepartment);
        SettingDepartment parentStDt = new SettingDepartment();
        parentStDt.setId(resStDt.getParentId());
        resStDt.setParentStDt(settingDepartmentService.get(parentStDt));
		return new RestResponse<SettingDepartment>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
                resStDt);
	}
	/**
	 *     获取分组的临时系
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param departmentMerge
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllTempSeries")
	public PageInfo<DepartmentMerge> loadAllTempSeries(int pageNo,int pageSize,DepartmentMerge departmentMerge) {
		return settingDepartmentService.loadAllTempSeries(pageNo,pageSize,departmentMerge);
	}
	/**
	 *     获取分组的临时专业
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param departmentMerge
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/loadAllTempSpecialty")
	public PageInfo<DepartmentMerge> loadAllTempSpecialty(int pageNo,int pageSize,DepartmentMerge departmentMerge) {
		this.InitMaps();
		PageIo<DepartmentMerge> pageInfo =  settingDepartmentService.loadAllTempSpecialty(pageNo,pageSize,departmentMerge);
		List<DepartmentMerge> list=pageInfo.getList();
		if(list!=null&&!list.isEmpty()){
			for (DepartmentMerge dep:list) {

				dep.setSchoolName((null!=departBaseCache.get(dep.getSchoolId()))?departBaseCache.get(dep.getSchoolId()).getName():null);
				dep.setCollegeName((null!=departBaseCache.get(dep.getCollegeId()))?departBaseCache.get(dep.getCollegeId()).getName():null);
				dep.setSeriesName((null!=departBaseCache.get(dep.getSeriesId()))?departBaseCache.get(dep.getSeriesId()).getName():null);
			}

		}
		pageInfo.setList(list);
		return pageInfo;
	}
	/**
	 *     合并系
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param departmentId (合并目标系的id)
	 * @param departmentIds (合并目标系的父ID组)
	 * @param departmentMerge (需要合并的临时系信息)
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/mergeSeries")
	public RestResponse<String> mergeSeries(String departmentId ,String departmentIds,DepartmentMerge departmentMerge){
		String[] split = departmentIds.split(",");
		if(split.length==4){
			String series =departmentId;
			String school =split[2];
			String college=split[3];
			settingDepartmentService.mergeSeries(school,college,series ,departmentMerge);
			//院系专业数据缓存
			redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,InitCache.DEPART_BASE_CACHE,InitCache.DEPART_CACHE);		
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "合并成功！", "");
		}else {
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "合并失败！", "");
		}
	}
	/**
	 *     合并专业
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 * @param departmentId (合并目标专业的id)
	 * @param departmentIds (合并目标专业的父ID组)
	 * @param departmentMerge (需要合并的临时专业信息)
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/mergeSpecialty")
	public RestResponse<String> mergeSpecialty(String departmentId ,  String departmentIds, DepartmentMerge departmentMerge){
		String[] split = departmentIds.split(",");
		if(split.length==5){
			String specialty =departmentId;
			String school =split[2];
			String college=split[3];
			String series=split[4];
			settingDepartmentService.mergeSpecialty(school,college,series ,specialty,departmentMerge);
			//院系专业数据缓存
			redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,InitCache.DEPART_BASE_CACHE,InitCache.DEPART_CACHE);		
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "合并成功！", "");
		}else {
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "合并失败！", "");
		}
	}


}