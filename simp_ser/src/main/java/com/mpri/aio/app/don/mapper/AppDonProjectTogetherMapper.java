package com.mpri.aio.app.don.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.donation.model.DonProjectTogether;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


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
public interface AppDonProjectTogetherMapper {

    Page<DonProjectTogether> loadByPage(@Param("entity") DonProjectTogether donProjectTogether);

    DonProjectTogether get(@Param("entity") DonProjectTogether donProject);

    void insert(DonProjectTogether donProjectTogether);
    void update(DonProjectTogether donProjectTogether);
    void delete(DonProjectTogether donProjectTogether);

    void updateMoneyAndPer( DonProjectTogether donProjectTogether);
}