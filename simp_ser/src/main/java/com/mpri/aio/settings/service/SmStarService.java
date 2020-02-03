package com.mpri.aio.settings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.mapper.SmStarMapper;
import com.mpri.aio.settings.model.SmStar;

 /**   
 *  
 * @Description:  星级——Service
 * @Author:       syp
 * @project 	  sm 
 * @CreateDate:   Wed Sep 12 10:53:53 CST 2018
 * @Version:      v_1.0
 *    
 */
@Service
public class SmStarService extends CrudService<SmStarMapper, SmStar>  {

	@Autowired
	private SmStarSminfoService smStarSminfoService;
	
	public List<SmStar> getSmStarInfos (SmStar smStar){
		List<SmStar> smStars = this.loadAllListBy(smStar);
		for (SmStar s : smStars) {
			s.setSmStarSminfos(smStarSminfoService.getSmStarSminfosBySmStar(s));
		}
		return smStars;
	}
	
	/**
	 * 获取星级所有信息
	 */
	public List<SmStar> loadInfoListBy(SmStar smStar){
		return mapper.loadInfoListBy(smStar);
	}
}