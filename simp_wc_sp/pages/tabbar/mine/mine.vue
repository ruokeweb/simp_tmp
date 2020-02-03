<template>
	<view>
		<view class="per_center" v-if="isLogin==true">
			<view class="top_bg">
				<image src="/static/my.png" mode=""></image>
			</view>
			<view class="head_ph">
				<image v-if="virtualPhoto == null || virtualPhoto == ''" src="/static/img/default.png" class="head" @click="changeAvatar"></image>
				<image v-else-if="virtualPhoto!=null && virtualPhoto.length == 24" :src="imagePath + virtualPhoto" class="head"
				 @click="changeAvatar"></image>
				<image v-else="virtualPhoto!=null && virtualPhoto.length != 24" :src="virtualPhoto" class="head" @click="changeAvatar"></image>
				<!-- <image class="head" style="" :src='url' @click="changeAvatar"></image> -->
				<image class="ph" src='/static/head_cam.png' @click="changeAvatar"></image>
			</view>
			<view class="head_info">
				<view>
					<text v-if="editName==false" class="nick-name">{{virtualName}}</text>
					<input v-if="editName==true" type="text" v-model="virtualName" maxlength=8 style="width:140upx;display:inline-block;position: relative;top:25upx;border:1px solid #eee;">
					<image v-if="editflag==true" src="/static/edit.png" class="edit" style="display:inline-block" @click="edit"></image>
					<image v-if="saveflag==true" src="/static/submit.png" class="edit" style="display:inline-block" @click="save">
					</image>
				</view>
				<!-- <view><text class="sex">{{sex}}</text></view> -->
			</view>
		</view>
		<view v-if="isLogin==false" style="height: 300upx; float: none;" class="banner">
			<image src="/static/img/jbanner.png" mode=""></image>
			<button class="login-btn" @click="gotologin">登录</button>
		</view>
		<view style="background-color: #f2f2f2;padding-bottom: 40upx;">
			<uni-list>
				<uni-list-item :show-badge="true" :badge-text="num" type="backstyle" title="通知消息" thumb="/static/img/my/myInfro.png"
				 @click="onClickMessage" />
				<uni-list-item :show-badge="true" title="校友卡" thumb="/static/img/my/myCard.png" @click="onClickCard" />
			</uni-list>
			<view style="height: 40upx;"></view>
			<uni-list>
				<uni-list-item :show-badge="true" title="我的信息" thumb="/static/img/my/myBase.png" @click="onClickMine" />
				<uni-list-item :show-badge="true" title="我的回馈" thumb="/static/img/my/myDon.png" @click="onClickDon" />
				<uni-list-item :show-badge="true" title="我的活动" thumb="/static/img/my/myAct.png" @click="onClickAct" />
				<uni-list-item :show-badge="true" title="我的校友会" thumb="/static/img/my/mySch.png" @click="onClickAss" />
			</uni-list>
			<view style="height: 40upx;"></view>
			<uni-list>
				<uni-list-item :show-badge="true" title="设置" thumb="/static/img/my/mySetting.png" @click="onClickSetting" />
			</uni-list>
		</view>
	</view>
</template>

