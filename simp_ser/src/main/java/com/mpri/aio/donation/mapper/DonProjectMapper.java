package com.mpri.aio.donation.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecord;


 /**   
 *  
 * @Description:  捐赠项目管理——DAO
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Wed Aug 29 15:22:22 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface DonProjectMapper extends CrudMapper<DonProject>{
	
	/**
	 * 新增捐赠钱数及人数
	* <p>Title: updateMoneyAndPer</p>  
	* <p>Description: </p>  
	* @param donRecord
	 */
	void addMoneyAndPer(DonRecord donRecord);
	
	
	/**
	 * 删除捐赠钱数及人数
	 */
	void delMoneyAndPer(DonRecord donRecord);
	
	/**
	 * 获取未结束的项目
	* <p>Title: loadAllListByStatus</p>  
	* <p>Description: </p>  
	* @param donProject
	* @return
	 */
	List<DonProject> loadAllListByStatus(DonProject donProject);

	/**
	 * 更新
	 * @param donRecord
	 */
	void updateMoneyAndPer(DonRecord donRecord);
	
}