<template>
    <view class="content">
        <view class="action-row title-row-right">
            <navigator url="./info">校友卡说明</navigator>
        </view>
        <view class="top">
            <text class="l_title">欢迎申领校友卡</text>
            <view class="s_words">
                <view v-if="content!=''">
                    <uParse :content="content" />
                </view>
                <view v-else>
                    <view>长亭外，古道边，芳草碧连天。</view>
                    <view>问君此去几时来，来时莫徘徊。</view>
                    <view>天之涯，地之角，知交半零落。</view>
                    <view>人生难得是欢聚，惟有别离多。</view>
                </view>
            </view>
        </view>
        <view style="text-align: center;">
            <image class="image_noc" src="../../static/img/card-anymous.png"></image>
        </view>
        <view class="sjz">
            <view class="example">
                <uni-steps :options="list1" :active="active" />
                <!-- <button type="primary" @click="change">改变状态</button> -->
            </view>
        </view>
        <view class="input-row" style="margin-top:120upx;">
            <button type="primary" class="primary btn_dark" @tap="gotoReg" open-type="getUserInfo" @getuserinfo="getuserinfo">申领校友卡</button>
            <view class="primary btn_white" @click="goLogin()">已申领，立即登录</view>
        </view>
        <!--    <view class="action-row action-row-center">
            <navigator url="./info">校友卡说明</navigator>
        </view> -->
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import uniSteps from '@/components/uni-steps/uni-steps.vue';
    import uParse from '@/components/uParse/src/wxParse.vue'; //富文本框转义
    import util from '@/common/util.js'; //公共变量
    export default {
        components: {
            uniSteps,
            uParse
        },
        data() {
            return {
                active: 0,
                content: "",
                list1: [{
                    title: '申请认证'
                }, {
                    title: '认证中'
                }, {
                    title: '认证完成'
                }]
            }
        },
        onShow() {
            uni.request({
                url: app.serverPath + '/app/wx/getContentByPage',
                method: 'POST',
                header: {
                    'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                    code: 'nocard',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.content =util.strcharacterDiscode(res.data.data);
                    }
                },
                fail: (res) => {

                }
            })
        },
        methods: {
            getuserinfo(e) {
                console.log(e.detail.userInfo);
                //储存信息到本地
                if (e.detail.userInfo != undefined) {
                    uni.setStorageSync('weixin_nickName', e.detail.userInfo.nickName);
                    uni.setStorageSync('weixin_avatarUrl', e.detail.userInfo.avatarUrl);
                }
            },
            goLogin() {
                //util.getStatPoint('click','EVENT_DESCRIBE_3');
                uni.navigateTo({
                    url: "./login"
                });
            },
            gotoReg() {
                //util.getStatPoint('click','EVENT_DESCRIBE_2');
                uni.navigateTo({
                    url: "./camera"
                })
            },
            change() {
                if (this.active < this.list1.length - 1) {
                    this.active += 1
                } else {
                    this.active = 0
                }
            }
        }
    }
</script>

<style lang="scss">
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

    .content {
        padding: 20upx 30upx 0 30upx;
    }

    .top {
        padding-left: 30upx;
        padding-top: 100upx;
    }

    .s_words {
        font-size: $uni-font-size-sm;
        color: #999999;
        letter-spacing: 1upx;
    }

    .s_words view {
        line-height: 1.5;
        font-size:$uni-font-size-sm;
    }

    .image_noc {
        width: 90vw;
        height: 51.8vw;
        margin: 30upx auto;
    }

    .action-row navigator {
        color: #c59b5c;
        padding: 0 20upx;
        margin-top: 20upx;
    }

    .title-row-right {
        float: right;
    }

    .action-row-center {
        display: flex;
        flex-direction: row;
        justify-content: center;
    }

    .input-group {
        margin-top: 160upx;
        margin-left: 20upx;
        margin-right: 20upx;
    }

    .primary {
        width: 100%;
    }

    .uni-icon {
        width: 27upx;
        height: 27upx;
        background-color: #f9c558;
        border-radius: 50%;
        box-shadow: 0 0 1upx 10upx rgba(249, 197, 88, 0.4);
    }

    .content .sjz {
        margin-top: 15upx;
    }

    .uni-steps-items {
        margin-left: 60upx !important;
    }

    .uni-steps-item-title {
        margin-left: -60upx;
        color: #000000;
        margin-top: 10upx;
    }

    .button-hover[type=primary] {
        color: rgba(255, 255, 255, 1);
    }
</style>
