package com.mpri.aio.untils.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.schoolmate.service.MergeSchoolmateInfoService;

/**
 * 排名数据收集
 */
@Component(value="MergeSmInfoJob")
public class MergeSmInfoJob implements Job {
    @Autowired
    private MergeSchoolmateInfoService mergeSchoolmateInfoService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	mergeSchoolmateInfoService.mergeConts();
    	mergeSchoolmateInfoService.mergeEdus();
    	mergeSchoolmateInfoService.mergePros();
    	mergeSchoolmateInfoService.mergeAddrs();
    }
}