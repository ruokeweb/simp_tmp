package com.mpri.aio.app.mine.service;

import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.app.act.mapper.AppActMapper;
import com.mpri.aio.app.act.vo.ActListVo;
import com.mpri.aio.app.association.mapper.AppAssociationMapper;
import com.mpri.aio.app.don.mapper.AppDonMapper;
import com.mpri.aio.app.don.mapper.AppDonRecordMapper;
import com.mpri.aio.app.mine.mapper.AppHisInfomationmapper;
import com.mpri.aio.app.mine.vo.HisAssVo;
import com.mpri.aio.app.mine.vo.HisDonVo;
import com.mpri.aio.app.mine.vo.HisInfomationVo;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.system.model.SysUser;
import org.activiti.bpmn.model.Association;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppHisInfomationService {
    @Autowired
    private AppHisInfomationmapper appHisInfomationmapper;
    @Autowired
    private AppAssociationMapper appAssociationMapper;
    @Autowired
    private AppActMapper appActMapper;

    /**
     * 查询个人信息 包含最新一条活动、捐赠信息、校友会
     * @param sysUser
     * @return
     */
    public HisInfomationVo getHisInfomation(SysUser sysUser) {
        HisInfomationVo hisInfomationVo=appHisInfomationmapper.getHisInfomation(sysUser);
        if(hisInfomationVo==null){
            hisInfomationVo = new HisInfomationVo();
        }
        hisInfomationVo.setActListVo(appActMapper.getPartakeActOne(new ActActivity(), sysUser.getId()));
        hisInfomationVo.setHisDonVo(appHisInfomationmapper.getDonRecordOne(sysUser.getId()));
        SysUserAsso sysUserAsso = new SysUserAsso();
        sysUserAsso.setUserId(sysUser.getId());
        sysUserAsso.setAsId("root");
        hisInfomationVo.setHisAssVo(appAssociationMapper.getAssociationOne(sysUserAsso));

        return  hisInfomationVo;
    }
}
