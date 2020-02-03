package com.mpri.aio.schoolmate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.ApplicationTests;
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

public class SmSchoolmateTest extends ApplicationTests {	
	
	@Autowired
	private SmSchoolmateService schoolmateService;
	
	@Autowired
	private SmContactService smContactService;
	
	@Autowired
	private MesTemplateService mesTemplateService;
	
    @Autowired
    private MailUtil mailUtil;
    
    public static final String BIRTHDAY = "BIRTHDAY";
//	@Test
	public void test() {
//		SmSchoolmate sm = new SmSchoolmate();
//		List<SmSchoolmate> sms = schoolmateService.ExportList(sm);
//		System.out.println(sms.size());
		MesTemplate mesTemplate = new MesTemplate();
		mesTemplate.setType(GlobalStr.MES_TEMPLATE_BIRTH);
		List<MesTemplate> mesTemplates =  mesTemplateService.loadAllListBy(mesTemplate);
		
		if(mesTemplates.size() == 0)
			return;

//		try {
//			//发送邮件
//			Map<String, Object> map = new HashMap<String, Object>();
//	    	map.put("name", "zhangs");
//	    	//目前开发阶段由于图片无法外网访问，因此在发送邮件时图片无法展示（需公网情况下图片可对外网访问）
////		    	map.put("backgroundImg", "http://192.168.140.13:8081/file/view/"+ resTemplate.getBackgroupImg());
//	    	
//    		map.put("backgroundImg", "https://resu8.hjfile.cn/resu8/2019/02/27/4e187bfa6e4a4aeb85fff66012126513.jpg");
//	    	map.put("content", mesTemplates.get(0).getContent()+",+${name}+");
//	    	map.put("date", DateUtils.getDate("yyyy-MM-dd"));
//			mailUtil.asyncSendMail("caryskying@163.com", "sendEmail", map,
//				StringUtil.isNotEmpty("caryskying@163.com") ? 
//					"caryskying@163.com"+"_"+BIRTHDAY : System.currentTimeMillis() +"_"+ BIRTHDAY
//			);
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 		
		
	}
	
	@Test
	public void getSmInfo() {
		SmSchoolmate schoolmate = new SmSchoolmate();
		schoolmate.setUserId("9547f60db68f4cd4877eec0b5e87f1e3");
		schoolmate =  schoolmateService.getUserINfoById(schoolmate);
		System.out.println(schoolmate.getSmEducations().size());
	}
}
