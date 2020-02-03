<template>
    <view class="payRes">
        <view class="pay_tu">
            <image :src="image" mode=""></image>
        </view>
        <view class="content">
            <view class="wb">
                <view class="wb_1">
                    亲爱的{{name}}：
                </view>
                <view class="wb_2">
                    感谢您对{{school_name}}<text>{{donProName}}</text>项目的关心和支持,我们将遵照您的意愿,将您捐赠的<text>{{money}}</text>元人民币全部用于该项目的发展
                </view>
                <view class="wb_3">
                    谨呈此证，深表谢忱
                </view>
                <view class="wb_4">
                    {{school_name}}
                </view>
                <view class="wb_5">
                    {{date}}
                </view>
            </view>
        </view>
        <view class="act_footer">
            <view class="input-row">
                <button type="primary" class="btn_dark" @click="togglePopup('middle-con')">分享</button>
            </view>
        </view>
        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="togglePopup('')">
            <view class="" style="width:100%;height:100%;">
                <view>
                    <image class="card_bg" src="../../static/login/ident-bg.png" style="" mode="widthFix"></image>
                    <image class="card_pop" style="" mode="widthFix" src="../../static/img/certificate.png"></image>
                </view>
                <view class="code">
                    <view class="hint">
                        <image v-bind:src="codeUrl" mode=""></image>
                        <view class="sub">
                            <text class="rescode">长按识别小程序码</text>
                            <view class="create">
                                校友卡由{{school_name}}【校友卡】小程序生成
                            </view>
                        </view>
                    </view>
                </view>
                <button type="primary" class="btn_dark inner primary" style="float: left; margin-left: 40upx;" @click="saveImage">保存图片</button>
                <button type="primary" class="btn_dark inner primary" style="float: right; margin-right: 40upx;"
                    open-type="share">分享</button>
                <view style="position:fixed;top:9999999999999rpx;">
                    <canvas canvas-id="sharecard" style="width:602px;height:848px; background-color: #FFFFFF;">
                    </canvas>
                </view>

            </view>
        </uni-popup>
    </view>
</template>

