package com.mpri.aio.app.friend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.friend.service.AppFriShareService;
import com.mpri.aio.app.friend.vo.ShareRankingVo;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.friend.model.FriShare;
import com.mpri.aio.friend.service.FriShareService;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 分享相关Controller
 * @author syp
 *
 */
@RestController
@RequestMapping("/app/friShare")
public class AppFriShareController extends BaseController{

	
	@Autowired
	private FriShareService friShareService;
	
	
	@Autowired
	private AppFriShareService appFriShareService;
	
	/**
	 * 增加或者更新用户分享
	* <p>Title: add</p>  
	* <p>Description: </p>  
	* @param friShare
	* @return
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<String> save(FriShare friShare,HttpServletRequest request){
		String userId = JWTUtil.getUserId(request.getHeader("Authorization"));
		if(StringUtil.isNotEmpty(userId)) {
			friShare.setUserId(userId);
		}
		friShareService.save(friShare);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");							
	}
	
	/**
	 * 获取分享排名
	 */
	@CrossOrigin
	@PostMapping(value = "/loadShareRanking")
	public RestResponse<List<ShareRankingVo>> loadShareRanking(){
		List<ShareRankingVo> list = appFriShareService.loadShareRanking();
		return new RestResponse<List<ShareRankingVo>>(ExceptionResult.REQUEST_SUCCESS, "获取排名成功！", list);							
	}
	
	
}
