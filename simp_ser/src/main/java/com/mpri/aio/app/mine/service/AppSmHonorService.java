package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmHonorMapper;
import com.mpri.aio.schoolmate.model.SmHonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmHonorService {
    @Autowired
    private AppSmHonorMapper appSmHonorMapper;

    public SmHonor getOne(SmHonor smHonor) {
        return appSmHonorMapper.getOne(smHonor);
    }

    public List<SmHonor> getHonors(SmHonor smHonor) {
        return appSmHonorMapper.getHonors(smHonor);
    }

    public void saveHonor(SmHonor smHonor) {
        if (smHonor.getIsNewRecord()) {
            smHonor.preInsert();
            appSmHonorMapper.insert(smHonor);
        } else {
            smHonor.preUpdate();
            appSmHonorMapper.update(smHonor);
        }
    }

    public void deleteHonor(SmHonor smHonor) {
        appSmHonorMapper.deleteHonor(smHonor);
    }
}
