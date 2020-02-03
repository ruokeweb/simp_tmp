package com.mpri.aio.schoolmate.service;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmContactMapper;
import com.mpri.aio.schoolmate.model.SmContact;

import java.util.List;

/**
 * 
 * @Description: 校友联系方式——Service
 * @Author: syp
 * @project exchange_datasource
 * @CreateDate: Mon Jan 28 15:30:05 CST 2019
 * @Version: v_1.0
 * 
 */

@Service
public class SmContactService extends CrudService<SmContactMapper, SmContact> {
	public List<SmContact> getUserId() {
		return this.mapper.getUserId();
	}
	
	public void deleteByUserId(SmContact contact) {
		mapper.deleteByUserId(contact);
	}
	
    /*
     * 获取联系方式重复的人
     */
    public List<SmContact> getMergeUserId(){
    	return mapper.getMergeUserId();
    }
}