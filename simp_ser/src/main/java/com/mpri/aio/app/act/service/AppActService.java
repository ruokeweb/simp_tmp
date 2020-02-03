package com.mpri.aio.app.act.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.model.ActSetting;
import com.mpri.aio.app.act.mapper.AppActMapper;
import com.mpri.aio.app.act.vo.ActDetailVo;
import com.mpri.aio.app.act.vo.ActListVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.act.vo.BannerActsVo;
import com.mpri.aio.common.page.PageIo;

@Service
public class AppActService {

    @Autowired
    private AppActMapper appActMapper;

    /**
     * 活动首页banner图
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    public PageIo<BannerActsVo> getBannerActs(int pageNo, int pageSize, ActActivity actActivity) {
        PageHelper.startPage(pageNo, pageSize);
        Page<BannerActsVo> pageList=appActMapper.getBannerActs(actActivity);
        PageIo<BannerActsVo> pageInfo = new PageIo<BannerActsVo>(pageList);
        return pageInfo;
    }

    /**
     * 活动首页列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    public PageIo<ActListVo> ActList(int pageNo, int pageSize, ActActivity actActivity) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActListVo> pageList=appActMapper.ActList(actActivity);
        PageIo<ActListVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    /**
     * 活动详情
     * @param actActivity
     * @return
     */
    public ActDetailVo getActDetail(ActActivity actActivity) {
        return appActMapper.getActDetail(actActivity);
    }

    /**
     * 已经报名该活动的校友列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @return
     */
    public PageInfo<AttendSmByAct> getAttendSmByAct(int pageNo, int pageSize, ActActivity actActivity) {
        PageHelper.startPage(pageNo, pageSize);
        Page<AttendSmByAct> pageList=appActMapper.getAttendSmByAct(actActivity);
        PageIo<AttendSmByAct> pageInfo = new PageIo<AttendSmByAct>(pageList);
        return pageInfo;
    }

    /**
     * 获取活动的动态表单
     * @param actActivity
     * @return
     */
    public List<ActSetting> getActForm(ActActivity actActivity) {
        return appActMapper.getActForm(actActivity);
    }

    /***
     * 获得该活动信息
     * @param actActivity
     * @return
     */
    public ActActivity getById(ActActivity actActivity) {
        return appActMapper.getById(actActivity);
    }

    public void update(ActActivity actActivity) {
        appActMapper.update(actActivity);
    }

    public PageInfo<ActListVo> hotActList(int pageNo, int pageSize, ActActivity actActivity) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActListVo> pageList=appActMapper.hotActList(actActivity);
        PageIo<ActListVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    /**
     * 我报名参与的活动列表
     * @param pageNo
     * @param pageSize
     * @param actActivity
     * @param userId
     * @return
     */
    public PageInfo<ActListVo> getPartakeActList(int pageNo, int pageSize, ActActivity actActivity, String userId) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActListVo> pageList=appActMapper.getPartakeActList(actActivity,userId);
        PageIo<ActListVo> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }
}
