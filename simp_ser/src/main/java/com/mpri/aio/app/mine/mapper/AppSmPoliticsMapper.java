package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmPolitics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmPoliticsMapper {

    List<SmPolitics> getPolitics(@Param("entity") SmPolitics smPolitics);

    void deletePolitic(SmPolitics smPolitics);

    void insert(SmPolitics smPolitics);

    void update(SmPolitics smPolitics);

    SmPolitics getOne(SmPolitics smPolitics);
}
