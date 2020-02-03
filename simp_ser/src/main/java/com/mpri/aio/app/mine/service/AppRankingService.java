package com.mpri.aio.app.mine.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.mine.mapper.*;
import com.mpri.aio.app.mine.vo.*;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

/**
 * @Description
 * @Date 2019/11/12 13:25
 * @Created by lzq
 */
@Service
public class AppRankingService {

    @Autowired
    private AppCardRankingMapper appCardRankingMapper;
    @Autowired
    private AppChaDonMoneyMapper appChaDonMoneyMapper;
    @Autowired
    private AppChaDonTimeMapper appChaDonTimeMapper;
    @Autowired
    private AppChaProveMapper appChaProveMapper;
    @Autowired
    private AppChaShareMapper appChaShareMapper;

//    @Value("${rank.cardNum}")
    private Integer persionNum;

    @Autowired
    private RunSettingParamsUtils runSettingParamsUtils;
    

    // 领卡排名
    public  Map<String,Object> getCardRank(AppCardRankingVo cardRankingVo){
        if(StringUtil.isNotEmpty(cardRankingVo.getCardId())){
            Map<String,Object> map = new HashMap<String,Object>();
            //查询领卡的前三名
            PageIo<AppCardRankingVo> pageIo = loadByPage( cardRankingVo);
            //查询片面 前后的N人
            cardRankingVo.setPerNum(Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)));
            List<AppCardRankingVo> list  = getCardRanking(cardRankingVo);
            map.put("ranking",pageIo.getList());
            map.put("order",list);
            return  map;
        }else{
            return  null;
        }
    }

    //领卡排名当前校友前后领卡的人
    public List<AppCardRankingVo>  getCardRanking(AppCardRankingVo cardRankingVo){
        Integer max = appCardRankingMapper.getMaxRanking(cardRankingVo);
        cardRankingVo.setMin(getMin(cardRankingVo.getRanking(),cardRankingVo.getPerNum()));
        cardRankingVo.setMax(getMax(cardRankingVo.getRanking(),cardRankingVo.getPerNum(), max));
        List<AppCardRankingVo> list = appCardRankingMapper.getCardRanking(cardRankingVo);
        return list;
    }

    //查询前三名领卡的人
    public PageIo<AppCardRankingVo> loadByPage(AppCardRankingVo cardRankingVo){
        PageHelper.startPage(cardRankingVo.getStartNum(),cardRankingVo.getEndNum());
        Page<AppCardRankingVo> pageList = appCardRankingMapper.loadByPage(cardRankingVo);
        PageIo<AppCardRankingVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    /**
     * 校友捐赠钱数归纳表
     */
    public Map<String,Object> getDonMoney(AppChaDonMoneyVo appChaDonMoneyVo) {
        Map<String,Object> map = new HashMap<String,Object>();
        PageIo<AppChaDonMoneyVo> pageIo =  getDonMoneylodapge( appChaDonMoneyVo);
        map.put("ranking",pageIo.getList());
        if(StringUtil.isNotEmpty(appChaDonMoneyVo.getUserId())){
            //查询片面 前后的N人
            appChaDonMoneyVo.setPerNum(Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)));
            List<AppChaDonMoneyVo> list  = getDonMoneyRank(appChaDonMoneyVo);
            map.put("order",list);
        }else{
            map.put("order",null);
        }
        return  map;
    }

    /**
     * 捐赠钱数 前三名排名的数据
     */
     public List<AppChaDonMoneyVo> getDonMoneyRank(AppChaDonMoneyVo appChaDonMoneyVo){
         //获取最大的数量
         Integer max = appChaDonMoneyMapper.getMaxRanking(appChaDonMoneyVo);
         //获取当前用户的排名数
         Integer ranging = appChaDonMoneyMapper.getRanking(appChaDonMoneyVo);
         appChaDonMoneyVo.setMin(getMin(ranging,appChaDonMoneyVo.getPerNum()));
         appChaDonMoneyVo.setMax(getMax(ranging,appChaDonMoneyVo.getPerNum(), max));

         List<AppChaDonMoneyVo> list = appChaDonMoneyMapper.getDonMoneyRanking(appChaDonMoneyVo);
         return list;
     }

    /**
     * 获取userid   当前用户赠捐钱数表前后排名的数据
     */
     public PageIo<AppChaDonMoneyVo> getDonMoneylodapge(AppChaDonMoneyVo appChaDonMoneyVo){
         PageHelper.startPage(appChaDonMoneyVo.getStartNum(),appChaDonMoneyVo.getEndNum());
         Page<AppChaDonMoneyVo> pageList = appChaDonMoneyMapper.loadByPage(appChaDonMoneyVo);
         PageIo<AppChaDonMoneyVo> pageInfo = new PageIo<>(pageList);
         return pageInfo;
     }

    /**
     * 校友捐赠次数归纳表
     */
    public Map<String,Object> getChaDonTime(AppChaDonTimeVo appChaDonTimeVo) {
        Map<String,Object> map = new HashMap<String,Object>();
        //查询领卡的前三名
        PageIo<AppChaDonTimeVo> pageIo =  getDonNumlodapge(appChaDonTimeVo);

        map.put("ranking",pageIo.getList());

        if(StringUtil.isNotEmpty(appChaDonTimeVo.getUserId())){
            //查询片面 前后的N人
            appChaDonTimeVo.setPerNum(Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)));
            List<AppChaDonTimeVo> list  = getDonNumRank(appChaDonTimeVo);
            map.put("order",list);
        }else{
            map.put("order",null);
        }
        return  map;
    }

    /**
     * 捐赠次数 前三名排名的数据
     */
    public  List<AppChaDonTimeVo> getDonNumRank(AppChaDonTimeVo appChaDonTimeVo){
        //获取最大的数量
        Integer max = appChaDonTimeMapper.getMaxRanking(appChaDonTimeVo);
        //获取当前用户的排名数
        Integer ranging = appChaDonTimeMapper.getRanking(appChaDonTimeVo);
        appChaDonTimeVo.setMin(getMin(ranging,appChaDonTimeVo.getPerNum()));
        appChaDonTimeVo.setMax(getMax(ranging,appChaDonTimeVo.getPerNum(), max));

        List<AppChaDonTimeVo> list = appChaDonTimeMapper.getDonTimeRanking(appChaDonTimeVo);
        return list;
    }

    /**
     * 获取userid 捐赠次数归纳表前后排名的数据
     */
    public PageIo<AppChaDonTimeVo> getDonNumlodapge(AppChaDonTimeVo appChaDonTimeVo){
        PageHelper.startPage(appChaDonTimeVo.getStartNum(),appChaDonTimeVo.getEndNum());
        Page<AppChaDonTimeVo> pageList = appChaDonTimeMapper.loadByPage(appChaDonTimeVo);
        PageIo<AppChaDonTimeVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    /**
     * 校友分享归纳表
     */
    public Map<String,Object> getChaShare(AppChaShareVo appChaShareVo) {
        Map<String,Object> map = new HashMap<String,Object>();
        //查询领卡的前三名
        PageIo<AppChaShareVo> pageIo =  getShareodapge(appChaShareVo);

        map.put("ranking",pageIo.getList());
        if(StringUtil.isNotEmpty(appChaShareVo.getUserId())){
            //查询片面 前后的N人
            appChaShareVo.setPerNum(Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)));
            List<AppChaShareVo> list  = getShareRank(appChaShareVo);
            map.put("order",list);

        }else{
            map.put("order", null);
        }
        return  map;
    }

    /**
     * 校友分享 前三名排名的数据
     */
    public List<AppChaShareVo> getShareRank(AppChaShareVo appChaShareVo){
        //获取最大的数量
        Integer max = appChaShareMapper.getMaxRanking(appChaShareVo);
        //获取当前用户的排名数
        Integer ranging = appChaShareMapper.getRanking(appChaShareVo);
        appChaShareVo.setMin(getMin(ranging,appChaShareVo.getPerNum()));
        appChaShareVo.setMax(getMax(ranging,appChaShareVo.getPerNum(), max));

        List<AppChaShareVo> list = appChaShareMapper.getShareRanking(appChaShareVo);
        return list;
    }

    /**
     * 获取userid 校友分享归纳表前后排名的数据
     */
    public PageIo<AppChaShareVo> getShareodapge(AppChaShareVo appChaShareVo){
        PageHelper.startPage(appChaShareVo.getStartNum(),appChaShareVo.getEndNum());
        Page<AppChaShareVo> pageList = appChaShareMapper.loadByPage(appChaShareVo);
        PageIo<AppChaShareVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    /**
     * 校友认证归纳表
     */
    public Map<String,Object> getChaProve(AppChaProveVo appChaProveVo) {
        Map<String,Object> map = new HashMap<String,Object>();
        //查询领卡的前三名
        PageIo<AppChaProveVo> pageIo =  getProvelodapge(appChaProveVo);
        map.put("ranking",pageIo.getList());

        if(StringUtil.isNotEmpty(appChaProveVo.getUserId())){
            //查询片面 前后的N人
            appChaProveVo.setPerNum(Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)));
            List<AppChaProveVo> list  = getProveRank(appChaProveVo);
            map.put("order",list);

        }else{
            map.put("order",null);
        }
        return  map;
    }

    /**
     * 校友认证 前三名排名的数据
     */
    public List<AppChaProveVo>  getProveRank(AppChaProveVo appChaProveVo){
        //获取最大的数量
        Integer max = appChaProveMapper.getMaxRanking(appChaProveVo);
        //获取当前用户的排名数
        Integer ranging = appChaProveMapper.getRanking(appChaProveVo);

        appChaProveVo.setMin(getMin(ranging,appChaProveVo.getPerNum()));
        appChaProveVo.setMax(getMax(ranging,appChaProveVo.getPerNum(), max));
        List<AppChaProveVo> list = appChaProveMapper.getProveRanking(appChaProveVo);
        return list;
    }

    /**
     * 获取userid 校友认证归纳表前后排名的数据
     */
    public PageIo<AppChaProveVo> getProvelodapge(AppChaProveVo appChaProveVo){
        PageHelper.startPage(appChaProveVo.getStartNum(),appChaProveVo.getEndNum());
        Page<AppChaProveVo> pageList = appChaProveMapper.loadByPage(appChaProveVo);
        PageIo<AppChaProveVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    private Integer getMax(Integer point, Integer perNum, Integer max){
        if(null == point || null ==perNum || null ==max){
           return  2 * Integer.parseInt(runSettingParamsUtils.getSettingByKey(RunSettingParamsUtils.rank_cardNum)) + 1;
        }else if(max < point+perNum){
            return  max;
        }else{
            return  max+perNum;
        }
    }

    private int getMin(Integer point, Integer perNum){
        if(null == point || null ==perNum){
            return 1;
        }else if(0 < point- perNum){
            return  point - perNum+1;
        }else{
            return 1;
        }
    }
}
