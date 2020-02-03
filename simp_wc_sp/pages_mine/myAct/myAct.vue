<template>
    <view class="content">
        <view class="top">
            <image class="head" v-if="virtualPhoto==null||virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
            <image class="head" v-if="virtualPhoto!=null&&virtualPhoto!=''" :src="imagePath + virtualPhoto" mode="aspectFill"></image>
            <view class="name"> {{name}} </view>
            <view class="board">
                <view class="item line">
                    <view class="label">报名活动</view>
                    <view class="num">{{myActNo}}</view>
                </view>
                <view class="item">
                    <view class="label">报名值年返校</view>
                    <view class="num">{{myBackSchoolNo}}</view>
                </view>
            </view>
        </view>
        <view>
            <view class="tabs">
                <view :class="cur==0 ? 'tab on' : 'tab' " @click="onClickItem(0)">
                    活动
                </view>
                <view :class="cur==1 ? 'tab on' : 'tab' " @click="onClickItem(1)">
                    值年返校
                </view>
            </view>

            <view class="list" id="list">
                <view v-show="cur === 0" v-for="(value, key) in actProList" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.image!=null&&value.image!=''" :src="imagePath + value.image" mode="aspectFill"></image>
                            <image v-if="value.image==null||value.image==''" src="/static/img/act/act_default.png" mode="aspectFill"></image>
                            <view v-if="value.status=='DONE'" class="end">
                                已结束
                            </view>
                            <view v-if="value.status=='DOING'" class="doing">
                                进行中
                            </view>
                            <view v-if="value.status=='READAY'" class="readay">
                                预备中
                            </view>
                        </view>
                        <view class="sch_cont">
                            <view class="title">
                                {{ value.name }}
                            </view>
                            <view class="date">
                                {{value.startDate}}至{{value.endDate}}
                            </view>
                            <view class="bm">
                                <view class="xy">
                                    · {{value.assoName}}
                                </view>
                                <view class="people">
                                    <text>{{null!=value.readyNo?value.readyNo:0}}</text>人已报名
                                </view>
                            </view>
                        </view>
                    </view>
                    <view class="activity_h"></view>
                </view>
                <view v-show="cur === 1" v-for="(value, key) in actBackSchoolList" :key="key" @click="goDetail(value)">
                    <view class="activity_2">
                        <view class="sch_tu_2 clearfix">
							<view v-if="value.status=='SUCCESS'&&todayDate>value.endDate" class="end_2">
							    已结束
							</view>
							<view v-if="value.status=='SUCCESS'&&value.createDate<= todayDate && todayDate<=value.endDate" class="doing_2">
							    进行中
							</view>
							<view v-if="value.status=='SUCCESS'&&todayDate<value.createDate" class="readay_2">
							    预备中
							</view>
							<view v-if="value.status=='AUDITING'" class="status certi" >
							    审核中
							</view>
							<view v-if="value.status=='LOSE'" class="status certi" >
							    发起失败
							</view>
                            <view class="title_2">
                                {{ value.name }}
                            </view>                            
                        </view>
                        <view class="sch_nr clearfix">
                            <view class="bm_2">
                                <view class="people_2">
                                    <text>{{null!=value.readyNo?value.readyNo:0}}</text>人已报名
                                </view>
                                <view class="date_2">
                                    报名时间：{{value.createDate}}至{{value.endDate}}
                                </view>
                                
                            </view>
                            <!--   <image :src="imagePath + value.truePhoto" mode="aspectFill"></image> -->
                            <image v-if="value.virtualPhoto==null||value.virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.virtualPhoto!=null&&value.virtualPhoto!=''" :src="imagePath + value.virtualPhoto" mode="aspectFill"></image>

                        </view>
                    </view>
                    <view class="activity_h"></view>
                </view>
                <uni-load-more :status="status" :contentText="contentText" color="#999999" />
            </view>
        </view>
    </view>
</template>



