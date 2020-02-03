package com.mpri.aio.settings.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingUserCard;



 /**   
 *  
 * @Description:  用户校友卡——DAO
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Thu Nov 07 17:32:00 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface SettingUserCardMapper extends CrudMapper<SettingUserCard>{

	/**
	 * 退出校友会时删除该校友在此校友会下的所有校友卡
	 */
	void deleteUserCard(SettingUserCard settingUserCard);
	
	/**
	 * 通过用户id获取改用户的校友卡
	 */
	List<SettingUserCard> loadCardListByUserId(@Param("entity")SettingUserCard settingUserCard);
}