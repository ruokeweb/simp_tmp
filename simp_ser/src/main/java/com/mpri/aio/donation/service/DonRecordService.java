package com.mpri.aio.donation.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.donation.mapper.DonProjectMapper;
import com.mpri.aio.donation.mapper.DonRecordMapper;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.model.DonRecordVo;
import com.mpri.aio.donation.vo.DonStatement;
import com.mpri.aio.schoolmate.mapper.SmContactMapper;
import com.mpri.aio.schoolmate.mapper.SmEducationMapper;
import com.mpri.aio.schoolmate.mapper.SmSchoolmateMapper;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.mapper.SysUserMapper;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.webber.vo.DonSchoolmateInfoVo;

/**
 *
 * @Description:  捐赠记录管理——Service
 * @Author:       LZQ
 * @project 	  AIO
 * @CreateDate:   Wed Aug 29 15:09:37 CST 2018
 * @Version:      v_1.0
 *
 */
@Service
public class DonRecordService extends CrudService<DonRecordMapper, DonRecord>  {

	@Autowired
	private DonProjectMapper donProjectMapper;

	@Autowired
	private DonRecordMapper donRecordMapper;

	@Autowired
	private RedisCacheService redisCacheService;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SmSchoolmateMapper smSchoolmateMapper;

	@Autowired
	private SmContactMapper smContactMapper;

	@Autowired
	private SmEducationMapper smEducationMapper;

	private Map<String, SettingDepartment> departBaseCache;

	private void InitMaps() {
		departBaseCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
	}


	public String getMoneySum(DonRecord donRecord)
	{
		String money = mapper.getMoneySum(donRecord);
		return money;
	}

