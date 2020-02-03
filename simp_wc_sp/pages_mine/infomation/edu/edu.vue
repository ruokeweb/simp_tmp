<template>
    <view class="con">
        <view class="uni-list"  v-if="eduList.length >0">
            
                <view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in eduList" :key="key" @click="goDetail(value.id)">
                    <view class="uni-left">
                       <view class="con-left">
<!--                            <view class="button" :class="value.isDefault === 'IS_DEFAULT' ? 'button default': 'button' "
                                @click="setDefault(value)">默认</view> -->
                        </view>
                        <view class="right-info" >
                            <view class="info-main">{{value.series}}</view>
                            <view class="uni-text">{{value.specialty}}</view>
                        </view>
                    </view>
                    <view class="uni-right">
                        <text class="uni-edit">编辑</text>
                    </view>
                </view>
         
        </view>
        <view v-else class="uni-nodata">
            没有更多
        </view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="goDetail(0)">新增教育经历</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import loadcache from '@/common/loadcache.js';
    import {
        mapMutations,
        mapState
    } from 'vuex';
    export default {
        data() {
            return {
                eduList: []
            }
        },
        onShow() {
            this.getFamliyList();
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo']),
        },
        methods: {
            ...mapMutations(['login', 'setUserInfo']),
            getFamliyList() {
                request.http({
                    url: '/app/edu/getEduList',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        /* pageNo: 1,
                         pageSize: 30 */
                    },
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.eduList = res.data.data;
                            console.log(this.eduList);
                            for (let i = 0; i < this.eduList.length; i++) {
                                if(this.eduList[i].series!=""&&this.eduList[i].series!=null){
                                    this.eduList[i].series = loadcache.getObjFromStorageById(app.CACHE_NAME
                                        .DEPARTMENTBASE, this.eduList[i].series).name;
                                }
                                if(this.eduList[i].specialty!=""&&this.eduList[i].specialty!=null){
                                    this.eduList[i].specialty = loadcache.getObjFromStorageById(
                                        app.CACHE_NAME.DEPARTMENTBASE, this.eduList[i].specialty
                                    ).name;
                                }
                                
                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none', 
                    title: msg
                });
            },
            setDefault(value) {
//                 if (value.isDefault == "IS_DEFAULT") {
//                     this.showpageToast("已经是默认教育经历");
//                     return false;
//                 } else {
//                     uni.showModal({
//                         title: '',
//                         content: '确定设置为默认教育经历？',
//                         success: (res) => {
//                             if (res.confirm) {
//                                 let defaultStr = "IS_DEFAULT";
//                                 request.http({
//                                     header: 'application/x-www-form-urlencoded',
//                                     url: "/app/edu/setDefault",
//                                     data: {
//                                         id: value.id,
//                                         isDefault: defaultStr,
//                                         /* pageNo: 1,
//                                         pageSize: 30 */
//                                     },
//                                     success: (res) => {
//                                         //  let list = this.setTime(res.data.data);
//                                         if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
//                                             this.showpageToast("设置成功");
//                                             this.eduList = res.data.data;
//                                             console.log(this.eduList);
//                                             for (let i = 0; i < this.eduList.length; i++) {
//                                                 this.eduList[i].series = loadcache.getObjFromStorageById(
//                                                     app.CACHE_NAME
//                                                     .DEPARTMENTBASE, this.eduList[i].series
//                                                 ).name;
//                                                 this.eduList[i].specialty = loadcache.getObjFromStorageById(
//                                                     app.CACHE_NAME.DEPARTMENTBASE, this.eduList[
//                                                         i].specialty
//                                                 ).name;
//                                                 // this.eduList[i].departmentName ="";
//                                                 // this.eduList[i].subjectName ="";\
//                                             }
// 
//                                             request.http({
//                                                 header: 'application/x-www-form-urlencoded',
//                                                 url: "/app/edu/getEduInfo",
//                                                 data: {
//                                                     id: value.id,
//                                                 },
//                                                 success: (res) => {
//                                                     //  let list = this.setTime(res.data.data);
//                                                     if (res.data.code == app.RESPONSE_STATUS
//                                                         .REQUEST_SUCCESS) {
//                                                         var user = this.userInfo;
//                                                         user.smEducation = res.data
//                                                             .data;
//                                                         this.setUserInfo(user);
//                                                     }
//                                                 },
//                                                 fail: (data, code) => {
//                                                     console.log(data);
//                                                 }
//                                             })
//                                         }
//                                     },
//                                     fail: (data, code) => {
//                                         console.log(data);
//                                     }
//                                 })
//                             }
//                         }
//                     });
// 
//                 }
            },
            goDetail(id) {
                console.log(id);
                var url = '/pages_mine/infomation/edu/eduDetail?id=' + encodeURIComponent(JSON.stringify(id));;
                uni.navigateTo({
                    url
                });
            }
        }
    }
</script>

<style lang="scss">
    // .uni-list .uni-list-cell {
    //    padding: 20rpx 0;position: relative;
    //    &:after{
    //        position: absolute;contain: "";top: 0;left: 20upx;right: 20upx;height: 2upx;-webkit-transform: scaleY(.5);-ms-transform: scaleY(.5);
    //        transform: scaleY(.5);background-color: #c8c7cc;
    //    }
    // }
    // .uni-list .uni-list-cell:last-child::after{
    //     height: 2upx;
    // }
    // .uni-list:after{
    //     left: 20upx;
    //     right: 20upx;
    // }
    // .uni-list-cell::after {
    //     left: 0;
    // }
    .uni-list-cell {
        padding: 20upx 0;
    }

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
