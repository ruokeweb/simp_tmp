<template>
    <view class="sch_Member">
        <view class="content" :style="{top:stickyTopData+'px'}">
            <mSearch @search="search($event,0)"></mSearch>
        </view>
        <view>
            <view v-for="(value, key) in schoolMembers" :key="key" @click="goDetail(value)" class="act_con">
                <view class="join">
                    <view class="tx">
                        <image v-if="value.virtualPhoto!=null&&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto" mode="aspectFill"></image>
                        <image v-if="value.virtualPhoto==null||value.virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                    </view>
                    <view class="name">{{value.virtualName}}</view>
                    <view class="date">
                        {{value.specialty==null?'':value.specialty}}
                        <text v-if="value.startdate!=null">{{value.startdate}}级</text>
                    </view>
                </view>

            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>

    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import '@/static/css/index.css';
    import request from '@/common/request.js';
    import util from '@/common/util.js';
    import loadcache from '@/common/loadcache.js';
    import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            // uniLoadMore,
            mSearch,
            uniLoadMore
        },
        data() {
            return {
                id: '',
                name: '',
                pic: '',
                schoolMembers: [],
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                pageNo: 0,
                pageSize: 5,
                imagePath: app.imagePath,
                disabled: false,
                detailflag: false,
                searchName: '',
            }
        },
        stickyTop: { // 距离头部多少px将其固定
            type: Number,
            default () {
                return 0;
            }
        },
        computed: {
            stickyTopData: function() {
                return uni.upx2px(this.stickyTop); // 顶部固定显示距离
            },
            ...mapState(['hasLogin','userInfo'])
        },
        onReachBottom() {
            this.status = "loading";
            if (this.detailflag) {
                this.getSchoolMebbers();
            } else {
                this.status = "noMore";
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
            if (this.schoolMembers.length == 0) {
                this.getSchoolMebbers();
            }
        },
        methods: {
            getSchoolMebbers() {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/as/getSchoolMembers',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize,
                        id: this.id,
                        searchName: this.searchName
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                            } else {
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    if (d.startdate != null) {
                                        d["startdate"] = util.dateUtils.getYearByDate(d.startdate);
                                    }

                                    if (d.specialty != null) {
                                        d["specialty"] = loadcache.getObjFromStorageById(app.CACHE_NAME
                                            .DEPARTMENTBASE, d.specialty).name
                                    }
                                    this.schoolMembers.push(d);
                                });
                            }
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }
                            this.detailflag = this.pageNo >= res.data.pages ? false : true;
                            this.disabled = false;
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            goDetail(detail) {
                if (this.hasLogin) {
                    if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                        let url = '/pages/login/cardstatusAuthing';
                        uni.navigateTo({
                            url: url
                        });
                        this.showpageToast("您个人信息没有审核通过，请先去认证！");
                    } else {
                        var url = '/pages_mine/myAssociation/memberDetail?id=' + encodeURIComponent(JSON.stringify(
                            detail.id));
                        uni.navigateTo({
                            url
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
            search(e, val) {
                if (this.disabled) {
                    return false;
                }
                this.disabled = true;
                this.searchName = e;
                this.schoolMembers = [];

                this.pageNo = 0;
                this.getSchoolMebbers();
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
        },

    }
</script>

<style>
    .sch_Member .join {
        height: 160upx;
        margin: 0 30upx;
        position: relative;
    }

    .sch_Member .act_con {
        overflow: hidden;
        overflow-y: scroll;
    }

    .sch_Member .join:after {
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

    .sch_Member .tx {
        width: 98upx;
        height: 98upx;
        border-radius: 50%;
        float: left;
        margin: 30upx 30upx 30upx 0;
    }

    .sch_Member .tx image {
        width: 100%;
        height: 100%;
        border-radius: 50%;

    }

    .sch_Member .name {
        float: left;
        margin-top: 55upx;
        font-size: 30upx;
        color: #333333;
    }

    .sch_Member .date {
        float: right;
        margin-top: 55upx;
        font-size: 24upx;
        color: #999999;
    }

    .sch_Member .content {
        position: sticky;
        height: 149upx;
        background-color: #EAEAEA;
        overflow: hidden;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 35upx 0;
        font-size: 30upx;
        color: #CCCCCC;
    }
</style>
