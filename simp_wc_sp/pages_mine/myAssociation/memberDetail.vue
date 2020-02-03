<template>
    <view class="content">
        <view class="card">
            <view class="left">
                <image class="head" v-if="userInfo.virtualPhoto==null||userInfo.virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                <image class="head" v-if="userInfo.virtualPhoto!=null&&userInfo.virtualPhoto!=''" :src="imagePath + userInfo.virtualPhoto" mode="aspectFill"></image>
                <text>{{userInfo.virtualName==null?'':userInfo.virtualName}}</text>
            </view>
            <view class="right">
                <view>入学时间 <text v-if="userInfo.startdate!=null">{{userInfo.startdate}}级</text></view>
                <view>学　　历 <text>{{userInfo.eduRecord==null?'':userInfo.eduRecord}}</text></view>
                <view>院　　系 <text>{{userInfo.series==null?'':userInfo.series}}</text></view>
                <view>专　　业 <text>{{userInfo.specialty==null?'':userInfo.specialty}}</text></view>
                <view>班　　级 <text>{{userInfo.classes==null?'':userInfo.classes}}</text></view>
            </view>
        </view>
        <view class="list">
            <uni-collapse @change="change">
                <uni-collapse-item title="他的捐赠" :show-badge="true" thumb="/static/img/my/myDon.png" @click="onClickMine">
                    <view class="content_view_item">
                        <text>{{userInfo.hisDonVo.name}}</text>
                        <text class="money" v-if="userInfo.hisDonVo!=null">{{userInfo.hisDonVo.money}}元</text>
                        <text class="time_1">{{userInfo.hisDonVo.createDate==null?'':userInfo.hisDonVo.createDate}}</text>
                    </view>
                </uni-collapse-item>
            </uni-collapse>
            <uni-collapse @change="change">
                <uni-collapse-item title="他的活动" :show-badge="true" thumb="/static/img/my/myAct.png" @click="onClickMine">
                    <view class="content_view_item">
                        <text >{{userInfo.actListVo.name}}</text>
                        <text class="time">{{userInfo.actListVo.createDate==null?'':userInfo.actListVo.createDate}}</text>
                    </view>
                </uni-collapse-item>
            </uni-collapse>
            <uni-collapse @change="change">
                <uni-collapse-item title="他的校友会" :show-badge="true" thumb="/static/img/my/mySch.png" @click="onClickMine">
                    <view class="content_view_item">
                        <text >{{userInfo.hisAssVo.name}}</text>
                        <text class="time">{{userInfo.hisAssVo.type==null?'':userInfo.hisAssVo.type}}</text>
                    </view>
                </uni-collapse-item>
            </uni-collapse>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import util from '@/common/util.js';
    import uniListItem from '@/components/uni-list-item/uni-list-item.vue';
    import uniCollapse from '@/components/uni-collapse/uni-collapse.vue';
    import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue';
    import uniList from '@/components/uni-list/uni-list.vue';
    import loadcache from '@/common/loadcache.js';
    export default {
        components: {
            uniCollapse,
            uniCollapseItem,
            uniList,
            uniListItem
        },
        data() {
            return {
                id: '', //用户id
                extraIcon: {
                    color: '#4cd964',
                    size: '22',
                    type: 'spinner'
                },
                userInfo: {},
                imagePath: app.imagePath
            }
        },
        onLoad(event) {
            const payload = event.id || event.payload;
            console.log(payload);
            try {
                this.id = JSON.parse(decodeURIComponent(payload));
            } catch (error) {
                this.id = JSON.parse(payload);
            };
        },
        onShow() {
            this.getHisInfomation();
        },
        methods: {
            getHisInfomation() {
                request.http({
                    url: '/app/his/getHisInfomation',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.data.startdate != null) {
                                res.data.data["startdate"] = util.dateUtils.getYearByDate(res.data.data.startdate);
                            }
                            if (res.data.data.specialty != null) {
                                res.data.data["specialty"] = loadcache.getObjFromStorageById(app.CACHE_NAME
                                    .DEPARTMENTBASE, res.data.data.specialty).name
                            }
                            if (res.data.data.series != null) {
                                res.data.data["series"] = loadcache.getObjFromStorageById(app.CACHE_NAME
                                    .DEPARTMENTBASE, res.data.data.series).name
                            }
                            if (res.data.data.college != null) {
                                res.data.data["college"] = loadcache.getObjFromStorageById(app.CACHE_NAME
                                    .DEPARTMENTBASE, res.data.data.college).name
                            }
                            if (res.data.data.eduRecord != null) {
                                res.data.data["eduRecord"] = loadcache.getDictByvalue(res.data.data.eduRecord,
                                    app.DICT_TYPECODE.EDU_RECORD).label;
                            }
                            if(res.data.data.hisAssVo!=null){
                                 res.data.data.hisAssVo["type"] = loadcache.getDictByvalue(res.data.data.hisAssVo["type"],
                                    app.DICT_TYPECODE.ASSOCIATION_TYPE).label;
                            }
                            this.userInfo = res.data.data;
                            if(this.userInfo.actListVo != null && this.userInfo.actListVo != undefined){
                                this.userInfo.actListVo.name = util.strcharacterDiscode(this.userInfo.actListVo.name);
                            }
                            if(this.userInfo.hisDonVo != null && this.userInfo.hisDonVo != undefined){
                                 this.userInfo.hisDonVo.name = util.strcharacterDiscode(this.userInfo.hisDonVo.name);
                            }
                        }
                    },
                    error: (res) => {

                    }
                });
            }
        }

    }
