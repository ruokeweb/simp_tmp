<template>
    <view>
        <view class="news-title">{{title}}</view>
        <view v-if="appendix!=null && appendix.length!=0" class="page-section page-section-gap" style="text-align: center;">
            <!-- :src="imagePath+appendix" -->
            <audio style="text-align: left"  autoplay preload :name="appendixName" id="myAudio" :action="audioAction"
                controls></audio>
        </view>
        <view class="article-content">
            <uParse :content="content" @preview="preview" @navigate="navigate" />
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
    export default {
        components: {
            uParse
        },
        data() {
            return {
                appendix: '',
                title: '',
                appendixName: '',
                content: '',
                imagePath: app.imagePath,
                audioAction: {
                    method: 'onPlay'
                }
            }
        },
        onLoad(option) {
            uni.showLoading({
                title: '加载中',
                mask: true
            })
            this.getMediaDetail(option.id);
        },
        methods: {
            getMediaDetail(id) {
                request.http({
                    url: '/app/index/getIndexMediaMessageDetail',
                    method: "POST",
                    data: {
                        id: id
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.data != null) {
                                this.title = util.strcharacterDiscode(res.data.data.title);
                                this.content = util.strcharacterDiscode(res.data.data.content);
                                this.appendix = res.data.data.appendix;
                                this.appendixName = res.data.data.appendixName;
                                this.playAudio(this.imagePath+this.appendix);
                                // audio.play();//音频播放
                                uni.hideLoading();
                            }
                        }
                    },
                    error: (res) => {

                    }
                })
            },
            playAudio(src){
                const innerAudioContext = wx.createAudioContext('myAudio');
                innerAudioContext.setSrc(src);
                innerAudioContext.play();
            }
        }
    }
</script>

<style>
    .relation {
        font: 26upx;
        font-weight: bold;
        margin-left: 20upx;
    }

    .banner-title {
        max-height: 84upx;
        overflow: hidden;
        position: absolute;
        left: 30upx;
        bottom: 30upx;
        width: 90%;
        font-size: 32upx;
        font-weight: 400;
        line-height: 42upx;
        color: white;
        z-index: 11;
    }

    .news-title {
        padding: 30upx;
        font-size: 37upx;
        text-align: center;
    }

    .article-meta {
        padding: 20upx 40upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        color: gray;
    }

    .article-content image {
        width: 100%;
        /* border-radius: 10upx; */
        display: block;
        margin: 20upx auto;
    }

    .article-text {
        font-size: 26upx;
        line-height: 50upx;
        margin: 0 20upx;
    }

    .article-author,
    .article-time {
        font-size: 30upx;
    }

    .article-content {
        /* padding: 20upx; */
        overflow: hidden;
        font-size: 30upx;
        margin-bottom: 30upx;
        margin-left: 10upx;
        margin-right: 10upx;
    }

    .label {
        height: 40rpx;
        border-radius: 20rpx;
        width: 100upx;
        text-align: center;
        color: #4c3915;
        float: left;
        font-size: 24rpx;
        line-height: 40rpx;
        background-image: -webkit-gradient(linear, left top, right top, from(rgba(243, 231, 200, 0.4)), to(rgba(222, 196, 144, 0.4)));
        background-image: -o-linear-gradient(left, rgba(243, 231, 200, 0.4) 0%, rgba(222, 196, 144, 0.4) 100%);
        background-image: linear-gradient(90deg, rgba(243, 231, 200, 0.4) 0%, rgba(222, 196, 144, 0.4) 100%);
        padding: 2upx;
        margin-right: 10upx;
    }

    .tips {
        padding: 30upx;
    }

    .tips text {
        line-height: 1;
    }

    .relation-news {
        margin-top: 10upx;
        color: #0451A5;
    }
</style>
