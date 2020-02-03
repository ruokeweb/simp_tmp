package com.mpri.aio.chart.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.chart.mapper.DonationChartMapper;
import com.mpri.aio.chart.vo.ProjectTypeVo;
import com.mpri.aio.chart.vo.TableVo;
import com.mpri.aio.chart.vo.UnitNature;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.mapper.SysDictMapper;
import com.mpri.aio.system.model.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 新版统计Sevice
 * @author Administrator
 *
 */
@Service
public class DonationChartService {
    @Autowired
    private DonationChartMapper donationChartMapper;
    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    SysDictMapper sysDictMapper;
    private Map<String,List<SysDict>> dictCache;

    private void InitMaps() {
        dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);
    }
    /**
     * 捐赠总金额统计
     * @return
     */
    public List<Map<String, Object>> donationTotalMoneyChart(String dateColumn, String groupType,
                                                        int num, String enddate,SmSchoolmate schoolmate) {

        List<Map<String, Object>> list = donationChartMapper.donationTotalMoneyChart(dateColumn, groupType, new int[num], enddate, schoolmate);
       /* List<Map<String,Object>> returnList = new ArrayList<>();
        if(list!=null){
            for (int i = 0 ;i < list.size(); i++){
                Map<String,Object> map = list.get(i);
                if(i>0){
                    Map<String,Object> map1 = list.get(i-1);
                    Double money =0.00;
                    money = Double.valueOf(map.get("sum").toString())+Double.valueOf(map1.get("sum").toString());
                    map.put("sum",money);
                }
                returnList.add(i,map);
            }
        }*/
        return list;
    }
    /**
     * 捐赠总次数统计
     * @return
     */
    public List<Map<String, Object>> donationTotalCountChart(String dateColumn, String groupType,
                                                        int num, String enddate,SmSchoolmate schoolmate) {

        List<Map<String, Object>> list = donationChartMapper.donationTotalCountChart(dateColumn, groupType, new int[num], enddate, schoolmate);
      /*  List<Map<String,Object>> returnList = new ArrayList<>();
        if(list!=null){
            for (int i = 0 ;i < list.size(); i++){
                Map<String,Object> map = list.get(i);
                if(i>0){
                    Map<String,Object> map1 = list.get(i-1);
                    Double money =0.00;
                    money = Double.valueOf(map.get("sum").toString())+Double.valueOf(map1.get("sum").toString());
                    map.put("sum",money);
                }
                returnList.add(i,map);
            }
        }*/
        return list;
    }

    /**
     * 捐赠的单月捐赠金额
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param schoolmate
     * @return
     */
    public List<Map<String, Object>> donationMonthlyMoneyChart(String dateColumn, String groupType,
                                                               int num, String enddate,SmSchoolmate schoolmate) {
        return donationChartMapper.donationMonthlyMoneyChart(dateColumn,groupType,new int[num],enddate,schoolmate);
    }
    /**
     * 捐赠的单月捐赠次数
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param schoolmate
     * @return
     */
    public List<Map<String, Object>> donationMonthlyCountChart(String dateColumn, String groupType,
                                                               int num, String enddate,SmSchoolmate schoolmate) {
        return donationChartMapper.donationMonthlyCountChart(dateColumn,groupType,new int[num],enddate,schoolmate);
    }

    /**
     * 项目类型金额统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<ProjectTypeVo> donationProjectTypeChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        /**获取学历信息性质*/
        SysDict eduDict = new SysDict();
        eduDict.setTypeCode("PROJECT_TYPE");
        List<SysDict> sysDictList = sysDictMapper.getSysDictByTypecode(eduDict);
        List<Map<String, String>> list = donationChartMapper.donationProjectTypeChart(donRecord);
        /*校友总人数*/
        Double count = getCount(list);
        List<ProjectTypeVo> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Map<String, String> map:list) {
                boolean flag = true;
                ProjectTypeVo projectTypeVo = new ProjectTypeVo();
                if(sysDictList!=null&&sysDictList.size()>0){
                    for (SysDict dict:sysDictList) {
                        if(map.get("type")!=null&&map.get("type").equals(dict.getValue())){
                            flag=false;
                            projectTypeVo.setType(dict.getLabel());
                        }
                    }
                }
                if(flag){
                    projectTypeVo.setType("不明");
                }
                Object ob = map.get("sum");
                String smsum= String.valueOf(ob);
                Double percentValue = Double.valueOf(Double.valueOf(smsum)/ count);
                projectTypeVo.setValuePercent(percentValue);
                projectTypeVo.setValue(smsum);
                result.add(projectTypeVo);
            }
        }
        return  result;
    }

    /**
     * 捐赠项目金额统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationProjectMoneyChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> list = donationChartMapper.donationProjectMoneyChart(donRecord);
        return list;
    }
    /**
     * 捐赠项目次数统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationProjectCountChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> list = donationChartMapper.donationProjectCountChart(donRecord);
        return list;
    }
    /**
     * 不同龄校友捐赠统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<Object, Object>> donationAgeGroupChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> list = donationChartMapper.donationAgeGroupChart(donRecord);
        List<Map<Object, Object>> returnList = new ArrayList<>();
        for (int i =1 ;i<9;i++){
            Map<Object, Object> retrunMap = new HashMap<>();
            int[] yearGroupByArg=new int[2];
            String argGroup ="";
            if(i==1){
                yearGroupByArg = getYearGroupByArg(0, (i * 10 + 9));
                argGroup="20岁之前";
            }else if(i==8){
                yearGroupByArg = getYearGroupByArg(i * 10);
                argGroup="80岁之后";
            }else{
                yearGroupByArg = getYearGroupByArg(i * 10, (i * 10 + 9));
                argGroup=i * 10+"-"+(i * 10 + 9)+"岁";
            }
            retrunMap.put("age",argGroup);
            retrunMap.put("money",0);
            if(list!=null){
                for (Map<String, Object> map :list) {
                    Object birthday = map.get("birthday");
                    int cou= 0;
                    Object ob = map.get("money");
                    Double smsum= Double.valueOf(String.valueOf(ob));
                    if(birthday!=null &&!"".equals(birthday.toString())){
                        if((yearGroupByArg[0]<=Integer.parseInt(birthday.toString()))&&(yearGroupByArg[1]>=Integer.parseInt(birthday.toString()))){
                            Object number=new Object();
                            Double aDouble = 0.00;
                            int num=0;
                            number = retrunMap.get("money");//获取已经存进去的
                            String getSum= String.valueOf(number);
                            if(number!=null){
                                aDouble = Double.valueOf(getSum);
                            }
                            retrunMap.put("money",smsum+aDouble);
                        }
                    }
                }
            }
            returnList.add(retrunMap);
        }
        return returnList;
    }

    /**
     * 捐赠金额区间统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationSectionMoneyChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> list = donationChartMapper.donationSectionMoneyChart(donRecord);
        List<Map<String, Object>> retrunlist = new ArrayList<>();
        String [] section =new String[]{ "0-10(元)","10-100(元)","100-500(元)","500-1000(元)","1000-3000(元)","3000-5000(元)","5000-10000(元)",">10000(元)"};
        for (int i =0 ; i<section.length ; i++){
            Map<String ,Object> map = new HashMap<>();
            map.put("section",section[i]);
            map.put("money",0);
           if(list!=null &&list.size()>0){
               for (Map<String, Object> backMap :list) {
                   if(backMap.get("qujian")!=null && backMap.get("qujian").equals(section[i])){
                       map.put("money",backMap.get("money"));
                   }
               }
           }
            retrunlist.add(map);
        }
        return retrunlist;
    }
    /**
     * 捐赠金额区间统计
     * @param numMonth
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationSectionCountChart(int numMonth, String enddate, DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(numMonth-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> list = donationChartMapper.donationSectionCountChart(donRecord);
        List<Map<String, Object>> retrunlist = new ArrayList<>();
        String [] section =new String[]{ "0-10(元)","10-100(元)","100-500(元)","500-1000(元)","1000-3000(元)","3000-5000(元)","5000-10000(元)",">10000(元)"};
        for (int i =0 ; i<section.length ; i++){
            Map<String ,Object> map = new HashMap<>();
            map.put("section",section[i]);
            map.put("count",0);
            if(list!=null &&list.size()>0){
                for (Map<String, Object> backMap :list) {
                    if(backMap.get("qujian")!=null && backMap.get("qujian").equals(section[i])){
                        map.put("count",backMap.get("count"));
                    }
                }
            }
            retrunlist.add(map);
        }
        return retrunlist;
    }
    /**
     * 一起捐金额统计
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationTogetherMoneyChart(String dateColumn, String groupType,
                                                               int num, String enddate,DonRecord donRecord) {
        return donationChartMapper.donationTogetherMoneyChart(dateColumn,groupType,new int[num],enddate,donRecord);
    }
    /**
     * 一起捐次数统计
     * @param dateColumn
     * @param groupType
     * @param num
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationTogetherCountChart(String dateColumn, String groupType,
                                                                int num, String enddate,DonRecord donRecord) {
        return donationChartMapper.donationTogetherCountChart(dateColumn,groupType,new int[num],enddate,donRecord);
    }

    /**
     * 用户捐赠分析（漏斗）
     * @param num
     * @param enddate
     * @param donRecord
     * @return
     */
    public List<Map<String, Object>> donationSmAnalysisChart(int num, String enddate,DonRecord donRecord) {
        donRecord.setChartEndDate(DateUtils.parseDate(enddate));
        Date date = DateUtils.addMonths(DateUtils.parseDate(enddate), -(num-1));
        donRecord.setChartStartDate(date);
        List<Map<String, Object>> returnList = new ArrayList<>();
        //第一获取校友总数
        Map<String,Object> mapallSm =new HashMap<>();
        mapallSm.put("name","校友总数");
        mapallSm.put("count",donationChartMapper.getAllSmSchoolmate(new SmSchoolmate()));
        //获取活跃校友数
        Map<String,Object> loginSignSm =new HashMap<>();
        loginSignSm.put("name","活跃校友数");
        loginSignSm.put("count",donationChartMapper.getloginSignSchoolmate(donRecord));

        //获取捐赠校友数
        Map<String,Object> donationSm =new HashMap<>();
        donationSm.put("name","捐赠校友数");
        donationSm.put("count",donationChartMapper.getDonationSchoolmate(donRecord));
        //获取捐赠成功数
        Map<String,Object> donationSuccessSm =new HashMap<>();
        donationSuccessSm.put("name","捐赠成功数");

        donRecord.setState(GlobalStr.NORMAL_DON);
        donationSuccessSm.put("count",donationChartMapper.getDonationSuccessSchoolmate(donRecord));
        returnList.add(mapallSm);
        returnList.add(loginSignSm);
        returnList.add(donationSm);
        returnList.add(donationSuccessSm);
        return returnList;
    }

    /**
     * 捐赠人金额排行
     * @param tableVo
     * @return
     */
    public PageInfo<Map<String,Object>> getDonationMoney( int pageNo,int pageSize,TableVo tableVo) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Map<String,Object>> pageList = donationChartMapper.getDonationMoney(tableVo);
        PageIo<Map<String,Object>> pageInfo = new PageIo(pageList);
        return pageInfo;
    }
    /**
     * 捐赠人次数排行
     * @param tableVo
     * @return
     */
    public PageInfo<Map<String,Object>> getDonationCount( int pageNo,int pageSize,TableVo tableVo) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Map<String,Object>> pageList = donationChartMapper.getDonationCount(tableVo);
        PageIo<Map<String,Object>> pageInfo = new PageIo(pageList);
        return pageInfo;
    }
    /**
     * 捐赠项目金额排行
     * @param tableVo
     * @return
     */
    public PageInfo<Map<String,Object>> getDonationProject( int pageNo,int pageSize,TableVo tableVo) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Map<String,Object>> pageList = donationChartMapper.getDonationProject(tableVo);
        PageIo<Map<String,Object>> pageInfo = new PageIo(pageList);
        return pageInfo;
    }
    public Double getCount(List<Map<String, String>> list) {
        Double count = 0.00;
        for (Map<String, String> map : list) {
            String smsum = String.valueOf(map.get("sum"));
            count += Double.valueOf(smsum == null || "".equals(smsum) || "null".equals(smsum)? "0" : smsum);
        }
        return count;
    }

    /**
     * 根据年龄段获取年份 0-19 20-29 30-39 40-49 50-59 60-69 70-79 80-150
     */
    private int[] getYearGroupByArg(int startArg,int endArg){
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int[] years =new int[2];
        years[0]=Integer.parseInt(year)-endArg;
        years[1]=Integer.parseInt(year)-startArg;
        return years;
    }
    private int[] getYearGroupByArg(int startArg){
        Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
        int[] years =new int[2];
        years[0]=0;
        years[1]=Integer.parseInt(year)-startArg;
        return years;
    }



}
