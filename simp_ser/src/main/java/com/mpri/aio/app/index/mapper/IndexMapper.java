package com.mpri.aio.app.index.mapper;

import com.mpri.aio.app.index.vo.IndexInfoVo;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndexMapper {

    Integer getCollarCardNum(@Param("entity") SmSchoolmate smSchoolmate);

    List<IndexInfoVo> getCollarCardList(@Param("entity") SmSchoolmate smSchoolmate,@Param("pageSize") Integer pageSize);
}
