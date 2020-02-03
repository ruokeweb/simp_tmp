<template>
	<view class="content">
		<view class="serv_banner">
			<image src="../../../static/img/server.png" mode="aspectFill"></image>
		</view>
		<!-- <view class="content_wb">
			<view class="jx"></view>
			<text>{{message[current]}}</text>
		</view> -->
		<view class="kuang"></view>

		<view class="card">
			<view class="xyk xyk_1" @click="notNotes">
				<view class="tu_1">
					<image src="/static/img/mine/icon-fx.png" mode=""></image>
				</view>
				<navigator url="">返校指南</navigator>
			</view>
			<view class="xyk" @click="notNotes">
				<view class="tu_1">
					<image src="/static/img/mine/icon-zj.png" mode=""></image>
				</view>
				<navigator url="">证件补办</navigator>
			</view>
			<view class="xyk" @click="notNotes">
				<view class="tu_1">
					<image src="/static/img/mine/icon-cd.png" mode=""></image>
				</view>
				<navigator url="">场地租用</navigator>
			</view>
			<view class="xyk" @click="notNotes">
				<view class="tu_1">
					<image src="/static/img/mine/icon-ts.png" mode=""></image>
				</view>
				<navigator url="">图书借阅</navigator>
			</view>
		</view>
	</view>

	</view>
</template>

<script>
	import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue'
	import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import util from '@/common/util.js';
	import '@/static/css/index.css'
	import {
		mapState
	} from 'vuex';
	export default {
		components: {
			uniSwiperDot,
			segmentedControl
		},
		data() {
			return {
				current: 0,
				mode: 'long',
				dotsStyles: {},
				isLogin: true
			}
		},
		onLoad() {},
        onTabItemTap(){
            //util.getStatPoint('click','EVENT_DESCRIBE_48')   
        },
		computed: {
			...mapState(['hasLogin'])
		},
		methods: {
			//判断是否登录   if (this.hasLogin)  
			change(e) {
				this.current = e.detail.current
			},
			notNotes() {
				uni.showToast({
					icon: 'none',
					title: "功能暂未开通"
				});
			}
		}
	}
</script>
<style>
	.serv_banner {
		position: relative;
		height: 380upx;
	}

	.serv_banner image {
		width: 100%;
		height: 100%;
	}
 .content .content_wb text {
	    margin-top: -4upx;
	}
	navigator {
		text-align:center;margin-top: 10rpx;

	}
</style>
