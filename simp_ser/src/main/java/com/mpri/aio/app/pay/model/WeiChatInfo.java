package com.mpri.aio.app.pay.model;
/**
* desc
* @author lzq
* @date 2018年9月5日 - 下午3:25:56
*/
public class WeiChatInfo
{

    String openId;
    String nickName;
    String gender;
    String city;
    String province;
    String country;
    String avatarUrl;
    String unionId;
    String formId;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getOpenId()
    {
        return openId;
    }
    public void setOpenId(String openId)
    {
        this.openId = openId;
    }
    public String getNickName()
    {
        return nickName;
    }
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    public String getGender()
    {
        return gender;
    }
    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getProvince()
    {
        return province;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public String getAvatarUrl()
    {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }
    public String getUnionId()
    {
        return unionId;
    }
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
}
