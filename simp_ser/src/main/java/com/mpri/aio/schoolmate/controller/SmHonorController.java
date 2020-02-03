package com.mpri.aio.schoolmate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmHonor;
import com.mpri.aio.schoolmate.service.SmHonorService;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysIndustry;

/**
 * 
 * @Description: 校友荣誉成果表——Controller
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:53:35 CST 2019
 * @Version: v_1.0
 * 
 */
@RestController
@RequestMapping("/sm/smHonor")
public class SmHonorController extends BaseController {

	@Autowired
	private SmHonorService smHonorService;

	@Autowired
	private RedisCacheService redisCacheService;

	/**
	 * 获取校友荣誉成果表列表
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param smHonor
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmHonor> list(int pageNo, int pageSize, SmHonor smHonor) {
		PageIo<SmHonor> pageInfo = smHonorService.loadByPage(pageNo, pageSize, smHonor);
		return pageInfo;
	}

	/**
	 * 增加或者更新校友荣誉成果表
	 * <p>
	 * Title: add
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smHonor
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmHonor> save(SmHonor smHonor) {
		smHonorService.save(smHonor);
		return new RestResponse<SmHonor>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smHonor);
	}

	/**
	 * 删除校友荣誉成果表（逻辑删除）
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smHonor
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmHonor smHonor) {
		smHonorService.delete(smHonor);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}

	/**
	 * 获取校友荣誉成果表
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smHonor
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmHonor> get(SmHonor smHonor) {
		return new RestResponse<SmHonor>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smHonorService.get(smHonor));
	}

	@CrossOrigin
	@PostMapping(value = "/loadAllListBy")
	public RestResponse<List<SmHonor>> loadAllListBy(SmHonor smHonor) {
		List<SmHonor> res = smHonorService.loadAllListBy(smHonor);
		Map<String, SysIndustry> sysBaseIndustryCache = (Map<String, SysIndustry>) redisCacheService
				.getBaseCache(new SysIndustry(), InitCache.SYS_BASE_INDUSTRY_CACHE);
		for (SmHonor sHonor : res) {
			if (StringUtil.isNotEmpty(sHonor.getIndustry())) {
				sHonor.setIndustryName(sysBaseIndustryCache.get(sHonor.getIndustry()).getName());
			}
		}
		return new RestResponse<List<SmHonor>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", res);
	}
}