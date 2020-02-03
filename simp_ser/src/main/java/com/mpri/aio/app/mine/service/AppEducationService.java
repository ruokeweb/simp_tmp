package com.mpri.aio.app.mine.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.mine.mapper.AppEducationMapper;
import com.mpri.aio.app.mine.vo.AppSmEduVo;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.system.common.GlobalStr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppEducationService {

    @Autowired
    AppEducationMapper appEducationMapper;

    public PageIo<SmEducation> loadByPage(int pageNo, int pageSize,SmEducation smEducation){
        PageHelper.startPage(pageNo, pageSize);
        Page<SmEducation> pageList = appEducationMapper.loadByPage(smEducation);
        PageIo<SmEducation> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public SmEducation save(SmEducation smEducation){
         if(null != smEducation.getId() && !"".equals(smEducation.getId()) && !"0".equals(smEducation.getId())){
             appEducationMapper.update(smEducation);
         }else{
             smEducation.preInsert();
             smEducation.setIsDefault(GlobalStr.NOT_DEFAULT);
             appEducationMapper.insert(smEducation);
         }
        return smEducation;
    }

    public SmEducation getById(SmEducation smEducation){
        return appEducationMapper.get(smEducation);
    }


    public void setDefault(SmEducation smEducation){
        appEducationMapper.setDefault(smEducation);
    }

    public void clearDefault(SmEducation smEducation){
        appEducationMapper.clearDefault(smEducation);
    }
    
    /**
     * 设置默认教育经历
     */
    @Transactional(readOnly = false)
    public void setDefalutEdu(SmEducation smEducation) {
    	smEducation.setIsDefault(GlobalStr.NOT_DEFAULT);
    	appEducationMapper.clearDefault(smEducation);
    	smEducation.setIsDefault(GlobalStr.IS_DEFAULT);
    	appEducationMapper.setDefault(smEducation);
    }

    public List<AppSmEduVo> getEduList(SmEducation smEducation) {
        return appEducationMapper.getEduList(smEducation);
    }
}
