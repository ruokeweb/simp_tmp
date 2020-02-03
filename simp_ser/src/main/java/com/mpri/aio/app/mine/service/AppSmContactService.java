package com.mpri.aio.app.mine.service;

import com.mpri.aio.app.mine.mapper.AppSmContactMapper;
import com.mpri.aio.schoolmate.model.SmContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppSmContactService {
    @Autowired
    private AppSmContactMapper appSmContactMapper;

    public List<SmContact> getContacats(SmContact smContact) {
        return appSmContactMapper.getContacats(smContact);
    }

    public void saveContacat(SmContact smContact) {
        if (smContact.getIsNewRecord()) {
            smContact.preInsert();
            appSmContactMapper.insert(smContact);
        } else {
            smContact.preUpdate();
            appSmContactMapper.update(smContact);
        }
    }

    public void deleteContact(SmContact smContact) {
        appSmContactMapper.deleteContact(smContact);
    }

    public SmContact get(SmContact smContact) {
        return  appSmContactMapper.get(smContact);
    }

    public SmContact getOne(SmContact smContact) {
        return appSmContactMapper.getOne(smContact);
    }
}
