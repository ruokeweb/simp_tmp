<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">{{username}}</text>
                <input class="input_reg" placeholder-style="color:#ccc;" v-model.trim="usernameshow" data-key="usernameshow"
                    @click="onClickphone" disabled="true" />
            </view>
            <view class="content_view_item">
                <text class="text_reg">密码</text>
                <input class="input_reg" placeholder-style="color:#ccc;" v-model.trim="password" data-key="password"
                    @click="onClickpassword" disabled="true" />
            </view>
            <!-- <uni-list>
				<view style="border-bottom: 1.25px solid #dedede;">
					<text>手机号：</text>
					<uni-list-item style="width: 80%;float: right;text-align: right;" :show-badge="true" :title="username" @click="onClickphone" />
				</view>
				<view>
					<text>密码：</text>
					<uni-list-item style="width: 80%;float: right; text-align: right;"  :show-badge="true" :title="password" @click="onClickpassword" />
				</view>
			</uni-list> -->
        </view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="settinglogout">退出登录</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import '../../common/tablefrom.css';
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import {
        mapMutations,
        mapState
    } from 'vuex';
    export default {
        // components: {
        // 	uniList,
        // 	uniListItem
        // },
        data() {
            return {
                username: "",
                password: "修改",
                usernameshow: "解绑",
                changePhoneFlag: true
            }
        },
        computed: {
            ...mapState(['userInfo'])
        },
        onShow() {
            this.username = this.userInfo.sysUser.username;
            this.changePhoneFlag = true;
            this.userType = this.userInfo.sysUser.userType;
        },
        methods: {
            ...mapMutations(['logout']),
            onClickphone() {
                if (!this.changePhoneFlag) {
                    return false
                } else {
                    this.changePhoneFlag = false;
                    if (this.userInfo.cardStatus == app.CARD_STATUS.NORMAL_CARD_STATUS) {
                        uni.navigateTo({
                            url: "/pages_mine/setting/changeMobile"
                        })
                    } else {
                        uni.showToast({
                            icon: 'none',
                            title: '请先完成校友认证',
                            duration: 2000
                        });
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
            onClickpassword() {
                if(this.userType=="NORMAL"||this.userType==null||this.userType==''){
                    uni.navigateTo({
                        url: "/pages_mine/setting/changePwd"
                    })
                }else{
                    this.showpageToast("您是系统管理员，请到系统管理端修改密码！");
                }
               
            },
            settinglogout() {
                this.logout();
                uni.setStorageSync("token", "");
                uni.setStorageSync("tokenTime", "");
                uni.setStorageSync("iscard", "");
                uni.setStorageSync("openid", "");
                uni.switchTab({
                    url: "/pages/tabbar/index/index"
                })
                // uni.navigateTo({
                // 	url:"/pages/tabbar/index/index"
                // })
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            }
        }
    }
</script>

<style lang="scss">
    .action-row-center {
        color: #fff;
        background-color: rgba(0, 0, 0, 0.4);
        height: 98%;
        line-height: 60upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
        font-size: 24upx;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content {
        /* background-color: #F2F2F2; */
        /* padding-bottom: 40upx; */
    }

    .content_view {
        background-color: #FFFFFF;
        margin-top: 20upx;
        margin-bottom: 20upx;
        padding-left: 20upx;
        padding-right: 20upx;
    }

    .content_view_item {
        height: 80upx;
        line-height: 80upx;
        border-bottom: 1upx solid #e6e6e6;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10upx 20upx;
    }

    .content_view_item:last-child {
        border: none;
    }

    .text_reg {
        /* width: 15%; */
        font-size: $uni-font-size-base;
        display: inline-block;
        color: #000;
        /* padding-left: 20upx; */
        position: relative;
 
    }
	.content_view_item input.input_reg:placeholder{
		color: #000;
	}
    .input_reg {
        width: 60% !important;
        float: right;
        text-align: right;
        // margin-right: 20upx;

    }

    .btn-verify {
        width: 174upx;
        height: 64upx;
        line-height: 64upx;
        background-color: #e2b352 !important;
        font-size: 26upx;
        color: #fff;
        padding: 0;
        display: inline-block;
        position: absolute;
        top: 18upx;
        right: 25upx;
        z-index: 1000;
    }

    .switch {
        float: right;
        padding-top: 10rpx;
    }

    .primary {
        width: 90%;
        padding-bottom: 20upx;
    }

    .uni-input {
        text-align: right;
        color: #cccccc;
        position: relative;
       
        padding-right: 0;
    }

    .ring {
        width: 27upx;
        height: 32upx;
        position: relative;
        top: 12rpx;
        right: 16rpx;
    }

    .primary.btn_dark.bottom-but {
        position: absolute;
        width: 100%;
        bottom: 0;
		 }
		 
    .tis {
        font-size: 26upx;
        margin-right: 5upx;
        max-height: 120upx;
    }

    .tis-image {
        width: 70upx;
        height: 70upx;
        border-radius: 100%;
    }
	.placeholderStyle{color: #ccc;}
	.input-row{
		width:  90vw;
		position:  absolute;
		bottom:  70rpx;
		left:  5vw; 
	}
</style>
