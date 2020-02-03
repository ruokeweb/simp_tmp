package com.mpri.aio.app.mine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.mine.mapper.AppSmProfessionMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmProfession;

@Service
public class AppSmProfessionService {

    @Autowired
    AppSmProfessionMapper appSmProfessionMapper;

    public PageIo<SmProfession> loadByPage(int pageNo, int pageSize, SmProfession smProfession){
        PageHelper.startPage(pageNo, pageSize);
        Page<SmProfession> pageList = appSmProfessionMapper.loadByPage(smProfession);
        PageIo<SmProfession> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public SmProfession save(SmProfession smProfession){
        if(!smProfession.getIsNewRecord()){
            appSmProfessionMapper.update(smProfession);
        }else{
            smProfession.preInsert();
            appSmProfessionMapper.insert(smProfession);
        }
        return smProfession;
    }

    public SmProfession getById(SmProfession smProfession){
        return appSmProfessionMapper.get(smProfession);
    }
    
    public List<SmProfession> loadAllListBy(SmProfession smProfession){
    	return appSmProfessionMapper.loadAllListBy(smProfession);
    }

}
