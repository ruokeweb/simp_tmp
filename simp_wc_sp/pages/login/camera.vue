<template>
    <view class="content">

        <view style="width: 440upx; margin: 140upx auto;">
            <view style="margin-top: 5upx; margin: auto; font-weight: bold; font-size: 52upx; line-height:0.8;display: flex; flex-direction: row;
        justify-content: center;">上传个人照片</view>
            <view class="" style="background-color:#FBC351; height: 20upx; margin-bottom: 0upx; width: 98%; margin: auto;"></view>
            <p style="display: flex; flex-direction: row;
        justify-content: center;">请对准脸部拍摄，提高认证成功率</p>
        </view>

        <cover-view class="search_1">

            <cover-view v-if="is_takePhoto == true" style="position: absolute;width: 100%;height: 100%;left: -100upx;margin: auto;border: 100upx solid #fff;right: 0;border-radius: 50%;overflow: hidden;top: -100upx;">
                <cover-image v-if="is_takePhoto == true" class="head-pic" :src="imagePath + truePhoto" mode="aspectFill">
                </cover-image>
                <cover-view v-if="is_auth== true" class="camera-animate" :style='{top:animateTop}'></cover-view>
            </cover-view>
            <camera v-if="is_takePhoto == false" device-position="front" flash="off">
                <cover-view style="position: absolute;width: 100%;height: 100%;left: -100upx;margin: auto;border: 100upx solid #fff;right: 0;border-radius: 50%;overflow: hidden;top: -100upx;z-index: 9999999;">
                </cover-view>
            </camera>
        </cover-view>
        <view v-if="is_takePhoto == false" class="input-row" style="margin-top:120upx;position: raletive;">
            <button type="primary" class="btn_1" @click="takePhoto">拍照</button>
            <view v-if="is_takePhoto == false" class="primary btn_white" @tap="gotoReg" style="position: absolute;left: 0;right: 0;bottom: 18px;">
                跳过
            </view>
        </view>

        <view v-if="is_takePhoto == true" class="input-row" style="margin-top:160upx;">
            <button type="primary" class="btn_1" @click="startAuthen">开始认证</button>
            <button type="primary" class="btn_1 btn_2" @click="sendTakePhoto">重新拍</button>
        </view>

    </view>
</template>

