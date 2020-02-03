package com.mpri.aio.webber.vo;

import com.mpri.aio.donation.model.DonProject;

public class DonProjectVo {
    private int pageNo;
    private int pageSize;
    private DonProject donProject;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public DonProject getDonProject() {
        return donProject;
    }

    public void setDonProject(DonProject donProject) {
        this.donProject = donProject;
    }
}
