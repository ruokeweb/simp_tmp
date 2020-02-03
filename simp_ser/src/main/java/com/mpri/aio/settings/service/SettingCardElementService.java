package com.mpri.aio.settings.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.model.SettingCardElement;
import com.mpri.aio.settings.mapper.SettingCardElementMapper;

 /**   
 *  
 * @Description:  校友卡元素管理——Service
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Mon Feb 18 10:33:48 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SettingCardElementService extends CrudService<SettingCardElementMapper, SettingCardElement>  {


     public PageIo<SettingCardElement> loadByCardIdNotNo(int pageNo, int pageSize, SettingCardElement settingCardElement) {
         PageHelper.startPage(pageNo, pageSize);
         Page<SettingCardElement> pageList = mapper.loadByCardIdNotNo(settingCardElement);
         PageIo<SettingCardElement> pageInfo = new PageIo<SettingCardElement>(pageList);
         return pageInfo;

     }
 }