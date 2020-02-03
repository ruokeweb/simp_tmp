package com.mpri.aio.schoolmate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.schoolmate.mapper.InfoIntegrityMapper;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.model.SmStarSminfo;
import com.mpri.aio.system.init.InitCache;

public class SmStarServiceTest extends ApplicationTests{
	/* 星级缓存 */
	//static Map<String, List<SmStar>> starCache = InitCache.starCache;
	@Autowired
	private InfoIntegrityMapper mapper;
	List<String> status =  new ArrayList<>();
	
	
	@Test
	public void setSmComplete() {
//		status.add("BASEINFO");
//		status.add("CONTACTINFO");
//		status.add("ADDRESSINFO");
//		
//		List<SmStar> smStars = starCache.get("starCache");
//		if (status.size() < 1) {
//			System.err.println("123");
//		} else {
//			for (SmStar star : smStars) {
//				List<String> starInfo = getStatus(star.getSmStarSminfo());
//				if(status.containsAll(starInfo) || compareList(starInfo,status)) {
//					System.err.println(star.getInfoLevel());
//				}
//			}
//		}
		
	}
	
	private  List<String> getStatus(List<SmStarSminfo> smStarSminfo){
		List<String> status = new ArrayList<String>();
		for (SmStarSminfo sm : smStarSminfo) {
			status.add(sm.getSmSminfoCode());
		}		
		return status;
	}
	
	
	private  Boolean compareList(List<String> stars, List<String> status) {
		if (stars.size() != status.size())
			return false;
		for (String object : status) {
			if (!stars.contains(object))
				return false;
		}
		return true;
}
}
