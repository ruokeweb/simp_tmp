package com.mpri.aio.app.don.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.don.mapper.AppDonProjectTogetherMapper;
import com.mpri.aio.app.wxdata.mapper.WxdataTemplateSendMapper;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.system.utils.BadWordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AppDonProjectTogetherService {
    @Autowired
    private AppDonProjectTogetherMapper appDonProjectTogetherMapper;

    @Autowired
    private WxdataTemplateSendMapper wxdataTemplateSendMapper;

    public PageIo<DonProjectTogether>  donProList(int pageNo, int pageSize, DonProjectTogether donProjectTogether) {
        PageHelper.startPage(pageNo, pageSize);
        Page<DonProjectTogether> pageList = appDonProjectTogetherMapper.loadByPage(donProjectTogether);
        PageIo<DonProjectTogether> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public DonProjectTogether getById( DonProjectTogether donProject){
        donProject= appDonProjectTogetherMapper.get(donProject);
        return donProject;
    }
    //只是单纯保存/更新一起捐
    @Transactional(readOnly = false)
    public DonProjectTogether save(DonProjectTogether donProjectTogether){
        if(null!=donProjectTogether.getId() && !"0".equals(donProjectTogether.getId())){
            appDonProjectTogetherMapper.update(donProjectTogether);
        }else{
            donProjectTogether.preInsert();
            appDonProjectTogetherMapper.insert(donProjectTogether);
        }
        return donProjectTogether;
    }
    //保存/更新一起捐并且新增模板消息
    @Transactional(readOnly = false)
    public DonProjectTogether save(DonProjectTogether donProjectTogether, WxdataTemplateSend wxdataTemplateSend){
        //BadWordUtil.isContaintBadWord(donProjectTogether.getName(), 2);
        String ss=BadWordUtil.replaceBadWord(donProjectTogether.getName(), 2,"*");
        donProjectTogether.setName(ss);
        if(null!=donProjectTogether.getId() && !"0".equals(donProjectTogether.getId())){
            appDonProjectTogetherMapper.update(donProjectTogether);
        }else{
            donProjectTogether.preInsert();
            appDonProjectTogetherMapper.insert(donProjectTogether);
        }
        wxdataTemplateSend.setEventId(donProjectTogether.getId());
        wxdataTemplateSend.preInsert();
        wxdataTemplateSendMapper.insert(wxdataTemplateSend);
        return donProjectTogether;
    }

    public  void delete(DonProjectTogether donProjectTogether){
        appDonProjectTogetherMapper.delete(donProjectTogether);
    }
    public void updateMoneyAndPer( DonProjectTogether donProjectTogether){
        appDonProjectTogetherMapper.updateMoneyAndPer(donProjectTogether);
    }
}
