package com.mpri.aio.chart;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.chart.service.ChartService;
import com.mpri.aio.chart.service.StatisticsService;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ChartTest extends ApplicationTests{
	@Autowired
    private ChartService service;

    
	/**
	 * 分页获取信息
	 */
	@Test
    public void test(){
		//statisticsService.smNewNumberBySex(new SmSchoolmate());
		service.smNationPlaceChart(new SmSchoolmate());
    }
}
