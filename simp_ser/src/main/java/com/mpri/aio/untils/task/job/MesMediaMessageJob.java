package com.mpri.aio.untils.task.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.message.model.MesMediaMessage;
import com.mpri.aio.message.service.MesMediaMessageService;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 小程序媒体通知
 * @author Administrator
 *
 */
@Component(value = "MesMediaMessageJob")
public class MesMediaMessageJob implements Job{

	@Autowired
	private MesMediaMessageService mesMediaMessageService;
	
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//查询未过期的媒体消息
		MesMediaMessage mesMediaMessage = new MesMediaMessage();
		mesMediaMessage.setStatus(GlobalStr.NOT_OVERDUE);
		List<MesMediaMessage> list = mesMediaMessageService.loadAllListBy(mesMediaMessage);
		for (MesMediaMessage media : list) {
			String status = mesMediaMessageService.judgeDate(media);
			media.setStatus(status);
			mesMediaMessageService.save(media);
		}
	}
}