	/**
	 * 获取捐赠成功的次数
	 * <p>Title: getCountById</p>
	 * <p>Description: </p>
	 * @param donRecord
	 * @return
	 */
	public Integer getCountByProId(DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON); //捐赠成功的
		return mapper.getCountByProId(donRecord);
	}

	/**
	 * 根据订单编号修改订单支付状态(更新捐赠项目的人数及金额)
	 * <p>Title: updateBycustomid</p>
	 * <p>Description: </p>
	 * @param donRecord
	 */
	@Transactional(readOnly = false)
	public void updateBycustomid(DonRecord donRecord) {
		//根据订单编号获取当前捐赠记录
		donRecord = mapper.getByCustomId(donRecord);
		//更新个人捐赠总额
		getSchoolmateByDon(donRecord);
		//更新项目的捐赠总金额及捐赠人数
		System.err.println(donRecord.getId());
		donProjectMapper.addMoneyAndPer(donRecord);
		//更新此订单的订单状态
		donRecord.setState(GlobalStr.NORMAL_DON);
		donRecord.setTime(new Date());
		mapper.updateBycustomid(donRecord);
	}

	/**
	 * 2019-02-11校友项目更新改版
	 * 根据捐赠信息获取用户校友信息
	 * <p>Title: getSchoolmateByDonRecord</p>
	 * <p>Description: </p>
	 * @param resDon
	 */
	private void getSchoolmateByDon(DonRecord resDon) {
//		SmSchoolmate schoolmate = new SmSchoolmate();
//		schoolmate.setDonFee(resDon.getMoney());
//		schoolmate.setSysUserId(resDon.getSysUserId());
//		schoolmateService.updateDonFee(schoolmate);
	}


	/**
	 * 根据订单编号修改订单支付状态(失败)
	 * <p>Title: updateBycustomid</p>
	 * <p>Description: </p>
	 */
	@Transactional(readOnly = false)
	public void updateBycustomidFail(String customid) {
		DonRecord donRecord = new DonRecord();
		donRecord.setCustomId(customid);
		donRecord.setState(GlobalStr.FAIL_DON);
		mapper.updateBycustomid(donRecord);
	}

	/**
	 * 根据订单Id获取此条记录
	 * <p>Title: getCountByProId</p>
	 * <p>Description: </p>
	 * @param donRecord
	 * @return
	 */
	public DonRecord getByCustomId(DonRecord donRecord){
		return  mapper.getByCustomId(donRecord);
	}

	/**
	 * 判断是否是当天首次捐赠
	 * <p>Title: isFirstDon</p>
	 * <p>Description: </p>
	 * @param donRecord
	 * @return
	 */
	public Boolean isFirstDon(DonRecord donRecord) {
		donRecord.setState(GlobalStr.NORMAL_DON);
		List<DonRecord> res = mapper.isFirstDon(donRecord);
		if(res.size() == 0) {
			return true;
		}
		return false;
	}

	public PageIo<DonRecord> loadByPageSchool(int pageNo, int pageSize, DonRecord donRecord){
		PageHelper.startPage(pageNo, pageSize);
		List<DonRecord> list = this.mapper.loadByPageSchool(donRecord);
		PageIo<DonRecord> pageInfo = new PageIo<DonRecord>(list);
		return pageInfo;
	}

	public List<DonRecord> ExportList() {
		return null;
	}

	public PageIo<DonRecord> getPageInfo(int pageNo, int pageSize, DonRecord donRecord) {
		PageHelper.startPage(pageNo, pageSize);
		Page<DonRecord> pageList = this.mapper.getPageInfo(donRecord);
		PageIo<DonRecord> pageInfo = new PageIo(pageList);
		return pageInfo;
	}

	/**
	 * 注册并保存捐赠记录
	 * @param httpServletRequest
	 * @return
	 */
	@Transactional(readOnly = false)
	public DonRecord saveSchoolmateAndRecord(HttpServletRequest httpServletRequest)  {
		this.InitMaps();
		DonRecord donRecord = new DonRecord();
		String isSchoolmate = httpServletRequest.getParameter("isSchoolmate");
		String isRegiste = httpServletRequest.getParameter("isRegiste");
		String userId = httpServletRequest.getParameter("userId");
		String donType = httpServletRequest.getParameter("donType");
		String donProjectId = httpServletRequest.getParameter("donProjectId");
		String name = httpServletRequest.getParameter("name");
		String sex =httpServletRequest.getParameter("sex");
		String phone = httpServletRequest.getParameter("phone");
		String email = httpServletRequest.getParameter("email");
		String money = httpServletRequest.getParameter("money");
		String style = httpServletRequest.getParameter("style");
		String isInvoice = httpServletRequest.getParameter("isInvoice");
		String invoiceTitle = httpServletRequest.getParameter("invoiceTitle");
		String address = httpServletRequest.getParameter("address");
		String startdate = httpServletRequest.getParameter("startdate");
		String enddate = httpServletRequest.getParameter("enddate");
		String college = httpServletRequest.getParameter("college");
		String series = httpServletRequest.getParameter("series");
		String specialty = httpServletRequest.getParameter("specialty");
		String classes = httpServletRequest.getParameter("classes");
		String studentNo = httpServletRequest.getParameter("studentNo");
		donRecord.setDonType(donType);
		donRecord.setDonProjectId(donProjectId);
		donRecord.setName(name);
		donRecord.setPhone(phone);
		donRecord.setEmail(email);
		donRecord.setMoney(new BigDecimal(money));
		donRecord.setStyle(style);
		donRecord.setIsInvoice(isInvoice);
		donRecord.setInvoiceTitle(invoiceTitle);
		donRecord.setAddress(address);
		if(GlobalStr.BOOLEAN_YES.equalsIgnoreCase(isSchoolmate)){//是校友
			if( "true".equalsIgnoreCase(isRegiste)){//已经注册
				donRecord.setUserId(userId);
			}else{//没有注册
				SmSchoolmate smSchoolmate = new SmSchoolmate();
				SysUser sysUser = new SysUser();
				// 设置用户
				sysUser.preInsert();
				sysUser.setPassword(initPwd(null));
				sysUser.setUserSource(GlobalStr.USER_SOURCE_NEW_CARD);
				sysUser.setUserType(GlobalStr.NORMAL);
				sysUser.setSafecode(IdGen.uuid());
				ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
				// 加盐炒三次safecode=salt
				String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
				sysUser.setPassword(result);
				sysUser.setVirtualName(name);
				if(StringUtil.isNotEmpty(phone)){
					sysUser.setUsername(phone);
					sysUser.setMobile(phone);
				}else{
					sysUser.setUsername(IdGen.uuid());
				}
				sysUser.setEmail(email);
				sysUserMapper.insert(sysUser);
				donRecord.setUserId(sysUser.getId());
				// 保存用户到校友总会下面
				sysUserMapper.insertDfAs(sysUser);
				smSchoolmate.setSex(sex);
				smSchoolmate.setUserId(sysUser.getId());
				smSchoolmate.preInsert();
				smSchoolmate.setName(name);
				smSchoolmate.setType(GlobalStr.DEFAULT_SCHOOLMATE_TYPE);
				smSchoolmate.setCardType(GlobalStr.ID_CARD);
				smSchoolmateMapper.insert(smSchoolmate);
				if(StringUtil.isNotEmpty(email)){
					SmContact smContact1 = new SmContact();
					smContact1.preInsert();
					smContact1.setType(GlobalStr.EMAIL);
					smContact1.setContact(email);
					smContact1.setUserId(sysUser.getId());
					smContactMapper.insert(smContact1);
				}
				if (StringUtil.isNotEmpty(phone)){
					SmContact smContact2 = new SmContact();
					smContact2.preInsert();
					smContact2.setType(GlobalStr.PHONE);
					smContact2.setContact(phone);
					smContact2.setUserId(sysUser.getId());
					smContactMapper.insert(smContact2);
				}
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SmEducation smEducation = new SmEducation();
				smEducation.preInsert();
				smEducation.setUserId(sysUser.getId());
				try {
					smEducation.setStartdate(format.parse(startdate));
					smEducation.setEnddate(format.parse(enddate));
				}catch (Exception e){

				}
				smEducation.setSchool(departBaseCache.get(college).getParentId());
				if(StringUtil.isNotEmpty(college)){
					smEducation.setCollege(college);
				}
				if(StringUtil.isNotEmpty(series)){
					smEducation.setSeries(series);
				}
				if(StringUtil.isNotEmpty(specialty)){
					smEducation.setSpecialty(specialty);
				}
				smEducation.setClasses(classes);
				smEducation.setStudentNo(studentNo);
				smEducationMapper.insert(smEducation);
			}
		}

		UUID uuid = UUID.randomUUID();
		String nonceStr = uuid.toString().replaceAll("-", "");
		donRecord.setCustomId(nonceStr);
		donRecord.setState(GlobalStr.WAITING_DON);
		donRecord.preInsert();
		donRecordMapper.insert(donRecord);
		return donRecord;
	}
	/**
	 * 初始化密码(冗余代码)
	 * <p>
	 * Title: initPwd
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param idCard
	 * @return
	 */
	private String initPwd(String idCard) {
		if (null != idCard && idCard.length() > 6) {
			return idCard.substring(idCard.length() - 6);
		} else {
			return GlobalStr.DEFAULT_PWD;
		}
	}

	/**
	 * 获取加盐的次数
	 */
	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		return Integer.parseInt(list.get(0).getValue());
	}

	/**
	 * 不带校友基本信息的查询
	 * @param pageNo
	 * @param pageSize
	 * @param donRecord
	 * @return
	 */
	public PageIo<DonRecord> getListNoSchoolmate(int pageNo, int pageSize, DonRecord donRecord) {
		PageHelper.startPage(pageNo, pageSize);
		Page<DonRecord> pageList = this.mapper.getListNoSchoolmate(donRecord);
		PageIo<DonRecord> pageInfo = new PageIo(pageList);
		return pageInfo;
	}
	/**
	 * 注册并保存捐赠记录
	 * @param donSchoolmateInfoVo
	 * @return
	 */
	@Transactional(readOnly = false)
	public DonRecord saveSchoolmateAndRecordNew(DonSchoolmateInfoVo donSchoolmateInfoVo) {
		this.InitMaps();
		DonRecord donRecord = new DonRecord();

		donRecord.setDonType(donSchoolmateInfoVo.getDonType());
		donRecord.setDonProjectId(donSchoolmateInfoVo.getDonProjectId());
		donRecord.setName(donSchoolmateInfoVo.getName());
		donRecord.setPhone(donSchoolmateInfoVo.getPhone());
		donRecord.setEmail(donSchoolmateInfoVo.getEmail());
		donRecord.setMoney(new BigDecimal(donSchoolmateInfoVo.getMoney()));
		donRecord.setStyle(donSchoolmateInfoVo.getStyle());
		donRecord.setIsInvoice(donSchoolmateInfoVo.getIsInvoice());
		donRecord.setInvoiceTitle(donSchoolmateInfoVo.getInvoiceTitle());
		donRecord.setAddress(donSchoolmateInfoVo.getAddress());
		if(GlobalStr.BOOLEAN_YES.equalsIgnoreCase(donSchoolmateInfoVo.getIsSchoolmate())){//是校友
			if( "true".equalsIgnoreCase(donSchoolmateInfoVo.getIsRegiste())){//已经注册
				donRecord.setUserId(donSchoolmateInfoVo.getUserId());
			}else{//没有注册
				SmSchoolmate smSchoolmate = new SmSchoolmate();
				SysUser sysUser = new SysUser();
				// 设置用户
				sysUser.preInsert();
				sysUser.setPassword(initPwd(null));
				sysUser.setUserSource(GlobalStr.USER_SOURCE_NEW_CARD);
				sysUser.setUserType(GlobalStr.NORMAL);
				sysUser.setSafecode(IdGen.uuid());
				ByteSource salt = ByteSource.Util.bytes(sysUser.getSafecode());
				// 加盐炒三次safecode=salt
				String result = new Md5Hash(sysUser.getPassword(), salt, getSaltTimes()).toString();
				sysUser.setPassword(result);
				sysUser.setVirtualName(donSchoolmateInfoVo.getName());
				if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getPhone())){
					sysUser.setUsername(donSchoolmateInfoVo.getPhone());
					sysUser.setMobile(donSchoolmateInfoVo.getPhone());
				}else{
					sysUser.setUsername(IdGen.uuid());
				}
				sysUser.setEmail(donSchoolmateInfoVo.getEmail());
				sysUserMapper.insert(sysUser);
				donRecord.setUserId(sysUser.getId());
				// 保存用户到校友总会下面
				sysUserMapper.insertDfAs(sysUser);
				smSchoolmate.setSex(donSchoolmateInfoVo.getSex());
				smSchoolmate.setUserId(sysUser.getId());
				smSchoolmate.preInsert();
				smSchoolmate.setName(donSchoolmateInfoVo.getName());
				smSchoolmate.setType(GlobalStr.DEFAULT_SCHOOLMATE_TYPE);
				smSchoolmate.setCardType(GlobalStr.ID_CARD);
				smSchoolmateMapper.insert(smSchoolmate);
				if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getEmail())){
					SmContact smContact1 = new SmContact();
					smContact1.preInsert();
					smContact1.setType(GlobalStr.EMAIL);
					smContact1.setContact(donSchoolmateInfoVo.getEmail());
					smContact1.setUserId(sysUser.getId());
					smContactMapper.insert(smContact1);
				}
				if (StringUtil.isNotEmpty(donSchoolmateInfoVo.getPhone())){
					SmContact smContact2 = new SmContact();
					smContact2.preInsert();
					smContact2.setType(GlobalStr.PHONE);
					smContact2.setContact(donSchoolmateInfoVo.getPhone());
					smContact2.setUserId(sysUser.getId());
					smContactMapper.insert(smContact2);
				}
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SmEducation smEducation = new SmEducation();
				smEducation.preInsert();
				smEducation.setUserId(sysUser.getId());
				try {
					smEducation.setStartdate(format.parse(donSchoolmateInfoVo.getStartdate()));
					smEducation.setEnddate(format.parse(donSchoolmateInfoVo.getEnddate()));
				}catch (Exception e){

				}
				smEducation.setSchool(departBaseCache.get(donSchoolmateInfoVo.getCollege()).getParentId());
				if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getCollege())){
					smEducation.setCollege(donSchoolmateInfoVo.getCollege());
				}
				if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getSeries())){
					smEducation.setSeries(donSchoolmateInfoVo.getSeries());
				}
				if(StringUtil.isNotEmpty(donSchoolmateInfoVo.getSpecialty())){
					smEducation.setSpecialty(donSchoolmateInfoVo.getSpecialty());
				}
				smEducation.setClasses(donSchoolmateInfoVo.getClasses());
				smEducation.setStudentNo(donSchoolmateInfoVo.getStudentNo());
				smEducationMapper.insert(smEducation);
			}
		}

		UUID uuid = UUID.randomUUID();
		String nonceStr = uuid.toString().replaceAll("-", "");
		donRecord.setCustomId(nonceStr);
		donRecord.setState(GlobalStr.WAITING_DON);
		donRecord.preInsert();
		donRecordMapper.insert(donRecord);
		return donRecord;
	}

	/**
	 * 获取每日捐赠
	 * @param donProject
	 * @return
	 */
	public List<DonRecordVo> loadAllListByDay(DonProject donProject) {
		return donRecordMapper.loadAllListByDay(donProject);
	}

	public BigDecimal loadAllmoneyByDay(DonProject donProject) {
		return donRecordMapper.loadAllmoneyByDay(donProject);
	}
	
	
	
	 /**
	  * 获取某日或者某个日期段
	  * @param donProject
	  * @return
	  */
	public List<DonRecordVo> loadListByDate(DonRecord donRecord){
		return mapper.loadListByDate(donRecord);
	}
	
	
	 /**
	  * 获取某段日期的
	  * @param donProject
	  * @return
	  */
	public BigDecimal loadCountByDate(DonRecord donRecord) {
		return mapper.loadCountByDate(donRecord);
	}
	
	public List<DonStatement> loadRecordByDate(DonRecord donRecord){
		return mapper.loadRecordByDate(donRecord);
	}
	
	public PageIo<DonRecord> loadRecordBy(int pageNo,int pageSize,DonRecord donRecord){
		PageHelper.startPage(pageNo, pageSize);
		Page<DonRecord> pageList = this.mapper.loadRecordBy(donRecord);
		PageIo<DonRecord> pageInfo = new PageIo(pageList);
		return pageInfo;
	}
	
	public int loadRecordCountBy(DonRecord donRecord){
		return mapper.loadRecordCountBy(donRecord);
	}
	
	
	
	@Transactional(readOnly = false)
	public void fileRecord(DonRecord donRecord) {
		//更新捐赠记录
		this.save(donRecord);
		donRecord = this.get(donRecord);
		//更新捐赠项目
		donProjectMapper.addMoneyAndPer(donRecord);
	}
	
	/**
	 * 给List增加order
	 */
	public List<DonRecordVo> addOrder(List<DonRecordVo> list){
		for (DonRecordVo donRecordVo : list) {
			donRecordVo.setOrder(list.indexOf(donRecordVo)+1);
		}
		return list;
	}

	public List<DonStatement> handleDonRecords(List<DonStatement> donRecords,BigDecimal couDecimal) {
		for (DonStatement donStatement : donRecords) {
			donStatement.setCount(couDecimal);
		}
		return donRecords;
	}
	
	/**
	 * 删除捐赠记录
	 * 同时更新捐赠项目
	 */
	@Transactional(readOnly = false)
	public void deleteRecord(DonRecord donRecord) {
		donRecord = this.get(donRecord);
		if(StringUtil.isEmpty(donRecord.getDonProjectId())) {
			mapper.delete(donRecord);
		}else {
			donProjectMapper.delMoneyAndPer(donRecord);
			mapper.delete(donRecord);
		}
	}

	
	/**
	 * 修改捐赠金额
	 * 同时更新捐赠项目
	 */
	@Transactional(readOnly = false)
	public void updateRecord(DonRecord donRecord) {
		DonRecord hisRecord = this.get(donRecord);
		if(StringUtil.isEmpty(donRecord.getDonProjectId())) {
			mapper.update(donRecord);
		}else {
			BigDecimal money = new BigDecimal(0);
			if(null == hisRecord.getMoney() && null == donRecord.getMoney()) {
				mapper.update(donRecord);
				return;
			}
			if(null == donRecord.getMoney() && null != hisRecord.getMoney()) {
				mapper.update(donRecord);
				money = money.subtract(hisRecord.getMoney());
				donRecord.setMoney(money);
				donProjectMapper.updateMoneyAndPer(donRecord);
				return;
			}
			if(null == hisRecord.getMoney() && null != donRecord.getMoney()) {
				mapper.update(donRecord);
				money = donRecord.getMoney().subtract(money);
				donRecord.setMoney(money);
				donProjectMapper.updateMoneyAndPer(donRecord);

				return;
			}
			if(null != hisRecord.getMoney() && null != donRecord.getMoney()) {
				mapper.update(donRecord);
				money = donRecord.getMoney().subtract(hisRecord.getMoney());
				donRecord.setMoney(money);
				donProjectMapper.updateMoneyAndPer(donRecord);
				return;
			}
		}
	}
}