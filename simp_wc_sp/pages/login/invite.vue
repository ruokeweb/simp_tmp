<template>
    <view class="content">
        <view class="title">赶快邀请好友帮您认证</view>
        <view class="s_title">快速领取校友卡</view>
        <view class="s_line"> </view>
        <view class="card">
            <view class="left">
                <image class="head" v-if="virtualPhoto==null||virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                <image class="head" v-if="virtualPhoto!=null&&virtualPhoto!=''" :src="imagePath + virtualPhoto" mode="aspectFill"></image>
                <text>{{name}}</text>
            </view>
            <view class="right">
                <view>入学时间：{{startdate}}</view>
                <view>学　　历：{{eduRecord}}</view>
                <view>院　　系：{{collage}}{{series}}</view>
                <view>专　　业：{{specialty}}</view>
            </view>
        </view>
        <view class="mid_title">{{list.length}}位校友已帮我认证</view>
        <view class="list">
            <view class="join" v-for="(value, key) in list" :key="key">
                <view class="list-item">
                    <view class="list-item-left">
                        <image class="list-head" v-if="value.virtualPhoto==null ||value.virtualPhoto==''" src="/static/img/man_default.png" alt=""></image>
                        <image class="list-head" v-if="value.virtualPhoto!=null &&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto"
                            alt=""></image>
                    </view>
                    <view class="list-item-right">
                        <text class="name">{{value.name}}</text>
                        <text class="time">{{value.createDate}}</text>
                    </view>
                </view>
            </view>
        </view>
        <view class="input-row" style="margin-top:50upx;">
            <button type="primary" class="primary btn_dark" open-type="share">发给校友</button>
            <button type="primary" class="primary btn_line" @click="gotocode">面对面认证</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    import util from '@/common/util.js';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            multipleLinkage
        },
        data() {
            return {
                name: "",
                startdate: "",
                eduRecord: "",
                collage: "",
                series: "",
                specialty: "",
                virtualPhoto: "",
                createDate: "",
                list: [],
                id: "",
                type: '',
                imagePath: ""
            }
        },
        computed: {
            ...mapState(['userInfo'])
        },
        onShow() {
            this.imagePath = app.imagePath;
            this.getProveList();
            const smInfo = this.userInfo;
            this.id = smInfo.id;
            this.name = smInfo.name;
            this.virtualPhoto = smInfo.truePhoto;
            this.startdate = util.dateUtils.getYearByDate(smInfo.smEducation.startdate);
            this.eduRecord = loadcache.getDictByvalue(smInfo.smEducation.eduRecord, app.DICT_TYPECODE.EDU_RECORD).label;
            // 
            this.collage = smInfo.smEducation.college == null ? "" : (loadcache.getObjFromStorageById(
                app.CACHE_NAME.DEPARTMENTBASE,
                smInfo.smEducation.college)).name;
            this.series = smInfo.smEducation.series == null ? "" : (loadcache.getObjFromStorageById(
                app.CACHE_NAME.DEPARTMENTBASE,
                smInfo.smEducation.series)).name;
            this.specialty = smInfo.smEducation.specialty == null ? "" : (loadcache.getObjFromStorageById(
                app.CACHE_NAME.DEPARTMENTBASE,
                smInfo.smEducation.specialty)).name;
        },
        onShareAppMessage(res) {
            //util.getStatPoint('click','EVENT_DESCRIBE_10');
            expand.saveShare(app.share_code.INVITE_SHARE);
            return {
                title: this.name + '邀请你帮他认证，快去帮忙认证吧！',
                 path: "/pages/login/identifyResult?scene="+encodeURIComponent(this.id),
            }

        },
        methods: {
            gotocode() {
                //util.getStatPoint('click','EVENT_DESCRIBE_11');
                uni.navigateTo({
                    url: "barcode"
                })
            },
            getProveList() {
                request.http({
                    url: '/app/getProveListByStart',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.list = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            }
        }
    }
</script>

<style>
    Page {
        background: #f2f2f2;
    }

    .content {
        padding: 70upx 0;
        padding-bottom: 0;
    }

    .title {
        font-size: 54upx;
        text-align: center;
        color: #4b505d;
    }

    .s_title {
        font-size: 36upx;
        text-align: center;
        color: #4b505d;
        position: relative;
    }

    .s_line {
        width: 102upx;
        height: 4upx;
        background: #fbc351;
        margin: 10upx auto;
    }

    .card {
        width: 90%;
        height: 300upx;
        background: #fff;
        border-radius: 30upx;
        margin: 10upx auto;
        margin-top: 40upx;
    }

    .left {
        width: 36%;
        display: inline-block;
        text-align: center;
        vertical-align: top;
        margin-top: 70upx;
    }

    .left text {
        color: #4b505d;
        font-size: 30upx;
        font-weight: bold;
    }

    .right {
        width: 55%;
        display: inline-block;
        margin-top: 28upx;
        padding-left: 7%;
        padding-top: 32upx;
        border-left: 4upx dashed #eee;
        height: 230upx;
    }

    .right view {
        color: #4b505d;
        font-size: 24upx;
    }

    .mid_title {
        font-size: 36upx;
        border-bottom: 1upx solid rgb(234, 234, 234);
        padding-left: 5vw;
        height: 80upx;
        margin-top: 20upx;
        font-weight: bold;
    }

    .head {
        width: 140upx;
        height: 140upx;
        border-radius: 70upx;
        display: block;
        margin: 0 auto;
    }

    .list-head {
        width: 98upx;
        height: 98upx;
        border-radius: 49upx;
        margin-top: 10upx;
    }

    .list {
        padding: 0 20upx;
        height: 245upx;
        overflow: auto;
    }

    .list-item {
        border-bottom: 1upx solid #eaeaea;
        height: 120upx;
    }

    .list-item-left {
        width: 15%;
        display: inline-block;
    }

    .list-item-right {
        width: 84%;
        display: inline-block;
        vertical-align: top;
        margin-top: 35upx;
    }

    .list-item-right .name {
        margin-left: 10upx;
    }

    .list-item-right .time {
        float: right;
        color: #999999;
    }

    .list-item text {}

    .primary {
        width: 90%;
        margin-bottom: 20upx;
    }
</style>
