package com.mpri.aio.chart.vo;

import com.mpri.aio.schoolmate.model.SmSchoolmate;

public class UnitNatureVo {

    private SmSchoolmate smSchoolmate;
    private String  startYearInternal;
    private String  endYearInternal;

    public SmSchoolmate getSmSchoolmate() {
        return smSchoolmate;
    }

    public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
        this.smSchoolmate = smSchoolmate;
    }

    public String getStartYearInternal() {
        return startYearInternal;
    }

    public void setStartYearInternal(String startYearInternal) {
        this.startYearInternal = startYearInternal;
    }

    public String getEndYearInternal() {
        return endYearInternal;
    }

    public void setEndYearInternal(String endYearInternal) {
        this.endYearInternal = endYearInternal;
    }
}
