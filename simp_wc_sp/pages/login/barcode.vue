<template>
    <view class="content">
        <view class="area">
            <image v-if="headUrl == null || headUrl == ''" src="/static/img/man_default.png" class="head"></image>
            <image v-else class="head" :src="imagePath + headUrl"></image>
            <view class="code">
                <image class="head" style="width:80%;height:80%;" v-bind:src="codeUrl"></image>
            </view>
        </view>
        <view class="tip">扫一扫上面的二维码快速认证</view>
    </view>
</template>

<script>
    import request from '@/common/request.js';
    import app from '@/common/app.js';
    import {
        mapState,
        mapMutations
    } from 'vuex';
    export default {
        data() {
            return {
                headUrl: "",
                codeUrl: "",
                imagePath: app.imagePath,
            }
        },
        onShow() {
            this.headUrl = this.userInfo.truePhoto;
            this.getCode();
        },
        computed: {
            ...mapState(['userInfo']),
        },
        methods: {
            //获取二维码连接
            getCode() {
                request.http({
                    url: '/app/wx/getWCRecode',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        'scene': this.userInfo.id,
                        'page': "pages/login/identifyResult",
                        'width': 300
                    },
                    responseType: 'arraybuffer',
                    success: (res) => {
                        // this.codeUrl = res.data;
                        this.codeUrl ='data:image/jpg;base64,'+ uni.arrayBufferToBase64(res.data)
                        // console.log(imgBase64);
                        // this.codeUrl = 'data:image/jpg;base64,' + imgBase64;
                    },
                    error: (res) => {
                        console.log(res);
                    }
                });
            }
        }
    }
</script>

<style>
    Page {
        background: #f2f2f2;
    }

    .content {
        padding: 100upx 20upx;
    }

    .head {
        width: 140upx;
        height: 140upx;
        border-radius: 70upx;
        display: block;
        margin: 0 auto;
        position: absolute;
        top: 40upx;
        left: 40upx;
    }

    .area {
        width: 690upx;
        height: 670upx;
        background: #fff;
        margin: 0 auto;
        position: relative;
        border-radius: 20upx;
        margin-top: 120upx;
    }

    .tip {
        font-size: 24upx;
        color: #666;
        margin-top: 20upx;
        text-align: center;
    }

    .code {
        width: 460upx;
        height: 460upx;
        margin: 0 auto;
        position: relative;
        top: 120upx;
    }
</style>
