package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmSocial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmSocialMapper {

    SmSocial getOne(SmSocial smSocial);

    List<SmSocial> getSocials(@Param("entity") SmSocial smSocial);

    void deleteSocial(SmSocial smSocial);

    void insert(SmSocial smSocial);

    void update(SmSocial smSocial);
}
