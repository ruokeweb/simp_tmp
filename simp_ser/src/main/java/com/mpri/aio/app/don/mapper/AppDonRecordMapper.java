package com.mpri.aio.app.don.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.donation.model.DonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;


/**
*
* @Description:  捐赠记录管理——DAO
* @Author:       LZQ
* @project 	  AIO
* @CreateDate:   Wed Aug 29 15:09:37 CST 2018
* @Version:      v_1.0
*
*/
@Mapper
public interface AppDonRecordMapper{



   Page<DonRecord> loadTeamByPage(@Param("entity") DonRecord donRecord);
   Page<DonRecord> loadByPage(@Param("entity") DonRecord donRecord);
//   Page<DonRecord> getLasterDonRecord(@Param("entity") DonRecord donRecord);

   Integer getCountByProId(@Param("entity") DonRecord donRecord);

   Integer getTemPerCount(@Param("entity") DonRecord donRecord);

   BigDecimal getCountByProIdForMoney(@Param("entity") DonRecord donRecord);

   BigDecimal getTemCountByProId(@Param("entity") DonRecord donRecord);

   BigDecimal getPerCountMoney(@Param("entity") DonRecord donRecord);

   Integer getPerCount(@Param("entity") DonRecord donRecord);

   DonRecord get(DonRecord donRecord);

   void insert(DonRecord donRecord);

   void update( DonRecord donRecord);

   void delete( DonRecord donRecord);

   void updateBycustomid(DonRecord donRecord);
}