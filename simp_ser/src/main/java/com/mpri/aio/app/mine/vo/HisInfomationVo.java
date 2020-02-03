package com.mpri.aio.app.mine.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.app.act.vo.ActListVo;

import java.util.Date;

public class HisInfomationVo {
    private String id;//校友id
    private String virtualName;//虚拟名称
    private String virtualPhoto;//虚拟图像
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date startdate;//入学时间
    private String eduRecord;//学历
    private String college;//学院
    private String series;//系
    private String specialty;//专业
    private String classes;//班级
    private ActListVo actListVo;//他的活动
    private HisDonVo hisDonVo;//他的捐赠
    private HisAssVo hisAssVo;//他的校友会

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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getEduRecord() {
        return eduRecord;
    }

    public void setEduRecord(String eduRecord) {
        this.eduRecord = eduRecord;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
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

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public ActListVo getActListVo() {
        return actListVo;
    }

    public void setActListVo(ActListVo actListVo) {
        this.actListVo = actListVo;
    }

    public HisDonVo getHisDonVo() {
        return hisDonVo;
    }

    public void setHisDonVo(HisDonVo hisDonVo) {
        this.hisDonVo = hisDonVo;
    }
    public HisAssVo getHisAssVo() {
        return hisAssVo;
    }

    public void setHisAssVo(HisAssVo hisAssVo) {
        this.hisAssVo = hisAssVo;
    }
}
