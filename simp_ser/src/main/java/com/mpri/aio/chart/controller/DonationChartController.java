package com.mpri.aio.chart.controller;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.chart.service.DonationChartService;
import com.mpri.aio.chart.vo.ProjectTypeVo;
import com.mpri.aio.chart.vo.TableVo;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Desc 新版统计功能 --捐赠统计
 * @author zdl
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/chart/donation")
public class DonationChartController {
    @Autowired
    private DonationChartService donationChartService;

    /**
     * 捐赠总金额统计
     * @param smSchoolmate
     * @return
     */
    @PostMapping(value = "donationTotalMoneyChart")
    public List<Map<String,Object>> donationTotalMoneyChart(SmSchoolmate smSchoolmate){
        if(smSchoolmate.getChartEndDate() != null) {
            return donationChartService.donationTotalMoneyChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                    DateUtils.formatDate(smSchoolmate.getChartEndDate(),"yyyy-MM-dd"),smSchoolmate);
        }

        return donationChartService.donationTotalMoneyChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                DateUtils.getDate(),smSchoolmate);
       // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 捐赠金额统计
     * @param smSchoolmate
     * @return
     */
    @PostMapping(value = "donationMonthlyMoneyChart")
    public List<Map<String,Object>> donationMonthlyMoneyChart(SmSchoolmate smSchoolmate){
        if(smSchoolmate.getChartEndDate() != null) {
            return donationChartService.donationMonthlyMoneyChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                    DateUtils.formatDate(smSchoolmate.getChartEndDate(),"yyyy-MM-dd"),smSchoolmate);
        }

        return donationChartService.donationMonthlyMoneyChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                DateUtils.getDate(),smSchoolmate);
       // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 捐赠总次数统计
     * @param smSchoolmate
     * @return
     */
    @PostMapping(value = "donationTotalCountChart")
    public List<Map<String,Object>> donationTotalCountChart(SmSchoolmate smSchoolmate){
        if(smSchoolmate.getChartEndDate() != null) {
            return donationChartService.donationTotalCountChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                    DateUtils.formatDate(smSchoolmate.getChartEndDate(),"yyyy-MM-dd"),smSchoolmate);
        }

        return donationChartService.donationTotalCountChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                DateUtils.getDate(),smSchoolmate);
        // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 捐赠次数统计
     * @param smSchoolmate
     * @return
     */
    @PostMapping(value = "donationMonthlyCountChart")
    public List<Map<String,Object>> donationMonthlyCountChart(SmSchoolmate smSchoolmate){
        if(smSchoolmate.getChartEndDate() != null) {
            return donationChartService.donationMonthlyCountChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                    DateUtils.formatDate(smSchoolmate.getChartEndDate(),"yyyy-MM-dd"),smSchoolmate);
        }

        return donationChartService.donationMonthlyCountChart("maintab.time","month",Integer.valueOf(smSchoolmate.getParamA()),
                DateUtils.getDate(),smSchoolmate);
        // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 项目类型金额统计
     */
    @PostMapping(value = "donationProjectTypeChart")
    public List<ProjectTypeVo> donationProjectTypeChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationProjectTypeChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationProjectTypeChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 捐赠项目金额统计
     */
    @PostMapping(value = "donationProjectMoneyChart")
    public List<Map<String, Object>> donationProjectMoneyChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationProjectMoneyChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationProjectMoneyChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 捐赠项目次数统计
     */
    @PostMapping(value = "donationProjectCountChart")
    public List<Map<String, Object>> donationProjectCountChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationProjectCountChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationProjectCountChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 不同龄校友捐赠统计
     */
    @PostMapping(value = "donationAgeGroupChart")
    public List<Map<Object, Object>> donationAgeGroupChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationAgeGroupChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationAgeGroupChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 捐赠金额区间统计
     */
    @PostMapping(value = "donationSectionMoneyChart")
    public List<Map<String, Object>> donationSectionMoneyChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationSectionMoneyChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationSectionMoneyChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 捐赠笔数金额区间统计
     */
    @PostMapping(value = "donationSectionCountChart")
    public List<Map<String, Object>> donationSectionCountChart (DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationSectionCountChart(Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationSectionCountChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }
    /**
     * 一起捐金额统计
     * @param donRecord
     * @return
     */
    @PostMapping(value = "donationTogetherMoneyChart")
    public List<Map<String,Object>> donationTogetherMoneyChart(DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationTogetherMoneyChart("maintab.time","month",Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationTogetherMoneyChart("maintab.time","month",Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
        // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 一起捐次数统计
     * @param donRecord
     * @return
     */
    @PostMapping(value = "donationTogetherCountChart")
    public List<Map<String,Object>> donationTogetherCountChart(DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationTogetherCountChart("maintab.time","month",Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }

        return donationChartService.donationTogetherCountChart("maintab.time","month",Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
        // return donationChartService.donationTotalChart(smSchoolmate);
    }
    /**
     * 用户捐赠分析（漏斗）
     */
    @PostMapping(value = "donationSmAnalysisChart")
    public List<Map<String,Object>> donationSmAnalysisChart(DonRecord donRecord){
        if(donRecord.getChartEndDate() != null) {
            return donationChartService.donationSmAnalysisChart( Integer.valueOf(donRecord.getParamA()),
                    DateUtils.formatDate(donRecord.getChartEndDate(),"yyyy-MM-dd"),donRecord);
        }
        return donationChartService.donationSmAnalysisChart(Integer.valueOf(donRecord.getParamA()),
                DateUtils.getDate(),donRecord);
    }

    /**
     * 捐赠人金额排行
     * @param tableVo
     * @return
     */
    @PostMapping(value = "getDonationMoney")
    public PageInfo<Map<String,Object>> getDonationMoney( int pageNo,int pageSize,TableVo tableVo){

        return donationChartService.getDonationMoney( pageNo,pageSize, tableVo);
    }

    /**
     * 捐赠人次数排行
     * @param tableVo
     * @return
     */
    @PostMapping(value = "getDonationCount")
    public PageInfo<Map<String,Object>> getDonationCount( int pageNo,int pageSize,TableVo tableVo){

        return donationChartService.getDonationCount( pageNo,pageSize, tableVo);
    }

    /**
     * 捐赠项目金额排行
     * @param tableVo
     * @return
     */
    @PostMapping(value = "getDonationProject")
    public PageInfo<Map<String,Object>> getDonationProject( int pageNo,int pageSize,TableVo tableVo){

        return donationChartService.getDonationProject( pageNo,pageSize, tableVo);
    }

}
