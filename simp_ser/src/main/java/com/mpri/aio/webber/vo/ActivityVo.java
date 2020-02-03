package com.mpri.aio.webber.vo;

import com.mpri.aio.act.model.ActActivity;

public class ActivityVo {
    private int pageNo;
    private int pageSize;
    private ActActivity actActivity;

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

    public ActActivity getActActivity() {
        return actActivity;
    }

    public void setActActivity(ActActivity actActivity) {
        this.actActivity = actActivity;
    }
}
