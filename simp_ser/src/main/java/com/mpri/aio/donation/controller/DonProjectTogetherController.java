package com.mpri.aio.donation.controller;

import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.app.reg.model.SysLoginExpand;
import com.mpri.aio.app.reg.service.SysLoginExpandService;
import com.mpri.aio.app.utils.WechatGlobal;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.app.utils.service.SubMessageService;
import com.mpri.aio.system.common.GlobalStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.exception.ExceptionResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.donation.service.DonProjectTogetherService;

import java.util.List;

/**
 *  
 * @Description:  一起捐记录——Controller
 * @Author:       lzq
 * @project 	  simp 
 * @CreateDate:   Mon May 27 17:54:56 CST 2019
 * @Version:      v_1.2
 *    
 */
@RestController
@RequestMapping("/donContent/donProjectTogether")
public class DonProjectTogetherController extends BaseController {
	
	@Autowired
	private DonProjectTogetherService donProjectTogetherService;

	@Autowired
	private SysLoginExpandService loginExpandService;

	@Autowired
	private SubMessageService subMessageService;

	/**
	 * 获取一起捐记录列表
	* <p>Title: list</p>  
	* <p>Description: </p>  
	* @param pageNo
	* @param pageSize
	* @param donProjectTogether
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<DonProjectTogether> list(int pageNo,int pageSize,DonProjectTogether donProjectTogether) {
		PageIo<DonProjectTogether> pageInfo =  donProjectTogetherService.loadByPage(pageNo,pageSize,donProjectTogether);							
		return pageInfo;
	}

	 /**
	  * 增加或者更新一起捐记录
	  * <p>Title: add</p>
	  * <p>Description: </p>
	  * @param donProjectTogether
	  * @return
	  */
	 @CrossOrigin
	 @PostMapping(value = "/updateStatus")
	 public RestResponse<String> updateStatus(DonProjectTogether donProjectTogether){
		 donProjectTogetherService.updateStatus(donProjectTogether);
		 if(!donProjectTogether.getStatus().isEmpty()) {

			 DonProjectTogether donProjectTogether1 = donProjectTogetherService.get(donProjectTogether);
			 String userId = donProjectTogether1.getUserId();
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
				 SubMessage.ExamResult er=null;
				 String result="审核完成";
				 int size= donProjectTogether1.getName().length();
				 if(donProjectTogether1.getName().length()>20){
					 size=19;
				 }
				 String name=donProjectTogether1.getName().substring(0,size);
				 if(GlobalStr.SELFORG_STATUS_SUCCESS.equals( donProjectTogether1.getStatus())) {
					 result="申请通过";
					 er=sm.new ExamResult(name,result,"共同献爱心申请已通过，快进入小程序分享吧");
				 }
				 if(GlobalStr.SELFORG_STATUS_LOSE.equals(donProjectTogether1.getStatus())) {
					 result="申请驳回";
					 er=sm.new ExamResult(name,result,"共同献爱心申请已被驳回，请重新发布信息");
				 }
				 sm.setData(er);
				 sm.setTouser(user_open_id);
				 sm.setTemplate_id(WechatGlobal.NoticeTemplate.SHJG);
				 subMessageService.notice(sm);
			 }
		 }
		 return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "修改状态成功！", "");
	 }

	/**
	 * 增加或者更新一起捐记录
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param donProjectTogether
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(DonProjectTogether donProjectTogether){
		donProjectTogetherService.save(donProjectTogether);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}	
	
	/**
	 * 删除一起捐记录（逻辑删除）
	* <p>Title: delete</p>  
	* <p>Description: </p>  
	* @param donProjectTogether
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(DonProjectTogether donProjectTogether) {
		donProjectTogetherService.delete(donProjectTogether);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}
	
	/**
	 * 获取一起捐记录
	* <p>Title: get</p>  
	* <p>Description: </p>  
	* @param donProjectTogether
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<DonProjectTogether> get(DonProjectTogether donProjectTogether) {
		return new RestResponse<DonProjectTogether>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				donProjectTogetherService.get(donProjectTogether));	
	}
		
}