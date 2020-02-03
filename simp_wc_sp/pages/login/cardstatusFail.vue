<template>
    <view class="content">
        <!-- 审核失败 -->
        <view>
            <view>
                <view>
                    <image class="card_bg_top" src="../../static/card-bg-top.png" mode="widthFix"></image>
                </view>
                <view>
                    <image class="image_noc" src="../../static/img/card-anymous.png"></image>
                </view>
            </view>
            <view class="con">
                <view>
                    <text class="l_title">抱歉您的校友卡领取失败</text>
                </view>
                <view>
                    <view class="title_h2">亲爱的校友，您好！</view>
                    <view class="title_h3">校友卡审核失败的原因是</view>
                    <view class="title_h4">{{resultContent}}</view>
                    <view class="title_h3">请您重新修改信息！</view>
                    <view v-if="content!=''" class="title_blue">
                        <uParse :content="content" @preview="preview" @navigate="navigate"/>
                    </view>
                    <view v-else="" class="title_blue">
                        <uParse :content="content" @preview="preview" @navigate="navigate"/>
                    </view>
                </view>
            </view>
            <view>
                <button type="primary" class="primary btn_dark" @click="updateInfo" :disabled="disabled">修改信息</button>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import uniSteps from '@/components/uni-steps/uni-steps.vue';
    import request from '@/common/request.js';
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
    import '@/static/css/index.css';
    import util from '@/common/util.js';
    export default {
        components: {
            uniSteps,
            uParse
        },
        data() {
            return {
                name: "李坤奇",
                grade: "经济管理学院",
                dept: "经济管理学院",
                resultContent: '信息不全',
                cardState: "NO_AUDIT", //卡片状态
                active: 0,
                content: '暂无联系方式',
                list1: [{
                    title: '申请认证'
                }, {
                    title: '认证中'
                }, {
                    title: '认证完成'
                }],
                disabled: false
            }
        },
        onShow() {
            this.disabled = false;
            uni.request({
                url: app.serverPath + '/app/wx/getContentByPage',
                method: 'POST',
                header: {
                    'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                    code: 'cardstatusFail',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        console.log(res.data.data);
                        this.content = util.strcharacterDiscode(res.data.data);
                        console.log(this.content);
                    }
                },
                fail: (res) => {

                }
            })
        },
        onLoad(options) {
            this.resultContent = decodeURIComponent(options.resultContent);
            //获取校友卡的状态 审核中--1  审核成功--2
            if (options.state == "NORMAL") {
                this.getschoolmate()
            }
        },
        methods: {
            getschoolmate() {
                // request.ajax({
                //     url: "",
                //     method: "GET",
                //     success: (res) => {
                //         //  let list = this.setTime(res.data.data);
                //         if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                //             // this.name = "李坤奇"
                //             // this.grade = "经济管理学院"
                //             // this.dept = "经济管理学院"
                //         }
                //     },
                //     fail: (data, code) => {
                //         console.log(data);
                //     }
                // })
            },
            change() {
                if (this.active < this.list1.length - 1) {
                    this.active += 1
                } else {
                    this.active = 0
                }
            },
            bintIdentif() {
                uni.navigateTo({
                    // url: "identifyResult",
                    url: "invite"
                })
            },
            gotoindex() {
                uni.switchTab({
                    url: "../tabbar/index/index"
                })
            },
            updateInfo() {
                this.disabled = true;
                uni.navigateTo({
                    url: "/pages/login/confirmInfo"
                })
            }

        }
    }
</script>

<style>
    /* 审核认证中 */
    .rule {
        position: absolute;
        right: 35upx;
        top: 30upx;
        font-size: 24upx;
        color: #c59b5c;
    }

    .search_1 {
        width: 368upx;
        height: 368upx;
        margin: 0 auto;
        margin-top: 110upx;
    }

    .search_1 image {
        width: 100%;
        height: 100%;
    }

    .process {
        padding: 20upx 60upx 0 60upx;
    }

    .nr {
        padding: 0 30upx;
    }

    .nr .title {
        text-align: center;
        font-size: 30upx;
        color: #666666;
        font-weight: bold;
    }

    .nr .nr_list2 {
        font-size: 24upx;
        color: #666666;
        line-height: 1.5;
    }

    .nr .nr_list3 {
        text-align: center;
        font-size: 24upx;
        color: #00a3e0;
    }

    .btn_1 {
        width: 90%;
        position: absolute;
        bottom: 246upx;
        left: 5%;
        background-color: #272d3d !important;
        border-radius: 44px;
    }

    .btn_2 {
        bottom: 118upx;
        background-color: white !important;
        color: #272d3d !important;
        border-radius: 44upx;
        border: solid 1upx #3c4250;
    }

    /* 认证完成 */
    .image_noc {
        width: 90vw;
        height: 51.8vw;
        margin: 0 auto;
        margin-top: 50upx;
        display: block;
    }

    .card_bg_top {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .con {
        padding: 60upx;
    }

    .l_title {
        font-size: 36upx;
        font-weight: bold;
    }

    .l_title:after {
        width: 100%;
    }

    .title_h2 {
        font-size: 30upx;
        color: #666;
        font-weight: bold;
        margin-top: 10upx;
    }

    .title_h3 {
        font-size: 24upx;
        color: #666;
    }

    .title_h4 {
        font-size: 32upx;
        color: #BD1E2C;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 5;
        -webkit-box-orient: vertical;
    }

    .title_blue {
        font-size: 24upx;
        color: #00a3e0;
    }

    .primary {
        width: 90%;
        position: absolute;
        bottom: 60upx;
        left: 5%;
    }

    .act_footer {
        position: fixed;
        bottom: 0;
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
    }

    .act_right {
        width: 50%;
        height: 98upx;
        float: right;
        text-align: center;
        background-color: #262F43;
        color: white;
        line-height: 98upx;
    }

    .name {
        font-size: 52upx;
        color: #603a2b;
        line-height: 1.4;
    }

    .dept,
    .grade {
        font-size: 22upx;
        color: #603a2b;
    }

    .text_con view {
        font-size: 24upx !important;
        color: #666;
    }

    .title_h3 {
        color: #666666;
        font-size: 30upx !important;
        font-weight: bold;
    }

    .b_num {
        font-size: 46upx;
        font-weight: bold;
    }

    .uni-icon {
        width: 27upx;
        height: 27upx;
        background-color: #f9c558;
        border-radius: 50%;
        box-shadow: 0 0 1upx 10upx rgba(249, 197, 88, 0.4);
        margin-left: 4upx;
    }

    .uni-steps-items {
        margin: 0 !important;
    }

    .uni-steps-item-title {
        margin-left: -60upx;
        color: #000000;
        margin-top: 10upx;
    }

    .example {
        margin-top: -5upx;
        padding: 0 0 120upx 40upx;
    }

    .uni-steps-item:last-child .uni-steps-item-circle-container {
        left: -42upx !important;
    }

    .uni-steps-item:first-child .uni-steps-item-line {
        left: 14upx !important;
    }

    .uni-steps-item:first-child .uni-steps-item-circle-container {
        left: -13px !important;
    }

    .uni-steps-item-circle-container {
        left: -18px !important;
    }
</style>
