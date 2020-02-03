<template>
    <view class="content">
        <!-- 审核成功 -->
        <view>
            <view>
                <view>
                    <image class="card_bg_top" src="../../static/card-bg-top.png" mode="widthFix"></image>
                </view>
                <view style="position:relative;">
                    <image class="image_noc" v-if="cardPng!=null" :src="cardPng"></image>
                    <image class="image_noc" v-else src="/static/img/bgCard.png"></image>
                    <view class="star">
                        <uni-rate v-bind:value="complete" max="5" disabled="true"></uni-rate>
                        <!-- <image src="/static/star.png" mode=""></image> -->
                    </view>
                    <view style="position:absolute;top:120upx;left:80upx;">
                        <view class="name">{{name}}</view>
                        <view class="dept">{{depe}}</view>
                        <view class="grade">{{grade}}</view>
                    </view>
                </view>
            </view>
            <view class="con">
                <view>
                    <view class="example">
                        <uni-steps :options="list1" :active="active" />
                        <!-- <button type="primary" @click="change">改变状态</button> -->
                    </view>
                </view>
                <view class="text_con">
                    <!-- <text class="l_title">恭喜你将是第<text class="b_num">{{howMany}}</text>位成功领取校友卡的校友</text> -->
                    <text class="2_title">点击底部<text class="stro_font">查看我的校友卡</text>即可完成领卡操作呦！</text>   
                    <view class="title_h3">亲爱的校友，您好！</view>
                    <view v-if="content!=''">
                        <uParse :content="content" />
                    </view>
                    <view v-else="">
                        <view>校友卡是校友身份的象征，</view>
                        <view>拥有校友卡可以让您享受诸多的校友服务呦！</view>
                    </view>
                </view>
            </view>
             <form @submit="goCard" report-submit="true">
                <view class="act_footer">
                    <button class="act_right" formType="submit">查看我的校友卡</button>
                </view>
            </form>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import uniSteps from '@/components/uni-steps/uni-steps.vue';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义    
    import '@/static/css/index.css';
    import util from '@/common/util.js';
    import {
		mapState,
        mapMutations
    } from 'vuex';
    export default {
        components: {
            uniSteps,
            uParse
        },
        data() {
            return {
                name: "",
                grade: "",
                dept: "",
                cardState: "NO_AUDIT", //卡片状态
                active: 2,
                complete: 0,
                content: '',
                list1: [{
                    title: '申请认证'
                }, {
                    title: '认证中'
                }, {
                    title: '认证完成'
                }],
                howMany: 0,
                form_id:'',
                openid:'',
                code:''
            }
        },
        computed:{
            ...mapState(['hasLogin','userInfo'])
        },
        onShow() {
            let userInfo = this.userInfo;
            // this.getCollarCardNum();
            this.name = this.userInfo.name;
            this.complete = this.userInfo.complete;
            let smEducation = this.userInfo.smEducation;
            this.dept = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                smEducation.college);
            let startdata = (null != smEducation.startdate ? smEducation.startdate : "").split("-")[0];
            this.grade = (null != startdata && "" != startdata ? startdata + "级" : "");
            request.http({
                url: '/app/getAlumniCard',
                method: "POST",
                data: {
                    startLevel: 1,
                    endLevel: 1,
                },
                header: 'application/x-www-form-urlencoded',
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.cardPng = app.imagePath + res.data.data.frontBackground;
                    }
                },
                error: (res) => {

                }
            });
            uni.request({
                url: app.serverPath + '/app/wx/getContentByPage',
                method: 'POST',
                header: {
                    'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                    code: 'cardstatusSuccess',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.content = util.strcharacterDiscode(res.data.data);
                    }
                },
                fail: (res) => {

                }
            })
        },
        onLoad(options) {
            //获取校友卡的状态 审核中--1  审核成功--2
            // uni.getProvider({
            //     service: 'oauth',
            //     success: (res) => {
            //         console.log(res.provider)
            //         if (~res.provider.indexOf('weixin')) {
            //             uni.login({
            //                 provider: 'weixin',
            //                 success: (loginRes) => {
            //                     console.log(JSON.stringify(loginRes));
            //                     this.code = loginRes.code;
            //                     //获取用户openid
            //                     request.http({
            //                         url: "/app/pay/getInfo",
            //                         data: {
            //                             'code': this.code
            //                         },
            //                         header: 'application/x-www-form-urlencoded',
            //                         success: (res) => {
            //                             this.openid = res.data.data.openId;
            //                         },
            //                         fail: () => {
            //                         }
            //                     })
            // 
            //                 }
            //             });
            //         }
            //     }
            // });
            
        },
        methods: {
            ...mapMutations(['setUserInfo']),
            getCollarCardNum() {
                request.http({
                    url: "/app/index/getCollarCardNum",
                    header: 'application/x-www-form-urlencoded',
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.howMany = 0;
                            this.howMany = res.data.data + 1;
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            change() {
                if (this.active < this.list1.length - 1) {
                    this.active += 1
                } else {
                    this.active = 0
                }
            },
            bintIdentif() {
                uni.navigateTo({
                    // url: "identifyResult",
                    url: "invite"
                })
            },
            gotoindex() {
                uni.switchTab({
                    url: "/poges/tabbar/index/index"
                })
            },
            goCard(e) {
                this.form_id=e.detail.formId;
                request.http({
                    url: '/app/updateCardStatusWorkFlow',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //  this.actProList = this.actProList.concat(res.data.list);
                            this.setUserInfo(res.data.data);
                            var truePath =encodeURIComponent(JSON.stringify( '/pages_mine/myCard/myCard'));
                            uni.redirectTo({
                                url: '/pages_mine/myCard/myCard'
                            });
                        }
                    },
                    error: (res) => {

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
            },

        }
    }
</script>

<style>
    /* 审核认证中 */
    .rule {
        position: absolute;
        right: 35upx;
        top: 30upx;
        font-size: 24upx;
        color: #c59b5c;
    }

    .search_1 {
        width: 368upx;
        height: 368upx;
        margin: 0 auto;
        margin-top: 110upx;
    }

    .search_1 image {
        width: 100%;
        height: 100%;
    }

    .process {
        padding: 20upx 60upx 0 60upx;
    }

    .nr {
        padding: 0 30upx;
    }

    .nr .title {
        text-align: center;
        font-size: 30upx;
        color: #666666;
        font-weight: bold;
    }

    .nr .nr_list2 {
        font-size: 24upx;
        color: #666666;
        line-height: 1.5;
    }

    .nr .nr_list3 {
        text-align: center;
        font-size: 24upx;
        color: #00a3e0;
    }

    .btn_1 {
        width: 90%;
        position: absolute;
        bottom: 246upx;
        left: 5%;
        background-color: #272d3d !important;
        border-radius: 44px;
    }

    .btn_2 {
        bottom: 118upx;
        background-color: white !important;
        color: #272d3d !important;
        border-radius: 44upx;
        border: solid 1upx #3c4250;
    }

    /* 认证完成 */
    .image_noc {
        width: 90vw;
        height: 51.8vw;
        margin: 0 auto;
        margin-top: 50upx;
        display: block;
    }

    .card_bg_top {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .con {
        padding: 60upx;
    }

    .l_title {
        font-size: 36upx;
        font-weight: bold;
    }

    .2_title {
        font-size: 26upx;
        font-weight: bold;
    }

    .l_title:after {
        width: 100%;
    }

    .title_h2 {
        font-size: 30upx;
        color: #666;
        font-weight: bold;
        margin-top: 10upx;
    }

    .title_h3 {
        font-size: 24upx;
        color: #666;
    }

    .title_blue {
        font-size: 24upx;
        color: #00a3e0;
    }

    .primary {
        width: 90%;
        position: absolute;
        bottom: 60upx;
        left: 5%;
    }

    .act_footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 98upx;
    }

    .act_left {
        width: 50%;
        height: 98upx;
        float: left;
        text-align: center;
        color: #262F43;
        line-height: 98upx;
        background-color: white;
    }

    .act_right {
        width: 100%;
        height: 98upx;
        float: right;
        text-align: center;
        background-color: #262F43;
        color: white;
        line-height: 98upx;
    }

    .name {
        font-size: 52upx;
        color: #603a2b;
        line-height: 1.4;
    }

    .dept,
    .grade {
        font-size: 22upx;
        color: #603a2b;
    }

    .text_con view {
        font-size: 24upx !important;
        color: #666;
    }

    .title_h3 {
        color: #666666;
        font-size: 30upx !important;
        font-weight: bold;
    }

    .b_num {
        font-size: 46upx;
        font-weight: bold;
    }

    .stro_font {
        font-size: 32upx;
        font-weight: bold;
    }
    .uni-icon {
        width: 27upx;
        height: 27upx;
        background-color: #f9c558;
        border-radius: 50%;
        box-shadow: 0 0 1upx 10upx rgba(249, 197, 88, 0.4);
        margin-left: 4upx;
    }

    .uni-steps-items {
        margin: 0 !important;
    }

    .uni-steps-item-title {
        margin-left: -60upx;
        color: #000000;
        margin-top: 10upx;
    }

    .example {
        margin-top: -5upx;
        padding: 0 0 120upx 40upx;
    }

    .uni-steps-item:last-child .uni-steps-item-circle-container {
        left: -42upx !important;
    }

    .uni-steps-item:first-child .uni-steps-item-line {
        left: 14upx !important;
    }

    .uni-steps-item:first-child .uni-steps-item-circle-container {
        left: -13px !important;
    }

    .uni-steps-item-circle-container {
        left: -18px !important;
    }

    .act_footer button {
        width: 100% !important;
        color: #fff !important;
    }
</style>
