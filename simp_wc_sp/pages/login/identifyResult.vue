<template>
    <view class="content">
        <view class="s_title">Ta还未通过校友认证，请助他一臂之力！</view>
        <view class="s_line"> </view>
        <view class="card">
            <view class="left">
                <image class="head" v-if="photo==null || photo==''" src="/static/img/man_default.png" ></image>
                <image class="head" v-if="photo!=null&& photo!=''" :src="imagePath + photo"></image>
                <text>{{name}}</text>
            </view>
            <view class="right">
                <view>入学时间：{{startdate}}</view>
                <view>学　　历：{{eduRecord}}</view>
                <view>院　　系：{{collage}}{{series}}</view>
                <view>专　　业：{{specialty}}</view>
            </view>
        </view>
        <view class="mid_title">{{list.length}}位校友已帮Ta认证</view>
        <view class="list">
            <view class="list-item" v-for="(value, key) in list" :key="key">
                <view class="list-item-left">
                    <image class="list-head" v-if="value.truePhoto==null || value.truePhoto==''" src="/static/img/man_default.png" alt=""></image>
                    <image class="list-head" v-if="value.truePhoto!=null && value.truePhoto!=''" :src="imagePath + value.truePhoto" alt=""></image>
                </view>
                <view class="list-item-right">
                    <text class="name">{{value.name}}</text>
                    <text class="time">{{value.createDate}}</text>
                </view>
            </view>

        </view>
        <view class="input-row" style="margin-top:50upx;">
            <button v-if="IsShowBut==true" type="primary" class="primary btn_dark" @click="togglePopup('middle-con')" :disabled="disabled">{{showContent}}</button>
            <button type="primary" class="primary btn_white" @click="gotoindex()">返回首页</button>
        </view>

        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="togglePopup('')">
            <view class="" style="width:100%;height:100%;">
                <view>
                    <image class="card_bg" src="../../static/login/ident-bg.png" style="" mode="widthFix"></image>
                    <image class="card_pop" style="" mode="widthFix" src="../../static/img/card-anymous.png"></image>
                </view>
                <view class="title_main">认证成功</view>
                <view class="title_sub">感谢您对Ta帮助</view>
                <button type="primary" class="primary btn_dark inner" @click="gotoindex()">返回首页</button>
            </view>
        </uni-popup>
    </view>
</template>

<script>
    import uniPopup from '@/components/uni-popup/uni-popup.vue'
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    import util from '@/common/util.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            uniPopup
        },
        onLoad(options) {
            this.id = decodeURIComponent(options.scene);
            console.log(this.id);
            if(this.id == this.userInfo.id){
                this.IsShowBut = false;
            }
        },
        onShow() {
            this.judgeProveSm();
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo'])
        },
        data() {
            return {
                id: "",
                smInfo: {},
                startdate: "",
                name: "",
                eduRecord: "",
                collage: "",
                series: "",
                specialty: "",
                photo:"",
                createDate: "",
                list: [],
                type: '',
                disabled: false,
                showContent: '认证',
                IsShowBut: true ,
                imagePath: app.imagePath
            }
        },
        methods: {
            togglePopup(type) {
                this.disabled = true;
                //util.getStatPoint('click','EVENT_DESCRIBE_12');
                //认证
                request.http({
                    url: '/app/helpProve',
                    method: "POST",
                    data: {
                        'startUserId': this.smInfo.userId,
                        'startUsername': this.smInfo.sysUser.username,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log(res.data);
                            this.showContent = "你已经认证过该校友了";
                        }
                    },
                    error: (res) => {

                    }
                });
                this.type = type
            },
            gotoindex() {
                uni.switchTab({
                    url: "/pages/tabbar/index/index"
                })
            },
            judgeProveSm() {
                if (!this.hasLogin) {
                    uni.showToast({
                        icon: 'none',
                        title: '请先登录'
                    });
                    uni.navigateTo({
                        url: '/pages/login/login'
                    });
                } else if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                    request.http({
                        url: '/app/index/getMyCard',
                        method: "POST",
                        data: {},
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                uni.navigateTo({
                                    url: res.data.data.pages + "?resultContent=" +
                                        encodeURIComponent(
                                            res.data.data.resultContent)
                                });
                            } else {
                                uni.navigateTo({
                                    url: "/pages/login/cardstatusAuthing"
                                });
                            }
                        },
                        error: (res) => {

                        }
                    });
                } else {
                    request.http({
                        url: '/app/getProvedSmByStart',
                        method: "POST",
                        header: 'application/x-www-form-urlencoded',
                        data: {
                            'id': this.id
                        },
                        success: (res) => {
                            console.log(res.data);
                            this.smInfo = res.data.data;
                            const smInfo = res.data.data;
                            this.id = smInfo.id;
                            this.name = smInfo.name;
                            this.photo = smInfo.truePhoto;
                            this.startdate = util.dateUtils.getYearByDate(smInfo.smEducation.startdate);
                            this.eduRecord = loadcache.getDictByvalue(smInfo.smEducation.eduRecord, app
                                .DICT_TYPECODE.EDU_RECORD).label;
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
                            this.list = smInfo.proveSchoolmates;
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {} else {
                                this.disabled = true;
                                this.showContent = "你已经认证过该校友了";
                            }
                        },
                        error: (res) => {

                        }
                    });
                }
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
        margin-top: 60upx;
    }

    .btn_white {
        color: #000000;
        background-color: #FFFFFF;
    }

    .uni-popup {
        width: 460upx;
        height: 600upx;
        padding: 0 !important;
        border-radius: 20upx !important;
    }

    .title_main {
        color: #e8b751;
        font-size: 36upx;
        text-align: center;
        margin-top: 120upx;
        letter-spacing: 1upx;
    }

    .title_sub {
        color: #999;
        text-align: center;
        position: relative;
        top: -10upx;
    }

    .primary.inner {
        height: 60upx;
        line-height: 60upx;
        margin-top: 30upx;
        font-size: 30upx;
        width: 60%;
    }

    .card_bg {
        border-radius: 20upx 20upx 0 0;
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .card_pop {
        display: block;
        width: 70%;
        margin: 50upx auto;
    }
    .primary.btn_white{
        color: #000000;
        background-color: #FFFFFF !important;
    }
</style>
