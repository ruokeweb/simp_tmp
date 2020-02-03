package com.mpri.aio.schoolmate.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.schoolmate.model.SmProfession;

/**
 * 
 * @author syp
 *
 */
@Mapper
public interface MergeSchoolmateInfoMapper {

	List<SmEducation> getEduRepeatUserIdByStuNo();
	
	List<SmEducation> getEduRepeatUserIdByStartDate();
	
	List<SmEducation> getEduRepeatUserIdByEndDate();
	
	List<SmEducation> getEduRepeatUserIdBySclasses();
	
	List<SmEducation> getEduRepeatUserIdByEclasses();
	
	List<SmContact> getContactRepeatUserIdBy();
	
	List<SmAddress> getAddrRepeatUserIdBy();
	
	List<SmProfession> getProRepeatUserIdBy();
	
	List<SmEducation> loadEduListBy(SmEducation education);
	
	List<SmContact> loadContListBy(SmContact smContact);
	
	List<SmAddress> loadAddrListBy(SmAddress smAddress);
	
	List<SmProfession> loadProListBy(SmProfession smProfession);
}
