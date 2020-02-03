package com.mpri.aio.message.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mpri.aio.message.model.MesMessage;
import com.mpri.aio.message.service.MesMessageService;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 消息过期定时器
* <p>Title: UpdateMesMessageQuartz</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年11月15日
 */
@Component
public class UpdateMesMessageQuartz {
	
	@Autowired
	private MesMessageService mesMessageService;
	
	/**
	 * 每天凌晨1点实行一次
	 * 消息过期定时器
	 */
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 */1 * * * ?")  /*测试 一分钟*/
	public void updateDonProStatus() {
    	MesMessage mesMessage = new MesMessage();
    	mesMessage.setStatus(GlobalStr.NOT_OVERDUE);
    	List<MesMessage> mesMessages = mesMessageService.loadAllListBy(mesMessage);
    	mesMessages.forEach((MesMessage m) -> {
    		m.setStatus(mesMessageService.judgeDate(m));
    		mesMessageService.save(m);
		});
	}
}
