<template>
    <view class="content">
        <view class="top">
            <image class="head" v-if="virtualPhoto==null||virtualPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>
            <image class="head" v-if="virtualPhoto!=null&&virtualPhoto!=''" :src="imagePath + virtualPhoto" mode="aspectFill"></image>
            <view class="name"> {{name}} </view>
            <view class="board">
                <view class="item line">
                    <view class="label">回馈金额</view>
                    <view class="num">{{null != myInfo.myDonMoney ? myInfo.myDonMoney :0}}</view>
                </view>
                <view class="item">
                    <view class="label">回馈次数</view>
                    <view class="num">{{null != myInfo.myDonNo ? myInfo.myDonNo :0}}次</view>
                </view>
            </view>
        </view>
        <view>
            <view class="tabs">
                <view :class="cur==0 ? 'tab on' : 'tab' " @click="onClickItem(0)">
                    回馈
                </view>
                <view :class="cur==1 ? 'tab on' : 'tab' " @click="onClickItem(1)">
                    共同献爱心
                </view>
            </view>
            <view class="list" id="list">
                <view v-if="cur==0" v-for="(value, key) in donProlist" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.donProject.pic!=null&&value.donProject.pic!=''" :src="imagePath +  value.donProject.pic"
                                mode="aspectFill"></image>
                            <image v-if="value.donProject.pic==null||value.donProject.pic==''" src="/static/img/don_default.png"
                                mode="aspectFill"></image>
                        </view>
                        <view class="sch_cont">
                            <view class="sch_cont_title">
                                {{value.donProject.name}}
                            </view>
                            <view class="date">
                                {{value.time}}
                            </view>
                            <view class="bm">
                                <view class="target">
                                    回馈 <text>{{value.money}}元</text>
                                </view>
                                <view v-if="value.state=='NORMAL'" class="status certi" @click.stop="goCertificate(value)">
                                    回馈证书
                                </view>
                                <view v-if="value.state=='WAITING'" class="status topay" @click.stop="goPayment(value)">
                                    支付
                                </view>
                                <view v-if="value.state=='HOLD'" class="status fail">
                                    回馈失败
                                </view>
                            </view>
                        </view>
                    </view>

                </view>
                <view v-if="cur==1" v-for="(value, key) in donMyTeam" :key="key" @click="goDetail(value)" >

                    <view class="activity_2">
                        <view class="sch_tu_2">
                            <view class="title_2">
                                {{null == value.donProjectTogether.name?"": value.donProjectTogether.name}}
                            </view>
                        </view>
                        <view class="sch_nr clearfix">
                            <view class="bm_2">回馈<text>{{value.money}}元</text></view>
                            <image v-if="trunPhoto!=null&&trunPhoto!=''" :src="imagePath +  trunPhoto" mode="aspectFill"></image>
                            <image v-if="trunPhoto==null||trunPhoto==''" src="/static/img/man_default.png" mode="aspectFill"></image>

                            <!-- 							<view v-if="value.donProject.status=='HASDONE' " class="end_2">
							    已结束
							</view>
							<view v-if="value.donProject.status=='WILL' " class="end_2">
							    未开始
							</view>
							<view v-if="value.donProject.status=='BEDOING'  && value.donProjectTogether.status=='SUCCESS'" class="doing_2">
							    进行中
							</view> -->
                        </view>
                    </view>
                </view>
            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>
    </view>
