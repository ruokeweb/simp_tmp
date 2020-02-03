package com.mpri.aio.donation.mapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecordVo;
import com.mpri.aio.donation.vo.DonStatement;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.donation.model.DonRecord;


 /**   
 *  
 * @Description:  捐赠记录管理——DAO
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Wed Aug 29 15:09:37 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface DonRecordMapper extends CrudMapper<DonRecord>{

    /**
     * 获取校友捐赠金额书
     * @param sysUser
     * @return
     */
    String getMoneySum(DonRecord donRecord);

    /**
     * 获取捐赠次数
    * <p>Title: getCountById</p>  
    * <p>Description: </p>  
    * @param donRecord
    * @return
     */
	Integer getCountByProId(DonRecord donRecord);
	
	/**
	 * 根据订单编号修改订单支付状态
	* <p>Title: updateBycustomid</p>  
	* <p>Description: </p>  
	* @param donRecord
	 */
	void updateBycustomid(DonRecord donRecord);

	/**
	 * 根据订单编号去查询当前订单
	* <p>Title: getCountByCustomId</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return
	 */
	DonRecord getByCustomId(DonRecord donRecord);
	
	
	/**
	 * 验证是否是当天首次捐赠
	* <p>Title: isFirstDon</p>  
	* <p>Description: </p>  
	* @param donRecord
	* @return
	 */
	List<DonRecord> isFirstDon(@Param("entity") DonRecord donRecord);


	 /**
	  * 验证是否是当天首次捐赠
	  * <p>Title: isFirstDon</p>
	  * <p>Description: </p>
	  * @param donRecord
	  * @return
	  */
	 List<DonRecord> loadByPageSchool(@Param("entity") DonRecord donRecord);

     Page<DonRecord> getPageInfo(@Param("entity")DonRecord donRecord);

     Page<DonRecord> getListNoSchoolmate(@Param("entity")DonRecord donRecord);

     List<DonRecordVo> loadAllListByDay(@Param("entity")DonProject donProject);

	 BigDecimal loadAllmoneyByDay(@Param("entity")DonProject donProject);
	 
	 /**
	  * 获取某日或者某个日期段
	  * @param donProject
	  * @return
	  */
	 List<DonRecordVo> loadListByDate(@Param("entity")DonRecord donRecord);
	 
	 
	 /**
	  * 获取某段日期的
	  * @param donProject
	  * @return
	  */
	 BigDecimal loadCountByDate(@Param("entity")DonRecord donRecord);
	 
	 
	 /**
	  * 获取某段时间的捐赠记录
	  */
	 List<DonStatement> loadRecordByDate(@Param("entity")DonRecord donRecord);
	 
	 
	 /**
	  * 获取没有捐赠项目的捐赠记录
	  */
	 Page<DonRecord> loadRecordBy(@Param("entity")DonRecord donRecord);
	 
	 
	 
	 int loadRecordCountBy(@Param("entity")DonRecord donRecord);
	 
	 
	 
	 
	 List<Map> findTimesByDay(@Param("entity")DonRecord donRecord);
	 
	 
	 List<Map> findCountByDay(@Param("entity")DonRecord donRecord);
 }