<script>
	import uniList from '@/components/uni-list/uni-list.vue'
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
	import request from '@/common/request.js';
	import app from '@/common/app.js'; //公共变量
	import loadcache from '@/common/loadcache.js';
	import '@/static/css/index.css';
	import util from '@/common/util.js';
	import {
		mapState,
		mapMutations
	} from 'vuex';
	export default {
		components: {
			uniList,
			uniListItem,
		},
		data() {
			return {
				extraIcon1: {
					color: '#007aff',
					size: '22',
					type: 'info-filled'
				},
				extraIcon2: {
					color: '#4cd964',
					size: '22',
					type: 'spinner'
				},
				virtualPhoto: "",
				virtualName: "",
				sex: "男",
				num: '0',
				isLogin: false,
				editName: false,
				imagePath: app.imagePath,
				editflag: true,
				saveflag: false

			}
		},
		computed: {
			...mapState(['hasLogin', 'userInfo'])
		},
		onTabItemTap() {
			//util.getStatPoint('click','EVENT_DESCRIBE_44')
		},
		methods: {
			...mapMutations(['setUserInfo']),
			gotologin() {
				uni.navigateTo({
					url: "/pages/login/login"
				})
			},
			onClickMessage() {
				//util.getStatPoint('click','EVENT_DESCRIBE_38');
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/message/message"
					})
				}

			},
			onClickMine() {
				//util.getStatPoint('click','EVENT_DESCRIBE_39');
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/infomation/info/info"
					})
				}
			},
			errorChange(e) {
				console.log(e.tar)
				this.url =
					"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1114585158,1026985006&fm=26&gp=0.jpg";
			},
			onClickCard() {
				//util.getStatPoint('click','EVENT_DESCRIBE_40');
				if (!this.hasLogin) {
					uni.navigateTo({
						url: "/pages/login/login"
					})
				} else {
					if (this.userInfo.cardStatus == app.CARD_STATUS.NORMAL_CARD_STATUS) {
						uni.navigateTo({
							url: "/pages_mine/myCard/myCard"
						});
					} else {
						request.http({
							url: '/app/index/getMyCard',
							method: "POST",
							data: {},
							success: (res) => {
								if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
									uni.navigateTo({
										url: res.data.data.pages + "?resultContent=" +
											encodeURIComponent(res.data.data.resultContent)
									});
								} else {
									uni.navigateTo({
										url: "/pages/login/cardstatusAuthing"
									});
								}
							},
							error: (res) => {

							}
						});
					}
				}

			},
			onClickDon() {
				//util.getStatPoint('click','EVENT_DESCRIBE_41');
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/myDon/myDon"
					})
				}
			},
			onClickAct() {
				//util.getStatPoint('click','EVENT_DESCRIBE_42');
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/myAct/myAct"
					})
				}
			},
			onClickAss() {
				//util.getStatPoint('click','EVENT_DESCRIBE_43');
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/myAssociation/myAssociation"
					})
				}
			},
			onClickSetting() {
				if (this.itemclick()) {
					uni.navigateTo({
						url: "/pages_mine/setting/setting"
					})
				}
			},
			changeAvatar() {
				uni.showActionSheet({
					itemList: ['拍照', '从相册选择'],
					success: (res) => {
						var index = res.tapIndex + 1;
						uni.chooseImage({
							count: 1,
							sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
							sourceType: index == 1 ? ['camera '] : ['album'],
							success: (res) => {
								var keyTime = new Date().getTime().toString();
								const tempFilePaths = res.tempFilePaths;
								uni.uploadFile({
									url: app.serverPath + '/app/uploadVirtualPhoto', //仅为示例，非真实的接口地址
									filePath: tempFilePaths[0],
									name: 'file',
									header: {
										'content-type': 'multipart/form-data',
										'Authorization': wx.getStorageSync("token"),
										'Time': keyTime,
										'Key': util.Encrypt(app.AES_KEY, keyTime)
									},
									success: (success) => {
										let res = JSON.parse(success.data);
										if (res.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
											console.log(res.data);
											this.setUserInfo(res.data);
											this.virtualPhoto = res.data.sysUser.virtualPhoto;
										} else {
											uni.showToast({
												icon: 'none',
												title: res.msg
											});
										}

									},
									fail: (error) => {
										console.log(error);
									}
								});
							}
						});
					},
					fail: (res) => {
						console.log(res.errMsg);
					}
				});

			},
			itemclick() {
				if (!this.isLogin) {
					uni.navigateTo({
						url: "../../login/login"
					})
					return false;
				}
				return true;
			},
			edit() {
				this.editName = !this.editName;
				this.editflag = false;
				this.saveflag = true;
			},
			save() {
				this.editName = !this.editName;
				this.editflag = true;
				this.saveflag = false;
				if (this.virtualName.length > 8) {
					uni.showToast({
						icon: 'none',
						title: '昵称最大不能超过8个字符'
					});
					return false;
				} else {
					request.http({
						url: '/app/updateVirtualName',
						method: "POST",
						header: 'application/x-www-form-urlencoded',
						data: {
							virtualName: this.virtualName
						},
						success: (res) => {
							if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
								this.setUserInfo(res.data.data);
							}
						},
						error: (res) => {

						}
					});
                    return false;
				}
			}
		},
		onShow() {
			this.isLogin = this.hasLogin;
			if (!this.isLogin) {
				this.num = 0;
			} else {
				this.virtualPhoto = this.userInfo.sysUser.virtualPhoto;
				this.virtualName = this.userInfo.sysUser.virtualName;
				request.http({
					url: '/app/getMesCountBy',
					method: "POST",
					data: {},
					success: (res) => {
						if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
							this.num = '' + res.data.data;
						} else {
							this.num = '0';
						}
					},
					error: (res) => {

					}
				});

			}
		}
	}
