package com.mpri.aio.schoolmate.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 执行SQL
* <p>Title: InfoIntegrityMapper</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年9月13日
 */
@Mapper
public interface InfoIntegrityMapper {
	 
	Boolean BASEINFO(String sysUserId);
	 
	Boolean CONTACTINFO(String sysUserId);
	 
	Boolean ADDRESSINFO(String sysUserId);
	 
	Boolean EDUCATIONINFO(String sysUserId);

	Boolean POLITICSINFO(String sysUserId);

	Boolean FAMILYINFO(String sysUserId);

	Boolean SOCIALINFO(String sysUserId);

	Boolean EXPERIENCEINFO(String sysUserId);

	Boolean PROFESSIONINFO(String sysUserId);

	Boolean HONORINFO(String sysUserId);

	Boolean HISTORYDATAINFO(String sysUserId);
}
