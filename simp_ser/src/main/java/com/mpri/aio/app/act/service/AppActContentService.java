package com.mpri.aio.app.act.service;

import com.mpri.aio.act.model.ActContent;
import com.mpri.aio.app.act.mapper.AppActContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppActContentService {

    @Autowired
    private AppActContentMapper appActContentMapper;
    /**
     * 判断是否报名
     * @param entity
     * @return
     */
    public int getNumByUserId(ActContent entity){
        return appActContentMapper.getNumByUserId(entity);
    }

    @Transactional(readOnly = false)
    public void saveActContent(List<ActContent> list) {
        appActContentMapper.saveActContent(list);
    }

    @Transactional(readOnly = false)
    public void insertOne(ActContent actContent) {
        appActContentMapper.insert(actContent);
    }
}
