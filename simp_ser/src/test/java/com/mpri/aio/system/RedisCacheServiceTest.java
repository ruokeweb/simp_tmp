package com.mpri.aio.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.system.utils.RedisUtil;
public class RedisCacheServiceTest extends ApplicationTests{

	
	@Autowired
	private RedisUtil service;
	
//	@Test
	public void put() {
		service.set("132727213123", "123456789", 60);
	}
	
	@Test
	public void get() {
		System.err.println(service.get("132727213123"));
	}
}
