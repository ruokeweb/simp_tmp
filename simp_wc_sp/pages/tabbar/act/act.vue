<template>
    <view class="content">
        <uni-swiper-dot :info="actBannerList" :current="current" :mode="mode" :dots-styles="dotsStyles" field="content">
            <swiper class="swiper-box" @change="change" :autoplay=true :interval=5000 :circular=true>
                <swiper-item v-for="(item ,index) in actBannerList" :key="index">
                    <view :class="item.colorClass" class="swiper-item">
                        <image :src="imagePath + item.image" mode="aspectFill" @click="goBannerDetail(item)" />
                    </view>
                    <view class="content_wb">
                        <view class="content_wb_jx">{{item.name}}</view>
                    </view>
                </swiper-item>
            </swiper>
        </uni-swiper-dot>
        <view class="kuang"></view>
        <view class="mp-search-box">
            <view class="content">
                <mSearch @search="search($event,0)" ref='mSearch'></mSearch>
            </view>
        </view>
        <view>
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view class="flex-item" @click="onClickItem(0)">
                    <text :class="cur==0 ? 'on' : '' ">活动</text>
                </view>
                <view class="flex-item" @click="onClickItem(1)">
                    <text :class="cur==1 ? 'on' : '' ">值年返校</text>
                </view>
            </view>
            <view class="list" id="list">
                <view v-show="cur === 0" v-for="(value, key) in actProList" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <!-- <image :src="imagePath + value.image" mode="aspectFill"></image> -->
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
                            <view class="sch_cont_title">
                                {{ value.name }}
                            </view>
                            <view class="sch_cont_date">
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
                <view v-show="cur === 1" v-for="(value, key) in actBackSchoolList" :key="key" @click="goDetail(value)">
                    <view class="activity_2">
                        <view class="sch_tu_2 clearfix">
                            <view v-if="todayDate>value.endDate" class="end_2">
                                已结束
                            </view>
                            <view v-if="value.createDate<= todayDate && todayDate<=value.endDate" class="doing_2">
                                进行中
                            </view>
                            <view v-if="todayDate<value.createDate" class="readay_2">
                                预备中
                            </view>
                            <view class="title_2">
                                {{ value.name }}
                            </view>

                        </view>
                        <view class="sch_nr clearfix">
                            <view class="bm_2">
                                <view class="people_2">
                                    <text>{{null!=value.readyNo?value.readyNo:0}}</text>人已报名
                                </view>
                                <view class="date_2">
                                    报名时间：{{value.createDate}}至{{value.endDate}}
                                </view>
                            </view>
                            <image v-if="value.virtualPhoto==null||value.virtualPhoto==''" src="/static/img/man_default.png"
                                mode="aspectFill"></image>
                            <image v-if="value.virtualPhoto!=null&&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto"
                                mode="aspectFill"></image>
                        </view>
                    </view>
                    <view class="activity_h"></view>
                    <!-- <view class="uni-loadmore" v-if="showLoadMore">{{loadMoreText}}</view> -->
                </view>

                <view class="act_foot">
                    <view class="fx" @click="goBackSchool()">
                        <view class="jia">
                            +
                        </view>
                        <text>发起值年返校</text>
                    </view>
                </view>
            </view>
        </view>

        <uni-load-more :status="status" :contentText="contentText" color="#999999" />
    </view>
</template>

