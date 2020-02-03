package com.mpri.aio.app.association.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SmEducationVo {
    private String userId;
    private String specialty;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date startdate;
    private String isDefault;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
