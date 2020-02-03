package com.mpri.aio.chart.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.chart.vo.TableVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 活动统计
 * @author zdl
 *
 */
@Mapper
public interface ActivityChartMapper {

	/**
	 * 活动报名统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param actActivity
	 * @return
	 */
	List<Map<String,Object>> actSignChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType") String groupType,
                                                @Param(value = "num") int[] num,
                                                @Param(value = "enddate") String enddate,
                                                @Param(value = "entity") ActActivity actActivity);
	/**
	 * 值年返校统计
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param actActivity
	 * @return
	 */
	List<Map<String,Object>> actBackChart(@Param(value = "dateColumn") String dateColumn, @Param(value = "groupType") String groupType,
										  @Param(value = "num") int[] num,
										  @Param(value = "enddate") String enddate, @Param(value = "entity")ActActivity actActivity);

	/**
	 * 活动人数排行
	 * @param tableVo
	 * @return
	 */
    Page<Map<String, Object>> getSignActNum(@Param(value = "entity") TableVo tableVo);

	/**
	 * 值年返校人数排行
	 * @param tableVo
	 * @return
	 */
	Page<Map<String, Object>> getSignActBackNum(@Param(value = "entity") TableVo tableVo);
}
