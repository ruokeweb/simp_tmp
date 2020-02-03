<template>
    <view>
        <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
            <view class="flex-item" @click="onClickItem(0)">
                <text :class="cur==0 ? 'on' : '' ">未读</text>
                <!-- <view v-if="cur==0" class="line" :style="{transform:'translateX(80px)',width:'250upx'}"></view> -->
            </view>
            <view class="flex-item" @click="onClickItem(1)">
                <text :class="cur==1 ? 'on' : '' ">已读</text>
                <!-- <view v-if="cur==1" class="line" :style="{transform:'translateX(80px)',width:'250upx'}"></view> -->
            </view>
        </view>

        <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108"  :current="cur" @clickItem="onClickItem"></segmented-control> -->
        <view class="list" id="list">
            <view class="list-cell" v-show="cur === 0" v-for="(value, key) in newMessageList" :key="key"  @click="showMessage('middle-con',value.id)">
                <view>
                    <view class="title"> {{value.title}}</view>
                    <view class="time">{{value.createDate}}</view>
                </view>
            </view>
            <view class="list-cell" v-show="cur === 1" v-for="(value, key) in messageList" :key="key" @click="showMessage('middle-con',value.id)">
                <view>
                    <view class="title"> {{value.title}}</view>
                    <view class="time"> {{value.createDate}}</view>
                </view>
            </view>
        </view>

        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="showMessage('','')">
            <view>
                <view v-if="mesContent!=''">
                    <uParse :content="mesContent" @preview="preview" @navigate="navigate" />
                </view>
                <view v-if="mesContent ==''">
                    无内容
                </view>
            </view>
        </uni-popup>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import request from '@/common/request.js';
    import uniPopup from '@/components/uni-popup/uni-popup.vue';
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
	  import util from '@/common/util.js';
    export default {
        components: {
            segmentedControl,
            uniPopup,
            uniLoadMore,
            uParse
        },
        data() {
            return {
                mesContent: "",
                type: "",
                items: ['未读', '已读'],
                cur: 0, //当前选中的下标
                newMessageList: [],
                messageList: [],
            }
        },
        methods: {
            showMessage(type, index) {
                this.mesContent ="读取中..."; 
                this.type = type;
                if (index == '' || null == index) {
                    return;
                }
                if (this.cur == 0) {
                    request.http({
                        url: '/app/getMesContent',
                        method: "POST",
                        data: {
                            id: index
                        },
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                this.mesContent = util.strcharacterDiscode(res.data.data.content);
                            }
                        },
                        error: (res) => {

                        }
                    });
                } else if (this.cur == 1) {
                    // this.mesContent = this.messageList[index].title;
                    request.http({
                        url: '/app/getMesContentById',
                        method: "POST",
                        data: {
                            id: index
                        },
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                this.mesContent = res.data.data.content;
                            }
                        },
                        error: (res) => {

                        }
                    });
                }
            },
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur === 0) {
                    this.findUnReadMesList();
                } else {
                    this.findReadMesList();
                }
            },
            findUnReadMesList() {
                request.http({
                    url: '/app/getMesListUnReadBy',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.newMessageList = res.data.data
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            findReadMesList() {
                request.http({
                    url: '/app/getMesListReadBy',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.messageList = res.data.data
                        }
                    },
                    error: (res) => {

                    }
                });
            }
        },
        onShow() {
            this.findUnReadMesList();
        }

    }
</script>

<style>
    Page {
        background-color: #f2f2f2;
    }

    .list-cell {
        height: 140upx;
        background-color: #fff;
        margin-bottom: 10upx;
        padding: 10upx;
    }

    .uni-row {
        background-color: #fff;
    }

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
    }

    .line {
        width: 56upx !important;
        height: 6upx !important;
        background-color: #262f43;
        border-radius: 3upx !important;
        position: absolute;
        top: 98upx;
        transition: all 0.3s;
    }

    .title {
        line-height: 1.2;
        height: 68upx;
        overflow: hidden;
        font-size: 30upx;
    }

    .time {
        font-size: 24upx;
        float: right;
        color: #999999;
        margin-top: 26upx;
    }

    .segmented-control {
        padding: 25rpx 0 19rpx 155rpx !important;
    }

    .segmented-control-item:last-child {
        margin-left: 150rpx;
    }

    .on {
        border-bottom: 4upx solid #272D3D;
    }

    .flex-item text {
        height: 100rpx;
        display: inline-block;
    }
</style>
