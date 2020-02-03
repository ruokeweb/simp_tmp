<template>
    <!-- 模板列表 -->
    <view class="con">
       	<view  class="uni-list" v-if="contactList.length >0" >
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in contactList" :key="key" @click="goDetail(value)">
				<view class="uni-left">
					<view class="con-left">
						<view v-if="value.isDefault === 'IS_DEFAULT'" class="button default">默认</view>
					</view>
				   <view class="right-info" >
						<text class="info-main">{{value.contact}}</text>
						<text class="uni-text">{{value.value}}</text>
					</view>
				</view>
				<view class="uni-right">
					<text class="uni-edit">编辑</text>
				</view>
			</view>
           
        </view>
		<view v-else class="uni-nodata">
			暂无数据
		</view>
        <view class="btn-con">
            <button type="primary" class="primary btn_dark" @click="goDetail()">新增联系方式</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import {
        mapState
    } from 'vuex';

    export default {
        computed: {
            ...mapState(['hasLogin','userInfo'])
        },
        data() {
            return {
                contactList: []
            }
        },
        onLoad() {

        },
        onShow() {
            this.contactList=[];
            this.getContactList();
        },
        methods: {
            getContactList() {
                request.http({
                    url: '/app/contact/getContacats',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.data;
                           
                            datas.forEach((d) => {
                                d["value"]=loadcache.getDictByvalue(d.type, app.DICT_TYPECODE.CONTACT_TYPE).label;
                                this.contactList.push(d);
                            });
                        }
                    },
                    error: (res) => {}
                });
            },
            goDetail(value) {
                var url='';
                if(value==undefined){
                    url = '/pages_mine/infomation/contact/contactDetail';
                }else{
                    url = '/pages_mine/infomation/contact/contactDetail?id=' + encodeURIComponent(JSON.stringify(value.id));
                }
                
                uni.navigateTo({
                    url
                });
            }
        }
    }
</script>

<style lang="scss">
    .uni-list-cell {
        padding: 20upx 0;
    }

    // .uni-list-cell::after {
    //     left: 0;
    // }

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
        color: #999;margin-left: 20upx;
    }

    .con-left {
        display: inline-block;
        width: 25%;
        position: relative;
        left: 20upx;
        
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
        padding: 0 20upx;
    }

    .btn-con {
        width: 90vw;
        position: absolute;
        bottom: 70upx;
        left: 5vw;
    }
</style>
