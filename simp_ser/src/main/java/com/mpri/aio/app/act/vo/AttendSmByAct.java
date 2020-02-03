package com.mpri.aio.app.act.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 已活动报名列表
 */
public class AttendSmByAct {
    private String id;
    private String truePhoto;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private String createDate;
    private String name;
    private String  virtualName;
    private String virtualPhoto;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTruePhoto() {
        return truePhoto;
    }

    public void setTruePhoto(String truePhoto) {
        this.truePhoto = truePhoto;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
