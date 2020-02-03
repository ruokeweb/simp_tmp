<template>
    <view class="con">
        <view class="uni-list" v-if="addrList.length >0">
            <view v-for="(value, key) in addrList" :key="key">
                <view class="uni-list-cell" hover-class="uni-list-cell-hover">
                    <view class="uni-left">
                        <view class="con-left">
                            <view class="button" :class="value.isDefault === 'IS_DEFAULT' ? 'button default': 'button' "
                                @click="setDefault(value)">默认</view>
                        </view>
                        <view class="right-info">
                            <view class="info-main">{{value.country}} {{value.province}} {{value.city}}{{value.district}}</view>
                            <view class="uni-text">{{null != value.detail? value.detail:""}}</view>
                        </view>
                    </view>
                    <view class="uni-right">
                        <text class="uni-edit" @click="goDetail(value.id)">编辑</text>
                    </view>
                </view>
            </view>
        </view>
        <view v-else class="uni-nodata">
            暂无数据
        </view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="goDetail(0)">新增联系地址</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import loadcache from '@/common/loadcache.js';
    export default {
        data() {
            return {
                addrList: []
            }
        },
        onShow() {
            this.getAddressList();
        },
        methods: {
            getAddressList() {
                request.http({
                    url: "/app/address/loadByPage",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageNo: 1,
                        pageSize: 30
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.addrList = res.data.data.list;
                            for (let i = 0; i < this.addrList.length; i++) {
                                if (null != this.addrList[i].country && this.addrList[i].country !=
                                    "") {
                                    this.addrList[i].country = loadcache.getObjFromStorageById(app.CACHE_NAME
                                        .AREABASE,
                                        this.addrList[i].country);
                                }
                                if (null != this.addrList[i].city && this.addrList[i].city !=
                                    "") {
                                    this.addrList[i].city = loadcache.getObjFromStorageById(app.CACHE_NAME
                                        .AREABASE,
                                        this.addrList[i].city);
                                }
                                if (null != this.addrList[i].province && this.addrList[i].province !=
                                    "") {
                                    this.addrList[i].province = loadcache.getObjFromStorageById(app
                                        .CACHE_NAME
                                        .AREABASE,
                                        this.addrList[i].province);
                                }
                                if (null != this.addrList[i].district && this.addrList[i].district !=
                                    "") {
                                    this.addrList[i].district = loadcache.getObjFromStorageById(app
                                        .CACHE_NAME
                                        .AREABASE,
                                        this.addrList[i].district);
                                }
                            }
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            setDefault(value) {
                if (value.isDefault == "IS_DEFAULT") {
                    this.showpageToast("已经是默认通讯地址");
                    return false;
                } else {
                    uni.showModal({
                        title: '',
                        content: '确定设置为默认通讯地址？',
                        success: (res) => {
                            if (res.confirm) {
                                let defaultStr = "IS_DEFAULT";
                                request.http({
                                    header: 'application/x-www-form-urlencoded',
                                    url: "/app/address/setDefault",
                                    data: {
                                        id: value.id,
                                        isDefault: defaultStr,
                                        pageNo: 1,
                                        pageSize: 30
                                    },
                                    success: (res) => {
                                        //  let list = this.setTime(res.data.data);
                                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                            this.addrList = res.data.data.list;
                                            for (let i = 0; i < this.addrList.length; i++) {
                                                if (null != this.addrList[i].country && this.addrList[
                                                        i].country !=
                                                    "") {
                                                    this.addrList[i].country = loadcache.getObjFromStorageById(
                                                        app.CACHE_NAME
                                                        .AREABASE,
                                                        this.addrList[i].country);
                                                }
                                                if (null != this.addrList[i].city && this.addrList[
                                                        i].city !=
                                                    "") {
                                                    this.addrList[i].city = loadcache.getObjFromStorageById(
                                                        app.CACHE_NAME
                                                        .AREABASE,
                                                        this.addrList[i].city);
                                                }
                                                if (null != this.addrList[i].province && this.addrList[
                                                        i].province !=
                                                    "") {
                                                    this.addrList[i].province = loadcache.getObjFromStorageById(
                                                        app
                                                        .CACHE_NAME
                                                        .AREABASE,
                                                        this.addrList[i].province);
                                                }
                                                if (null != this.addrList[i].district && this.addrList[
                                                        i].district !=
                                                    "") {
                                                    this.addrList[i].district = loadcache.getObjFromStorageById(
                                                        app
                                                        .CACHE_NAME
                                                        .AREABASE,
                                                        this.addrList[i].district);
                                                }
                                            }
                                            this.showpageToast("设置成功");
                                        }
                                    },
                                    fail: (data, code) => {
                                        console.log(data);
                                    }
                                })
                            }
                        }
                    });

                }
            },
            goDetail(id) {
                var url = '/pages_mine/infomation/addr/addrDetail?id=' + id;
                uni.navigateTo({
                    url
                });
            }
        }
    }
</script>

<style>
    .uni-list-cell {
        padding: 20upx 0;
    }
/*    .uni-list-cell {
        height: 190upx;
    }

    .uni-list-cell::after {
        left: 0;
    } */

    .uni-left {
        width: 80%;
        float: left;
    }

    .uni-right {
        width: 17%;
        float: right;
        padding-left: 50upx;
        border-left: 1upx solid #e5e5e5;
    }

    .uni-text {
        color: #999;
    }

    .con-left {
        display: inline-block;
        width: 25%;
        position: relative;
        left: 20upx;
        bottom: 50upx;
    }

    .right-info {
        display: inline-block;
        width: 70%;
    }

    .info-main {
        height: 70upx !important;
        overflow: hidden;
        line-height: 1.2;
        padding-top: 20upx;
    }

    .button {
        width: 100upx;
        height: 40upx;
        line-height: 40upx;
        border-radius: 20upx;
        border: solid 2upx rgba(225, 182, 91, 0.4);
        text-align: center;
    }

    .default.button {
        background-image: linear-gradient(0deg,
            rgba(243, 231, 200, 0.4) 0%,
            rgba(222, 196, 144, 0.4) 100%);
        border: none;
    }

    .uni-edit {
        font-size: 30upx;
        color: #999;
    }

    .con {
        height: calc(100vh - 180upx);
        overflow-y: auto;
    }

    .btn-con {
        width: 90vw;
        position: absolute;
        bottom: 70upx;
        left: 5vw;
    }
</style>