<script>
    import '@/static/css/index.css';
    import app from '@/common/app.js';
    import util from '@/common/util.js';
    export default {

        data() {
            return {
                imagePath: app.imagePath,
                truePhoto: '',
                is_takePhoto: false,
                animateTop: '',
                an: 0,
                is_auth: false
            }
        },
        onShow() {},
        onLoad() {
            this.animate()
        },
        methods: {
            animate() {
                if (this.an <= 430) {
                    this.an = this.an + 1;
                    this.animateTop = this.an + 'rpx';

                    setTimeout(() => {
                        this.animate();
                    }, 10);
                } else {
                    this.an = 0;
                    this.animateTop = this.an + 'rpx';
                    setTimeout(() => {
                        this.animate();
                    }, 10);
                }
            },
            sendTakePhoto(){
                this.is_takePhoto = false;
                this.is_auth = false;
            },
            takePhoto() {
                // //util.getStatPoint('click', 'EVENT_DESCRIBE_5');
                this.is_takePhoto = false;
                this.is_auth = false;
                const ctx = uni.createCameraContext();
                ctx.takePhoto({
                    quality: 'high',
                    success: (res) => {
                        const tempFilePaths = res.tempImagePath;
                        console.log(res.tempImagePath);
                        uni.uploadFile({
                            url: app.serverPath + '/file/upload', //仅为示例，非真实的接口地址
                            filePath: tempFilePaths,
                            name: 'file',
                            header: {
                                'content-type': 'multipart/form-data',
                            },
                            success: (suc) => {
                                var result = JSON.parse(suc.data);
                                console.log(result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS);
                                if (result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                    this.truePhoto = result.data;
                                    this.is_takePhoto = true;
                                } else {
                                    uni.showToast({
                                        icon: 'none',
                                        title: result.msg
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

            uploadtruePhoto() {
                uni.chooseImage({
                    count: 1,
                    sizeType: ['original', 'compressed'], //是原图还是压缩图，默认二者都有
                    sourceType: ['camera '],
                    success: (res) => {
                        const tempFilePaths = res.tempFilePaths;
                        uni.uploadFile({
                            url: app.serverPath + '/file/upload', //仅为示例，非真实的接口地址
                            filePath: tempFilePaths[0],
                            name: 'file',
                            header: {
                                'content-type': 'multipart/form-data',
                            },
                            success: (suc) => {
                                var result = JSON.parse(suc.data);
                                console.log(result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS);
                                if (result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                    this.truePhoto = result.data;
                                    this.is_takePhoto = true;
                                } else {
                                    uni.showToast({
                                        icon: 'none',
                                        title: result.msg
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
            gotoReg() {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_6');
                uni.navigateTo({
                    url: "./regist?"
                })
            },
            startAuthen() {
                this.is_auth = true;
                uni.showLoading({
                    title: '识别中',
                    mask: true
                });
                console.log("==================开始搜索=========================");
                uni.request({
                    url: app.serverPath + '/app/findSmByFace',
                    method: 'POST',
                    header: {
                        'content-type': 'application/x-www-form-urlencoded'
                    },
                    data: {
                        fileId: this.truePhoto,
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //判断人脸库是否存在
                            var userId = res.data.data;
                            uni.navigateTo({
                                url: "./regist?userId=" + userId+ "&truePhoto="+this.truePhoto
                            })
                        } else {
                            uni.navigateTo({
                                url: "./regist?truePhoto="+this.truePhoto
                            })
                        }
                        uni.hideLoading();
                        console.log(res);
                    },
                    fail: (res) => {
                        uni.showToast({
                            icon: 'none',
                            title: '请求错误'
                        });
                        uni.hideLoading();
                    }
                })
            }
        }
    }
</script>

<style>
    /**/


    .camera-animate {
        position: absolute;
        width: 100%;
        height: 10upx;
        background: #F0AD4E;
        left: 0;
        top: 0;
        z-index: 99999999999999;
        box-shadow: 0 0 30upx #f0ad4e;
        /* 		 animation: scan 3s ease-in-out infinite;
             -webkit-animation: scan 3s ease-in-out infinite; */


    }

    @keyframes scan {
        from {
            top: -430upx;
        }

        /*网格移动到显示区域的外面*/
        to {
            top: 0;
        }
    }

    @-webkit-keyframes scan {
        from {
            top: -430upx;
        }

        to {
            top: 0;
        }
    }





    camera {
        width: 100%;
        overflow: hidden;
        height: 100%;
        display: flex;
        align-items: center;
    }

    /**/
    .search_1 {
        width: 430upx;
        height: 430rpx;
        margin: auto;
        position: relative;
        overflow: hidden;
    }

    .search_1 .head-pic {
        max-width: 100%;
        max-height: 100%;
        border-radius: 50%;
        background: #aaa;
    }

    .circle {
        position: absolute;
        left: 50%;
        top: 0;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background: #aaa;
        animation: spin 3s infinite linear
    }

    @keyframes spin {
        from {
            transform: translate(-50%, 135px) rotate(0turn) translate(50%, -135px)
        }

        to {
            transform: translate(-50%, 135px) rotate(1turn) translate(50%, -135px)
        }
    }

    .ph {
        width: 357upx;
        height: 81upx;
        position: absolute;
        right: 37upx;
        bottom: 72upx;
        z-index: 100;
    }

    .btn_1 {
        width: 90%;
        position: absolute;
        bottom: 172upx;
        left: 5%;
        background-color: #272d3d !important;
        border-radius: 44px;
    }

    /*    .btn_2 {
        bottom: 145upx;
        background-color: white !important;
        border-radius: 44upx;
        border: solid 1upx #3c4250;
        color: #D0B083 !important;
        font-size: 33upx !important;
    } */

    .view_but {
        margin-top: 323upx !important;
        display: flex;
        background-color: white !important;
        color: #D0B083 !important;
        font-size: 26upx !important;
        border: none !important;
        flex-direction: row;
        justify-content: center;
    }

    .btn_2 {
        bottom: 53upx;
        background-color: white !important;
        color: #272d3d !important;
        border-radius: 44upx;
        border: solid 1upx #3c4250;
    }

    .but_view {
        width: 100%;
        position: absolute;
        bottom: 246upx;
    }

    .btn_white {
        background-color: #FFFFFF !important;
        color: #C59B5C !important;
        margin-top: 20upx !important;
        font-weight: bold !important;
        font-size: 26upx !important;
        border: none !important;
        flex-direction: row;
        justify-content: center;
        display: flex;
    }
</style>
