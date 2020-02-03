package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppChaProveVo;
import com.mpri.aio.app.mine.vo.AppChaShareVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 
 * @Date 2019/11/12 13:54
 * @Created by lzq
 */
@Mapper
public interface AppChaShareMapper {

    List<AppChaShareVo>  getShareRanking(@Param("entity")  AppChaShareVo appChaShareVo);

    Page<AppChaShareVo> loadByPage(@Param("entity") AppChaShareVo appChaShareVo);

    Integer getMaxRanking(@Param("entity")AppChaShareVo appChaShareVo);

    Integer getRanking(@Param("entity") AppChaShareVo appChaShareVo);

}