</script>

<style lang="scss">
    Page {
        background: #f2f2f2;
    }

    .card {
        width: 100%;
        height: 390upx;display: flex;align-items: center;
        background: #fff;
    }

    .left {
        width: 33%;
        display: inline-block;
        text-align: center;
        vertical-align: top;
       
    }

    .left text {     
        font-size: $uni-font-size-base;
        font-weight: bold;margin-top: 20rpx;display: inline-block;
    }

    .right {
        width: 55%;
        display: inline-block;
       
        padding-left: 7%;
        border-left: 3upx dashed #eee;
        height: 237upx;
    }

    .right view {padding: 6rpx 0;

      /*  color: #4b505d;
        font-size: 28upx; */
    }

    .head {
        width: 140upx;
        height: 140upx;
        border-radius: 70upx;
        display: block;
        margin: 0 auto;
    }

    .right text {
        margin-left: 40upx;
        color: #999999;font-size: $uni-font-size-sm;
    }

    .list {
        margin-top: 20upx;
        background-color: white;
    }

    .uni-list-item__container:after {
        left: 100upx !important;
    }

    .uni-list-item__container {
        padding: 30upx 30upx;
    }

    uni-list-item:last-child view:after {
        height: 0 !important;
    }

    .uni-collapse:before {
        height: 0 !important;
    }

    .content_view_item {
        clear: both;
        height: 80upx;
        padding-top: 10upx;
        padding-bottom: 10upx;
        border-bottom: 1upx solid #F2F2F2;
        margin-left: 30upx;
    }

    .content_view_item text:first-child {
        margin-left: 0;
    }

    .content_view_item text {
        display: block;
        margin-left: 22%;
        margin-top: 20upx;
        float: left;
        color: #999999;
    }

    .content_view_item .time_1 {
        margin-left: 0;
        float: right;
        margin-right: 23upx;
    }

    .content_view_item .time {
        float: right;
        margin-right: 23upx;
    }

    .uni-collapse:after {
       /* position: absolute;
        z-index: 10;
        right: 0;
        bottom: 0;
        left: 100upx !important;
        height: 1px;
        content: '';
        -webkit-transform: scaleY(.5);
        transform: scaleY(.5);
        background-color: #c8c7cc; */
    }

   /* .uni-collapse-cell:after {
        height: 0 !important;
    } */
	
	.money {
		float:right !important;
		margin-right:23rpx;		
	}
</style>
