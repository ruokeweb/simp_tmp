<template>
    <view class="content">
        <text class="l_title">登录校友卡</text>
        <view class="input-group">
            <view class="input-row">
                <input class="input_login" type="text" maxlength=11 placeholder="请输入手机号" v-model.trim="username"
                    data-key="username"></input>
            </view>
            <view class="input-row">
                <input class="input_login" type="password" maxlength=18 placeholder="请输入密码" v-model.trim="password"
                    data-key="password"></input>
            </view>
        </view>
        <view class="action-row action-row-right">
            <navigator url="./updatePass">忘记密码?</navigator>
        </view>

        <view class="input-row" style="margin-top:150upx;">
            <button type="primary" class="primary btn_dark" @tap="userlogin" :disabled="disabled">{{note}}</button>
            <!-- <button type="primary" class="primary btn_line" @tap="nologin">申领校友卡</button> -->
            <!--            <view class="action-row action-row-center">
                <navigator url="/" style="color:#c59b5c;">已有电子校友卡</navigator>
            </view> -->
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import {
        mapMutations
    } from 'vuex';
    export default {
        data() {
            return {
                username: '',
                password: '',
                // username: '17629261881',
                // password: 'suyupeng',                
                comeFrom: app.COME_FROM,
                note: "登录",
                disabled: false,
                updatePassDisable: false,
                openid: ''
            }
        },
        onShow() {
          this.getOpenId();  
        },
        methods: {
            ...mapMutations(['login', 'setUserInfo']),
            userlogin(event) {
                //util.getStatPoint('click','EVENT_DESCRIBE_4');
                if (this.username.length == 0) {
                    this.showpageToast("请输入账号");
                    return false;
                } else if (!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    return false;
                } else if (this.password.length == 0) {
                    this.showpageToast("请输入密码");
                    return false;
                } else if (this.password.length < 6) {
                    this.showpageToast("密码最短为6位");
                    return false;
                } else {
                    this.disabled = true;
                    this.note = "登录中";
                    this.password = this.password
                }
                uni.request({
                    url: app.serverPath + '/app/login',
                    method: 'POST',
                    header: {
                        'content-type': 'application/x-www-form-urlencoded'
                    },
                    data: {
                        username: this.username,
                        password: util.Encrypt(app.AES_KEY, this.password),
                        comeFrom: this.comeFrom,
                        openid: this.openid
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.login(res.data.data);
                            this.setUserInfo(res.data.data.userInfo);
                            //判断是否领卡， 关闭当前页跳转
                            uni.reLaunch({
                                url: "/pages/tabbar/index/index"
                            })
                        } else {
                            this.disabled = false;
                            this.note = "登录";
                            this.showpageToast(res.data.msg);
                        }
                    },
                    fail: (res) => {
                        // this.showpageToast(res.data.msg);
                        this.showpageToast("请求错误");
                        this.disabled = false;
                    }
                })

            },
            getOpenId() {
                if (this.openid == '') {
                    uni.getProvider({
                        service: 'oauth',
                        success: (res) => {
                            console.log(res.provider)
                            if (~res.provider.indexOf('weixin')) {
                                uni.login({
                                    provider: 'weixin',
                                    success: (loginRes) => {
                                        console.log(JSON.stringify(loginRes));
                                        this.WxCode = loginRes.code;
                                        console.log(this.code)
                                        //获取用户openid
                                        request.http({
                                            url: "/app/pay/getInfo",
                                            data: {
                                                'code': this.WxCode
                                            },
                                            header: 'application/x-www-form-urlencoded',
                                            success: (res) => {
                                                this.openid = res.data.data.openId;
                                            },
                                            fail: () => {}
                                        })

                                    }
                                });
                            }
                        }
                    });
                }
            },
            showpageToast(msg) {
                this.disabled = false;
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            nologin(event) {
                console.log("点击没有电子卡跳转到申领界面");
                uni.navigateTo({
                    url: "./nocard"
                })
            },
            accInput(e) {
                this.account = e.target.value;
            },
            psdInput(e) {
                this.password = e.target.value;
            }
        }
    }
</script>

<style>
    .content {
        padding: 120upx 70upx;
    }

    .input_login {
        border-bottom: 1px solid #e6e6e6;
        height: 2rem;
    }

    .input-group {
        margin-top: 150upx;
    }

    .input-row {
        margin-top: 40upx;
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

    .input-row button {
        margin: 20upx 0;
    }
</style>
