package com.mpri.aio.schoolmate.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时任务
 * 
 * @author syp
 *
 */
@Component
public class DelDuplicateQuartz {

//	/* 去重的数据 */	
//	@Value("#{'${spring.business.smSchoolmateTemp.delDuplicatesStr}'.split(',')}")
//    private List<String> compareStrs;
//	
//	@Autowired
//	private SmSchoolmateTempService smSchoolmateTempService;
//
//
//	/**
//	 * 每周星期天凌晨1点执行一次
//	 * 校友临时表数据去重
//	 */
//    @Scheduled(cron = "0 0 1 ? * SAT")
//	public void delDuplicateSmSchoolmateTemp() {   
//        SmSchoolmateTemp smSchoolmateTemp = new SmSchoolmateTemp();
//        smSchoolmateTemp.setCompareStrs(compareStrs);
//        smSchoolmateTempService.delDuplicate(smSchoolmateTemp);
//	}
}
