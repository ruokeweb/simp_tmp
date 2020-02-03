package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmExperienceMapper {
    SmExperience getOne(SmExperience smExperience);

    List<SmExperience> getExperiences(@Param("entity") SmExperience smExperience);

    void insert(SmExperience smExperience);

    void update(SmExperience smExperience);

    void deleteExperience(SmExperience smExperience);
}
