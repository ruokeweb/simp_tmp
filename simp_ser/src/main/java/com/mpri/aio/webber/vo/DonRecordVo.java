package com.mpri.aio.webber.vo;

import com.mpri.aio.donation.model.DonRecord;

public class DonRecordVo {
    private int pageNo;
    private int pageSize;
    private DonRecord donRecord;

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

    public DonRecord getDonRecord() {
        return donRecord;
    }

    public void setDonRecord(DonRecord donRecord) {
        this.donRecord = donRecord;
    }
}
