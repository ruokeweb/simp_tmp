package com.mpri.aio.chart.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpri.aio.chart.mapper.ChartMapper;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.mapper.SysDictMapper;
import com.mpri.aio.system.model.SysDict;

/**
 * 新版统计Sevice
 * @author Administrator
 *
 */
@Service
public class ChartService {

	@Autowired
	private ChartMapper chartMapper;
	
	@Autowired
	SysDictMapper sysDictMapper;

	@Autowired
	private RedisCacheService redisCacheService;

	private Map<String,List<SysDict>> dictCache;

	private Map<String, SysArea> areaCache;

	private void InitMaps() {
		dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
	}
	private void InitAreaMaps() {
		areaCache= (Map<String,SysArea>)redisCacheService.getBaseCache(new SysDict(), InitCache.AREA_BASE_CACHE);
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
	public List<Map<String, Object>> smCountBarChart(String dateColumn, String countColumn, String groupType,
													 int num, String enddate,SmSchoolmate schoolmate) {
		return chartMapper.smCountBarChart(dateColumn, countColumn, groupType, new int[num],enddate,schoolmate);
	}
	
	
	/**
	 * 校友学历统计
	 */	
	public List<UnitNature> qualificationsChart(SmSchoolmate schoolmate) {

		/**获取学历信息性质*/
		SysDict eduDict = new SysDict();
		eduDict.setTypeCode("EDU_RECORD");
		List<SysDict> SysDictList = sysDictMapper.getSysDictByTypecode(eduDict);

		/**获取刷选条件下的校友信息*/
		List<Map<String, String>> list = chartMapper.qualificationsChart(schoolmate);
		
		/*校友总人数*/
		Long count = getCount(list);
		
		/**获取各个单位性质下的校友人数*/
		List<UnitNature> result = new ArrayList<>();
		for (int i = 0; i < SysDictList.size(); i++) {
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					if((SysDictList.get(i).getValue()).equals(list.get(j).get("eduRecord"))){
						if(GlobalStr.EDU_RECORD_EDP.equals(list.get(j).get("eduRecord")) ) {
							break;
						}
						UnitNature un = new UnitNature();
						long num = 0 ;
						un.setType(SysDictList.get(i).getLabel());
						un.setKey(SysDictList.get(i).getValue());
						String smsum = String.valueOf(list.get(j).get("count"));
						num = Long.valueOf(smsum == null || "".equals(smsum) || "null".equals(smsum)? "0" : smsum);
						Double percentValue = Double.valueOf(Double.valueOf(num)/Double.valueOf(count));
						un.setValuePercent(percentValue);
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
				if(list.get(j).get("eduRecord")==null || "".equals(list.get(j).get("eduRecord")) 
						|| "null".equals(list.get(j).get("eduRecord"))
						|| GlobalStr.EDU_RECORD_EDP.equals(list.get(j).get("eduRecord"))){
					String smsum = String.valueOf(list.get(j).get("count"));
					num += Long.valueOf(smsum == null || "".equals(smsum) || "null".equals(smsum)? "0" : smsum);
				}
			}
			Double percentValue = Double.valueOf(Double.valueOf(num)/Double.valueOf(count));
			un.setValuePercent(percentValue);
			un.setValue(num);
			result.add(un);
		}
		return  result;
	}
	/**
	 * 校友新增人数统计
	 * @param dateColumn
	 * @param countColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @return
	 */
	public List<Map<Object, Object>> smNewNumberBySex(String dateColumn, String countColumn, String groupType,
													 int num, String enddate,SmSchoolmate schoolmate) {
		List<Map<String, Object>> list = chartMapper.smNewNumberBySex(dateColumn, countColumn, groupType, new int[num], enddate, schoolmate);
		Map<Object, Object> menList = new HashMap<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date date0 = null;
		try {
			date0 = format.parse( enddate );
		} catch (ParseException e) {
			System.out.println(e);
		}
		menList.put("name",GlobalStr.SEX_NAME_MALE);
		menList.put(format.format(date0),0);
		Map<Object, Object> womenList = new HashMap<>();
		womenList.put("name",GlobalStr.SEX_NAME_FEMALE);
		womenList.put(format.format(date0),0);
		Map<Object, Object> unKnownList = new HashMap<>();
		unKnownList.put("name","不明");
		unKnownList.put(format.format(date0),0);
		if(list!=null){
			for (Map<String, Object> map :list) {
				if((map.get("sex")==null)||"".equals(map.get("sex"))){
					Object date = unKnownList.get(map.get("date"));
					if(date!=null){
						unKnownList.put(map.get("date"),Integer.parseInt(date.toString())+Integer.parseInt(map.get("count").toString()));
					}else{
						unKnownList.put(map.get("date"),map.get("count"));
					}

				}else{
					if(map.get("sex").equals(GlobalStr.SEX_CODE_MALE)){
						menList.put(map.get("date"),map.get("count"));
					}else if(map.get("sex").equals(GlobalStr.SEX_CODE_FEMALE)){
						womenList.put(map.get("date"),map.get("count"));
					}
				}

			}
		}
		List<Map<Object, Object>> returnList = new ArrayList<>();
		returnList.add(menList);
		returnList.add(womenList);
		returnList.add(unKnownList);
		return returnList;
	}
	
	public Long getCount(List<Map<String, String>> list) {
		Long count = 0L;
		for (Map<String, String> map : list) {
			String smsum = String.valueOf(map.get("count"));
			count += Long.valueOf(smsum == null || "".equals(smsum) || "null".equals(smsum)? "0" : smsum);
		}
		return count;
	}

	/**
	 * 校友工作单位性质统计
	 * @param schoolmate
	 * @return
	 */
	public Map<String,Object> unitPropertyChart(SmSchoolmate schoolmate) {
		this.InitMaps();
		List<SysDict> sds=(List<SysDict>)dictCache.get(GlobalStr.COMPANY_TPYE);//获取所有的单位性质
		Map<String,Object> returnMap = new HashMap<>();
		List<Map<String, Object>> list = chartMapper.unitPropertyChart(schoolmate);
		Map<Object, Object> menList = new HashMap<>();
		menList.put("name",GlobalStr.SEX_NAME_MALE);
		menList.put("其它",0);
		Map<Object, Object> womenList = new HashMap<>();
		womenList.put("name",GlobalStr.SEX_NAME_FEMALE);
		womenList.put("其它",0);
		Map<Object, Object> unKnownList = new HashMap<>();
		unKnownList.put("name","不明");
		unKnownList.put("其它",0);
		List<String> nature_name = new ArrayList<>();
		nature_name.add("其它");
		if(sds!=null){
			for (SysDict sysDict:sds) {
				nature_name.add(sysDict.getLabel());
			}
		}
		if(list!=null){
			for (Map<String, Object> map :list) {
				int cou= 0;
				Object countMap = map.get("count");
				if(countMap!=null){
					cou=Integer.parseInt(countMap.toString());
				}
				int numMen = 0;
				int numWomen = 0;
				int numUn = 0;
				Object nature = map.get("nature");
				if(nature==null||"".equals(nature.toString())){
					nature="其它";
				}else{
					nature=setFormatDictValue(nature.toString(),GlobalStr.COMPANY_TPYE);
				}
				Object numberMen = menList.get(nature);//获取已经存进去的
				if(numberMen!=null){
					numMen = Integer.parseInt(numberMen.toString());
				}
				Object numberWomen = womenList.get(nature);//获取已经存进去的
				if(numberWomen!=null){
					numWomen = Integer.parseInt(numberWomen.toString());
				}
				Object numberUn = unKnownList.get(nature);//获取已经存进去的
				if(numberUn!=null){
					numUn = Integer.parseInt(numberUn.toString());
				}

				if((map.get("sex")==null)){
					unKnownList.put(nature,numUn+cou);
				}else{
					if(map.get("sex").equals(GlobalStr.SEX_CODE_MALE)){
						menList.put(nature,numMen+cou);
					}else if(map.get("sex").equals(GlobalStr.SEX_CODE_FEMALE)){
						womenList.put(nature,numWomen+cou);
					}else{
						unKnownList.put(nature,numUn+cou);
					}
				}

			}
		}
		List<Map<Object, Object>> returnList = new ArrayList<>();
		returnList.add(menList);
		returnList.add(womenList);
		returnList.add(unKnownList);
		returnMap.put("names",nature_name);
		returnMap.put("data",returnList);
		return returnMap;
	}


	/**
	 * 校友政治面貌统计
	 * @param smSchoolmate
	 * @return
	 */
	public List<Map<Object, Object>> politicCountenanceChart(SmSchoolmate smSchoolmate) {
		this.InitMaps();
		List<Map<Object, Object>> returnList = new ArrayList<>();
		List<Map<String, Object>> list = chartMapper.politicCountenanceChart(smSchoolmate);
		List<SysDict> sds=(List<SysDict>)dictCache.get(GlobalStr.POLITICS_NAME);//获取所有的政治面貌

		if(sds!=null){
			for (SysDict dict :sds){//从缓存中获取所有的政治面貌
				Object politics = dict.getValue();//获取当前政治面貌的vlaue

				Map<Object, Object> politicsMap = new HashMap<>();//新建返回数据的map
				politicsMap.put("politics",dict.getLabel());
				politicsMap.put(setFormatDictValue(GlobalStr.SEX_CODE_MALE, GlobalStr.SEX_TYPECODE),0);
                politicsMap.put(setFormatDictValue(GlobalStr.SEX_CODE_FEMALE, GlobalStr.SEX_TYPECODE),0);
                politicsMap.put("不明",0);
				boolean flag =true;
				if(list!=null&&politics!=null&&!"".equals(politics.toString())){

					for (Map<String, Object> map:list) {
						if(map.get("politics")!=null&&politics.toString().equals(map.get("politics").toString())){
							flag = false;
							int num= 0;
							Object countMap = map.get("count");
							if(countMap!=null){
								num=Integer.parseInt(countMap.toString());
							}
							if((map.get("sex")==null||"".equals(map.get("sex").toString()))){
								int cou=0;
								Object count  = politicsMap.get("不明");
								if(count!=null){
									cou =Integer.parseInt(count.toString());
								}
								politicsMap.put("不明",cou+num);
							}else{
								if(map.get("sex").equals(GlobalStr.SEX_CODE_MALE)){
									int cou=0;
									String s = setFormatDictValue(map.get("sex").toString(), GlobalStr.SEX_TYPECODE);
									Object count  = politicsMap.get(s);
									if(count!=null){
										cou =Integer.parseInt(count.toString());
									}
									politicsMap.put(s,cou+num);
								}else if(map.get("sex").equals(GlobalStr.SEX_CODE_FEMALE)){
									int cou=0;
									String s = setFormatDictValue(map.get("sex").toString(), GlobalStr.SEX_TYPECODE);
									Object count  = politicsMap.get(s);
									if(count!=null){
										cou =Integer.parseInt(count.toString());
									}
									politicsMap.put(s,cou+num);
								}else{
									int cou=0;
									Object count  = politicsMap.get("不明");
									if(count!=null){
										cou =Integer.parseInt(count.toString());
									}
									politicsMap.put("不明",cou+num);
								}
							}


						}
					}
				}
				returnList.add(politicsMap);
			}
		}
		Map<Object, Object> politicsMap = new HashMap<>();
		politicsMap.put("politics","其它");


		for (Map<String, Object> map:list) {
			int num= 0;
			Object countMap = map.get("count");
			if(countMap!=null){
				num=Integer.parseInt(countMap.toString());
			}
			if(map.get("politics")==null||"".equals(map.get("politics").toString())){
				if((map.get("sex")==null||"".equals(map.get("sex").toString()))){
					int cou=0;
					Object count  = politicsMap.get("不明");
					if(count!=null){
						cou =Integer.parseInt(count.toString());
					}
					politicsMap.put("不明",cou+num);
				}else{
					if(map.get("sex").equals(GlobalStr.SEX_CODE_MALE)){
						int cou=0;
						String s = setFormatDictValue(map.get("sex").toString(), GlobalStr.SEX_TYPECODE);
						Object count  = politicsMap.get(s);
						if(count!=null){
							cou =Integer.parseInt(count.toString());
						}
						politicsMap.put(s,cou+num);
					}else if(map.get("sex").equals(GlobalStr.SEX_CODE_FEMALE)){
						int cou=0;
						String s = setFormatDictValue(map.get("sex").toString(), GlobalStr.SEX_TYPECODE);
						Object count  = politicsMap.get(s);
						if(count!=null){
							cou =Integer.parseInt(count.toString());
						}
						politicsMap.put(s,cou+num);
					}else{
						int cou=0;
						Object count  = politicsMap.get("不明");
						if(count!=null){
							cou =Integer.parseInt(count.toString());
						}
						politicsMap.put("不明",cou+num);
					}
				}
			}

		}
		returnList.add(politicsMap);
		return returnList;
	}

	/**
	 * 校友年龄段统计
	 * @param smSchoolmate
	 * @return
	 */
	public List<Map<Object, Object>> ageGroupChart(SmSchoolmate smSchoolmate) {
		this.InitMaps();
		List<Map<String, Object>> list = chartMapper.ageGroupChart(smSchoolmate);
		List<Map<Object, Object>> returnList = new ArrayList<>();
		Map<Object, Object> menList = new HashMap<>();
		menList.put("name",GlobalStr.SEX_NAME_MALE);
		Map<Object, Object> womenList = new HashMap<>();
		womenList.put("name",GlobalStr.SEX_NAME_FEMALE);
		Map<Object, Object> unKnownList = new HashMap<>();
		unKnownList.put("name","不明");
		System.out.println(list);
		for (int i =1 ;i<9;i++){
			int[] yearGroupByArg=new int[2];
			String argGroup ="";
			//Map<Object, Object> returnMap = new HashMap<>();

			if(i==1){
				yearGroupByArg = getYearGroupByArg(0, (i * 10 + 9));
				argGroup="20岁之前";
			}else if(i==8){
				yearGroupByArg = getYearGroupByArg(i * 10);
				argGroup="80岁之后";
			}else{
				yearGroupByArg = getYearGroupByArg(i * 10, (i * 10 + 9));
				argGroup=i * 10+"-"+(i * 10 + 9)+"岁";
			}
			menList.put(argGroup,0);
			womenList.put(argGroup,0);
			unKnownList.put(argGroup,0);
			if(list!=null){
				for (Map<String, Object> map :list) {
					Object birthday = map.get("birthday");
					int cou= 0;
					Object countMap = map.get("count");
					if(countMap!=null){
						cou=Integer.parseInt(countMap.toString());
					}
					if(birthday!=null &&!"".equals(birthday.toString())){
						if((yearGroupByArg[0]<=Integer.parseInt(birthday.toString()))&&(yearGroupByArg[1]>=Integer.parseInt(birthday.toString()))){
							Object number=new Object();
							int num=0;
							if((map.get("sex")==null||"".equals(map.get("sex").toString()))){
								number = unKnownList.get(argGroup);//获取已经存进去的
								if(number!=null){
									num = Integer.parseInt(number.toString());
								}
								unKnownList.put(argGroup,cou+num);
							}else{
								if(map.get("sex").equals(GlobalStr.SEX_CODE_MALE)){
									number = menList.get(argGroup);//获取已经存进去的
									if(number!=null){
										num = Integer.parseInt(number.toString());
									}
									menList.put(argGroup,cou+num);
								}else if(map.get("sex").equals(GlobalStr.SEX_CODE_FEMALE)){
									number = womenList.get(argGroup);//获取已经存进去的
									if(number!=null){
										num = Integer.parseInt(number.toString());
									}
									womenList.put(argGroup,cou+num);
								}else{
									number = unKnownList.get(argGroup);//获取已经存进去的
									if(number!=null){
										num = Integer.parseInt(number.toString());
									}
									unKnownList.put(argGroup,cou+num);
								}
							}


						}
					}
				}

			}

		}
		returnList.add(menList);
		returnList.add(womenList);
		returnList.add(unKnownList);
		return returnList;
	}

	/**
	 * 获取校友信息完整度统计
	 * @param smSchoolmate
	 * @return
	 */
	public List<UnitNature> smCompleteChart(SmSchoolmate smSchoolmate) {
		List<Map<String, String>>  list = chartMapper.smCompleteChart(smSchoolmate);
		Long count = getCount(list);
		/**获取各个星级下的校友人数*/
		List<UnitNature> result = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (Map<String, String> map:list) {
				UnitNature un = new UnitNature();
				long num = 0 ;
				if(map.get("complete")!=null&&!"".equals(map.get("complete"))){

					un.setType(String.valueOf(map.get("complete"))+"星");
					un.setKey(String.valueOf(map.get("complete")));

				}else{
					un.setType("不明");
					un.setKey("0");
				}
				String smsum = String.valueOf(map.get("count"));
				num = Long.valueOf(smsum == null || "".equals(smsum) || "null".equals(smsum)? "0" : smsum);
				Double percentValue = Double.valueOf(Double.valueOf(num)/Double.valueOf(count));
				un.setValuePercent(percentValue);
				un.setValue(num);
				result.add(un);
			}
		}

		return result;
	}

