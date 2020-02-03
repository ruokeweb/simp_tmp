<template>
    <!-- 值年返校详情-->
    <view class="act_detail">
        <view class="act_head">
            <!-- 顶部活动详情 -->
            <view>
                <view class="list_1">
                    <view class="zk">
                        <view class="jx">{{title}}</view>
                    </view>
                    <view class="zk_two clearfix">
                        <image v-if="virtualPhoto==null||virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                        <image v-if="virtualPhoto!=null&&virtualPhoto!=''" :src="imagePath + virtualPhoto" mode="aspectFill"></image>
                        <view class="container">
                            {{userName==null?'':userName}}
                        </view>
                    </view>
                    <view class="wb">
                        {{content==null?'':content}}
                    </view>
                </view>
                <view class="time">
                    <view class="sj sj_1">已报名人数<text>{{null!=readyNo?readyNo:0}}人</text></view>
                    <view class="sj">报名截止日期<text>{{endDate}}</text></view>
                    <view class="sj sj_3">返校时间<text>{{actDate}}</text></view>
                </view>
            </view>
        </view>
        <view class="part">
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="applay_data clearfix">
                <text>已报名校友</text>
                <view class="apply">
                    {{null!=readyNo?readyNo:0}}人已报名
                </view>
            </view>

        </view>
        <view class="act_con">
            <view class="join" v-for="(value, key) in readyNoList" :key="key">
                <view class="tx" @click="goInfo(value)">
                    <image v-if="value.virtualPhoto==null||value.virtualPhoto==''" src="/static/img/man_default.png"
                        mode="aspectFill"></image>
                    <image v-if="value.virtualPhoto!=null&&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto"
                        mode="aspectFill"></image>
                </view>
                <view class="name">{{value.virtualName==null?'':value.virtualName}}</view>
                <view class="date">
                    {{value.createDate}}
                </view>
            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>
        <view class="act_footer">
            <view class="act_left" @click="goIndex()">
                <button>返回首页</button>
            </view>
            <!-- v-if="status == 'SUCCESS'" -->
            <view class="act_left act2" open-type="share" v-if="status == 'SUCCESS'">
                <button open-type="share">邀请好友</button>
            </view>
            <view class="act_left act3" v-if="!hasEnroll" @click="goEnroll()">
                <button formType="submit">我要报名</button>
            </view>
            <view class="act_left act1" v-if="hasEnroll" @click="isEnroll()">
                <button>我要报名</button>
            </view>

        </view>
    </view>
</template>

