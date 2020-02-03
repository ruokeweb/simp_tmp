<template>
    <view class="content">
        <view class="mp-search-box">
            <view class="content">
                <mSearch @search="search($event,0)"></mSearch>
            </view>
        </view>
        <view>
            <view class="pick-panel">
                <view class="pick-bar">
                    <!-- <text>类型</text> -->
                    <picker class="picker" :range="typeArray" range-key="label" @change="bindChangeType(typeArray,$event)">
                        <view class="uni-input">类型
                            <!--{{typeArray[typeIndex]}}-->
                            <!-- <image class="arrow" src="../../../static/img/pick-arrow.png"></image> -->
                        </view>
                    </picker>
                </view>
                <view class="pick-bar">
                    <!-- <text>时间</text> -->
                    <picker class="picker" :range="dateArray" range-key="label" @change="bindChangeDate(dateArray,$event)">
                        <view class="uni-input">时间
                            <!--{{dateArray[dateIndex]}}-->
                            <!-- <image class="arrow" src="../../../static/img/pick-arrow.png"></image> -->
                        </view>
                    </picker>
                </view>
            </view>
            <view class="list" id="list" style="background-color: #eaeaea;">
                <view v-for="(value, key) in assList" :key="key" @click="goDetail(value)" class="list-item" style="">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.logo==null ||value.logo==''" src="/static/img/as_default.png" mode="aspectFill"></image>
                            <image v-if="value.logo!=null" :src="imagePath + value.logo" mode="aspectFill"></image>
                        </view>
                        <view class="con_right">
                            <view class="sch_cont">
                                <view class="title">
                                    {{ value.name }}
                                </view>
                                <view class="bm">
                                    <view class="target">
                                        <image class="icon-people" src="../../../static/img/people.png"></image>{{value.sum==null?0:value.sum}}
                                    </view>
                                   <view class="got" v-if="value.type!=undefined">
                                       {{value.type}}
                                   </view>
                                </view>
                            </view>

                            <!-- <button class="join" @click.stop="joinAs(value)">
                                加入
                            </button> -->
                        </view>
                    </view>
                </view>
            </view>
            <view class="act_foot">
                <view class="fx" @click="goMyAss()">
                    <view class="jia">
                        +
                    </view>
                    <text>我的校友会</text>
                </view>
            </view>
        </view>
        <uni-load-more :status="status" :contentText="contentText" color="#999999" />
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import mSearch from '@/components/mehaotian-search/mehaotian-search.vue';
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import loadcache from '@/common/loadcache.js';
    import {
        mapState
    } from 'vuex';

    export default {
        components: {
            mSearch,
            uniLoadMore
        },
        data() {
            return {
                typeArray: [{
                    label: "全部",
                    value: ""
                }],
                typeIndex: 0,
                dateArray: [{
                    label: "正序",
                    value: "asc"
                }, {
                    label: "逆序",
                    value: "desc"
                }],
                dateIndex: 0,
                assList: [],
                mode: 'nav',
                tab_type: 0,
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                pageNo: 0,
                pageSize: 10,
                imagePath: app.imagePath,
                detailflag: false,
                isLogin: false,
                searchType: '',
                orderType: '',
                searchName: '',
                disabled: false
            }
        },
        computed: {
            ...mapState(['hasLogin'])
        },
        onReachBottom() {
            //请求(生产环境需要判断是捐赠还是一起捐)
            this.status = "loading";
            if (this.detailflag) {
                this.getAssList();
            } else {
                this.status = "noMore";
            }
        },
        onLoad() {
            this.isLogin = this.hasLogin;
            loadcache.getDictsByTypeCode(app.DICT_TYPECODE.ASSOCIATION_TYPE).forEach((d) => {
                this.typeArray.push(d);
            });
        },
        onShow() {
            this.assList = [];
            this.pageNo=0;
            this.getAssList();
        },
        methods: {
            getAssList() {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/as/getAssociations',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize,
                        type: this.searchType,
                        orderType: this.orderType,
                        name: this.searchName
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
                                    if (d.type != null) {
                                        d.type = loadcache.getDictByvalue(d.type, app.DICT_TYPECODE
                                            .ASSOCIATION_TYPE).label;
                                    }
                                    this.assList.push(d);
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
                });
            },
            goDetail(e) {
                var url = '/pages_mine/myAssociation/myAssociationDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                uni.navigateTo({
                    url
                });
            },
            bindChangeType(value, e) {
                this.assList = [];
                this.pageNo = 0;
                this.searchType = value[e.target.value].value;
                this.getAssList();
            },
            bindChangeDate(value, e) {
                this.assList = [];
                this.pageNo = 0;
                this.orderType = value[e.target.value].value;
                this.getAssList();
            },
            search(e, val) {
                if (this.disabled) {
                    return false;
                }
                this.disabled = true;
                this.assList = [];
                this.searchName = e;
                this.pageNo = 0;
                this.getAssList();
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            goMyAss(e) {
                //这块还需要判断是否能够发起值年返校
                if (this.isLogin) {
                    let url = '/pages_mine/myAssociation/myAssociation';
                    uni.navigateTo({
                        url: url
                    });
                } else {
                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("您还未登录，不能发起值年返校");
                }

            },
        }
    }
