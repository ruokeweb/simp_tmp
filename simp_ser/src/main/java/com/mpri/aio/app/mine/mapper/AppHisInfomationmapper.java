package com.mpri.aio.app.mine.mapper;

import com.mpri.aio.app.mine.vo.HisDonVo;
import com.mpri.aio.app.mine.vo.HisInfomationVo;
import com.mpri.aio.system.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppHisInfomationmapper {

    HisInfomationVo getHisInfomation(SysUser sysUser);

    HisDonVo getDonRecordOne(String userId);
}
