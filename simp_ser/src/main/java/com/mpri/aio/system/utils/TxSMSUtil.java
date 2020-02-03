package com.mpri.aio.system.utils;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

/**
 * 短信发送Util(腾讯云)
 * <p>
 * Title: SMSUtil
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author syp
 * @date 2018年10月8日
 */
@Component
public class TxSMSUtil {

//	@Value("${sms.appkey}")
	String smsappkey; // 短信应用SDK AppKey    

//	@Value("${sms.appid}")
	int smsappid; // 短信应用SDK AppID 1400开头
	
	private static String NATIONCODE = "86";

	private String RESTR = ""; // 定义返回值    
	private static String SMSSIGN = "校友卡平台"; // 签名，使用的是`签名内容`，而不是`签名ID`  
	
    @Autowired(required = true)
    private RunSettingParamsUtils runSettingParamsUtils;
	
	/**
	 * 发送短信
	* <p>Title: sendSMS</p>  
	* <p>Description: </p>  
	* @param phoneNumber
	* @param context
	* @return
	 * @throws IOException 
	 * @throws HTTPException 
	 * @throws JSONException 
	 */
	public String sendSMS(String phoneNumber,ArrayList<String> params,int templateId) throws Exception {
		MySmsSingleSender ssender = new MySmsSingleSender(
				Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_appid)), 
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_appkey));
		SmsSingleSenderResult result = ssender.sendWithParam(NATIONCODE, phoneNumber,templateId, params, SMSSIGN, "", "");
		// 签名参数未提供或者为空时，会使用默认签名发送短信            
		if(result.result == 0) {
			RESTR = "success";
		}else {
			RESTR = "erro";
		}
		return RESTR;
	}
	
}
