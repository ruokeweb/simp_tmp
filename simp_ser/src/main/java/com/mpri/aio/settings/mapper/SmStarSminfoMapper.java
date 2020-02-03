package com.mpri.aio.settings.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.model.SmStarSminfo;


 /**   
 *  
 * @Description:  星级设置——DAO
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:52:29 CST 2018
 * @Version:      v_1.0
 *    
 */
@Mapper
public interface SmStarSminfoMapper extends CrudMapper<SmStarSminfo>{

	List<SmStarSminfo> getSmStarSminfosBySmStar(SmStar smStar);
	
	void deleteStarInfo(String id);
}