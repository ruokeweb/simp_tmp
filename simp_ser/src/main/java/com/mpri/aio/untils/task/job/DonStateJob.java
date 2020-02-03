package com.mpri.aio.untils.task.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.service.DonProjectService;
import com.mpri.aio.system.common.GlobalStr;

/**
 * 捐赠状态定时
 */
@Component(value="DonStateJob")
public class DonStateJob implements Job {
    @Autowired
    private DonProjectService donProjectService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //首先获取除已经结束的捐赠项目列表
        DonProject donProject = new DonProject();
        donProject.setStatus(GlobalStr.DON_HASDONE);
        List<DonProject> list = donProjectService.loadAllListByStatus(donProject);
        if(list!=null){
            for (DonProject don:list) {
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        		Date endDate = null;
	        		Date startDate = null;
	        		Date currtDate = null;
	        		try {
	        			currtDate = sdf.parse(sdf.format(new Date()));
	        			if (null != don.getEnddate()) {
	        				endDate = sdf.parse(sdf.format(don.getEnddate()));
	        			}
	        			if(null != don.getStartdate()) {
	        				startDate = sdf.parse(sdf.format(don.getStartdate()));
	        			}
		        		if (null == don.getEnddate()) {
		        			don.setStatus(GlobalStr.DON_BEDOING);
		        		} else if (null!=endDate && endDate.before(currtDate)) {
		        			don.setStatus(GlobalStr.DON_HASDONE);
		        		} else if (null !=don.getStartdate()  && currtDate.before(startDate)) {
		        			don.setStatus(GlobalStr.DON_WILL);
		        		} else if (null !=don.getStartdate() && null!=startDate && startDate.before(currtDate) && currtDate.before(endDate)) {
		        			don.setStatus(GlobalStr.DON_BEDOING);
		        		}else {
		        			don.setStatus(GlobalStr.DON_BEDOING);
		        		}
	        		} catch (ParseException e) {
	        			don.setStatus(GlobalStr.DON_BEDOING);
	        		}
	        		donProjectService.save(don);
            }
        }
    }
}