	/**
	 * 校友籍贯统计（热力地图）
	 * @param smSchoolmate
	 * @return
	 */
	public List<Map<String, Object>> smNationPlaceChart(SmSchoolmate smSchoolmate) {
//		InitAreaMaps();
		List<Map<String, Object>> list = chartMapper.smNationPlaceChart(smSchoolmate);
//		System.out.println(list);
//		if(list!=null){
//			for (Map<String, Object> map :list) {
//				if(map.get("province")!=null&&!"".equals(map.get("province"))){
//					map.put("province",areaCache.get(map.get("province").toString()).getName());
//				}
//			}
//		}
		return list;
	}

	/***
	 * 工作地点
	 * @param smSchoolmate
	 * @return
	 */
	public List<Map<String, Object>> smWorkPlaceChart(SmSchoolmate smSchoolmate) {
		SmProfession smProfession = new SmProfession();
		smProfession.setStatus(GlobalStr.PROFESSION_STATUS_JOIN_IN);
		smSchoolmate.setSmProfession(smProfession);
		List<Map<String, Object>> list = chartMapper.smWorkPlaceChart(smSchoolmate);
		return list;
	}

	/**
	 * 根据code获取label
	 * @param value
	 * @param typeCode
	 * @return
	 */
	private String setFormatDictValue(String value,String typeCode) {
		if(!StringUtil.isEmpty(value)) {
			//key: redis内key的规则：type:value
			List<SysDict> sds=(List<SysDict>)dictCache.get(typeCode);
			if(!sds.isEmpty()){
				for(SysDict dict : sds) {
					if(value.equals(dict.getValue())) {
						return dict.getLabel();
					}
				}
			}
		}
		return "";
	}
	/**
	 * 根据年龄段获取年份 0-19 20-29 30-39 40-49 50-59 60-69 70-79 80-150
	 */
	private int[] getYearGroupByArg(int startArg,int endArg){
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		int[] years =new int[2];
		years[0]=Integer.parseInt(year)-endArg;
		years[1]=Integer.parseInt(year)-startArg;
		return years;
	}
	private int[] getYearGroupByArg(int startArg){
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		int[] years =new int[2];
		years[0]=0;
		years[1]=Integer.parseInt(year)-startArg;
		return years;
	}



}
