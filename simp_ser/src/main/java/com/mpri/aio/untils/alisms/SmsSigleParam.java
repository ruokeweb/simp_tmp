package com.mpri.aio.untils.alisms;

import java.util.Map;

/**
 * 短信发送参数封装
 * @author Cary
 *
 */
public class SmsSigleParam {

	private String phoneNumbers;
	
	private String templateCode;
	
	private String templateParam;
	
	private Map<String,String> templateParamMap;

	public String getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateParam() {
		templateParam="";
		int i=0;
		for(Map.Entry<String, String> entry : getTemplateParamMap().entrySet()){
		    String mapKey = entry.getKey();
		    String mapValue = entry.getValue();
		    String dot="";
		    if(i>0) {
		    	dot=",";
		    }
		    templateParam=templateParam+dot+"'" + mapKey + "'";
		    templateParam=templateParam + ":'" + mapValue + "'";
		    i++;
		}
		templateParam="{"+templateParam+"}";
		
		return templateParam;
	}

	public void setTemplateParam(String templateParam) {
		this.templateParam = templateParam;
	}

	public Map<String, String> getTemplateParamMap() {
		return templateParamMap;
	}

	public void setTemplateParamMap(Map<String, String> templateParamMap) {
		this.templateParamMap = templateParamMap;
	}
	
	
	
	
}
