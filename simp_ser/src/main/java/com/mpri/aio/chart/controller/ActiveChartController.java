package com.mpri.aio.chart.controller;

import com.mpri.aio.chart.service.ActiveChartService;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Desc 校友活跃度统计
 * @author zdl
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/chart/active")
public class ActiveChartController {

	@Autowired
	private ActiveChartService activeChartService;
	
	
	/**
	 * 月活跃校友统计（三折线）
	 * @param schoolmate
	 * @return
	 */
	@PostMapping(value="activeMonthlyChart")
	public List<Map<String,Object>> activeMonthlyChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDate() != null) {
			return activeChartService.activeMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);	
		}
		
		return activeChartService.activeMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);	
	}

	/**
	 * 日活跃校友统计（三折线）
	 * @param schoolmate
	 * @return
	 */
	@PostMapping(value = "activeEverydayChart")
	public List<Map<String,Object>> activeEverydayChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDayDate() != null) {
			return activeChartService.activeEverydayChart("maintable.create_date", "day",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDayDate(),"yyyy-MM-dd"),schoolmate);
		}

		return activeChartService.activeEverydayChart("maintable.create_date", "day",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);
	}
	/**
	 * 捐赠人数统计（三折线）
	 * @param schoolmate
	 * @return
	 */
	@PostMapping(value = "donationMonthlyChart")
	public List<Map<String,Object>> donationMonthlyChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDate() != null) {
			return activeChartService.donationMonthlyChart("maintable.time", "month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);
		}

		return activeChartService.donationMonthlyChart("maintable.time", "month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);
	}
	/**
	 * 活动参与人数统计（三折线）
	 * @param schoolmate
	 * @return
	 */
	@PostMapping(value = "activityMonthlyChart")
	public List<Map<String,Object>> activityMonthlyChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDate() != null) {
			return activeChartService.activityMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);
		}

		return activeChartService.activityMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);
	}
	/**
	 * 祝福人数统计（三折线）
	 * @param schoolmate
	 * @return
	 */
	@PostMapping(value = "smWishMonthlyChart")
	public List<Map<String,Object>> smWishMonthlyChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDate() != null) {
			return activeChartService.smWishMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);
		}

		return activeChartService.smWishMonthlyChart("maintable.create_date", "month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);
	}
}
