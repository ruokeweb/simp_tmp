package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.schoolmate.model.SmContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppSmContactMapper {
    /**
     * 获取个人的所有联系方式
     * @param smContact
     * @return
     */
    List<SmContact> getContacats(@Param("entity") SmContact smContact);

    void insert(SmContact smContact);

    void update(SmContact smContact);

    void deleteContact(SmContact smContact);

    SmContact get(SmContact smContact);

    SmContact getOne(SmContact smContact);
}