</script>

<style lang="scss">
	// page {
	// 	display: flex;
	// 	flex-direction: column;
	// 	box-sizing: border-box;
	// 	background-color: #fff
	// }
	.per_center{position: relative;display: flex;align-items: center;justify-content: center;height: 270upx;
		.top_bg{position: absolute;
			width: 100%;
			height: 100%;
			image{width: 100%;height: 100%;opacity: 0.3;}			
		}
	}
	.head_ph{position: relative;width: 170upx;height: 170upx;
		.head {
			width: 170upx;
			height: 170upx;
			border-radius: 50%;		
		}

		.ph {
			width: 148upx;
			height: 40upx;
			position: absolute;
			right:0;
			bottom: 0;left: 0;margin: auto
			
		}
	}
	
	.head_info{margin-left: 20upx;}
	.banner image {
		width: 100vw;
		height: 260upx;
		margin-top: 157upx;
	}

	.content {
		text-align: center;

	}

	.content_item {
		margin-top: 30upx;
	}

	.action-row-right {
		display: flex;
		flex-direction: row;
		justify-content: flex-end;
		padding-left: 80upx;
	}

	.flex-item {
		// position: absolute;
		// width: 49%;
		// height: 200upx;
		// left: -62upx;
	}

	.uni-badge-backstyle {
		color: #fff;
		background-color: #FD2E32
	}

	

	.info {
		// margin-top: 100upx;
		// padding-left: 474upx;
	}

	.nick-name {
		font-size: $uni-font-size-base;
		color: #4b505d;
		font-weight: bold;
		position: relative;		
		height: 65rpx;
		line-height: 65rpx;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.sex {
		font-size: 30upx;
		color: #999;
	}

	.login-btn {
		width: 206upx;
		height: 66upx;
		line-height: 66upx;
		border-radius: 33upx;
		margin: 0 auto;
		position: absolute;
		top: 120upx;
		left: 275upx;
		background: #272d3d;
		color: #fff;
		font-size: 30upx;
	}

	.uni-list:before,
	.uni-list:after {
		height: 0 !important;
	}

	uni-list-item:last-child view:after {
		height: 0 !important;
	}


	.uni-list-item__icon-img {
		height: 32upx;
		width: 32upx;
	}

	

	.edit {
		width: 30upx;
		height: 30upx;
		display: inline-block;
		position: relative;
		margin-left: 15upx;
	}

	.uni-row .top image {
		// width: 100vw;
		// height: 280upx;
		// opacity: 0.3;
	}

	.uni-badge-success {
		color: #FFFFFF !important;
		background-color: #FD2E32 !important;
		position: relative;
		bottom: 2px;
	}
</style>
