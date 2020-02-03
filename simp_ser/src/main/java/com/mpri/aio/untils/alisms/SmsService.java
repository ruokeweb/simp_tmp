package com.mpri.aio.untils.alisms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

@Component
public class SmsService {
	
	@Autowired
	SmsConfig smsConfig;
	
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
	
	/**
	 * 单条短信发送接口
	 * 调用样例见单元测试
	 * @param smsParam
	 * @return
	 */
	public String sendSms(SmsSigleParam smsParam) {
		String result="";
		DefaultProfile profile = DefaultProfile.getProfile(
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_regionId),
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accessKey), 
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accessKeySecret));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_domain));
        request.setAction("SendSms");
        request.setVersion(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_version));
        request.putQueryParameter("RegionId",
        		runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_regionId));
        request.putQueryParameter("PhoneNumbers", smsParam.getPhoneNumbers());
        request.putQueryParameter("SignName", smsConfig.getSignName());
        request.putQueryParameter("TemplateCode", smsParam.getTemplateCode());
        request.putQueryParameter("TemplateParam", smsParam.getTemplateParam());
        try {
            CommonResponse response = client.getCommonResponse(request);
            result=response.getData();
        } catch (ServerException e) {
            e.printStackTrace();
            result="ERROR:ServerException";
        } catch (ClientException e) {
            e.printStackTrace();
            result="ERROR:ClientException ";
        }
        return result;
	}

	/**
	 * 批量发送短信接口
	 * 样例见单元测试
	 * @param smsParam
	 * @return
	 */
	public String SendBatchSms(SmsMultiParam smsParam) {
		String result="";
		DefaultProfile profile = DefaultProfile.getProfile(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_regionId),
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accessKey), 
				runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_accessKeySecret));
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_domain));
        request.setVersion(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_version));
        request.setAction("SendBatchSms");
        request.putQueryParameter("RegionId",
        		runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_regionId));
        request.putQueryParameter("PhoneNumberJson", smsParam.getPhoneNumbers());
        request.putQueryParameter("SignNameJson", smsParam.getSignName());
        request.putQueryParameter("TemplateCode", smsParam.getTemplateCode());
        request.putQueryParameter("TemplateParamJson", smsParam.getTemplateParam());
        try {
            CommonResponse response = client.getCommonResponse(request);
            result=response.getData();
        }catch (ServerException e) {
            e.printStackTrace();
            result="ERROR:ServerException";
        } catch (ClientException e) {
            e.printStackTrace();
            result="ERROR:ClientException ";
        }
        return result;
	}
	
}
