package com.mpri.aio.untils.task.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.service.ActActivityService;
import com.mpri.aio.system.common.GlobalStr;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 活动状态定时
 */
@Component(value="ActStateJob")
public class ActStateJob implements Job {
    @Autowired
    private ActActivityService actActivityService;
    /*预备中*/
    private static String READAY = "READAY";
    /*报名进行中*/
    private static String DOING = "DOING";
    /*报名已结束*/
    private static String DONE = "DONE";
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //首先获取除已经结束的活动列表
        ActActivity actActivity = new ActActivity();
        actActivity.setStatus(DONE);
        List<ActActivity> list = actActivityService.loadAllListByNotDone(actActivity);
        if(list!=null){
            for (ActActivity act:list) {
//                if(READAY.equals(act.getStatus())){
//                    if (act.getSignStartDate().getTime() < currtTime && act.getSignEndDate().getTime() > currtTime){
//                        act.setStatus(DOING);
//                        actActivityService.save(act);
//                    }else if(act.getSignEndDate().getTime() < currtTime){
//                        act.setStatus(DONE);
//                        actActivityService.save(act);
//                    }
//                }else if(DOING.equals(act.getStatus())){
//                    if(act.getSignEndDate().getTime() < currtTime){
//                        act.setStatus(DONE);
//                        actActivityService.save(act);
//                    }
//                }
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        		Date endDate = null;
        		Date startDate = null;
        		Date currtDate = null;
        		try {
        			currtDate = sdf.parse(sdf.format(new Date()));
        			if (null != act.getSignEndDate()) {
        				endDate = sdf.parse(sdf.format(act.getSignEndDate()));
        			}
        			if(null != act.getSignStartDate()) {
        				startDate = sdf.parse(sdf.format(act.getSignStartDate()));
        			}
	        		if (null == act.getSignEndDate()) {
	        			act.setStatus(DOING);
	        		} else if (null!=endDate && endDate.before(currtDate)) {
	        			act.setStatus(DONE);
	        		} else if (null !=act.getSignStartDate()  && currtDate.before(startDate)) {
	        			act.setStatus(READAY);
	        		} else if (null !=act.getSignStartDate() && null!=startDate && startDate.before(currtDate) && currtDate.before(endDate)) {
	        			act.setStatus(DOING);
	        		}else {
	        			act.setStatus(DOING);
	        		}
        		} catch (ParseException e) {
        			act.setStatus(DOING);
        		}
        		actActivityService.save(act);
            }
        }
    }
}
