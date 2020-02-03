package com.mpri.aio.app.index.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.app.index.vo.News;
import com.mpri.aio.app.index.vo.TreeColumn;
import com.mpri.aio.app.index.vo.VsbView;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

/**
 * 网站群相关service
 * @author Administrator
 *
 */
@Service
public class WebberVsbService {
	
	private String ASSESSTOKEN = null;
//	@Value("${vsb.webber.url}")
	private String webberUrl;

//	@Value("${vsb.webber.username}")
	private String username;
	
//	@Value("${vsb.webber.domain}")
	private String domain;
		
//	@Value("${vsb.webber.password}")
	private String password;	

//	@Value("${vsb.webber.cid}")
	private int cid;
	
//	@Value("${vsb.webber.wbfid}")
	private int wbfid;
	
	
	/*站点ID*/
//	@Value("${vsb.webber.ownnerId}")
	private  int ownnerId;
	
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
	
//	
//	@Value("${vsb.webber.username}")
//    public void setUserName(String vsbUsername) {
//    	username = vsbUsername;
//    }
//	@Value("${vsb.webber.url}")
//	public static void setWebberUrl(String   vsbWebberUrl) {
//		username = vsbWebberUrl;
//	}
//	@Value("${vsb.webber.password}")
//	public static void setPassword(String vsbPassword) {
//		System.err.println("@value 先执行了");
//		password = vsbPassword;
//	}

