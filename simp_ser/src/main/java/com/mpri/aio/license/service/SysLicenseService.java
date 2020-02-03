package com.mpri.aio.license.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.common.page.PageIo;
import org.springframework.stereotype.Service;
import com.mpri.aio.base.service.CrudService;
import com.mpri.aio.license.model.SysLicense;
import com.mpri.aio.license.mapper.SysLicenseMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 认证——Service
 * @Author: daihongbo
 * @project simp
 * @CreateDate: Mon Sep 16 19:24:31 GMT+08:00 2019
 * @Version: v_2.01
 */
@Service
public class SysLicenseService extends CrudService<SysLicenseMapper, SysLicense> {

    public SysLicense getBeanBySchoolName(SysLicense sysLicense) {
        return this.mapper.getBeanBySchoolName(sysLicense);
    }

    public PageIo<SysLicense> loadByPageAll(int pageNo, int pageSize, SysLicense sysLicense) {
        PageHelper.startPage(pageNo, pageSize);
        Page<SysLicense> pageList = this.mapper.loadByPageAll(sysLicense);
        PageIo<SysLicense> pageInfo = new PageIo(pageList);
        return pageInfo;
    }

    public void updateLicenseInfo(SysLicense sysLicense) {
        this.mapper.updateLicenseInfo(sysLicense);
    }

    public SysLicense getBeanBySchoolCodeAndDate(SysLicense sysLicense) {
        return this.mapper.getBeanBySchoolCodeAndDate(sysLicense);
    }

    public List<Map<String, Object>> loadShoolKeyAndValue(String label) {
        return this.mapper.loadShoolKeyAndValue(label);
    }
}