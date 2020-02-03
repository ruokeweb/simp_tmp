package com.mpri.aio.webber.vo;

public class DonationVo {
    private String orderId;//订单流水号
    private String notify_url;//捐赠之后的回调

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
