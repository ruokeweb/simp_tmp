package com.mpri.aio.donation.model;

import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;

/**
 * @Description: 一起捐记录——Controller
 * @Author: lzq
 * @project simp
 * @CreateDate: Mon May 27 17:54:56 CST 2019
 * @Version: v_1.2
 */
public class DonProjectTogether extends DataEntity<DonProjectTogether> {

    private static final long serialVersionUID = 1550478269798L;

    private String projectId;
    private String userId;
    private String name;
    private String summary;
    private Double targetMoney;
    private Double gotMoney;
    private String status;
    private String personNum;

    private DonProject donProject;
    private SmSchoolmate smSchoolmate;


    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public SmSchoolmate getSmSchoolmate() {
        return smSchoolmate;
    }

    public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
        this.smSchoolmate = smSchoolmate;
    }

    public DonProject getDonProject() {
        return donProject;
    }

    public void setDonProject(DonProject donProject) {
        this.donProject = donProject;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(Double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Double getGotMoney() {
        return gotMoney;
    }

    public void setGotMoney(Double gotMoney) {
        this.gotMoney = gotMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
