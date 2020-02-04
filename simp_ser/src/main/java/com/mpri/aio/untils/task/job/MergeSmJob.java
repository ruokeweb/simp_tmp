package com.mpri.aio.untils.task.job;

import com.mpri.aio.schoolmate.service.SchoolmateMergeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 合并相同校友
 */
@Component(value="MergeSmJob")
public class MergeSmJob implements Job {
    @Autowired
    private SchoolmateMergeService schoolmateMergeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String oldUserId = "";
        String newUserId = "";
        schoolmateMergeService.mergeSchoolmateInfo(oldUserId,newUserId);
    }
}
