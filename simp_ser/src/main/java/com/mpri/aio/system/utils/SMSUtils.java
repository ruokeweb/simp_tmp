package com.mpri.aio.system.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.untils.alisms.SmsService;
import com.mpri.aio.untils.alisms.SmsSigleParam;
import com.mpri.aio.untils.alisms.SmsTemplate;

/**
 * 短信发送
* <p>Title: SMSTUtils</p>  
* <p>Description: </p>  
* @author syp  
* @date 2019年3月14日
 */
@Component
public class SMSUtils {
		
    @Autowired
    private EmcSmsUtils emcSUtils;
    
    @Autowired 
    private TxSMSUtil txSMSUtil;
    
	@Autowired
	private SmsService smsService;
    
	/*姓名表达式*/
	public static final String NAME_EL = "{name}";
	
	/*日期表达式*/
	public static final String DATA_EL = "{date}";
    
	/**
	 * 发送短信接口
	 * @return 
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String sendSms(String tosend,Object content, String template) throws NumberFormatException, Exception{
		
		ArrayList<String> arrayList = (ArrayList<String>)content;
		//生产环境下具体开发

// 西安交大发送短信
//		if(arrayList.size() > 1) {
//			String sendContent = replaceEl(arrayList.get(0),arrayList.get(1));
//			return emcSUtils.sendSms(tosend, sendContent);
//		}else {
//			System.out.println("=============================发送短信失败=============================");
//			return null;
//		}
// 腾讯云发送短信
//		return txSMSUtil.sendSMS(tosend, (ArrayList<String>)content, Integer.valueOf(template));
		
//阿里云发送短信
		if(arrayList.size() > 1) {
			SmsSigleParam sp=new SmsSigleParam();
			sp.setPhoneNumbers(tosend);
			sp.setTemplateCode(String.valueOf(template));
			
			Map<String,String> parMap=new HashMap<String ,String>();
			parMap.put("a", arrayList.get(0));
			parMap.put("b", arrayList.get(1));
			sp.setTemplateParamMap(parMap);
			
			String result=smsService.sendSms(sp);
			String code =JSONObject.parseObject(result).getString("Code");
			if("OK".equals(code)) { 
				return GlobalStr.SUCCESS;
			}
			return GlobalStr.ERRO;
		}else {
			System.out.println("=============================发送短信失败=============================");
			return GlobalStr.ERRO;
		}	
	}
	
	/**
	 * 替换表达式
	 */
	public String replaceEl(String name,String content) {
		content = StringEscapeUtils.unescapeHtml4(content);
		content = content.replace(NAME_EL, name);
		content = content.replace(DATA_EL, DateUtils.getDate("yyyy-MM-dd"));
		return content;
	}
}
