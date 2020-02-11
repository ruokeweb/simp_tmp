<template>
	<view>
		<!--导航头 -->
		<cu-custom bgColor="bg-red" :isBack="false"><block slot="content">交大校友</block></cu-custom>
		<!-- 轮播图-当前为课程-->
		<swiper class="screen-swiper solid-bottom lines-red" :class="dotStyle?'square-dot':'square-dot'" :indicator-dots="true" 
		:circular="true" :autoplay="true" interval="3000" duration="500">
			<swiper-item v-for="(item,index) in swiperList" :key="index">
				<image :src="item.pic" mode="aspectFill"></image>
			</swiper-item>
		</swiper>
		
		<!--搜索-->
		
		<!--活动列表-->
		<view class="cu-card article">
			<view class="cu-item shadow margin-bottom-sm" v-for="(item,index) in activityList" :key="index">
				<view class="content margin-top align-center">
					<image :src="item.pic" mode="aspectFill"></image>
					<view class="desc">
						<view class="text-gray text-lg text-bold text-cut" style="max-width:400upx">{{item.title}}</view>
						
						<view class="text-content">{{item.content}}</view>
						<view class="flex justify-between">
							<view class="cu-tag bg-red light sm round">校友总会</view>
							<view class="text-sm text-gray"><text class="text-red">10</text>人已报名</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!--更多-->
		<uni-load-more :status="status" showIcon="true" @clickLoadMore="pageList" v-if="activityList.length > 0"></uni-load-more>
		
	</view>
</template>

<script>
	
	import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
	export default {
		data() {
			return {
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
				activityList:[
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月13'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					},
					{
						title:'测试1',
						content:'折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而',
						pic:"/static/tmp/index/index3.jpg",
						date:'2019年10月12'
					}
				]
			}
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
