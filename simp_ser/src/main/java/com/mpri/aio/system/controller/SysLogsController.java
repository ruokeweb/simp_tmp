package com.mpri.aio.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.model.SysLogs;
import com.mpri.aio.system.service.SysLogsService;

	/**
	 *        系统日志模块——controller
	* <p>Title: SysLogsController</p>  
	* <p>Description: </p>  
	* @author syp  
	* @date 2018年8月16日
	 */
@RestController
@RequestMapping("sys/syslogs")
public class SysLogsController extends BaseController{
	
	@Autowired
	private SysLogsService sysLogsService;
	
	
	/**
	 * 日志管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param sysLogs
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageIo<SysLogs> list(int pageNo,int pageSize,SysLogs sysLogs){
		return sysLogsService.loadByPage(pageNo, pageSize, sysLogs);
	}
	
	/**
	 * 日志管理列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param sysLogs
	* @return
	 * @throws Exception 
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SysLogs> get(SysLogs sysLogs){
		//返回受到封装的对象
		return new RestResponse<SysLogs>(ExceptionResult.REQUEST_SUCCESS,"获取成功",sysLogsService.get(sysLogs));
	}

		/**
		 * 清理 所有捐赠
		 * 清理 所有活动
		 * 清理 所有日志
		 * @throws Exception
		 */
		@CrossOrigin
		@PostMapping(value = "/reduction")
		public RestResponse<String> reduction(){
			sysLogsService.deleteAll();
			//返回受到封装的对象
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS,"清除成功",null);
		}

}
