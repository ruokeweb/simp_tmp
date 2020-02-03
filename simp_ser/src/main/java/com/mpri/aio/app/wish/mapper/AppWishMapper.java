package com.mpri.aio.app.wish.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.schoolmate.model.SmWish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppWishMapper {

    void insert (SmWish smWish);

    Page<SmWish> loadByPage(@Param("entity") SmWish smWish);

    List<SmWish> getThankslist(@Param("entity")SmWish smWish, @Param("pageSize") Integer pageSize);
}
