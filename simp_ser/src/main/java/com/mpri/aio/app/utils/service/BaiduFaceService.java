package com.mpri.aio.app.utils.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;

/**
 * 百度人脸库Service
 * @author syp
 *
 */
@Service
public class BaiduFaceService {
	
	//private static final BASE64Decoder decoder = new BASE64Decoder();
	//需要修改为自己的
	private static final String AppID = "16822915";
	private static final String APIKey = "pl9uQbG7IuSmykawjdiohmSc";
	private static final String SecretKey = "0tg4ZgT2cdcyc1o6NaFX4oikpPCNsP7q";
	
	private static final String def_group_id_list = "xiaoyou_001";
	private static final String def_max_face_num = "1";
	private static final String def_match_threshold = "60";
	private static final String def_quality_control = "NORMAL";
	private static final String def_liveness_control = "LOW";
	private static final String def_max_user_num = "1";
	private static final String add_action_type = "APPEND";
	private static final String update_action_type = "REPLACE";
	
	
	static AipFace client = null;
	static {
		client = new AipFace(AppID, APIKey, SecretKey);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);
	}
	
	/**
	 * 人脸搜索
	 * 
	 * @param file
	 * @param groupIdList
	 * @param userId
	 * @return
	 */
	public String searchFace(File file, String groupIdList, String userId) {
		try {
			return searchFaceOfByte(FileToByte(file), groupIdList, userId);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}

	/**
	 * 人脸搜索
	 * 
	 * @param file
	 * @param groupIdList
	 * @param userId
	 * @return
	 */
	public String searchFaceOfByte(byte[] arg0, String groupIdList, String userId) {
		return searchFace(arg0, groupIdList, userId);
	}	
	
	/**
	 * 人脸搜索
	 * 
	 * @param arg0
	 * @param groupIdList
	 * @return
	 */
	public static String searchFace(byte[] arg0, String groupIdList,
			String userId) {
		String imgStr = Base64Util.encode(arg0);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("quality_control", def_quality_control);
		options.put("liveness_control", def_liveness_control);
		options.put("max_user_num", def_max_user_num);
		options.put("match_threshold", def_match_threshold);
		options.put("max_user_num", def_max_face_num);
		options.put("group_id_list", def_group_id_list);
		
		if (userId != null) {
			options.put("user_id", userId);
		}
		options.put("max_user_num", def_max_user_num);
		JSONObject res = client.search(imgStr, imageType, groupIdList, options);
		return res.toString(2);
	}
	
	/**
	 * 人脸比对
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	public String matchFace(File file1, File file2) {
		try {
			return matchFace(FileToByte(file1), FileToByte(file2));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}

	/**
	 * 人脸比对
	 * 
	 * @param arg0
	 *            人脸1
	 * @param arg1
	 *            人脸2
	 * @return
	 */
	public static String matchFace(byte[] arg0, byte[] arg1) {
		String imgStr1 = Base64Util.encode(arg0);
		String imgStr2 = Base64Util.encode(arg1);
		MatchRequest req1 = new MatchRequest(imgStr1, "BASE64");
		MatchRequest req2 = new MatchRequest(imgStr2, "BASE64");
		ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		requests.add(req1);
		requests.add(req2);
		JSONObject res = client.match(requests);
		return res.toString();
	}

	
	
	public static byte[] FileToByte(File file) throws IOException {
		// 将数据转为流
		@SuppressWarnings("resource")
		InputStream content = new FileInputStream(file);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = content.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		content.close();
		// 获得二进制数组
		return swapStream.toByteArray();
	}
	
	
	/**
	 * 人脸库人脸注册  
	 */
	public void faceAdd(String image,String imageType,String groupId,String userId) {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("quality_control", def_quality_control);
	    options.put("liveness_control", def_liveness_control);
	    options.put("action_type", add_action_type);
	    // 人脸更新
	    JSONObject res = client.updateUser(image, imageType, groupId, userId, options);
	    System.out.println(res.toString(2));

	}
	/**
	 * 人脸更新
	 */
	public void faceUpdate(String image,String imageType,String groupId,String userId) {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("quality_control", def_quality_control);
	    options.put("liveness_control", def_liveness_control);
	    options.put("action_type", update_action_type);
	    // 人脸更新
	    JSONObject res = client.updateUser(image, imageType, groupId, userId, options);
	    System.out.println(res.toString(2));

	}	
	
	
	/**
	 * 人脸删除
	 */
	public void faceDel(String userId,String groupId,String faceToken) {
	    // 传入可选参数调用接口
	    HashMap<String, String> options = new HashMap<String, String>();    
	    // 人脸删除
	    JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
	    System.out.println(res.toString(2));

	}
}