<script>
    import util from '@/common/util.js';
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import uniPopup from '@/components/uni-popup/uni-popup.vue';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            uniPopup
        },
        data() {
            return {
                id: "",
                type: '',
                name: '',
                donProName: '',
                money: "",
                date: '',
                image: '/static/img/feedback.png',
                codeUrl: "/static/logo.png",
                school_name: ""
            }
        },
        computed: {
            ...mapState(['configParams'])
        },
        onLoad(options) {
            // this.getCode();
            this.id = options.id;
            request.http({
                url: "/app/don/getId",
                data: {
                    id: this.id,
                },
                header: 'application/x-www-form-urlencoded',
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.name = res.data.data.name;
                        this.money = res.data.data.money;
                        this.donProName = util.strcharacterDiscode(res.data.data.donProject.name);
                        this.date = util.dateUtils.ConverStringToDate(res.data.data.time);

                    }
                },
                fail: (data, code) => {
                    console.log(data);
                }
            })
        },
        onShow() {
            this.school_name = this.configParams.school_name;
        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.MYCERTIFICATE_SHARE);

            let donName = this.name;
            var truePath = encodeURIComponent(JSON.stringify('/pages_mine/myDon/myCertificate?id=' + encodeURIComponent(
                this.id)));
            return {
                title: donName + '分享了ta的捐赠证书，赶快去看看吧',
                path: '/pages/tabbar/index/index?truePath=' + truePath,
            }
        },
        methods: {
            togglePopup(type) {
                this.type = type
            },
            goIndex() {
                uni.switchTab({
                    url: '/pages/tabbar/index/index'
                });
            },
            getInfo() {

            },
            //获取二维码连接
            getCode() {
                request.http({
                    url: '/app/wx/getWCRecode',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        'scene': "scene",
                        'page': "pages/tabbar/index/index",
                        'width': 300
                    },
                    responseType: 'arraybuffer',
                    success: (res) => {
                        // this.codeUrl = res.data;
                        this.codeUrl = 'data:image/jpg;base64,' + uni.arrayBufferToBase64(res.data)
                        // console.log(imgBase64);
                        // this.codeUrl = 'data:image/jpg;base64,' + imgBase64;
                    },
                    error: (res) => {
                        console.log(res);
                    }
                });
            },
            share() {
                uni.share({
                    provider: 'weixin',
                    type: 5,
                    imageUrl: 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/app/share-logo@3.png',
                    title: '欢迎体验uniapp',
                    miniProgram: {
                        id: 'gh_abcdefg',
                        path: 'pages/index/index',
                        type: 0,
                        webUrl: 'http://uniapp.dcloud.io'
                    },
                    success: ret => {
                        console.log(JSON.stringify(ret));
                    }
                });
            },
            /* saveImage() {
                 uni.chooseImage({
                     count: 1,
                     sourceType: ['camera'],
                     success: function(res) {
                         uni.saveImageToPhotosAlbum({
                             filePath: res.tempFilePaths[0],
                             success: function() {
                                 console.log('save success');
                             }
                         });
                     }
                 });
             }, */
            saveImage(canvasId) {

                // x 圆角矩形选区的左上角 x坐标
                // y 圆角矩形选区的左上角 y坐标
                // w 圆角矩形选区的宽度
                // h 圆角矩形选区的高度
                // r 圆角的半径 
                let x = 120
                let y = 70
                let w = 150
                let h = 30
                let r = 14
                uni.showLoading({
                    title: '图片绘制中...',
                })
                let cav = uni.createCanvasContext('sharecard');

                //上部背景
                cav.drawImage('/static/img/feedback.png', 0, 0, 602, 848);
                //文字
                cav.font = 'normal bold 18px sans-serif';
                // cav.setTextAlign('center');
                cav.fillText('亲爱的' + this.name, 120, 320);
                var str = "感谢您对" + this.school_name + this.donProName + "项目的关心和支持,我们将遵照您的意愿,将您捐赠的" + this.money +
                    "元人民币全部用于" + this.school_name +
                    this.donProName +
                    "项目";
                var lineWidth = 0;
                var canvasWidth = 350; //计算canvas的宽度
                var initHeight = 350; //绘制字体距离canvas顶部初始的高度
                var lastSubStrIndex = 0; //每次开始截取的字符串的索引
                for (let i = 0; i < str.length; i++) {
                    lineWidth += cav.measureText(str[i]).width;
                    if (lastSubStrIndex == 0) {
                        if (lineWidth > canvasWidth) {
                            cav.fillText(str.substring(lastSubStrIndex, i), 150, initHeight); //绘制截取部分
                            initHeight += 30; //20为字体的高度
                            lineWidth = 0;
                            lastSubStrIndex = i;
                        }
                    } else {
                        if (lineWidth > canvasWidth) {
                            cav.fillText(str.substring(lastSubStrIndex, i), 120, initHeight); //绘制截取部分
                            initHeight += 30; //20为字体的高度
                            lineWidth = 0;
                            lastSubStrIndex = i;
                        }
                    }

                    if (i == str.length - 1) { //绘制剩余部分
                        cav.fillText(str.substring(lastSubStrIndex, i + 1), 120, initHeight);
                    }
                }

                cav.fillText("谨呈此证，深表谢忱", 190, 520);
                cav.fillText(this.school_name, 380, 613);
                cav.fillText(this.date, 300, 633);
                cav.draw(false, function() {
                    uni.canvasToTempFilePath({
                        canvasId: 'sharecard',
                        success: function(res) {
                            uni.hideLoading()
                            console.log(res.tempFilePath)
                            uni.saveImageToPhotosAlbum({
                                filePath: res.tempFilePath,
                                success: function(res) {
                                    console.log("图片已保存")
                                    uni.showToast({
                                        title: '图片已保存'
                                    })
                                },
                                fail() {
                                    console.log("失败了canvasToTempFilePath");
                                }
                            })
                        },
                        fail(res) {
                            console.log(res);
                        }
                    })

                })
            }
        },
    }
