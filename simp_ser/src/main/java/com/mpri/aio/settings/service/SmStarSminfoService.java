package com.mpri.aio.settings.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.vo.StarInfoEnum;
import com.mpri.aio.settings.mapper.SmStarMapper;
import com.mpri.aio.settings.mapper.SmStarSminfoMapper;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.model.SmStarSminfo;

 /**   
 *  
 * @Description:  星级设置——Service
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:52:29 CST 2018
 * @Version:      v_1.0
 *    
 */
@Service
public class SmStarSminfoService extends CrudService<SmStarSminfoMapper, SmStarSminfo>  {

	@Autowired
	private SmStarMapper smStarMapper;
	
	/**
	 * 根据星级id获取星级配置
	* <p>Title: getSmStarSminfosBySmStar</p>  
	* <p>Description: </p>  
	* @param smStar
	* @return
	 */
	public List<SmStarSminfo> getSmStarSminfosBySmStar(SmStar smStar){
		return mapper.getSmStarSminfosBySmStar(smStar);
	}

	
	/**
	 * 检查库中没有的进行修改
	* <p>Title: saveSmStarInfo</p>  
	* <p>Description: </p>  
	* @param smStar
	* @param startInfoCodes
	 */
	public void saveSmStarInfo(SmStar smStar,List<String> startInfoCodes) {
		if(startInfoCodes.size() <= 0) {
			return;
		}else{
			this.deleteStarInfo(smStar.getId());
			for(String code : startInfoCodes) {
				this.executeSmStarInfo(smStar, code);
			}
		}
	}
	
	/**
	 * 新增
	* <p>Title: executeSmStarInfo</p>  
	* <p>Description: </p>  
	* @param smStar
	* @param code
	 */
	@Transactional(readOnly = false)
	public void executeSmStarInfo(SmStar smStar,String code) {
		StarInfoEnum sienum =  StarInfoEnum.getValue(code);
		SmStarSminfo sminfo = new SmStarSminfo();
		sminfo.setSmStarId(smStar.getId());
		sminfo.setSmSminfoCode(code);
		sminfo.setSmSminfoId(sienum.getDesc());
		sminfo.setRemark(sienum.getDesc());
		this.save(sminfo);
	}
	
	/**
	 * 删除之前配置的
	* <p>Title: deleteStarInfo</p>  
	* <p>Description: </p>  
	* @param smStar
	* @param code
	 */
	@Transactional(readOnly = false)
	public void deleteStarInfo(String id) {
		mapper.deleteStarInfo(id);
	}
	
	
	/**
	 * 获取所有信息
	* <p>Title: getInfo</p>  
	* <p>Description: </p>  
	* @param smStar
	* @return
	 */
	public SmStarSminfo getInfo(SmStar smStar) {
		smStar = smStarMapper.get(smStar);
		List<SmStarSminfo> sminfos  =  getSmStarSminfosBySmStar(smStar);
		SmStarSminfo sminfo = new SmStarSminfo();
		List<String> codes = new ArrayList<String>();
		for (SmStarSminfo smStarSminfo : sminfos) {
			codes.add(smStarSminfo.getSmSminfoCode());
		}
		sminfo.setSmStar(smStar);
		sminfo.setStartInfoCodes(codes);
		return sminfo;
	}
}