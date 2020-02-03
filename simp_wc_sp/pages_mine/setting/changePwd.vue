<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">旧密码</text>
                <input class="input_reg" type="password" maxlength=11 placeholder="旧密码" v-model.trim="oldPass" data-key="oldPass"></input>
            </view>
            <view class="content_view_item">
                <text class="text_reg">新密码</text>
                <input class="input_reg" type="password" maxlength=11 placeholder="新密码" v-model.trim="newPass" data-key="newpass"></input>
            </view>
            <view class="content_view_item">
                <text class="text_reg">确认密码</text>
                <input class="input_reg" type="password" maxlength=11 placeholder="确认密码" v-model.trim="aginPass"
                    data-key="aginPass"></input>
            </view>
        </view>
        <view class="input-row" style="margin-top:420upx;">
            <button type="primary" class="primary btn_dark down_but" :disabled="disabled" @tap="save">确定修改</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    // import '../../common/tablefrom.css'
    export default {
        data() {
            return {
                oldPass: '',
                newPass: '',
                aginPass: '',
                disabled: false
            }
        },
        methods: {
            save(event) {
                this.disabled = true;
                if (this.oldPass.length == 0) {
                    this.showpageToast("请输入密码");
                    this.disabled = false;
                    return false;
                } else if (this.oldPass.length < 6) {
                    this.showpageToast("密码最短为 6 个字符");
                    this.disabled = false;
                    return false;
                }else  if (this.newPass.length == 0) {
                    this.showpageToast("请输入密码");
                    this.disabled = false;
                    return false;
                } else if (this.newPass.length < 6) {
                    this.showpageToast("密码最短为 6 个字符");
                    this.disabled = false;
                    return false;
                }else  if (this.aginPass.length == 0) {
                    this.showpageToast("请输入密码");
                    this.disabled = false;
                    return false;
                } else if (this.aginPass.length < 6) {
                    this.showpageToast("密码最短为 6 个字符");
                    this.disabled = false;
                    return false;
                }else  if (this.newPass != this.aginPass) {
                    this.showpageToast("两次密码不一致");
                    this.disabled = false;
                    return false;
                }
                //修改密码
                request.http({
                    url: '/app/updatePwd',
                    data: {
                        oldPass: util.Encrypt(app.AES_KEY, this.oldPass),
                        newPass: util.Encrypt(app.AES_KEY, this.newPass),
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.showpageToast("密码修改成功")
                            uni.reLaunch({
                                url: "/pages/login/login"
                            })
                        }else{
                             this.disabled = false;
                             this.showpageToast(res.data.msg)
                        }
                    },
                    fail: () => {
                        this.disabled = false;
                        this.showpageToast("请求失败")
                    }
                })
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
        padding-left: 0;
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

    .down_but {
        margin-top: 666upx;
        width: 666upx;
    }
</style>
