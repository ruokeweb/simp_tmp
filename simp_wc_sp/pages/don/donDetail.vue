<template>
    <!-- 捐献项目详情页 -->
    <view class="act_detail content">

        <view class="info" v-show="isshow">
            <view class="tb">
                <view class="uni-swiper-msg">
                    <swiper vertical="true" autoplay="true" circular="true" interval="3000">
                        <swiper-item v-for="(item, index) in navMsg" :key="index">
                            <view style="">
                                <view class="tz">
                                    <image src="/static/img/index/tz_2.png" mode=""></image>
                                </view>
                                <navigator style="display:inline;"> 感谢{{item.name}}捐赠{{item.money}}元</navigator>
                            </view>
                        </swiper-item>
                    </swiper>
                </view>
            </view>
        </view>
        <view class="act_head">
            <!-- 顶部活动详情 -->
            <view>
                <view class="list_1 clearfix">
                    <view class="top">
                        <view class="tu_mod">
                            <image v-if="donProject.pic==null||donProject.pic==''" src="/static/img/don_default.png"
                                mode="aspectFill"></image>
                            <image v-if="donProject.pic!=null && donProject.pic!=''" class="uni-media-list-logo" :src="imagePath + donProject.pic"
                                mode="aspectFill"></image>
                        </view>
                        {{donProject.name}}
                    </view>
                    <view class="container">
                        <view class="bm" v-if="null != donProject.startdate && null!= donProject.enddate">
                            时间：{{donProject.startdate}}至{{donProject.enddate}}
                        </view>
                    </view>
                </view>

            </view>
            <view class="zk">
                <view class="intro">
                    <view class="intro_jx">简介</view>
                   
                </view>
                <view class="wb">
                    <uParse :content="donProject.summary" @preview="preview" @navigate="navigate" />
                </view>
            </view>
            <view class="up" @click="btn" v-show="fold">
                <text class="tu_1">展开</text>
            </view>
            <view class="zk" @click="btn" v-show="!fold">
                <view class="intro">
                    <view class="intro_jx">内容</view>
                </view>
                <view class="wb">
                    <uParse :content="donProject.content" @preview="preview" @navigate="navigate" />
                    <!-- {{donProject.content}} -->
                </view>
                <view class="up">
                    <text class="tu_2">收起</text>
                </view>
            </view>
        </view>
        <view>
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view class="flex-item" @click="onClickItem(0)">
                    <text :class="cur==0 ? 'on' : '' ">回馈公示</text>
                </view>
                <view v-show="is_don_team" class="flex-item" @click="onClickItem(1)">
                    <text :class="cur==1 ? 'on' : '' ">共同献爱心</text>
                </view>
            </view>
            <view class="list">
                <view v-show="cur === 0" v-for="(value, key) in donPersons" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu tx">
                            <image v-if="value.smSchoolmate.truePhoto == null ||value.smSchoolmate.truePhoto == undefined || value.isShow == is_no ||value.smSchoolmate.truePhoto == '' || value.smSchoolmate.truePhoto == 'undefined'"
                                src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto!=null && value.isShow != is_no && value.smSchoolmate.truePhoto != '' && value.smSchoolmate.truePhoto != 'undefined'"
                                :src="imagePath + value.smSchoolmate.truePhoto" mode="aspectFill"></image>
                            <!-- <image :src="imagePath+ value.smSchoolmate.truePhoto" mode="aspectFill"></image> -->
                        </view>
                        <view class="name">
                            {{value.name}}
                        </view>
                        <view class="target target_1">
                            回馈<text>{{value.money}}元</text>
                        </view>
                        <view class="date">
                            {{value.time}}
                        </view>
                    </view>
                </view>
                <view v-show="cur === 1&& is_don_team" v-for="(value, key) in teamDonProList" :key="key" @click="goTeamDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.smSchoolmate.truePhoto==null ||value.smSchoolmate.truePhoto == '' || value.smSchoolmate.truePhoto == 'undefined' "
                                src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto!=null && value.smSchoolmate.truePhoto != '' && value.smSchoolmate.truePhoto != 'undefined'"
                                :src="imagePath+value.smSchoolmate.truePhoto" mode="aspectFill"></image>
                        </view>
                        <view class="sch_cont">
                            <view class="l-title">
                                {{ (null!= value.smSchoolmate)?(null!=value.smSchoolmate.name?value.smSchoolmate.name:"校友" ):"校友" }}发起“{{value.name}}”一起参与捐赠
                            </view>
                            <view class="sch_cont_bm clearfix">
                                <view class="target">
                                    已参与人数 <text>{{null == value.personNum? 1:value.personNum}}人</text>
                                </view>
                                <view class="got">
                                    已捐赠额度 <text>{{null ==value.gotMoney? 0 : value.gotMoney }}元</text>
                                </view>
                            </view>
                        </view>
                    </view>
                </view>
                <view class="foot">
                    <view v-if="(cur === 0 && null != donPersons && donPersons.length > 2 ) || (cur === 1 && null != teamDonProList && teamDonProList.length>2)"
                        class="more" @click="goMore()">
                        查看更多
                    </view>
                    <view v-if="(cur === 0 && null != donPersons && donPersons.length ==0 ) || (cur === 1 && null != teamDonProList && teamDonProList.length==0)"
                        class="uni-nodata">
                        暂无数据
                    </view>
                </view>
            </view>
        </view>
        <view class="act_footer">
            <view v-show="is_don_team">
                <view class="act_left" open-type="share">
                    <button open-type="share">邀请朋友</button>
                </view>
                <view class="act_right" @click="goTeamDon()">
                    <button> 共同献爱心</button>
                </view>
            </view>
            <view v-show="!is_don_team">
                <view class="act_left_team" open-type="share">
                    <button open-type="share">邀请朋友</button>
                </view>
            </view>
        </view>
        <view class='popup' v-show="is_don_project" @click="goDon()">
            <view class="popup-shade"></view>
            <view class="suspension-hide">
                <image src="/static/img/don.png"></image>
                <view style="color: #FFFFFF;">
                    回馈
                </view>

            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import util from '@/common/util.js';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue"
    import uParse from '@/components/uParse/src/wxParse.vue'
    import store from '@/store/index.js';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';

    export default {
        components: {
            segmentedControl,
            uniLoadMore,
            uParse
        },
        data() {
            return {
                id: '',
                name: '',
                fold: true,
                items: ['个人捐', '共同献爱心'],
                loadingText: '',
                percent: 60,
                cur: 0,
                title: "滚动公告",
                donProject: {},
                imagePath: app.imagePath,
                navMsg: [],
                donPersons: [],
                teamDonProList: [],
                isshow: true,
                boolean_false: app.boolean_str.boolean_false,
                pic: '',
                is_don_project: "",
                is_don_team: ""
            }
        },
        computed: {
            ...mapState(['hasLogin','userInfo','configParams'])
        },
        onLoad(options) {
            this.id = decodeURIComponent(options.id);
            //捐赠项目详情 页面接收传参数值options  options.id
            //  this.getDonDetail();
            //轮播捐赠记录
            //  this.getNav();
            //捐赠记录
            //this.getDonPersons();
        },
        onShow(options) {
            this.is_don_project = this.configParams.is_don_project;
            this.is_don_team = this.configParams.is_don_project;
            this.getDonDetail();
            this.getNav();
            this.getDonPersons();
            //一起捐项目记录
            this.getTeamDonProList();
            console.log(111)
        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.DONDETAIL_SHARE);
            //util.getStatPoint('click','EVENT_DESCRIBE_22');
            var name = '';
            if (this.userInfo.name != undefined || this.userInfo.name != null) {
                name = this.userInfo.name;
            }
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                name = this.userInfo.sysUser.virtualName;
            }
            if (res.from === 'button') { // 来自页面内分享按钮
            }
            var truePath = encodeURIComponent(JSON.stringify('/pages/don/donDetail?id=' + encodeURIComponent(this.id)));
            var img = this.imagePath + this.pic
            if (this.pic == null || this.pic == '') {
                img = '/static/img/man_default.png';
            }
            return {
                title: name + '邀请你一起回馈母校，赶快去看看吧',
                path: '/pages/tabbar/index/index?truePath=' + truePath,
                imageUrl: img
            }
        },
        methods: {
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur == 0) {
                    if (this.donPersons.length == 0) {
                        this.getDonPersons();
                    }
                } else {
                    if (this.donPersons.length == 0) {
                        this.getTeamDonProList();
                    }
                }

            },
            btn: function() {
                this.fold = !this.fold;
            },
            //捐献项目详情
            getDonDetail(projectId) {
                request.http({
                    url: "/app/don/getDonProDetail",
                    data: {
                        id: this.id,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.pic = res.data.data.pic;
                            this.donProject = res.data.data;
                            this.donProject.name = util.strcharacterDiscode(res.data.data.name);
                            this.donProject.content = util.strcharacterDiscode(res.data.data.content);
                            this.donProject.summary = util.strcharacterDiscode(res.data.data.summary);
                            this.defaultMoney = res.data.data.defaultMoney;
                            this.donProject.gotMoney = (null != this.donProject.gotMoney ? this.donProject.gotMoney :
                                0)
                            this.donProject.targetMoney = (null != this.donProject.targetMoney ? this.donProject
                                .targetMoney : 0)
                            this.percent = util.toFix((this.donProject.gotMoney / this.donProject.targetMoney) *
                                100);
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            //轮播捐赠记录
            getNav() {
                request.http({
                    url: "/app/don/getLasterDonRecord",
                    data: {
                        pageNo: 1,
                        pageSize: 5,
                        donProjectId: this.id
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {


                            if (null != res.data.list && res.data.list.length == 0) {
                                this.isshow = false
                            } else {
                                this.isshow = true
                                this.navMsg = res.data.list;
                            }

                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            //捐赠记录
            getDonPersons() {
                request.http({
                    url: "/app/don/getDonRecordByPro",
                    data: {
                        pageNo: 1,
                        pageSize: 5,
                        donProjectId: this.id,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.donPersons = res.data.list;
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            //一起捐记录
            getTeamDonProList() {
                request.http({
                    url: "/app/don/teamDonRecords",
                    data: {
                        pageNo: 1,
                        pageSize: 5,
                        projectId: this.id,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.teamDonProList = res.data.list;
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            goMore() {
                let url = '';
                if (this.cur == 0) {
                    url = '/pages/don/donPersons?id=' + encodeURIComponent(JSON.stringify(this.id)) + '&cut=' +
                        encodeURIComponent(
                            JSON.stringify(0));
                } else {
                    url = '/pages/don/donPersons?id=' + encodeURIComponent(JSON.stringify(this.id)) + '&cut=' +
                        encodeURIComponent(
                            JSON.stringify(1));
                }

                console.log(url)
                uni.navigateTo({
                    url: url
                });
            },
            //发起一起捐
            goTeamDon() {
                //util.getStatPoint('click','EVENT_DESCRIBE_23');
                if (!this.hasLogin) {
                    uni.showToast({
                        icon: 'none',
                        title: '您没有登录，请先登录！'
                    })
                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                } else if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                    uni.showToast({
                        icon: 'none',
                        title: '您个人信息没有审核通过，请先去认证！'
                    })
                } else if (this.donProject.status == 'WILL') {
                    uni.showToast({
                        icon: 'none',
                        title: '项目为预备状态，不能发起共同献爱心'
                    })
                } else if (this.donProject.status == 'HASDONE') {
                    uni.showToast({
                        icon: 'none',
                        title: '项目已经结束，不能发起共同献爱心'
                    })
                } else {
                    let url = '/pages/don/launchTeamDon?donProject=' + encodeURIComponent(JSON.stringify(this.donProject));
                    uni.navigateTo({
                        url: url
                    });
                }
            },
            goJoinIn() {
                //util.getStatPoint('click','EVENT_DESCRIBE_27');
                if (this.donProject.status == 'WILL') {
                    uni.showToast({
                        icon: 'none',
                        title: '项目为预备状态，不能回馈'
                    })
                } else if (this.donProject.status == 'HASDONE') {
                    uni.showToast({
                        icon: 'none',
                        title: '项目已经结束，不能回馈'
                    })
                } else {
                    let url = '/pages/don/donPay?projectId=' + encodeURIComponent(JSON.stringify(this.id)) +
                        "&defaultMoney=" + encodeURIComponent(JSON.stringify(this.defaultMoney)) +
                        "&donProName=" + encodeURIComponent(JSON.stringify(this.donProject.name));
                    uni.navigateTo({
                        url: url
                    });
                }
                // if (this.hasLogin) {

                // } else {
                // 	uni.showToast({
                // 		icon: 'none',
                // 		title: '您没有登陆，请先登录！'
                // 	})
                // }
            },
            goDetail(detail) {
                var url = '/pages_mine/myAssociation/memberDetail?id=' + encodeURIComponent(JSON.stringify(detail.userId));
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
                } else if (detail.userId == undefined) {
                    return;
                } else if (detail.isShow != app.boolean_str.boolean_false) {
                    if (this.cur == 1) {
                        url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail.id));
                    }
                    uni.navigateTo({
                        url
                    });
                }
            },
            goTeamDetail(detail) {
                var url = "";
                if (detail.isShow != app.boolean_str.boolean_false) {
                    if (this.cur == 1) {
                        url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail.id));
                    }
                    uni.navigateTo({
                        url
                    });
                }
            },
            goDon() {
                var url = '/pages/don/donPay';
                uni.navigateTo({
                    url
                });
            }
        },
    }
</script>

<style lang="scss">
	.act_detail {padding: 20rpx;}
    .act_detail .act_head {padding: 0 20rpx;

        box-shadow: 0px 0px 30px 0px rgba(116, 116, 116, 0.15);
        border-radius: 20upx;
        margin: 25upx auto;
        margin-top: 80rpx;
    }

    .act_detail .list_1 {
        padding: 45upx 0;
		.tu_mod {
		    width: 210upx;
		    height: 140upx;
		    border-radius: 8upx;
		    float: left;
		    margin-right: 20upx;
			image {
			    width: 100%;
			    height: 100%;
			}
		}
		.container {
		    float: left;
		    margin-top: 10upx;
		    width: 350rpx;
			.title {
			    font-size: 34upx;
			    font-weight: bold;
			    overflow: hidden;
			    text-overflow: ellipsis;
			    display: -webkit-box;
			    -webkit-line-clamp: 3;
			    -webkit-box-orient: vertical;
			    line-height: 1.2
			}
		}
	}


 

  
    .act_detail .time {
        height: 170upx;
        font-size: 28upx;
        padding: 0upx 50upx;
        letter-spacing: 1upx;
		text {
		    margin-left: 40upx;
		    color: #999999;
		}
		.sj {
		    line-height: 60upx;
		}
    }

    .act_detail .up{line-height: 95upx;height: 95upx;text-align: center;width: 200upx;margin: auto;
		text {
					color: #c79f63;
					font-size: 28upx;
				
				}
			.tu_1 {display: inline-block;
			   padding-left: 40rpx;text-align: center;
		
			    position: relative;
				&:before{
					position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(45deg);left: -6upx;
					top: 0;bottom: 0;margin: auto;
				}
				&:after{
					position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(-45deg);
					left: 6upx;top: 0;bottom: 0;margin: auto;
				}
			}
			
			.tu_2 {display: inline-block;
			   padding-left: 40rpx;text-align: center;
		
			    position: relative;
				&:before{
					position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(-45deg);left: -6upx;
					top: 0;bottom: 0;margin: auto;
				}
				&:after{
					position: absolute;content: "";width: 20upx;height: 1upx;background: #c79f63;transform: rotate(45deg);
					left: 6upx;top: 0;bottom: 0;margin: auto;
				}
			}
		
	} 

   

    

    .act_detail .zk{
		.intro {
		    width: 100upx;
		   
		    margin-top: 40upx;
		    overflow: hidden;
			.intro_jx {position: relative;font-size: $uni-font-size-lg;color: #333333;font-weight: bold;padding-left: 20upx;
					&:before{position: absolute;content:"";width: 9upx;height: 40upx;background-color: #deb051;
					border-radius: 4upx;left:0;top:0;bottom:0;margin: auto 0;}
			}
		}
	} 


 

    .act_detail .wb {
       
        font-size: 28upx;
        color: #999999;
        margin: 30upx 0;
       text-align:justify;
	  
    }

    .act_detail .wb image {
        width: 100% !important;
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
    }

    .act_detail .apply {
        position: absolute;
        right: 25upx;
        top: 45upx;
        font-size: 24upx;
        color: #999999;
    }

    .act_detail .join {
        height: 160upx;
        margin: 0 30upx;
        position: relative;
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
        color: #333333;
    }

    .act_detail .date {
        font-size: $uni-font-size-sm;
        color: #999999;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 35upx 0;
        font-size: $uni-font-size-base;
        color: #CCCCCC;
    }

    .act_detail .act_footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 98upx;
        border-top: 1px solid #262f43;
        z-index: 888;left: 0;
    }

    .act_detail .act_con {
        height: 430upx;
        overflow-y: scroll;
    }

    .act_detail .info {
        width: 100%;
        height: 60upx;
        margin: 0;
        border-radius: 0upx;
        position: fixed;
        top: 0;
        background-color: #999999;left: 0;
    }

    .act_detail .info image {
        margin-top: 4upx;
        margin-left: 0upx;
    }

    .act_detail .tb {
        width: 330upx;
        margin: 0 auto;
    }

    .act_detail .tz {
        width: 27upx;
        height: 32upx;
        float: left;
        margin-right: 10upx;
        margin-top: 6upx;
    }

    .act_detail .tz image {
        width: 100%;
        height: 100%;
    }

    .act_detail .tb text {
        float: left;
        color: white;
        font-size: $uni-font-size-sm;
        margin-left: 12upx;
        line-height: 60upx;
    }

    .act_detail .sch_cont_bm {
        font-size:$uni-font-size-sm;
        color: #999999;margin-top: 20upx;
    }
	.content .activity .sch_cont{width: calc(100% - 93upx);height: auto;margin-left: 20rpx;
}
    progress {
        width: 600upx;
        height: 12upx;
        color: #999999;
        margin-left: 20upx;
        margin-top: -15upx;
        border-radius: 6upx !important;
    }

    .act_detail .rs {
        padding: 45upx 50upx;
        overflow: hidden;
    }

    .act_detail .rs .rs_1 {
        width: 168upx;
        text-align: center;
        float: left;
    }

    .act_detail .rs .rs_2 {
        margin-left: 43upx;
    }

    .act_detail .member {
        font-size: 40upx;
        color: #c79f63;
        font-weight: bold;
    }

    .act_detail .money {
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

    .content .activity {position: relative;
        margin: 0 30upx;
		display: flex;
		align-items: center;justify-content: space-between;padding: 20upx 0;
		&:after{position: absolute;
		content: '';
		left: 0;
		right: 0;
		bottom: 0;
		height: 2upx;
		background: #eaeaea;
		width: 100%;
		margin: auto;
		}
    }

    .content .activity .sch_tu {
        width: 73upx;
        height: 73upx;
        border-radius: 50%;
    }

    .content .activity .sch_tu image {
        border-radius: 50%;
    }

    .activity .tx {
        float: left;
   
    }

    .activity .name {
 
        width: 25%;
    }

    .activity .target_1 {
      
     
        font-size: $uni-font-size-sm;
        width: 30%;
        color: #999999;
    }

    .activity .target_1 text {
        color: #ed5849;
        margin-left: 6upx;
    }


    .list {
        padding-bottom: 100upx;
    }

    .content swiper {
        font-size: $uni-font-size-base;
        color: #FFFFFF;
        margin-left: 12upx;
        margin-top: -4upx;
    }

    .uni-swiper-msg {
        width: 462upx;
        padding: 0;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .uni-swiper-msg swiper {
        font-size: $uni-font-size-base;
        margin-top: 7upx;
        margin-left: 10upx;
    }

    .act_left button {
        width: 50%;
        background-color: white;
    }

    .act_left_team button {
        width: 100%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_center button {
        background-color: #525764;
        color: #FFFFFF;
    }

    .act_right button {
        width: 50%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_footer button:after {
        border-radius: 0;
    }

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: $uni-font-size-base;
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size:$uni-font-size-base;
    }

    .flex-item text {
        height: 96upx;
        display: inline-block;
    }

    .content .sch_cont .l-title {
        width: 100%;
  
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden;
    }

    .top {
        font-size: $uni-font-size-lg;
        font-weight: bold;
        line-height: 1.45;
        text-align: justify;
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
