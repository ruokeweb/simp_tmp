package com.mpri.aio.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.system.utils.MailUtil;
import com.mpri.aio.system.utils.SMSUtils;
import com.mpri.aio.system.utils.TxSMSUtil;

public class MailUtilTest extends ApplicationTests {

	@Autowired
	private MailUtil mailUtil;
	
	
	@Value("${sms.template_backPwd}")
	private String template_backPwd;
	
    @Autowired
    private SMSUtils smsUtil;
    
    @Autowired
    private TxSMSUtil txsmsUtil;
    
	@Test
	public void test() {
		Map map = new HashMap();
		String content = 
				"	<div style=\"height: 100%;background-image: url('http://img.alicdn.com/tfs/TB1zhJccDtYBeNjy1XdXXXXyVXa-2794-400.png'); background-size: cover; background-repeat: repeat\">" + 
				"		<div style=\"width: 98%; height: 98%; \">" + 
				"			<p style=\"margin-left: 6.25rem;float: left; width: 98%; font-size: 16px; font-weight: bolder;\" >尊敬的校友:</p>" + 
				"			<p style=\"margin-left: 7.25rem; text-indent: 2em; font-size: 16px;\">笔记本北京租比市场价不接啊设备厂家这次不行冲击波将笔记本中独具超级杯别急着说从笔记本就撒都不不接啊三四百的常见病速度不变·2uhasbdjbasjdfbub1jbajdsczhbxcbh1hbjzdbhjcfbhj·1环保局进口政策性户籍阿巴斯不能剧场版您哈斯必定会就不着急行程报价编辑好不长记性才不会的笔记本聚精会神边吃边环境的保存基本中东部回车键会被删除计划表蚕丝被自动化三场比赛的计划表加把劲蚕丝被煎熬保护层把环境不会传播的环境中把环境编辑保存家在东北很长时间好吧好吧</p>" + 
				"			<p style=\"float: right; padding-right: 6.25rem;font-size: 16px;\">2019-03-12</p>" + 
				"		</div>" + 
				"	</div>";
		try {
			mailUtil.sendMailTls("suyupeng@aliyun.com", "测试html发送", "测试html发送");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//// ex.printStackTrace();;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			//// ex.printStackTrace();;
		} 
	}
//    @Test
    public void testSms() {
    	try {
            ArrayList<String> params = new ArrayList<String>();
            params.add("阿萨斯");
            params.add("1231阿萨斯的23");
			smsUtil.sendSms("17629261881",params,template_backPwd);
//    		txsmsUtil.sendSMS("17629261881", params, template_backPwd);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
