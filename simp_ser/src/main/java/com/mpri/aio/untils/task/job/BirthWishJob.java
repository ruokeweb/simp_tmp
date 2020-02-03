package com.mpri.aio.untils.task.job;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.service.MesTemplateService;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.utils.MailUtil;

import freemarker.template.TemplateException;

/**
 * 生日祝福 job
* <p>Title: BirthWishJob</p>  
* <p>Description: </p>  
* @author syp  
* @date 2019年3月12日
 */
@Component(value = "BirthWishJob")
public class BirthWishJob implements Job{
	
	@Autowired
	private SmSchoolmateService schoolmateService;
	
	@Autowired
	private SmContactService smContactService;
	
	@Autowired
	private MesTemplateService mesTemplateService;
	
    @Autowired
    private MailUtil mailUtil;
    
	@Value("${server.address.mapper}")
	private String ip;
	
	@Value("${server.port}")
	private String port;
	
    public static final String BIRTHDAY = "BIRTHDAY";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/*
		 * 1、获取生日祝福消息模板
		 * 2、根据当前日期获取 当天过生日的校友
		 * 3、获取这些校友的邮箱（实现发送邮件）
		 * 
		 * */
		MesTemplate mesTemplate = new MesTemplate();
		mesTemplate.setType(GlobalStr.MES_TEMPLATE_BIRTH);
		List<MesTemplate> mesTemplates =  mesTemplateService.loadAllListBy(mesTemplate);
		System.err.println(mesTemplates.size());
		if(mesTemplates.size() == 0)
			return;
		//获取校友
		SmSchoolmate schoolmate = new SmSchoolmate();
		try {
			List<SmSchoolmate> schoolmates = schoolmateService.loadListByBirth(schoolmate);
			for (SmSchoolmate smSchoolmate : schoolmates) {
				SmContact smContact = new SmContact();
				smContact.setUserId(smSchoolmate.getUserId());
				smContact.setType(GlobalStr.EMAIL);
				List<SmContact> smContacts = smContactService.loadAllListBy(smContact);
				if(smContacts.size() != 0) {
					//发送邮件
					Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("name", smSchoolmate.getName());
			    	//目前开发阶段由于图片无法外网访问，因此在发送邮件时图片无法展示（需公网情况下图片可对外网访问）
			    	map.put("backgroundImg", "http://"+ip+":"+port+"/file/view/"+ mesTemplate.getBackgroupImg());
			    	
//		    		map.put("backgroundImg", "https://resu8.hjfile.cn/resu8/2019/02/27/4e187bfa6e4a4aeb85fff66012126513.jpg");
			    	map.put("content", mesTemplates.get(0).getContent());
			    	map.put("date", DateUtils.getDate("yyyy-MM-dd"));
						mailUtil.asyncSendMail(smContacts.get(0).getContact(), "sendEmail", map,
								StringUtil.isNotEmpty(smContacts.get(0).getContact()) ? 
										smContacts.get(0).getContact()+"_"+BIRTHDAY : System.currentTimeMillis() +"_"+ BIRTHDAY
								);
				}
			}
		}catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (TemplateException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		} catch (GeneralSecurityException e) {
			System.out.println(e.getMessage());
		} 	
	}

}
