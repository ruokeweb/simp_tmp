package com.mpri.aio.chart.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.chart.vo.DataSetForG2;
import com.mpri.aio.chart.vo.SmCountByArea;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.schoolmate.model.SmEducation;

@Mapper
public interface StatisticsMapper
{
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
    List<Map<String,Object>> groupByData(@Param(value = "tableName") String tableName,@Param(value = "dateColumn") String dateColumn,@Param(value = "countColumn") String countColumn,@Param(value = "groupType")String groupType, @Param(value = "num")int[] num,@Param(value = "querySQL")String sql);
    
    
    
    
    /**
     * 获取捐赠项目的捐赠人数及捐赠总额
    * <p>Title: getAllDonProFee</p>  
    * <p>Description: </p>  
    * @param donProject
    * @return
     */
    List<DonProject> getAllDonProFee(DonProject donProject);
    
    /**
     * .统计校友区域分布(目前仅限China)
    * <p>Title: smCountByArea</p>  
    * <p>Description: </p>  
    * @return
     */
    List<SmCountByArea> smCountByArea();
    
    /**
     * 根据入学年和院系专业统计校友
     */
    List<Map<String,Object>> smCountBarChart(@Param(value = "tableName") String tableName,@Param(value = "dateColumn") String dateColumn,
    		@Param(value = "countColumn") String countColumn,@Param(value = "groupType")String groupType,
    		@Param(value = "num")int[] num,@Param(value = "enddate") String enddate,
    		@Param(value = "entity")SmEducation smEducation);

    /**
     * 根据性别统计校友
     */
    List<DataSetForG2> smCountBySex(@Param(value = "entity")SmEducation smEducation);


	/**
	 *统计校友政治面貌
	 **/
	List<Map<String, String>> loadListByStaticCondition(@Param(value = "entity") SmSchoolmate smSchoolmate);

	/**
	 *统计校友学历信息
	 **/
	List<Map<String, String>> loadEducationListByStaticCondition(@Param(value = "entity") SmSchoolmate smSchoolmate);

	/**
	 *统计校友单位性质
	 **/
	List<Map<String, String>> loadNatureListByStaticCondition(@Param(value = "entity") SmSchoolmate smSchoolmate);
}
