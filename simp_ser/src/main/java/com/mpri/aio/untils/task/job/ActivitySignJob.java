package com.mpri.aio.untils.task.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mpri.aio.act.service.ActSelforgContentService;
import com.mpri.aio.system.common.GlobalStr;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.act.service.ActContentService;
import com.mpri.aio.app.utils.WechatGlobal.NoticeTemplate;
import com.mpri.aio.app.utils.model.SubMessage;
import com.mpri.aio.app.utils.model.SubMessage.ActNotice;
import com.mpri.aio.app.utils.service.SubMessageService;
import com.mpri.aio.system.utils.RunSettingParamsUtils;
import com.mpri.aio.system.utils.StringUtils;


@Component(value="ActivitySignJob")
public class ActivitySignJob implements Job {

    @Autowired
    private ActContentService actContentService;

	@Autowired
	private ActSelforgContentService actSelforgContentService;
    
    @Autowired
    private SubMessageService subMessageService;
    
    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
   
    /**
     * 消息发送的提前时间
     */
    private static int dateNum = 1;
    
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	
    	List<ActSignVo> asvList=actContentService.loadActSign(dateNum);
    	for(ActSignVo asv:asvList ) {
    		String actName=asv.getActName();
    		Date startDate=asv.getStartDate();
    		String area=asv.getArea();
    		String assName=asv.getAssName();
    		String openId=asv.getOpenId();
    		
    		SubMessage sm = new SubMessage();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateStr = sdf.format(startDate);  
	        
    		ActNotice an = sm.new ActNotice(StringUtils.cutMaxString(20, actName),
    				StringUtils.cutMaxString(10, assName),
    				dateStr,
    				StringUtils.cutMaxString(20, area),
    				"请注意，您报名的活动即将开始了");//封装业务参数
    		sm.setData(an);
    		sm.setTouser(openId);
    		sm.setTemplate_id(NoticeTemplate.HDKS);
    		subMessageService.notice(sm);
    	}

		List<ActSignVo> asvSelList=actSelforgContentService.loadActSign(dateNum);
		for(ActSignVo asv:asvSelList ) {
			String actName=asv.getActName();
			Date startDate=asv.getStartDate();
			String assName=asv.getAssName();
			String openId=asv.getOpenId();

			SubMessage sm = new SubMessage();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(startDate);
			ActNotice an = sm.new ActNotice(StringUtils.cutMaxString(20, actName),
					StringUtils.cutMaxString(10, assName),
					dateStr,
					runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.school_name),
					"请注意，您报名的值年返校即将开始了");//封装业务参数
			sm.setData(an);
			sm.setTouser(openId);
			sm.setTemplate_id(NoticeTemplate.HDKS);
			String notice = subMessageService.notice(sm);
			System.err.println(notice);
		}


    }

}
