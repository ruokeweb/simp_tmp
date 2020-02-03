package com.mpri.aio.schoolmate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.schoolmate.mapper.SmMarkMapper;
import com.mpri.aio.schoolmate.mapper.SmSchoolmateMapper;
import com.mpri.aio.schoolmate.model.SmMark;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

 /**   
 *  
 * @Description:  校友标签管理——Service
 * @Author:       LZQ
 * @project 	  AIO 
 * @CreateDate:   Fri Aug 24 11:05:42 CST 2018
 * @Version:      v_1.0
 *    
 */
@Service
public class SmMarkService extends CrudService<SmMarkMapper, SmMark>  {
	

	@Autowired
	SmSchoolmateService  schoolmateService;
	
	/**
	 * 获取当前用户的标签
	* <p>Title: getMarkByUser</p>  
	* <p>Description: </p>  
	* @param schoolmate
	* @return
	 */
	public List<SmMark> getMarkByUser(SmSchoolmate schoolmate){
		return mapper.loadMarkByUser(schoolmate);
	}
	
	/**
	 * 是否被使用
	 */
	public Boolean isUseAble(String id) {
		return mapper.isUseAble(id);
	}

	/**
	 * 是否有标签被使用
	 */
	public Boolean isUse(List<SmMark> smMarks) {
		for (SmMark smMark : smMarks) {
			if(mapper.isUseAble(smMark.getId()))
				return true;
		}
		return false;
	}
	
	/**
	 * 删除标签(同时清除中间表数据，和校友表数据)带删除子节点。级联删除
	 */
	@Transactional(readOnly = false)
	public Boolean deleteMark(SmMark smMark){
		//删除校友管理中的相关标签信息
//		List<SmSchoolmate> smSchoolmates = excuteMarkInfo(smMark);
//		for (SmSchoolmate smSchoolmate : smSchoolmates) {
//			schoolmateService.save(smSchoolmate);
//		}
//		mapper.deleteSmMarks(smMark);
		SmMark condi = new SmMark();
		condi.setId(smMark.getId());
		List<SmMark> smMarks = mapper.loadByParent(condi);
		smMarks.add(smMark);
		if(isUse(smMarks)) {
			return true;
		}else {
			for (SmMark mark : smMarks) {
				this.delete(mark);	
			}
		}
		return false;
	}
	
	
	/**
	 * 修改校友中的标签
	 */
	@Transactional(readOnly = false)
	public void updateMark(SmMark smMark){
		StringBuffer buffer = new StringBuffer();
		//修改校友管理中的相关标签信息
		List<SmSchoolmate> smSchoolmates = schoolmateService.loadSmsByMark(smMark);
		for (SmSchoolmate smSchoolmate : smSchoolmates) {
			String [] marks = smSchoolmate.getMarks().split(",");		
			String [] markIds = smSchoolmate.getMarkIds().split(",");
			for (int i = 0; i < markIds.length; i++) {
				if(markIds[i].equalsIgnoreCase(smMark.getId())) {
					marks[i] = smMark.getName();
				}
			}
			for (int i = 0; i < marks.length; i++) {
				if(i == 0) {
					buffer.append(marks[i]);
				}else {
					buffer.append(","+marks[i]);
				}
			}
			smSchoolmate.setMarks(buffer.toString());
			schoolmateService.save(smSchoolmate);
		}		
	}	

	
	/**
	 * 处理校友表标签相关信息
	* <p>Title: excuteMarkInfo</p>  
	* <p>Description: </p>  
	* @param smMark
	* @return
	 */
	private List<SmSchoolmate> excuteMarkInfo(SmMark smMark){
		List<SmSchoolmate> smSchoolmates = schoolmateService.loadSmsByMark(smMark);
		for (SmSchoolmate smSchoolmate : smSchoolmates) {
			String [] marks = smSchoolmate.getMarks().split(",");
			smSchoolmate.setMarks(deleteMarkInfo(marks,smMark.getName()).toString());
			String [] markIds = smSchoolmate.getMarkIds().split(",");
			smSchoolmate.setMarkIds(deleteMarkInfo(markIds,smMark.getId()).toString());
		}		
		return smSchoolmates;
	}
	
	private StringBuffer deleteMarkInfo(String [] arr,String mark) {
		StringBuffer buffer = new StringBuffer();
		List<String> list = Arrays.asList(arr);
		ArrayList<String> arrList = new ArrayList<String>(list);
		if(arrList.contains(mark)) {
			arrList.remove(mark);
		}
		for (int i =0 ;i<arrList.size();i++) {
			if(i == 0) {
				buffer.append(arrList.get(i));
			}else {
				buffer.append(","+arrList.get(i));
			}
		}
		return buffer;
	}
	
	/**
	 * 更改标签状态
	* <p>Title: updateUnable</p>  
	* <p>Description: </p>  
	* @param smMark
	 */
	@Transactional(readOnly = false)
	public void updateUnable(SmMark smMark) {
		mapper.updateUnable(smMark);
	}
}