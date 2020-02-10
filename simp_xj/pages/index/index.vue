<template>
	<view>
        <!--导航头 -->
        <cu-custom bgColor="bg-red" :isBack="false"><block slot="content">交大校友</block></cu-custom>
        
        <!--头部滚动通知 
		<uni-notice-bar v-if="infoMessage!=null" scrollable="true" 
		@getmore="disotherImage" :show-get-more="true"  show-icon="true" more-text="查看详情>" single="true" 
		:text="infoMessage.title" ></uni-notice-bar>-->
        
		<!-- 轮播图-当前为课程-->
		<swiper class="screen-swiper solid-bottom lines-red" :class="dotStyle?'square-dot':'square-dot'" :indicator-dots="true" 
        :circular="true" :autoplay="true" interval="3000" duration="500">
			<swiper-item v-for="(item,index) in swiperList" :key="index">
				<image :src="item.pic" mode="aspectFill"></image>
			</swiper-item>
		</swiper>
		
		<!-- 下划线占位-->
		<view class="solid-bottom solid-top lines-red"></view>
		
		<!-- 领卡与快捷功能区-->
		<view  class="flex justify-center margin" v-if='isLogin'>
			
		</view>
		<view  class="flex justify-arround bg-img margin" style="background-image: url('/static/img/index/reg_card.png');background-size:100% 100%; height:250upx;" v-else>
			<view class="basis-lg margin-left">
				<view class="flex justify-center margin-top text-lg text-brown text-bold">10位</view> 
				<view class="flex justify-center margin-top-sm text-sm text-brown">校友已领取校友卡,你在等什么?</view> 
				<view class="flex justify-center margin-top padding-top-sm solid-top lines-white text-white text-cut">土木工程专业的张三已经领取</view>
			</view> 
			<view class="flex basis-sm justify-center align-center" >
				<view class="text-white text-lg">立即领卡</view> 
			</view>
		</view>
		
		<!-- 内容切换区-->
		<scroll-view scroll-x class="bg-white text-lg nav" scroll-with-animation :scroll-left="scrollLeft">
			<view class="cu-item" :class="'news'===tabCur?'text-black cur text-blod ':''"  @tap="tabSelect('news')" >
				母校动态
			</view>
			<view class="cu-item" :class="'activity'==tabCur?'text-black cur text-blod':''"  @tap="tabSelect('activity')" >
				校友活动
			</view>
		</scroll-view>
		
		<view class="cu-card article" v-if="'news'===tabCur"> 
			<view class="cu-item shadow margin-bottom-sm">
				<view class="content margin-top align-center">
					<image src="/static/tmp/index/index1.jpg" mode="aspectFill"></image>
					<view class="desc">
						<view class="text-gray text-lg text-bold text-cut" style="max-width:400upx">测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试</view>
						
						<view class="text-content "> 折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而自由！</view>
						<view class="flex justify-end">
							<view class="cu-tag bg-green light sm round">2019-09-13</view>
						</view>
					</view>
				</view>
			</view>
			<view class="cu-item shadow margin-bottom-sm">
				<view class="content margin-top align-center">
					<image src="/static/tmp/index/index2.jpg" mode="aspectFill"></image>
					<view class="desc">
						<view class="text-gray text-lg text-bold text-cut" style="max-width:400upx">测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试测试新闻1测试</view>
						
						<view class="text-content "> 折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而自由！</view>
						<view class="flex justify-end">
							<view class="cu-tag bg-green light sm round">2019-09-12</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="cu-card article" v-if="'activity'===tabCur">
			<view class="cu-item shadow margin-bottom-sm">
				<view class="content margin-top align-center">
					<image src="/static/tmp/index/index3.jpg" mode="aspectFill"></image>
					<view class="desc">
						<view class="text-gray text-lg text-bold text-cut" style="max-width:400upx">活动测试活动测试</view>
						
						<view class="text-content"> 折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而自由！</view>
						<view class="flex justify-between">
							<view class="cu-tag bg-red light sm round">校友总会</view>
							<view class="text-sm text-gray"><text class="text-red">10</text>人已报名</view>
						</view>
					</view>
				</view>
			</view>
			<view class="cu-item shadow margin-bottom-sm">
				<view class="content margin-top align-center">
					<image src="/static/tmp/index/index1.jpg" mode="aspectFill"></image>
					<view class="desc">
						<view class="text-gray text-lg text-bold text-cut" style="max-width:400upx">活动测试活动测试活动测试</view>
						<view class="text-content "> 折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而自由！</view>
						<view class="flex justify-between">
							<view class="cu-tag bg-red light sm round">校友总会</view>
							<view class="text-sm text-gray"><text class="text-red">30</text>人已报名</view>
						</view>
					</view>
				</view>
			</view>
		</view>

        <!--首页弹窗-->
        <view class='suspension-show' v-show='show_mes&&infoMessage!=null'>
            <view class='toastbg' @click='disotherremark()'></view>
            <view class='showToast'>
                <image v-if="infoMessage.coverImage!=null&&infoMessage.coverImage!=''" :src="infoMessage.coverImage" mode="aspectFill" @click='disotherImage(mediaId)'></image>
                <view class="close" @click='disotherremark()'></view>
            </view>
        </view>        
	</view>
