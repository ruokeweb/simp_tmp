package com.mpri.aio.schoolmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.schoolmate.mapper.MergeSchoolmateInfoMapper;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;
import com.mpri.aio.system.common.GlobalStr;

import tk.mybatis.mapper.util.StringUtil;

/**
 * 校友信息合并
 * @author Administrator
 *
 */
@Service
public class MergeSchoolmateInfoService {
	
	@Autowired
	private MergeSchoolmateInfoMapper mergeMapper;
	
	@Autowired
	private SmAddressService smAddressService;
	@Autowired
	private SmContactService smContactService;
	@Autowired
	private SmEducationService smEducationService;
	@Autowired
	private SmProfessionService smProfessionService;
	
	/**
	 * 合并教育经历
	 */
	public void mergeEdus() {
		List<SmEducation> list = mergeMapper.getEduRepeatUserIdByStartDate();
		for (SmEducation smEducation : list) {
			List<SmEducation> mergeList = mergeMapper.loadEduListBy(smEducation);
			if(mergeList.size() >1) {
				this.mergeEduInfo(mergeList.get(1),mergeList.get(0));
			}
		}
	}
	
	/**
	 * 合并联系方式
	 */
	public void mergeConts() {
		List<SmContact> list = mergeMapper.getContactRepeatUserIdBy();
		for (SmContact smContact : list) {
			List<SmContact> mergeList = mergeMapper.loadContListBy(smContact);
			if(mergeList.size() >1) {
				this.mergeContInfo(mergeList.get(1),mergeList.get(0));
			}
		}
	}
	
	/**
	 * 合并地址
	 */
	public void mergeAddrs() {
		List<SmAddress> list = mergeMapper.getAddrRepeatUserIdBy();
		for (SmAddress smAddress : list) {
			List<SmAddress> mergeList = mergeMapper.loadAddrListBy(smAddress);
			if(mergeList.size() >1) {
				this.mergeAddrInfo(mergeList.get(1),mergeList.get(0));
			}
		}
	}
	
