<template>
    <view class="act_detail">
        <view class="act_head">
            <!-- 顶部活动详情 -->
            <view>
                <view class="list_1">
                    <image v-if="image!=''&&image!=null" class="uni-media-list-logo" :src="imagePath + image" mode="aspectFill"></image>
                    <image v-if="image==''||image==null" class="uni-media-list-logo" src="/static/img/act/act_default.png"
                        mode="aspectFill"></image>
					{{name==null?'':name}}
                    <view class="container">
                    </view>
                </view>
                <view class="time">
					<view class="sj">发起方<text class="dept">{{assoName==null?'':assoName}}</text></view>
                    <view class="sj">报名时间<text>{{signStartDate}}至{{signEndDate}}</text></view>
                    <view class="sj">活动时间<text>{{startDate}}至{{endDate}}</text></view>
                    <view class="sj">活动地点<text>{{area}}</text></view>
                </view>
            </view>
            <view class="up" @click="btn" v-show="fold">
                <text class="tu_1">展开</text>
            </view>
            <view class="zk" @click="btn" v-show="!fold">
                <view class="intro">
                    <view class="intro_jx">简介</view>
               
                </view>
                <view v-if="content!=''" class="wb">
                    <uParse :content="content" @preview="preview" @navigate="navigate" />
                </view>
                <view v-if="content==''" class="wb">
                    无
                </view>
                <view class="up">
                    <text class="tu_2">收起</text>
                </view>
            </view>
        </view>
        <view class="part">
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="applay_data clearfix">
                <text>已报名伙伴</text>
				<view class="apply">
				    {{readyNo!=null?readyNo:0}}人已报名
				</view>
            </view>
            
        </view>
        <view class="act_con">
            <view class="join" v-for="(value, key) in readyNoList" :key="key">
                <view class="tx" @click="goInfo(value)">
                    <image v-if="value.virtualPhoto==null||value.virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                    <image v-if="value.virtualPhoto!=null&&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto" mode="aspectFill"></image>
                </view>
                <view class="name">{{value.virtualName==null?'':value.virtualName}}</view>
                <view class="date">
                    {{value.createDate}}
                </view>
            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>
        <view class="act_footer">
            <view class="act_left">
                <button open-type="share">邀请好友</button>
            </view>
            <view class="act_right" v-show="!hasEnroll" @click="goEnroll()">
                <button> 我要报名</button>
            </view>
            <view class="act_right1" v-show="hasEnroll" @click="isEnroll()">
                <button> 我要报名</button>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import uParse from '@/components/uParse/src/wxParse.vue'; //富文本框转义
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
        computed: {
            ...mapState(['hasLogin']),
            ...mapState(['userInfo'])
        },
        data() {
            return {
                id: '',
                name: '',
                image: '',
                content: '',
                endDate: '',
                signEndDate: '',
                signStartDate: '',
                startDate: '',
                area: '',
                limitNo: 0,
                readyNo: 0,
                readyNoList: [],
                assoName: '',
                fold: true,
                items: ['已报名伙伴'],
                status: "noMore",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                hasEnroll: false, //判断是否已经报名,
                imagePath: app.imagePath,
                pageNo: 0,
                pageSize: 10,
                detailflag: false,
                isLogin: false,
                flag:false,
                actStatus:''
            }
        },
        onReachBottom() {
            this.status = "loading";
            if (this.detailflag) {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/act/getAttendSmByAct',
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
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }

                        }
                    },
                    error: (res) => {}
                });
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
            this.isLogin = this.hasLogin;
        },
        onShow(event) {
            this.flag=false;
            this.hasSign();

            if (this.name == '') {
                this.getNews(this.id);
            }
            if (this.readyNoList.length == 0) {
                this.pageNo = 0;
                this.getActEnroll();
            }

        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.ACTDETAIL_SHARE);
            
            //util.getStatPoint('click','EVENT_DESCRIBE_33');
            var name = '';
            if (this.userInfo.name != undefined || this.userInfo.name != null) {
                name = this.userInfo.name;
            }
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                name = this.userInfo.sysUser.virtualName;
            }
            var truePath = encodeURIComponent(JSON.stringify('/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(
                this.id))));
            var img =this.imagePath + this.image;
            if(this.image ==null||this.image==''){
              img='/static/img/act/act_default.png ';
            }
            console.log(img);
            return {
                title: name + '邀请你一起参加母校活动，赶快去报名吧',
                path: '/pages/tabbar/index/index?truePath=' + truePath,
                imageUrl: img
            }
        },
        methods: {
            btn: function() {
                this.fold = !this.fold;
            },
            getNews(e) {
                request.http({
                    url: '/app/act/getActDetail',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.name = util.strcharacterDiscode(res.data.data.name);
                            this.image = res.data.data.image;
                            this.content = util.strcharacterDiscode( res.data.data.content);
                            this.assoName = res.data.data.assoName;
                            this.endDate = res.data.data.endDate;
                            this.signEndDate = res.data.data.signEndDate;
                            this.signStartDate = res.data.data.signStartDate;
                            this.startDate = res.data.data.startDate;
                            this.area = res.data.data.area;
                            if(undefined!=res.data.data.readyNo){
                                this.readyNo = res.data.data.readyNo;
                            }
                            this.limitNo = res.data.data.limitNo;
                            this.actStatus =res.data.data.status
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            getActEnroll(e) {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: '/app/act/getAttendSmByAct',
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
            goInfo(e) {
                if (!this.hasLogin) {
                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("请先登录");
                } else if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {

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

            },
            goEnroll(e) {
                //util.getStatPoint('click','EVENT_DESCRIBE_34');
                if(this.flag){
                    return false;
                }
                this.flag=true;
                //首先判断是否登录
                //然后判断活动是否能报名，包括是否在报名范围内，否是还有名额
                if (this.isLogin) {
                    if (this.userInfo.cardStatus != app.CARD_STATUS.NORMAL_CARD_STATUS) {
                        let url = '/pages/login/cardstatusAuthing';
                        uni.navigateTo({
                            url: url
                        });
                        this.showpageToast("您的信息还未审核通过，不能报名活动");
                    } else {
                        if (this.actStatus =='DONE') {
                            this.showpageToast("报名已经结束");
                            return false;
                        }
                        if (this.actStatus =='READAY') {
                            this.showpageToast("报名还未开始");
                            return false;
                        }
                        if (this.limitNo > this.readyNo) {
                            let url = '/pages/act/partakeAct?id=' + encodeURIComponent(JSON.stringify(this.id)) +
                                '&image=' + encodeURIComponent(JSON.stringify(this.image))+
                                '&actProName=' + encodeURIComponent(JSON.stringify(this.name)) +
                                '&startDate=' + encodeURIComponent(JSON.stringify(this.startDate))+
                                '&area=' + encodeURIComponent(JSON.stringify(this.area));
                                
                            uni.navigateTo({
                                url: url
                            });
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
            isEnroll() {
                this.showpageToast("请勿重复报名");
            },
            goLogin(e) {
                this.showpageToast("请先登录");
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            hasSign(e) {
                if (this.isLogin) {
                    request.http({
                        url: '/app/act/getNumByUserId',
                        data: {
                            activityId: this.id
                        },
                        header: 'application/x-www-form-urlencoded',
                        method: "POST",
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
            }
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
        padding: 45upx 50upx;
        overflow: hidden;
    }

    .act_detail .list_1 image {
        width: 210upx;
        height: 140upx;
        border-radius: 8upx;
        float: left;
    }

    .act_detail .list_1 .container {
        float: left;
        margin-left: 10upx;
        width: 58%;
    }

	.act_detail .list_1 {
        font-size: $uni-font-size-lg;
        font-weight: bold;
		line-height: 1.45;
		text-align:justify;
	}
	
    .act_detail .list_1 .container .title {
        font-size: 34upx;
        font-weight: bold;
        /*overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;*/
        line-height: 1.2;
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
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .act_detail .time {
        /* height: 210upx; */
        font-size: 28upx;
        padding: 0upx 50upx;
        letter-spacing: 1upx;
    }

    .act_detail .time text {
        margin-left: 40upx;
        color: #999999;
    }

    .act_detail .time .sj {
        line-height: 60upx;
        /* height: 60rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap; */
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
        .intro_jx {position: relative;font-size: $uni-font-size-lg;color: #333333;font-weight: bold;padding-left: 20upx;
        		&:before{position: absolute;content:"";width: 9upx;height: 40upx;background-color: #deb051;
        		border-radius: 4upx;left:0;top:0;bottom:0;margin: auto 0;}
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
        font-size: 28upx;
        color: #999999;
        margin: 30upx 50upx;
        line-height: 1.5;
        letter-spacing: 1upx;
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
		
		.applay_data {
		    height: 80upx;
		    border-bottom: 1upx solid #f6f6f6;
		    padding-left: 30upx;
		    line-height: 80upx;
		    margin-top: 20upx;padding-right: 30upx;
		
		
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
			    color: #999999;height: 80upx;line-height: 80upx;
			}
		}	
		
    }

   

    .act_detail .join {display: flex;align-items:center;justify-content:space-between;
       margin: 0 30upx;
        padding: 20upx 0;
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
        width:73upx;
        height: 73upx;
        border-radius: 50%;
        float: left;
        
    }

    .act_detail .tx image {
        width: 100%;
        height: 100%;
        border-radius: 50%;

    }

    .act_detail .name {
		width: calc(100% - 333upx);padding-left: 20upx;
        font-size: $uni-font-size-base;
        color: #333333;
    }

    .act_detail .date {      
        font-size: 24upx;
        color: #999999;width: 240upx;text-align: right;
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
    }

    .act_left button {
        width: 50%;
        background-color: white;
    }

    .act_right button {
        width: 50%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_right1 button {
        width: 50%;
        background-color: #A8A8A8;
        color: #FFFFFF;
    }

    .act_detail .act_con {
        overflow-y: scroll;
        padding-bottom: 100upx;margin-top: 20upx;
    }

    .act_footer button:after {
        border-radius: 0;
    }

    
	.dept {
		margin-left: 70rpx !important;
	}
	
	.uni-media-list-logo {
		float: left;
	}
</style>
