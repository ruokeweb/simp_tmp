package com.mpri.aio.schoolmate;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.app.reg.utils.InfoUtils;
import com.mpri.aio.message.service.MesTemplateService;
import com.mpri.aio.schoolmate.model.*;
import com.mpri.aio.schoolmate.service.*;
import com.mpri.aio.system.utils.MailUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmSchoolmateBrithdayTest extends ApplicationTests {
	
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
    private MailUtil mailUtil;
    
    public static final String BIRTHDAY = "BIRTHDAY";
	@Test
	public void test() {
		List<SmSchoolmate> list = schoolmateService.getListByCardNum(new SmSchoolmate());
		System.out.println(list);
		for (SmSchoolmate sm:
			 list) {
			String cardNum = sm.getCardNum();
			if((cardNum.length()==15||cardNum.length()==18)&&isStartWithNumber(cardNum)){
				Date birthByIdCard = InfoUtils.getBirthByIdCard(cardNum);
				sm.setBirthday(birthByIdCard);
				schoolmateService.save(sm);
			}
		}

	}

	//判断字符串是不是以数字开头
	public static boolean isStartWithNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str.charAt(0)+"");
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}


	/**
	 * 处理校友基本信息
	 */
	@Test
	public void mergeSm(){
		//获取所有校友
		/*List<SmSchoolmate> smSchoolmates = schoolmateService.getUserEduInfo(new SmSchoolmate());
		//将第一条取出 判断是否有相同校友
		this.deleteSchoolmate(smSchoolmates,smSchoolmates.size());
*/
		//合并校友
		//删除map集合的已经合并的校友
	}

	/**
	 * 处理联系方式
	 */
	@Test
	public void megreContact(){
		List<SmContact> userIds = smContactService.getUserId();
		for (SmContact smContact:userIds) {
			List<SmContact> smContacts = smContactService.loadAllListBy(smContact);
			this.deleteContact(smContacts,smContacts.size());
		}
	}

	/**
	 * 处理地址
	 */
	@Test
	public void megreAddress(){
		List<SmAddress> userIds = smAddressService.getUserId();
		for (SmAddress smAddress:userIds) {
			List<SmAddress> smAddresss = smAddressService.loadAllListBy(smAddress);
			this.deleteAddress(smAddresss,smAddresss.size());
		}
	}

	/**
	 * 处理教育经历
	 */
	@Test
	public void megreEducation(){
		List<SmEducation> userIds = smEducationService.getUserId();
		for (SmEducation SmEducation:userIds) {
			List<SmEducation> smEducations = smEducationService.loadAllListBy(SmEducation);
			this.deleteEducation(smEducations,smEducations.size());
		}


	}

	/**
	 * 处理职业经历
	 */
	@Test
	public void megreProfession(){
		List<SmProfession> userIds = smProfessionService.getUserId();
		for (SmProfession smProfession:userIds) {
			List<SmProfession> smProfessions = smProfessionService.loadAllListBy(smProfession);
			this.deleteProdession(smProfessions,smProfessions.size());
		}
	}
	/**
	 * 处理社会经历
	 */
	@Test
	public void megreSocial(){
		List<SmSocial> userIds = smSocialService.getUserId();
		for (SmSocial smSocial:userIds) {
			List<SmSocial> smSocials = smSocialService.loadAllListBy(smSocial);
			this.deleteSocial(smSocials,smSocials.size());
		}
	}
	//删除相同的校友schoolmate
	public  void deleteSocial(List<SmSocial> smSocials ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmSocial nowSocial = smSocials.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmSocial smSocial =smSocials.get(i);
			if(nowSocial.getName().equals(smSocial.getName())
					&&nowSocial.getPosition().equals(smSocial.getPosition())
					&&nowSocial.getInfoval().equals(smSocial.getInfoval())
			){
				smSocialService.delete(smSocial);
				smSocials.remove(smSocial);
			}
		}
		smSocials.remove(nowSocial);
		this.deleteSocial(smSocials,smSocials.size());
	}
	//删除相同的校友schoolmate
	public  void deleteSchoolmate(List<SmSchoolmate> smSchoolmates ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmSchoolmate nowSchoolmate = smSchoolmates.get(nowNum-1);
		List<SmSchoolmate> listCompare = schoolmateService.getListCompareByUserId(nowSchoolmate);
		schoolmateMergeService.mergeSchoomates(listCompare,nowSchoolmate);
		for (int j = 0 ;j<listCompare.size() ;j++){
			for(int i=nowNum-2;i>=0;i--)
			{
				//查出和第一条相同的校友
				if(listCompare.get(j).getId().equals(smSchoolmates.get(i).getId())){
					smSchoolmates.remove(smSchoolmates.get(i));
				}
			}
		}
		smSchoolmates.remove(nowSchoolmate);
		this.deleteSchoolmate(smSchoolmates,smSchoolmates.size());
	}

	//删除相同的教育经历
	public  void deleteEducation(List<SmEducation> smEducations ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmEducation nowEducation = smEducations.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmEducation SmEducation =smEducations.get(i);
			if(nowEducation.getSchool().equals(SmEducation.getSchool())
					&&nowEducation.getCollege().equals(SmEducation.getCollege())
					&&nowEducation.getSeries().equals(SmEducation.getSeries())
					&&nowEducation.getSpecialty().equals(SmEducation.getSpecialty())
					&&nowEducation.getClasses().equals(SmEducation.getClasses())
					&&nowEducation.getEduRecord().equals(SmEducation.getEduRecord())
					){
				smEducationService.delete(SmEducation);
					smEducations.remove(SmEducation);
			}
		}
		smEducations.remove(nowEducation);
		this.deleteEducation(smEducations,smEducations.size());
	}

	//删除相同的职业经历
	public  void deleteProdession(List<SmProfession> smProfessions ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmProfession nowProfession = smProfessions.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmProfession smProfession =smProfessions.get(i);
			if(nowProfession.getType().equals(smProfession.getType())
					&&nowProfession.getCountry().equals(smProfession.getCountry())
					&&nowProfession.getProvince().equals(smProfession.getProvince())
					&&nowProfession.getCity().equals(smProfession.getCity())
					&&nowProfession.getDistrict().equals(smProfession.getDistrict())
					&&nowProfession.getDetail().equals(smProfession.getDetail())
					&&nowProfession.getWorkplace().equals(smProfession.getWorkplace())
					&&nowProfession.getNature().equals(smProfession.getNature())
					&&nowProfession.getIndustry().equals(smProfession.getIndustry())
					&&nowProfession.getDepartment().equals(smProfession.getDepartment())
					&&nowProfession.getPosition().equals(smProfession.getPosition())
					&&nowProfession.getTelephone().equals(smProfession.getTelephone())){//类型和国家省市县详情邮编相同则删除
				smProfessionService.delete(smProfession);
				smProfessions.remove(smProfession);
			}
		}
		smProfessions.remove(nowProfession);
		this.deleteProdession(smProfessions,smProfessions.size());
	}

	//删除相同的地址
	public  void deleteAddress(List<SmAddress> smAddresss ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmAddress nowAddress = smAddresss.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmAddress smAddress =smAddresss.get(i);
			if(nowAddress.getType().equals(smAddress.getType())&&nowAddress.getCountry().equals(smAddress.getCountry())
					&&nowAddress.getProvince().equals(smAddress.getProvince())&&nowAddress.getCity().equals(smAddress.getCity())
					&&nowAddress.getDistrict().equals(smAddress.getDistrict())
					&&nowAddress.getDetail().equals(smAddress.getDetail())
					&&nowAddress.getZipcode().equals(smAddress.getZipcode())){//类型和国家省市县详情邮编相同则删除
				smAddressService.delete(smAddress);
				smAddresss.remove(smAddress);
			}
		}
		smAddresss.remove(nowAddress);
		this.deleteAddress(smAddresss,smAddresss.size());
	}

	//删除相同的联系方式
	public  void deleteContact(List<SmContact> smContacts ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmContact nowContact = smContacts.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmContact smContact =smContacts.get(i);
			if(smContact.getType().equals(nowContact.getType())&&smContact.getContact().equals(nowContact.getContact())){//类型和联系方式相同则删除
				smContactService.delete(smContact);
				smContacts.remove(smContact);
			}
		}
		smContacts.remove(nowContact);
		this.deleteContact(smContacts,smContacts.size());
	}
}
