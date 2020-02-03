package com.mpri.aio.app.mine.controller;

import com.mpri.aio.app.mine.service.AppRankingService;
import com.mpri.aio.app.mine.vo.*;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Date 2019/11/12 13:18
 * @Created by lzq
 */
@RestController
@RequestMapping("app/ranking")
public class AppRankingController {

	@Autowired
	private AppRankingService appRankingService;

	/**
	 * 领卡排名
	 */
	@PostMapping(value = "/getCard")
	public RestResponse<Map<String, Object>> getCardRanking(AppCardRankingVo cardRankingVo, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		cardRankingVo.setUserId(userId);
		Map<String, Object> map = appRankingService.getCardRank(cardRankingVo);
		if (null != map) {
			return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", map);
		} else {
			return new RestResponse<Map<String, Object>>(ExceptionResult.NOT_FOUND, "未知的卡片类型！", null);
		}
	}

	/**
	 * 校友捐赠钱数归纳表
	 */
	@PostMapping(value = "/getDonMoney")
	public RestResponse<Map<String, Object>> getDonMoney(AppChaDonMoneyVo appChaDonMoneyVo, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		appChaDonMoneyVo.setUserId(userId);

		Map<String, Object> map = appRankingService.getDonMoney(appChaDonMoneyVo);
		if (null != map) {
			return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", map);
		} else {
			return new RestResponse<Map<String, Object>>(ExceptionResult.NOT_FOUND, "未知的卡片类型！", null);
		}
	}

	/**
	 * 校友捐赠次数归纳表
	 */
	@PostMapping(value = "/getChaDonTime")
	public RestResponse<Map<String, Object>> getChaDonTime(AppChaDonTimeVo appChaDonTimeVo, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		appChaDonTimeVo.setUserId(userId);

		Map<String, Object> map = appRankingService.getChaDonTime(appChaDonTimeVo);
		if (null != map) {
			return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", map);
		} else {
			return new RestResponse<Map<String, Object>>(ExceptionResult.NOT_FOUND, "暂无数据！", null);
		}
	}

	/**
	 * 校友分享归纳表
	 */
	@PostMapping(value = "/getChaShare")
	public RestResponse<Map<String, Object>> getChaShare(AppChaShareVo appChaShareVo, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		appChaShareVo.setUserId(userId);

		Map<String, Object> map = appRankingService.getChaShare(appChaShareVo);
		if (null != map) {
			return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", map);
		} else {
			return new RestResponse<Map<String, Object>>(ExceptionResult.NOT_FOUND, "暂无数据！", null);
		}
	}

	/**
	 * 校友认证归纳表
	 */
	@PostMapping(value = "/getChaProve")
	public RestResponse<Map<String, Object>> getChaProve(AppChaProveVo appChaProveVo, HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		String userId = JWTUtil.getUserId(authorization);
		appChaProveVo.setUserId(userId);

		Map<String, Object> map = appRankingService.getChaProve(appChaProveVo);
		if (null != map) {
			return new RestResponse<Map<String, Object>>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", map);
		} else {
			return new RestResponse<Map<String, Object>>(ExceptionResult.NOT_FOUND, "暂无数据！", null);
		}
	}
}
