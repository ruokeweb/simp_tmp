package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmHonor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmHonorMapper {
    SmHonor getOne(SmHonor smHonor);

    List<SmHonor> getHonors(@Param("entity") SmHonor smHonor);

    void insert(SmHonor smHonor);

    void update(SmHonor smHonor);

    void deleteHonor(SmHonor smHonor);
}
