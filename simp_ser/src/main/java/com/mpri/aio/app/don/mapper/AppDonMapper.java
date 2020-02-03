package com.mpri.aio.app.don.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonProjectTogether;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
*
* @Description:  捐赠项目管理——DAO
* @Author:       LZQ
* @project 	  AIO
* @CreateDate:   Wed Aug 29 15:22:22 CST 2018
* @Version:      v_1.0
*
*/
@Mapper
public interface AppDonMapper {

    Page<DonProject> loadByPage(@Param("entity")DonProject donProject);

    Page<DonProject> bannerDonPros(@Param("entity")DonProject donProject);

    DonProject get(@Param("entity") DonProject donProject);

    void updateMoneyAndPer(DonProject donProject);

    List<DonProjectTogether> loadAllListBy(@Param("entity") DonProject donProject);
}