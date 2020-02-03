package com.mpri.aio.schoolmate;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.message.service.MesTemplateService;
import com.mpri.aio.schoolmate.model.*;
import com.mpri.aio.schoolmate.service.*;
import com.mpri.aio.system.utils.MailUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

public class SmSchoolmateRemarkTest extends ApplicationTests {

	@Autowired
	private SmSchoolmateService schoolmateService;

	@Autowired
	private SmContactService smContactService;

	@Autowired
	private MesTemplateService mesTemplateService;

	@Autowired
	private SmPoliticsService smPoliticsService;

	@Autowired
	private SmAddressService smAddressService;

	@Autowired
	private SmProfessionService smProfessionService;

	@Autowired
	private SmSocialService smSocialService;

	@Autowired
	private SmEducationService smEducationService;

	@Autowired
	private SchoolmateMergeService schoolmateMergeService;

	@Autowired
	private SmHistorydataService smHistorydataService;

	@Autowired
	private MailUtil mailUtil;

	public static final String BIRTHDAY = "BIRTHDAY";

	@Test
	public void test() {
		// sm_education  sm_schoolmate
		/**
		 * 处理sm_education
		 */

		/*List<SmEducation> smEducations = smEducationService.loadAllListBy(new SmEducation());
		for (SmEducation smEducation:smEducations) {
			String remark = smEducation.getRemark();
			if(StringUtil.isNotEmpty(remark)){
				String[] split = remark.split(",");
				String remark1 = "";
				for(int i=0,len=split.length;i<len;i++){
					if(StringUtil.isNotEmpty(split[i])&&!split[i].contains("null")){
						if("".equals(remark1)){
							remark1  = split[i];
						}else{
							remark1  = remark1+","+split[i];
						}
					}
				}
				smEducation.setRemark(remark1);
				smEducationService.updateRemark(smEducation);
			}

		}*/
	}
	@Test
	public void schoolmateTest(){
		/*List<SmSchoolmate> smSchoolmates = schoolmateService.loadAllListBy(new SmSchoolmate());
		for (SmSchoolmate smSchoolmate:smSchoolmates) {
			String remark = smSchoolmate.getRemark();
			//个人爱好:null,个人说明:null,去世时间:null
			if(StringUtil.isNotEmpty(remark)){
				String[] split = remark.split(",");
				String remark1 = "";
				for(int i=0,len=split.length;i<len;i++){
					if(!split[i].contains("null")){
						if("".equals(remark1)){
							remark1  = split[i];
						}else{
							remark1  = remark1+","+split[i];
						}
					}

				}
				smSchoolmate.setRemark(remark1);
				schoolmateService.updateRemark(smSchoolmate);
			}


		}*/


	}

}