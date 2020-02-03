package com.mpri.aio.settings.mapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.settings.model.SettingCardElement;
import org.apache.ibatis.annotations.Param;


/**
 *  
 * @Description:  校友卡元素管理——DAO
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Mon Feb 18 10:33:48 CST 2019
 * @Version:      v_1.2
 *    
 */
@Mapper
public interface SettingCardElementMapper extends CrudMapper<SettingCardElement>{


     Page<SettingCardElement> loadByCardIdNotNo(@Param("entity") SettingCardElement settingCardElement);
 }