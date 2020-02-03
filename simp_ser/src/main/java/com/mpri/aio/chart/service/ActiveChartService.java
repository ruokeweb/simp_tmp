package com.mpri.aio.chart.service;

import com.mpri.aio.chart.mapper.ActiveChartMapper;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.common.GlobalStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 校友活跃度统计Sevice
 * @author zdl
 *
 */
@Service
public class ActiveChartService {

	@Autowired
	private ActiveChartMapper activeChartMapper;

	/**
	 * 月活跃校友统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	public List<Map<String, Object>> activeMonthlyChart(String dateColumn,  String groupType,
													 int num, String enddate,SmSchoolmate schoolmate) {
		return activeChartMapper.activeMonthlyChart(dateColumn,   groupType, new int[num],enddate,schoolmate);
	}

	/**
	 * 日活跃校友统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	public List<Map<String, Object>> activeEverydayChart(String dateColumn,  String groupType,
														int num, String enddate,SmSchoolmate schoolmate) {
		return activeChartMapper.activeEverydayChart(dateColumn,   groupType, new int[num],enddate,schoolmate);
	}
	/**
	 * 捐赠人数统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	public List<Map<String, Object>> donationMonthlyChart(String dateColumn,  String groupType,
														int num, String enddate,SmSchoolmate schoolmate) {
		return activeChartMapper.donationMonthlyChart(dateColumn,   groupType, new int[num],enddate,schoolmate, GlobalStr.NORMAL_DON);
	}
	/**
	 * 活动参与人数统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	public List<Map<String, Object>> activityMonthlyChart(String dateColumn,  String groupType,
														int num, String enddate,SmSchoolmate schoolmate) {
		return activeChartMapper.activityMonthlyChart(dateColumn,   groupType, new int[num],enddate,schoolmate );
	}
	/**
	 * 祝福人数统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	public List<Map<String, Object>> smWishMonthlyChart(String dateColumn,  String groupType,
														int num, String enddate,SmSchoolmate schoolmate) {
		return activeChartMapper.smWishMonthlyChart(dateColumn,   groupType, new int[num],enddate,schoolmate, GlobalStr.SELFORG_STATUS_SUCCESS);
	}

}
