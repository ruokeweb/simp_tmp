package com.mpri.aio.app.act.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.mapper.AppActSelfMapper;
import com.mpri.aio.app.act.mapper.AppActSelforgContentMapper;
import com.mpri.aio.app.act.vo.ActSelfVo;
import com.mpri.aio.common.page.PageIo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AppActSelfContentService {
    @Autowired
    private AppActSelforgContentMapper appActSelforgContentMapper;
    @Autowired
    private AppActSelfMapper appActSelfMapper;
    /**
     * 判断是否报名
     * @param actSelforgContent
     * @return
     */
    public int getNumByUserId(ActSelforgContent actSelforgContent) {
        return appActSelforgContentMapper.getNumByUserId(actSelforgContent);
    }

    @Transactional(readOnly = false)
    public void saveSelfContent(ActSelforgContent actSelforgContent) {
        appActSelforgContentMapper.insert(actSelforgContent);
        ActSelforg actSelforg = new ActSelforg();
        actSelforg.setId(actSelforgContent.getActSelforgId());
        actSelforg=appActSelfMapper.getById(actSelforg);
        if(null==actSelforg.getReadyNo()){
            actSelforg.setReadyNo(1);
        }else{
            actSelforg.setReadyNo(actSelforg.getReadyNo()+1);
        }
        appActSelfMapper.update(actSelforg);
    }

    public Integer getMyActSelfNum(ActSelforgContent actSelforg) {
        return appActSelforgContentMapper.getMyActSelfNum(actSelforg);
    }

    public PageInfo<ActSelfVo> getActSelfList(int pageNo, int pageSize, ActSelforgContent actSelforg) {
        PageHelper.startPage(pageNo, pageSize);
        Page<ActSelfVo> pageList=appActSelforgContentMapper.getActSelfList(actSelforg);
        PageIo<ActSelfVo> pageInfo = new PageIo<ActSelfVo>(pageList);
        return pageInfo;
    }
}
