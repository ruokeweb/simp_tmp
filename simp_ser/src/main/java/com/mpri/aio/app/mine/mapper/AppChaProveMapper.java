package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppChaDonTimeVo;
import com.mpri.aio.app.mine.vo.AppChaProveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 
 * @Date 2019/11/12 13:54
 * @Created by lzq
 */
@Mapper
public interface AppChaProveMapper {

    List<AppChaProveVo> getProveRanking(@Param("entity")  AppChaProveVo appChaProveVo);

    Page<AppChaProveVo> loadByPage(@Param("entity") AppChaProveVo appChaProveVo);

    Integer getMaxRanking(@Param("entity")AppChaProveVo appChaProveVo);

    Integer getRanking(@Param("entity") AppChaProveVo appChaProveVo);

}
