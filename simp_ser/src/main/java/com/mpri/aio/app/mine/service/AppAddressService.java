package com.mpri.aio.app.mine.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.mine.mapper.AppAddressMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmAddress;
import com.mpri.aio.schoolmate.model.SmEducation;
import com.mpri.aio.system.common.GlobalStr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppAddressService {

    @Autowired
    AppAddressMapper  appAddressMapper;

    public SmAddress get(SmAddress smAddress){
        return appAddressMapper.get(smAddress);
    }

    public PageIo<SmAddress> loadByPage (int pageNo, int pageSize,SmAddress smAddress){
        PageHelper.startPage(pageNo, pageSize);
        Page<SmAddress> pageList = appAddressMapper.loadByPage(smAddress);
        PageIo<SmAddress> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public void delete(SmAddress smAddress){
        appAddressMapper.delete(smAddress);
    }

    public SmAddress insert(SmAddress smAddress){
        smAddress.preInsert();
        appAddressMapper.insert(smAddress);
        return smAddress;
    }

    public SmAddress update(SmAddress smAddress){
        appAddressMapper.update(smAddress);
        return  smAddress;
    }

    public void setDefault(SmAddress smAddress){
        appAddressMapper.setDefault(smAddress);
    }

    public  void clearDefault(SmAddress smAddress){
        appAddressMapper.clearDefault(smAddress);
    }
    
    /**
     * 设置默认教育经历
     */
    @Transactional(readOnly = false)
    public void setDefalutAddr(SmAddress smAddress) {
    	smAddress.setIsDefault(GlobalStr.NOT_DEFAULT);
    	appAddressMapper.clearDefault(smAddress);
    	smAddress.setIsDefault(GlobalStr.IS_DEFAULT);
    	appAddressMapper.setDefault(smAddress);
    }
}
