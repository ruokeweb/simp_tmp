package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppChaDonMoneyVo;
import com.mpri.aio.app.mine.vo.AppChaDonTimeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 
 * @Date 2019/11/12 13:54
 * @Created by lzq
 */
@Mapper
public interface AppChaDonTimeMapper {

    List<AppChaDonTimeVo> getDonTimeRanking(@Param("entity")  AppChaDonTimeVo appChaDonTimeVo);

    Page<AppChaDonTimeVo> loadByPage(@Param("entity") AppChaDonTimeVo appChaDonTimeVo);

    Integer getMaxRanking(@Param("entity")AppChaDonTimeVo appChaDonTimeVo);

    Integer getRanking(@Param("entity") AppChaDonTimeVo appChaDonTimeVo);

}
