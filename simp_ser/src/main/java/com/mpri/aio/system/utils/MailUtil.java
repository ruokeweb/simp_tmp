package com.mpri.aio.system.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.system.common.GlobalStr;
import com.sun.mail.util.MailSSLSocketFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class MailUtil {

    @Autowired
    private FreeMarkerConfigurer configurer;

    /*校友卡邮件发送主题*/
    public static final String MAIL_SUBJECT = "校友卡";

    /*姓名表达式*/
    public static final String NAME_EL = "{name}";

    /*日期表达式*/
    public static final String DATA_EL = "{date}";
    
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    

    /**
     * 管理员邮箱
     */
//    //	@Value("${spring.mail.adminAddress}")
//    private static String adminAddress = (String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_adminAddress);
//    //	@Value("${spring.mail.username}")
//    private static String username = (String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_username);
//    //	@Value("${spring.mail.port}")
//    private static String port =(String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_port);
//    //	@Value("${spring.mail.password}")
//    private static String password = (String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_password);
//    //	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private static Boolean tlsEnable= (Boolean) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_properties_mail_smtp_starttls_enable);
//    //	@Value("${spring.mail.smtp.ssl.checkserveridentity}")
//    private static Boolean checkserveridentity = (Boolean) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_smtp_ssl_checkserveridentity);
//    //	@Value("${spring.mail.host}")
//    private static String host= (String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_host);
//    //  @Value("${spring.mail.mailType}")
//    private static String mailType= (String) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_mailType);
//    //	@Value("${spring.mail.smtp.auth}")
//    private static Boolean smtpAuth= (Boolean) runSettingParamsUtils.getSettingByKey(String.class,RunSettingParamsUtils.spring_mail_smtp_auth);

	//@Value("${spring.mail.adminAddress}")
	private String adminAddress;
	//@Value("${spring.mail.username}")
	private String username;
	//@Value("${spring.mail.port}")
	private String port;
	//@Value("${spring.mail.password}")
	private String password;
	//@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private Boolean tlsEnable;
	//@Value("${spring.mail.smtp.ssl.checkserveridentity}")
	private Boolean checkserveridentity;
	//@Value("${spring.mail.host}")
	private String host;
	//@Value("${spring.mail.mailType}")
	private String mailType;
	//@Value("${spring.mail.smtp.auth}")
	private Boolean smtpAuth;    
    
	

	/**
	 * 使用freemarker模板异步发送邮件
	 * @param toEmail 目标地址
	 * @param templateName freemarker模板名称
	 * @param model 模板参数
	 * @throws IOException
	 * @throws TemplateException
	 * @throws MessagingException
	 * @throws GeneralSecurityException 
	 */
	public void asyncSendMail(String toEmail, String templateName,Map<String, Object> map,String subject)
			throws IOException, TemplateException, MessagingException, GeneralSecurityException{
	
		if(!templateName.endsWith(".ftl"))
		{
			templateName=templateName+".ftl";
		}
		Template template = configurer.getConfiguration().getTemplate(templateName);
		String html = FreeMarkerTemplateUtils
				.processTemplateIntoString(template, map);
		sendMail(toEmail, html,subject);
	}
	
	
	/**
	 * 同步发送邮件，由于网络原因，响应可能被阻塞
	 * @param toEmail 目标地址
	 * @param html 发送内容
	 * @throws IOException
	 * @throws MessagingException
	 * @throws GeneralSecurityException 
	 */
	public void sendMail(String toEmail, String html,String subject) throws IOException, MessagingException, GeneralSecurityException{
	
		if(GlobalStr.Mail_TLS.equals(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_mailType))) {
			sendMailTls(toEmail, html,subject);
		}else {
			sendMailSsl(toEmail, html,subject);
		}
	}
	
	/**
	 * Tls 发送邮件()
	* <p>Title: sendMailTls</p>  
	* <p>Description: </p>
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws UnsupportedEncodingException 
	 */
	public void sendMailTls(String toEmail, String html,String subject) throws AddressException, MessagingException, UnsupportedEncodingException { 
		Properties props = new Properties();
		props.put("mail.smtp.auth", Boolean.parseBoolean(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_smtp_auth)));
		props.put("mail.smtp.starttls.enable", 
				Boolean.parseBoolean(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_properties_mail_smtp_starttls_enable)));
		//不做服务器证书校验
		props.put("mail.smtp.ssl.checkserveridentity", 
				Boolean.parseBoolean(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_smtp_ssl_checkserveridentity)));
//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//添加信任的服务器地址，多个地址之间用空格分开
		props.put("mail.smtp.host", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_host));
		props.put("mail.smtp.port", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_port));
		props.put("mail.smtp.ssl.trust", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_host));
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_username), 
								runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_password));
					}
				});
		Message message = new MimeMessage(session);
		message.setSubject(this.encodedSuject(subject));
		message.setFrom(new InternetAddress(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_adminAddress)));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
		message.setContent(html, "text/html;charset=UTF-8");
		System.out.println("===========================TLS邮件发送中======================");
		Transport.send(message);
	}
	
	/**
	 * ssl 发送邮件()
	 * @throws MessagingException 
	 * @throws UnsupportedEncodingException 
	 * @throws GeneralSecurityException 
	 */
	public void sendMailSsl(String toEmail, String html,String subject) throws MessagingException, UnsupportedEncodingException, GeneralSecurityException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", Boolean.parseBoolean(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_smtp_auth)));
		props.put("mail.smtp.ssl.enable", 
				"true");
		//不做服务器证书校验
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//添加信任的服务器地址，多个地址之间用空格分开
		props.put("mail.smtp.host", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_host));
		props.put("mail.smtp.port", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_port));
		props.put("mail.smtp.ssl.trust", runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_host));
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
            //发信人的账号 密码
            return new PasswordAuthentication(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_username)
            		, runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_password));
            }
        };
        Session session = Session.getInstance(props, auth);
		Message message = new MimeMessage(session);
		message.setSubject(this.encodedSuject(subject));
		message.setFrom(new InternetAddress(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.spring_mail_adminAddress)));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
		message.setContent(html, "text/html;charset=UTF-8");
		System.out.println("===========================SSL邮件发送中======================");
		Transport.send(message);
	}
	
	
	
	/**
	 * 重新编译主题
	 * @throws UnsupportedEncodingException 
	 */
	private String encodedSuject(String subject) throws UnsupportedEncodingException {
		return MimeUtility.encodeText(subject, MimeUtility.mimeCharset("gb2312"), null);
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