<script>
    function getDate(type) {
        const date = new Date();

        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();

        if (type === 'start') {
            year = year - 60;
        } else if (type === 'end') {
            year = year + 2;
        }
        month = month > 9 ? month : '0' + month;;
        day = day > 9 ? day : '0' + day;

        return `${year}-${month}-${day}`;
    }
    import app from '@/common/app.js'; //公共变量
    import '@/static/css/index.css';
    import request from '@/common/request.js';
    import util from '@/common/util.js'; //公共变量
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import {
        mapState
    } from 'vuex';

    export default {
        components: {
            uniLoadMore
        },
        data() {
            return {
                cur: 0,
                mode: 'nav',
                tab_type: 0,
                loadingText: '',
                actProList: [],
                actBackSchoolList: [],
                name: '',
                virtualPhoto: '',
                myActNo: 0,
                myBackSchoolNo: 0,
                status: "more",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                imagePath: app.imagePath,
                actPageNo: 0,
                backSchoolPageNo: 0,
                pageSize: 10,
                actflag: false,
                backSchoolflag: false,
                todayDate: getDate(),
                isLogin: false,
                send_cur:0//该字段只是在模板消息使用，当值年返校审核失败时使用
            }
        },
        computed: {
            ...mapState(['hasLogin']),
            ...mapState(['userInfo'])
        },
        onReachBottom() {
            this.status = "noMore";
            //我报名的活动
            if (this.cur == 0 && this.actflag) {
                this.status = "loading";
                this.actPageNo = this.actPageNo + 1;
                setTimeout(() => {
                    request.http({
                        url: '/app/act/getPartakeActList',
                        header: 'application/x-www-form-urlencoded',
                        method: "POST",
                        data: {
                            pageNo: this.actPageNo,
                            pageSize: this.pageSize
                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                //  this.actProList = this.actProList.concat(res.data.list);
                                if (res.data.list == undefined || res.data.list.length == 0) {
                                    this.status = "noMore";
                                    setTimeout(() => {
                                        this.showLoadMore = false;
                                    }, 500);
                                } else {
                                    var datas = res.data.list;
                                    datas.forEach((d) => {
                                        this.actProList.push(d);
                                    });
                                    this.actflag = this.actPageNo >= res.data.pages ? false : true;
                                }
                                if (res.data.list.length < this.pageSize) {
                                    this.status = "noMore";
                                }
                            }

                        },
                        error: (res) => {

                        }
                    }), 1000
                })

            } else if (this.cur == 1 && this.backSchoolflag) {
                this.status = "loading";
                this.backSchoolPageNo = this.backSchoolPageNo + 1;
                setTimeout(() => {
                    request.http({
                        url: '/app/actSelf/getMyActSelfList',
                        header: 'application/x-www-form-urlencoded',
                        method: "POST",
                        data: {
                            pageNo: this.backSchoolPageNo,
                            pageSize: this.pageSize
                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                //  this.actProList = this.actProList.concat(res.data.list);
                                if (res.data.list == undefined || res.data.list.length == 0) {
                                    this.status = "noMore";
                                    setTimeout(() => {
                                        this.showLoadMore = false;
                                    }, 500);
                                } else {
                                    var datas = res.data.list;
                                    datas.forEach((d) => {
                                        this.actBackSchoolList.push(d);
                                    });
                                    this.backSchoolflag = this.backSchoolPageNo >= res.data.pages ?
                                        false : true;
                                }
                                if (res.data.list.length < this.pageSize) {
                                    this.status = "noMore";
                                }

                            }
                        },
                        error: (res) => {

                        }
                    }), 1000
                })
            }
        },
        onLoad(event) {
            console.log(event);
            if( (event.cur || event.payload)!=undefined){
                this.send_cur =event.cur || event.payload;
            }
        },
        onShow() {
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                this.name = this.userInfo.sysUser.virtualName;
                this.virtualPhoto = this.userInfo.sysUser.virtualPhoto
            }
            if (this.cur == 0) {
                this.actProList=[];
                this.actPageNo=0;
                this.getActProList();
                if(this.send_cur==1){
                   this.onClickItem(1); 
                   this.send_cur=0;
                }
            }else{
                this.actBackSchoolList=[];
                this.backSchoolPageNo=0;
                this.getBackSchoolList();
            }
            this.getBackSchoolNum();
        },
        methods: {
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur == 0) {
                    if (this.actProList.length == 0) {
                        this.actPageNo=0;
                        this.getActProList();
                    }
                } else {
                    this.actBackSchoolList=[];
                    if (this.actBackSchoolList.length == 0) {
                        this.backSchoolPageNo=0;
                        this.getBackSchoolList();
                    }

                }

            },
            getActProList() {
                this.actPageNo = this.actPageNo + 1;
                this.status = "loading";
                request.http({
                    url: '/app/act/getPartakeActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.list == undefined||res.data.list.length == 0) {
                                this.status = "noMore";
                                return;
                            }
                            this.myActNo = res.data.total;
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actProList = datas;
                            this.actflag = this.actPageNo >= res.data.pages ? false : true;
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getBackSchoolList() {
                this.backSchoolPageNo = this.backSchoolPageNo + 1;
                this.status = "loading";
                request.http({
                    url: '/app/actSelf/getMyActSelfList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.backSchoolPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log(res.data.count)
                            ;
                            if (res.data.list == undefined||res.data.list.length==0) {
                                this.status = "noMore";
                                return;
                            }
                            // this.myBackSchoolNo = res.data.total;
                            this.actBackSchoolList = res.data.list;
                            console.log(this.actBackSchoolList)
                            this.backSchoolflag = this.backSchoolPageNo >= res.data.pages ? false : true;
                            console.log(this.backSchoolflag);
                            if (res.data.list.length < this.pageSize) {
                                this.status = "noMore";
                            }
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getBackSchoolNum() {
                request.http({
                    url: '/app/actSelf/getMyActSelfNum',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.backSchoolPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.myBackSchoolNo = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            goDetail(e) {
                let url = '';
                if (this.cur == 0) {
                    url = '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                } else {
                    url = '/pages/act/backSchoolDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                }
                uni.navigateTo({
                    url: url
                });
            },

        }
    }
</script>
<style lang="scss">
    .head {
        width: 170upx;
        height: 170upx;
        border-radius: 85upx;
        display: block;
        margin: 0 auto;
        margin-top: 40upx;
    }

    .name {
        text-align: center;
        color: #4b505d;
        font-size: 30upx;
        margin-top: 15upx;
    }

    .board {
        width: 80%;
        height: 136upx;
        background: #f3c560;
        border-radius: 10upx;
        margin: 0 auto;
        margin-top: 30upx;
    }

    .item {
        width: 49%;
        height: 130upx;
        display: inline-block;
        position: relative;
    }

    .label {
        text-align: center;
        margin-top: 20upx;
        color: #fff;
        font-size: 24upx;
    }

    .num {
        text-align: center;
        font-size: 36upx;
       
    }
	.tabs {
		padding: 0 30rpx;
	}

    .tab {
        display: inline-block;
        width: 50%;
        border: solid 1upx #262f43;
        text-align: center;
        height: 74upx;
        line-height: 74upx;
        margin-top: 40upx;
        box-sizing: border-box;
    }

    .on {
        background: #262f43;
        color: #fff;
    }

    .list {
        // background: #eaeaea;
    }

    .content .sch_cont .target {
        // width: 126upx;
        font-size: 24upx;
        float: left;
        color: #999999;
    }

    .content .sch_cont .got {
        // width: 126upx;
        font-size: 24upx;
        float: right;
        color: #999999;
    }

    .content .sch_cont .got text {
        color: red;
    }

    .content .sch_cont .target text {
        color: red;
    }

    .content .activity {
        background: #fff;
        // margin-bottom: 10upx;
    }

    .activity_2 {
        padding: 40upx 30upx;
    	.title_2{float: left;margin-left:20upx;width:calc(100% - 140upx);}
    	.doing_2 {
    	    padding: 0 4upx;
    	    height: 40upx;
    	    background-color: rgba(55, 151, 84, 0.5);
    	    border-radius: 5upx;
    	    color: white;
    	    line-height: 40upx;
    	    text-align: center;
    	    float: left;
    	}
    	.sch_nr {
    	    margin-top: 48upx;
    		image{width: 73upx;height: 73upx;border-radius: 50%;}
    		 .bm_2{width: calc(100% - 73upx);float: left;}
    	}
    	.readay_2 {
    	    width: 93upx;
    	    height: 40upx;
    	    background-color: rgba(158, 117, 33, 0.5);
    	    border-radius: 5upx;
    	    color: white;
    	    line-height: 40upx;
    	    text-align: center;
    	    float: right;
    	}
    	
    }

 

    .content .sch_cont .people {
        font-size: 24upx;
        color: #999999;
    }

    .content .people text {
        color: #ed5849;
    }

    .content .content_wb text {
   
    }

    .line:after {
        content: " ";
        position: absolute;
        width: 1upx;
        height: 55upx;
        top: 40upx;
        right: 0;
        background: #333;
    }

    .content .activity .sch_tu .doing {
        width: 230upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity .sch_tu .readay {
        width: 230upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .content_wb text {
        margin-top: -4upx;
    }

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .sch_cont .bm {
        width: 432upx;
    }

    .content .activity .sch_cont {
        width: 432upx;
        margin-top: 0;
    }

    .content .sch_cont .date {
        margin-top: 0upx;
    }

    .content .activity .sch_tu .end {
        width: 230upx;
        height: 40upx;
        background-color: rgba(0, 0, 0, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 0upx;
        bottom: 0upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .status {
        // width: 126upx;
        font-size: 24upx;
       
        text-align: center;
        
    }

    .status.certi {width: 93rpx;float: left;color:#fff;border-radius: 5rpx;height: 40upx;line-height: 40upx;background: #cccccc;}
</style>
