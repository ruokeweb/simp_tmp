package com.mpri.aio.chart.mapper;

import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 校友活跃度统计
 * @author zdl
 *
 */
@Mapper
public interface ActiveChartMapper {

	/**
	 * 月活跃校友统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> activeMonthlyChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType")String groupType,
											 @Param(value = "num")int[] num,
											 @Param(value = "enddate") String enddate,
											 @Param(value = "entity")SmSchoolmate schoolmate);
	/**
	 * 日活跃校友统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> activeEverydayChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType")String groupType,
												@Param(value = "num")int[] num,
												@Param(value = "enddate") String enddate,
												@Param(value = "entity")SmSchoolmate schoolmate);
	/**
	 * 捐赠人数统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> donationMonthlyChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType")String groupType,
												@Param(value = "num")int[] num,
												@Param(value = "enddate") String enddate,
												@Param(value = "entity")SmSchoolmate schoolmate ,@Param("donState") String donState);
	/**
	 * 活动参与人数统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> activityMonthlyChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType")String groupType,
												@Param(value = "num")int[] num,
												@Param(value = "enddate") String enddate,
												@Param(value = "entity")SmSchoolmate schoolmate  );
	/**
	 * 祝福人数统计（三折线）
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param schoolmate
	 * @return
	 */
	List<Map<String,Object>> smWishMonthlyChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType")String groupType,
												@Param(value = "num")int[] num,
												@Param(value = "enddate") String enddate,
												@Param(value = "entity")SmSchoolmate schoolmate ,@Param("isshow") String isshow);
}
