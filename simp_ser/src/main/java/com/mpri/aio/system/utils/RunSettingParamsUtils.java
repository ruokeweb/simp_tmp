package com.mpri.aio.system.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.service.SysSettingService;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 运行参数
 * @author syp
 *
 */
@Component
public class RunSettingParamsUtils{

	public static final String wechat_secret  = "wechat.secret";	//小程序配置
	public static final String wechat_mchId_key = "wechat.mchId.key";	//小程序配置
	public static final String vsb_webber_cid = "vsb.webber.cid";	//小程序新闻配置
	public static final String spring_mail_port = "spring.mail.port";	//邮件参数
	public static final String spring_mail_smtp_auth = "spring.mail.smtp.auth";	//邮件参数
	public static final String spring_mail_default_encoding = "spring.mail.default-encoding";	//邮件参数
	public static final String spring_mail_smtp_ssl_checkserveridentity =	"spring.mail.smtp.ssl.checkserveridentity";	//邮件参数
	public static final String sms_appkey =	"sms.appkey";	//腾讯云短信接口
	public static final String wechat_appId = "wechat.appId";	//小程序配置
	public static final String vsb_webber_password = "vsb.webber.password";	//小程序新闻配置
	public static final String ps_salt = "ps.salt";	//加密盐值
	public static final String vsb_webber_username = "vsb.webber.username";	//小程序新闻配置
	public static final String sms_template_regdit = "sms.template_regdit";	//腾讯云短信接口
	public static final String sms_regionId = "sms.regionId";	//阿里云短信
	public static final String sms_template_backPwd = "sms.template_backPwd";	//腾讯云短信接口
	public static final String prove_num =	"prove.num"; //认证人数量
	public static final String sms_accountID  = "sms.accountID";	//第三方自建短信平台
	public static final String sms_serviceUrl = "sms.serviceUrl";	//第三方自建短信平台
	public static final String sms_appid = "sms.appid";	//腾讯云短信接口
	public static final String sms_accessKey = "sms.accessKey";	//阿里云短信
	public static final String vsb_webber_ownnerId = "vsb.webber.ownnerId";	//小程序新闻配置
	public static final String spring_mail_host = "spring.mail.host";	//邮件参数
	public static final String spring_mail_password = "spring.mail.password";	//邮件参数
	public static final String spring_mail_properties_mail_smtp_starttls_enable = "spring.mail.properties.mail.smtp.starttls.enable";	//邮件参数
	public static final String spring_mail_mailType = "spring.mail.mailType";	//邮件参数
	public static final String sms_accountKey = "sms.accountKey";	//第三方自建短信平台
	public static final String vsb_webber_domain = "vsb.webber.domain";	//小程序新闻配置
	public static final String vsb_webber_url = "vsb.webber.url";	//小程序新闻配置
	public static final String rank_cardNum = "rank.cardNum";	//小程序排名前后人数
	public static final String vsb_webber_wbfid = "vsb.webber.wbfid";	//小程序新闻配置
	public static final String sms_template_changeBind = "sms.template_changeBind";	//腾讯云短信接口
	public static final String sms_template_common = "sms.template_common";	//腾讯云短信接口
	public static final String sms_version = "sms.version";	//阿里云短信
	public static final String spring_mail_username = "spring.mail.username";	//邮件参数
	public static final String spring_mail_properties_mail_smtp_starttls_required =	"spring.mail.properties.mail.smtp.starttls.required";	//邮件参数
	public static final String spring_mail_adminAddress = "spring.mail.adminAddress";	//邮件参数
	public static final String sms_accessKeySecret = "sms.accessKeySecret";	//阿里云短信
	public static final String wechat_mchId = "wechat.mchId";	//小程序配置
	public static final String sms_domain = "sms.domain";	//阿里云短信

	
	//小程序配置
	public static final String is_on_webvsb = "is_on_webvsb"; //是否开启网站群新闻
	public static final String school_name = "school_name";
	public static final String is_on_index_mediaMes = "is_on_index_mediaMes";
	public static final String is_don_project = "is_don_project";
	public static final String is_don_team = "is_don_team";
	public static final String template_jftz = "template_jftz";
	public static final String template_zcsh = "template_zcsh";
	public static final String template_shjg = "template_shjg";
	public static final String template_hdks = "template_hdks";
	
	
    @Autowired
    private RedisCacheService redisCacheService;
	
	public String getSettingByKey(String key) {
        List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, key);
        try {
            if(list.size() > 0) {
            	SysSetting setting = list.get(0);
            	if(StringUtil.isNotEmpty(setting.getValue())) {
            		return setting.getValue();
            	}
            }
            return null;
		} catch (Exception e) {
			return null;
		}
    }

}
