package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppSmEduVo;
import com.mpri.aio.schoolmate.model.SmEducation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppEducationMapper {

    Page<SmEducation> loadByPage(@Param("entity") SmEducation smEducation);

    void insert(SmEducation smEducation);

    void update(SmEducation smEducation);

    SmEducation get(@Param("entity") SmEducation smEducation);

    void setDefault(SmEducation smEducation);

    void clearDefault(SmEducation smEducation);

    List<AppSmEduVo> getEduList(@Param("entity") SmEducation smEducation);
}
