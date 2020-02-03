package com.mpri.aio.chart.service;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.chart.mapper.StatisticsMapper;
import com.mpri.aio.chart.vo.DataSetForG2;
import com.mpri.aio.chart.vo.SmCountByArea;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.chart.vo.UnitNatureVo;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.schoolmate.mapper.SmProfessionMapper;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.mapper.SysDictMapper;
import com.mpri.aio.system.model.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService
{

	@Autowired
	private RedisCacheService redisCacheService;
	/*百度地图省*/
	private final static String[] PROVINCES = {
			"北京","天津","上海","重庆","河北","河南","云南","辽宁","黑龙江","湖南","安徽","山东","新疆","江苏","浙江","江西","湖北","广西","甘肃","山西","内蒙古","陕西","吉林","福建","贵州","广东","青海","西藏","四川","宁夏","海南","台湾","香港","澳门"
	};

	@Autowired
	StatisticsMapper statisticsMapper;
	@Autowired
	SmProfessionMapper smProfessionMapper;

	@Autowired
	SysDictMapper sysDictMapper;

	@Autowired
	private SmSchoolmateService smSchoolmateService;
	/**
	 * 获取统计数据
	 * @param tableName 需要统计的表名
	 * @param dateColumn 日期字段名
	 * @param countColumn 统计字段名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。
	 * @param querySQL 外部查询条件，未做防注入处理，需保证安全性。<br>
	 * Example: groupByData("sys_user","create_date","id","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String dateColumn,String countColumn,String groupType, int num,String sql)
	{
		List<Map<String,Object>> list=statisticsMapper.groupByData(tableName,dateColumn,countColumn,groupType,new int[num],sql);
		return list;
	}

	/**
	 * 获取统计数据
	 * @param tableName 需要统计的表名
	 * @param dateColumn 日期字段名
	 * @param countColumn 统计字段名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。<br>
	 * Example: groupByData("sys_user","create_date","id","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String dateColumn,String countColumn,String groupType, int num)
	{
		List<Map<String,Object>> list=groupByData(tableName,dateColumn,countColumn,groupType,num,null);
		return list;
	}

	/**
	 * 获取统计数据。统计字段默认为ID
	 * @param tableName 需要统计的表名
	 * @param dateColumn 日期字段名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。<br>
	 * Example: groupByData("sys_user","create_date","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String dateColumn,String groupType, int num)
	{
		List<Map<String,Object>> list=groupByData(tableName,dateColumn,"id",groupType,num);
		return list;
	}

	/**
	 * 获取统计数据。统计字段默认为ID
	 * @param tableName 需要统计的表名
	 * @param dateColumn 日期字段名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。
	 * @param querySQL 外部查询条件，未做防注入处理，需保证安全性。<br>
	 * Example: groupByData("sys_user","create_date","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String dateColumn,String groupType, int num,String sql)
	{
		List<Map<String,Object>> list=groupByData(tableName,dateColumn,"id",groupType,num,sql);
		return list;
	}

	/**
	 * 获取统计数据。日期字段默认为create_date,统计字段默认为ID
	 * @param tableName 需要统计的表名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。<br>
	 * Example: groupByData("sys_user","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String groupType, int num)
	{
		List<Map<String,Object>> list=groupByData(tableName,"create_date",groupType,num);
		return list;
	}

	/**
	 * 获取统计数据。日期字段默认为create_date,统计字段默认为ID
	 * @param tableName 需要统计的表名
	 * @param groupType 统计维度 取值为 day、month、year
	 * @param num 维度的长度。
	 * @param querySQL 外部查询条件，未做防注入处理，需保证安全性。<br>
	 * Example: groupByData("sys_user","day",30)表示统计sys_user表最近30天每天注册的数量
	 * @return
	 */
	public List<Map<String,Object>> groupByData(String tableName,String groupType, int num,String sql)
	{
		List<Map<String,Object>> list=groupByData(tableName,"create_date",groupType,num,sql);
		return list;
	}


	/**
	 * 获取捐赠项目的捐赠人数及捐赠总额
	 * <p>Title: getAllDonProFee</p>
	 * <p>Description: </p>
	 * @param donProject
	 * @return
	 */
	public List<DonProject> getAllDonProFee(DonProject donProject){
		return statisticsMapper.getAllDonProFee(donProject);
	}

	/**
	 * 捐赠项目查询
	 * <p>Title: donationStatistic</p>
	 * <p>Description: </p>
	 * @param num
	 * @param status
	 * @return
	 */
	public List<Map<String,Object>> donationStatistic(int num,String status){
		String querySQL = "status ='" +status+"'";
		return groupByData("don_project","month",num,querySQL);
	}

	/**
	 * .统计校友区域分布(目前仅限China)
	 * <p>Title: smCountByArea</p>
	 * <p>Description: </p>
	 * @return
	 */
	public List<SmCountByArea> smCountByArea(){
		return this.excuteRes();
	}

	/**
	 * 处理数据结果集
	 * <p>Title: excuteRes</p>
	 * <p>Description: </p>
	 * @return
	 */
	private List<SmCountByArea> excuteRes(){
		List<SmCountByArea> map = statisticsMapper.smCountByArea();
		for (SmCountByArea smCountByArea : map) {
			if(StringUtil.isNotEmpty(smCountByArea.getName())) {
				String [] arg = smCountByArea.getName().split(",");
				for (String str : PROVINCES) {
					if(str.equals(arg[arg.length-1].substring(0, 2))) {
						smCountByArea.setName(str);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 校友数量统计
	 * @param tableName
	 * @param dateColumn
	 * @param countColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param education
	 * @return
	 */
	public List<Map<String, Object>> smCountBarChart(String tableName, String dateColumn, String countColumn, String groupType,
													 int num,  String enddate,SmEducation education) {
		return statisticsMapper.smCountBarChart(tableName, dateColumn, countColumn, groupType, new int[num], enddate, education);
	}

	/**
	 * 获取校友单位性质
	 */
	public List<UnitNature> getUnitNature(UnitNatureVo unitNatureVo) {

		if (unitNatureVo.getSmSchoolmate().getSmEducation().getSchool()!=null || unitNatureVo.getStartYearInternal() != null || unitNatureVo.getEndYearInternal()!=null ) {
			String startYearInternal = unitNatureVo.getStartYearInternal();
			String endYearInternal = unitNatureVo.getEndYearInternal();
			/**处理起至年时间区间 以及 学籍信息*/
			handlerYearInternal(unitNatureVo.getSmSchoolmate(),startYearInternal,endYearInternal,unitNatureVo.getSmSchoolmate().getSmEducation().getSchool());
		}

		/**获取所有单位性质*/
		SysDict sysDict = new SysDict();
		sysDict.setTypeCode("COMPANY_TPYE");
		List<SysDict> SysDictList = sysDictMapper.getSysDictByTypecode(sysDict);


		/**获取刷选条件下的校友信息*/
		List<Map<String, String>> list = statisticsMapper.loadNatureListByStaticCondition(unitNatureVo.getSmSchoolmate());
		List<UnitNature> result = new ArrayList<>();

		/**获取各个单位性质下的校友人数*/
		for (int i = 0; i < SysDictList.size(); i++) {
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if (SysDictList.get(i).getValue().equals(list.get(j).get("nature"))) {
						UnitNature un = new UnitNature();
						un.setType(SysDictList.get(i).getLabel());
						un.setKey(SysDictList.get(i).getValue());
						un.setValue(Long.valueOf(String.valueOf(list.get(j).get("natureSum"))));
						result.add(un);
					}
				}
			}
		}

		long sumNull = 0;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).get("nature")==null || "".equals(list.get(i).get("nature"))) {
					sumNull+= Long.valueOf(String.valueOf(list.get(i).get("natureSum")));
				}
			}
			UnitNature un = new UnitNature();
			un.setType("不明");
			un.setKey("不明");
			un.setValue(sumNull);
			result.add(un);
		}
		return  result;
	}


	/**
	 * 学历信息统计
	 */
	public List<UnitNature> getEducationInfo(UnitNatureVo unitNatureVo) {

		/**处理起至年时间区间 以及 学籍信息*/
		if (unitNatureVo.getSmSchoolmate().getSmEducation().getSchool()!=null || unitNatureVo.getStartYearInternal() != null || unitNatureVo.getEndYearInternal()!=null ) {
			String startYearInternal = unitNatureVo.getStartYearInternal();
			String endYearInternal = unitNatureVo.getEndYearInternal();
			handlerYearInternal(unitNatureVo.getSmSchoolmate(),startYearInternal,endYearInternal,unitNatureVo.getSmSchoolmate().getSmEducation().getSchool());
		}

		/**获取学历信息性质*/
		SysDict eduDict = new SysDict();
		eduDict.setTypeCode("EDU_RECORD");
		List<SysDict> SysDictList = sysDictMapper.getSysDictByTypecode(eduDict);

		/**获取刷选条件下的校友信息*/
		List<Map<String, String>> list = statisticsMapper.loadEducationListByStaticCondition(unitNatureVo.getSmSchoolmate());

		/**获取各个单位性质下的校友人数*/
		List<UnitNature> result = new ArrayList<>();
		for (int i = 0; i < SysDictList.size(); i++) {
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if((SysDictList.get(i).getValue()).equals(list.get(j).get("SmRecord"))){
						if(GlobalStr.EDU_RECORD_EDP.equals(list.get(j).get("SmRecord"))) {
							break;
						}
						UnitNature un = new UnitNature();
						long num = 0 ;
						un.setType(SysDictList.get(i).getLabel());
						un.setKey(SysDictList.get(i).getValue());
						String smsum = String.valueOf(list.get(j).get("smSum"));
						num = Long.valueOf(smsum == null ? "0" : smsum);
						un.setValue(num);
						result.add(un);

					}
				}
			}
		}
		if (list != null && list.size() > 0) {
			long num = 0 ;
			UnitNature un = new UnitNature();
			un.setType("其它");
			un.setKey("其它");
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).get("SmRecord")==null || "".equals(list.get(j).get("SmRecord")) || GlobalStr.EDU_RECORD_EDP.equals(list.get(j).get("SmRecord"))){
					String smsum = String.valueOf(list.get(j).get("smSum"));
					num += Long.valueOf(smsum == null || "".equals(smsum) ? "0" : smsum);
				}
			}
			un.setValue(num);
			result.add(un);
		}

		return  result;
	}

	/**
	 * 校友政治面貌统计
	 */
	public List<UnitNature> getPoliticalAppearance(UnitNatureVo unitNatureVo) {
		/**处理起至年时间区间 以及 学籍信息*/
		if (unitNatureVo.getSmSchoolmate().getSmEducation().getSchool()!=null || unitNatureVo.getStartYearInternal() != null || unitNatureVo.getEndYearInternal()!=null ) {
			String startYearInternal = unitNatureVo.getStartYearInternal();
			String endYearInternal = unitNatureVo.getEndYearInternal();
			handlerYearInternal(unitNatureVo.getSmSchoolmate(),startYearInternal,endYearInternal,unitNatureVo.getSmSchoolmate().getSmEducation().getSchool());
		}
		/**获取校友政治面貌*/
		SysDict eduDict = new SysDict();
		eduDict.setTypeCode("POLITICS_NAME");
		List<SysDict> SysDictList = sysDictMapper.getSysDictByTypecode(eduDict);
		/**获取刷选条件下的校友信息*/
		List<Map<String, String>> list = statisticsMapper.loadListByStaticCondition(unitNatureVo.getSmSchoolmate());

		/**获取各个政治面貌的校友人数*/
		List<UnitNature> result = new ArrayList<>();
		for (int i = 0; i < SysDictList.size(); i++) {
			if(list != null && list.size()>0){
				for (int j = 0; j < list.size(); j++) {
					if((SysDictList.get(i).getValue()).equals(list.get(j).get("PoliticsType"))){
						UnitNature un = new UnitNature();
						long sum = 0;
						double politicPercent = 0;
						un.setType(SysDictList.get(i).getLabel());
						un.setKey(SysDictList.get(i).getValue());
						String politicSum = String.valueOf(list.get(j).get("PoliticsSum"));
						sum = Long.valueOf(politicSum == null ? "0" : politicSum);
						String PercentValue = String.valueOf(list.get(j).get("politicPercent"));
						politicPercent = PercentValue == null ? 0 : Double.valueOf(PercentValue);
						un.setValue(sum);
						un.setValuePercent(politicPercent);
						result.add(un);
						break;
					}
				}

			}
		}

		long sum = 0;
		double politicPercent = 0;
		UnitNature un = new UnitNature();
		un.setType("不明");
		un.setKey("不明");
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j).get("PoliticsType") == null || "".equals(list.get(j).get("PoliticsType"))) {
				String politicSum = String.valueOf(list.get(j).get("PoliticsSum"));
				sum += Long.valueOf(politicSum == null || "".equals(politicSum) ? "0" : politicSum);
				String PercentValue = String.valueOf(list.get(j).get("politicPercent"));
				politicPercent += PercentValue == null || "".equals(PercentValue) ? 0 : Double.valueOf(PercentValue);
			}
		}
		un.setValue(sum);
		un.setValuePercent(politicPercent);
		result.add(un);

		return  result;
	}


	/**
	 * 处理起至年时间区间
	 */
	private void handlerYearInternal(SmSchoolmate smSchoolmate, String startYearInternal, String endYearInternal, String schoolId) {
		SmEducation smEducation = smSchoolmate.getSmEducation();

		if(null != schoolId){
			String[] schoolIds = schoolId.substring(schoolId.indexOf(",") + 1).split(",");
			if (schoolIds != null) {
				if (schoolIds.length == 5) {
					smEducation.setSchool(schoolIds[1]);
					smEducation.setCollege(schoolIds[2]);
					smEducation.setSeries(schoolIds[3]);
					smEducation.setSpecialty(schoolIds[4]);
				}else if(schoolIds.length == 4) {
					smEducation.setSchool(schoolIds[1]);
					smEducation.setCollege(schoolIds[2]);
					smEducation.setSeries(schoolIds[3]);
				}else if(schoolIds.length == 3) {
					smEducation.setSchool(schoolIds[1]);
					smEducation.setCollege(schoolIds[2]);
				}else if(schoolIds.length == 2)  {
					smEducation.setSchool(schoolIds[1]);
				}
			}
		}

		if(null != startYearInternal){
			String[] startDateList = startYearInternal.split("-");
			if (startDateList != null && startDateList.length > 1){
				smEducation.setStartYearInternalFirst(startDateList[0].trim());
				smEducation.setStartYearInternalSencond(startDateList[1].trim());
				smSchoolmate.setSmEducation(smEducation);
			}
		}
		if(null != endYearInternal){
			String[] endDateList = endYearInternal.split("-");
			if (endDateList != null && endDateList.length > 1){
				smEducation.setEndYearInternalFirst(endDateList[0].trim());
				smEducation.setEndYearInternalSencond(endDateList[1].trim());
				smSchoolmate.setSmEducation(smEducation);
			}
		}
	}

	/**
	 * 处理校友信息 获取现任职信息
	 */
	private List<SmSchoolmate> handlerSmSchoolmates(List<SmSchoolmate> smSchoolmates) {
		for (SmSchoolmate smSchoolmate : smSchoolmates) {
			List<SmProfession> smProfessions = smSchoolmate.getSmProfessions();
			if(null != smProfessions && smProfessions.size()> 1){
				for (int i = 0; i < smProfessions.size(); i++) {
					if("JOIN_IN".equals(smProfessions.get(i).getStatus())){
						smSchoolmate.setSmProfession(smProfessions.get(i));
						break;
					}
				}
			}
		}
		return  smSchoolmates;
	}


	/**
	 * 根据性别统计校友
	 */
	public List<DataSetForG2> smCountBySex(SmEducation smEducation){
		Map<String,List<SysDict>> dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
		List<SysDict> sexDicts = dictCache.get(GlobalStr.SEX_TYPECODE);
		List<DataSetForG2> dForG2s =  statisticsMapper.smCountBySex(smEducation);
		for (DataSetForG2 dG2 : dForG2s) {
			for (SysDict dict : sexDicts) {
				if(StringUtil.isEmpty(dG2.getProperty())) {
					dG2.setProperty("不明");
				}else{
					if(dG2.getProperty().equalsIgnoreCase(dict.getValue())) {
						dG2.setProperty(dict.getLabel());
					}
				}
			}
		}
		return dForG2s;
	}
}
