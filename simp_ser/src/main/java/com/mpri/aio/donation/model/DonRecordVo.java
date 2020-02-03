package com.mpri.aio.donation.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.math.BigDecimal;

public class DonRecordVo {
	
	private int order;
	
    @Excel(name ="项目名称" ,width = 40)
    private String name ;
    @Excel(name ="合计捐赠金额" ,width = 20)
    private BigDecimal money;
    @Excel(name ="合计捐赠人数",width = 20)
    private int countSum;
    @Excel(name ="所选日期捐赠金额",width = 20)
    private BigDecimal dayMoney;
    @Excel(name ="所选日期捐赠人数",width = 20)
    private int dayCount;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }



    public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getCountSum() {
        return countSum;
    }

    public void setCountSum(int countSum) {
        this.countSum = countSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(BigDecimal dayMoney) {
        this.dayMoney = dayMoney;
    }

    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

}
