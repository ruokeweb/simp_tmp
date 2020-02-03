package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmPoliticsMapper;
import com.mpri.aio.schoolmate.model.SmPolitics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmPoliticsService {
    @Autowired
    private AppSmPoliticsMapper appSmPoliticsMapper;

    public List<SmPolitics> getPolitics(SmPolitics smPolitics) {
        return appSmPoliticsMapper.getPolitics(smPolitics);
    }

    public void deletePolitic(SmPolitics smPolitics) {
        appSmPoliticsMapper.deletePolitic(smPolitics);
    }

    public void savePolitic(SmPolitics smPolitics) {
        if (smPolitics.getIsNewRecord()) {
            smPolitics.preInsert();
            appSmPoliticsMapper.insert(smPolitics);
        } else {
            smPolitics.preUpdate();
            appSmPoliticsMapper.update(smPolitics);
        }
    }

    public SmPolitics getOne(SmPolitics smPolitics) {
        return appSmPoliticsMapper.getOne(smPolitics);
    }
}
