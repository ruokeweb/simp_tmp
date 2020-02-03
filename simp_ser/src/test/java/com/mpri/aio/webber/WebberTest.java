package com.mpri.aio.webber;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.ApplicationTests;

public class WebberTest extends ApplicationTests {

	/**
	 * 获取token
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public String getToken(String username, String password) throws ParseException, IOException {
		String params = "account=" + username + "&password=" + password;
		
		
        HttpGet request = new HttpGet("http://114.116.25.97:8080/system/api/client/createtoken?"+params);//这里发送get请求
        // 获取当前客户端对象 
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
        } 
		return result;
	}

	/**
	 * 获取站点Id
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String getOwnerId(String cid, String token) throws ClientProtocolException, IOException {
		String params = "cid=" + cid + "&accesstoken=" + token;
        HttpGet request = new HttpGet("http://114.116.25.97:8080/system/api/news/getowners?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
        } 
		return result;
	}

	/**
	 * 获取所有栏目
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public String getTree(String wbfid, String owner, String cid,String accesstoken) throws ParseException, IOException {
		String params = "cid="+ cid +"&accesstoken=" + accesstoken +"&wbfid=" + wbfid + "&owner=" + owner;
        HttpGet request = new HttpGet("http://114.116.25.97:8080/system/api/news/gettree?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
        } 
		return result;
	} 

	/**
	 * 获取栏目下的文章列表分页
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public String getNewsByTree(int treeid,int viewid,int owner,String cid,String accesstoken,int start,int count,
			String domain) throws ClientProtocolException, IOException {
		String params = "cid=" + cid +
				"&accesstoken=" + accesstoken +
				"&owner="+owner+
				"&treeid=" + treeid +
				"&viewid=" + viewid +
				"&start=" + start +
				"&count=" + count +
				"&domain=" + domain;
		HttpGet request = new HttpGet("http://114.116.25.97:8080/system/api/news/getWbnewsListBytreeid?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
        } 
		return result;
	}
	
	/**
	 * 获取文章内容
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public String getWbnewsDetailById(String cid,String accesstoken,String wbnewsid,String owner,String domain) throws ParseException, IOException {
		String params = "cid=" + cid +
				"&accesstoken=" + accesstoken +
				"&owner="+owner+
				"&wbnewsid=" + wbnewsid +
				"&domain=" + domain;
		HttpGet request = new HttpGet("http://114.116.25.97:8080/system/api/news/getnewsdetailbyid?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
        } 
		return result;	
	}
	
	@Test
	public void test() {
		String tokenRes;
		try {
			tokenRes = this.getToken("syp", "webber");
			JSONObject objToken = JSONObject.parseObject(tokenRes);
			String token = objToken.getString("result");

			String ownnerRes = this.getOwnerId("2", token);
			JSONObject jsonOwner = JSONObject.parseObject(ownnerRes);
			JSONArray jsonArray = jsonOwner.getJSONArray("result");
			String owner = "";
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取ownner
				owner = ((JSONObject) jsonArray.get(i)).getString("owner");
			}

//			String news = this.getNewsByTree(1031, 1021, 1536369762, "2",token, 1, 3,"http://114.116.25.97:8080");
//			JSONObject newsjson = JSONObject.parseObject(news);
//			JSONObject newsdata = newsjson.getJSONObject("result");
//			JSONArray newsjsonArray = newsdata.getJSONArray("data");
//			
//			for (int i = 0; i < newsjsonArray.size(); i++) {
//				// 获取ownner
//				String newsid = ((JSONObject) newsjsonArray.get(i)).getString("newsid");
//				String newsDetail = this.getWbnewsDetailById("2", token, newsid, owner, "http://114.116.25.97:8080");
//				System.err.println(newsDetail);
//			}
//			System.out.println(news);
			String tree = this.getTree("0", owner, "2",token);//栏目列表
			
			System.out.println(tree);
//			
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
