package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmFamily;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmFamilyMapper {

    List<SmFamily> getFamilys(@Param("entity") SmFamily smFamily);

    void insert(SmFamily smFamily);

    void update(SmFamily smFamily);

    void deleteFamily(SmFamily smFamily);

    SmFamily getOne(SmFamily smFamily);
}
