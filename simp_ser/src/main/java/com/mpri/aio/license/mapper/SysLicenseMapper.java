package com.mpri.aio.license.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.base.mapper.CrudMapper;
import com.mpri.aio.license.model.SysLicense;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  
 * @Description:  认证——DAO
 * @Author:       daihongbo
 * @project 	  simp 
 * @CreateDate:   Mon Sep 16 19:24:31 GMT+08:00 2019
 * @Version:      v_2.01
 *    
 */
@Mapper
public interface SysLicenseMapper extends CrudMapper<SysLicense>{

  /**
   * 根据学校获取sysLicense
   */
  SysLicense getBeanBySchoolName(SysLicense sysLicense);

  SysLicense getBeanBySchoolCodeAndDate(SysLicense sysLicense);

  Page<SysLicense> loadByPageAll(@Param("entity") SysLicense sysLicense);

  void updateLicenseInfo(SysLicense sysLicense);

  //List<Map<String,Object>> loadShoolKeyAndValue();

  List<Map<String, Object>> loadShoolKeyAndValue(@Param("label") String label);
}