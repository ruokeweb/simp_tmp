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

public class SmSchoolmateMergeTest extends ApplicationTests {
	
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
//		SmSchoolmate sm = new SmSchoolmate();
//		List<SmSchoolmate> sms = schoolmateService.ExportList(sm);
//		System.out.println(sms.size());
//		MesTemplate mesTemplate = new MesTemplate();
//		mesTemplate.setType(GlobalStr.MES_TEMPLATE_BIRTH);
//		List<MesTemplate> mesTemplates =  mesTemplateService.loadAllListBy(mesTemplate);
//
//		if(mesTemplates.size() == 0)
//			return;
//
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
		/**
		 * 进行交大老数据的政治面貌同步到校友表的字段中
		 */
		SmPolitics smPolitics = new SmPolitics();
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		List<SmPolitics> smPoliticsList = smPoliticsService.loadAllListBy(smPolitics);

		for (SmPolitics politics:smPoliticsList) {
			smSchoolmate.setUserId(politics.getUserId());
			smSchoolmate.setPolitics(politics.getName());
			schoolmateService.deleteOldSm(smSchoolmate);
		}
	}


	/**
	 * 处理校友基本信息
	 */
	@Test
	public void mergeSm(){
		//获取所有校友
		for (int i=0 ;i<156700 ;i++){
				SmSchoolmate smSchoolmates = schoolmateService.getUserEduInfo(new SmSchoolmate());
				if(smSchoolmates!=null){
					List<SmSchoolmate> listCompareByUserId = schoolmateService.getListCompareByUserId(smSchoolmates);
					schoolmateMergeService.mergeSchoomates(listCompareByUserId,smSchoolmates);
				}

		}



		//将第一条取出 判断是否有相同校友
		/*List<SmSchoolmate> listCompar	e =null;
		SmSchoolmate nowSchoolmate =null;
		int num=0;
		this.deleteSchoolmate(smSchoolmates,smSchoolmates.size(),listCompare,nowSchoolmate,num);*/

		//合并校友
		//删除map集合的已经合并的校友
		this.megreContact();
		this.megreAddress();
		this.megreEducation();
		this.megreProfession();
		this.megreSocial();
		this.megreHistroyData();
	}

	/**
	 * 处理联系方式
	 */
	@Test
	public void megreContact(){
		System.err.println("处理联系方式");
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
		System.err.println("处理地址");
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
		System.err.println("处理联系教育经历");
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
		System.err.println("处理联系职业经理");
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
		System.err.println("处理社会经历");
		List<SmSocial> userIds = smSocialService.getUserId();
		for (SmSocial smSocial:userIds) {
			List<SmSocial> smSocials = smSocialService.loadAllListBy(smSocial);
			this.deleteSocial(smSocials,smSocials.size());
		}
	}

	/**
	 * 历史数据 ---合并主信息之后，再处理
	 */
	@Test
	public void megreHistroyData(){
		System.err.println("处理历史数据");
		List<SmHistorydata> userIds = smHistorydataService.getUserId();
		for (SmHistorydata smHistorydata:userIds) {
			List<SmHistorydata> smHistorydatas = smHistorydataService.loadAllListBy(smHistorydata);
			this.deleteHistroyData(smHistorydatas,smHistorydatas.size());
		}
	}

	//删除相同的历史数据
	public  void deleteHistroyData(List<SmHistorydata> smHistorydatas ,int nowNum  ){
		if(nowNum<=1){
			return;
		}
		SmHistorydata nowHistorydata = smHistorydatas.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmHistorydata smHistorydata =smHistorydatas.get(i);
			if((nowHistorydata.getOtherSchool()==null?"":nowHistorydata.getOtherSchool()).equals((smHistorydata.getOtherSchool()==null?"":smHistorydata.getOtherSchool()))
					&&(nowHistorydata.getOtherSpecialty()==null?"":nowHistorydata.getOtherSpecialty()).equals((smHistorydata.getOtherSpecialty()==null?"":smHistorydata.getOtherSpecialty()))
					&&(nowHistorydata.getOtherDegree()==null?"":nowHistorydata.getOtherDegree()).equals((smHistorydata.getOtherDegree()==null?"":smHistorydata.getOtherDegree()))
					&&(nowHistorydata.getTrustUnit()==null?"":nowHistorydata.getTrustUnit()).equals((smHistorydata.getTrustUnit()==null?"":smHistorydata.getTrustUnit()))
					&&(nowHistorydata.getBeforeSchoolunit()==null?"":nowHistorydata.getBeforeSchoolunit()).equals((smHistorydata.getBeforeSchoolunit()==null?"":smHistorydata.getBeforeSchoolunit()))
			){
				smHistorydataService.delete(smHistorydata);
				smHistorydatas.remove(smHistorydata);
			}else{
				nowHistorydata.setFlag("NORMAL1");
				smHistorydataService.save(nowHistorydata);
			}
		}
		nowHistorydata.setFlag("NORMAL1");
		smHistorydataService.save(nowHistorydata);
		smHistorydatas.remove(nowHistorydata);
		this.deleteHistroyData(smHistorydatas,smHistorydatas.size());
	}

	//删除social
	public  void deleteSocial(List<SmSocial> smSocials ,int nowNum  ){
		if(nowNum<=1){
			return;
		}
		SmSocial nowSocial = smSocials.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmSocial smSocial =smSocials.get(i);
			if((nowSocial.getName()==null?"":nowSocial.getName()).equals((smSocial.getName()==null?"":smSocial.getName()))
					&&(nowSocial.getPosition()==null?"":nowSocial.getPosition()).equals((smSocial.getPosition()==null?"":smSocial.getPosition()))
			){
				smSocialService.delete(smSocial);
				smSocials.remove(smSocial);
			}else{
				smSocial.setFlag("NORMAL1");
				smSocialService.save(smSocial);
			}
		}
		nowSocial.setFlag("NORMAL1");
		smSocialService.save(nowSocial);
		smSocials.remove(nowSocial);
		this.deleteSocial(smSocials,smSocials.size());
	}
	//删除相同的校友schoolmate
	public  void deleteSchoolmate(List<SmSchoolmate> smSchoolmates ,int nowNum ,List<SmSchoolmate> listCompare,SmSchoolmate nowSchoolmate,int num ){
		try {
			//System.gc();
			num++;
			System.out.println(smSchoolmates.size());
			if(nowNum<=1){
				return;
			}
			nowSchoolmate =smSchoolmates.get(nowNum-1);


			listCompare = schoolmateService.getListCompareByUserId(nowSchoolmate);
			schoolmateMergeService.mergeSchoomates(listCompare,nowSchoolmate);
		/*for (int j = 0 ;j<listCompare.size() ;j++){
			for(int i=nowNum-2;i>=0;i--)
			{
				try {
					//查出和第一条相同的校友
					if(listCompare.get(j).getId().equals(smSchoolmates.get(i).getId())){
						smSchoolmates.remove(smSchoolmates.get(i));

					}
				}catch (Exception e){
					System.out.println("======"+e+"======");
				}

			}
		}*/
			//smSchoolmates.remove(nowSchoolmate);
			Iterator<SmSchoolmate> it = smSchoolmates.iterator();
			while(it.hasNext()){
				SmSchoolmate sm = it.next();
				if(sm.getId().equals(nowSchoolmate.getId())){
					it.remove();
				}
				for (int j = 0 ;j<listCompare.size() ;j++){
					if(sm.getId().equals(listCompare.get(j).getId())){
						it.remove();
					}
				}

			}
			listCompare =null;
			nowSchoolmate =null;
			if(num==5000){
				num=0;
				System.gc();
			}

			this.deleteSchoolmate(smSchoolmates,smSchoolmates.size(),listCompare,nowSchoolmate,num);
		}catch (Exception e){
			System.err.println(e);
		}

	}

	//删除相同的教育经历
	public  void deleteEducation(List<SmEducation> smEducations ,int nowNum ){
		if(nowNum<=1){
			return;
		}
		SmEducation nowEducation = smEducations.get(nowNum-1);
		for(int i=nowNum-2;i>=0;i--)
		{
			SmEducation smEducation =smEducations.get(i);
			if((nowEducation.getCollege()==null?"":nowEducation.getCollege()).equals((smEducation.getCollege()==null?"":smEducation.getCollege()))
					&&(nowEducation.getSeries()==null?"":nowEducation.getSeries()).equals((smEducation.getSeries()==null?"":smEducation.getSeries()))
					&&(nowEducation.getSpecialty()==null?"":nowEducation.getSpecialty()).equals((smEducation.getSpecialty()==null?"":smEducation.getSpecialty()))
					&&(nowEducation.getEduRecord()==null?"":nowEducation.getEduRecord()).equals((smEducation.getEduRecord()==null?"":smEducation.getEduRecord()))
					&&(nowEducation.getStartdate()==null?"":nowEducation.getStartdate()).equals((smEducation.getStartdate()==null?"":smEducation.getStartdate()))
					&&(nowEducation.getStudentNo()==null?"":nowEducation.getStudentNo()).equals((smEducation.getStudentNo()==null?"":smEducation.getStudentNo()))
					&&(nowEducation.getTempSeries()==null?"":nowEducation.getTempSeries()).equals((smEducation.getTempSeries()==null?"":smEducation.getTempSeries()))
					&&(nowEducation.getTempSpecialty()==null?"":nowEducation.getTempSpecialty()).equals((smEducation.getTempSpecialty()==null?"":smEducation.getTempSpecialty()))//院+系+专业+学历+入学年+学号+临时系+临时专业
					){
				if(StringUtil.isEmpty(nowEducation.getEduModel())&&StringUtil.isNotEmpty(smEducation.getEduModel())){
					nowEducation.setEduModel(smEducation.getEduModel());
				}
				if(StringUtil.isEmpty(nowEducation.getClasses())&&StringUtil.isNotEmpty(smEducation.getClasses())){
					nowEducation.setClasses(smEducation.getClasses());
				}
				if(StringUtil.isEmpty(nowEducation.getEduType())&&StringUtil.isNotEmpty(smEducation.getEduType())){
					nowEducation.setEduType(smEducation.getEduType());
				}
				if(StringUtil.isEmpty(nowEducation.getSchoollen())&&StringUtil.isNotEmpty(smEducation.getSchoollen())){
					nowEducation.setSchoollen(smEducation.getSchoollen());
				}
				if(nowEducation.getStartdate()==null&&smEducation.getStartdate()!=null){
					nowEducation.setStartdate(smEducation.getStartdate());
				}
				if(nowEducation.getEnddate()==null&&smEducation.getEnddate()!=null){
					nowEducation.setEnddate(smEducation.getEnddate());
				}
					smEducationService.delete(smEducation);
					smEducations.remove(smEducation);
			}else {
				smEducation.setFlag("NORMAL1");
				smEducationService.save(smEducation);
			}
		}
		nowEducation.setFlag("NORMAL1");
		smEducationService.save(nowEducation);
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
			if( (nowProfession.getWorkplace()==null?"":nowProfession.getWorkplace()).equals((smProfession.getWorkplace()==null?"":smProfession.getWorkplace()))
					&&(nowProfession.getDepartment()==null?"":nowProfession.getDepartment()).equals((smProfession.getDepartment()==null?"":smProfession.getDepartment()))
					&&(nowProfession.getPosition()==null?"":nowProfession.getPosition()).equals((smProfession.getPosition()==null?"":smProfession.getPosition()))
					){//单位+部门+职位
				if(StringUtil.isEmpty(nowProfession.getCountry())&&StringUtil.isNotEmpty(smProfession.getCountry())){//国家
					nowProfession.setCountry(smProfession.getCountry());
				}
				if(StringUtil.isEmpty(nowProfession.getProvince())&&StringUtil.isNotEmpty(smProfession.getProvince())){//省
					nowProfession.setProvince(smProfession.getProvince());
				}
				if(StringUtil.isEmpty(nowProfession.getCity())&&StringUtil.isNotEmpty(smProfession.getCity())){//市
					nowProfession.setCity(smProfession.getCity());
				}
				if(StringUtil.isEmpty(nowProfession.getDetail())&&StringUtil.isNotEmpty(smProfession.getDetail())){//详情
					nowProfession.setDetail(smProfession.getDetail());
				}
				if(StringUtil.isEmpty(nowProfession.getTelephone())&&StringUtil.isNotEmpty(smProfession.getTelephone())){//电话
					nowProfession.setTelephone(smProfession.getTelephone());
				}
				if(StringUtil.isEmpty(nowProfession.getFax())&&StringUtil.isNotEmpty(smProfession.getFax())){//传真
					nowProfession.setFax(smProfession.getFax());
				}
				if(StringUtil.isEmpty(nowProfession.getRemark())&&StringUtil.isNotEmpty(smProfession.getRemark())){//备注
					nowProfession.setRemark(smProfession.getRemark());
				}
				smProfessionService.delete(smProfession);
				smProfessions.remove(smProfession);
			}else {
				smProfession.setFlag("NORMAL1");
				smProfessionService.save(smProfession);
			}
		}
		nowProfession.setFlag("NORMAL1");
		smProfessionService.save(nowProfession);
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
			if((nowAddress.getType()==null?"":nowAddress.getType()).equals((smAddress.getType()==null?"":smAddress.getType()))
					&&(nowAddress.getCountry()==null?"":nowAddress.getCountry()).equals((smAddress.getCountry()==null?"":smAddress.getCountry()))
					&&(nowAddress.getProvince()==null?"":nowAddress.getProvince()).equals((smAddress.getProvince()==null?"":smAddress.getProvince()))
					&&(nowAddress.getCity()==null?"":nowAddress.getCity()).equals((smAddress.getCity()==null?"":smAddress.getCity()))
					&&(nowAddress.getDistrict()==null?"":nowAddress.getDistrict()).equals((smAddress.getDistrict()==null?"":smAddress.getDistrict()))
					&&(nowAddress.getDetail()==null?"":nowAddress.getDetail()).equals((smAddress.getDetail()==null?"":smAddress.getDetail()))
			){//类型和国家省市县详情相同则删除
				if(StringUtil.isEmpty(nowAddress.getZipcode())&&StringUtil.isNotEmpty(smAddress.getZipcode())){
					nowAddress.setZipcode(smAddress.getZipcode());
				}
				smAddressService.delete(smAddress);
				smAddresss.remove(smAddress);
			}else {
				smAddress.setFlag("NORMAL1");
				smAddressService.save(smAddress);
			}
		}
		nowAddress.setFlag("NORMAL1");
		smAddressService.save(nowAddress);
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
			if((smContact.getType()==null?"":smContact.getType()).equals((nowContact.getType()==null?"":nowContact.getType()))
					&&(smContact.getContact()==null?"":smContact.getContact()).equals((nowContact.getContact()==null?"":nowContact.getContact()))){//类型和联系方式相同则删除
				smContactService.delete(smContact);
				smContacts.remove(smContact);
			}else {
				smContact.setFlag("NORMAL1");
				smContactService.save(smContact);
			}
		}
		nowContact.setFlag("NORMAL1");
		smContactService.save(nowContact);
		smContacts.remove(nowContact);
		this.deleteContact(smContacts,smContacts.size());
	}


}
