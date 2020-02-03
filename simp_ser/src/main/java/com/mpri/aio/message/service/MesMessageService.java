package com.mpri.aio.message.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.mapper.MesMessageMapper;
import com.mpri.aio.message.model.MesGroupCondition;
import com.mpri.aio.message.model.MesMessage;
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.model.MesUserMessage;
import com.mpri.aio.message.vo.MesGroupConditionEnum;
import com.mpri.aio.message.vo.MesGroupSchoolmates;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.utils.MailUtil;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.system.utils.SMSUtils;
import com.mpri.aio.untils.mq.KafkaSender;
import com.mpri.aio.untils.mq.KafkaTopic;

 /**   
 *  
 * @Description:  通知消息表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Mon Nov 12 17:31:22 CST 2018
 * @Version:      v_1.02
 *    
 */
@Service
public class MesMessageService extends CrudService<MesMessageMapper, MesMessage>  {
	
//	@Value("${sms.template_common}")
	private String template_common;
	
	@Value("${server.address.mapper}")
	private String ip;
	
	@Value("${server.port}")
	private String port;
	@Autowired
	private SmSchoolmateService schoolmateService;
	
	@Autowired
	private MesGroupConditionService mesGroupConditionService;
	
	@Autowired 
	private RedisCacheService redisCacheService;
	
	
	@Autowired
	private MesTemplateService mesTemplateService;
	
	@Autowired
	private MesUserMessageService mesUserMessageService;
	
    @Autowired
    private MailUtil mailUtil;
    
    @Autowired
    private SMSUtils smsUtil;
    
    @Autowired
    private KafkaSender<Map<String,Object>> kafkaSender;
   
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
       
    
    
    /**
     * 判断日期
    * <p>Title: judgeDate</p>  
    * <p>Description: </p>
     */
    public String judgeDate(MesMessage mes) {
    	Long currtTime = new Date().getTime();
    	if(mes.getDelDate() == null) {
    		return GlobalStr.NOT_OVERDUE;
    	}
    	if(mes.getDelDate().getTime() > currtTime) {
    		return GlobalStr.NOT_OVERDUE;
    	}
    	return GlobalStr.IS_OVERDUE;
    }
    
	/**
	 * 通过接受人或者接受组获取消息列表
	 */
	public List<MesMessage> getMesListBy(@Param("entity")MesMessage mesMessage){
		return mapper.getMesListBy(mesMessage);
	}
	
	
	/**
	 * 通过接受人或者接受组获取消息条数
	 */
	public int getMesCountBy(@Param("entity")MesMessage mesMessage) {
		return mapper.getMesCountBy(mesMessage);
	}
	
	/**
	 * 获取首页单条信息
	 */
	public MesMessage getFirstMesBy(@Param("entity")MesMessage mesMessage) {
		return mapper.getFirstMesBy(mesMessage);
	}
	
	/**
	 * 通过接受人或者接受组获取消息列表(已读)
	 */
	public List<MesMessage> getMesLisReadtBy(@Param("entity")MesMessage mesMessage){
		return mapper.getMesLisReadtBy(mesMessage);
	}
	
