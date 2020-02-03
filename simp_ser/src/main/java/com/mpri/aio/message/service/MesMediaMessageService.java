package com.mpri.aio.message.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.message.mapper.MesMediaMessageMapper;
import com.mpri.aio.message.model.MesMediaMessage;
import com.mpri.aio.system.common.GlobalStr;

 /**   
 *  
 * @Description:  每日多媒体通知——Service
 * @Author:       syp
 * @project 	  simp 
 * @CreateDate:   Tue Nov 05 14:18:55 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Service
public class MesMediaMessageService extends CrudService<MesMediaMessageMapper, MesMediaMessage>  {


	
	
    /**
     * 判断日期
    * <p>Title: judgeDate</p>  
    * <p>Description: </p>
     */
    public String judgeDate(MesMediaMessage media) {
    	Long currtTime = new Date().getTime();
    	if(null == media.getDelDate()) {
    		return GlobalStr.NOT_OVERDUE;
    	}else if(media.getDelDate().getTime() < currtTime) {
    		return GlobalStr.IS_OVERDUE;
    	}else if(currtTime <= media.getDelDate().getTime()) {
    		return GlobalStr.NOT_OVERDUE;
    	}
    	return GlobalStr.NOT_OVERDUE;
    }
    
    
    /**
     * 获取首页要展示的通知
     */
    public MesMediaMessage getIndexMesMediaMessage(MesMediaMessage media) {
    	media.setStatus(GlobalStr.NOT_OVERDUE);
    	return mapper.getIndexMesMediaMessage(media);
    }
}