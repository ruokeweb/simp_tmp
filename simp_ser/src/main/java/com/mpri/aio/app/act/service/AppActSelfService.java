package com.mpri.aio.app.act.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.mapper.AppActSelfMapper;
import com.mpri.aio.app.act.mapper.AppActSelforgContentMapper;
import com.mpri.aio.app.act.vo.ActSelfVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.wxdata.mapper.WxdataTemplateSendMapper;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.IdGen;

@Service
public class AppActSelfService {

    @Autowired
    private AppActSelfMapper appActSelfMapper;

    @Autowired
    private AppActSelforgContentMapper appActSelforgContentMapper;

    @Autowired
    private WxdataTemplateSendMapper wxdataTemplateSendMapper;

    public PageIo<ActSelfVo> getActSelfList(int pageNo, int pageSize, ActSelforg actSelforg) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActSelfVo> pageList=appActSelfMapper.getActSelfList(actSelforg);
        PageIo<ActSelfVo> pageInfo = new PageIo<ActSelfVo>(pageList);
        return pageInfo;
    }

    /**
     * 保存值年返校
     * @param actSelforg
     */
    @Transactional(readOnly = false)
    public void insert(ActSelforg actSelforg, WxdataTemplateSend wxdataTemplateSend) {
        /*先保存值年返校*/
        appActSelfMapper.insert(actSelforg);
        /*然后在将发起的用户加入到值年返校报名列表里面*/
        ActSelforgContent actSelforgContent = new ActSelforgContent();
        actSelforgContent.setId(IdGen.uuid());
        actSelforgContent.setCreateDate(new Date());
        actSelforgContent.setActSelforgId(actSelforg.getId());
        actSelforgContent.setUserId(actSelforg.getUserId());
        appActSelforgContentMapper.insert(actSelforgContent);
        actSelforg.setId(actSelforgContent.getActSelforgId());
        actSelforg=appActSelfMapper.getById(actSelforg);
        if(null==actSelforg.getReadyNo()){
            actSelforg.setReadyNo(1);
        }else{
            actSelforg.setReadyNo(actSelforg.getReadyNo()+1);
        }
        appActSelfMapper.update(actSelforg);
        wxdataTemplateSend.setEventId(actSelforg.getId());
        wxdataTemplateSend.preInsert();
        wxdataTemplateSendMapper.insert(wxdataTemplateSend);

    }

    public ActSelfVo getSelfDetail(ActSelforg actSelforg) {
       return appActSelfMapper.getSelfDetail(actSelforg);
    }



    public PageInfo<AttendSmByAct> getAttendSmBySelf(int pageNo, int pageSize, ActSelforg actSelforg) {
        PageHelper.startPage(pageNo, pageSize);
        Page<AttendSmByAct> pageList=appActSelfMapper.getAttendSmBySelf(actSelforg);
        PageIo<AttendSmByAct> pageInfo = new PageIo<AttendSmByAct>(pageList);
        return pageInfo;
    }

    public ActSelforg getById(ActSelforg actSelforg) {
        return appActSelfMapper.getById(actSelforg);
    }

    public void update(ActSelforg actSelforg) {
        appActSelfMapper.update(actSelforg);
    }

    public Integer getMyActSelfNum(ActSelforg actSelforg) {
        return appActSelfMapper.getMyActSelfNum(actSelforg);
    }
}
