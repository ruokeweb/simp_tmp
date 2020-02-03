package com.mpri.aio.app.reg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.app.reg.model.SmSchoolmateProve;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * 相互认证的
 * @author Administrator
 *
 */
@Mapper
public interface SmSchoolmateProveMapper extends CrudMapper<SmSchoolmateProve>{
	
	void updateInfo(SmSchoolmateProve smSchoolmateProve);
	/**
	 * 判断是否认证或者认识
	 */
	SmSchoolmateProve judgeisCongnize(@Param("entity") SmSchoolmateProve smSchoolmateProve);
	
	/**
	 * 获取已经认识他的人
	 */
	List<SmSchoolmate> loadAllSmListBy(@Param("entity") SmSchoolmateProve smSchoolmateProve);
	
	
	List<Map> findCountByDay(SmSchoolmateProve smSchoolmateProve);
}
