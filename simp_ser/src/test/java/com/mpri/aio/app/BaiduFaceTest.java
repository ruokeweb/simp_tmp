package com.mpri.aio.app;

import java.net.URL;
import java.util.Optional;
import com.mpri.aio.untils.file.model.File;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.app.utils.service.BaiduFaceService;
import com.mpri.aio.untils.file.service.FileService;

public class BaiduFaceTest extends ApplicationTests {
	
	@Autowired
	private BaiduFaceService baiduFaceService;
	
	@Autowired
	private FileService fileService;
	
	@Test
	public void test() {
//		String res = baiduFaceService.searchFace(new File("C:\\Users\\Administrator\\Desktop\\IMG\\LDH.png"), "xiaoyou_001", null);
//		System.err.println(res);
//		JSONObject resJson = JSONObject.parseObject(res);
//		JSONObject result  = resJson.getJSONObject("result");
//		System.err.println(result == null);
		Optional<File> file = fileService.getFileById("5d2ed73ce4fb3c2d98feb1b2");
		System.err.println(file.get().getContent().getData());
		String res = baiduFaceService.searchFaceOfByte(file.get().getContent().getData(), "xiaoyou_001", null);
		System.err.println(res);
		JSONObject resJson = JSONObject.parseObject(res);
		JSONObject result  = resJson.getJSONObject("result");
		System.err.println(result == null);
	}

//	@Test
//	public void test1() {
//		String res = baiduFaceService.matchFace(new File("C:\\Users\\Administrator\\Desktop\\IMG\\defFace.png"), 
//				new File("//192.168.140.13:8080/file/view/5d2ed73ce4fb3c2d98feb1b2"));
//		System.err.println(res);
//	}
}
