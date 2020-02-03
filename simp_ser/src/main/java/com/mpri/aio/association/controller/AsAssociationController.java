package com.mpri.aio.association.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.page.ResJson;
import com.mpri.aio.common.response.RestResponse;

/**
 *
 * @Description: 校友会——Controller
 * @Author: syp
 * @project sm
 * @CreateDate: Thu Jan 24 16:49:29 CST 2019
 * @Version: v_1.0
 *
 */
@RestController
@RequestMapping("/as/asAssociation")
public class AsAssociationController extends BaseController {

	@Autowired
	private AsAssociationService asAssociationService;

	/**
	 * 根据管理员ID获取该管理员所管理的校友会
	 */
	@CrossOrigin
	@PostMapping(value = "/findAsByUser")
	public ResJson<AsAssociation> findAsByUser(HttpServletRequest request) {
		ResJson<AsAssociation> rj = new ResJson<AsAssociation>();
		List<AsAssociation> list = asAssociationService.findAsByUser(request);
		rj.setData(list);
		return rj;
	}
	
	
	
	/**
	 * 根据管理员ID获取该管理员所管理的校友会
	 */
	@CrossOrigin
	@PostMapping(value = "/findAsListByUser")
	public RestResponse<List<AsAssociation>> findAsListByUser(HttpServletRequest request) {
		List<AsAssociation> list = asAssociationService.findAsByUser(request);
		return new RestResponse<List<AsAssociation>>(ExceptionResult.REQUEST_SUCCESS, "获取校友会列表成功", list);
	}	
	
	
	
	/**
	 * 获取所有的校友会
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/tree")
	public ResJson<AsAssociation> tree(AsAssociation asAssociation, HttpServletRequest request) {
		ResJson<AsAssociation> rj = new ResJson<AsAssociation>();
		List<AsAssociation> list = asAssociationService.loadAllListBy(asAssociation);
		rj.setData(list);
		return rj;
	}

	
	/**
	 * 获取所有的校友会	
	 */	
	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<AsAssociation>> loadAllListBy(AsAssociation asAssociation) {
		List<AsAssociation> list = asAssociationService.loadAllListBy(asAssociation);
		return new RestResponse<List<AsAssociation>>(ExceptionResult.REQUEST_SUCCESS, "获取校友会列表成功", list);
	}

	
	/**
	 * 分页加载校友会类列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param asAssociation
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageIo<AsAssociation> list(int pageNo, int pageSize, AsAssociation asAssociation) {
		PageIo<AsAssociation> pageInfo = asAssociationService.loadByPage(pageNo, pageSize, asAssociation);
		return pageInfo;
	}

	/**
	 * 增加或者更新校友会
	 * <p>
	 * Title: add
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@Logs(value = "校友会修改", type = "UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(AsAssociation asAssociation) {
		AsAssociation parentAsAssociation = new AsAssociation();
		parentAsAssociation.setName(asAssociation.getName());
		List<AsAssociation> list = asAssociationService.loadAllListBy(asAssociation);

		if (StringUtil.isEmpty( asAssociation.getId()) && null != asAssociation.getId()
				&& null != list && list.size() > 0) {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在此校友会信息！", "");
		}

		parentAsAssociation.setId(asAssociation.getParentId());
		parentAsAssociation = asAssociationService.get(parentAsAssociation);
		asAssociation.setParentIds(parentAsAssociation.getParentIds() + "," + parentAsAssociation.getId());
		asAssociationService.save(asAssociation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
	}

	/**
	 * 删除
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@Logs(value = "校友会删除", type = "DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(AsAssociation asAssociation) {
		int count = asAssociationService.loadChildsListBy(asAssociation);
		if(count>0){
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "该校友会有子级，删除失败！", "");
		}

		asAssociationService.delete(asAssociation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");

	}

	/**
	 * 获取校友会信息
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<AsAssociation> get(AsAssociation asAssociation) {
		AsAssociation resAsAssociation = asAssociationService.get(asAssociation);
		AsAssociation parentAsAssociation = new AsAssociation();
		parentAsAssociation.setId(resAsAssociation.getParentId());
		resAsAssociation.setParentAsAssociation(asAssociationService.get(parentAsAssociation));
		return new RestResponse<AsAssociation>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", resAsAssociation);
	}

	/**
	 * 添加校友会成员
	 * 
	 * @param sysUserAsso
	 */
	@CrossOrigin
	@PostMapping(value = "/addSysAs")
	public RestResponse<String> addSysAs(SysUserAsso sysUserAsso) {
		if (asAssociationService.selectSysAs(sysUserAsso)) {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在对应关系！", "");
		}
		asAssociationService.addSysAs(sysUserAsso);

		// 添加校友和校友会关系后，校友会成员加一
		AsAssociation asAssociation = new AsAssociation();
		asAssociation.setId(sysUserAsso.getAsId());
		asAssociation = asAssociationService.get(asAssociation);
		int sum = asAssociation.getSum();
		asAssociation.setSum(sum + 1);
		asAssociationService.save(asAssociation);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "添加成员成功", "");
	}

	/**
	 * 删除校友会成员
	 * 
	 * @param sysUserAsson
	 */
	@CrossOrigin
	@PostMapping(value = "/deleteSysAs")
	public RestResponse<String> deleteSysAs(SysUserAsso sysUserAsson) {
		asAssociationService.deleteSysAs(sysUserAsson);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成员成功", "");
	}

	/**
	 * 批量加载校友会成员
	 * 
	 * @param asAssociation
	 */
	@CrossOrigin
	@PostMapping(value = "/loadSysAs")
	public PageIo<AsAssociation> loadSysAs(int pageNo, int pageSize, AsAssociation asAssociation) {
		PageIo<AsAssociation> pageIo = asAssociationService.loadSysAs(pageNo, pageSize, asAssociation);
		return pageIo;
	}
	

}