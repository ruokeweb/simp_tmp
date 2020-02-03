package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppChaDonMoneyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 
 * @Date 2019/11/12 13:54
 * @Created by lzq
 */
@Mapper
public interface AppChaDonMoneyMapper {

    List<AppChaDonMoneyVo> getDonMoneyRanking(@Param("entity")  AppChaDonMoneyVo appChaDonMoneyVo);

    Page<AppChaDonMoneyVo> loadByPage(@Param("entity") AppChaDonMoneyVo appChaDonMoneyVo);

    Integer getMaxRanking(@Param("entity")AppChaDonMoneyVo appChaDonMoneyVo);

    Integer getRanking(@Param("entity")AppChaDonMoneyVo appChaDonMoneyVo);

}
