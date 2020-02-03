package com.mpri.aio.donation.model;

import com.mpri.aio.base.model.DataEntity;

/**
 * @Description: 捐赠证书模板表
 * @Author: lzq
 * @project smmp
 * @CreateDate: Wed Feb 20 13:56:10 CST 2019
 * @Version: v_1.2
 */
public class DonTemplate extends DataEntity<DonTemplate> {

    private static final long serialVersionUID = 1550642142491L;

    private String name;
    private String description;
    private String url;
    private String content;
    private Integer startAmount;
    private Integer endAmount;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStartAmount() {
        return this.startAmount;
    }

    public void setStartAmount(Integer startAmount) {
        this.startAmount = startAmount;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEndAmount() {
        return this.endAmount;
    }

    public void setEndAmount(Integer endAmount) {
        this.endAmount = endAmount;
    }

}
