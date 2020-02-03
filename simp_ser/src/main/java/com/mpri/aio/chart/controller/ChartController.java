package com.mpri.aio.chart.controller;

import java.util.List;
import java.util.Map;

import com.mpri.aio.chart.vo.DataSetForG2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.chart.service.ChartService;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * @Desc 新版统计功能
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/chart/schoolmate")
public class ChartController {

	@Autowired
	private ChartService chartService;
	
	
	/**
	 * 校友总量统计
	 * @param education
	 * @return
	 */
	@PostMapping(value="/year/count")
	public List<Map<String,Object>> smCountBarChart(SmSchoolmate schoolmate){
		if(schoolmate.getChartEndDate() != null) {
			return chartService.smCountBarChart("create_date","user_id","month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);	
		}
		
		return chartService.smCountBarChart("create_date","user_id","month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);	
	}
	
	/**
	 * 校友学历统计
	 */
    @PostMapping(value="qualificationsChart")
    public List<UnitNature> qualificationsChart(@RequestBody(required = false) SmSchoolmate schoolmate){
        return chartService.qualificationsChart(schoolmate);
    }
	/**
	 * @desc 校友新增人数统计     
	 */
	@PostMapping(value = "smNewNumberBySex")
	public List<Map<Object,Object>> smNewNumberBySex(SmSchoolmate schoolmate){
 		if(schoolmate.getChartEndDate() != null) {
			return chartService.smNewNumberBySex("create_date","user_id","month",Integer.valueOf(schoolmate.getParamA()),
					DateUtils.formatDate(schoolmate.getChartEndDate(),"yyyy-MM-dd"),schoolmate);
		}

		return chartService.smNewNumberBySex("create_date","user_id","month",Integer.valueOf(schoolmate.getParamA()),
				DateUtils.getDate(),schoolmate);
	}
	/**
	 * @desc 校友工作单位性质统计
	 */
	@PostMapping(value = "unitPropertyChart")
	public Map<String,Object> unitPropertyChart(SmSchoolmate schoolmate){
		return chartService.unitPropertyChart(schoolmate);
	}
	/**
	 * @desc 校友政治面貌统计
	 */
	@PostMapping(value = "politicCountenanceChart")
	public List<Map<Object,Object>> politicCountenanceChart(SmSchoolmate smSchoolmate){
		return chartService.politicCountenanceChart(smSchoolmate);
	}
	/**
	 * @desc 校友年龄段统计
	 */
	@PostMapping(value = "ageGroupChart")
	public List<Map<Object,Object>> ageGroupChart(SmSchoolmate smSchoolmate){
		return chartService.ageGroupChart(smSchoolmate);
	}
	/**
	 * @desc 校友信息完整度统计
	 */
	@PostMapping(value = "smCompleteChart")
	public List<UnitNature> smCompleteChart(@RequestBody(required = false)SmSchoolmate smSchoolmate){
		return chartService.smCompleteChart(smSchoolmate);
	}
	/**
	 * @desc 籍贯统计
	 */
	@PostMapping(value = "smNationPlaceChart")
	public List<Map<String, Object>> smNationPlaceChart(SmSchoolmate smSchoolmate){
		return chartService.smNationPlaceChart(smSchoolmate);
	}
	
	
	/**
	 * @desc 校友工作统计
	 */
	@PostMapping(value = "smWorkPlaceChart")
	public List<Map<String, Object>> smWorkPlaceChart(SmSchoolmate smSchoolmate){
		return chartService.smWorkPlaceChart(smSchoolmate);
	}
	

	
	




}
