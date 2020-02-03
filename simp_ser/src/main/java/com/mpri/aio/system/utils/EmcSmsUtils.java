package com.mpri.aio.system.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.mpri.aio.system.common.GlobalStr;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.emc.sendmsg.HttpClientUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
	

/**
 * 交大（漫道短信接口）
 * <p>
 * Title: EmcSmsUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年11月20日
 */
@Component
public class EmcSmsUtils {

	
	@Autowired
    private FreeMarkerConfigurer configurer;
	//短信tittle
	private static final String TITTLE = "TITTLE";
	//短信类型
	private static final Integer ISCRON= 0; //0 及时短信 1 延时
	//通道code channelCode
	private static final String CHANNELCODE = "ydxy-mobile";
	
//	@Value("${sms.serviceUrl}")
	String serviceUrl ;
//	@Value("${sms.accountID}")
	String accountID ;
//	@Value("${sms.accountKey}")
	String accountKey ;
	protected static Map<String, String> channelCodeToId = new HashMap<String, String>();
	protected static Map<String, String> channelCodeToName = new HashMap<String, String>();

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    
	
	/**
	 * 动态获取接收端渠道列表
	 */
	public void initChannelInfo() {
		String url = runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_serviceUrl) + "/restful/get_channels";
		Map<String, String> data = new HashMap<String, String>();
		data.put("accountID", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountID));
		data.put("accountKey", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountKey));
		HttpClientUtils client = new HttpClientUtils();
		JSONObject obj = client.httpGet(url, data);
		if (obj.getInt("errcode") == 0) {
			JSONObject res = obj.getJSONObject("data");
			Set<?> keys = res.keySet();
			Iterator<?> tempKeys = keys.iterator();
			while (tempKeys.hasNext()) {
				String key = tempKeys.next().toString();
				JSONArray tempArray = res.getJSONArray(key);
				for (int i = 0; i < tempArray.size(); i++) {
					JSONObject temp = tempArray.getJSONObject(i);
					if (temp.getString("status").equals("0")) {
						channelCodeToId.put(key, temp.getString("id"));
						channelCodeToName.put(key, temp.getString("channelName"));
						break;
					}
				}
			}
		} else {
			System.err.println(obj.getString("msg"));
		}
	}

	/**
	 * 邮件发送（外部）
	 * 
	 * @return
	 */
	private String sendmsg(String title, String content,int isCron, String sendTime,String typecode,
			String channelCode, List<String> extReceiver, List<String> intReceiver
			) {
		Map<String, String> result = new HashMap<String, String>();
		if ((channelCodeToId == null) || (channelCodeToId.get(channelCode) == null)) {
			initChannelInfo();
		}
		String channelId = channelCodeToId.get(channelCode).toString();
		if (channelId == null) {
			result.put("errcode", "40014");
			result.put("msg", "接入端没有此渠道的发送权限");
			return result.toString();
		}

		JSONObject dataJson = new JSONObject();
		dataJson.put("title", title);
		dataJson.put("content", content);
		dataJson.put("isCron", isCron);
		dataJson.put("sendtime", sendTime);
		dataJson.put("typecode", typecode);
		dataJson.put("channels", channelCode);
		dataJson.put("channelIds", channelId);
		dataJson.put("extReceiver", extReceiver);
		dataJson.put("intReceiver", intReceiver);
		HttpPost httpPost = new HttpPost(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_serviceUrl) + "/restful/v1/sendmsg");
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("accountID", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountID)));
			params.add(new BasicNameValuePair("accountKey", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accountKey)));
			params.add(new BasicNameValuePair("msgJson", dataJson.toString()));

			HttpEntity httpEntity = new UrlEncodedFormEntity(params, "UTF-8");
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = client.execute(httpPost);
			String str1 = EntityUtils.toString(response.getEntity());

			JSONObject jsonObj = JSONObject.fromObject(str1);
			if(null != jsonObj.get("errcode") && jsonObj.getInt("errcode")==0 ) {
				return GlobalStr.SUCCESS;
			}
			return GlobalStr.ERRO;
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
			return "发送失败";
		} finally {
			httpPost.releaseConnection();
		}
	}
	
	
	/**
	 * 发送短信
	* <p>Title: sendSms</p>  
	* <p>Description: </p>
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	public void sendSms(String tosend, String templateName,Map<String, Object> map) throws IOException, TemplateException {
		if(!templateName.endsWith(".ftl"))
		{
			templateName=templateName+".ftl";
		}
		Template template = configurer.getConfiguration().getTemplate(templateName);
		String html = FreeMarkerTemplateUtils
				.processTemplateIntoString(template, map);
		
		List<String> outAddresses = new ArrayList<String>();
		outAddresses.add(tosend);
		this.sendmsg(TITTLE, html, ISCRON,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()),
				"",CHANNELCODE,outAddresses, new ArrayList<String>()
				);
		
	}
	
	/**
	 * 发送短信
	* <p>Title: main</p>  
	* <p>Description: </p>  
	 */
	public String sendSms(String tosend,String html) {
		List<String> outAddresses = new ArrayList<String>();
		outAddresses.add(tosend);
//		String res = this.sendmsg(TITTLE, html, ISCRON,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()),
//				"",CHANNELCODE,outAddresses, new ArrayList<String>()
//				);
		String res = this.sendmsg(TITTLE, html, ISCRON,null,
				"",CHANNELCODE,outAddresses, new ArrayList<String>()
				);			
		System.out.println("交大漫道短信发送结果======"+res);
		return res;
	}
	
	
	public static void main(String[] args) {
		// 外部用户：输入手机号
//		List outAddresses = new ArrayList();
//		outAddresses.add("17629261881");
//		String result = sendmsg("测试", "会议内容如下: 1111.", 0,"短信","","ydxy-mobile",outAddresses, new ArrayList()
//				);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date()));
	}
 
}
