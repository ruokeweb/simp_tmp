package com.mpri.aio.settings.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SmStar;


 /**   
 *  
 * @Description:  星级——DAO
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:53:53 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmStarMapper extends CrudMapper<SmStar>{
	
	/**
	 * 获取星级所有信息
	 */
	List<SmStar> loadInfoListBy(@Param("entity") SmStar smStar);
}