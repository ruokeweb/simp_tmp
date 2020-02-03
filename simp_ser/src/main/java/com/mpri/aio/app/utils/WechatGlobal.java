package com.mpri.aio.app.utils;
/**
 * 微信常量类
 * @author suyupeng
 *
 */
public class WechatGlobal {
	
	public static final String WX_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";
	
	//微信获取access_token接口
	public static final String WX_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	//微信订阅信息接口
	public static final String WX_SUB_SEND="https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
	
	
	//通知参数
	public class NoticeTemplate{
		//缴费通知
		public static final String JFTZ="AnGEBDpTlbeYwxib7vn_WG_YKxYKpPHknJzrmEQ3mv8";
		//注册审核通知 
		public static final String ZCSH="DBvj0icOdt9obMpYMtTQoMPLXM3KI-BUMByeS8vAzsM";
		//审核结果通知
		public static final String SHJG="AIAQH66PLGBbA5RV3FtPDHvvODx6QqDxF0K9vDlkfwM";
		//活动开始通知
		public static final String HDKS="XzUbBjFMeMtQ-l9t-Xm-iY36uDOzj0HBK4y99p1JuYM";
	}
	
	
}
