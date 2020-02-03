package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmSocialMapper;
import com.mpri.aio.schoolmate.model.SmSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmSocialService {
    @Autowired
    private AppSmSocialMapper appSmSocialMapper;

    public SmSocial getOne(SmSocial smSocial) {
        return appSmSocialMapper.getOne(smSocial);
    }

    public List<SmSocial> getSocials(SmSocial smSocial) {
        return appSmSocialMapper.getSocials(smSocial);
    }

    public void saveSocial(SmSocial smSocial) {
        if (smSocial.getIsNewRecord()) {
            smSocial.preInsert();
            appSmSocialMapper.insert(smSocial);
        } else {
            smSocial.preUpdate();
            appSmSocialMapper.update(smSocial);
        }
    }

    public void deleteSocial(SmSocial smSocial) {
        appSmSocialMapper.deleteSocial(smSocial);
    }
}
