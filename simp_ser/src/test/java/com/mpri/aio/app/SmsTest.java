package com.mpri.aio.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.untils.alisms.SmsConfig;
import com.mpri.aio.untils.alisms.SmsMultiParam;
import com.mpri.aio.untils.alisms.SmsService;
import com.mpri.aio.untils.alisms.SmsSigleParam;
import com.mpri.aio.untils.alisms.SmsTemplate;

public class SmsTest  extends ApplicationTests{
	
	@Autowired
	private  SmsService  smsService;
	@Autowired
	SmsConfig smsConfig;
	
	//@Test
	public void sendSmsTest() {
		
		SmsSigleParam sp=new SmsSigleParam();
		sp.setPhoneNumbers("15389286194");
		sp.setTemplateCode(SmsTemplate.test);
		
		Map<String,String> parMap=new HashMap<String ,String>();
		parMap.put("code", "2333");
		
		sp.setTemplateParamMap(parMap);
		
		String result=smsService.sendSms(sp);
		System.out.println(result);
		System.out.println(sp.getTemplateParam());
	}

	@Test
	public void sendMultiSmsTest() {
		
		SmsMultiParam sp=new SmsMultiParam();
		//封入 组织名称
		sp.setSignName(smsConfig.getSignName());
		
		
		//您好，${name}同学，博达校友卡向您发起${content}的提醒，如非本人，请忽略本短信。
		List<String> phoneNumList=new ArrayList<String>();
		phoneNumList.add("15389286194");
		phoneNumList.add("17629261881");
		phoneNumList.add("17791990971");
		sp.setPhoneNumbersList(phoneNumList);
		
		List<Map<String,String>> paramList=new ArrayList<Map<String,String>>();
		
		Map<String,String> parMap1=new HashMap<String ,String>();
		parMap1.put("name", "Cary");
		parMap1.put("content", ":请立刻前往南天门东北角参加“领取100斤大米福利”");
		
		
		Map<String,String> parMap2=new HashMap<String ,String>();
		parMap2.put("name", "苏胖胖");
		parMap2.put("content", ":请立刻前往南天门东北角参加“领取200斤大米福利”");
		
		
		Map<String,String> parMap3=new HashMap<String ,String>();
		parMap3.put("name", "方涛");
		parMap3.put("content", ":请立刻前往南天门东北角参加“领取300斤大米福利”");
		
		paramList.add(parMap1);
		paramList.add(parMap2);
		paramList.add(parMap3);
		sp.setTemplateParamMapList(paramList);
		
		sp.setTemplateCode(SmsTemplate.common);
		
		String result=smsService.SendBatchSms(sp);
		System.out.println(result);
		System.out.println(sp.getPhoneNumbers());
		System.out.println(sp.getSignName());
		System.out.println(sp.getTemplateParam());
	}
}
