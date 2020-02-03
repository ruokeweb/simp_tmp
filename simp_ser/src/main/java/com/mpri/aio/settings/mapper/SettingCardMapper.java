package com.mpri.aio.settings.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingCard;


 /**   
 *  
 * @Description:  校友卡管理——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Fri Feb 15 10:15:16 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SettingCardMapper extends CrudMapper<SettingCard>{


	/**
	 *  根据等级获校友卡
	 */
	List<SettingCard> getSettingCardByLevel(SettingCard settingCard);
}