<template>
	<view class="uni-swiper__warp">
		<slot />
		<view v-if="mode === 'long'" :style="{'bottom':dots.bottom + 'px'}" class="uni-swiper__dots-box">
			<view v-for="(item,index) in info" :class="[index === current&&'uni-swiper__dots-long']" :style="{
				'width':(index === current? dots.width*5:dots.width ) + 'px','height':dots.height +'px' ,'background-color':index !== current?dots.backgroundColor:dots.selectedBackgroundColor,'border':index !==current ? dots.border:dots.selectedBorder}" :key="index" class="uni-swiper__dots-item ">
			</view>
		</view>	
		<view v-if="mode === 'long'" :style="{'bottom':dots.bottom + 'px'}" class="uni-swiper__dots-box">
			<view v-for="(item,index) in info" :class="[index === current&&'uni-swiper__dots-long']" :style="{
				'width':(index === current? dots.width*5:dots.width ) + 'px','height':dots.height +'px' ,'background-color':index !== current?dots.backgroundColor:dots.selectedBackgroundColor,'border':index !==current ? dots.border:dots.selectedBorder}" :key="index" class="uni-swiper__dots-item ">
			</view>
		</view>	
	</view>
</template>
<script>
	export default {
		name: 'UniSwiperDot',
		props: {
			info: {
				type: Array,
				default () {
					return []
				}
			},
			current: {
				type: Number,
				default: 0
			},
			dotsStyles: {
				type: Object,
				default () {
					return {}
				}
			},
			// 类型 ：default(默认) indexes long nav
			mode: {
				type: String,
				default: 'long'
			},
		},
		data() {
			return {
				dots: {
					width: 6,
					height: 6,
					color: '#fff',
					backgroundColor: 'rgba(255, 255, 255, 0.4)',
					selectedBackgroundColor: '#fff'
				}
			}
		},
		watch: {
			dotsStyles(newVal) {
				this.dots = Object.assign(this.dots, this.dotsStyles)
			},
			mode(newVal) {
				if (newVal === 'indexes') {
					this.dots.width = 30
					this.dots.height = 6
				} else {
					this.dots.width = 6
					this.dots.height = 6
				}
			}

		},
		created() {
			if (this.mode === 'indexes') {
				this.dots.width = 30
				this.dots.height = 6
			}
			this.dots = Object.assign(this.dots, this.dotsStyles)
		}
	}
</script>

<style>
	.uni-swiper__warp {
		position: relative;
		width: 100%;
		box-sizing: border-box;
		overflow: hidden;
	}

	.uni-swiper__dots-box {
		position: absolute;
		bottom: 20upx;
		display: flex;
		justify-content: center;
		align-items: center;
		box-sizing: box-sizing;
		width: 100%;
	}

	.uni-swiper__dots-item {
		flex-shrink: 0;
		width: 16upx;
		border-radius: 50%;
		margin-left: 12upx;
		background: rgba(255, 255, 255, 1);
		transition: all 0.2s linear;
	}

	.uni-swiper__dots-item:first-child {
		margin: 0;
	}

	.uni-swiper__dots-long {
		border-radius: 100upx;
	}

	.uni-swiper__dots-nav-item {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		font-size: 28upx;
		color: #fff;
		box-sizing: box-sizing;
		margin: 0 30upx;
	}

</style>