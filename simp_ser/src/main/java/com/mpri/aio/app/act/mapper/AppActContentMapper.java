package com.mpri.aio.app.act.mapper;

import com.mpri.aio.act.model.ActContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppActContentMapper {

    /**
     * 判断是否报名
     * @param entity
     * @return
     */
    int getNumByUserId(@Param("entity") ActContent entity);

    void saveActContent(List<ActContent> list);

    void insert(ActContent actContent);
}