</script>

<style>
    Page {
        background-color: #D5D8D9;
    }

    .payRes text {
        display: block;
        margin-left: 242upx;
        margin-top: 30upx;
    }

    .l_title:after {
        content: " ";
        background: #fbc351;
        height: 12upx;
        width: 73%;
        position: absolute;
        bottom: 16upx;
        left: -33upx;
        z-index: -1;
    }

    .payRes .pay_tu image {
        width: 91vw;
        height: 78vh;
        margin-top: 33upx;
        margin-left: 33upx;
    }

    .payRes .content {
        width: 385upx;
        height: 340upx;
        position: absolute;
        top: 27%;
        left: 165upx;
    }

    .payRes .content view {
        font-size: 24upx;
        color: #262f43;
        line-height: 1.5;
    }

    .payRes .content .wb {
        width: 385upx;
        height: 340upx;
        position: relative;
    }

    .payRes .wb_2 {
        text-indent: 2em;
        position: absolute;
        top: 50upx;
        letter-spacing: 1upx;
    }

    .payRes .wb_2 text {
        margin-left: 0;
        margin-top: 0;
        display: inline;
        padding-bottom: 5upx;
        text-decoration: underline;
    }

    .payRes .wb_3 {
        position: absolute;
        left: 50upx;
        bottom: 50upx;
    }

    .payRes .wb_4 {
        position: absolute;
        right: -58upx;
        bottom: -33upx;
    }

    .payRes .wb_5 {
        position: absolute;
        right: -58upx;
        bottom: -64upx;
        font-size: 18upx !important;
    }

    .act_footer {
        position: fixed;
        bottom: 65upx;
        width: 100%;
        height: 98upx;
    }

    .act_left {
        width: 50%;
        height: 98upx;
        float: left;
        text-align: center;
        color: #262F43;
        line-height: 98upx;
        background-color: white;
        font-size: 30upx;
    }

    .act_right {
        width: 50%;
        height: 98upx;
        float: right;
        text-align: center;
        background-color: #262F43;
        color: white;
        line-height: 98upx;
        font-size: 30upx !important;
    }

    .uni-popup {
        width: 460upx;
        height: 600upx;
        padding: 0 !important;
        border-radius: 20upx !important;
    }

    button {
        width: 93%;
        border-radius: 44upx;
        margin-right: auto;
        padding-left: 0;
        padding-right: 0;
        box-sizing: border-box;
        font-size: 30upx;
        text-align: center;
        text-decoration: none;
        line-height: 98upx;
    }

    .card_bg {
        border-radius: 20upx 20upx 0 0;
        width: 100% !important;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .card_pop {
        display: block;
        width: 225upx !important;
        height: 310upx !important;
        margin: 50upx auto;
    }

    .primary.inner {
        width: 177upx;
        height: 60upx;
        background-color: #272d3d;
        border-radius: 30upx;
        line-height: 60upx;
        margin-top: 25upx;
        font-size: 30upx;
    }

    uni-popup .hint {
        height: 100upx;
        margin-left: 80upx;
        padding-bottom: 0;
        overflow: hidden;
        margin-top: -23upx;
    }

    uni-popup .hint .sub {
        float: left;
        margin-left: 15upx;
        margin-top: 20upx;
    }

    uni-popup .hint image {
        float: left;
        width: 85upx;
        height: 85upx;
        border-radius: 50%;
    }

    uni-popup .hint .rescode {
        font-size: 15upx;
        font-weight: bold;
        margin-left: 0;
        margin-top: 0;
    }

    uni-popup .hint .create {
        font-size: 13upx;
    }
</style>
