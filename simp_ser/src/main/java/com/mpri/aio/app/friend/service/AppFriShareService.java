package com.mpri.aio.app.friend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mpri.aio.app.friend.mapper.AppFriShareMapper;
import com.mpri.aio.app.friend.vo.ShareRankingVo;

@Service
public class AppFriShareService {

	@Autowired
	private AppFriShareMapper appFriShareMapper;
	
	public List<ShareRankingVo> loadShareRanking(){
		return appFriShareMapper.loadShareRanking();
	}
}
