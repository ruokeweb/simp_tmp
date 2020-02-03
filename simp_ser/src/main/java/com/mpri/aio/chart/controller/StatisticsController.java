package com.mpri.aio.chart.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.chart.service.StatisticsService;
import com.mpri.aio.chart.vo.DataSetForG2;
import com.mpri.aio.chart.vo.SmCountByArea;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.chart.vo.UnitNatureVo;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.system.common.GlobalStr;

@RestController
@RequestMapping("/chart/statistic")
@CrossOrigin
public class StatisticsController
{
	@Autowired
	StatisticsService statisticsService;
	
	@PostMapping(value="/regist/day/{num}")
	public List<Map<String,Object>> registStatistic(@PathVariable int num)
	{
		return statisticsService.groupByData("sys_user","day",num);
	}
	 
	@PostMapping(value="/inited/day/{num}")
	public List<Map<String,Object>> initedStatistic(@PathVariable int num)
	{
		return statisticsService.groupByData("sm_schoolmate","day",num);
	}
	
	@PostMapping(value="/donation/day/{num}")
	public List<Map<String,Object>> donMoneyByDay(@PathVariable int num)
	{
		return statisticsService.groupByData("don_record","time","money","day",num,"");
	}
	
	@PostMapping(value="/donation/month/{num}")
	public Map<String,List<Map<String,Object>>> donProByMonth(@PathVariable int num){
		Map<String,List<Map<String,Object>>> map = new HashMap<String,List<Map<String,Object>>>();
		map.put(GlobalStr.DON_WILL, statisticsService.donationStatistic(num,GlobalStr.DON_WILL));
		map.put(GlobalStr.DON_BEDOING, statisticsService.donationStatistic(num,GlobalStr.DON_BEDOING));
		map.put(GlobalStr.DON_HASDONE, statisticsService.donationStatistic(num,GlobalStr.DON_HASDONE));
		return map;
	}
	
	@PostMapping("getAllDonProFee")
	public List<DonProject> getAllDonProFee(DonProject donProject){
		donProject.setFlag(GlobalStr.NORMAL);
		return statisticsService.getAllDonProFee(donProject);
	}
	
	@PostMapping("smCountByArea")
	public List<SmCountByArea> smCountByArea(HttpServletRequest request){
		return statisticsService.smCountByArea();
	}
	/**
	 * 校友数量统计
	 * @param education
	 * @return
	 */
	@PostMapping(value="/schoolmate/year/count")
	public List<Map<String,Object>> smCountBarChart(SmEducation education){
		Date date  = education.getEnddate();
		if(null == date) {
			return statisticsService.smCountBarChart("sm_education","startdate","user_id","year",10,null,education);
		}else {
			String enddate = DateUtils.formatDateTime(education.getEnddate());
			int difference = Integer.valueOf(education.getParamA());
			return statisticsService.smCountBarChart("sm_education","startdate","user_id","year",difference,enddate,education);	
		}
	}
	
	/**
	 * 校友性别统计 
	 * @return
	 */
	@PostMapping(value="/schoolmate/sex/smCountBySex")
	public List<DataSetForG2> smCountBySex(SmEducation smEducation){
		return statisticsService.smCountBySex(smEducation);
	}
	

	@PostMapping(value="unitnature",produces="application/json")
	public List<UnitNature> getUnitNature(HttpServletRequest request, @RequestBody UnitNatureVo unitNatureVo){
		return statisticsService.getUnitNature(unitNatureVo);
	}

    @PostMapping(value="education",produces="application/json")
    public List<UnitNature> getEducationInfo(HttpServletRequest request, @RequestBody UnitNatureVo unitNatureVo){
        return statisticsService.getEducationInfo(unitNatureVo);
    }

	@PostMapping(value="politicalappearance",produces="application/json")
	public List<UnitNature> getPoliticalAppearance(HttpServletRequest request, @RequestBody UnitNatureVo unitNatureVo){
		return statisticsService.getPoliticalAppearance(unitNatureVo);
	}

}
