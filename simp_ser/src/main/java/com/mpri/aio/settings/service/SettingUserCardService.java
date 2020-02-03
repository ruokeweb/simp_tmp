package com.mpri.aio.settings.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.model.SettingUserCard;
import com.mpri.aio.settings.mapper.SettingUserCardMapper;

 /**   
 *  
 * @Description:  用户校友卡——Service
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Thu Nov 07 17:32:00 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Service
public class SettingUserCardService extends CrudService<SettingUserCardMapper, SettingUserCard>  {


	/**
	 * 通过用户id获取该用户的校友卡
	 */
	public List<SettingUserCard> loadCardListByUserId(SettingUserCard settingUserCard){
		return mapper.loadCardListByUserId(settingUserCard);
	}
}