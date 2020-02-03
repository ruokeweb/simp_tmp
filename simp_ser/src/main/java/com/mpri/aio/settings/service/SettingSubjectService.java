package com.mpri.aio.settings.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.settings.mapper.SettingSubjectMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  
 * @Description:  学科管理——Service
 * @Author:       zdl
 * @project 	  smmp 
 * @CreateDate:   Tue Feb 19 16:23:21 CST 2019
 * @Version:      v_1.2
 *    
 */
@Service
public class SettingSubjectService extends CrudService<SettingSubjectMapper, SettingSubject>  {

    @Transactional(readOnly = false)
     public void deleteAllChildrenByParentId(SettingSubject settingSubject) {
         realDelete(settingSubject);
     }
    private void  realDelete(SettingSubject settingSubject){
        SettingSubject setting = new SettingSubject();
        setting.setParentId(settingSubject.getId());
        List<SettingSubject> children = getChildren(setting);
        boolean b = ifChildren(children);
        if(b){
            for (SettingSubject settingSubject1:children ){
                realDelete(settingSubject1);

            }
        }

        mapper.delete(settingSubject);
       //    System.out.println("需要删除=============="+settingSubject.getId());
    }
    /**
     * 根据id获取子集
     * @param settingSubject
     * @return
     */
    private List<SettingSubject> getChildren(SettingSubject settingSubject){
        return mapper.loadAllListBy(settingSubject);
    }

    /**
     * 是否存在字集
     * @param settingSubjects
     * @return
     */
    private boolean ifChildren(List<SettingSubject> settingSubjects){
        if(settingSubjects!=null&&!settingSubjects.isEmpty()){
            return true;
        }
        return false;
    }
 }