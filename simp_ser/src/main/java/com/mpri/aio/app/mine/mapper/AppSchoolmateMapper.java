package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppSchoolmateMapper {

    void update(SmSchoolmate smSchoolmate);

    SmSchoolmate getByUserId(SmSchoolmate smSchoolmate);

}
