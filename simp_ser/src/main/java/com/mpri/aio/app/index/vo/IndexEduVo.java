package com.mpri.aio.app.index.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class IndexEduVo {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date startdate;//入学时间
    private String series;//系
    private String specialty;//专业

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
