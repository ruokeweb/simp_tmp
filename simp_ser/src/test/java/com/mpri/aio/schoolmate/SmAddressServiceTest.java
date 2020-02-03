package com.mpri.aio.schoolmate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.ApplicationTests;
//import com.mpri.aio.schoolmate.model.SmEducation;
//import com.mpri.aio.schoolmate.model.SmSchoolmate;
//import com.mpri.aio.schoolmate.service.SmAddressService;
//import com.mpri.aio.schoolmate.service.SmLoginSignService;
//import com.mpri.aio.schoolmate.service.SmSchoolmateFriendService;
//import com.mpri.aio.schoolmate.service.SmSchoolmateProveService;
//import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.utils.MailUtil;

import freemarker.template.TemplateException;

public class SmAddressServiceTest extends ApplicationTests{

//	@Autowired
//	private SmAddressService smAddressService;
//	@Autowired
//	private SmSchoolmateService smSchoolmateService;
//    @Autowired
//    private MailUtil mailUtil;
//	
//	@Autowired
//	private SmSchoolmateTempService smSchoolmateTempService;
//	@Autowired
//	private SmSchoolmateFriendService fried;
//	
//	@Autowired
//	private SmLoginSignService smLoginSignService;
//	
//	@Autowired
//	private SmSchoolmateProveService smSchoolmateProveService;
//	
////	@Test
//	public void getNationBySysUserTest() {
//
//		SmSchoolmate schoolmate = new SmSchoolmate();
////		schoolmate.setPosLat("34.195988");
////		schoolmate.setPosLong("108.840164");
////		schoolmate.setSex("MALE");
////		schoolmate.setName("苏宇鹏");
////		SmAddress smAddress = new SmAddress();
////		smAddress.setProvince("110100");
////		smAddress.setCity("110000");
////		smAddress.setDistrict("110101");
////		schoolmate.setSmAddress(smAddress);
//		SmEducation smEducation = new SmEducation();
//		smEducation.setCollege("da18f27feb86462b801c2d4fe1a21d8b");
//		smEducation.setDegree("GRADUATE_COLLEGE");
////		SmContact smContact = new SmContact();
////		smContact.setContact("176");
////		schoolmate.setSmContact(smContact);
//		schoolmate.setCardStatus("NORMAL");
//		schoolmate.setSmEducation(smEducation);
//		schoolmate.setParamA("1");
//		schoolmate.setParamB("5");
//		List<SmSchoolmate> schoolmates = smSchoolmateService.getListByWeichat(schoolmate);
//		System.err.println(schoolmates.size());
////		List<SmSchoolmate> schoolmates = smSchoolmateService.getAroudSms(schoolmate,2000);
//		for (SmSchoolmate smSchoolmate : schoolmates) {
//			System.err.println(smSchoolmate.getUsername());
////	        super.outprint("com.mpri.aio.schoolmate.model.SmSchoolmate", smSchoolmate);	
//		}
//	}
//	
////	
//	@Test
//	public void getNationBySysUserTest1() {
////		SmSchoolmateFriend smSchoolmateFriend = new SmSchoolmateFriend();
////		smSchoolmateFriend.setUsername("17629261881");
////		smSchoolmateFriend.setParamA(GlobalStr.BOOLEAN_YES);
////		List<SmSchoolmateFriend> list =  fried.loadAllInfoListBy(smSchoolmateFriend);	
////		for (SmSchoolmateFriend s : list) {
////			System.err.println(s.getFriendName());
////			List<SmContact> cons = s.getFriendContacts();
////			for (SmContact c : cons) {
////				System.err.println(c.getContact());
////			}
////		}
//		
////		SmSchoolmateProve smSchoolmateProve = new SmSchoolmateProve();
////		//还需对方确认的
//////		smSchoolmateProve.setStartUserId("293030606f5d45b38604ef754fdb55e3");
//////		smSchoolmateProve.setStartUsername("13484478235");
//////		smSchoolmateProve.setProveUserId("c003ff97a3ea4d3989e562a085f58d9e");
//////		smSchoolmateProve.setProveUsername("17629261881");
////		
////		smSchoolmateProve.setStartUserId("a7061a3dfb33436bbb13d09ddd28439a");
////		smSchoolmateProve.setStartUsername("18229080727");
////		smSchoolmateProve.setProveUserId("c003ff97a3ea4d3989e562a085f58d9e");
////		smSchoolmateProve.setProveUsername("17629261881");		
////		String res = smSchoolmateProveService.congnizeTest(smSchoolmateProve);
////		System.out.println(res);
//		
//		SmSchoolmate schoolmate = new SmSchoolmate();
//		schoolmate.setUsername("15596887871");
//		PageInfo<SmSchoolmate> sms = smSchoolmateService.getListByEdu(schoolmate,1,10);
//		for (SmSchoolmate smSchoolmate : sms.getList()) {
//			System.err.println(smSchoolmate.getUsername());
//		}
//	}
//	
//	
////	@Test
//	public void getInfoList() {
//		SmSchoolmateTemp smSchoolmateTemp = new SmSchoolmateTemp();
//		
//		smSchoolmateTemp.setName("王锤锤");
//		smSchoolmateTemp.setSchool("9919");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("params", smSchoolmateTemp);
////		smSchoolmate.setFlag("FLAG_NORMAL");
////		List<SmSchoolmate> schoolmates = smSchoolmateService.loadInfoAllListBy(smSchoolmate);
////		System.out.println(schoolmates.size());
////		super.outprint("java.util.List", schoolmates);
////		smSchoolmateTempService.cardIssue("suyupeng@aliyun.com", map);
//		try {
//			System.out.println("33");
//			
//
//			mailUtil.asyncSendMail("suyupeng@aliyun.com", "email", map,"");
//	        //2、主线程sleep()
//	        Thread.sleep(2000);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
