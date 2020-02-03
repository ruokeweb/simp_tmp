<template>
    <view class="content">
        <!-- 审核认证中 -->
        <view>
            <view class="rule">
                <navigator url="authrule">认证规则</navigator>
            </view>
            <view class="search_1">
                <image class="head-pic"  src="/static/login/check.png" 
                    mode="aspectFill"></image>
<!--                <image class="head-pic" v-if="truePhoto!=null" :src="imagePath + truePhoto" @click="uploadtruePhoto"
                    mode="aspectFill"></image>
                <image class="ph" src='/static/head_cam.png' @click="uploadtruePhoto" mode="aspectFill"></image>
                <p class="tip">上传真人头像更有利于认证哦！</p> -->
            </view>
            <view class="process">
                <view class="example">
                    <uni-steps :options="list1" :active="active" />
                    <!-- <button type="primary" @click="change">改变状态</button> -->
                </view>
            </view>
            <view class="nr">
                <view class="title">
                    亲爱的校友，您好！
                </view>
                <view class="nr_list2">
                    您于{{startDate}}已申领校友卡，校友卡将于{{endDate}}前审核完成，请您耐心等待，您也可以选择以下方式完成认证。
                </view>
                <view class="nr_list3">
                    <view v-if="content!=''">
                        <uParse :content="content" />
                    </view>
                    <view v-else="">

                    </view>
                </view>
            </view>
            <view>
                <button type="primary" class="btn_1" @click="bintIdentif" :disabled="flagHelp">邀请校友帮我认证</button>
                <button type="primary" class="btn_1 btn_2" @click="gotoindex">返回首页</button>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import uniSteps from '@/components/uni-steps/uni-steps.vue'
    import request from '@/common/request.js'
    import '@/static/css/index.css';
    import util from '@/common/util.js';
    import uParse from '@/components/uParse/src/wxParse.vue' //富文本框转义
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
                content: '暂无联系方式',
                cardState: "", //卡片状态
                active: 1,
                truePhoto: "",
                imagePath: app.imagePath,
                startDate: '',
                endDate: '',
                flagHelp: true,
                list1: [{
                    title: '申请认证'
                }, {
                    title: '认证中'
                }, {
                    title: '认证完成'
                }]
            }
        },
        computed: {
            ...mapState(['userInfo'])
        },
        onShow() {
            this.truePhoto = this.userInfo.truePhoto;
            this.startDate = util.dateUtils.format_date(this.userInfo.createDate.split(" ")[0]);
            this.endDate = util.dateUtils.getDateAfter_n(this.userInfo.createDate.split(" ")[0], 3);
            if (app.is_face) {
                this.checkIdentiFace();
            } else {
                this.flagHelp = false;
            }
            uni.request({
                url: app.serverPath + '/app/wx/getContentByPage',
                method: 'POST',
                header: {
                    'content-type': 'application/x-www-form-urlencoded'
                },
                data: {
                    code: 'cardstatusAuthing',
                },
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        this.content = res.data.data;
                    }
                },
                fail: (res) => {

                }
            })
        },
        onLoad(options) {
            //获取校友卡的状态 审核中--1  审核成功--2
            if (options.state == "NORMAL") {
                this.getschoolmate()
            }
        },
        methods: {
            ...mapMutations(['setUserInfo']),
            uploadtruePhoto() {
                uni.showActionSheet({
                    itemList: ['拍照', '从相册选择'],
                    success: (res) => {
                        var index = res.tapIndex + 1;
                        uni.chooseImage({
                            count: 1,
                            sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
                            sourceType: index == 1 ? ['camera '] : ['album'],
                            success: (res) => {
                                var keyTime = new Date().getTime().toString();
                                const tempFilePaths = res.tempFilePaths;
                                uni.uploadFile({
                                    url: app.serverPath + '/app/uploadTruePhoto', //仅为示例，非真实的接口地址
                                    filePath: tempFilePaths[0],
                                    name: 'file',
                                    header: {
                                        'content-type': 'multipart/form-data',
                                        'Authorization': wx.getStorageSync("token"),
                                        'Time': keyTime,
                                        'Key': util.Encrypt(app.AES_KEY, keyTime)
                                    },
                                    success: (success) => {
                                        let res = JSON.parse(success.data);
                                        if (res.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                            this.setUserInfo(res.data);
                                            this.truePhoto = res.data.truePhoto;
                                            // if (app.is_face) {
                                            //     this.checkIdentiFace();
                                            // } else {
                                            //     this.flagHelp = false;
                                            // }
                                            this.flagHelp = false;
                                        } else {
                                            uni.showToast({
                                                icon: 'none',
                                                title: res.msg
                                            });
                                        }

                                    },
                                    fail: (error) => {
                                        console.log(error);
                                    }
                                });
                            }
                        });
                    },
                    fail: (res) => {
                        console.log(res.errMsg);
                    }
                });

            },
            change() {
                if (this.active < this.list1.length - 1) {
                    this.active += 1
                } else {
                    this.active = 0
                }
            },
            bintIdentif() {
                // if (this.truePhoto == null || this.truePhoto == "") {
                //     uni.showToast({
                //         icon: 'none',
                //         title: "请上传个人真实头像"
                //     });
                //     return false;
                // } else {
                    //util.getStatPoint('click','EVENT_DESCRIBE_9');
                    uni.navigateTo({
                        url: "invite"
                    })
                // }
            },
            gotoindex() {
                uni.switchTab({
                    url: "../tabbar/index/index"
                })
            },
            checkIdentiFace() {
                uni.showLoading({
                    title: '正在校验...',
                });
                util.getBaiduToken(app.baidu_apiKey, app.baidu_secKey).then((res) => {
                    console.log(res);
                    let token = res.data.access_token;
                    console.log(token);
                    let data = {
                        "image": app.imagePath + this.truePhoto,
                        "image_type": "URL",
                        "face_field": "ge,beauty,expression,face_shape,gender,glasses,landmark,race,quality,eye_status,emotion,face_type"
                    }
                    this.getImgIdentify(token,data);
                });
                 uni.hideLoading();
            },
            getImgIdentify(token,data) {
                var that = this;
                util.getImgIdentify(token, data).then((res) => {
                    if (res.data.error_code === 0 && res.data.result.face_list.length > 0) {
                        that.flagHelp = false;
                        uni.showToast({
                            icon: 'none',
                            title: "人脸验证已通过"
                        });
                    } else if (res.data.erro_code == 18) {
                        uni.showToast({
                            icon: 'none',
                            title: res.data.error_msg
                        });
                        this.getImgIdentify(token,data);
                    } else{
                        that.flagHelp = true;
                        uni.showToast({
                            icon: 'none',
                            title: "请上传个人真实头像"
                        });
                    }
                })
            }
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
        height: 440upx;
        margin: 0 auto;
        margin-top: 90upx;
        position: relative;
    }

    .search_1 .head-pic {
        width: 100%;
        height: 80%;
        border-radius: 177upx;
    }

    .ph {
        width: 280upx;
        height: 60upx;
        position: absolute;
        right: 40upx;
        bottom: 92upx;
        z-index: 100;
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
        width: 50%;
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

    .tip {
        color: #BD1E2C;
        font-size: 26upx;
    }

    .head {
        width: 140upx;
        height: 140upx;
        border-radius: 70upx;
        display: block;
        margin: 0 auto;
    }
</style>