</template>
<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import '@/static/css/index.css';
    import request from '@/common/request.js';
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
                name: '',
                virtualPhoto: '',
                mode: 'nav',
                openid: '',
                myInfo: {},
                tab_type: 0,
                loadingText: '',
                donProlist: [],
                donMyTeam: [],
                imagePath: app.imagePath, //图片路径
                pageSize: 10,
                status: "more",
                moreflag: false, //捐赠是否有上拉更多
                pagenum: 0,
                moretemflag: false, //一起捐是否有上拉更多
                pagetemnum: 0,
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                trunPhoto: '',
                send_cur:0//该字段只是在模板消息使用，当值年返校审核失败时使用
            }
        },
        computed: {
            ...mapState(['hasLogin']),
            ...mapState(['userInfo'])
        },
        onReachBottom() {
            this.status = "loading";

            if (this.cur === 1 && this.moretemflag) {
                this.status = "loading";
                setTimeout(() => {
                    this.getTeamDonList();
                }, 2000, null);
            } else if (this.cur === 0 && this.moreflag) {
                this.status = "loading";
                setTimeout(() => {
                    this.getDonProList();
                }, 2000, null);
            } else {
                this.status = "noMore";
            }
        },
        onLoad(event) {
            if( (event.cur || event.payload)!=undefined){
                this.send_cur =event.cur || event.payload;
            }
            //小程序登录获取信息
            uni.getProvider({
                service: 'oauth',
                success: (res) => {
                    console.log(res.provider)
                    if (~res.provider.indexOf('weixin')) {
                        uni.login({
                            provider: 'weixin',
                            success: (loginRes) => {
                                console.log(JSON.stringify(loginRes));
                                this.code = loginRes.code;
                                console.log(this.code)
                                //获取用户openid
                                request.http({
                                    url: "/app/pay/getInfo",
                                    data: {
                                        'code': this.code
                                    },
                                    header: 'application/x-www-form-urlencoded',
                                    success: (res) => {
                                        this.openid = res.data.data.openId;
                                    },
                                    fail: () => {
                                        this.showpageToast("捐赠失败")
                                    }
                                })

                            }
                        });
                    }
                }
            });
        },
        onShow() {
            this.getMyInfo();
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                this.name = this.userInfo.sysUser.virtualName;
                this.virtualPhoto = this.userInfo.sysUser.virtualPhoto;
                this.trunPhoto = this.userInfo.truePhoto;
            }
            this.donProlist = [];
            this.donMyTeam = [];
            //if (this.donProlist.length == 0) {
            if(this.cur==0){
                this.pagenum = 0;
                this.getDonProList();
                //}
                //if (this.donMyTeam.length == 0) {
                if(this.send_cur==1){
                   this.onClickItem(1); 
                   this.send_cur=0;
                }
            }else{
                this.pagetemnum=0;
                this.getMyTeamList();
            }
            
            
            //}
        },
        methods: {
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                }
                if (this.cur === 0) {
                    if (this.donProlist.length == 0) {
                        this.getDonProList();
                    }
                } else {
                    if (this.donMyTeam.length == 0) {
                        this.getMyTeamList();
                    }
                }
            },
            getMyInfo() {
                request.http({
                    url: '/app/don/getProDon',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.myInfo = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getDonProList() {
                this.pagenum = this.pagenum + 1;
                request.http({
                    url: "/app/don/getmyDonRecordByPro",
                    data: {
                        pageNo: this.pagenum,
                        pageSize: this.pageSize,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            // this.donProlist = res.data.data;
                            var datas = res.data.list;
                            if (datas.length === 0) {
                                this.status = "noMore";
                                return;
                            }
                            datas.forEach((d) => {
                                var percent = 100;
                                if (d.gotMoney < d.targetMoney) {
                                    percent = Number(d.gotMoney / d.targetMoney * 100).toFixed(0)
                                }
                                d.donProject.name = util.strcharacterDiscode(d.donProject.name);
                                d["percent"] = percent;
                                this.donProlist.push(d);
                            });
                        }
                        this.moreflag = (res.data.pages > (this.pagenum) ? true : false);
                        if (!this.moreflag) {
                            this.status = "noMore";
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
            getMyTeamList() {
                this.pagetemnum = this.pagetemnum + 1;
                request.http({
                    // url: "/app/don/myteamDonRecords",
                    url: "/app/don/loadTeamByPage",
                    data: {
                        pageNo: this.pagetemnum,
                        pageSize: this.pageSize,
                        state:'NORMAL',
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            // this.donMyTeam = res.data.data;
                            var datas = res.data.list;
                            console.log(this.donMyTeam);
                            if (datas.length === 0) {
                                this.status = "noMore";
                                return;
                            }
                            datas.forEach((d) => {
                                this.donMyTeam.push(d);
                            });

                        }
                        this.moretemflag = (res.data.pages > (this.pagetemnum) ? true : false);
                        console.log("一起捐状态：" + this.moretemflag)
                        if (!this.moretemflag) {
                            this.status = "noMore";
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            goDetail(detail) {
                let url = '';
                if (this.cur == 0) {
                    if(detail.state=='HOLD'){
                        return false;
                    }
                    url = '/pages/don/donDetail?id=' + encodeURIComponent(detail.donProjectId);
                } else {
                    url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail.togetherId));
                }
                uni.navigateTo({
                    url: url
                });
            },
            goCertificate(detail) {
                let url = '/pages_mine/myDon/myCertificate?id=' + encodeURIComponent(detail.id);
                uni.navigateTo({
                    url: url
                });
            },
            goPayment(detail) {
               
                request.http({
                    url: "/app/weichatpay/againPay",
                    data: {
                        "orderId": detail.customId,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var payObj = res.data.data.obj;
                             console.log(payObj);
                              console.log(detail);
                            uni.requestPayment({
                                provider: 'wxpay',
                                timeStamp: payObj.timeStamp,
                                nonceStr: payObj.nonceStr,
                                package: payObj.package,
                                signType: payObj.signType,
                                paySign: payObj.paySign,
                                success: (res) => {
                                    //更新支付状态
                                    request.http({
                                        url: "/app/don/updateState",
                                        data: {
                                            "id": detail.id,
                                            "state": "NORMAL",
                                        },
                                        header: 'application/x-www-form-urlencoded',
                                        success: (res) => {
                                            if (res.data.code == app.RESPONSE_STATUS
                                                .REQUEST_SUCCESS) {
                                                console.log("修改状态成功");
                                            }
                                        }

                                    });
                                    //支付成功跳转到证书也
                                    this.showpageToast("捐赠成功");
                                    var url = '/pages/don/payResult?id=' + encodeURIComponent(
                                        detail.id);
                                    uni.navigateTo({
                                        url
                                    });
                                },
                                fail: function(err) {
                                    console.log('fail:' + JSON.stringify(err));
                                }
                            });
                        } else {
                            this.showpageToast("获取订单失败")
                        }
                    },
                    fail: () => {
                        this.showpageToast("获取订单失败")
                    }
                });
            },
            getDate(type) {
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
        }
    }
</script>

<style lang="scss">
    .uni-tab-bar-loading {
        text-align: center;
        padding: 20upx 0;
        font-size: 14px;
        color: #CCCCCC;
    }

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
        color: #262f43;
        font-weight: bold;
    }
.tabs{
	padding: 0 30upx;
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
}
    

    .on {
        background: #262f43;
        color: #fff;
    }

    .list {
        background: #eaeaea;
    }

    .content .sch_cont .target {
        // width: 126upx;
        font-size: 24upx;
        float: left;
        color: #999999;
    }

    .content .sch_cont .status {
        // width: 126upx;
        font-size: 24upx;
        float: right;
        width: 140upx;
        height: 40upx;
        line-height: 40upx;
        text-align: center;
        border-radius: 20upx;
    }

    .status.certi {
        border: solid 1px rgba(229, 229, 229, 1);
        color: #a4cf7f;
    }

    .status.topay {
        background: #f3c560;
        color: #fff;
    }

    .status.fail {
        border: solid 1px rgba(204, 204, 204, 1);
        color: #cccccc;
    }

    .fail_2,
    .auditing {
        font-size: 24upx;
        color: #999999;
        width: 140upx;
        height: 40upx;
        line-height: 40upx;
        text-align: center;
        border-radius: 20upx;
        border: solid 1px #999;
        float: right;
        position: relative;
        right: 15upx;
        top: 7upx;
    }

    .content .sch_cont .target text {
        color: red;
    }

    .bm {
        overflow: visible !important;
    }

    .content .activity {
        background: #fff;
        margin-bottom: 10upx;
    }

    .content .activity_2 {
        background: #fff;
        padding: 40upx 30upx;
        margin-bottom: 10upx;
    }

    .bm_2 text {
        color: #ed5849;
    }

    .content .sch_nr {
        margin-top: 52upx;
    }

    .content .sch_nr .bm_2 {
        width: 470upx;
        height: 73rpx;
        line-height: 73rpx;
        float: left;

    }

    .content .activity_2 .people_2 {
        float: left;
    }

    .content .activity_2 .date_2 {
        float: left;
        margin-top: 20upx;
    }

    .content .content_wb text {
        margin-top: -4upx;
    }

    .line:after {
        content: " ";
        position: absolute;
        width: 1upx;
        height: 55upx;
        top: 40upx;
        right: 0;
        background: #262f43;
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
	.content .sch_nr image {
	    width: 73upx;
	    height: 73upx;
	    border-radius: 50%;
	    float: right;
	}
</style>
