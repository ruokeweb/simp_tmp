package com.mpri.aio.untils.task.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.message.model.MesScheduler;
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.service.MesSchedulerService;
import com.mpri.aio.message.service.MesTemplateService;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.utils.MailUtil;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.system.utils.SMSUtils;

/**
 * 定时通知消息
* <p>Title: MesSchedulerJob</p>  
* <p>Description: </p>  
* @author syp  
* @date 2019年3月12日
 */
@Component(value="MesSchedulerJob")
public class MesSchedulerJob implements Job {
	
//	@Value("${sms.template_common}")
	private String template_common;
	
	@Value("${server.address.mapper}")
	private String ip;
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private MesSchedulerService mesSchedulerService;
	
	@Autowired
	private MesTemplateService mesTemplateService;
	
	@Autowired
	private SmSchoolmateService schoolmateService;
	
	@Autowired
	private SmContactService smContactService;
	
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private SMSUtils smsUtil;
    
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;


	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		/**
		 * 1、当日的定时消息列表
		 * 2、给所有校友发送
		 */
		MesScheduler mesScheduler = new MesScheduler();
		List<MesScheduler> mesSchedulers = mesSchedulerService.loadListByDate(mesScheduler);
		//循环定时通知消息
		//获取校友
		SmSchoolmate schoolmate = new SmSchoolmate();
		List<SmSchoolmate> schoolmates = schoolmateService.loadAllListBy(schoolmate);
		for (MesScheduler mes : mesSchedulers) {
			MesTemplate mesTemplate = new MesTemplate();
			mesTemplate.setId(mes.getMesTemplate());
			mesTemplate = mesTemplateService.get(mesTemplate);
			//获取模板内容
			if(mesTemplate == null)
				return;
			//获取发送方式
			try {
				if(GlobalStr.MES_SENDTYPE_SMS.equals(mes.getSendType())) {
					//循环校友
					for (SmSchoolmate smSchoolmate : schoolmates) {
						SmContact smContact = new SmContact();
						smContact.setUserId(smSchoolmate.getUserId());
						smContact.setType(GlobalStr.PHONE);
						List<SmContact> smContacts = smContactService.loadAllListBy(smContact);
						if(smContacts.size() != 0) {
							ArrayList<String> params = new ArrayList<String>();
							params.add(smSchoolmate.getName());
							params.add(mesTemplate.getContent());
							smsUtil.sendSms(smContacts.get(0).getContact(),params,runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_template_common));
						}					
					}				
				}else if(GlobalStr.MES_SENDTYPE_EMAIL.equals(mes.getSendType())) {
					//循环校友
					for (SmSchoolmate smSchoolmate : schoolmates) {
						SmContact smContact = new SmContact();
						smContact.setUserId(smSchoolmate.getUserId());
						smContact.setType(GlobalStr.EMAIL);
						List<SmContact> smContacts = smContactService.loadAllListBy(smContact);
						if(smContacts.size() != 0) {
							//发送邮件
							Map<String, Object> map = new HashMap<String, Object>();
							//更换姓名 日期
							String content  = mailUtil.replaceEl(smSchoolmate.getName(), mes.getContent());
					    	//目前开发阶段由于图片无法外网访问，因此在发送邮件时图片无法展示（需公网情况下图片可对外网访问）
//					    	map.put("backgroundImg", "http://192.168.140.13:8081/file/view/"+ resTemplate.getBackgroupImg());
					    	
					    	map.put("backgroundImg", "http://"+ip+":"+port+"/file/view/"+ mesTemplate.getBackgroupImg());
							//发送消息的内容
					    	map.put("content", content);
							mailUtil.asyncSendMail(smContacts.get(0).getContact(), "sendEmail", map,
									StringUtil.isNotEmpty(smContacts.get(0).getContact()) ? 
											smContacts.get(0).getContact()+"_"+mes.getName() : System.currentTimeMillis() +"_"+ mes.getName()
									);
						}						
					}					
				}else {
					return;
				}
			} catch (Exception e) {
				//// ex.printStackTrace();;
				System.err.println(e.getMessage());
			}
			//发送消息
		}
	}

}
