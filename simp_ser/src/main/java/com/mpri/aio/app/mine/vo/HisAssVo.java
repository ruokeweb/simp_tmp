package com.mpri.aio.app.mine.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class HisAssVo {
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date createDate;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
