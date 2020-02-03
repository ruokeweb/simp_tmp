package com.mpri.aio.chart.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.chart.vo.TableVo;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 新版统计Mapper
 * @author Administrator
 *
 */
@Mapper
public interface DonationChartMapper {

	/**
	 * 捐赠金额统计
	 *
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param smSchoolmate
     * @return
	 */
	List<Map<String, Object>> donationMonthlyMoneyChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
												 @Param("num")int[] num,
												 @Param("enddate")String enddate, @Param("entity") SmSchoolmate smSchoolmate);
	/**
	 * 捐赠总金额统计
	 *
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param smSchoolmate
	 * @return
	 */
	List<Map<String, Object>> donationTotalMoneyChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
													  @Param("num")int[] num,
													  @Param("enddate")String enddate, @Param("entity") SmSchoolmate smSchoolmate);
	/**
	 * 捐赠次数统计
	 *
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param smSchoolmate
     * @return
	 */
	List<Map<String, Object>> donationMonthlyCountChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
												 @Param("num")int[] num,
												 @Param("enddate")String enddate, @Param("entity") SmSchoolmate smSchoolmate);
	/**
	 * 捐赠总次数统计
	 *
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param smSchoolmate
	 * @return
	 */
	List<Map<String, Object>> donationTotalCountChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
														@Param("num")int[] num,
														@Param("enddate")String enddate, @Param("entity") SmSchoolmate smSchoolmate);
	/**
	 * 项目类型金额统计
	 * @param donRecord
	 * @return
	 */
	List<Map<String, String>> donationProjectTypeChart( @Param("entity")DonRecord donRecord);

	/**
	 * 捐赠项目金额统计
	 * @param donRecord
	 * @return
	 */
    List<Map<String, Object>> donationProjectMoneyChart(@Param("entity")DonRecord donRecord);
	/**
	 * 捐赠项目次数统计
	 * @param donRecord
	 * @return
	 */
	List<Map<String, Object>> donationProjectCountChart(@Param("entity")DonRecord donRecord);
	/**
	 * 不同龄校友捐赠统计
	 * @param donRecord
	 * @return
	 */
	List<Map<String, Object>> donationAgeGroupChart(@Param("entity")DonRecord donRecord);
	/**
	 * 捐赠金额区间统计
	 * @param donRecord
	 * @return
	 */
	List<Map<String, Object>> donationSectionMoneyChart(@Param("entity")DonRecord donRecord);
    /**
     * 捐赠笔数金额区间统计
     * @param donRecord
     * @return
     */
    List<Map<String, Object>> donationSectionCountChart(@Param("entity") DonRecord donRecord);
	/**
	 * 一起捐金额统计
	 *
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param donRecord
	 * @return
	 */
	List<Map<String, Object>> donationTogetherMoneyChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
														@Param("num")int[] num,
														@Param("enddate")String enddate, @Param("entity") DonRecord donRecord);
	/**
	 * 一起捐次数统计
	 *
	 * @param dateColumn
	 * @param groupType
	 * @param num
	 * @param enddate
	 * @param donRecord
	 * @return
	 */
	List<Map<String, Object>> donationTogetherCountChart(@Param("dateColumn") String dateColumn, @Param("groupType")String groupType,
														@Param("num")int[] num,
														@Param("enddate")String enddate, @Param("entity") DonRecord donRecord);

	/**
	 * 获取校友总数
	 * @param smSchoolmate
	 * @return
	 */
    int getAllSmSchoolmate(@Param("entity") SmSchoolmate smSchoolmate);

	/**
	 * 获取活跃校友数
	 * @param donRecord
	 * @return
	 */
	int getloginSignSchoolmate(@Param("entity") DonRecord donRecord);
	/**
	 * 获取捐赠校友数
	 * @param donRecord
	 * @return
	 */
	int getDonationSchoolmate(@Param("entity") DonRecord donRecord);
	/**
	 * 获取捐赠校友数
	 * @param donRecord
	 * @return
	 */
	int getDonationSuccessSchoolmate(@Param("entity") DonRecord donRecord);

	/**
	 * 捐赠人金额排行
	 * @param tableVo
	 * @return
	 */
	Page<Map<String, Object>> getDonationMoney(@Param("entity") TableVo tableVo);

	/**
	 * 捐赠人次数排行
	 * @param tableVo
	 * @return
	 */
	Page<Map<String, Object>> getDonationCount(@Param("entity") TableVo tableVo);

	/**
	 * 捐赠项目金额排行
	 * @param tableVo
	 * @return
	 */
	Page<Map<String, Object>> getDonationProject(@Param("entity") TableVo tableVo);
}
