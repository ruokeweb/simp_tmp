package com.mpri.aio.app.reg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mpri.aio.app.reg.mapper.SmSchoolmateProveMapper;
import com.mpri.aio.app.reg.model.SmSchoolmateProve;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * 
 * @Description: 校友认证信息表——Service
 * @Author: syp
 * 
 */
@Service
public class SmSchoolmateProveService extends CrudService<SmSchoolmateProveMapper, SmSchoolmateProve> {

	/**
	 * 获取认证他的校友
	 */
	public List<SmSchoolmate> loadAllSmListBy(SmSchoolmateProve smSchoolmateProve){
		return mapper.loadAllSmListBy(smSchoolmateProve);
	}
	
	/**
	 * 判断该校友是否已经被几人认证
	 */
	public Boolean judgeisNumProve(int num,SmSchoolmateProve smSchoolmateProve) {
		if(mapper.loadAllListBy(smSchoolmateProve).size() >= num) {
			return true;
		}
		return false;
	}
}
