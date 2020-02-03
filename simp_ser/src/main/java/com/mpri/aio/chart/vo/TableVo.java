package com.mpri.aio.chart.vo;

import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.donation.model.DonRecord;

public class TableVo {
    private String field; //排序字段
    private String type; //排序方式
    private DonRecord donRecord;//其他参数
    private ActActivity actActivity;//活动
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DonRecord getDonRecord() {
        return donRecord;
    }

    public void setDonRecord(DonRecord donRecord) {
        this.donRecord = donRecord;
    }

    public ActActivity getActActivity() {
        return actActivity;
    }

    public void setActActivity(ActActivity actActivity) {
        this.actActivity = actActivity;
    }
}
