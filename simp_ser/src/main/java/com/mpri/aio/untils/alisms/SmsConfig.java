package com.mpri.aio.untils.alisms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
	private String signName="博达软件";
	
//	@Value("${sms.regionId}")
	private String regionId;
	
//	@Value("${sms.domain}")
	private String domain;
	
//	@Value("${sms.version}")
	private String version;
	
//	@Value("${sms.accessKey}")
	private String accessKey;
	
//	@Value("${sms.accessKeySecret}")
	private String accessKeySecret;

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	
	
	
}
