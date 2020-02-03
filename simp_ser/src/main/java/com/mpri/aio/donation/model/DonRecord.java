package com.mpri.aio.donation.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mpri.aio.base.model.DataEntity;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 捐赠记录
 * @Author: lzq
 * @project smmp
 * @CreateDate: Tue Feb 19 16:08:53 CST 2019
 * @Version: v_1.2
 */
public class DonRecord extends DataEntity<DonRecord> {

    private static final long serialVersionUID = 1550563711188L;

    private String donProjectId;
    private String userId;
    private String togetherId;
    @Excel(name ="捐赠者")
    private String name;
    @Excel(name ="捐赠类型")
    private String donType;
    @ExcelEntity
    private DonProject donProject;
    @Excel(name ="电话")
    private String phone;
    @Excel(name ="邮箱")
    private String email;
    @Excel(name ="币种")
    private String moneyType;
    @Excel(name ="金额")
    private BigDecimal money;
    @Excel(name ="付款方式")
    private String style;
    @Excel(name = "捐物(名称数量)")
    private String goodsName;
    @Excel(name = "捐赠时间" ,format="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date time;
    private String isShow;
    private String isCertificate;
    private String templateId;
    private String isInvoice;
    private String invoiceTitle;
    private String address;
    private String invoiceStatus;
    private String customTemp;
    private String customId;
    private String state;

    private SmSchoolmate smSchoolmate;
    private DonProjectTogether donProjectTogether;

    /**
     * 报表查询时间
     */
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM")
    private Date chartStartDate;
    @DateTimeFormat(pattern = "yyyy-MM")
    @JsonFormat(pattern = "yyyy-MM")
    private Date chartEndDate;

    /**
     * 报表查询项目类型
     * @return
     */
    private String projectType;




    public Date getChartStartDate() {
        return chartStartDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM")
    public void setChartStartDate(Date chartStartDate) {
        this.chartStartDate = chartStartDate;
    }
    public Date getChartEndDate() {
        return chartEndDate;
    }
    @DateTimeFormat(pattern = "yyyy-MM")
    public void setChartEndDate(Date chartEndDate) {
        this.chartEndDate = chartEndDate;
    }


    public DonProjectTogether getDonProjectTogether() {
        return donProjectTogether;
    }

    public void setDonProjectTogether(DonProjectTogether donProjectTogether) {
        this.donProjectTogether = donProjectTogether;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public DonProject getDonProject() {
        return donProject;
    }

    public void setDonProject(DonProject donProject) {
        this.donProject = donProject;
    }

    public SmSchoolmate getSmSchoolmate() {
        return smSchoolmate;
    }

    public void setSmSchoolmate(SmSchoolmate smSchoolmate) {
        this.smSchoolmate = smSchoolmate;
    }

    public String getTogetherId() {
        return togetherId;
    }

    public void setTogetherId(String togetherId) {
        this.togetherId = togetherId;
    }

    public String getDonType() {
        return this.donType;
    }

    public void setDonType(String donType) {
        this.donType = donType;
    }

    public String getIsInvoice() {
        return this.isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIsShow() {
        return this.isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public BigDecimal getMoney() {
        return this.money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getTime() {
        return this.time;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoodsName() {
        return this.goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMoneyType() {
        return this.moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomId() {
        return this.customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getInvoiceStatus() {
        return this.invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomTemp() {
        return this.customTemp;
    }

    public void setCustomTemp(String customTemp) {
        this.customTemp = customTemp;
    }

    public String getInvoiceTitle() {
        return this.invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getDonProjectId() {
        return this.donProjectId;
    }

    public void setDonProjectId(String donProjectId) {
        this.donProjectId = donProjectId;
    }

    public String getIsCertificate() {
        return this.isCertificate;
    }

    public void setIsCertificate(String isCertificate) {
        this.isCertificate = isCertificate;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
