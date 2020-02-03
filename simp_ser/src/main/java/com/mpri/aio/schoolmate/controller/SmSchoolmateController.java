package com.mpri.aio.schoolmate.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.mpri.aio.app.reg.utils.InfoUtils;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.common.utils.FileUtils;
import com.mpri.aio.common.utils.PinyinUtil;
import com.mpri.aio.common.utils.PinyinUtil.Type;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SchoolmateMergeService;
import com.mpri.aio.schoolmate.service.SmMarkService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.utils.SchoolmateExportHandler;
import com.mpri.aio.schoolmate.vo.EmailInfoVo;
import com.mpri.aio.schoolmate.vo.ExcelVerifyEntityOfMode;
import com.mpri.aio.schoolmate.vo.SmSchoolmateVo;
import com.mpri.aio.schoolmate.vo.TelephoneInfoVo;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.UserInfoUtil;
import com.mpri.aio.system.utils.RedisUtil;
import com.mpri.aio.untils.export.ExcelExportUtils;
import com.mpri.aio.untils.export.WordExportUtils;
import com.mpri.aio.untils.file.dao.FileRepository;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * @Description: 校友基本信息——Controller
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:29:11 CST 2019
 * @Version: v_1.0
 * 
 */
@RestController
@RequestMapping("/sm/smSchoolmate")
public class SmSchoolmateController extends BaseController {

	@Autowired
	private SmSchoolmateService smSchoolmateService;	
	
	@Autowired
	private SchoolmateExportHandler exportHandler;

	@Autowired
	private SchoolmateMergeService schoolmateMergeService;

	@Autowired
	private SmMarkService smMarkService;

	@Autowired
	public FileRepository fileRepository;

	@Autowired
	private RedisUtil redisUtil;


	@Value("${file.uploadFolder}")
	private String uploadFolder;
	
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;

