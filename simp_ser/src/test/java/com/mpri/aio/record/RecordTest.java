package com.mpri.aio.record;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.donation.model.DonRecord;
import com.mpri.aio.donation.service.DonRecordService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RecordTest extends ApplicationTests {

    @Autowired
    private DonRecordService donRecordService;

    @Test
    public void  getAllRecord(){
        DonRecord donRecord =  new DonRecord();
//          donRecordService.loadAllListBy(new DonRecord());
//        donRecordService.loadByPage(0,10,new DonRecord());
        donRecord.setId("037e4e367b6642818861a523c49244e2");
        donRecord =  donRecordService.get(donRecord);
        System.out.println(donRecord.getName());
    }

}
