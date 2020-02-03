package com.mpri.aio.act.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.service.ActActivityService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;

/**
 *  
 * @Description:  活动——Controller
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Fri Mar 01 13:25:30 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/act/actActivity")
public class ActActivityController extends BaseController {
	
	@Autowired
	private ActActivityService actActivityService;
	/*预备中*/
	private static String READAY = "READAY";
	/*报名进行中*/
	private static String DOING = "DOING";
	/*报名已结束*/
	private static String DONE = "DONE";
	/**
	 * 获取活动列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actActivity
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ActActivity> list(int pageNo,int pageSize,ActActivity actActivity) {
		PageIo<ActActivity> pageInfo =  actActivityService.loadByPage(pageNo,pageSize,actActivity);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新活动
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param actActivity
	* @return
	 */
	@Logs(value = "活动修改",type ="UPDATE")
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(ActActivity actActivity){
		actActivity.setStatus(judgeDate(actActivity));
		actActivityService.save(actActivity);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除活动（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param actActivity
	* @return
	 */
	@Logs(value = "活动删除",type ="DELETE")
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ActActivity actActivity) {
		actActivityService.delete(actActivity);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取活动
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param actActivity
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ActActivity> get(ActActivity actActivity) {
		return new RestResponse<ActActivity>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				actActivityService.get(actActivity));	
	}

	private static String judgeDate(ActActivity act) {
		Long currtTime = new Date().getTime();
		if(null == act.getSignStartDate() || null ==act.getSignEndDate()) {
			return DOING;
		}else if(act.getSignEndDate().getTime() < currtTime) {
			return DONE;
		}else if(currtTime <= act.getSignStartDate().getTime()) {
			return READAY;
		}else if(act.getSignStartDate().getTime() < currtTime && act.getSignEndDate().getTime() > currtTime) {
			return DOING;
		}
		return DOING;
	}
		
}