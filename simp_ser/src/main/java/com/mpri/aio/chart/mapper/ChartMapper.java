package com.mpri.aio.chart.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * 新版统计Mapper
 * @author Administrator
 *
 */
@Mapper
public interface ChartMapper {

	
    /**
     * 根据入学年和院系专业统计校友
     */
    List<Map<String,Object>> smCountBarChart(@Param(value = "dateColumn") String dateColumn,
    		@Param(value = "countColumn") String countColumn,@Param(value = "groupType")String groupType,
    		@Param(value = "num")int[] num,
    		@Param(value = "enddate") String enddate,
    		@Param(value = "entity")SmSchoolmate schoolmate);
    
    
    
    List<Map<String, String>> qualificationsChart(@Param(value = "entity")SmSchoolmate schoolmate);

	/**
	 * 校友新增人数统计(统计按照性别)
	 * @param dateColumn
	 * @param countColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String, Object>> smNewNumberBySex(@Param(value = "dateColumn") String dateColumn, @Param(value = "countColumn")String countColumn,
											   @Param(value = "groupType")String groupType, @Param(value = "num")int[] num,
											   @Param(value = "enddate")String enddate, @Param(value = "entity")SmSchoolmate schoolmate);

	/**
	 * 校友工作单位性质统计
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> unitPropertyChart(@Param(value = "entity")SmSchoolmate schoolmate);

	/**
	 * 校友政治面貌统计
	 * @param smSchoolmate
	 * @return
	 */
	List<Map<String, Object>> politicCountenanceChart(@Param(value = "entity")SmSchoolmate smSchoolmate);

	/**
	 * 校友年龄段统计
	 * @param smSchoolmate
	 * @return
	 */
    List<Map<String, Object>> ageGroupChart(@Param("entity") SmSchoolmate smSchoolmate);
	/**
	 * 获取校友信息完整度统计
	 * @param smSchoolmate
	 * @return
	 */
    List<Map<String, String>> smCompleteChart(@Param("entity") SmSchoolmate smSchoolmate);

	/**
	 * 校友籍贯统计（热力地图）
	 * @param smSchoolmate
	 * @return
	 */
    List<Map<String, Object>> smNationPlaceChart(@Param("entity") SmSchoolmate smSchoolmate);

	/**
	 * 校友工作地点（热力地图）
	 * @param smSchoolmate
	 * @return
	 */
	List<Map<String, Object>> smWorkPlaceChart(@Param("entity")SmSchoolmate smSchoolmate);

}
