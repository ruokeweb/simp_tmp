<template>
    <view class="con">
        <view class="uni-list" v-if="professionList.length >0">
            <view v-for="(value, key) in professionList" :key="key" @click="goDetail(value.id)">
                <view class="uni-list-cell" hover-class="uni-list-cell-hover">
                    <view class="uni-left">
                        <view class="con-left">
                            <!-- <view class="button"
                               :class="value.isDefault === true ? 'button default': 'button' "
                                @click="setDefault(value)">默认</view> -->
                        </view>
                        <view class="right-info">
                            <view class="info-main">{{value.workplace}}</view>
                            <view class="uni-text">{{value.industry}}</view>
                        </view>
                    </view>
                    <view class="uni-right">
                        <text class="uni-edit">编辑</text>
                    </view>
                </view>
            </view>
        </view>
        <view v-else class="uni-nodata">
            暂无数据
        </view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="goDetail()">新增职业经历</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import loadcache from '@/common/loadcache.js';
    export default {
        data() {
            return {
                professionList: []
            }
        },
        onShow() {
            this.getProfession();
        },
        methods: {
            getProfession() {
                request.http({
                    url: "/app/profession/getProList",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageNo: 1,
                        pageSize: 30
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.professionList = res.data.data;
                            for (let i = 0; null != this.professionList && i < this.professionList.length; i++) {
                                // this.professionList[i].industry = 
                                var arrTem = [];
                                var parentid = "root";
                                var industry = this.professionList[i].industry;
                                do {

                                    var industryObj = loadcache.getObjFromStorageById(app.CACHE_NAME.SYSINDUSTRYBASE,
                                        industry);
                                    parentid = industryObj.parentId;
                                    arrTem.push(industryObj)
                                    industry = industryObj.parentId;
                                } while (parentid != "root");
                                this.professionList[i].industry = "";
                                for (var j = arrTem.length; j > 0; j--) {
                                    if (i == arrTem.length) {
                                        this.professionList[i].industry = arrTem[j - 1].name;
                                    } else {
                                        this.professionList[i].industry = arrTem[j - 1].name + '-' + this.professionList[
                                            i].industry;
                                    }

                                }

                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            setDefault(value) {
                //具体开发时再处理
                console.log(value);
                value.isDefault = !value.isDefault;
            },
            goDetail(id) {
                var url = "";
                if(null == id){
                    url = '/pages_mine/infomation/profession/professionDetail';
                }else{
                    url = '/pages_mine/infomation/profession/professionDetail?id=' + id;
                }
                uni.navigateTo({
                    url
                });
            }
        }
    }
</script>

<style>
    .uni-list-cell {
        padding: 20upx 0;
    }
/*    .uni-list-cell {
        height: 190upx;
    }

    .uni-list-cell::after {
        left: 0;
    } */

    .uni-left {
        width: 80%;
        float: left;
    }

    .uni-right {
        width: 17%;
        float: right;
        padding-left: 50upx;
        border-left: 1upx solid #e5e5e5;
    }

    .uni-text {
        color: #999;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        line-clamp: 2;
        -webkit-box-orient: vertical;
    }

    .con-left {
        display: inline-block;
        width: 25%;
        position: relative;
        left: 20upx;
        bottom: 50upx;
    }

    .right-info {
        display: inline-block;
        width: 70%;
    }

    .info-main {
        height: 70upx !important;
        overflow: hidden;
        line-height: 1.2;
        padding-top: 20upx;
    }

    .button {
        width: 100upx;
        height: 40upx;
        line-height: 40upx;
        border-radius: 20upx;
        border: solid 2upx rgba(225, 182, 91, 0.4);
        text-align: center;
    }

    .default.button {
        background-image: linear-gradient(0deg,
            rgba(243, 231, 200, 0.4) 0%,
            rgba(222, 196, 144, 0.4) 100%);
        border: none;
    }

    .uni-edit {
        font-size: 30upx;
        color: #999;
    }

    .con {
        height: calc(100vh - 180upx);
        overflow-y: auto;
    }

    .btn-con {
        width: 90vw;
        position: absolute;
        bottom: 70upx;
        left: 5vw;
    }
</style>
