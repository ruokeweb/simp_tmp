package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.schoolmate.model.SmProfession;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppSmProfessionMapper {

    SmProfession get(SmProfession smProfession);

    Page<SmProfession> loadByPage (@Param("entity") SmProfession smProfession);

    void delete(SmProfession smProfession);

    void insert(SmProfession smProfession);

    void update(SmProfession smProfession);
    
    List<SmProfession> loadAllListBy(@Param("entity") SmProfession smProfession);

}
