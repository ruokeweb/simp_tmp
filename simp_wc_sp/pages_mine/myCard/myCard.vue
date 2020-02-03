<template>
    <view>
        <view>
            <view>
                <image class="card_bg_top" src="../../static/card-bg-top.png" mode="widthFix"></image>
            </view>
            <view style="position:relative;">
                <uni-swiper-dot :info="cardInfo" :current="current" :mode="mode" :dots-styles="dotsStyles" field="content">
                    <swiper class="swiper-box" @change="change" @animationfinish="animationfinish" style="display: block; height: 499rpx;"
                        :circular=true>
                        <swiper-item v-for="(item ,index) in cardInfo" :key="index">
                            <view class="swiper-item">
                                <!-- <image :src="item.url" mode="aspectFill" /> -->
                                <image class="image_noc" v-if="item.settingCard.frontBackground!=null" :src="serverPath+item.settingCard.frontBackground"></image>
                                <image class="image_noc" v-else src="/static/img/bgCard.png"></image>


                                <view class="star">
                                    <view class="starText">您是第{{item.ranking}}位领取校友卡的校友</view>
                                    <uni-rate v-bind:value="complete" max="5" disabled="true"></uni-rate>
                                    <!-- <image src="/static/star.png" mode=""></image> -->
                                </view>
                                <view class="card" style="" v-if="isTeacher ==false">
                                    <view class="name">{{name}}</view>
                                    <view class="dept">{{college}}</view>
                                    <view class="grade">{{startYear}}</view>
                                </view>
                                <view class="card" style="" v-if="isTeacher ==true">
                                    <view class="name">{{name}}</view>
                                    <view class="dept">{{workplace}}</view>
                                </view>
                            </view>
                        </swiper-item>
                    </swiper>
                </uni-swiper-dot>


            </view>
        </view>

        <view class="b-title">
            我的排名
        </view>
        <view class="links">
            <view class="xyk xyk_1" @click="cardRank">
                <view class="tu_1">
                    <image src="/static/img/mine/icon-kp.png" mode=""></image>
                </view>
                <navigator url="">领卡排名</navigator>
            </view>
            <view class="xyk" @click="proveRank">
                <view class="tu_1">
                    <image src="/static/img/mine/icon-zj.png" mode=""></image>
                </view>
                <navigator url="">认证排名</navigator>
            </view>
            <view class="xyk" @click="donRank">
                <view class="tu_1">
                    <image src="/static/img/mine/icon-ax.png" mode=""></image>
                </view>
                <navigator url="">捐赠排名</navigator>
            </view>
            <view class="xyk" @click="shareRank">
                <view class="tu_1">
                    <image src="/static/img/mine/icon-fx.png" mode=""></image>
                </view>
                <navigator url="">分享排名</navigator>
            </view>
        </view>

        <view class="b-title">
            我的祝福
        </view>
        <!-- <view class="send-wish"> -->
        <view class="uni-flex uni-row">
            <!-- <view class="text" style="width: 200upx;">固定宽度</view> -->
            <input type="text" placeholder="写下你的祝福" class="input-wish" maxlength="13" v-model.trim="content" data-key="content">
            <!-- <view class="text" style="-webkit-flex: 1;flex: 1;">自动占满余量</view> -->
            <view class="button-wish" @click="saveinfo">发布</view>
        </view>
        <!--            <input type="text" placeholder="写下你的祝福" class="input-wish" maxlength="13" v-model.trim="content" data-key="content">
            <view class="button-wish" @click="saveinfo">发布</view> -->
        <!-- </view> -->
        <!-- 祝福语列表 -->
        <view class="list" v-if="showList">
            <view v-for="(value, key) in congratulations" :key="key" @click="goDetail(value)" class="list-item">
                {{value.information}}
            </view>
            <uni-load-more :status="status" :contentText="contentText" color="#999999" />
        </view>

        <view class="invite_btn">
            <button type="primary" class="button" @click="togglePopup('middle-con')">邀请校友</button>
        </view>
        <!-- 弹出页面 -->
        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="togglePopup('')">
            <view class="" style="width:100%;height:100%;z-index:1000;">
                <view class="tip">
                    <text>我是第{{cardInfo[current].ranking}}位成功领取校友卡的校友</text>
                </view>
                <view class="wait">
                    你还在等什么！
                </view>
                <view>
                    <image class="card_bg" src="/static/login/ident-bg.png" style="" mode="widthFix"></image>
                    <image class="card_pop" v-if="cardInfo[current].settingCard.frontBackground!=null" :src="serverPath+cardInfo[current].settingCard.frontBackground"></image>
                    <image class="card_pop" v-else src="/static/img/bgCard.png"></image>
                    <view class="star">
                        <uni-rate v-bind:value="complete" max="5" disabled="true"></uni-rate>
                    </view>
                    <view class="card" style="" v-if="isTeacher ==true">
                        <view class="name">{{name}}</view>
                        <view class="dept">{{workplace}}</view>
                    </view>
                    <view class="card" style="" v-if="isTeacher ==false">
                        <view class="name">{{name}}</view>
                        <view class="dept">{{college}}</view>
                        <view class="grade">{{startYear}}</view>
                    </view>
                </view>
                <view class="code">
                    <view class="hint">
                        <image v-bind:src="codeUrl" mode=""></image>
                        <view class="sub">
                            <text class="rescode">长按识别小程序码</text>
                            <view class="create">
                                校友卡由{{schoolName}}校友小程序生成
                            </view>
                        </view>
                    </view>
                </view>
                <button type="primary" class="btn_dark inner primary" style="float: left; margin-left: 40upx;" @click="saveImg()">保存图片</button>
                <button type="primary" class="btn_dark inner primary" style="float: right; margin-right: 40upx;"
                    open-type="share">分享</button>
                <view style="position:fixed;top:9999999999999rpx;" v-for="(item ,index) in cardInfo" :key="index">
                    <canvas :canvas-id="item.id" style="width:390px;height:640px; background-color: #FFFFFF;">
                    </canvas>
                </view>

            </view>
        </uni-popup>
    </view>
