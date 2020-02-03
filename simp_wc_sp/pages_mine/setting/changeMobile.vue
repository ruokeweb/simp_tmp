<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="zy_text_reg">旧手机号</text>
                <input class="zy_input_reg" type="text" maxlength=11 placeholder="旧手机号" v-model.trim="username" data-key="username" disabled="true"></input>
            </view>
            <view class="content_view_item">
                <text class="zy_text_reg">新手机号</text>
                <input class="zy_input_reg" type="text" maxlength=11 placeholder="新手机号" v-model.trim="newphone" data-key="newphone"></input>
            </view>
            <view class="content_view_item">
                <text class="zy_text_reg">验证码</text>
                <input class="zy_input_reg" type="text" placeholder="请输入验证码" v-model.trim="captChaCode" data-key="captChaCode"
                    maxlength=6></input>
                <button class="yz_btn-verify" @click="getCode" :disabled="disabled">{{time}}</button>
            </view>
        </view>
        <view class="input-row">
            <button type="primary" class="primary btn_dark down_but" @click="save" :disabled="changephoneDisabled">确定修改</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import {
        mapState,
        mapMutations
    } from 'vuex';
    export default {
        data() {
            return {
                username: '',
                newphone: '',
                captChaCode: '',
                interval: null, //倒计时函数
                time: '获取验证码', //倒计时
                currentTime: 61,
                disabled: false,
                changephoneDisabled: false,
            }
        },
        onShow() {
            this.username = this.userInfo.sysUser.username;
        },
        computed: {
            ...mapState(['userInfo'])
        },
        methods: {
            ...mapMutations(['setUserInfo']),
            save(event) {
                if (this.newphone.length == 0) {
                    this.disabled = false;
                    this.showpageToast("请输手机号");
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.newphone)) {
                    this.showpageToast("请输入正确的手机号");
                    return false;
                }

                if (this.captChaCode.length == '') {
                    this.showpageToast("请输入验证码");
                    return false;
                } else if (this.captChaCode.length != 6) {
                    this.showpageToast("请输入正确的验证码");
                    return false;
                }
                //修改密码
                request.http({
                    
                    url: '/app/changePhone',
                    data: {
                        username: this.username,
                        newphone: this.newphone,
                        captChaCode: this.captChaCode
                    },
                    header: 'application/x-www-form-urlencoded',
                    success:(res) =>{
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.showpageToast(res.data.msg)
                            this.setUserInfo(res.data.data);
                            this.username = res.data.data.sysUser.username;
                            uni.switchTab({
                                url: "/pages/tabbar/index/index"
                            })
                        }
                        this.showpageToast(res.data.msg)
                    },
                    fail:()=> {
                        this.showpageToast("请求失败")
                    }
                })
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            nologin(event) {
                uni.navigateTo({
                    url: "./nocard"
                })
            },
            getCode() {
                if (this.newphone.length == 0) {
                    this.showpageToast("请输手机号");
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.newphone)) {
                    this.showpageToast("请输入正确的手机号");
                    return false;
                }
                this.currentTime = 61;
                this.disabled = true;
                this.interval = setInterval(() => {
                    this.currentTime--;
                    this.time = this.currentTime + '秒'
                    if (this.currentTime <= 0) {
                        clearInterval(this.interval)
                        this.disabled = false;
                        this.time = '重新发送';
                    }
                }, 1000)
                request.http({
                    url: "/app/sendChangePhoneCaptCha",
                    data: {
                        newphone: this.newphone,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {

                        }else{
                            clearInterval(this.interval);
                            this.time = '获取验证码';
                            this.disabled = false                            
                        }
                        this.showpageToast(res.data.msg);
                    },
                    fail: (error) => {

                    }
                });
            }
        }
    }
</script>
<style lang="scss">
    .content {
        background-color: #FFFFFF;
    }
	.zy_text_reg{width: calc(100% - 394upx);}
	input.zy_input_reg{margin-right: 20upx;}
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
    .zy_input_reg {width: 220rpx;
text-align: right;

       
    }
	.yz_btn-verify{float: right;}
    .content_view_item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    }
	.yz_btn-verify{
		width: 174upx;
		height: 64upx;
		line-height: 64upx;
		background-color: #e2b352;
		font-size: 24upx;
		color: #fff;
		padding: 0;
		display: inline-block;

	}
	.input-row{width: 90vw;
position: absolute;
bottom: 70rpx;
left: 5vw;
}
	
</style>
