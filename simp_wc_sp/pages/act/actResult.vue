<template>
    <!-- 报名成功-->
    <view class="content">
        <view v-if="hasSuccess" class="act_scuccess">
            <view style="text-align:center;margin-top:50rpx;">
                <text class="l_title">恭喜您报名成功</text>
            </view>
            <view class="input-row" style="margin-top:100upx;">
                <button type="primary" class="primary btn_dark" open-type="share">活动分享</button>
                <view style="margin-top: 50upx;">
                    <button type="primary" class="primary btn_center" @click="myAct()">我参与的活动</button>
                </view>
                <view style="margin-top: 50upx;">
                    <button type="primary" class="primary btn_line" @click="goIndex()">返回首页</button>
                </view>
            </view>
        </view>
        <view v-if="!hasSuccess" class="act_scuccess">
            <view style="text-align:center;margin-top:50rpx;">
                <text class="l_title">报名失败</text>
            </view>
            <view class="input-row" style="margin-top:100upx;">
                <view style="margin-top: 50upx;">
                    <button type="primary" class="primary btn_line" @click="goIndex()">返回首页</button>
                </view>
            </view>
        </view>
        <view>
            <view class="act_h"></view>
            <view class="cat_body">
                活动推荐
            </view>
            <view class="act_h"></view>
            <view class="list" id="list">
                <view v-for="(value, key) in actlist" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.image!=null&&value.image!=''" :src="imagePath + value.image" mode="aspectFill"></image>
                            <image v-if="value.image==null||value.image==''" src="/static/img/act/act_default.png" mode="aspectFill"></image>
                            <view v-if="value.status=='DONE'" class="end">
                                已结束
                            </view>
                            <view v-if="value.status=='DOING'" class="doing">
                                进行中
                            </view>
                            <view v-if="value.status=='READAY'" class="readay">
                                预备中
                            </view>
                        </view>
                        <view class="sch_cont">
                            <view class="title">
                                {{ value.name }}
                            </view>
                            <view class="date">
                                {{value.startDate}}至{{value.endDate}}
                            </view>
                            <view class="bm">
                                <view class="xy">
                                    · {{value.assoName}}
                                </view>
                                <view class="people">
                                    <text>{{null!=value.readyNo?value.readyNo:0}}</text>人已报名
                                </view>
                            </view>
                        </view>
                    </view>
                    <view class="activity_h"></view>
                </view>

                <view class="foot">
                    <view class="more" @click="goMore()">
                        查看更多
                    </view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import '@/static/css/index.css'
    import store from '@/store/index.js';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {},
        data() {
            return {
                id: '',
                actlist: [],
                reload: false,
                hasSuccess: true,
                image: '', //活动首图
                imagePath: app.imagePath,
                actPageNo: 1,
                pageSize: 3
            }
        },
        computed: {
            ...mapState(['hasLogin','userInfo'])
        },
        onLoad(event) {
            // this.isLogin = this.hasLogin;
            const payload = event.id || event.payload;
            try {
                this.id = JSON.parse(decodeURIComponent(payload));
            } catch (error) {
                this.id = JSON.parse(payload);
            };

            const image = event.image || event.payload;
            try {
                this.image = JSON.parse(decodeURIComponent(image));
            } catch (error) {
                this.image = JSON.parse(image);
            };
            const hasSuccess = event.hasSuccess || event.payload;
            var data = 0;
            try {
                data = JSON.parse(decodeURIComponent(hasSuccess));
            } catch (error) {
                data = JSON.parse(hasSuccess);
            };

            if (data == 1) {
                this.hasSuccess = false;
            } else {
                this.hasSuccess = true;
            }
        },
        onShow() {
            //this.isLogin = this.hasLogin;

            if (this.actlist.length == 0) {
                this.getActList();
            }
        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.ACTRESULT_SHARE);
            
            //util.getStatPoint('click','EVENT_DESCRIBE_36');
            var name = '';
            if (this.userInfo.name != undefined || this.userInfo.name != null) {
                name = this.userInfo.name;
            }
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                name = this.userInfo.sysUser.virtualName;
            }
            if (res.from === 'button') { // 来自页面内分享按钮
                console.log(res.target)
            }
            var truePath = encodeURIComponent(JSON.stringify('/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(this.id))));
            var img =this.imagePath + this.image;
            if(this.image ==null||this.image==''){
              img='/static/img/act/act_default.png ';
            }
            return {
                title: name + '邀请你一起参加母校活动，赶快去报名吧',
                path: '/pages/tabbar/index/index?truePath='+truePath,
                imageUrl: img
            }
        },
        methods: {

            getActList() {
                request.http({
                    url: '/app/act/hotActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actlist = datas;
                        }
                    },
                    error: (res) => {

                    }
                });

            },

            goDetail(e) {

                uni.navigateTo({
                    url: '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id))
                });
            },
            goMore() {
                uni.switchTab({
                    url: '/pages/tabbar/act/act'
                });

            },
            myAct() {
                uni.navigateTo({
                    url: '/pages_mine/myAct/myAct'
                });
            },
            goAgain() {
                uni.navigateBack()
            },
            goIndex(e) {
                uni.switchTab({
                    url: '/pages/tabbar/index/index'
                })
            }

        }
    }
</script>

<style>
    .act_scuccess {
        padding: 50upx 70upx;
    }

    .cat_body {
        height: 100upx;
        font-size: 36upx;
        padding-top: -80upx;
        margin-left: 30rpx;
        font-weight: bold;
        line-height: 100upx;
    }

    .uni-swiper-msg {
        padding: 0;
    }

    .content .sch_wish swiper {
        font-size: 26upx;
        color: #FFFFFF;
        margin-left: 12upx;
        margin-top: -4upx;
    }

    .navigator-hover {
        background-color: rgba(0, 0, 0, 0);
        opacity: 1;
    }

    .act_h {
        width: 100%;
        height: 4rpx;
        background-color: #EAEAEA;

    }

    button {
        font-size: 36upx !important;
    }

    .content .sch_cont .people {
        width: 126upx;
        font-size: 24upx;
        float: right;
        color: #999999;
    }

    .content .people text {
        color: #ed5849;
    }

    .l_title {
        font-size: 48upx;
    }

    .content .activity .sch_tu .doing {
        width: 230upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity .sch_tu .readay {
        width: 230upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .sch_cont .bm {
        width: 432upx;
    }

    .content .activity .sch_cont {
        width: 432upx;
        margin-top: 0;
    }

    .content .sch_cont .date {
        margin-top: 0upx;
    }

    .content .activity .sch_tu .end {
        width: 230upx;
        height: 40upx;
        background-color: rgba(0, 0, 0, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .btn_center {
        background: #525764 !important;
        border: 2upx solid #000;
    }
</style>
