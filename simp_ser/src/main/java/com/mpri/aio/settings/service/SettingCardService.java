package com.mpri.aio.settings.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.model.SettingCard;
import com.mpri.aio.settings.mapper.SettingCardMapper;

 /**   
 *  
 * @Description:  校友卡管理——Service
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Feb 15 10:15:16 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SettingCardService extends CrudService<SettingCardMapper, SettingCard>  {


	/**
	 *  根据等级获校友卡
	 */
	public List<SettingCard>  getSettingCardByLevel(SettingCard settingCard) {
		return mapper.getSettingCardByLevel(settingCard);
	}
}