	/**
	 * 获取校友基本信息列表
	 * <p>
	 * Title: list
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/list")
	public PageInfo<SmSchoolmate> list(@RequestBody SmSchoolmateVo smSchoolmateVo) {
		int pageNo = smSchoolmateVo.getPageNo();
		
		int pageSize = smSchoolmateVo.getPageSize();
		
		SmSchoolmate shSchoolmate = smSchoolmateVo.getSmSchoolmate();
		
   		PageIo<SmSchoolmate> pageInfo = smSchoolmateService.loadByPage(pageNo,
				pageSize, shSchoolmate);
		
		return pageInfo;
	}
/**
     *校友数据全字段模糊查询
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/listSelect")
    public PageInfo<SmSchoolmate> listSelect(@RequestBody SmSchoolmateVo smSchoolmateVo) {
//	 public PageInfo<SmSchoolmate> listSelect(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,@RequestParam("selectStr") String selectStr) {
		int pageNo = smSchoolmateVo.getPageNo();

		int pageSize = smSchoolmateVo.getPageSize();

		String selectStr = smSchoolmateVo.getSmSchoolmate().getName();
		String type =  smSchoolmateVo.getSmSchoolmate().getType();
        PageIo<SmSchoolmate> pageInfo = smSchoolmateService.loadByPageAll(pageNo, pageSize, selectStr,type);
        return pageInfo;
    }
	
	
	@CrossOrigin
	@PostMapping(value = "/listByName")
	public PageInfo<SmSchoolmate> listByName(@RequestBody SmSchoolmateVo smSchoolmateVo,HttpServletRequest request) {
		String userId = UserInfoUtil.getUserId(request);
		SmSchoolmate schoolmate = smSchoolmateVo.getSmSchoolmate();
		if(StringUtil.isNotEmpty(userId)) {
			schoolmate.setParamA(userId);
		}
		PageIo<SmSchoolmate> pageInfo = smSchoolmateService.listByName(smSchoolmateVo.getPageNo(),
				smSchoolmateVo.getPageSize(), schoolmate);
		return pageInfo;
	}
	
	@CrossOrigin
	@PostMapping(value = "/listOnlyByName")
	public PageInfo<SmSchoolmate> listOnlyByName(@RequestBody SmSchoolmateVo smSchoolmateVo) {
		PageIo<SmSchoolmate> pageInfo = smSchoolmateService.listOnlyByName(smSchoolmateVo.getPageNo(),
				smSchoolmateVo.getPageSize(), smSchoolmateVo.getSmSchoolmate());
		 List<SmSchoolmate> list=pageInfo.getList();
	        if(list!=null&&!list.isEmpty()){
	            for (SmSchoolmate sm:list) {
	            	System.out.println(smSchoolmateVo.getSmSchoolmate().getUserId()+"===");
	            	if(null!=smSchoolmateVo.getSmSchoolmate().getUserId()&&smSchoolmateVo.getSmSchoolmate().getUserId().equals(sm.getUserId())) {
	            		sm.setLAY_CHECKED(true);
	            	}
	            }

	        }
	    pageInfo.setList(list);
		return pageInfo;
	}
	/**
	 * 增加或者更新校友基本信息
	 * <p>
	 * Title: add
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smSchoolmate
	 * @return
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 */
	@CrossOrigin
	@PostMapping(value = "/save")
	public RestResponse<SmSchoolmate> saveSmAndUser(@RequestBody SmSchoolmate smSchoolmate) throws BadHanyuPinyinOutputFormatCombination {
		PinyinUtil pu = new PinyinUtil();
		smSchoolmate.setPinyin(pu.toPinYin(smSchoolmate.getName(), "",Type.LOWERCASE));
		if(null != smSchoolmate.getCardType() && GlobalStr.ID_CARD.equalsIgnoreCase(smSchoolmate.getCardType())) {
			if(StringUtils.isNotEmpty(smSchoolmate.getCardNum())) {
				smSchoolmate.setBirthday(InfoUtils.getBirthByIdCard(smSchoolmate.getCardNum()));
			}
		}
//		if(null == smSchoolmate.getBirthday() && smSchoolmate.getCardType().equals(GlobalStr.ID_CARD) && StringUtil.isNotEmpty(smSchoolmate.getCardNum())  ){
//			smSchoolmate.setBirthday(InfoUtils.getBirthByIdCard(smSchoolmate.getCardNum()));
//		}
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smSchoolmateService.saveSmAndUser(smSchoolmate));
	}

	/**
	 * 删除校友基本信息（逻辑删除）
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smSchoolmate
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/delete")
	public RestResponse<String> delete(SmSchoolmate smSchoolmate) {
		smSchoolmateService.deleteSmInfo(smSchoolmate);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "删除成功！", "");
	}

	/**
	 * 获取校友基本信息
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param smSchoolmate
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/get")
	public RestResponse<SmSchoolmate> get(SmSchoolmate smSchoolmate) {
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smSchoolmateService.get(smSchoolmate));
	}
	
	/**
	 *通过校友id获取校友基本信息(籍贯和用户名（即联系方式）)
	 */
	@CrossOrigin
	@PostMapping(value = "/getSmInfoById")
	public RestResponse<SmSchoolmate> getSmInfoById(SmSchoolmate smSchoolmate) {
		smSchoolmate = smSchoolmateService.getSmInfoById(smSchoolmate);
//		SmAddress smAddress = new SmAddress();
//		smAddress.setUserId(smSchoolmate.getUserId());
//		smAddress.setType(GlobalStr.IS_NATION_PLACE);
//		List<SmAddress> smAddresses= smAddressService.loadAllListBy(smAddress);
//		if(smAddresses.size() != 0) {
//			smSchoolmate.setSmAddress(smAddresses.get(0));
//		}
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！",
				smSchoolmate);
	}
	

	/**
	 * 获取校友常用信息
	 */
	@CrossOrigin
	@PostMapping(value = "/exportsmDatas",produces="application/json")
	public RestResponse<String> exportsmData(HttpServletResponse response,@RequestBody SmSchoolmateVo smSchoolmateVo){
		/**导出类型*/
		String informationType = smSchoolmateVo.getInformationType();
		/**处理查询类型 1、为多条件查询 2、模糊查询*/
		if("1".equals(smSchoolmateVo.getExportFlag())){
			smSchoolmateVo.setFuzzySearchFiled("");
		}else if("2".equals(smSchoolmateVo.getExportFlag())){
			String orgId = smSchoolmateVo.getSmSchoolmate().getOrgId();
			String type = smSchoolmateVo.getSmSchoolmate().getType();
			String fuzzySearchFiled = smSchoolmateVo.getFuzzySearchFiled();
			smSchoolmateVo = new SmSchoolmateVo();
			smSchoolmateVo.setSmSchoolmate(new SmSchoolmate());
			smSchoolmateVo.getSmSchoolmate().setOrgId(orgId);
			smSchoolmateVo.getSmSchoolmate().setType(type);
			smSchoolmateVo.setFuzzySearchFiled(fuzzySearchFiled);
		}
		/**查询数据*/
//		List<SmSchoolmate> smList = smSchoolmateService.ExportListByCondition(smSchoolmateVo.getSmSchoolmate(),smSchoolmateVo.getFuzzySearchFiled());
		/**分类导出*/
		String fileName = DateUtils.getDate("yyyy-MM-dd HH:mm:ss").replace(":", "");
		try {
			if(informationType.equalsIgnoreCase("telephoneType")){
				SmSchoolmate schoolmate = smSchoolmateVo.getSmSchoolmate();
				SmContact contact = schoolmate.getSmContact();
				if(null!=contact) {
					contact.setType(GlobalStr.CONTACT_TYPE_PHONE);
				}else {
					contact = new SmContact();
					contact.setType(GlobalStr.CONTACT_TYPE_PHONE);
				}
				schoolmate.setSmContact(contact);
				List<SmContact> smList = smSchoolmateService.ExportContactListByCondition(schoolmate,smSchoolmateVo.getFuzzySearchFiled());				
				List<TelephoneInfoVo> phoneList = getTelephoneInfo(smList);
				List<List<TelephoneInfoVo>> partitionList = Lists.partition(phoneList, 2000);
				ExcelExportUtils.exportExcel(partitionList,"校友手机信息","sheet1",TelephoneInfoVo.class,uploadFolder + "校友手机信息"+ fileName+".xlsx");
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +"校友手机信息"+ fileName+".xlsx");

			}else if (informationType.equalsIgnoreCase("emailType")){
				SmSchoolmate schoolmate = smSchoolmateVo.getSmSchoolmate();
				SmContact contact = schoolmate.getSmContact();
				if(null!=contact) {
					contact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
				}else {
					contact = new SmContact();
					contact.setType(GlobalStr.CONTACT_TYPE_EMAIL);
				}
				schoolmate.setSmContact(contact);
				List<SmContact> smList = smSchoolmateService.ExportContactListByCondition(schoolmate,smSchoolmateVo.getFuzzySearchFiled());	
				List<EmailInfoVo> emailList = getEmailInfo(smList);
				List<List<EmailInfoVo>> partitionList = Lists.partition(emailList, 2000);
				ExcelExportUtils.exportExcel(partitionList,"校友邮箱信息","sheet1",EmailInfoVo.class,uploadFolder + "校友邮箱信息"+ fileName+".xlsx");
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +"校友邮箱信息"+ fileName+".xlsx");

			}else{
				List<SmSchoolmate> smList = smSchoolmateService.ExportListByCondition(smSchoolmateVo.getSmSchoolmate(),smSchoolmateVo.getFuzzySearchFiled());
				List<SmSchoolmateVo> exportlist = exportHandler.handlerSchoolmateExport(smList);
				List<List<SmSchoolmateVo>> partitionList = Lists.partition(exportlist, 500);
				ExcelExportUtils.exportExcel(partitionList,"校友常用信息","sheet1",SmSchoolmateVo.class,uploadFolder + "校友常用信息"+ fileName+".xlsx");
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +"校友常用信息"+ fileName+".xlsx");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		}
	}

	/**
	 * 获取校友电话 信息
	 * @param exportlist
	 * @return 
	 */
	private List<TelephoneInfoVo> getTelephoneInfo(List<SmContact> exportlist) {
		List<TelephoneInfoVo>  phoneList = new ArrayList<>();
		int order = 0;
		for (SmContact obj: exportlist) {
			TelephoneInfoVo telephoneInfoVo = new TelephoneInfoVo();
			if(obj.getContact() != null && !("").equals(obj.getContact().trim())){
				telephoneInfoVo.setName(obj.getName());
				telephoneInfoVo.setContact(obj.getContact());
				order+=1;
				telephoneInfoVo.setOrder(order);
				phoneList.add(telephoneInfoVo);
			}
		}
		return phoneList;
	}

	/**
	 * 获取校友邮箱信息
	 * @param exportlist
	 * @return
	 */
	private List<EmailInfoVo> getEmailInfo(List<SmContact> exportlist) {
		List<EmailInfoVo> emailinfoList = new ArrayList<>();
		int order = 0;
		for (SmContact obj: exportlist) {
			EmailInfoVo emailInfoVo = new EmailInfoVo();
			if(obj.getContact() != null && !("").equals(obj.getContact().trim())){
				emailInfoVo.setName(obj.getName());
				emailInfoVo.setContact(obj.getContact());
				order+=1;
				emailInfoVo.setOrder(order);
				emailinfoList.add(emailInfoVo);
			}
		}
		return emailinfoList;
	}

	/**
	 * 导出word
	 * @param smSchoolmate
	 */
	@CrossOrigin
	@PostMapping(value = "/exportsWordDatas")
	public RestResponse<String> exportsWordDatas(HttpServletRequest request,SmSchoolmate smSchoolmate){

		/** 校友信息 字典转义*/
		SmSchoolmate schoolmate = smSchoolmateService.getUserINfoById(smSchoolmate);
		if(null == schoolmate){
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		}
		schoolmate= exportHandler.handlerSchoolMateInfo(schoolmate);
		Optional<com.mpri.aio.untils.file.model.File> optfile = fileRepository.findById(schoolmate.getTruePhoto()==null ? "":schoolmate.getTruePhoto());

		String myPic = "";
		try {
			/** 文件名称 */
			String fileName = new String((schoolmate.getName()==null ? "" :schoolmate.getName() +"简历").getBytes(),"utf-8");
			/** 组装数据 */
			Map<String, Object> dataMap = new HashMap<String, Object>();
			/**基本数据*/
			dataMap.put("name",(schoolmate.getName()==null || "".equals(schoolmate.getName())) ? "" :schoolmate.getName());
			dataMap.put("sex",(schoolmate.getSex()==null || "".equals(schoolmate.getSex()) )? "" :schoolmate.getSex());
			dataMap.put("nation", (schoolmate.getNation()==null || "".equals(schoolmate.getType())) ? "" :schoolmate.getNation());
			if(optfile.isPresent()){
				myPic = WordExportUtils.getImageStringByByte(optfile.get().getContent());
			}else{//默认图片
				ClassPathResource resource = new ClassPathResource("static/default.png");
				try {
					InputStream inputStream = resource.getInputStream();
					byte[] data = null;
					if(inputStream.available() > 0) {
						data = new byte[inputStream.available()];
						int res =inputStream.read(data);
						inputStream.close();
						myPic = data != null ? new String(Base64.encodeBase64(data)) : "";
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月");
			if(schoolmate.getBirthday() != null &&  !"".equals(schoolmate.getBirthday())){
                dataMap.put("birthday", sdf.format(schoolmate.getBirthday()));
            }else{
                dataMap.put("birthday", "");
            }
			dataMap.put("pic", myPic);
			/**联系方式*/
			List<Map<String, Object>> smContactsList=new ArrayList<Map<String,Object>>();
			List<SmContact> smContacts = schoolmate.getSmContacts();
			for (SmContact smContact : smContacts) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("contacttype", (smContact.getType()==null || "".equals(smContact.getType())) ? "-" :smContact.getType());
				map.put("contact", (smContact.getContact()==null || "".equals(smContact.getContact()))? "-" :smContact.getContact());
				smContactsList.add(map);
			}
			dataMap.put("smContactsLists", smContactsList);
			List<Map<String, Object>> smAddressesList=new ArrayList<Map<String,Object>>();
			List<SmAddress> smAddresses = schoolmate.getSmAddresses();
			for (SmAddress smAddress : smAddresses) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("addressType", "通讯地址");
				map.put("address", (smAddress.getDetail()==null || "".equals(smAddress.getDetail())) ? "-" :smAddress.getDetail());
				smAddressesList.add(map);
			}
			dataMap.put("smAddressesLists", smAddressesList);
			/**教育背景*/
			List<Map<String, Object>> smEducationList=new ArrayList<Map<String,Object>>();
			List<SmEducation> smEducations = schoolmate.getSmEducations();
			for (SmEducation smEducation : smEducations) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("startdate", (smEducation.getStartdate()==null || "".equals(smEducation.getStartdate())) ? "-" : sdf.format(smEducation.getStartdate()));
				map.put("enddate", (smEducation.getEnddate() ==null|| "".equals(smEducation.getEnddate())) ? "-" : sdf.format(smEducation.getEnddate()));
				/*if(StringUtils.isNotBlank(smEducation.getTempSeries()) || StringUtils.isNotBlank(smEducation.getTempSpecialty())){
					map.put("specialty", (smEducation.getTempSeries()==null || "".equals(smEducation.getTempSeries()))  ? "-" :smEducation.getTempSeries()  +" "+smEducation.getTempSpecialty()==null ? "-" :smEducation.getTempSpecialty());
				}else{
					map.put("specialty", (smEducation.getSpecialty()==null || "".equals(smEducation.getSpecialty())) ? "-" :smEducation.getSpecialty());
				}*/

				if(StringUtils.isNotBlank(smEducation.getSpecialty())){
					map.put("specialty",smEducation.getSpecialty());
				}else if(StringUtils.isNotBlank(smEducation.getTempSpecialty())){
					map.put("specialty",smEducation.getTempSpecialty());
				}else {
					map.put("specialty","-");
				}
				map.put("eduRecord", (smEducation.getEduRecord()==null || "".equals(smEducation.getEduRecord())) ? "-" :smEducation.getEduRecord());
				smEducationList.add(map);
			}
			dataMap.put("smEducationLists", smEducationList);
			/**职业信息*/
			List<Map<String, Object>> smProfessionList=new ArrayList<Map<String,Object>>();
			List<SmProfession> smProfessions = schoolmate.getSmProfessions();
			for (SmProfession smProfession : smProfessions) {
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("workplace", "单位："+ ((smProfession.getWorkplace()==null|| "".equals(smProfession.getWorkplace())) ? "-": smProfession.getWorkplace()) +" "+ "职务："+ ( (smProfession.getPosition()==null || "".equals(smProfession.getPosition())) ? "-": smProfession.getPosition()));
				String dataStart = "";
				String dataEnd = "";
                if(smProfession.getStartDate() != null && !"".equals(smProfession.getStartDate())){
                    dataStart = sdf.format(smProfession.getStartDate());
                }
                if(smProfession.getEndDate() != null && !"".equals(smProfession.getEndDate())){
                    dataEnd = sdf.format(smProfession.getStartDate());
                }
                map.put("workdate", dataStart+"-"+dataEnd);
				smProfessionList.add(map);
			}
			dataMap.put("smProfessionLists", smProfessionList);

			/** 生成word */
			WordExportUtils.createWord(dataMap, "wordExport.ftl", uploadFolder, fileName);
			return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "导出成功！", staticAccessPath.replaceAll("\\*", "") +fileName+".doc");

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new RestResponse<String>(ExceptionResult.SYS_ERROR, "导出失败！", "");
		}
	}
	
	@SuppressWarnings("unchecked") 
	@CrossOrigin
	@PostMapping(value = "/importsmdatas")
	public RestResponse<String> importsmtempdata(MultipartFile file,HttpServletRequest request,String type) {
        String fileName = file.getOriginalFilename();
        String newFilName = String.valueOf(new Date().getTime()) + "." + fileName.substring(fileName.lastIndexOf(".") + 1); /* 更改文件名 */
        String resfillPath = DateUtils.getDate();
        String filePath = request.getSession().getServletContext().getRealPath(resfillPath + "/");
        String erroFilePath = uploadFolder + "导入失败的校友信息_"+ resfillPath+".xls";
//		String type = request.getParameter("type");
        try {
			FileUtils.uploadFile(file.getBytes(), filePath, newFilName);
			//支持校验
			ExcelImportResult<ExcelVerifyEntityOfMode> results = smSchoolmateService.getListByImportType(type, new File(filePath+newFilName));
			//数据转换
			List<SmSchoolmate> list = smSchoolmateService.covertByImportType(type,results.getList());
			smSchoolmateService.importSmDatas(list);
			if(results.isVerfiyFail()) {
				int successSize = results.getList().size();
				int erroSize = results.getFailList().size();
				smSchoolmateService.exportErroDatas(type, erroFilePath, results.getFailList());
				return new RestResponse<String>(ExceptionResult.SYS_ERROR, 
						"成功导入"+successSize+"条，失败"+erroSize+"条！", staticAccessPath.replaceAll("\\*", "") +filePath+newFilName);			
			}else {
				return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "成功导入"+results.getList().size()+"条！", "");		
			}
		} catch (Exception e) {
			return new RestResponse<String>(ExceptionResult.DATA_USED, "导入失败！", "请重新整理校友信息进行导入！");
		}
	} 
	
	
    /**
     * 更新校友卡状态
    * <p>Title: updateCardStatus</p>  
    * <p>Description: </p>
     */
    @CrossOrigin
    @PostMapping(value = "/updateCardStatus")
    public  RestResponse<String> updateCardStatus(SmSchoolmate smSchoolmate) {
    	smSchoolmateService.save(smSchoolmate);
    	return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "修改成功！", "");
    }
    
    
    /**
     * 通过校友id 获取当前校友的所有标签
    * <p>Title: getMarksBy</p>  
    * <p>Description: </p>  
    * @param smSchoolmate
    * @return
     */
	@CrossOrigin
	@PostMapping(value = "/getMarksBy")
	public RestResponse<SmSchoolmate> getMarksBy(SmSchoolmate smSchoolmate) {
		List<SmMark> ss= smMarkService.getMarkByUser(smSchoolmate);
		smSchoolmate.setMarkList(ss);
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", smSchoolmate);
	}
	
	/**
	 * 更新校友标签
	* @param smSchoolmate
	* @return RestResponse<SmSchoolmate>
	 */
	@CrossOrigin
	@PostMapping(value = "/saveSchoolmateMark")
	public RestResponse<SmSchoolmate> saveSchoolmateMark(@RequestBody SmSchoolmate smSchoolmate){
		smSchoolmateService.saveUserMark(smSchoolmate);
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", smSchoolmate);
	}
	
	/**
	 * 根据用户id获取校友的所有信息(带可能相似的人)
	 * @param smSchoolmate
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/getLikeById")
	public RestResponse<SmSchoolmateVo> getLikeById(SmSchoolmate smSchoolmate){
		SmSchoolmateVo schoolmateVo = new SmSchoolmateVo();
		smSchoolmate = smSchoolmateService.getUserINfoById(smSchoolmate);
		SmSchoolmate search = new SmSchoolmate();
		search.setUserId(smSchoolmate.getUserId());
		search.setName(smSchoolmate.getName());
		SmEducation edu = new SmEducation();
		edu.setIsDefault(GlobalStr.IS_DEFAULT);
		search.setSmEducation(edu);
		List<SmSchoolmate> likeSmschoolmate = smSchoolmateService.getListLike(search);
		for (SmSchoolmate schoolmate : likeSmschoolmate) {
			schoolmate = exportHandler.handlerSmInfo(schoolmate);
		}
		
		smSchoolmate = exportHandler.handlerSmInfo(smSchoolmate);
		schoolmateVo.setSmSchoolmate(smSchoolmate);
		schoolmateVo.setLikeSmschoolmate(likeSmschoolmate);
		
		return new RestResponse<SmSchoolmateVo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				schoolmateVo);
	}
	
	
	/**
	 * 根据用户id获取校友的所有信息
	 * @param smSchoolmate
	 * @return
	 */
	@CrossOrigin
	@PostMapping(value = "/getUserINfoById")
	public RestResponse<SmSchoolmate> getUserINfoById(SmSchoolmate smSchoolmate){
		smSchoolmate = smSchoolmateService.getUserINfoById(smSchoolmate);		
		return new RestResponse<SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", 
				exportHandler.handlerSmInfo(smSchoolmate));

	}
	
	/**    
	 * 校友新信息与老信息合并
	 */
	@CrossOrigin
	@PostMapping(value = "/mergeSchoolmateInfo")
	public RestResponse<String> mergeSchoolmateInfo(@RequestParam("oldUserId")String oldUserId,
			@RequestParam("newUserId")String newUserId,@RequestParam("processInstanceId") String processInstanceId){
		schoolmateMergeService.mergeSchoolmateInfo(oldUserId, newUserId,processInstanceId);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "合并成功！", "");
	}
	
	
	/**
	 * 校友合并功能
	 */
	@CrossOrigin
	@PostMapping(value = "/mergeSchoolmate")	
	public RestResponse<String> mergeSchoolmate(@RequestParam("oldUserId")String oldUserId,
			@RequestParam("newUserId")String newUserId){
		schoolmateMergeService.mergeSchoolmateInfo(oldUserId, newUserId);
		return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "合并成功！", oldUserId);
	}
}