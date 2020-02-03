package com.mpri.aio.info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.info.model.InfoInformation;
import com.mpri.aio.info.model.InfoMessage;
import com.mpri.aio.info.mapper.InfoInformationMapper;

 /**   
 *  
 * @Description:  信息详情表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Wed Dec 12 09:24:19 CST 2018
 * @Version:      v_1.2
 *    
 */
@Service
public class InfoInformationService extends CrudService<InfoInformationMapper, InfoInformation>  {
	
	@Autowired
	private InfoMessageService infoMessageService;

	/**
	 * 更新阅读量
	* <p>Title: updateReadNum</p>  
	* <p>Description: </p>  
	* @param id
	 */
	@Transactional(readOnly = false)
	public void updateReadNum(String id) {
		mapper.updateReadNum(id);
	}
	
	/**
	 * 更新评论量
	* <p>Title: updatemessageNum</p>  
	* <p>Description: </p>  
	* @param id
	 */
	@Transactional(readOnly = false)
	public void updatemessageNum(String id) {
		mapper.updatemessageNum(id);
	}
	
	/**
	 * 插入评论内容并更新评论量
	 */
	@Transactional(readOnly = false)
	public void saveInfoMsg(InfoMessage infoMessage) {
		infoMessageService.save(infoMessage);
		this.updatemessageNum(infoMessage.getInfoId());
	}
}