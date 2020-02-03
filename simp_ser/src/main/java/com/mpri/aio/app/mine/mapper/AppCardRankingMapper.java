package com.mpri.aio.app.mine.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.mine.vo.AppCardRankingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 
 * @Date 2019/11/12 13:54
 * @Created by lzq
 */
@Mapper
public interface AppCardRankingMapper {

    List<AppCardRankingVo> getCardRanking(@Param("entity")  AppCardRankingVo cardRankingVo);

    Page<AppCardRankingVo> loadByPage(@Param("entity") AppCardRankingVo cardRankingVo);

    int getMaxRanking(@Param("entity")AppCardRankingVo cardRankingVo);

}