</template>

<script>
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue";
    import uniPopup from '@/components/uni-popup/uni-popup.vue';
    import request from '@/common/request.js';
    import app from '@/common/app.js'; //公共变量
    import loadcache from '@/common/loadcache.js';
    import uniRate from "@/components/uni-rate/uni-rate.vue";
    import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue';
    import expand from '@/common/expand.js';
    import {
        mapState
    } from 'vuex';

    export default {
        components: {
            uniLoadMore,
            uniPopup,
            uniRate
        },

        data() {
            return {
                type: '',
                status: "nomore",
                contentText: {
                    contentdown: '加载更多',
                    contentrefresh: '加载中',
                    contentnomore: '没有更多'
                },
                windowWidth: '',
                windowHeight: '',
                congratulations: [],
                name: "",
                startYear: "",
                college: "",
                content: "",
                pageNo: 0,
                pageSize: 10,
                complete: 0,
                cardPng: "",
                isMore: false,
                howMany: "",
                totalSm: 0,
                codeUrl: "/static/logo.png",
                showList: false,
                saveFlag: false,
                smType: '',
                schoolName: "",
                workplace: '',
                isTeacher: '',
                alumni_teacher: app.alumni_type.teacher,
                cardInfo: [],
                serverPath: app.imagePath,
                current: 0
            }
        },
        computed: {
            ...mapState(['userInfo','configParams'])
        },
        onLoad() {},
        onShow() {
            this.schoolName = this.configParams.school_name; 
            uni.showLoading({title: '加载中'});
            this.pageNo = 0;
            this.congratulations = [];
            this.getWish();
            this.getCollarCardNum();
            //this.getCode();
            this.smType = this.userInfo.type;
            this.isTeacher = this.userInfo.type == app.alumni_type.teacher ? true : false;
            this.name = this.userInfo.name;
            this.complete = this.userInfo.complete;
            if (this.smType != this.alumni_teacher) {
                var smEducation = this.userInfo.smEducation;
                if (smEducation != null) {
                    this.college = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                        smEducation.college).name;
                    var startdata = (null != smEducation.startdate ? smEducation.startdate : "").split("-")[0];
                    this.startYear = (null != startdata && "" != startdata ? startdata + "级" : "");
                }
            } else {
                this.workplace = this.userInfo.smProfession.workplace;
            }
            //获取屏幕  
            uni.getSystemInfo({
                success: (res) => {
                    this.windowWidth = res.windowWidth;
                    this.windowHeight = res.windowHeight;
                }
            });
            request.http({
                url: '/app/getAlumniCards',
                method: "POST",
                data: {
                    startLevel: 1,
                    endLevel: 1,
                },
                header: 'application/x-www-form-urlencoded',
                success: (res) => {
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        // this.cardPng = app.imagePath + res.data.data.frontBackground;
                        this.cardInfo = res.data.data;
                    }
                    uni.hideLoading();
                },
                error: (res) => {
                    uni.hideLoading();
                }
            });
            uni.hideLoading();
        },
        methods: {
            change(e) {
                this.current = e.detail.current
            },
            animationfinish(e) {
                console.log(e.detail.current);
            },
            // notNotes() {
            // 	uni.showToast({
            // 		icon: 'none',
            // 		title: "功能暂未开通"
            // 	});
            // },
            cardRank() {
                uni.navigateTo({
                    url: "/pages_mine/myCard/cardRank?cardId=" + this.cardInfo[this.current].cardId
                })
            },
            proveRank() {
                uni.navigateTo({
                    url: "/pages_mine/myCard/proveRank"
                })
            },
            shareRank() {
                uni.navigateTo({
                    url: "/pages_mine/myCard/shareRank"
                })
            },
            donRank() {
                uni.navigateTo({
                    url: "/pages_mine/myCard/donRank"
                })
            },

            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            getCollarCardNum(e) {
                request.http({
                    url: "/app/index/getCollarCardNum",
                    header: 'application/x-www-form-urlencoded',
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            if (res.data.data == "" || res.data.data == null || res.data.data == "null") {
                                this.howMany = 1;
                            } else {
                                this.howMany = res.data.data + 1;
                                this.totalSm = res.data.data;
                            }
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            saveinfo() {
                if (this.saveFlag) {
                    return false;
                }
                this.saveFlag = true;
                if (this.content.length == 0) {
                    this.showpageToast("祝福语不能为空");
                    this.saveFlag = false;
                    return false;
                } else {
                    request.http({
                        url: "/app/wish/save",
                        data: {
                            information: this.content,
                            name: this.userInfo.name,
                            pageNo: this.pageNo,
                            pageSize: this.pageSize
                        },
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                this.showpageToast("发送祝福成功");
                                this.content = "";
                                this.congratulations = res.data.data.list;
                                /* datas.forEach((d) => {
                                     this.congratulations.push(d);
                                 }); */
                                if (res.data.data.list.length <= this.pageSize) {
                                    this.status = "nomore";
                                } else {
                                    this.status = "more";
                                }
                                this.isMore = (res.data.pages > (this.pageNo) ? true : false);
                                this.pageNo = 0;
                                this.congratulations = [];
                                this.getWish();
                            }
                            this.saveFlag = false;
                        },
                        fail: (data, code) => {
                            console.log(data);
                            this.saveFlag = false;
                        }
                    })
                }
            },
            togglePopup(type) {
                this.type = type
            },

            //获取二维码
            getCode() {
                request.http({
                    url: '/app/wx/getWCRecode',
                    method: "POST",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        'scene': "scene",
                        'page': "pages/tabbar/index/index",
                        'width': 300
                    },
                    responseType: 'arraybuffer',
                    success: (res) => {
                        // this.codeUrl = res.data;
                        this.codeUrl = 'data:image/jpeg;base64,' + uni.arrayBufferToBase64(res.data)
                        // this.codeUrl = 'data:image/jpg;base64,' + imgBase64;
                    },
                    error: (res) => {
                        console.log(res);
                    }
                });
            },
            //获取真机地址
            //创建画布
            saveImg() {
                // x 圆角矩形选区的左上角 x坐标
                // y 圆角矩形选区的左上角 y坐标
                // w 圆角矩形选区的宽度
                // h 圆角矩形选区的高度
                // r 圆角的半径 
                let x = 120
                let y = 70
                let w = 150
                let h = 30
                let r = 14
                uni.showLoading({
                    title: '图片绘制中...',
                })
                let canvasId = this.cardInfo[this.current].id;
                // const cav =  uni.createCanvasContext('sharecard');
                let cav = uni.createCanvasContext(canvasId);
                //上部背景
                cav.drawImage('/static/login/ident-bg.png', 0, 0, 390, 320);
                //下部底背景图
                cav.setFillStyle('white');
                cav.fillRect(0, 320, 390, 390);
                //校友卡
                // cav.drawImage('/static/img/bgCard.png', 10, 178, 370, 230);
                const cardSrc = this.serverPath + this.cardInfo[this.current].settingCard.frontBackground;
                console.log(cardSrc);
                uni.getImageInfo({
                    src: cardSrc,
                    success: (res) => {
                        console.log(res.path);
                        // ctx.drawImage(res.path, 0, 0, 375, uni.upx2px(1020));
                        cav.clearRect(10, 178, 370, 230);
                        cav.drawImage(res.path, 10, 178, 370, 230);

                        //小程序码1
                        cav.drawImage(this.codeUrl, 48, 456, 100, 100);
                        //星星（生产环境下需要循环判断）
                        cav.drawImage('/static/star.png', 314, 358, 20, 20);
                        //文字
                        cav.font = 'normal bold 18px sans-serif';
                        cav.setTextAlign('center');
                        cav.fillText('我是第' + this.cardInfo[this.current].ranking + '位成功领取校友卡的校友', 195, 54);

                        cav.setFillStyle("#593629");
                        cav.setTextAlign('left');
                        cav.font = 'normal bold 18px sans-serif';
                        cav.fillText(this.name, 50, 275);
                        cav.setFillStyle("#593629");
                        cav.setFontSize(11);
                        cav.fillText(this.college, 50, 303);
                        cav.setFillStyle("#593629");
                        cav.setFontSize(11);
                        cav.fillText(this.startYear, 50, 323);

                        cav.setFontSize(11);
                        cav.setTextAlign('left');
                        cav.setFillStyle('black');
                        cav.font = 'normal bold 14px sans-serif';
                        cav.fillText('长按识别小程序码', 180, 471);
                        cav.setFontSize(10);
                        cav.fillText('校友卡由' + this.schoolName + '校友小程序生成', 180, 531);

                        //绘制圆角矩形
                        cav.setFillStyle('white')
                        // cav.setStrokeStyle('transparent')
                        // 左上角
                        cav.arc(x + r, y + r, r, Math.PI, Math.PI * 1.5)

                        // border-top
                        cav.moveTo(x + r, y)
                        cav.lineTo(x + w - r, y)
                        cav.lineTo(x + w, y + r)
                        // 右上角
                        cav.arc(x + w - r, y + r, r, Math.PI * 1.5, Math.PI * 2)

                        // border-right
                        cav.lineTo(x + w, y + h - r)
                        cav.lineTo(x + w - r, y + h)
                        // 右下角
                        cav.arc(x + w - r, y + h - r, r, 0, Math.PI * 0.5)

                        // border-bottom
                        cav.lineTo(x + r, y + h)
                        cav.lineTo(x, y + h - r)
                        // 左下角
                        cav.arc(x + r, y + h - r, r, Math.PI * 0.5, Math.PI)

                        // border-left
                        cav.lineTo(x, y + r)
                        cav.lineTo(x + r, y)

                        // 这里是使用 fill 还是 stroke都可以，二选一即可，但是需要与上面对应
                        cav.fill()
                        // cav.stroke()
                        cav.closePath()
                        // 剪切
                        cav.clip();
                        cav.font = 'normal bold 14px sans-serif';

                        cav.setTextAlign('center');
                        cav.setFillStyle('black');
                        cav.fillText('你还在等待什么呢！', 195, 90);
                        cav.draw(false, function() {

                            uni.canvasToTempFilePath({
                                // canvasId: 'sharecard',
                                canvasId: canvasId,
                                success: (res) => {
                                    uni.hideLoading()
                                    console.log(res.tempFilePath)
                                    uni.saveImageToPhotosAlbum({
                                        filePath: res.tempFilePath,
                                        success: function(res) {
                                            console.log("图片已保存")
                                            uni.showToast({
                                                title: '图片已保存'
                                            })
                                        },
                                        fail() {
                                            console.log(
                                                "失败了canvasToTempFilePath");
                                        }
                                    })
                                },
                                fail(res) {
                                    console.log(res);

                                }
                            })

                        })

                    }
                })


            },
            getWish() {
                this.pageNo = this.pageNo + 1;
                request.http({
                    url: "/app/wish/loadbypage",
                    data: {
                        pageNo: this.pageNo,
                        pageSize: this.pageSize
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.data.list;
                            datas.forEach((d) => {
                                this.congratulations.push(d);
                            });
                            this.status = "more";
                            this.isMore = (res.data.pages > (this.pageNo) ? true : false);
                            if (datas.length > 0) {
                                this.showList = true;
                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
        },
        onReachBottom() {
            this.status = "loading";
            setTimeout(() => {
                if (this.isMore) {
                    this.getWish();
                } else {
                    this.status = "noMore";
                }
            }, 2000, null);

        },
        onShareAppMessage() {
            this.type = !(this.type);
            expand.saveShare(app.share_code.MYCARD_SHARE);
            return {
                title: "我已领取到校友卡，你也赶快去领取吧",
                path: '/pages/tabbar/index/index'
                // imageUrl: this.image ? this.image : 'https://img-cdn-qiniu.dcloud.net.cn/uniapp/app/share-logo@3.png'
            }
        },
    }
</script>

<style lang="scss">
    .swiper-item image.image_noc {
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

    .card {
        position: absolute;
        top: 139upx;
        left: 80upx;
    }

    .xyk {
        width: 140upx;
        height: 168upx;
        float: left;
        margin-left: 6%;
    }

    .xyk_1 {
        margin-left: 4%;
    }

    .tu_1 {
        width: 114upx;
        height: 114upx;
        margin: 0 auto;
    }

    .tu_1 image {
        width: 100%;
        height: 100%;
    }

    .links {
        margin-top: 20upx;
        overflow: hidden;
    }

    .links navigator {
        text-align: center;
        margin-top: 10upx;
    }

    .links text {
        width: 100%;
        display: block;
        font-size: 28upx;
        text-align: center;
        margin-top: 12upx;
    }

    .b-title {
        position: relative;
        padding-left: 38upx;
        margin-top: 20upx;
        font-size: $uni-font-size-lg;
        color: #333;
        font-weight: bold;
		&:before{position: absolute;content:'';width: 10upx;
        height: 40upx;
        background: #deb051;
        display: inline-block;
        border-radius: 5upx;
        margin-right: 10upx;
        position: relative;
        top: 8upx;
			
		}
    }

    .uni-row {
        margin-top: 20upx;background-color:#e9eaeb;padding: 20upx 38upx;

		.button-wish{vertical-align: middle;
			width: 120upx;
			height: 64upx;
			line-height: 64upx;
			border-radius: 10upx;
			background: #e2b352;
			
			text-align: center;
			color: #fff;
			
		}
		.input-wish{width: calc(100% - 120upx);height: 64upx;
			line-height: 64upx;}
    }

    .send-wish {
        height: 50upx;
        background-color: #e9eaeb;
        margin-top: 20upx;
    }

    

 
.invite_btn{position:fixed;width:100%;bottom:0;left:0;
	.button {
	    background: #262f43;
	    color: #fff;
	    border-radius: 0;
	}
}
    

    .list {
        background: #eaeaea;
        padding-bottom: 100upx;
    }

    .list-item {
       
      
        padding: 20upx 30upx 20upx 38upx;
        margin-bottom: 10upx;
        background: #fff;
        line-height: 1.4;
    }

    uni-popup .hint {
        height: 100upx;
        margin-left: 80upx;
        padding-bottom: 0;
        overflow: hidden;
        margin-top: -23upx;
    }

    uni-popup .hint .sub {
        float: left;
        margin-left: 15upx;
    }

    uni-popup .hint image {
        float: left;
        width: 85upx;
        height: 85upx;
        border-radius: 50%;
    }

    uni-popup .hint .rescode {
        font-size: 15upx;
        font-weight: bold;
        margin-left: 0;
        margin-top: 0;
    }

    uni-popup .hint .create {
        font-size: 13upx;
    }

    .card_bg {
        border-radius: 20upx 20upx 0 0;
        width: 100% !important;
        position: absolute;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .card_pop {
        display: block;
        width: 390upx !important;
        height: 225upx !important;
        margin: 15upx auto 50upx auto;
    }

    .primary.inner {
        width: 180upx;
        height: 60upx;
        background-color: #272d3d;
        border-radius: 30upx;
        line-height: 60upx;
        margin-top: 15upx;
        font-size: 30upx;
    }

    .uni-popup {
        width: 460upx;
        height: 600upx;
        padding: 0 !important;
        border-radius: 20upx !important;
        position: fixed !important;
        top: 50% !important;
    }

    uni-popup .tip {
        font-size: 24upx;
        color: #FFFFFF;
        text-align: center;
        margin-top: 40upx;
    }

    uni-popup .tip text {
        font-size: 31upx;
    }

    uni-popup .wait {
        width: 173upx;
        height: 38upx;
        background-color: #ffffff;
        border-radius: 19upx;
        font-size: 20upx;
        color: #f99859;
        margin: 0 auto;
        line-height: 38upx;
        text-align: center;
        margin-top: 5upx;
    }

    uni-popup .name {
        margin-top: 110upx;
        font-size: 30upx;
        color: #603a2b;
        line-height: 1.4;
        margin-left: -20upx;
    }

    uni-popup .dept {
        font-size: 15upx;
        color: #603a2b;
        margin-left: -20upx;
    }

    uni-popup .grade {
        font-size: 15upx;
        color: #603a2b;
        margin-left: -20upx;
    }

    .star {
        
        position: absolute;
        left: 0;
        height:100upx;
        bottom: 50upx;
        right: 0;
        width: 86vw;
        margin: auto;
		.starText {
        float: left;
        font-size: 24upx;
        color: #603a2b;
        line-height: 100upx;
    }

    }

    

    .star image {
        width: 100%;
        height: 100%;
    }

    uni-popup .star {
        /* width: 25upx; */
        height: 25upx;
        position: absolute;
        right: 73upx;
        top: 300upx;
    }
</style>
