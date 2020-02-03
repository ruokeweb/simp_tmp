<template>
    <view class="content">
        <view>
            <image class="card_bg_top" src="../../static/card-bg-top.png" mode="widthFix"></image>
        </view>
        <view>
            <image class="image_noc" src="../../static/img/card-anymous.png"></image>
        </view>
        <view class="text-con">
            <view class="title_h3">亲爱的校友，您好！</view>
            <view v-if="content!=''">
                <uParse :content="content" />
            </view>
            <view v-else="">
                <view>校友卡是校友身份的象征， </view>
                <view>拥有校友卡可以让您享受诸多的校友服务呦！</view>
            </view>
        </view>
    </view>
</template>
<script>
    import app from '@/common/app.js'; //公共变量
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
     import util from '@/common/util.js';
    export default {
        components: {
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
                    code: 'info',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.content = util.strcharacterDiscode(res.data.data);
                    }
                },
                fail: (res) => {

                }
            })
        },
        methods: {}
    }
</script>
<style>
    view {
        line-height: 1.4;
    }

    .image_noc {
        width: 90vw;
        height: 51.8vw;
        margin: 0 auto;
        margin-top: 50upx;
        display: block;
    }

    .text-con {
        padding: 50upx 60upx;
        color: #666666;
    }

    .text-con view {
        font-size: 24upx;
    }

    .card_bg_top {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .title_h3 {
        color: #666666;
        font-size: 30upx !important;
        font-weight: bold;
        padding-bottom: 20upx;
    }
</style>
