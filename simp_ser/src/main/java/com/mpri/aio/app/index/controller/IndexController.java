package com.mpri.aio.app.index.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.index.service.IndexService;
import com.mpri.aio.app.index.vo.BannerVo;
import com.mpri.aio.app.index.vo.IndexInfoVo;
import com.mpri.aio.app.index.vo.News;
import com.mpri.aio.app.reg.service.RegistService;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.response.RestToken;
import com.mpri.aio.message.model.MesMediaMessage;
import com.mpri.aio.message.service.MesMediaMessageService;
import com.mpri.aio.schoolmate.aop.aspect.InfoIntegrity;
import com.mpri.aio.schoolmate.mapper.InfoIntegrityMapper;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.utils.InfoIntegrityUtils;
import com.mpri.aio.settings.model.SettingWebvsb;
import com.mpri.aio.settings.service.SettingWebvsbService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.untils.file.model.File;
import com.mpri.aio.untils.file.service.FileService;

/**
 * 首页相关接口
 * @author syp
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/app/index")
public class IndexController extends BaseController{

	@Autowired
	private RegistService registService;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private SettingWebvsbService settingWebvsbService;

	@Autowired
	private InfoIntegrityMapper mapper;

	@Autowired
	private InfoIntegrityUtils infoIntegrityUtils;
	
	@Autowired
	private MesMediaMessageService mesMediaMessageService;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 刷新token接口
	 */
	@PostMapping("/refreshToken")
	public RestResponse<RestToken> refreshToken(@RequestParam("comeFrom") String comeFrom, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		// 当前时间
		Date now = new Date();
		long nowTime = now.getTime();
		// 当前token过期时间
		long tokenTime = JWTUtil.getTokenTime(token);
		String username = JWTUtil.getUsername(token);

		long freshTime = 0;

		if (comeFrom.equals(JWTUtil.FROM_WEB)) {
			freshTime = JWTUtil.REFESH_TIME;
		} else if (comeFrom.equals(JWTUtil.FROM_APP)) {
			freshTime = JWTUtil.APP_REFESH_TIME;
		} else {
			freshTime = JWTUtil.REFESH_TIME;
		}
		// 刷新token时间
		if ((tokenTime - nowTime) > 0 && (tokenTime - nowTime) < freshTime) {
			SysUser sysUser = registService.getUserByUserName(username);
			String password = sysUser.getPassword();
			String userId = sysUser.getId();
			// 注册new token
			String newToken = JWTUtil.sign(username, userId, password, comeFrom);

			// 获取token过期时间
			long newTokenTime = JWTUtil.getTokenTime(newToken);

			// 封装token
			RestToken restToken = new RestToken();
			restToken.setToken(newToken);
			restToken.setTokenTime(newTokenTime);

			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "Token已刷新", restToken);

		} else if ((tokenTime - nowTime) < 0) {
			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "Token已经过期，请重新登陆", null);
		} else {
			// 封装token
			RestToken restToken = new RestToken();
			restToken.setToken(token);
			restToken.setTokenTime(tokenTime);
			return new RestResponse<RestToken>(ExceptionResult.REQUEST_SUCCESS, "token可以继续使用", restToken);
		}
	}
	
	
	/**
	 * 首页 我的校友卡
	 */
	@InfoIntegrity
	@PostMapping(value = "/getMyCard")
	public RestResponse<Map<String,String>> getMyCard(HttpServletRequest request){
		Map<String,String> map = indexService.getMyCard(request);
		if(map.size() == 0) {
			return new RestResponse<Map<String,String>>(ExceptionResult.DATA_USED, 
					"该校友卡在认证中",map);			
		}else {
			return new RestResponse<Map<String,String>>(ExceptionResult.REQUEST_SUCCESS, 
					"获取校友卡状态成功",map);				
		}
	}
	
	
	@InfoIntegrity	
	@PostMapping(value = "/refreshStar")
	public RestResponse<Integer> refreshStar(HttpServletRequest request){
		String authorization = request.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);

		List<String> status  = null;
		int level=0;
		try {
			status = InfoIntegrityUtils.getInfoIntegrity(mapper, userId);
			level = infoIntegrityUtils.setSmComplete(status);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		//获取用户的星级

			return new RestResponse<Integer>(ExceptionResult.REQUEST_SUCCESS,
					"刷新星级成功",level);
	}
	
	/**
	 *获取已经领卡的人数
	 */
	@PostMapping("/getCollarCardNum")
	public RestResponse<Integer> getCollarCardNum(){
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
		return new RestResponse<Integer>(ExceptionResult.REQUEST_SUCCESS,
				"获取数据成功",indexService.getCollarCardNum(smSchoolmate));
	}
	/**
	 * 获取近期领卡成功的校友列表
	 */
	@PostMapping("/getCollarCardList")
	public RestResponse<List<IndexInfoVo>> getCollarCardList(Integer pageSize){
		if(pageSize==null){
			pageSize=0;
		}
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setCardStatus(GlobalStr.NORMAL_CARD_STATUS);
		return new RestResponse<List<IndexInfoVo>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",indexService.getCollarCardList(smSchoolmate,pageSize));
	}
	/**
	 * 获取近期领卡成功的校友列表
	 */
	@PostMapping("/getThankslist")
	public RestResponse<List<SmWish>> getThankslist(Integer pageSize){
		if(pageSize==null){
			pageSize=0;
		}
		SmWish smWish = new SmWish();
		smWish.setIsshow(GlobalStr.SELFORG_STATUS_SUCCESS);
		return new RestResponse<List<SmWish>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",indexService.getThankslist(smWish,pageSize));
	}
	
	/**
	 * 获取首页banner图
	 */
	@PostMapping("/getIndexBanners")
	public RestResponse<List<BannerVo>> getIndexBanners(int pageNo,int pageSize){
		return new RestResponse<List<BannerVo>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",indexService.getIndexBanners(pageNo, pageSize)); 
	}
	
	/**
	 * 获取新闻n   
	 */
	@PostMapping("/getIndexNews")
	public RestResponse<List<News>> getIndexNewss(SettingWebvsb settingWebvsb){
		System.out.println(settingWebvsb.toString());
		List<News> news = new ArrayList<News>();
		try {
			news = indexService.getIndexNews(settingWebvsb.getPageNo(), settingWebvsb.getPageSize(),settingWebvsb); 
			return new RestResponse<List<News>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",news);
		} catch (ClientProtocolException e) {
			System.err.println(e.getMessage());
			return new RestResponse<List<News>>(ExceptionResult.SYS_ERROR,"获取数据失败",news);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new RestResponse<List<News>>(ExceptionResult.SYS_ERROR,"获取数据失败",news);
		} 
	}
	
	
	
	/**
	 * 获取栏目
	 */
	@PostMapping(value = "/getNewsTrees")
	public RestResponse<List<SettingWebvsb>> getNewsTrees(SettingWebvsb settingWebvsb) {
		List<SettingWebvsb> list =  settingWebvsbService.loadAllListBy(settingWebvsb);							
		return new RestResponse<List<SettingWebvsb>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",list);
	}
	
	/**
	 * 获取新闻详情
	 */
	@PostMapping(value = "/getNewsDetail")
	public RestResponse<News> getNewsDetail (String id){
		News news = new News();
		try {
			news = indexService.getNewsDetail(id);
			return new RestResponse<News>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",news);
		} catch (ParseException e) {
			return new RestResponse<News>(ExceptionResult.SYS_ERROR,"获取数据失败",news);
		} catch (IOException e) {
			return new RestResponse<News>(ExceptionResult.SYS_ERROR,"获取数据失败 ",news);
		}
	}
	
	/**
	 * 获取推荐文章
	 */
	@PostMapping(value = "/getrelationNews")
	public RestResponse<List<News>> getrelationNews(int pageNo,int pageSize,News news){
		List<News> list = new ArrayList<News>();
		try {
			list = indexService.getrelationNews(news, (pageNo-1)*pageSize, pageSize);
			return new RestResponse<List<News>>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",list);
		} catch (ClientProtocolException e) {
			return new RestResponse<List<News>>(ExceptionResult.SYS_ERROR,"获取数据失败",list);
		} catch (IOException e) {
			return new RestResponse<List<News>>(ExceptionResult.SYS_ERROR,"获取数据失败",list);
		}
	}
	
	/**
	 * 获取首页通知消息
	 */
	@PostMapping(value = "/getIndexMediaMessage")
	public RestResponse<MesMediaMessage> getIndexMediaMessage(){
		MesMediaMessage mediaMessage = new MesMediaMessage();

		mediaMessage = mesMediaMessageService.getIndexMesMediaMessage(mediaMessage);
		
		return new RestResponse<MesMediaMessage>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",mediaMessage);
	}
	
	/**
	 * 获取首页新闻详情
	 */
	@PostMapping(value = "/getIndexMediaMessageDetail")
	public RestResponse<MesMediaMessage> getIndexMediaMessageDetail(MesMediaMessage mediaMessage){
		mediaMessage = mesMediaMessageService.get(mediaMessage);
		if(null !=mediaMessage && StringUtil.isNotEmpty(mediaMessage.getAppendix())) {
			Optional<File> file = fileService.getFileById(mediaMessage.getAppendix());
			if(null != file) {
				mediaMessage.setAppendixName(file.get().getName());
			}
		}
		return new RestResponse<MesMediaMessage>(ExceptionResult.REQUEST_SUCCESS,"获取数据成功",mediaMessage);
	}
	
}
