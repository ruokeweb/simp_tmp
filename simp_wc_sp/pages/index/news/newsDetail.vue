<template>
    <view class="detail_spacing">
        
        <view class="news-title">{{title}}</view>
		<view class="article-meta">
		    <!-- <text class="article-author">{{author}}</text> -->
		    <text class="article-text">发表于</text>
		    <text class="article-time">{{createDate}}</text>
		</view>
        <view class="article-content">
            <!--            <rich-text :nodes="htmlNodes">{{content}}</rich-text> -->
            <uParse :content="content" @preview="preview" @navigate="navigate" />
        </view>
        <view class="relation">
            相关阅读
        </view>
        <view class="tips">
            <view v-for="(value, key) in newslist" :key="key" class="relation-news" @click="goDetail(value)">
                <!-- <view class="label"> 上一篇 </view> -->
                <text>{{value.title}}</text>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
    export default {
        components: {
            uParse
        },
        data() {
            return {
                id: '',
                title: '',
                image: '',
                content: '',
                author: '',
                createDate: '',
                newslist: '',
                viewid: '',
                treeid: '',
                pageNo: 1,
                pageSize: 3
            }
        },
        onLoad(event) {
            // TODO 后面把参数名替换成 payload
            const payload = event.id || event.payload;
            // 目前在某些平台参数会被主动 decode，暂时这样处理。
            this.id = decodeURIComponent(payload);
            // console.log(this.id);
            // this.getNews(this.id);
        },
        onShow(event) {
            console.log(this.id);
            this.getNewsDetail(this.id);
        },
        methods: {
            getNewsDetail(e) {
                request.http({
                    url: '/app/index/getNewsDetail',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: e
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log(res.data.data);
                            var news = res.data.data;
                            this.title = news.title;
                            this.createDate = news.date;
                            this.content = news.wbcontent;
                            this.treeid = news.wbtreeid;
                            this.viewid = news.wbviewid;
                            console.log(this.treeid);
                            console.log(this.viewid);
                            this.getrelationNews();
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            goDetail: function(e) {
                console.log(e);
                let url = '/pages/index/news/newsDetail?id=' + e.newsid;
                uni.navigateTo({
                    url: url
                });
            },
            /*获取推荐文章*/
            getrelationNews: function(){
                console.log(this.treeid);
                console.log(this.viewid);
                request.http({
                    url: '/app/index/getrelationNews',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo : this.pageNo,
                        pageSize: this.pageSize,
                        wbtreeid: this.treeid,
                        wbviewid: this.viewid,
                        newsid: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.newslist = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            }
        },

    }
</script>

<style>
	.detail_spacing{padding: 0 30upx;}
	
    .banner {
        height: 360upx;
        overflow: hidden;
        position: relative;
        background-color: #ccc;
    }

    .banner-img {
        width: 100%;
    }

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
        padding: 30upx 0 0;
        font-size:32upx;
		text-align: center;
    }

    .article-meta {
        padding: 0 40upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
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
        height: 40upx;
        border-radius: 20upx;
        width: 100upx;
        text-align: center;
        color: #4c3915;
        float: left;
        font-size: 24upx;
        line-height: 40upx;
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
