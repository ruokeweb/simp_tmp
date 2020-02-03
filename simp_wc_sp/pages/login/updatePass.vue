<template>
    <view class="content">
        <text class="l_title">重新设置登录密码</text>
        <view class="input-group">
            <view class="input-row">
                <input class="input_login" type="text" maxlength=11 placeholder="请输入手机号" v-model.trim="username" placeholder-class="placeholderStyle"
                    data-key="username"></input>
            </view>
            <view class="input-row" style="position:relative;">
                <input class="input_login" type="text" placeholder="请输入验证码" v-model.trim="code" data-key="code" placeholder-class="placeholderStyle"
                    maxlength=6></input>
                <button class="btn-verify" @click="getCode" :disabled="disabled">{{time}}</button>
            </view>
            <view class="input-row">
                <input class="input_login" type="password" maxlength=18 placeholder="请输入密码" v-model.trim="password" placeholder-class="placeholderStyle"
                    data-key="password"></input>
            </view>
            <view class="input-row">
                <input class="input_login" type="password" maxlength=18 placeholder="请确认密码" v-model.trim="aginpassword" placeholder-class="placeholderStyle"
                    data-key="aginpassword"></input>
            </view>
        </view>
        <view class="input-row" style="margin-top:150upx;">
            <button type="primary" class="primary btn_dark" @tap="save" :disabled="saveDisabled">确定修改</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    export default {
        data() {
            return {
                username: '',
                password: '',
                aginpassword: '',
                code: '',
                interval: null, //倒计时函数
                time: '获取验证码', //倒计时
                currentTime: 61,
                disabled: false,
                saveDisabled: false
            }
        },
        methods: {
            save(event) {
                if (this.username.length == 0) {
                    this.showpageToast("请输手机号");
                    return false;
                }else if (!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    return false;
                }else  if (this.code.length == '') {
                    this.showpageToast("请输入验证码");
                    return false;
                } else if (this.code.length != 6) {
                    this.showpageToast("请输入正确的验证码");
                    return false;
                }else if (this.password.length == 0) {
                    this.showpageToast("请输入密码");
                    return false;
                } else if (this.password.length < 6) {
                    this.showpageToast("密码最短为6位");
                    return false;
                }else if (this.aginpassword.length == 0) {
                    this.showpageToast("两次密码不一致");
                    return false;
                } else if (this.password != this.aginpassword) {
                    this.showpageToast("两次密码不一致");
                    return false;
                }
                this.saveDisabled = true;
                //修改密码
                uni.request({
                    url: app.serverPath + '/app/updateBackPwd',
                    data: {
                        username: this.username,
                        password: util.Encrypt(app.AES_KEY, this.password),
                        captChaCode: this.code
                    },
                    method: 'POST',
                    header:{
                        'content-type': 'application/x-www-form-urlencoded'
                    },                      
                    success:(res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.showpageToast(res.data.msg)
                            let url = '/pages/login/login';
                            uni.reLaunch({
                                url: url
                            });
                        }else {
                             this.saveDisabled = true;
                        }
                        this.showpageToast(res.data.msg)
                    },
                    fail:() => {
                        this.showpageToast("请求失败")
                         this.saveDisabled = true;
                    }
                })
            },
            getCode() {
                if (this.username == "") {
                    this.showpageToast("请输入手机号");
                    this.disabled = false;
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    this.disabled = false;
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
                uni.request({
                    url: app.serverPath + "/app/sendBackPwdCaptCha",
                    method: 'POST',
                    data: {
                        username: this.username,
                    },
                    header: {
                        'content-type': 'application/x-www-form-urlencoded'
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.showpageToast(res.data.msg);
                        } else {
                            this.showpageToast(res.data.msg);
                            this.time = '获取验证码',
                                clearInterval(this.interval)
                            this.disabled = false;
                        }
                    },
                    fail: (error) => {
                        clearInterval(this.interval);
                        this.disabled = false;
                        this.time = '重新发送';
                    }
                });
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
<style>
    .title {
        font-size: 52upx;
        position: relative;
        z-index: 1;
    }

    .title:after {
        content: " ";
        background: #fbc351;
        height: 12upx;
        width: 100%;
        position: absolute;
        bottom: 2upx;
        left: 0;
        z-index: -1;
    }

    .content {
        padding: 120upx 70upx;
    }

    .primary {
        height: 90upx;
        line-height: 90upx;
        border-radius: 45upx;
    }

    .btn_dark {
        background: #272d3d !important;
    }

    .line {
        background: #fff !important;
        color: #3c4250 !important;
        border: 2upx solid #000;
    }

    .input_login {
        
        height: 2rem;
    }

    .input-group {
        margin-top: 150upx;
    }

    .input-row {
        margin-top: 40upx;border-bottom: 1px solid #e6e6e6;
    }

    .action-row-center {
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .action-row-right {
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .action-row navigator {
        color: #999999;
        padding-top: 10upx;
    }

    .input-row button {}

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
        top: 0upx;
        right: 0upx;
        z-index: 1000;
    }
    .placeholderStyle{color: #ccc;}
</style>
