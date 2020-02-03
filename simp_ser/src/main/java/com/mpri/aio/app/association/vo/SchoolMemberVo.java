package com.mpri.aio.app.association.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 校友会下校友列表
 */
public class SchoolMemberVo {
    private String id;
    private String virtualName;
    private String virtualPhoto;
    private String specialty;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date startdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVirtualName() {
        return virtualName;
    }

    public void setVirtualName(String virtualName) {
        this.virtualName = virtualName;
    }

    public String getVirtualPhoto() {
        return virtualPhoto;
    }

    public void setVirtualPhoto(String virtualPhoto) {
        this.virtualPhoto = virtualPhoto;
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
}