<script>
    import util from '@/common/util.js';
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import cache from '@/common/loadcache.js';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';

    export default {
        components: {
            segmentedControl,
            uniLoadMore
        },
        computed: {
            ...mapState(['hasLogin']),
            ...mapState(['userInfo'])
        },
        data() {
            return {
                id: '',
                userName: '',
                virtualPhoto: '',
                title: '',
                content: '',
                endDate: '',
                actDate: '',
                area: '',
                limitNo: 0,
                readyNo: 0,
                readyNoList: [],
                assoName: '',
                items: ['已报名校友'],
                status: "noMore",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                showLoadMore: true,
                hasEnroll: false,
                imagePath: app.imagePath,
                isLogin: false,
                detailflag: false,
                pageNo: 0,
                pageSize: 10,
                openid: '',
                formId: '',
                template_hdks: ""
            }
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo', 'configParams'])
        },
        onReachBottom() {
            this.status = "noMore";
            if (this.detailflag) {
                this.status = "loading";
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/actSelf/getAttendSmBySelf',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize,
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                                setTimeout(() => {
                                    this.showLoadMore = false;
                                }, 500);
                            } else {
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    this.readyNoList.push(d);
                                });
                                this.detailflag = this.pageNo >= res.data.pages ? false : true;
                            }

                        }
                    },
                    error: (res) => {}
                });
            }
        },
        onLoad(event) {
            const payload = event.id || event.payload;
            try {
                this.id = JSON.parse(decodeURIComponent(payload));
            } catch (error) {
                this.id = JSON.parse(payload);
            };
        },
        onShow(event) {
            this.template_hdks = this.configParams.TEMPLATE_IDS.HDKS;
            this.isLogin = this.hasLogin;
            this.hasSign(); //判断是否已经报名
            this.getBackSchoolDetail(this.id);
            if (this.readyNoList.length == 0) {
                this.pageNo = 0;
                this.getBackSchoolEnroll();
            };
            uni.getProvider({
                service: 'oauth',
                success: (res) => {
                    console.log(res.provider)
                    if (~res.provider.indexOf('weixin')) {
                        uni.login({
                            provider: 'weixin',
                            success: (loginRes) => {
                                console.log(JSON.stringify(loginRes));
                                this.code = loginRes.code;
                                console.log(this.code)
                                //获取用户openid
                                request.http({
                                    url: "/app/pay/getInfo",
                                    data: {
                                        'code': this.code
                                    },
                                    header: 'application/x-www-form-urlencoded',
                                    success: (res) => {
                                        this.openid = res.data.data.openId;
                                    },
                                    fail: () => {}
                                })

                            }
                        });
                    }
                }
            });

        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.BACKSCHOOLDETAIL_SHARE);

            var name = '';
            if (this.userInfo.name != undefined || this.userInfo.name != null) {
                name = this.userInfo.name;
            }
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                name = this.userInfo.sysUser.virtualName;
            }
            if (res.from === 'button') { // 来自页面内分享按钮
            }
            var truePath = encodeURIComponent(JSON.stringify('/pages/act/backSchoolDetail?id=' + encodeURIComponent(
                JSON.stringify(this.id))));
            return {
                title: name + '发起了值年返校活动，赶快去报名吧',
                path: '/pages/tabbar/index/index?truePath=' + truePath
            }
        },
        methods: {
            getBackSchoolDetail(e) {
                request.http({
                    url: '/app/actSelf/getSelfDetail',
                    data: {
                        id: this.id
                    },
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.userName = res.data.data.virtualName;
                            this.virtualPhoto = res.data.data.virtualPhoto;
                            this.content = res.data.data.content;
                            this.assoName = res.data.data.assoName;
                            this.endDate = res.data.data.endDate;
                            this.actDate = res.data.data.actDate;
                            this.title = res.data.data.name;
                            this.status = res.data.data.status;
                            if (undefined != res.data.data.readyNo) {
                                this.readyNo = res.data.data.readyNo;
                            }
                            this.limitNo = res.data.data.limitNo
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            getBackSchoolEnroll(e) {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/actSelf/getAttendSmBySelf',
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize,
                        id: this.id
                    },
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                                setTimeout(() => {
                                    this.showLoadMore = false;
                                }, 500);
                            } else {
                                this.readyNoList = res.data.list;

                            }
                            this.detailflag = this.pageNo >= res.data.pages ? false : true;
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            hasSign(e) {
                if (this.isLogin) {
                    request.http({
                        url: '/app/actSelf/getNumByUserId',
                        header: 'application/x-www-form-urlencoded',
                        data: {
                            id: this.id
                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                this.hasEnroll = res.data.data;
                            }
                        },
                        error: (res) => {

                        }
                    })
                }
            },
            goEnroll() {
                // console.log(e);
                // this.formId = e.detail.formId;
                if (this.isLogin) {
                    if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                        let url = '/pages/login/cardstatusAuthing';
                        uni.navigateTo({
                            url: url
                        });
                        this.showpageToast("您的信息还未审核通过，不能报名活动");
                    } else {
                        if (this.getDate() > this.endDate) {
                            this.showpageToast("报名已经结束");
                            return false;
                        }
                        if (this.limitNo > this.readyNo) {
                            var hasSuccess = false;
                            request.http({ //报名接口
                                url: '/app/actSelf/saveSelfContent',
                                header: 'application/x-www-form-urlencoded',
                                data: {
                                    actSelforgId: this.id
                                },
                                success: (res) => {
                                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                        console.log(res.data.data);
                                        hasSuccess = true;
                                        let version = wx.getSystemInfoSync().SDKVersion;
                                        if (util.compareVersion(version, '2.8.2') >= 0) {
                                            wx.requestSubscribeMessage({
                                                tmplIds: [this.template_hdks],
                                                success(res) {},
                                                error(res) {}
                                            });
                                        }
                                        this.showpageToast(res.data.msg);
                                        //报名成功跳转至成功页面
                                        let url = '/pages/act/backSchoolResult?id=' + encodeURIComponent(
                                                JSON.stringify(this.id)) + '&hasSuccess=' +
                                            encodeURIComponent(JSON.stringify(hasSuccess));
                                        uni.reLaunch({
                                            url: url
                                        });

                                    }
                                },
                                error: (res) => {}
                            })
                        } else {
                            this.showpageToast("报名人数已满");
                        }
                    }
                } else {
                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("请先登录");
                }
            },
            goLogin(e) {
                this.showpageToast("请先登录");
            },
            isEnroll() {
                this.showpageToast("请勿重复报名");
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            goInfo(e) {
                if (this.isLogin) {
                    if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                        let url = '/pages/login/cardstatusAuthing';
                        uni.navigateTo({
                            url: url
                        });
                        this.showpageToast("您个人信息没有审核通过，请先去认证！");
                    } else {
                        let url = '/pages_mine/myAssociation/memberDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                        uni.navigateTo({
                            url: url
                        });
                    }
                } else {
                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("请先登录");
                }


            },
            getDate(type) {
                const date = new Date();

                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                let day = date.getDate();

                if (type === 'start') {
                    year = year - 60;
                } else if (type === 'end') {
                    year = year + 2;
                }
                month = month > 9 ? month : '0' + month;;
                day = day > 9 ? day : '0' + day;

                return `${year}-${month}-${day}`;
            },
            goIndex(e) {
                uni.switchTab({
                    url: '/pages/tabbar/index/index'
                })
            },

        },

    }