	/**
	 * 合并职业经历
	 */
	public void mergePros() {
		List<SmProfession> list = mergeMapper.getProRepeatUserIdBy();
		for (SmProfession smProfession : list) {
			List<SmProfession> mergeList = mergeMapper.loadProListBy(smProfession);
			if(mergeList.size() >1) {
				this.mergeProInfo(mergeList.get(1),mergeList.get(0));
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	private void mergeEduInfo(SmEducation mainEdu,SmEducation branchEdu) {
		if(StringUtil.isNotEmpty(branchEdu.getDegreeType())) {
			mainEdu.setDegreeType(branchEdu.getDegreeType());
		}
		if(StringUtil.isNotEmpty(branchEdu.getTempSpecialty())) {
			mainEdu.setTempSpecialty(branchEdu.getTempSpecialty());
		}
		if(StringUtil.isNotEmpty(branchEdu.getAcademy())) {
			mainEdu.setAcademy(branchEdu.getAcademy());
		}
		if(StringUtil.isNotEmpty(branchEdu.getEduModel())) {
			mainEdu.setEduModel(branchEdu.getEduModel());
		}
		if(StringUtil.isNotEmpty(branchEdu.getSchool())) {
			mainEdu.setSchool(branchEdu.getSchool());
		}
		if(StringUtil.isNotEmpty(branchEdu.getType())) {
			mainEdu.setType(branchEdu.getType());
		}
		if(StringUtil.isNotEmpty(branchEdu.getEduRecord())) {
			mainEdu.setEduRecord(branchEdu.getEduRecord());
		}
		if(StringUtil.isNotEmpty(branchEdu.getSchoollen())) {
			mainEdu.setSchoollen(branchEdu.getSchoollen());
		}
		if(StringUtil.isNotEmpty(branchEdu.getEduType())) {
			mainEdu.setEduType(branchEdu.getEduType());
		}
		if(StringUtil.isNotEmpty(branchEdu.getSeries())) {
			mainEdu.setSeries(branchEdu.getSeries());
		}
		if(StringUtil.isNotEmpty(branchEdu.getClasses())) {
			mainEdu.setClasses(branchEdu.getClasses());
		}
		if(null != branchEdu.getEnddate()) {
			mainEdu.setEnddate(branchEdu.getEnddate());
		}	
		if(StringUtil.isNotEmpty(branchEdu.getSpecialty())) {
			mainEdu.setSpecialty(branchEdu.getSpecialty());
		}	
		if(StringUtil.isNotEmpty(branchEdu.getCollege())) {
			mainEdu.setCollege(branchEdu.getCollege());
		}	
		if(null != branchEdu.getStartdate()) {
			mainEdu.setStartdate(branchEdu.getStartdate());
		}	
		if(StringUtil.isNotEmpty(branchEdu.getStudentNo())) {
			mainEdu.setStudentNo(branchEdu.getStudentNo());
		}	
		if(StringUtil.isNotEmpty(branchEdu.getDegree())) {
			mainEdu.setDegree(branchEdu.getDegree());
		}	
		if(StringUtil.isNotEmpty(branchEdu.getTempSeries())) {
			mainEdu.setTempSeries(branchEdu.getTempSeries());
		}
		if(StringUtil.isNotEmpty(branchEdu.getIsDefault())){
			if(GlobalStr.IS_DEFAULT.equals(branchEdu.getIsDefault())) {
				mainEdu.setIsDefault(GlobalStr.IS_DEFAULT);
			}
		}
		if(StringUtil.isNotEmpty(branchEdu.getRemark())) {
			mainEdu.setRemark(branchEdu.getRemark());
		}
		smEducationService.save(mainEdu);
		smEducationService.delete(branchEdu);
	}
	
	@Transactional(readOnly = false)
	private void mergeContInfo(SmContact mainCont,SmContact branchCont) {
		if(StringUtil.isNotEmpty(branchCont.getType())) {
			mainCont.setType(branchCont.getType());
		}
		if(StringUtil.isNotEmpty(branchCont.getContact())) {
			mainCont.setContact(mainCont.getContact());
		}		
		if(StringUtil.isNotEmpty(branchCont.getIsDefault())) {
			if(GlobalStr.IS_DEFAULT.equals(branchCont.getIsDefault())) {
				mainCont.setIsDefault(GlobalStr.IS_DEFAULT);
			}
		}
		if(StringUtil.isNotEmpty(branchCont.getRemark())) {
			mainCont.setRemark(branchCont.getRemark());
		}
		smContactService.save(mainCont);
		smContactService.delete(branchCont);
	}
	
	@Transactional(readOnly = false)
	private void mergeAddrInfo(SmAddress mainAddr,SmAddress branchAddr) {
		if(StringUtil.isNotEmpty(branchAddr.getType())) {
			mainAddr.setType(branchAddr.getType());
		}
		if(StringUtil.isNotEmpty(branchAddr.getCountry())) {
			mainAddr.setCountry(branchAddr.getCountry());
		}
		if(StringUtil.isNotEmpty(branchAddr.getProvince())) {
			mainAddr.setProvince(branchAddr.getProvince());
		}
		if(StringUtil.isNotEmpty(branchAddr.getCity())) {
			mainAddr.setCity(branchAddr.getCity());
		}
		if(StringUtil.isNotEmpty(branchAddr.getDistrict())) {
			mainAddr.setDistrict(branchAddr.getDistrict());
		}
		if(StringUtil.isNotEmpty(branchAddr.getDetail())) {
			mainAddr.setDetail(branchAddr.getDetail());
		}
		if(StringUtil.isNotEmpty(branchAddr.getRemark())) {
			mainAddr.setRemark(branchAddr.getRemark());
		}
		if(StringUtil.isNotEmpty(branchAddr.getZipcode())) {
			mainAddr.setZipcode(branchAddr.getZipcode());
		}
		if(StringUtil.isNotEmpty(branchAddr.getIsDefault())) {
			if(GlobalStr.IS_DEFAULT.equals(branchAddr.getIsDefault())) {
				mainAddr.setIsDefault(GlobalStr.IS_DEFAULT);
			}
		}
		smAddressService.save(mainAddr);
		smAddressService.delete(branchAddr);
	}
	
	@Transactional(readOnly = false)
	private void mergeProInfo(SmProfession mainPro,SmProfession branchPro) {
		if(StringUtil.isNotEmpty(branchPro.getType())) {
			if(GlobalStr.IS_DEFAULT.equals(branchPro.getType())) {
				mainPro.setType(GlobalStr.IS_DEFAULT);
			}
		}
		if(StringUtil.isNotEmpty(branchPro.getDistrict())) {
			mainPro.setDistrict(branchPro.getDistrict());
		}
		if(StringUtil.isNotEmpty(branchPro.getProvince())) {
			mainPro.setProvince(branchPro.getProvince());
		}
		if(StringUtil.isNotEmpty(branchPro.getCity())) {
			mainPro.setCity(branchPro.getCity());
		}
		if(StringUtil.isNotEmpty(branchPro.getCountry())) {
			mainPro.setCountry(branchPro.getCountry());
		}
		if(StringUtil.isNotEmpty(branchPro.getFax())) {
			mainPro.setFax(branchPro.getFax());
		}
		if(StringUtil.isNotEmpty(branchPro.getDetail())) {
			mainPro.setDetail(branchPro.getDetail());
		}
		if(StringUtil.isNotEmpty(branchPro.getWorkplace())) {
			mainPro.setWorkplace(branchPro.getWorkplace());
		}
		if(StringUtil.isNotEmpty(branchPro.getNature())) {
			mainPro.setNature(branchPro.getNature());
		}
		if(StringUtil.isNotEmpty(branchPro.getIndustry())) {
			mainPro.setIndustry(branchPro.getIndustry());
		}
		if(StringUtil.isNotEmpty(branchPro.getDepartment())) {
			mainPro.setDepartment(branchPro.getDepartment());
		}
		if(StringUtil.isNotEmpty(branchPro.getPosition())) {
			mainPro.setPosition(branchPro.getPosition());
		}
		if(StringUtil.isNotEmpty(branchPro.getTelephone())) {
			mainPro.setTelephone(branchPro.getTelephone());
		}
		if(StringUtil.isNotEmpty(branchPro.getStatus())) {
			mainPro.setStatus(branchPro.getStatus());
		}
		if(StringUtil.isNotEmpty(branchPro.getRemark())) {
			mainPro.setRemark(branchPro.getRemark());
		}
		if(null != branchPro.getStartDate()) {
			mainPro.setStartDate(branchPro.getStartDate());
		}
		if(null != branchPro.getEndDate()) {
			mainPro.setEndDate(branchPro.getEndDate());
		}
		smProfessionService.save(mainPro);
		smProfessionService.delete(branchPro);
	}
}
