package com.mpri.aio.untils.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mpri.aio.ranking.util.ChartRankingService;

/**
 * 排名数据收集
 */
@Component(value="RankingJob")
public class RankingJob implements Job {
    @Autowired
    private ChartRankingService chartRankingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    	chartRankingService.excuteChaDonTime();
    	chartRankingService.excuteChaProve();
    	chartRankingService.excuteChaShare();
    	chartRankingService.excuteDonMoney();
    }
}
