<template>
    <!-- 捐赠记录详情页 -->
    <view class="act_detail content">
        <view>
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view class="flex-item" @click="onClickItem(0)">
                    <text :class="cur==0 ? 'on' : '' ">回馈公示</text>
                </view>
                <view v-show="is_don_team" class="flex-item" @click="onClickItem(1)">
                    <text :class="cur==1 ? 'on' : '' ">共同献爱心</text>
                </view>
            </view>
            <view class="list" id="list">
                <view class="message">
                    <view v-if="cur==0" class="tag">
                        <view class="messLeft">
                            个人回馈共<text>{{myDonList.perNum}}次</text>
                        </view>
                        <view class="messRight">
                            已回馈<text>{{myDonList.perMoneyNum}}元</text>
                        </view>
                    </view>
                    <view v-if="cur==1" class="tag">
                        <view class="messLeft">
                            共同献爱心共<text>{{myDonList.temNum}}次</text>
                        </view>
                        <view class="messRight">
                            已回馈<text>{{myDonList.temMoneyNum}}元</text>
                        </view>
                    </view>
                </view>
                <view v-show="cur === 0" v-for="(value, key) in donPersons" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu tx">
                            <image v-if="value.smSchoolmate.truePhoto==null || value.isShow == is_no || value.smSchoolmate.truePhoto == ''"
                                src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto!=null && value.isShow != is_no && value.smSchoolmate.truePhoto != ''"
                                :src="imagePath+value.smSchoolmate.truePhoto" mode="aspectFill"></image>
                        </view>
                        <view class="name">
                            {{value.name}}
                        </view>
                        <view class="target target_1">
                            回馈<text>{{null == value.money? 0 :value.money}}元</text>
                        </view>
                        <view class="date">
                            {{value.time}}
                        </view>
                    </view>
                </view>
                <view v-show="cur === 1&&is_don_team" v-for="(value, key) in teamDonProList" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.smSchoolmate.truePhoto==undefined  ||value.smSchoolmate.truePhoto == null || value.smSchoolmate.truePhoto == ''"
                                src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto!=null && value.smSchoolmate.truePhoto != '' "
                                :src="imagePath + value.smSchoolmate.truePhoto" mode="aspectFill"></image>
                            <!-- <image :src="value.pic" mode="aspectFill"></image> -->
                        </view>
                        <view class="sch_cont">
                            <view class="l-title">
                                {{ (null!= value.smSchoolmate)?(null!=value.smSchoolmate.name?value.smSchoolmate.name:"校友" ):"校友" }}发起“{{value.name}}”一起参与捐赠
                            </view>
                            <view class="bm">
                                <view class="target">
                                    已参与人数 <text>{{null == value.personNum? 1:value.personNum}}人</text>
                                </view>
                                <view class="got">
                                    已回馈额度 <text>{{null ==value.gotMoney? 0 : value.gotMoney }}元</text>
                                </view>
                            </view>
                        </view>
                    </view>
                </view>
                <uni-load-more :status="status" :contentText="contentText" color="#999999" />
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            segmentedControl,
            uniLoadMore
        },
        computed: {
            ...mapState(['hasLogin','userInfo','configParams'])
        },
        data() {
            return {
                id: '',
                items: ['个人捐', '共同献爱心'],
                loadingText: '',
                percent: 60,
                cur: 0,
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                donPersons: [],
                teamDonProList: [],
                myDonList: {},
                pagesNum: 10,
                moreflag: false,
                morePages: 0,
                moreTempflag: false,
                moreTempPages: 0,
                imagePath: app.imagePath,
                is_no: app.is_str.is_no,
                is_don_project: "",
                is_don_team: ""
            }
        },
        onLoad(options) {
            //当前项目的id
            this.loadMoreText = "加载更多";
            const payload = options.cur || options.payload;
            // 目前在某些平台参数会被主动 decode，暂时这样处理。
            this.cur = JSON.parse(decodeURIComponent(options.cut));
            this.id = JSON.parse(decodeURIComponent(options.id));
        },
        onReachBottom() {
            //请求(生产环境需要判断是捐赠还是一起捐)
            this.status = "loading";
            //普通捐赠
            if (this.cur === 0 && this.moreflag) {
                setTimeout(() => {
                    this.getDonPersons()
                }, 2000, null);
            } else if (this.cur === 1 && this.moreTempflag) {
                setTimeout(() => {
                    this.getTeamDonProList()
                }, 2000, null);
            } else {
                this.status = "noMore";
            }
        },

        onShow(event) {
            this.is_don_project = this.configParams.is_don_project;
            this.is_don_team = this.configParams.is_don_project;
            if (this.donPersons.length == 0) {
                this.getDonPersons();
            }
            if (this.teamDonProList.length == 0) {
                this.getTeamDonProList()
            }
            this.getMyDonList();
        },
        methods: {
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                }
            },
            getDonPersons() {
                this.morePages = this.morePages + 1;
                request.http({
                    url: "/app/don/getDonRecordByPro",
                    data: {
                        pageNo: this.morePages,
                        pageSize: this.pagesNum,
                        donProjectId: this.id,
                        state: 'NORMAL'
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                this.donPersons.push(d);
                            });
                            this.moreflag = (res.data.pages > (this.morePages) ? true : false);
                            if (!this.moreflag) {
                                this.status = "noMore"
                            }
                        }

                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            getTeamDonProList() {
                this.moreTempPages = this.moreTempPages + 1;
                request.http({
                    url: "/app/don/teamDonRecords",
                    data: {
                        pageNo: this.moreTempPages,
                        pageSize: this.pagesNum,
                        projectId: this.id,
                        state: 'NORMAL'
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                this.teamDonProList.push(d);
                            });
                            this.loadingText = "";
                            this.moreTempflag = (res.data.pages > (this.moreTempPages) ? true : false);
                            if (!this.moreTempflag) {
                                this.status = "noMore"
                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            getMyDonList() {
                request.http({
                    url: "/app/don/myDonList",
                    data: {
                        donProjectId: this.id,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.myDonList = res.data.data;
                            console.log(this.myDonList);
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            goDetail(detail) {
                var url = "";
                if (this.cur == 0) {
                    url = '/pages_mine/myAssociation/memberDetail?id=' + encodeURIComponent(JSON.stringify(detail.userId));
                    if (!this.hasLogin) {
                        uni.showToast({
                            icon: 'none',
                            title: '您没有登录，请先登录！'
                        });
                    } else if (this.userInfo.cardStatus != 'NORMAL') {
                        uni.showToast({
                            icon: 'none',
                            title: '您个人信息没有审核通过，请先去认证！'
                        });
                    } else if (detail.isShow != app.is_str.is_no) {
                        uni.navigateTo({
                            url
                        });
                    }
                } else {
                    url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail.id));
                    uni.navigateTo({
                        url
                    });
                }
            },
        },
    }
</script>

<style>
    .act_detail .tx {
        width: 98upx;
        height: 98upx;
        border-radius: 50%;
        float: left;
        margin: 30upx 30upx 30upx 0;
    }

    .act_detail .tx image {
        width: 100%;
        height: 100%;
        border-radius: 50%;

    }

    .act_detail .name {
        float: left;
        margin-top: 55upx;
        font-size: 30upx;
        color: #333333;
    }

    .act_detail .date {
        float: right;
        margin-top: 55upx;
        font-size: 24upx;
        color: #999999;
    }

    .segmented-control {
        padding: 25upx 0 19upx 155upx !important;

    }

    .segmented-control-item:last-child {
        margin-left: 150upx;
    }

    .line {
        top: 98upx !important;
    }

    .act_detail .line {
        left: -8upx;
    }

    .navigator-hover {
        background-color: rgba(0, 0, 0, 0);
        opacity: 1;
    }

    .sch_cont .target {
        font-size: 24upx;
        float: left;
        color: #999999;
    }

    .sch_cont .got {
        font-size: 24upx;
        float: right;
        color: #999999;
    }

    .sch_cont .got text {
        color: red;
    }

    .sch_cont .target text {
        color: red;
    }

    .segmented {
        position: -webkit-sticky;
        position: sticky;
        top: 0 !important;
        z-index: 10;
    }

    .activity_2 {
        padding: 35upx 30upx 35upx 30upx;
    }

    .bm_2 text {
        color: #ed5849;
    }

    .sch_nr .bm_2 {
        width: 470upx;
        margin-top: 10upx;
    }

    .activity_2 .people_2 {
        float: left;
    }

    .activity_2 .date_2 {
        float: right;
        margin-top: 0;
    }

    .content_wb text {
        margin-top: -4upx;
    }

    .content .activity {
        padding: 0;
        margin: 30upx 30upx 0 30upx;
        border-bottom: 1upx solid #EAEAEA;
    }

    .content .activity .sch_tu {
        width: 98upx;
        height: 98upx;
        border-radius: 50%;
    }

    .content .activity .sch_tu image {
        border-radius: 50%;
    }

    .content .activity .sch_cont {
        width: 563upx;
        float: left;
        margin-left: 30upx;
        margin-top: 0;
    }

    .content .sch_cont .bm {
        width: 545upx;
        top: 56upx;
    }

    .activity .tx {
        float: left;
        margin-top: 0upx;
    }

    .activity .name {
        float: left;
        margin-top: 24upx;
        width: 25%;
    }

    .activity .target_1 {
        float: left;
        margin-top: 29upx;
        font-size: 24upx;
        color: #999999;
        width: 30%;
    }

    .activity .target_1 text {
        color: #ed5849;
        margin-left: 6upx;
    }

    .activity .date {
        float: left;
        margin-top: 29upx;
    }

    #list {
        padding-bottom: 100upx;
    }

    .segmented-control {
        padding: 25upx 0 10 155upx !important;
        border-bottom: none !important;
    }

    .message {
        height: 88upx;
        background-color: #deb051;
        color: white;
        font-size: 24upx;
    }

    .message text {
        font-size: 30upx;
    }

    .message .tag {
        width: 500upx;
        margin: 0 auto;
        overflow: hidden;
    }

    .message .messLeft {
        float: left;
        line-height: 88upx;
        font-size: 24upx;
    }

    .message .messRight {
        float: left;
        margin-left: 55upx;
        line-height: 88upx;
        font-size: 24upx;
    }

    .uni-loadmore {
        color: #999999;
    }

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: 30rpx;
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: 34rpx;
    }

    .flex-item text {
        height: 96rpx;
        display: inline-block;
    }

    .content .sch_cont .l-title {
        font-size: 30upx;
        overflow: hidden;
        text-overflow: ellipsis;
        height: 36upx;
        width: 100%;
        white-space: nowrap;
        line-height: 1.2;
    }
</style>
