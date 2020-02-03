package com.mpri.aio.don;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.donation.model.DonThanks;
import com.mpri.aio.donation.service.DonThanksService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


public class DonThanksServiceTest extends ApplicationTests {

    @Autowired
    private DonThanksService donThanksService;
    @Test
    public void test() {
        DonThanks donThanks = new DonThanks();

        donThanks.setInformation("母校你好！");
        donThanks.setIsshow("NO");
        donThanks.setName("游客");
        donThanks.setCreateDate(new Date());
        donThanksService.save(donThanks);

    }

}