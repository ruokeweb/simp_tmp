package com.mpri.aio.app.don.service;

import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.don.mapper.AppDonRecordMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.donation.model.DonRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 捐赠记录管理——Service
 * @Author: LZQ
 * @project AIO
 * @CreateDate: Wed Aug 29 15:09:37 CST 2018
 * @Version: v_1.0
 */
@Service
public class AppDonRecordService {

    @Autowired
    private AppDonRecordMapper appDonRecordMapper;



    public PageIo<DonRecord> loadByPage(int pageNo, int pageSize, DonRecord donRecord) {
        PageHelper.startPage(pageNo, pageSize);
        List<DonRecord> list = this.appDonRecordMapper.loadByPage(donRecord);
        PageIo<DonRecord> pageInfo = new PageIo<DonRecord>(list);
        return pageInfo;
    }

    public PageIo<DonRecord> loadTeamByPage(int pageNo, int pageSize, DonRecord donRecord){
        PageHelper.startPage(pageNo, pageSize);
        List<DonRecord> pageList = this.appDonRecordMapper.loadTeamByPage(donRecord);
        PageIo<DonRecord> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public Integer getCountByProId(DonRecord donRecord) {
        Integer num = this.appDonRecordMapper.getCountByProId(donRecord);
        return num;
    }

    public Integer getTemPerCount(DonRecord donRecord) {
        Integer num = this.appDonRecordMapper.getTemPerCount(donRecord);
        return num;
    }

    public BigDecimal getCountByProIdForMoney(DonRecord donRecord) {
        BigDecimal money = this.appDonRecordMapper.getCountByProIdForMoney(donRecord);
        return money;
    }

    public BigDecimal getTemCountByProId(DonRecord donRecord) {
        BigDecimal money = this.appDonRecordMapper.getTemCountByProId(donRecord);
        return money;
    }

    public void save(DonRecord donRecord){
        if(null!=donRecord.getId() && !"0".equals(donRecord.getId())){
            appDonRecordMapper.update(donRecord);
        }else{
            donRecord.preInsert();
            appDonRecordMapper.insert(donRecord);
        }
    }

    public void updateBycustomid(DonRecord donRecord){
        this.appDonRecordMapper.updateBycustomid(donRecord);
    }

    public DonRecord get(DonRecord donRecord){
         donRecord = this.appDonRecordMapper.get(donRecord);
         return  donRecord;
    }


    public  BigDecimal getPerCountMoney(DonRecord donRecord){
        return appDonRecordMapper.getPerCountMoney(donRecord);
    }

    public Integer getPerCount(DonRecord donRecord){
        return appDonRecordMapper.getPerCount(donRecord);
    }
}