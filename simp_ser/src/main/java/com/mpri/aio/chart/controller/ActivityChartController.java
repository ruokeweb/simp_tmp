package com.mpri.aio.chart.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.chart.service.ActivityChartService;
import com.mpri.aio.chart.vo.TableVo;
import com.mpri.aio.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Desc 新版统计功能 --活动统计
 * @author zdl
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/chart/activity")
public class ActivityChartController {
    @Autowired
    private ActivityChartService activityChartService;

    /**
     * 活动报名统计
     * @param actActivity
     * @return
     */
    @PostMapping(value = "actSignChart")
    public List<Map<String,Object>> actSignChart(ActActivity actActivity){
        if(actActivity.getChartEndDate() != null) {
            return activityChartService.actSignChart("maintab.create_date","month",Integer.valueOf(actActivity.getParamA()),
                    DateUtils.formatDate(actActivity.getChartEndDate(),"yyyy-MM-dd"),actActivity);
        }

        return activityChartService.actSignChart("maintab.create_date","month",Integer.valueOf(actActivity.getParamA()),
                DateUtils.getDate(),actActivity);
       // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 值年返校统计
     */
    @PostMapping(value = "actBackChart")
    public List<Map<String,Object>> actBackChart (ActActivity actActivity){
        if(actActivity.getChartEndDate() != null) {
            return activityChartService.actBackChart("maintab.create_date","month",Integer.valueOf(actActivity.getParamA()),
                    DateUtils.formatDate(actActivity.getChartEndDate(),"yyyy-MM-dd"),actActivity);
        }

        return activityChartService.actBackChart("maintab.create_date","month",Integer.valueOf(actActivity.getParamA()),
                DateUtils.getDate(),actActivity);
    }

    /**
     * 活动人数排行
     * @param pageNo
     * @param pageSize
     * @param tableVo
     * @return
     */
    @PostMapping(value = "getSignActNum")
    public PageInfo<Map<String,Object>> getSignActNum(int pageNo, int pageSize, TableVo tableVo){
        return activityChartService.getSignActNum( pageNo,pageSize, tableVo);
    }
    /**
     * 值年返校人数排行
     * @param pageNo
     * @param pageSize
     * @param tableVo
     * @return
     */
    @PostMapping(value = "getSignActBackNum")
    public PageInfo<Map<String,Object>> getSignActBackNum(int pageNo, int pageSize, TableVo tableVo){
        return activityChartService.getSignActBackNum( pageNo,pageSize, tableVo);
    }
}
