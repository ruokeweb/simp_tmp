package com.mpri.aio.app.act.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 活动列表
 */
public class ActListVo {

    private String id;
    private String image;
    private String name;
    private String assoId;
    private String assoName;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date startDate;
    private String status;
    private Integer readyNo;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssoId() {
        return assoId;
    }

    public void setAssoId(String assoId) {
        this.assoId = assoId;
    }

    public String getAssoName() {
        return assoName;
    }

    public void setAssoName(String assoName) {
        this.assoName = assoName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getReadyNo() {
        return readyNo;
    }

    public void setReadyNo(Integer readyNo) {
        this.readyNo = readyNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
