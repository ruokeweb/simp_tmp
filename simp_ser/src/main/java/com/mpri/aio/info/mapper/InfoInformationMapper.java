package com.mpri.aio.info.mapper;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.info.model.InfoInformation;


 /**   
 *  
 * @Description:  信息详情表——DAO
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Wed Dec 12 09:24:19 CST 2018
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface InfoInformationMapper extends CrudMapper<InfoInformation>{

	/**
	 * 更新阅读量
	 */
	void updateReadNum(String id);
	
	
	/**
	 * 更新评论数
	 */
	void updatemessageNum(String id);
}