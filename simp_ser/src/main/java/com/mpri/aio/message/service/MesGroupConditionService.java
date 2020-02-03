package com.mpri.aio.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.mapper.MesGroupConditionMapper;
import com.mpri.aio.message.model.MesGroupCondition;
import com.mpri.aio.message.vo.MesGroupConditionEnum;
import com.mpri.aio.message.vo.MesGroupConditionVo;

 /**   
 *  
 * @Description:  信息群组条件——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Wed Nov 14 15:50:23 CST 2018
 * @Version:      v_1.02
 *    
 */
@Service
public class MesGroupConditionService extends CrudService<MesGroupConditionMapper, MesGroupCondition>  {
	
	//存入新的条件
	@Transactional(readOnly = false)
	public void saveInfo(MesGroupConditionVo mesGroupConditionVo) {
		//删除
		MesGroupCondition entity = new MesGroupCondition();
		entity.setGroupId(mesGroupConditionVo.getGroupId());
		mapper.delete(entity);
		//插入
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getDepartment())) {
			this.save(new MesGroupCondition( 
				mesGroupConditionVo.getGroupId(),
				MesGroupConditionEnum.DEPARTMENT.getCode(),
				mesGroupConditionVo.getDepartment()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getSex())) {
			this.save(new MesGroupCondition( 
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.SEX.getCode(),
					mesGroupConditionVo.getSex()
				));
		}
		/*if(StringUtil.isNotEmpty(mesGroupConditionVo.getType())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.TYPE.getCode(),
					mesGroupConditionVo.getType()
			));
		}*/

		//新增群组条件
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getProvince())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.PROVINCE.getCode(),
					mesGroupConditionVo.getProvince()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getCountry())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.COUNTRY.getCode(),
					mesGroupConditionVo.getCountry()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getCity())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.CITY.getCode(),
					mesGroupConditionVo.getCity()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getStartdate())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.STARTDATE.getCode(),
					mesGroupConditionVo.getStartdate()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getEnddate())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.ENDDATE.getCode(),
					mesGroupConditionVo.getEnddate()
			));
		}
		if(StringUtil.isNotEmpty(mesGroupConditionVo.getSchoolmatemark())) {
			this.save(new MesGroupCondition(
					mesGroupConditionVo.getGroupId(),
					MesGroupConditionEnum.SCHOOLMATEMARK.getCode(),
					mesGroupConditionVo.getSchoolmatemark()
			));
		}
	}

	
	
}