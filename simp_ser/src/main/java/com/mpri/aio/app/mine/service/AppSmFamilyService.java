package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmFamilyMapper;
import com.mpri.aio.schoolmate.model.SmFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmFamilyService {
    @Autowired
    private AppSmFamilyMapper appSmFamilyMapper;

    public List<SmFamily> getFamilys(SmFamily smFamily) {
        return appSmFamilyMapper.getFamilys(smFamily);
    }

    public void saveFamily(SmFamily smFamily) {
        if (smFamily.getIsNewRecord()) {
            smFamily.preInsert();
            appSmFamilyMapper.insert(smFamily);
        } else {
            smFamily.preUpdate();
            appSmFamilyMapper.update(smFamily);
        }
    }

    public void deleteFamily(SmFamily smFamily) {
        appSmFamilyMapper.deleteFamily(smFamily);
    }

    public SmFamily getOne(SmFamily smFamily) {
        return appSmFamilyMapper.getOne(smFamily);
    }
}