<script>
    function getDate(type) {
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
    }
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js'; //公共变量
    import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue';
    import segmentedControl from '@/components/uni-act-segmented-control/uni-act-segmented-control.vue';
    import mSearch from '@/components/act-mehaotian-search/mehaotian-search.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            uniSwiperDot,
            segmentedControl,
            mSearch,
            uniLoadMore
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo']),
        },
        data() {
            return {
                items: ['活动', '值年返校'],
                actProList: [],
                actBannerList: [],
                actBackSchoolList: [],
                current: 0,
                mode: 'long',
                dotsStyles: {},
                cur: 0,
                status: "loading",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                imagePath: app.imagePath,
                actPageNo: 0,
                backSchoolPageNo: 0,
                pageSize: 10,
                actflag: false,
                backSchoolflag: false,
                todayDate: getDate(),
                isLogin: false,
                searchName: '',
                disabled: false

            }
        },
        onLoad() {
            //util.getStatPoint('pageLoad','EVENT_DESCRIBE_53')
            this.isLogin = this.hasLogin;
        },
        onUnload() {
            //util.getStatPoint('pageUnload','EVENT_DESCRIBE_52')
        },
        onReachBottom() {
            if (this.cur == 0 && this.actflag) {
                //util.getStatPoint('onReachBottom','EVENT_DESCRIBE_50')
                this.actPageNo = this.actPageNo + 1;
                request.http({
                    url: '/app/act/ActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize,
                        name: this.searchName
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //  this.actProList = this.actProList.concat(res.data.list);
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                                setTimeout(() => {
                                    this.showLoadMore = false;
                                }, 500);
                            } else {
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    d.name = util.strcharacterDiscode(d.name);
                                    this.actProList.push(d);
                                });
                                this.actflag = this.actPageNo >= res.data.pages ? false : true;
                            }
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }

                        }
                    },
                    error: (res) => {

                    }
                });

            } else if (this.cur == 1 && this.backSchoolflag) {
                //util.getStatPoint('onReachBottom','EVENT_DESCRIBE_51')
                this.backSchoolPageNo = this.backSchoolPageNo + 1;
                request.http({
                    url: '/app/actSelf/getActSelfList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.backSchoolPageNo,
                        pageSize: this.pageSize,
                        name: this.searchName
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //  this.actProList = this.actProList.concat(res.data.list);
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                                setTimeout(() => {
                                    this.showLoadMore = false;
                                }, 500);
                            } else {
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    d.name = util.strcharacterDiscode(d.name);
                                    this.actBackSchoolList.push(d);
                                });
                                this.backSchoolflag = this.backSchoolPageNo >= res.data.pages ? false :
                                    true;
                            }
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }

                        }
                    },
                    error: (res) => {

                    }
                });

            } else {
                this.status = "noMore";
            }
        },
        onShow() {
            //util.getStatPoint('enterPage','EVENT_DESCRIBE_54')
            if (this.cur == 0) {
                this.actPageNo = 0;
                this.getActProList();
                // this.getDonProList();
            } else {
                this.backSchoolPageNo = 0;
                this.getBackSchoolList();
            }

            this.getBannerList();
        },
        onTabItemTap() {
            //util.getStatPoint('switchTab','EVENT_DESCRIBE_47')   
        },
        methods: {
            onClickItem(index) {
                this.searchName = '';


                this.$refs.mSearch.clear();
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur == 0) {
                    //util.getStatPoint('click','EVENT_DESCRIBE_30');
                    this.actProList = [];
                    this.actPageNo = 0;
                    this.getActProList();
                } else {
                    //util.getStatPoint('click','EVENT_DESCRIBE_32');
                    this.actBackSchoolList = [];
                    this.backSchoolPageNo = 0;
                    this.getBackSchoolList();
                }
            },
            change: function(e) {
                this.current = e.detail.current;
            },
            getActProList: function(e) {
                this.actPageNo = this.actPageNo + 1;
                this.status = "loading";
                request.http({
                    url: '/app/act/ActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize,
                        name: this.searchName
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                            }
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actProList = datas;
                            this.actflag = this.actPageNo >= res.data.pages ? false : true;
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getBannerList: function(e) {
                request.http({
                    url: '/app/act/bannerActs',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageNo: 0,
                        pageSize: 3
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actBannerList = datas;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getBackSchoolList: function(e) {
                this.backSchoolPageNo = this.backSchoolPageNo + 1;
                this.status = "loading";
                request.http({
                    url: '/app/actSelf/getActSelfList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.backSchoolPageNo,
                        pageSize: this.pageSize,
                        name: this.searchName
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined || res.data.list.length == 0) {
                                this.status = "noMore";
                            }
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actBackSchoolList = datas;
                            this.backSchoolflag = this.backSchoolPageNo >= res.data.pages ? false :
                                true;
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            goDetail(e) {
                let url = '';
                if (this.cur == 0) {
                    url = '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                } else {
                    url = '/pages/act/backSchoolDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                }
                uni.navigateTo({
                    url: url
                });
            },
            goBannerDetail(e) {
                //util.getStatPoint('click','EVENT_DESCRIBE_31')   
                let url = '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                uni.navigateTo({
                    url: url
                });
            },
            search(e, val) {
                //util.getStatPoint('click','EVENT_DESCRIBE_49')   
                if (this.disabled) {
                    return false;
                }
                this.disabled = true;
                this.searchName = e;
                this.actProList = [];
                this.actBackSchoolList = [];
                this.backSchoolPageNo = 0;
                this.actPageNo = 0;
                if (this.cur == 0) {
                    this.actPageNo = this.actPageNo + 1;
                    request.http({
                        url: '/app/act/ActList',
                        header: 'application/x-www-form-urlencoded',
                        method: "POST",
                        data: {
                            pageNo: this.actPageNo,
                            pageSize: this.pageSize,
                            name: this.searchName

                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                if (res.data.list == undefined || res.data.list.length == 0) {
                                    this.status = "noMore";
                                }
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    d.name = util.strcharacterDiscode(d.name);
                                });
                                this.actProList = datas;
                                this.actflag = this.actPageNo >= res.data.pages ? false : true;
                                if (res.data.list.length < this.pageSize) {
                                    this.status = "noMore";
                                }
                                this.disabled = false;
                            }
                        },
                        error: (res) => {

                        }
                    });
                } else {
                    this.backSchoolPageNo = this.backSchoolPageNo + 1;
                    request.http({
                        url: '/app/actSelf/getActSelfList',
                        header: 'application/x-www-form-urlencoded',
                        method: "POST",
                        data: {
                            pageNo: this.backSchoolPageNo,
                            pageSize: this.pageSize,
                            name: this.searchName
                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                if (res.data.list == undefined || res.data.list.length == 0) {
                                    this.status = "noMore";
                                }
                                this.actBackSchoolList = res.data.list;
                                this.backSchoolflag = this.backSchoolPageNo >= res.data.pages ? false :
                                    true;
                                if (res.data.list.length < this.pageSize) {
                                    this.status = "noMore";
                                }
                                this.disabled = false;
                            }
                        },
                        error: (res) => {

                        }
                    });
                }
            },
            goBackSchool(e) {
                //util.getStatPoint('click','EVENT_DESCRIBE_37')   
                //这块还需要判断是否能够发起值年返校
                if (this.isLogin) {
                    if (this.userInfo.cardStatus != 'NORMAL') {
                        this.showpageToast("您的信息还未审核通过，不能发起值年返校");
                    } else {
                        let url = '/pages/act/backSchoolInfo';
                        uni.navigateTo({
                            url: url
                        });
                    }

                } else {

                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("您还未登录，不能发起值年返校");
                }

            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
        }
    }
</script>

<style lang="scss">
    .mp-search-box {
        position: absolute;
        top: -5upx;
        left: 34upx
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

    .content .sch_cont {
        .people {
            width: 126upx;
            font-size: 24upx;
            float: right;
            color: #999999;

            text {
                color: #ed5849;
            }
        }
    }



    .act_foot {
        .fx {
            position: fixed;
            left: 210upx;
            width: 328upx;
            height: 80upx;
            box-shadow: 1upx 2upx 10upx 0upx rgba(0, 0, 0, 0.2);
            border-radius: 40upx;
            bottom: 25upx;
            background-color: #ffffff;

            text {
                display: block;
                line-height: 80upx;
                float: left;
                color: #c59b5c;
                font-size: 30upx;
                margin-left: 25upx;
            }

            .jia {
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
        }
    }




    .segmented {
        position: -webkit-sticky;
        position: sticky;
        top: 0 !important;
        z-index: 10
    }

    .content .activity .sch_tu {
        .doing {
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

        .readay {
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

    }

    .activity_2 {
        padding: 40upx 30upx !important;

        .title_2 {
            float: left;
            margin-left: 20upx;
            width: calc(100% - 113upx);
        }

        .doing_2 {
            width: 93upx;
            height: 40upx;
            background-color: rgba(55, 151, 84, 0.5);
            border-radius: 5upx;
            color: white;
            line-height: 40upx;
            text-align: center;
            float: left;
        }

        .sch_nr {
            margin-top: 48upx;

            image {
                width: 73upx;
                height: 73upx;
                border-radius: 50%;
            }

            .bm_2 {
                width: calc(100% - 73upx);
                float: left;
            }
        }

        .readay_2 {
            width: 93upx;
            height: 40upx;
            background-color: rgba(158, 117, 33, 0.5);
            border-radius: 5upx;
            color: white;
            line-height: 40upx;
            text-align: center;
            float: right;
        }
    }
    .content .content_wb {
        background: rgba(0, 0, 0, 0.3);
        .content_wb_jx {
            color: #fff;
            position: relative;
            font-size: $uni-font-size-base;
            padding-left: 18upx;
            margin: 0 40upx;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 2;
            overflow: hidden;

            &:before {
                position: absolute;
                content: '';
                width: 9upx;
                height: 28upx;
                background-color: #deb051;
                border-radius: 4upx;
                top: 8upx;
                left: 0;
            }

        }
    }

    .content .content_wb text {
        margin-top: -4upx;
        width: 95%;
        height: 36rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .sch_cont {
        .bm {
            width: 432upx;
        }

        .sch_cont_title {
            overflow: hidden;
            -o-text-overflow: ellipsis;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            line-clamp: 1;
            -webkit-box-orient: vertical;

            &.dyn {
                -webkit-line-clamp: 2;
                line-clamp: 2;
            }
        }

        .sch_cont_date {
            position: absolute;
            width: 431upx;
            height: 32upx;
            line-height: 32upx;
            font-size: 24upx;
            top: 72upx;
            color: #999999;

        }

    }

    .content .activity .sch_cont {
        width: 432upx;
        margin-top: 0;
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

    /* tab */
    .uni-row {
        background-color: #fff;
    }

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: $uni-font-size-lg;
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: $uni-font-size-lg;
    }

    .flex-item text {
        height: 96rpx;
        display: inline-block;
    }

    .content .bm .xy {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
</style>