	/**
	 * 获取消息内容
	 */
	public MesMessage getMesContent(MesMessage mesMessage) {
		return mapper.getMesContent(mesMessage);
	}
		
	
	/**
	 * 通过发送方式发送信息
	 */
	@Async("mailTaskExecutor")
	public void sendMes(MesMessage mesMessage) {
		MesTemplate mesTemplate = new MesTemplate();
		mesTemplate.setId(mesMessage.getMesTemplate());
		MesTemplate resTemplate = mesTemplateService.get(mesTemplate);
		try {
			//获取接受人
 			List<MesGroupSchoolmates> schoolmates  = getReceives(mesMessage);
 			System.out.println("======================给"+schoolmates.size()+"人发送=================");
			if(GlobalStr.MES_SENDTYPE_SMS.equals(mesMessage.getSendType())) {
				for (MesGroupSchoolmates mesGroupSchoolmate : schoolmates) {
					//参数map列表
					Map<String,Object> sms_map=new HashMap<String,Object>();
					sms_map.put("mesMessage", mesMessage);
					sms_map.put("resTemplate", resTemplate);
					sms_map.put("smSchoolmate", mesGroupSchoolmate);
					//发送消息
					kafkaSender.send(KafkaTopic.SMS_MQ,sms_map);
					
				}
			}else if(GlobalStr.MES_SENDTYPE_EMAIL.equals(mesMessage.getSendType())) {
				for (MesGroupSchoolmates mesGroupSchoolmate : schoolmates) {
					//参数map列表
					Map<String,Object> email_map=new HashMap<String,Object>();
					
					email_map.put("mesMessage", mesMessage);
					email_map.put("resTemplate", resTemplate);
					email_map.put("smSchoolmate", mesGroupSchoolmate);
					//发送消息
					kafkaSender.send(KafkaTopic.EMAIL_MQ,email_map);
				}			
			}else {
				System.out.println("校友卡发送消息");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

	/**
	 * 监听sms 的 topic
	 *
	 * @param record
	 * @param topic  topic
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@KafkaListener(groupId="sms",id = "sms_id", topics = "sms_mq")
	public void listenSms(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

		//判断是否NULL
	    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
	    if (kafkaMessage.isPresent()) {
	        //获取消息
	        Object message = kafkaMessage.get(); 
	        Map<String ,Object> sms_map =(Map<String, Object>) JSON.parseObject(message.toString(),Map.class);
	        MesMessage mesMessage=(MesMessage)JSON.parseObject(sms_map.get("mesMessage").toString(),MesMessage.class);
	        MesTemplate resTemplate=(MesTemplate)JSON.parseObject(sms_map.get("resTemplate").toString(),MesTemplate.class);
	        MesGroupSchoolmates smSchoolmate=(MesGroupSchoolmates )JSON.parseObject(sms_map.get("smSchoolmate").toString(),MesGroupSchoolmates.class);
	        
			if(smSchoolmate.getContact() != null) {
				ArrayList<String> params = new ArrayList<String>();
				params.add(smSchoolmate.getName());
				params.add(mesMessage.getContent());
				//发送
				try {
						System.out.println("接受人========"+ smSchoolmate.getContact());
						String result = smsUtil.sendSms(smSchoolmate.getContact(),params,runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.sms_template_common));
						if(result.equalsIgnoreCase("success")) {
							//写库
							MesUserMessage mesUserMessage = new MesUserMessage();
							mesUserMessage.setMessageId(mesMessage.getId());
							mesUserMessage.setUserId(smSchoolmate.getUserId());
							mesUserMessage.setStatus(GlobalStr.MES_READ);
							mesUserMessageService.save(mesUserMessage);
							System.out.println("=============短信发送成功==============================");
						}
						
//						System.out.println("=============短信发送测试中=======\"接受人========"+smSchoolmate.getContact()+"======"+params.toString()+"=================");
				} catch (NumberFormatException e) {
					System.out.println("=============短信发送失败==============================");
				} catch (Exception e) {
					System.out.println("=============短信发送失败==============================");
				}
			}

	    }
	}
	
	
	/**
	 * 监听email 的 topic
	 *
	 * @param record
	 * @param topic  topic
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@KafkaListener(groupId="email",id = "email_id", topics = "email_mq")
	public void listenEmail(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){

		//判断是否NULL
	    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
	    if (kafkaMessage.isPresent()) {
	        //获取消息
	        Object message = kafkaMessage.get(); 
	        Map<String ,Object> email_map =(Map<String, Object>) JSON.parseObject(message.toString(),Map.class);
	        MesMessage mesMessage=(MesMessage)JSON.parseObject(email_map.get("mesMessage").toString(),MesMessage.class);
	        MesTemplate resTemplate=(MesTemplate)JSON.parseObject(email_map.get("resTemplate").toString(),MesTemplate.class);
	        MesGroupSchoolmates smSchoolmate=(MesGroupSchoolmates )JSON.parseObject(email_map.get("smSchoolmate").toString(),MesGroupSchoolmates.class);
	        
			if(smSchoolmate.getContact() != null) {
				//发送邮件 
				Map<String, Object> map = new HashMap<String, Object>();
				//更换姓名 日期
				String content  = mailUtil.replaceEl(smSchoolmate.getName(), mesMessage.getContent());
				
		    	//目前开发阶段由于图片无法外网访问，因此在发送邮件时图片无法展示（需公网情况下图片可对外网访问）
		    	map.put("backgroundImg", "http://"+ip+":"+port+"/file/view/"+ resTemplate.getBackgroupImg());
		    	
//	    		map.put("backgroundImg", "http://n.sinaimg.cn/news/1_img/upload/c4b46437/0/w1024h576/20190311/zqPQ-htzuhtp6453846.jpg");
				//发送消息的内容
		    	map.put("content", content);
		    	//发送
				try {
						mailUtil.asyncSendMail(smSchoolmate.getContact(), "sendEmail", map,
								StringUtil.isNotEmpty(smSchoolmate.getContact()) ?
										smSchoolmate.getContact()+"_"+mesMessage.getTitle() : System.currentTimeMillis() +"_"+ mesMessage.getTitle()
						);
						//写库
						MesUserMessage mesUserMessage = new MesUserMessage();
						mesUserMessage.setMessageId(mesMessage.getId());
						mesUserMessage.setUserId(smSchoolmate.getUserId());
						mesUserMessage.setStatus(GlobalStr.MES_READ);
						mesUserMessageService.save(mesUserMessage);
						System.out.println("=============邮箱发送成功==============================");
//						System.out.println("=============邮箱发送成功=======\"接受人========"+smSchoolmate.getContact()+"======"+content+"=================");
				} catch (Exception e) {
					//e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}

	    }
	}
	
	/**
	 * 获取接受人
	* <p>Title: getReceives</p>  
	* <p>Description: </p>
	 */
	public List<MesGroupSchoolmates> getReceives(MesMessage mesMessage) {
		SmContact contact = new SmContact();
		if(GlobalStr.MES_SENDTYPE_SMS.equals(mesMessage.getSendType())) {
			contact.setType(GlobalStr.CONTACT_TYPE_PHONE);
		}else if(GlobalStr.MES_SENDTYPE_EMAIL.equals(mesMessage.getSendType())) {
			contact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
		}
		if(GlobalStr.MES_NOTICE.equals(mesMessage.getType())) {
			SmSchoolmate schoolmate = new SmSchoolmate();
			schoolmate.setSmContact(contact);
			List<MesGroupSchoolmates> list  = schoolmateService.loadListByCondition(schoolmate);
			return list;
		}else if(GlobalStr.MES_NEWS.equals(mesMessage.getType())) {
			if(StringUtil.isNotEmpty(mesMessage.getReceiveGroupId())) { //接受组
				MesGroupCondition mesGroupCondition = new MesGroupCondition();
				mesGroupCondition.setGroupId(mesMessage.getReceiveGroupId());
				List<MesGroupCondition> conditions = mesGroupConditionService.loadAllListBy(mesGroupCondition);
				SmSchoolmate schoolmate = new SmSchoolmate();
				SmProfession smProfession = new SmProfession();
				SmEducation smEducation = new SmEducation();
				for (MesGroupCondition condition : conditions) {
					if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.DEPARTMENT.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							smEducation = this.getEduByDepartCondition(condition.getValue(),smEducation);
							schoolmate.setSmEducation(smEducation);
						}
					}/*else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.TYPE.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							schoolmate.setType(condition.getValue());
						}
					}*/else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.SEX.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							schoolmate.setSex(condition.getValue());
						}
						//新增条件
					}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.SCHOOLMATEMARK.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							schoolmate.setMarkIds(condition.getValue());
						}
					}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.COUNTRY.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							smProfession.setCountry(condition.getValue());
							schoolmate.setSmProfession(smProfession);
						}
					}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.PROVINCE.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							smProfession.setProvince(condition.getValue());
							schoolmate.setSmProfession(smProfession);
						}
					}else if(condition.getField().equalsIgnoreCase(MesGroupConditionEnum.CITY.getCode())) {
						if(StringUtil.isNotEmpty(condition.getValue())) {
							smProfession.setCity(condition.getValue());
							schoolmate.setSmProfession(smProfession);
						}
					} else if (condition.getField().equalsIgnoreCase(MesGroupConditionEnum.STARTDATE.getCode())) {
						if (StringUtil.isNotEmpty(condition.getValue())) {
							String[] startDateList = condition.getValue().split("-");
							if (startDateList != null && startDateList.length > 1){
								smEducation.setStartYearInternalFirst(startDateList[0].trim());
								smEducation.setStartYearInternalSencond(startDateList[1].trim());
								schoolmate.setSmEducation(smEducation);
							}
						}
					} else if (condition.getField().equalsIgnoreCase(MesGroupConditionEnum.ENDDATE.getCode())) {
						if (StringUtil.isNotEmpty(condition.getValue())) {
							String[] endDateList = condition.getValue().split("-");
							if (endDateList != null && endDateList.length > 1){
								smEducation.setEndYearInternalFirst(endDateList[0].trim());
								smEducation.setEndYearInternalSencond(endDateList[1].trim());
								schoolmate.setSmEducation(smEducation);
							}
						}
					}


				}
				schoolmate.setSmContact(contact);
				List<MesGroupSchoolmates> list  = schoolmateService.loadListByCondition(schoolmate);
				return list;
			}else{ //接受人
				SmSchoolmate schoolmate = new SmSchoolmate();
				schoolmate.setUserId(mesMessage.getReceiveUserId());
				schoolmate.setSmContact(contact);
				List<MesGroupSchoolmates> list  = schoolmateService.loadListByCondition(schoolmate);
				return list;
			}
		}
		return null;
	}
	
	public SmEducation getEduByDepart (String departmentId) {
		Map<String,SettingDepartment> departBaseCache= 
				(Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		SettingDepartment settingDepartment = departBaseCache.get(departmentId);
		SmEducation smEducation = new SmEducation();
		switch (settingDepartment.getType()) {
			case GlobalStr.SCHOOL:
				smEducation.setSchool(departmentId);
				break;
			case GlobalStr.COLLEGE:
				smEducation.setCollege(departmentId);
				break;
			case GlobalStr.DEPARTMENT:
				smEducation.setSeries(departmentId);
				break;
			case GlobalStr.MAJOR:
				smEducation.setSpecialty(departmentId);
				break;
		}
		return smEducation;
	}

	 public SmEducation getEduByDepartCondition (String departmentId,SmEducation smEducation) {
		 Map<String,SettingDepartment> departBaseCache=
				 (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
		 SettingDepartment settingDepartment = departBaseCache.get(departmentId);
		 switch (settingDepartment.getType()) {
			 case GlobalStr.SCHOOL:
				 smEducation.setSchool(departmentId);
				 break;
			 case GlobalStr.COLLEGE:
				 smEducation.setCollege(departmentId);
				 break;
			 case GlobalStr.DEPARTMENT:
				 smEducation.setSeries(departmentId);
				 break;
			 case GlobalStr.MAJOR:
				 smEducation.setSpecialty(departmentId);
				 break;
		 }
		 return smEducation;
	 }
}