</script>

<style lang="scss">
    .mp-search-box {
        width: 100%;
        height: 150upx;
        background-color: #eaeaea;
        overflow: hidden;
    }

    .picker {
        display: inline-block;
    }

    .pick-panel {
        border: 1upx solid #eaeaea;
    }

    .pick-bar {
        width: 49%;
        display: inline-block;
        text-align: center;
        position: relative;
    }

    .pick-bar:first-child:after {
        content: " ";
        position: absolute;
        width: 1upx;
        height: 30upx;
        top: 25upx;
        right: 0;
        background: #eaeaea;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 20upx 0;
        font-size: 14px;
        color: #CCCCCC;
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

    .content .sch_cont .target {
        // width: 126upx;
        font-size: 24upx;
        float: left;
        color: #999999;
    }

    .content .sch_cont .bm {
        width: 250upx;
        top: 88upx
    }

    .content .sch_cont .got {
        // width: 126upx;
        font-size: 24upx;
        float: right;
        color: #999999;
    }

    .content .sch_cont .got text {
        color: red;
    }

    .content .sch_cont .target text {
        color: red;
    }

    .arrow {
        width: 21upx;
        height: 10upx;
        position: relative;
        bottom: 3upx;
        left: 20upx;
    }

    .uni-input {position: relative;background: none;height: auto;padding: 15rpx 40rpx;

    	&:after{
    		position: absolute;content:'';border-left:10upx solid transparent;border-right:10upx solid transparent;border-top: 10upx solid #a1a1a1;
    		top:36upx;
    		margin: auto;
    		right:0;
    	}
    }

    .icon-people {
        width: 25upx;
        height: 22upx;
        padding-right: 10upx;
    }

    .list-item {
        margin-bottom: 10upx;
        background-color: #fff;
        height: 200upx;
    }

    .content .activity .sch_tu {
        width: 120upx;
        height: 120upx;
    }

    .content .activity .sch_cont {
        margin-top: 0;
        position: relative;
        right: 8%;
    }

    .join {
        float: left;
        width: 120upx;
        height: 56upx;
        background-color: #deb051;
        border-radius: 28upx;
        border: solid 1upx #f9c558;
        line-height: 56upx;
        font-size: 24upx;
        color: #FFFFFF;
        text-align: center;
        position: relative;
        top: -90upx;
        left: 560upx;
    }

    .act_foot .fx {
        position: fixed;
        left: 210upx;
        width: 280upx;
        height: 80upx;
        box-shadow: 1upx 2upx 10upx 0upx rgba(0, 0, 0, 0.2);
        border-radius: 40upx;
        bottom: 25upx;
        background-color: #ffffff;
    }

    .act_foot .fx {
        position: fixed;
        left: 210upx;
        width: 328upx;
        height: 80upx;
        box-shadow: 1upx 2upx 10upx 0upx rgba(0, 0, 0, 0.2);
        border-radius: 40upx;
        bottom: 25upx;
        background-color: #ffffff;
    }

    .act_foot .fx .jia {
        width: 60upx;
        height: 60upx;
        background-color: #e2b352;
        border-radius: 50%;
        font-size: 51upx;
        color: white;
        text-align: center;
        line-height: 51upx;
        margin-top: 11upx;
        margin-left: 10upx;
        float: left;
    }

    .act_foot .fx text {
        display: block;
        line-height: 80upx;
        float: left;
        color: #c59b5c;
        font-size: 30upx;
        margin-left: 25upx;
    }
</style>