	@PostConstruct
	public void setAssessToken() {
		try {
//			webberUrl =  runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url);
//			username =  runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_username);
//			domain = runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
//	    	password =  runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password);
//	    	password=  runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password);
//	    	cid =  Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid));
//	    	wbfid= Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_wbfid));
//	    	ownnerId=  Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId));

			ASSESSTOKEN = getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url),
					runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_username),
					runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password));
			System.err.println("==站群token=="+ASSESSTOKEN);
		} catch (ParseException e) {
			// e.printStackTrace();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取token
	 * @throws IOException 
	 * @throws ParseException 
	 */
	private String getToken(String webberUrl,String username,String password) throws ParseException, IOException {
		String params = "account=" + username + "&password=" + password;
        HttpGet request = new HttpGet(webberUrl+ "/system/api/client/createtoken?"+params);//这里发送get请求
        // 获取当前客户端对象 
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        String result= "";
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result= EntityUtils.toString(response.getEntity(),"utf-8");
    		JSONObject objToken = JSONObject.parseObject(result);
    		if(objToken.getInteger("code") >= 0) {
         		return objToken.getString("result");
    		}
        }
        return null;
	}
	
	
	/**
	 * 获取栏目
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<TreeColumn> getTreeColumns() throws ClientProtocolException, IOException {
		List<TreeColumn> list = new ArrayList<TreeColumn>();
		String params = "cid="+ Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +"&accesstoken=" + ASSESSTOKEN +"&wbfid=" + 
				Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_wbfid)) + "&owner=" + 
				Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId));
        HttpGet request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+ "/system/api/news/gettree?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result= EntityUtils.toString(response.getEntity(),"utf-8");
            JSONObject jsonResult = JSONObject.parseObject(result);
            JSONArray jsonArray = jsonResult.getJSONObject("result").getJSONArray("subtree");
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取ownner
				TreeColumn column = JSONObject.toJavaObject((JSON)jsonArray.get(i), TreeColumn.class);
				list.add(column);
			}
        } 
		return list;
	}
	
	/**
	 * 获取栏目下组件
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public List<VsbView> getViewidByTree(String cid,String accesstoken,String treeid,String owner) throws ParseException, IOException {
		List<VsbView> list = new ArrayList<VsbView>();
		String params = "cid=" + cid +
				"&accesstoken=" + accesstoken +
				"&owner="+owner+
				"&treeid=" + treeid;
		HttpGet request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getViewIdByTree?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        	String result= EntityUtils.toString(response.getEntity(),"utf-8");
        	JSONObject jsonRes = JSONObject.parseObject(result);
        	JSONArray jsonArray = jsonRes.getJSONArray("result");
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取ownner
				VsbView view = JSONObject.toJavaObject((JSON)jsonArray.get(i), VsbView.class);
				list.add(view);
			}
        } 
		return list;	
	}	
	
	/**
	 * 通过栏目获取文章列表
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public List<News> getNewsByTree(TreeColumn trColumn,int start,int count) throws ClientProtocolException, IOException {
		List<News> newsList = new ArrayList<News>();
		String params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
				"&accesstoken=" + ASSESSTOKEN +
				"&owner="+ Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
				"&treeid=" + trColumn.getTreeid() +
				"&viewid=" + trColumn.getViewid() +
				"&start=" + start +
				"&count=" + count +
				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
		HttpGet request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getWbnewsListBytreeid?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象    
        HttpResponse response = httpClient.execute(request);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result =  EntityUtils.toString(response.getEntity(),"utf-8");
            String code = JSONObject.parseObject(result).getString("code"); 
            if("-1".equals(code)) {
            	ASSESSTOKEN= getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_username),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password));
        	    params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
        				"&accesstoken=" + ASSESSTOKEN +
        				"&owner="+ Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
        				"&treeid=" + trColumn.getTreeid() +
        				"&viewid=" + trColumn.getViewid() +
        				"&start=" + start +
        				"&count=" + count +
        				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
        		request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getWbnewsListBytreeid?"+params);//这里发送get请求
                // 获取当前客户端对象
                httpClient = HttpClients.createDefault();
                // 通过请求对象获取响应对象    
                response = httpClient.execute(request);
                result =  EntityUtils.toString(response.getEntity(),"utf-8");
            }
			JSONObject newsdata = JSONObject.parseObject(result).getJSONObject("result");
			JSONArray jsonArray = newsdata.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取ownner
				News news = JSONObject.toJavaObject((JSON)jsonArray.get(i), News.class);
				newsList.add(news);
			}
        }
		return newsList;	
	}
	
	/**
	 * 通过文章ID 获取文章的具体内容
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public News getNewsDetail(String wbnewsid) throws ParseException, IOException{
		News news = new News();
		String params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
				"&accesstoken=" + ASSESSTOKEN +
				"&owner=" +Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
				"&wbnewsid=" + wbnewsid +
				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
		HttpGet request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getnewsdetailbyid?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象
        HttpResponse response = httpClient.execute(request);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        	String result = EntityUtils.toString(response.getEntity(),"utf-8");
            String code = JSONObject.parseObject(result).getString("code"); 
            if("-1".equals(code)) {
            	ASSESSTOKEN= getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_username),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password));
        		params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
        				"&accesstoken=" + ASSESSTOKEN +
        				"&owner=" +Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
        				"&wbnewsid=" + wbnewsid +
        				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
        		request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getnewsdetailbyid?"+params);//这里发送get请求
                // 获取当前客户端对象
                httpClient = HttpClients.createDefault();
                // 通过请求对象获取响应对象
                response = httpClient.execute(request);
                result =  EntityUtils.toString(response.getEntity(),"utf-8");
            }
        	JSONObject jsonResult = JSONObject.parseObject(result);
            news = JSONObject.toJavaObject((JSON)jsonResult, News.class);
            news.setTitle(jsonResult.getString("wbtitle"));
            news.setDate(jsonResult.getString("wbdate"));
        } 
		return news;	
	}
	
	
	/**
	 * 获取相关阅读
	 */
	public List<News> getrelationNews(News news,int start,int count) throws ClientProtocolException, IOException {
		List<News> newsList = new ArrayList<News>();
		String params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
				"&accesstoken=" + ASSESSTOKEN +
				"&owner="+ Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
				"&treeid=" + news.getWbtreeid() +
				"&viewid=" + news.getWbviewid() +
				"&start=" + start +
				"&count=" + count +
				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
		HttpGet request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getWbnewsListBytreeid?"+params);//这里发送get请求
        // 获取当前客户端对象
        HttpClient httpClient = HttpClients.createDefault();
        // 通过请求对象获取响应对象    
        HttpResponse response = httpClient.execute(request);
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String result =  EntityUtils.toString(response.getEntity(),"utf-8");
            String code = JSONObject.parseObject(result).getString("code"); 
            if("-1".equals(code)) {
            	ASSESSTOKEN= getToken(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_username),
            			runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_password));
        	    params = "cid=" + Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_cid)) +
        				"&accesstoken=" + ASSESSTOKEN +
        				"&owner="+ Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_ownnerId))+
						"&treeid=" + news.getWbtreeid() +
						"&viewid=" + news.getWbviewid() +
        				"&start=" + start +
        				"&count=" + count +
        				"&domain=" + runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_domain);
        		request = new HttpGet(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.vsb_webber_url)+"/system/api/news/getWbnewsListBytreeid?"+params);//这里发送get请求
                // 获取当前客户端对象
                httpClient = HttpClients.createDefault();
                // 通过请求对象获取响应对象    
                response = httpClient.execute(request);
                result =  EntityUtils.toString(response.getEntity(),"utf-8");
            }
			JSONObject newsdata = JSONObject.parseObject(result).getJSONObject("result");
			JSONArray jsonArray = newsdata.getJSONArray("data");
			for (int i = 0; i < jsonArray.size(); i++) {
				// 获取ownner
				News res = JSONObject.toJavaObject((JSON)jsonArray.get(i), News.class);
				if(!res.getNewsid().equals(news.getNewsid())) {
					newsList.add(res);
				}
			}
        }
		return newsList;	
	}
 }
