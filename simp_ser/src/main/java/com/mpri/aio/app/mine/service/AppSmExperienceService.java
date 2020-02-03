package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmExperienceMapper;
import com.mpri.aio.schoolmate.model.SmExperience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmExperienceService {
    @Autowired
    private AppSmExperienceMapper appSmExperienceMapper;

    public SmExperience getOne(SmExperience smExperience) {
        return appSmExperienceMapper.getOne(smExperience);
    }

    public List<SmExperience> getExperiences(SmExperience smExperience) {
        return appSmExperienceMapper.getExperiences(smExperience);
    }

    public void saveExperience(SmExperience smExperience) {
        if (smExperience.getIsNewRecord()) {
            smExperience.preInsert();
            appSmExperienceMapper.insert(smExperience);
        } else {
            smExperience.preUpdate();
            appSmExperienceMapper.update(smExperience);
        }
    }

    public void deleteExperience(SmExperience smExperience) {
        appSmExperienceMapper.deleteExperience(smExperience);
    }
}
