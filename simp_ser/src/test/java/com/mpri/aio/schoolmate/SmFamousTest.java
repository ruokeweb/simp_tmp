package com.mpri.aio.schoolmate;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.message.model.MesTemplate;
import com.mpri.aio.message.service.MesTemplateService;
import com.mpri.aio.schoolmate.model.SmFamous;
import com.mpri.aio.schoolmate.service.SmContactService;
import com.mpri.aio.schoolmate.service.SmFamousService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.utils.MailUtil;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmFamousTest extends ApplicationTests {
	
	@Autowired
	private SmFamousService smFamousService;
	

	@Test
	public void test() {
//		SmSchoolmate sm = new SmSchoolmate();
//		List<SmSchoolmate> sms = schoolmateService.ExportList(sm);
//		System.out.println(sms.size());
		SmFamous smFamous = new SmFamous();
		PageIo<SmFamous> randList = smFamousService.loadByPage(0, 27, smFamous);
		System.out.println(randList);
	}
}
