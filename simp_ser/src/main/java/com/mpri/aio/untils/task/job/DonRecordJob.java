package com.mpri.aio.untils.task.job;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.service.DonRecordService;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 
 * @author syp
 *
 */
@Component(value = "DonRecordJob")
public class DonRecordJob implements Job {

	@Autowired
	private DonRecordService donRecordService;
	
	/**
	 * 过期时间
	 */
	private static long EXPIRE_TIME = 24 * 60 * 60 *1000L;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//查询所有等待支付的订单
		DonRecord donRecord = new DonRecord();
		donRecord.setState(GlobalStr.WAITING_DON);
		List<DonRecord> list = donRecordService.loadAllListBy(donRecord);
		//根据创建时间与当前时间进行对比
		for (DonRecord record : list) {
			if(new Date().getTime() - record.getCreateDate().getTime() >= EXPIRE_TIME) {
				//更新状态
				record.setState(GlobalStr.FAIL_DON);
				record.setTime(new Date());
				donRecordService.save(record);
			}
		}
			
	}

}
