<template>
    <view class="con">
        <view class="uni-list" v-if="socialList.length >0">
            <view v-for="(value, key) in socialList" :key="key" @click="goDetail(value)">
                <view class="uni-list-cell" hover-class="uni-list-cell-hover">
                    <view class="uni-left">
                        <view class="con-left">
                            <!-- <view class="button" :class="value.isDefault === true ? 'button default': 'button' " @click="setDefault(value)">默认</view> -->
                        </view>
                        <view class="right-info">
                            <view class="info-main">{{value.name}}</view>
                            <view class="uni-text">{{value.position}}</view>
                        </view>
                    </view>
                    <view class="uni-right">
                        <text class="uni-edit" >编辑</text>
                    </view>
                </view>
            </view>
        </view>
        <view v-else class="uni-nodata">
            暂无数据
        </view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="goDetail()">新增社会经历</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import {
        mapState
    } from 'vuex';
    export default {
        data() {
            return {
                socialList: []
            }
        },
        onShow() {
            this.socialList = [];
            this.getSocialList();
        },
        methods: {
            getSocialList() {
                request.http({
                    url: '/app/social/getSocials',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.socialList = res.data.data;
                            /*  datas.forEach((d) => {
                                  d["cnSex"] = loadcache.getDictByvalue(d.sex, app.DICT_TYPECODE.SEX)
                                      .label;
                                  this.socialList.push(d);
                              }); */
                        }
                    },
                    error: (res) => {}
                });
            },
            setDefault(value) {
                //具体开发时再处理
                console.log(value);
                value.isDefault = !value.isDefault;
            },
            goDetail(value) {
                var url = '';
                if (value == undefined) {
                    url = '/pages_mine/infomation/social/socialDetail';
                } else {
                    url = '/pages_mine/infomation/social/socialDetail?id=' + encodeURIComponent(JSON.stringify(value.id));
                }

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
