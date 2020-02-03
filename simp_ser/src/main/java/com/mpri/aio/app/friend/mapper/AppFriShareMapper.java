package com.mpri.aio.app.friend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.app.friend.vo.ShareRankingVo;

@Mapper
public interface AppFriShareMapper {

	List<ShareRankingVo> loadShareRanking();
}
