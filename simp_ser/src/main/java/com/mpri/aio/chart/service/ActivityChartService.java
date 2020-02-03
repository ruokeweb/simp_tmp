package com.mpri.aio.chart.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.chart.mapper.ActivityChartMapper;
import com.mpri.aio.chart.vo.TableVo;
import com.mpri.aio.common.page.PageIo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 活动统计Sevice
 * @author zdl
 *
 */
@Service
public class ActivityChartService {

	@Autowired
	private ActivityChartMapper activityChartMapper;

	/**
	 * 活动报名统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param actActivity
	 * @return
	 */
	public List<Map<String, Object>> actSignChart(String dateColumn, String groupType,
												  int num, String enddate, ActActivity actActivity) {
		return activityChartMapper.actSignChart(dateColumn,   groupType, new int[num],enddate,actActivity);
	}

	/**
	 * 值年返校报名统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param actActivity
	 * @return
	 */
	public List<Map<String, Object>> actBackChart(String dateColumn,  String groupType,
												  int num, String enddate,ActActivity actActivity) {
		return activityChartMapper.actBackChart(dateColumn,   groupType, new int[num],enddate,actActivity);
	}

	/**
	 * 活动人数排行
	 * @param pageNo
	 * @param pageSize
	 * @param tableVo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getSignActNum(int pageNo, int pageSize, TableVo tableVo) {
		PageHelper.startPage(pageNo, pageSize);
		Page<Map<String,Object>> pageList = activityChartMapper.getSignActNum(tableVo);
		PageIo<Map<String,Object>> pageInfo = new PageIo(pageList);
		return pageInfo;
	}

	/**
	 * 值年返校人数排行
	 * @param pageNo
	 * @param pageSize
	 * @param tableVo
	 * @return
	 */
	public PageInfo<Map<String, Object>> getSignActBackNum(int pageNo, int pageSize, TableVo tableVo) {
		PageHelper.startPage(pageNo, pageSize);
		Page<Map<String,Object>> pageList = activityChartMapper.getSignActBackNum(tableVo);
		PageIo<Map<String,Object>> pageInfo = new PageIo(pageList);
		return pageInfo;
	}
}
