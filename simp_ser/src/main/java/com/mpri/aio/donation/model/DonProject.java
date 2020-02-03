package com.mpri.aio.donation.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Description: 捐赠项目
 * @Author: lzq
 * @project simp
 * @CreateDate: Mon Feb 18 16:24:54 CST 2019
 * @Version: v_1.2
 */
public class DonProject extends DataEntity<DonProject> {

    private static final long serialVersionUID = 1550478269798L;
    @Excel(name = "捐赠项目名称")
    private String name;
    private String secondName;
    private String pic;
    private String type;
    private String master;
    private String phone;
    private String email;
    private Double targetMoney;
    private Double gotMoney;
    private String defaultMoney;
    private Integer donatingNum;
    private String summary;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startdate; 
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date enddate;
    private Integer sort;
    private String status;
    private String topic;

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setDefaultMoney(String defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getDefaultMoney() {
        return defaultMoney;
    }

    public Double getGotMoney() {
        return gotMoney;
    }

    public void setGotMoney(Double gotMoney) {
        this.gotMoney = gotMoney;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Integer getDonatingNum() {
        return donatingNum;
    }

    public void setDonatingNum(Integer donatingNum) {
        this.donatingNum = donatingNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
