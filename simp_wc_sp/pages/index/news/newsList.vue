<template>
    <view class="content">
        <uni-swiper-dot :info="bannerlist" :current="current" :mode="mode">
            <swiper :autoplay="true" class="swiper-box" @change="change" :interval="5000">
                <swiper-item v-for="(item ,index) in bannerlist" :key="index">
                    <view :class="item.colorClass" class="swiper-item">
                        <image :src="item.picurl" mode="aspectFill" @click="goBannerDetail(item)" />
						<image v-if="item.picurl == domain"  src="/static/img/act/act_default.png" mode="aspectFill"  @click="goBannerDetail(item)"></image>
						<image v-if="item.picurl != domain" :src="item.picurl" mode="aspectFill"  @click="goBannerDetail(item)" ></image>
                    </view>
                    <view class="content_wb"> 
                        <view class="content_wb_jx">{{item.title}}</view>
                    </view>
                </swiper-item>
            </swiper>
        </uni-swiper-dot>  
        <view class="kuang"></view> 


        <view>
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view v-for="(value, key) in treelist" class="flex-item" :key="key" @click="onClickItem(value,key)">
                    <text :class="cur==key ? 'on' : '' ">{{value.name}}</text>
                </view>
            </view>
        </view>

        <view class="list" id="list">
            <view v-for="(value, key) in newslist" :key="key" @click="goDetail(value)">
                <view class="activity">
                    <view class="sch_tu">
                        <!-- <image :src="value.picurl" mode="aspectFill"></image> -->
						<image v-if="value.picurl == domain"  src="/static/img/act/act_default.png" mode="aspectFill" ></image>
						<image v-if="value.picurl != domain" :src="value.picurl" mode="aspectFill" ></image>
                    </view>
                    <view class="sch_cont">
                        <view class="sch_cont_title dyn">
                            {{ value.title }}
                        </view>
                        <view class="bm">
                            <view class="target">
                                {{value.date}}
                            </view>
                        </view>
                    </view>
                </view>
                <view class="activity_h"></view>
            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>
    </view>

