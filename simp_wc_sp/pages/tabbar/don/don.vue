<template>
    <view class="content">
        <uni-swiper-dot :info="swiperDatas" :current="current" :mode="mode" :dots-styles="dotsStyles" field="content">
            <swiper class="swiper-box" :autoplay=true :interval=5000 :circular=true @change="change">
                <swiper-item v-for="(item ,index) in swiperDatas" :key="index">
                    <view :class="item.colorClass" class="swiper-item">
                        <image :src="imagePath + item.pic" mode="aspectFill" @click="goBannerDetail(item.id)" />
                    </view>
                    <view class="content_wb">
                        <view class="content_wb_jx">{{item.name}}</view>
                    </view>
                </swiper-item>
            </swiper>
        </uni-swiper-dot>

        <view class="kuang"></view>
        <view class="mp-search-box">
            <view class="">
                <mSearch @search="search($event,0)" ref='mSearch'></mSearch>
            </view>
        </view>

        <view>
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control>			 -->
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view  class="flex-item" @click="onClickItem(0)">
                    <text :class="cur==0 ? 'on' : '' ">回馈项目</text>
                </view>
                <view v-show="is_don_team" class="flex-item" @click="onClickItem(1)">
                    <text :class="cur==1 ? 'on' : '' ">共同献爱心</text>
                </view>
            </view>
            <view class="list" id="list">
                <view v-show="cur === 0" v-for="(value, key) in donProlist" :key="key" @click="goDetail(value.id)">
                    <view class="activity clearfix">
                        <view class="sch_tu">
                            <image v-if="value.pic!=null&&value.pic!=''" :src="imagePath + value.pic" mode="aspectFill"></image>
                            <image v-if="value.pic==null||value.pic==''" src="/static/img/don_default.png" mode="aspectFill"></image>
                            <view v-if="value.type=='LONG_TIME'" class="doing">
                                进行中
                            </view>
                            <view v-if="value.status=='HASDONE'" class="end">
                                已结束
                            </view>
                            <view v-if="value.status=='BEDOING'" class="doing">
                                进行中
                            </view>
                            <view v-if="value.status=='WILL'" class="readay">
                                预备中
                            </view>
                        </view>
                        <view class="sch_cont">
                            <view class="sch_cont_title">
                                {{ value.name }}
                            </view>
                            <view class="don_bm" v-if="null!=value.startdate && null !=value.enddate">
                                {{value.summary}}
                            </view>
                            <view class="don_bm_nodate" v-if="null==value.startdate || null ==value.enddate">
                                {{value.summary}}
                            </view>
                            <view class="don_date" v-if="null!=value.startdate && null !=value.enddate">
                                时间：{{value.startdate}}至{{value.enddate}}
                            </view>
                        </view>
                    </view>
                    <!-- <view class="activity_h"></view> -->
                </view>
                <view v-show="cur === 1&&is_don_team" v-for="(value, key) in teamDonList" :key="key" @click="goDetail(value.id)">
                    <view class="activity_2">
                        <view class="sch_tu_2 clearfix">
                            <view class="title_2">
                                {{ value.name }}
                            </view>
                            <view v-if="value.status!='SUCCESS' || value.donProject.flag!='NORMAL'" class="end_2">
                                已结束
                            </view>
                            <view v-if="value.status=='SUCCESS'  && value.donProject.flag=='NORMAL'" class="doing_2">
                                进行中
                            </view>
                        </view>
                        <view class="sch_nr clearfix">
                            <view class="bm_2">
                                <view class="people_2">
                                    {{value.summary}}
                                </view>
                            </view>
                            <image v-if="value.smSchoolmate.truePhoto!=null&&value.smSchoolmate.truePhoto!=''" :src="imagePath + value.smSchoolmate.truePhoto"
                                mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto==null||value.smSchoolmate.truePhoto==''" src="/static/img/man_default.png"
                                mode="aspectFill"></image>
                        </view>
                    </view>
                    <!-- <view class="activity_h"></view> -->
                </view>
            </view>
        </view>
        <view class='popup' v-show="cur === 0&&is_don_project" @click="goDon()">
            <view class="popup-shade"></view>
            <view class="suspension-hide">
                <image src="/static/img/don.png"></image>
                <view style="color: #FFFFFF;">
                    回馈
                </view>

            </view>
        </view>
        <uni-load-more :status="status" :contentText="contentText" color="#999999" />
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js'; //公共变量
    import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue';
    import segmentedControl from '@/components/uni-don-segmented-control/uni-don-segmented-control.vue';
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
            mSearch,
            segmentedControl,
            uniLoadMore
        },
        data() {
            return {
                items: ['回馈项目', '共同献爱心'],
                swiperDatas: [],
                donProlist: [],
                teamDonList: [],
                pageNo: 0,
                pageSize: 10,
                cur: 0,
                mode: 'long',
                tab_type: 0,
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                current: 0,
                modeIndex: -1,
                styleIndex: -1,
                dotsStyles: {},
                imagePath: app.imagePath, //图片路径
                moreflag: false, //捐赠是否有上拉更多
                pagenum: 0,
                moretemflag: false, //一起捐是否有上拉更多
                pagetemnum: 0,
                serachvalue: "",
                disabled: false,
                is_don_project: "",
                is_don_team: ""
            }
        },
        computed: {
            ...mapState(['hasLogin','configParams'])
        },
        onReachBottom() {
            if (this.cur === 1 && this.moretemflag) {
                this.status = "loading";
                setTimeout(() => {
                    this.getTeamDonList();
                }, 2000, null);
            } else if (this.cur === 0 && this.moreflag) {
                this.status = "loading";
                setTimeout(() => {
                    this.getDonProList();
                }, 2000, null);
            } else {
                this.status = "noMore";
            }
        },
        onLoad() {},
        onTabItemTap() {
            //util.getStatPoint('switchTab','EVENT_DESCRIBE_46')   
        },
        onShow() {
            this.is_don_project = this.configParams.is_don_project;
            this.is_don_team = this.configParams.is_don_project;
            if (this.donProlist.length == 0) {
                this.getDonProList();
            }
            if (this.swiperDatas.length == 0) {
                this.getswiperDatas();
            }
        },
        methods: {
            change(e) {
                this.current = e.detail.current
            },
            clear() {
                console.log("清空搜索数据")
            },
            search(e, b) {
                if (this.disabled) {
                    return false;
                }
                this.disabled = true;
                this.serachvalue = e;
                this.isSearch = true;
                //将状态修改为搜索，并清空捐赠项目和一起捐的搜索列表
                this.donProlist = [];
                this.teamDonList = [];
                this.pagenum = 0;
                this.pagetemnum = 0;
                if (this.cur === 0) {
                    this.status = "loading";
                    this.getDonProList();
                } else if (this.cur === 1) {
                    this.status = "loading";
                    this.getTeamDonList();
                }
            },
            selectStyle(index) {
                this.dotsStyles = this.dotStyle[index]
                this.styleIndex = index
            },
            selectMode(mode, index) {
                this.mode = mode
                this.modeIndex = index
                this.styleIndex = -1
                this.dotsStyles = this.dotStyle[0]
            },
            onClickItem(index) {
                this.donProlist = [];
                this.teamDonList = [];


                this.serachvalue = '';
                this.$refs.mSearch.clear();
                // this.clear();
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur == 0) {
                    this.pagenum = 0;
                    //util.getStatPoint('click','EVENT_DESCRIBE_19');
                    this.getDonProList();
                } else if (this.cur == 1) {
                    this.pagetemnum = 0;
                    //util.getStatPoint('click','EVENT_DESCRIBE_21');
                    this.getTeamDonList();

                }
            },
            getDonProList() {
                this.pagenum = this.pagenum + 1;
                request.http({
                    // url: app.serverPath + "/don/teamDonList.json",
                    url: "/app/don/donProList",
                    data: {
                        pageNo: this.pagenum,
                        pageSize: this.pageSize,
                        name: this.serachvalue
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            if (datas.length === 0) {
                                this.status = "noMore";
                                return;
                            }
                            datas.forEach((d) => {
                                var percent = 0;
                                if (d.gotMoney < d.targetMoney) {
                                    percent = Number(d.gotMoney / d.targetMoney * 100).toFixed(0)
                                }
                                d.name = util.strcharacterDiscode(d.name);
                                d.summary= util.strcharacterDiscode(d.summary);
                                d["percent"] = percent;
                                this.donProlist.push(d);
                            });
                        }
                        this.moreflag = (res.data.pages > (this.pagenum) ? true : false);
                        if (!this.moreflag) {
                            this.status = "noMore";
                        }
                        this.disabled = false;
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            getswiperDatas() {
                request.http({
                    // url: app.serverPath + "/don/donProList.json",
                    url: "/app/don/donProList",
                    data: {
                        pageNo: 1,
                        pageSize: 3,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.swiperDatas = datas;
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            tabClick(e) {
                this.tab_type = e;
            },
            getTeamDonList() {
                this.pagetemnum = this.pagetemnum + 1;
                request.http({
                    url: "/app/don/teamDonRecords",
                    data: {
                        pageNo: this.pagetemnum,
                        pageSize: this.pageSize,
                        name: this.serachvalue
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            // this.teamDonList = res.data.data;
                            var datas = res.data.list;
                            console.log(this.teamDonList);
                            if (datas.length === 0) {
                                this.status = "noMore";
                                return;
                            }
                            datas.forEach((d) => {
                                this.teamDonList.push(d);
                            });

                        }
                        this.moretemflag = (res.data.pages > (this.pagetemnum) ? true : false);
                        console.log("一起捐状态：" + this.moretemflag)
                        if (!this.moretemflag) {
                            this.status = "noMore";
                        }
                        this.disabled = false;
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            goDetail(detail) {
                var url = '/pages/don/donDetail?id=' + encodeURIComponent(detail);
                if (this.cur == 1) {
                    // url = '/pages/don/teamDonDetail?id=' + encodeURIComponent(JSON.stringify(detail));
                    url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail));
                }

                uni.navigateTo({
                    url
                });
            },
            goBannerDetail(detail) {
                //util.getStatPoint('click','EVENT_DESCRIBE_20');
                var url = '/pages/don/donDetail?id=' + encodeURIComponent(detail);
                uni.navigateTo({
                    url
                });
            },
            goDon() {
                var url = '/pages/don/donPay';
                uni.navigateTo({
                    url
                });
            }
        }
    }
</script>

<style lang="scss">
    .uni-tab-bar-loading {
        text-align: center;
        padding: 20upx 0;
        font-size: 14px;
        color: #CCCCCC;
    }

    .mp-search-box {
        position: absolute;
        top: -5upx;
        left: 34upx
    }

    .uni-swiper-msg {
        padding: 0;
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
	
    .content{
		.sch_wish swiper {
		    font-size: 26upx;
		    color: #FFFFFF;
		    margin-left: 12upx;
		    margin-top: -4upx;
		}
		.sch_cont{
			.sch_cont_title{
				overflow: hidden;
				-o-text-overflow: ellipsis;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 1;
				line-clamp: 1;
				-webkit-box-orient: vertical;
			}
			.don_bm_nodate {margin-top: 6rpx;
			    overflow: hidden;
			    -o-text-overflow: ellipsis;
			    text-overflow: ellipsis;
			    display: -webkit-box;
			    -webkit-line-clamp:2;
			    line-clamp: 2;
			    -webkit-box-orient: vertical;
			    font-size: 24upx;color: #999;
			}
			.don_bm{ margin-top: 5rpx;overflow: hidden;
			    -o-text-overflow: ellipsis;
			    text-overflow: ellipsis;
			    display: -webkit-box;
			    -webkit-line-clamp: 2;
			    line-clamp: 2;
			    -webkit-box-orient: vertical;
			    font-size: 24upx;color: #999;}
            .don_date{
			    font-size: 24upx;color: #999;margin-top: 10upx;}
            .target {
			    // width: 126upx;
			    font-size: 24upx;
			    float: left;
			    color: #999999;
				text {
				    color: red;
				}
			}
			.got {
			    // width: 126upx;
			    font-size: 24upx;
			    float: right;
			    color: #999999;
				text {
				    color: red;
				}
			}
			.bm {
			    width: 432upx;
			}
				
		} 
		.sch_nr{margin-top:10upx;display: flex;align-items: center;

			.bm_2 {float: left;
			    width: calc(100% - 100upx);
			   
				text {
				    color: #ed5849;
				}
			}
			image{width: 73rpx;
			height: 73rpx;
			float: right;
			margin-left: 20rpx;
			border-radius: 50%;
			}
		} 
		.content_wb {
			background: rgba(0, 0, 0, 0.3);
		    .content_wb_jx{color: #fff;
				position: relative;font-size: $uni-font-size-base;padding-left: 18upx;margin: 0 40upx;display: -webkit-box;
				-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;
				&:before{position: absolute;content:'';width: 9upx;height: 28upx;background-color: #deb051;border-radius: 4upx;top: 8upx;left: 0;}
		    	}
					text {
					    margin-top: -4upx;
					    width: 95%;
					    height: 36rpx;
					    overflow: hidden;
					    text-overflow: ellipsis;
					    white-space: nowrap;
					}
		}
			
		.activity{position: relative;
			.sch_tu{width: 230upx;height: 160upx;
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
				.end {
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
			} 
			.sch_cont {
			    width: 432upx;
			    margin-top: 0;
			}
			&:after{
				position: absolute;content: '';width: 100%;left: 0;right: 0;bottom: 0;
				height: 10upx;
				background-color: #EAEAEA;
			}
		} 
		.activity_2{ padding: 40upx 30upx;position: relative;
			.people_2 {
			    
				overflow: hidden;
				-o-text-overflow: ellipsis;
				text-overflow: ellipsis;
				display: -webkit-box;
				-webkit-line-clamp: 3;
				line-clamp: 3;
				-webkit-box-orient: vertical;
				font-size: $uni-font-size-sm;
				color: #999;

			}
			.date_2 {
			    float: right;
			    margin-top: 0;
			}
			.doing_2 {
			    width: 100upx;margin-right: 20upx;
			    height: 45upx;
			    background-color: rgba(55, 151, 84, 0.5);
			    border-radius: 5upx;
			    color: #fff;
			    line-height: 45upx;
			    text-align: center;
			    float: left;font-size: $uni-font-size-sm;
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
			.title_2 {
				width: calc(100% - 120upx);
				height: 45upx;
				font-size: $uni-font-size-base;
				color: #333333;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				float: right;
				line-height: 45upx;
			}
			&:after{
				position: absolute;content: '';width: 100%;left: 0;right: 0;bottom: 0;
				height: 10upx;
				background-color: #EAEAEA;
			}
		} 
	
		
			
	} 

    .navigator-hover {
        background-color: rgba(0, 0, 0, 0);
        opacity: 1;
    }


    .segmented {
        position: -webkit-sticky;
        position: sticky;
        top: 0 !important;
        z-index: 10;
    }



   
 

 

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: $uni-font-size-lg;
		text {
		    height: 96rpx;
		    display: inline-block;
		}
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: $uni-font-size-lg;
    }


    .popup {
        position: fixed;
        width: 136rpx;
        height: 70rpx;
        right: 0;
        top: 800upx;
    }

    .popup-team {
        position: fixed;
        width: 190rpx;
        height: 70rpx;
        right: 0;
        top: 70%;
    }

    .popup-shade {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: #000;
        border-radius: 50rpx 0 0 50rpx;
        opacity: 0.4;
    }

    .suspension-hide {
        background: 0;
        position: absolute;
        padding-right: 0;
        color: #000;
        right: 0;
        width: 140rpx;
        height: 70rpx;
        font-size: 20upx;
        border-radius: 50rpx 0 0 50rpx;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .suspension-hide-team {
        background: 0;
        position: absolute;
        padding-right: 0;
        color: #000;
        right: 0;
        width: 182rpx;
        height: 70rpx;
        font-size: 20upx;
        border-radius: 50rpx 0 0 50rpx;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .suspension-hide{
		image {
		    width: 53rpx;
		    height: 53rpx;
		    margin-right: 10rpx;
		}
		title {
		    font-size: 24rpx;
		}
	} 
    .suspension-hide-team{
		image {
		    width: 53rpx;
		    height: 53rpx;
		    margin-right: 20rpx;
		}
		title {
		    font-size: 24rpx;
		}
	} 
  

</style>
