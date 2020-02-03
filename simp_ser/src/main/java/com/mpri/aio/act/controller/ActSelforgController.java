package com.mpri.aio.act.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.service.ActSelforgService;
import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.service.SysLoginExpandService;
import com.mpri.aio.app.utils.WechatGlobal.NoticeTemplate;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.app.utils.model.SubMessage.ExamResult;
import com.mpri.aio.app.utils.service.SubMessageService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;

 /**   
 *  
 * @Description:  值年返校——Controller
 * @Author:       cary
 * @project 	  simp 
 * @CreateDate:   Mon May 27 15:49:44 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/actContent/actSelforg")
public class ActSelforgController extends BaseController {
	
	@Autowired
	private ActSelforgService actSelforgService;
	
	@Autowired
	private SysLoginExpandService loginExpandService;
	
	@Autowired
	private SubMessageService subMessageService;
	/**
	 * 获取值年返校列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param actSelforg
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<ActSelforg> list(int pageNo,int pageSize,ActSelforg actSelforg) {
		PageIo<ActSelforg> pageInfo =  actSelforgService.loadByPage(pageNo,pageSize,actSelforg);							
		return pageInfo;
	}
	
	
	/**
	 * 增加或者更新值年返校
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param actSelforg
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save( ActSelforg actSelforg){
		actSelforgService.save(actSelforg);
		//在此增加审核的通知-当前端传入状态值时，即为审核
		if(!actSelforg.getStatus().isEmpty()) {
			
			ActSelforg aso=actSelforgService.get(actSelforg);
			String userId = aso.getUserId();
			//获取业务用户的id
			SysLoginExpand loginExpandParam= new SysLoginExpand();
			loginExpandParam.setUserId(userId);
			SysLoginExpand loginExpand=null;
			List<SysLoginExpand> list=loginExpandService.loadAllListBy(loginExpandParam);
			if(list.size()>0) {
				loginExpand=list.get(0);
			}
			if(loginExpand!=null) {
				String user_open_id=loginExpand.getExpand();
				SubMessage sm = new SubMessage();
				ExamResult er=null;
				String result="审核完成";
				String name=aso.getName();
				if(name.length()>20) {
					name=name.substring(0,19);
				}
				if(actSelforg.getStatus().equals("SUCCESS")) {
					result="申请通过";
					er=sm.new ExamResult(name,result,"值年返校申请已通过，快进入小程序分享吧");
				}
				if(actSelforg.getStatus().equals("LOSE")) {
					result="申请驳回";
					er=sm.new ExamResult(name,result,"值年返校申请已驳回，请重新发布信息");
				}
				sm.setData(er);
				sm.setTouser(user_open_id);
				sm.setTemplate_id(NoticeTemplate.SHJG);
				subMessageService.notice(sm);
			}
		}

		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除值年返校（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param actSelforg
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(ActSelforg actSelforg) {
		actSelforgService.delete(actSelforg);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取值年返校
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param actSelforg
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<ActSelforg> get(ActSelforg actSelforg) {
		return new RestResponse<ActSelforg>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				actSelforgService.get(actSelforg));	
	}
		
}