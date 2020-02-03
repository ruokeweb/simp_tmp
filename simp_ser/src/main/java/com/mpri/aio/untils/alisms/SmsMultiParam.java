package com.mpri.aio.untils.alisms;

import java.util.List;
import java.util.Map;

/**
 * 多人短信发送参数封装
 * @author Cary
 *
 */
public class SmsMultiParam {

	
	private String templateCode;
	
	private String phoneNumbers;
	private List<String> phoneNumbersList;
	
	private String signName; 
	
	private String templateParam;
	private List<Map<String,String>> templateParamMapList;
	
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	
	public String getPhoneNumbers() {
		phoneNumbers="";
		String dot="";
		List<String> list=getPhoneNumbersList();
		
		for(int i=0;i<list.size();i++) {
			if(i>0) {
				dot=",";
			}
			phoneNumbers=phoneNumbers+dot+"'"+list.get(i)+"'";
		}
		
		phoneNumbers="["+phoneNumbers+"]";
		return phoneNumbers;
	}
	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	public List<String> getPhoneNumbersList() {
		return phoneNumbersList;
	}
	public void setPhoneNumbersList(List<String> phoneNumbersList) {
		this.phoneNumbersList = phoneNumbersList;
	}
	public String getSignName() {
		String newSignName="";
		int size=getPhoneNumbersList().size();
		for(int i=0;i<size;i++) {
			if(i==0) {
				newSignName="'"+signName+"'";
			}else {
				newSignName=newSignName+",'"+signName+"'";
			}
		}
		newSignName="["+newSignName+"]";
		return newSignName;
	}
	public void setSignName(String signName) {
		
		this.signName = signName;
	}
	public String getTemplateParam() {		
		templateParam="";
		
		String bigDot="";
		
		for(int i=0;i<getTemplateParamMapList().size();i++) {
			String smallDot="";
			String paramSingle="";
			if(i>0) {
				bigDot=",";
			}
			int j=0;
			for(Map.Entry<String, String> entry : getTemplateParamMapList().get(i).entrySet()){
			    String mapKey = entry.getKey();
			    String mapValue = entry.getValue();
			   
			    if(j>0) {
			    	smallDot=",";
			    }
			    paramSingle=paramSingle+smallDot+"'" + mapKey + "'";
			    paramSingle=paramSingle + ":'" + mapValue + "'";
			    j++;
			}
			//对象封
			templateParam=templateParam+bigDot+"{"+paramSingle+"}";
		}
		//数组封
		templateParam="["+templateParam+"]";

		return templateParam;
		
	}
	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}
	public List<Map<String, String>> getTemplateParamMapList() {
		return templateParamMapList;
	}
	public void setTemplateParamMapList(List<Map<String, String>> templateParamMapList) {
		this.templateParamMapList = templateParamMapList;
	}

}
