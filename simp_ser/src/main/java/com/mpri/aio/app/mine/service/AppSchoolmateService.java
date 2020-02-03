package com.mpri.aio.app.mine.service;


import com.mpri.aio.app.mine.mapper.AppSchoolmateMapper;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppSchoolmateService {
    @Autowired
    AppSchoolmateMapper schoolmateMapper;

    public SmSchoolmate saveinfo(SmSchoolmate smSchoolmate){
        schoolmateMapper.update(smSchoolmate);
        return smSchoolmate;
    }

    public SmSchoolmate getByUserId(SmSchoolmate smSchoolmate){
        smSchoolmate = schoolmateMapper.getByUserId(smSchoolmate);
        return smSchoolmate;
    }

}
