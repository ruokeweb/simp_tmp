package com.mpri.aio.app.mine.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class HisDonVo {
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date time;
    private BigDecimal money;
    private String name;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
