<!-- 分享排名 -->
<template>
    <view class="space">
        <view class="pg_bg"><img src="/static/img/pages_rank/bg_icon.png" /></view>
        <view class="ranking_index">
            <view class="title_1"><text>分享排名</text></view>
            <view class="tab_title">
                <text v-for="(item,index) in tabList" :key="item.id" :class="{active:num==index}" @click="getNum(index)">{{item}}</text>
            </view>
            <view class="tab_content">
                <view>
                    <view class="tab_content_0 bottom_line">
                        <view class="ranking_list" style="position: absolute;left: 0;bottom: 56upx;" v-if="null!=ranking[1].smSchoolmate">
                            <view class="photo">
                                <view class="second"><img src="/static/img/pages_rank/second.png" /></view>
                                <img v-if="null!=ranking[1].smSchoolmate.truePhoto && '' != ranking[1].smSchoolmate.truePhoto && 'null'!= ranking[1].smSchoolmate.truePhoto && 'undefined'!= ranking[1].smSchoolmate.truePhoto"
                                    :src="serverPath+ranking[1].smSchoolmate.truePhoto" mode="aspectFill" />
                                <img v-else src="/static/img/default.png" />
                            </view>
                            <view class="name_text">
                                <text class="name_text">{{ranking[1].smSchoolmate.name}}</text>
                                <text class="name_text">分享{{ranking[1].shareTimes}}次</text>
                            </view>
                        </view>
                        <view class="ranking_list" style="position: absolute;left: 0;right: 0;margin: auto;" v-if="null!=ranking[0].smSchoolmate">
                            <view class="photo first_n">
                                <view class="first"><img src="/static/img/pages_rank/first.png" /></view>
                                <img v-if="null!=ranking[0].smSchoolmate.truePhoto && '' != ranking[0].smSchoolmate.truePhoto && 'null'!= ranking[0].smSchoolmate.truePhoto && 'undefined'!= ranking[0].smSchoolmate.truePhoto"
                                    :src="serverPath+ranking[0].smSchoolmate.truePhoto" mode="aspectFill" />
                                <img v-else src="/static/img/default.png" />
                            </view>
                            <view>
                                <view class="name_text">
                                    <text>{{ranking[0].smSchoolmate.name}}</text>
                                    <text>分享{{ranking[0].shareTimes}}次</text>
                                </view>
                            </view>
                        </view>
                        <view class="ranking_list" style="position: absolute;right: 0;bottom: 56upx;" v-if="null!=ranking[2].smSchoolmate">
                            <view class="photo">
                                <view class="third"><img src="/static/img/pages_rank/third.png" /></view>
                                <img v-if="null!=ranking[2].smSchoolmate.truePhoto && '' != ranking[2].smSchoolmate.truePhoto && 'null'!= ranking[2].smSchoolmate.truePhoto && 'undefined'!= ranking[2].smSchoolmate.truePhoto"
                                    :src="serverPath+ranking[2].smSchoolmate.truePhoto" mode="aspectFill" />
                                <img v-else src="/static/img/default.png" />
                            </view>
                            <view>
                                <view class="name_text">
                                    <text>{{ranking[2].smSchoolmate.name}}</text>
                                    <text>分享{{ranking[2].shareTimes}}次</text>
                                </view>
                            </view>
                        </view>

                    </view>
                    <view>
                        <view class="ranking_item bottom_line" v-for="(value, key) in order" :key="key">
                            <text class="no_hao">{{value.ranking}}</text>
                            <view class="photo_hao">
                                <img v-if="null!=value.smSchoolmate.truePhoto && '' != value.smSchoolmate.truePhoto && 'null'!= value.smSchoolmate.truePhoto && 'undefined'!= value.smSchoolmate.truePhoto"
                                    :src="serverPath+value.smSchoolmate.truePhoto" mode="aspectFill" />
                                <img v-else src="/static/img/default.png" />
                                {{value.smSchoolmate.name}}
                            </view>
                            <text class="state_hao">分享{{value.shareTimes}}次</text>
                        </view>
                    </view>
                    <button class="btn_share" id="btn_share" open-type="share"> 分享</button>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';
    export default {
        data() {
            return {
                serverPath: app.imagePath,
                num: 0,
                school_name: "",
                tabList: ['当月', '今年'],
                ranking: [], //前三名排名列表
                order: [] // 下面的顺序列表
            }
        },
        computed: {
            ...mapState(['configParams'])
        },
        onShow() {
            this.school_name = this.configParams.school_name;
            this.getYearDate();
        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.SHARERANK_SHARE);
            return {
                title: this.school_name + '分享排名',
                path: '/pages_mine/myCard/shareRank'
            }
        },
        methods: {
            getNum(index) {
                this.num = index;
                console.log(this.num)
                if (index == 1) {
                    this.getYearDate();
                } else {
                    this.getMonthDate();
                }
            },
            getYearDate() { //获取当年的数据
                let date = new Date();
                let year = date.getFullYear();
                //获取排名
                request.http({
                    url: '/app/ranking/getChaShare',
                    method: "POST",
                    data: {
                        year: year
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.ranking = res.data.data.ranking;
                            this.order = res.data.data.order;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getMonthDate() { // 获取当月的数据
                let date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                //获取排名
                request.http({
                    url: '/app/ranking/getChaShare',
                    method: "POST",
                    data: {
                        year: year,
                        month: month
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.ranking = res.data.data.ranking;
                            this.order = res.data.data.order;
                        }
                    },
                    error: (res) => {

                    }
                });
            }
        }
    }
</script>

<style>
    .space {
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color: #fdfdfb;
        height: 100%;
    }

    .pg_bg {
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;

    }

    .pg_bg img {
        width: 100%;
        height: 100%;
        margin-top: -120upx;
    }

    .title_1 {
        font-size: 48upx;
        text-align: center;
        color: #b68929;
        font-weight: bold;
        padding-top: 50upx
    }

    .ranking_index {
        margin-top: 20upx;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        padding: 0 64upx;
    }

    .tab_content {
        margin-top: 66upx;

    }

    .tab_title {
        font-size: 24upx;
        width: 190upx;
        margin: auto;
        border: 2upx solid #dab159;
        margin-top: 30upx;
        height: 50upx;
        line-height: 50upx;
    }

    .tab_title text {
        width: 50%;
        text-align: center;
        display: inline-block;
        color: #dcb25a;
    }

    .tab_title text.active {
        background: #dab159;
        color: #fff;
    }

    .ranking_list {
        width: 33.333333%;
        display: inline-block;
    }

    .photo {
        text-align: center;
        position: relative;
        margin-bottom: 8upx;
    }

    .photo view {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20upx;
        background-size: 100%;
        background-repeat: no-repeat;
    }

    .photo img {
        width: 84upx;
        height: 84upx;
        border-radius: 50%;
    }

    .photo view img {
        width: 46upx;
        height: 34upx;
    }

    .photo.first_n img {
        width: 138upx;
        height: 138upx;
    }

    .photo.first_n view img {
        width: 62upx;
        height: 44upx;
    }

    .name_text {
        text-align: center;
        color: #666;
        font-size: 24upx;
    }

    .name_text text {
        display: block;
    }

    .tab_content_0 {
        position: relative;
        padding-bottom: 20px;
        height: 260upx;

    }

    .bottom_line {
        border-bottom: 1upx solid #dcdcdc;
    }

    .ranking_item {
        line-height: 66upx;
        padding: 20upx 0 20upx;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .ranking_item text {
        display: inline-block;
        float: left;
        font-size: 24upx;
        color: #666;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .ranking_item text.no_hao {
        font-size: 38upx;
        color: #e3b85c;
        width: 100upx;
    }

    .ranking_item .photo_hao {
        width: calc(100% - 270upx);
        display: inline-block;
        float: left;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .ranking_item text.state_hao {
        width: 170upx;
    }

    .ranking_item .photo_hao img {
        width: 66upx;
        height: 66upx;
        border-radius: 50%;
        vertical-align: middle;
        margin-right: 30upx;
    }

    button.btn_share {
        font-size: 36upx;
        color: #ffffff;
        margin-top: 26upx;
        background: #272d3d;
        border-radius: 44upx;
        margin-bottom: 40upx;
    }

    button.btn_share:after {
        border: 0;
    }
</style>