</script>

<style lang="scss">
    .act_detail .act_head {
        width: 690upx;
        box-shadow: 0px 0px 30px 0px rgba(116, 116, 116, 0.15);
        border-radius: 20upx;
        margin: 25upx auto;
    }

    .act_detail .list_1 {
        padding: 45upx 50upx 35upx 50upx;
        overflow: hidden;
    }

    .act_detail .list_1 image {

        width: 40upx;
        height: 40upx;
        border-radius: 50%;
        float: left;
        display: inline-block;
        overflow: hidden;
    }

    .act_detail .list_1 .zk_two {
        margin-top: 20upx;
    }

    .act_detail .list_1 .container {
        font-size: $uni-font-size-base;
        float: left;
        margin-left: 15upx;
        height: 40upx;
        line-height: 40upx;
    }

    .act_detail .list_1 .container .title {
        font-size: 36upx;
        font-weight: bold;
    }

    .act_detail .container .xy {
        width: 140upx;
        height: 40upx;
        border-radius: 20upx;
        text-align: center;
        color: #4c3915;
        font-size: 24upx;
        line-height: 40upx;
        background-image: linear-gradient(90deg, rgba(243, 231, 200, 0.4) 0%, rgba(222, 196, 144, 0.4) 100%);
        margin-top: 35upx;
    }

    .act_detail .time {
        height: 170upx;
        font-size: 28upx;
        padding: 10upx 50upx;
        letter-spacing: 1upx;
        margin-top: -10upx;
    }

    .act_detail .time text {
        margin-left: 40upx;
        color: #999999;
    }

    .act_detail .time .sj {
        line-height: 50upx;
        font-size: $uni-font-size-sm;
    }

    .act_detail .time .sj_1 text {
        margin-left: 73upx;
    }

    .act_detail .time .sj_3 text {
        margin-left: 99upx;
    }

    .act_detail .up {
        width: 97upx;
        height: 95upx;
        margin: 0 auto;
        overflow: hidden;
        margin-top: 26upx;
    }



    .act_detail .up text {
        color: #c79f63;
        font-size: 28upx;
        float: right;
    }

    .act_detail .zk {
        overflow: hidden;
    }

    .act_detail .zk .jx {
        position: relative;
        padding-left: 20upx;
        font-size: $uni-font-size-lg;
        font-weight: bold;

        &:before {
            position: absolute;
            content: '';
            width: 9upx;
            height: 40upx;
            background-color: #deb051;
            border-radius: 4upx;
            left: 0;
            top: 0;
            bottom: 0;
            margin: auto;
        }
    }

    .act_detail .intro {
        width: 100upx;
        margin-left: 50upx;
        margin-top: 40upx;
        overflow: hidden;
    }



    .act_detail .wb {
        width: 598upx;
        color: #999999;
        margin-top: 20upx;
        line-height: 1.5;
        letter-spacing: 1upx;
    }

    .act_detail .segmented-control {
        box-shadow: none !important;
    }

    .act_detail .segmented-control:after {
        position: absolute;
        content: "";
        width: 100%;
        left: 0;
        bottom: 0;
        height: 1upx;
        background-color: rgba(225, 225, 225, 1);
        -webkit-transform: scale(1, 0.5);
        transform: scale(1, 0.5);
        -webkit-transform-origin: center bottom;
        transform-origin: center bottom;
    }

    .act_detail .line {
        left: 15upx;
    }

    .act_detail .part {
        position: relative;

        .applay_data {
            height: 80upx;
            border-bottom: 1upx solid #f6f6f6;
            padding-left: 30upx;
            line-height: 80upx;
            margin-top: 20upx;
            padding-right: 30upx;


            text {
                display: inline-block;
                border-bottom: 6upx solid #272D3D;
                color: #333;
                font-weight: bold;
                font-size: $uni-font-size-base;
            }

            .apply {
                float: right;
                font-size: $uni-font-size-base;
                color: #999999;
                height: 80upx;
                line-height: 80upx;
            }
        }

    }





    .act_detail .join {

        margin: 0 30upx;
        position: relative;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 20upx 0;
    }

    .act_detail .join:after {
        position: absolute;
        content: " ";
        width: 100%;
        left: 0;
        bottom: 0;
        height: 1upx;
        background-color: rgba(225, 225, 225, 1);
        -webkit-transform: scale(1, 0.5);
        transform: scale(1, 0.5);
        -webkit-transform-origin: center bottom;
        transform-origin: center bottom;
    }

    .act_detail .tx {
        width: 73upx;
        height: 73upx;
        border-radius: 50%;

    }

    .act_detail .tx image {
        width: 100%;
        height: 100%;
        border-radius: 50%;

    }

    .act_detail .name {
        width: calc(100% - 353upx);
        padding-left: 20upx;
        color: #333333;
    }

    .act_detail .date {

        font-size: $uni-font-size-sm;
        color: #999999;
        width: 260upx;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 10upx 0;
        font-size: 30upx;
        color: #CCCCCC;
    }

    .act_detail .act_footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 98upx;
        border-top: 1px solid #262f43;
        z-index: 888;
        display: flex;
        justify-content: space-around;

    }

    .act_detail .act_con {
        overflow-y: scroll;
        padding-bottom: 100upx;
        margin-top: 40upx;
    }

    .act_detail .zk text {
        /* float: left; */
        font-size: 36upx;
        font-weight: bold;
        margin-top: -10upx;
        margin-left: 20upx;
    }

    image {
        width: 73upx;
        height: 73upx;
        border-radius: 50%;
        float: right;
        width: 320px;
        display: inline-block;
        overflow: hidden;
    }

    .act_left {
        width: 100%;
    }

    .act_left button {
        background-color: white;
    }

    .act_left.act2 button {
        background-color: #525764;
        color: #FFFFFF;
    }

    .act_left.act3 button {
        /* width: 34%; */
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_left.act1 button {
        /*        width: 34%; */
        background-color: #A8A8A8;
        color: #FFFFFF;
    }

    .act_footer button:after {
        border-radius: 0;
    }

    .data {
        height: 100upx;
        border-bottom: 1upx solid #f6f6f6;
        padding-left: 30upx;
    }

    .data text {
        height: 67upx;
        display: inline-block;
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: $uni-font-size-base;
        margin-top: 28upx;
    }

    .btn {
        width: 0rpx;
        height: 0rpx;
        margin: 0;
        padding: 0;
        border-radius: 0;
        position: absolute;
        background: rgba(0, 155, 0, 0.5);
        top: 0;
    }

    .vv {
        width: 200rpx;
        height: 200rpx;
        background: rgba(0, 155, 0, 0.5);
        position: fixed;
        top: 0;
    }

    button::after {
        border: none;
    }

    .act_footer button {
        width: 100%;
        float: initial;
    }
</style>
