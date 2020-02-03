package com.mpri.aio.info.service;

import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.info.model.InfoMessage;
import com.mpri.aio.info.model.InfoMessageLike;
import com.mpri.aio.info.mapper.InfoMessageMapper;

 /**   
 *  
 * @Description:  信息留言表——Service
 * @Author:       syp
 * @project 	  smmp 
 * @CreateDate:   Fri Dec 14 09:44:55 CST 2018
 * @Version:      v_1.2
 *    
 */
@Service
public class InfoMessageService extends CrudService<InfoMessageMapper, InfoMessage>  {

	/**
	 * 更新点赞数
	* <p>Title: updateLikeNum</p>  
	* <p>Description: </p>  
	* @param infoMessageLike
	 */
	public void updateLikeNum (InfoMessageLike infoMessageLike) {
		mapper.updateLikeNum(infoMessageLike);
	}
}