</template>

<script>
	import app from '@/common/app.js';
	import util from '@/common/util.js';
	import request from '@/common/request.js';
    //import store from '@/store/index.js';
	export default {
		data() {
			return {
				isLogin: false,
				swiperList:[
					{	name:'示例图1',
						pic:'/static/tmp/index/index1.jpg',
					},{	
						name:'示例图2',
						pic:'/static/tmp/index/index2.jpg',
					},{	
						name:'示例图3',
						pic:'/static/tmp/index/index3.jpg',
					}
				],
				tabCur: 'news',

                show_mes:false,
				openid:'',
				code: '',
				current: 0,
				mode:'round',
				courseList: [],
				mallSpuList: [],
				scheduleList: [],
                infoMessage:null,
				nowDate:new Date().getTime(),
                showLoading:false,
                account:{}
			}
		},
		onLoad() { 
			//延迟加载弹窗
			// var that = this;
			// setTimeout(function(){
			// 	//延迟弹窗
			// 	that.show_mes=true;
			// 	// debugger
			// 	var nowDate=new Date().getTime();
			// 	var showDate=uni.getStorageSync(app.noticeTime);
			// 	var hasTime=(nowDate-showDate)<app.noticeStep;//时差小于一天不显示
			// 	if(hasTime){
			// 		that.show_mes=false;
			// 	}
			// }, 200);
            //this.loadIndexRes();
		},
		onShow() {
            // this.isLogin=store.state.isLogin;
            // if(this.isLogin){
            //     //获取用户信息
            //     var userInfo=uni.getStorageSync(app.storageKey.userInfo);
            //     if(userInfo.sysAccount!=null){
            //         this.account.account = userInfo.sysAccount.account;
            //         this.account.name = userInfo.sysAccount.name;
            //         this.account.point = userInfo.sysAccount.point;
            //         this.account.credit = userInfo.sysAccount.credit;
            //         this.account.type=userInfo.sysAccount.type;
            //     }
            // }
            // this.loadSchedule();
		},
		methods: {
            toPage(url){
                util.toPage(url);
            },
			tabSelect(e) {
				this.tabCur = e;
			},
			loadIndexRes(){
                uni.showLoading({
                    title: '努力加载中...',
                    mask: true
                });
				request.http({
					url: app.serverPath + '/index/index',
					data: {},
					success: (res) => { //组装首页数据
                        var courseList=res.data.data.courseList;
                        //course获取类型键值
                        for(var i=0;i<courseList.length;i++){
                            var typeName=util.getDictByValue(courseList[i].type,"COURSE_TYPE").label;
                            courseList[i].typeName=typeName;
                        }
                        this.courseList = courseList;
						this.mallSpuList = res.data.data.mallSpuList;
                        if(res.data.data.infoMessage!=null){
                            this.infoMessage = res.data.data.infoMessage;
                        }
                        //隐藏系统加载条
                        uni.hideLoading();
					},
					fail: (res) => {
						console.log(res);
					}
				})
			},
            loadSchedule(){
                request.http({
                	url: app.serverPath + '/index/loadSchedule',
                	data: {},
                	success: (res) => { //组装首页数据
                        var courseList=res.data.data;
                		this.scheduleList = courseList;
                	},
                	fail: (res) => {
                		console.log(res);
                	}
                })
            },
            //弹框 点击其他地方
            disotherremark() {
                this.show_mes = false;
				uni.setStorageSync(app.noticeTime,this.nowDate);
            },
            disotherImage() {
                this.show_mes = false;
				var id=this.infoMessage.id;
				uni.setStorageSync(app.noticeTime,this.nowDate);
                //跳转至详情页
                uni.navigateTo({
                    url: '/pages/index/mediaMessage?id='+ id
                }); 
            }
		}
	}
</script>

<style lang="scss">

    .suspension-show { //页面弹窗
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        right: 0;
        z-index: 9;
        .toastbg {
            opacity: 0.8;
            background-color: black;
            position: absolute;
            width: 100%;
            min-height: 100vh;
        }
        .showToast {
            text-align: center;
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            margin: auto;
            height: 760upx;
            image {
                width: 600upx;
                height: 800upx;
            }
            .close {
                width: 120rpx;
                height: 120rpx;
                position: relative;
                border: solid 1px #fff;
                border-radius: 50%;
                margin: 30rpx auto 0;
            }
            .close:before {
                position: absolute;
                content: "";
                width: 40px;
                height: 1px;
                background: #fff;
                transform: rotate(-45deg);
                top: 0px;
                bottom: 0;
                margin: auto;
                left: 0;
                right: 0;
            }
            
            .close:after {
                position: absolute;
                content: "";
                width: 40px;
                height: 1px;
                background: #fff;
                transform: rotate(45deg);
                top: 0px;
                bottom: 0;
                margin: auto;
                left: 0;
                right: 0;
            }
        }
    }
</style>
