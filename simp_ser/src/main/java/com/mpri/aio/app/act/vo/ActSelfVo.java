package com.mpri.aio.app.act.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActSelfVo {
    private String id;
    private String name;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date actDate;
    private Integer readyNo;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;
    private String truePhoto;
    private String userId;
    private String userName;
    private String content;
    private Integer limitNo;
    private String  virtualName;
    private String virtualPhoto;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getActDate() {
        return actDate;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
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

    public String getTruePhoto() {
        return truePhoto;
    }

    public void setTruePhoto(String truePhoto) {
        this.truePhoto = truePhoto;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLimitNo() {
        return limitNo;
    }

    public void setLimitNo(Integer limitNo) {
        this.limitNo = limitNo;
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
}
