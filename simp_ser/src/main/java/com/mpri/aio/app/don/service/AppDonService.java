package com.mpri.aio.app.don.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mpri.aio.app.don.mapper.AppDonMapper;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonProjectTogether;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 小程序捐赠项目——Controller
 * @Author: LZQ
 * @project AIO
 * @CreateDate: Wed Aug 29 15:22:22 CST 2018
 * @Version: v_1.0
 */
@Service
public class AppDonService {

    @Autowired
    private AppDonMapper donMapper;

    public DonProject getById( DonProject donProject){
         donProject= donMapper.get(donProject);
        return donProject;
    }

    public PageIo<DonProject> donProList(int pageNo, int pageSize, DonProject donProject) {
        PageHelper.startPage(pageNo, pageSize);
        Page<DonProject> pageList = donMapper.loadByPage(donProject);
        PageIo<DonProject> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public PageIo<DonProject> bannerDonPros(int pageNo, int pageSize, DonProject donProject) {
        PageHelper.startPage(pageNo, pageSize);
        Page<DonProject> pageList = donMapper.loadByPage(donProject);
        PageIo<DonProject> pageInfo = new PageIo<>(pageList);
        return pageInfo;
    }

    public void  updateMoneyAndPer(DonProject donProject){
        donMapper.updateMoneyAndPer(donProject);
}

    public List<DonProjectTogether> getAllDonPro(DonProject donProject) {
        return donMapper.loadAllListBy(donProject);
    }

}