</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniIcon from '@/components/uni-icon/uni-icon.vue';
    import store from '@/store/index.js';
    import util from '@/common/util.js';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import '@/static/css/index.css'
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            uniSwiperDot,
            uniIcon,
            segmentedControl,
            uniLoadMore
        },
        data() {
            return {
                current: '',
                numCard: '',
                treelist: '',
                cardMsg: [],
                bannerlist: [],
                newslist: [],
                schoolNewslist: [],
                sMNewslist: [],
                noticeNewslist: [],
                mode: 'long',
                cur: 0,
                pageSize: 3,
                pageNo: 1,
                isLogin: false,
                domain: "",
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                items: ['新闻动态', '学校新闻', '校友动态', '通知公告'],
            };
        },
        computed: {
            ...mapState(['hasLogin','configParams'])
        },
        onLoad() {
            this.isLogin = this.hasLogin;
        },

        onReachBottom() {
            this.pageNo = this.pageNo + 1;
            var id = this.treelist[this.cur].id;
            request.http({
                url: '/app/index/getIndexNews',
                header: 'application/x-www-form-urlencoded',
                method: "POST",
                data: {
                    pageNo : this.pageNo,
                    pageSize: this.pageSize,
                    id: id
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        if (res.data.data == undefined || res.data.data.length == 0) {
                            this.status = "noMore";
                            setTimeout(() => {
                                this.showLoadMore = false;
                            }, 500);
                        } else {
                            this.bannerlist = res.data.data;
                            var datas = res.data.data;
                            datas.forEach((d) => {
                                this.newslist.push(d);
                            });
                        }
                        if (res.data.data.length < this.pageSize) {
                            this.status = "noMore";
                        }
                    }
                },
                error: (res) => {

                }
            }); 
        },
        onShow() {
            this.domain = this.configParams.domain;
            if(this.bannerlist.length == 0){
                request.http({
                    url: '/app/index/getIndexNews',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.data == undefined || res.data.data.length == 0) {
                                this.status = "noMore";
                                setTimeout(() => {
                                    this.showLoadMore = false;
                                }, 500);
                            } else {
                                this.bannerlist = res.data.data;
                                var datas = res.data.data;
                                datas.forEach((d) => {
                                    this.newslist.push(d);
                                });
                            }
                            if (res.data.data.length < this.pageSize) {
                                this.status = "noMore";
                            }
                        }
                    },
                    error: (res) => {

                    }
                }); 
            }
            this.getTreeList();
        },
        methods: {
            onClickItem: function(index,key) {
                this.pageNo = 1;
                if (this.cur !== key) {
                    this.cur = key;
                }
                var id = index.id;
                request.http({
                    url: '/app/index/getIndexNews',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo : this.pageNo,
                        pageSize: this.pageSize,
                        id: id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.newslist = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            change: function(e) {
                this.current = e.detail.current;
            },
            getTreeList: function (){
                request.http({
                    url: '/app/index/getNewsTrees',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.treelist = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            },

            getSMNewsList: function(e) {
                this.status = "loading";
                request.ajax({
                    url: '/news/vsbSMNews.json',
                    method: "GET",
                    success: (res) => {
                        if (res.data.data.length == 0) {
                            this.status = "noMore";
                            setTimeout(() => {

                            }, 500);
                        } else {
                            this.sMNewslist = this.sMNewslist.concat(res.data.data);
                            this.last_id = res.data.data[res.data.data.length - 1].id;
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            goDetail: function(e) {
                let url = '/pages/index/news/newsDetail?id=' + encodeURIComponent(e.newsid);
                uni.navigateTo({
                    url: url
                });
            },
            goBannerDetail: function(e) {
                let url = '/pages/index/news/newsDetail?id=' + encodeURIComponent(e.newsid);
                uni.navigateTo({
                    url: url
                });
            }
        },

    };
</script>

<style lang="scss">
    .sw_image {
        width: 750upx;
        height: 400upx;
    }

    .mycard {
        width: 90%;
        margin: auto;
        margin-top: 20upx;
    }

    .uni-media-list-logo {
        width: 180upx;
        height: 140upx;
    }

    .uni-media-list-body {
        height: auto;
        justify-content: space-around;
    }

    .uni-media-list-text-top {
        height: 74upx;
        font-size: 28upx;
        overflow: hidden;
    }

    .uni-media-list-text-bottom {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
    }

    .navbar {
        display: flex;
        height: 40px;
        padding: 0 5px;
        background: #fff;
        box-shadow: 0 1px 5px rgba(0, 0, 0, .06);
        position: relative;
        z-index: 10;

        .nav-item {
            position: relative;
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100%;
            font-size: 15px;
            color: $font-color-dark;
            position: relative;

            &.current {
                color: $base-color;

                &:after {
                    content: '';
                    position: absolute;
                    left: 50%;
                    bottom: 0;
                    transform: translateX(-50%);
                    width: 44px;
                    height: 0;
                    border-bottom: 2px solid $base-color;
                }
            }
        }
    }

    .mp-search-box {
        width: 95%;
        padding: 0 18upx;

        .ser-input {
            flex: 1;
            height: 70upx;
            line-height: 56upx;
            text-align: center;
            font-size: 28upx;
            color: $font-color-base;
            border-radius: 20px;
            background: #c0c0c0;
        }
    }

    .swiper-dot-don {
        margin-top: 5upx;
    }

    .content .content_wb{background: rgba(0, 0, 0, 0.3);
		.content_wb_jx{color:#fff;position: relative;font-size: $uni-font-size-base;padding-left: 18upx;margin: 0 40upx;display: -webkit-box;
-webkit-box-orient: vertical;
-webkit-line-clamp: 2;
overflow: hidden;
			&:before{
				position: absolute;content:'';width: 9upx;height: 28upx;background-color: #deb051;border-radius: 4upx;top: 8upx;left: 0;
			}
			
		}
		text {
		    margin-top: -4upx;
		}
	} 

    .content .sch_cont{
		.sch_cont_title{
			overflow: hidden;
			-o-text-overflow: ellipsis;
			text-overflow: ellipsis;
			display: -webkit-box;
			-webkit-line-clamp: 1;
			line-clamp: 1;
			-webkit-box-orient: vertical;
			&.dyn{
				-webkit-line-clamp: 2;
				line-clamp: 2;
			}
			
		}
		.date_mod {
		    position: absolute;
		    left: 0;
		    top: 127upx;
		    margin-top: 0;
		}
	} 

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .activity .sch_cont {
        margin-top: 0;
        width: 427upx;
        position: relative;
    }

    .content .sch_cont{
		.target {
		    font-size: $uni-font-size-sm;
		    color: #999999;
		}
		.bm {
		    width: 427upx;
		}
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

    .uni-row {
        padding-left: 20upx;
    }
</style>
