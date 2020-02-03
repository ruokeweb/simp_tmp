<template>
    <view class="content">
        <uni-swiper-dot :info="bannerlist" :current="current" :mode="mode" field="content">
            <swiper class="swiper-box" @change="change" :autoplay=true :interval=5000 :circular=true>
                <swiper-item v-for="(item ,index) in bannerlist" :key="index">
                    <view :class="item.colorClass" class="swiper-item">
                        <image :src="imagePath + item.pic" mode="aspectFill" @click="goBannerDetail(item)" />

                    </view>
                    <!-- <view class="content_wb">
                        <view class="jx"></view>
                        <text>{{item.tittle}}</text>
                    </view> -->
                </swiper-item>
            </swiper>
        </uni-swiper-dot>
        <view class="uni-swiper-msg sch_wish">
            <swiper vertical="true" autoplay="true" circular="true" interval="5000">
                <swiper-item v-for="(item, index) in thankslist" :key="index">
                    <view style="float:right;margin-right:20upx;background:rgba(0,0,0,.5);border-radius:30rpx;height:45rpx;">
                        <image src="/static/img/index/flower.png" style="position: relative; top: 1upx; right: 0;"></image>
                        <navigator class="wnr" style="display:inline;position:relative;bottom:6upx;padding-right:10rpx;">{{item.information}}</navigator>
                    </view>
                </swiper-item>
            </swiper>
        </view>
        <view class="kuang"></view>
        <view class="sch_content" v-if="isLogin==true">
            <view class="info uni-swiper-msg">
                <image src="/static/img/index/tz.png" mode=""></image>
                <swiper vertical="true" autoplay="true" circular="true" interval="5000">
                    <swiper-item v-for="(item, index) in cardMsg" :key="index">
                        <navigator class="text_nr">祝贺{{item.startdate==null ?'***':item.startdate}}级{{item.specialty==null ?'***':item.specialty}}{{item.name}}申领成功</navigator>
                    </swiper-item>
                </swiper>
            </view>
            <view class="card">
                <view class="xyk xyk_1" @click="goMyCard()">
                    <view class="tu_1">
                        <image src="/static/img/index/xyk.png" mode=""></image>
                    </view>
                    <text>校友卡</text>
                </view>
                <view class="xyk" @click="goBackSchool()">
                    <view class="tu_1">
                        <image src="/static/img/index/fx.png" mode=""></image>
                    </view>
                    <text>值年返校</text>
                </view>
                <view class="xyk" @click="goMyAssociation()">
                    <view class="tu_1">
                        <image src="/static/img/index/xyh.png" mode=""></image>
                    </view>
                    <text>校友会</text>
                </view>
                <view class="xyk" @click="goDon()">
                    <view class="tu_1">
                        <image src="/static/img/index/hk.png" mode=""></image>
                    </view>
                    <text>回馈</text>
                </view>
            </view>
            <view class="lq">
                已有{{numCard}}位校友领取校友卡
            </view>
        </view>
        <view class="xiao_card" v-if="isLogin==false" style="z-index:7">
            <view class="yl" style="z-index:8">
                <image src="/static/img/index/yl.png" mode=""></image>
                <view class="title">
                    {{numCard}}<text>位</text>
                </view>
                <view class="nr">
                    校友已领取校友卡，您还在等什么？
                </view>
                <view class="hx">

                </view>
                <view class="tz uni-swiper-msg">
                    <image src="/static/img/index/tz_2.png" mode=""></image>
                    <swiper vertical="true" autoplay="true" circular="true" interval="5000">
                        <swiper-item v-for="(item, index) in cardMsg" :key="index">
                            <navigator class="wb">祝贺{{item.startdate==null ?'***':item.startdate}}级{{item.specialty==null ?'***':item.specialty}}{{item.name}}申领成功</navigator>
                        </swiper-item>
                    </swiper>
                </view>
            </view>
            <view class="wl" @click="goLogin()" style="z-index:7">
                <image src="/static/img/index/wl.png" mode="false"></image>
                <text>立即领取</text>
            </view>
        </view>
        <view>
            <!-- <segmented-control id="tabbar" :values="items" :stickyTop="108" :current="cur" @clickItem="onClickItem"></segmented-control> -->
            <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
                <view class="flex-item" v-if="is_on_webvsb" @click="onClickItem(2)">
                    <text :class="cur==2 ? 'on' : '' ">公司动态</text>
                </view>
                <view class="flex-item" @click="onClickItem(0)">
                    <text :class="cur==0 ? 'on' : '' ">活动推荐</text>
                </view>
                <view class="flex-item" @click="onClickItem(1)">
                    <text :class="cur==1 ? 'on' : '' ">回馈推荐</text>
                </view>
            </view>
            <view class="list" id="list">
                <view v-show="cur === 0" v-for="(value, key) in actlist" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.image!=null&&value.image!=''" :src="imagePath + value.image" mode="aspectFill"></image>
                            <image v-if="value.image==null||value.image==''" src="/static/img/act/act_default.png" mode="aspectFill"></image>
                            <!-- <image :src="imagePath + value.image" mode="aspectFill"></image> -->
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
                            <view class="sch_cont_title">
                                {{ value.name }}
                            </view>
                            <view class="sch_cont_date">
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
                <view v-show="cur === 1" v-for="(value, key) in donlist" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.pic!=null&&value.pic!=''" :src="imagePath + value.pic" mode="aspectFill"></image>
                            <image v-if="value.pic==null||value.pic==''" src="/static/img/don_default.png" mode="aspectFill"></image>
                            <view v-if="value.status=='HASDONE'" class="end">
                                已结束
                            </view>
                            <view v-if="value.status=='BEDOING'" class="doing">
                                进行中
                            </view>
                            <view v-if="value.status=='WILL'" class="readay">
                                预备中
                            </view>
                        </view>
                        <view class="sch_cont">
                            <view class="sch_cont_title">
								{{ value.name }}
                            </view>
                            <view class="don_bm" v-if="null!=value.startdate && null !=value.enddate">
                                {{value.summary}}
                            </view>
                            <view class="don_bm_nodate" v-if="null==value.startdate || null ==value.enddate">
                                {{value.summary}}
                            </view>
                            <view class="don_date" v-if="null!=value.startdate && null !=value.enddate">
                                时间：{{value.startdate}}至{{value.enddate}}
                            </view>
                        </view>
                    </view>
                    <view class="activity_h"></view>
                </view>
                <view v-show="cur === 2" v-for="(value, key) in newslist" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.picurl == domain || value.picurl == ''" src="/static/img/act/act_default.png" mode="aspectFill"></image>
                            <image v-if="value.picurl != domain" :src="value.picurl" mode="aspectFill"></image>
                        </view>
                        <view class="sch_cont">
                            <view class="sch_cont_title dyn">
                                {{ value.title }}
                            </view>
                            <view class="bm">
                                <view class="target">
                                    {{value.date}}
                                </view>
                            </view>
                        </view>
                    </view>
                    <view class="activity_h"></view>
                </view>
                <view class="foot">
                    <view class="more" @click="goMore()">
                        查看更多
                    </view>

                </view>
            </view>
            <view class='popup' v-if="is_on_index_mediaMes && mediaId.length !=0 && null != mediaId" @click='disotherImage(mediaId)'>
                <view class="popup-shade"></view>
                <view class="suspension-hide">
                    <image src="/static/img/index/tz(1).png"></image>
                    <title></title>
                </view>
            </view>


            <view class='suspension-show' v-if='is_on_index_mediaMes && is_show_mediaMes'>
                <view class='toastbg' @click='disotherremark()'></view>
                <view class='showToast'>
                    <!-- <text class='title-text' @click='disotherImage(mediaId)' >{{mediaTitle}}</text> -->
                    <image v-if="mediaCoverImage!=null&&mediaCoverImage!=''" :src="imagePath +mediaCoverImage" mode="aspectFill"
                        @click='disotherImage(mediaId)'></image>
                    <!-- <image v-if="mediaCoverImage==null||mediaCoverImage==''" src="/static/img/act/act_default.png" mode="aspectFill" @click='disotherImage()'></image> -->
                    <view class="close" @click='disotherremark()'></view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import util from '@/common/util.js';
    import uniSwiperDot from '@/components/uni-swiper-dot/uni-swiper-dot.vue'
    import segmentedControl from '@/components/uni-segmented-control/uni-segmented-control.vue';
    import '@/static/css/index.css'
    import store from '@/store/index.js';
    import uniPopup from '@/components/uni-popup/uni-popup.vue'
    import {
        mapState,
        mapMutations
    } from 'vuex';

    export default {
        components: {
            uniSwiperDot,
            segmentedControl,
            uniPopup
        },
        data() {
            return {
                items: ['公司动态', '活动推荐', '回馈推荐'],
                // items: ['活动推荐', '回馈推荐'],
                // canIUse: wx.canIUse('button.open-type.getUserInfo'),
                cur: 0,
                bannerlist: [],
                thankslist: [],
                modeIndex: -1,
                styleIndex: -1,
                current: 0,
                mode: 'long',
                dotsStyles: {},
                isLogin: true,
                numCard: '',
                cardMsg: [],
                actlist: [],
                donlist: [],
                newslist: [],
                actPageNo: 1,
                donPageNo: 1,
                newsPageNo: 1,
                pageSize: 3,
                is_on_webvsb: "",
                imagePath: app.imagePath,
                truePath: '',
                trueCardPath: '',
                is_auth: true,
                type: '',
                wxCode: '',
                openid: '',
                mediaId: '',
                mediaTitle: '',
                mediaCoverImage: '',
                is_show_mediaMes: true,
                domain: "",
                is_on_index_mediaMes: "" //首页通知总开关
            }
        },
        computed: {
            ...mapState(['hasLogin', 'userInfo','configParams'])
        },
        onLoad(event) {
            const payload = event.truePath || event.payload;
            if (payload != null) {
                try {
                    this.truePath = JSON.parse(decodeURIComponent(payload));
                } catch (error) {
                    this.truePath = JSON.parse(payload);
                };
                if (this.truePath != '') {
                    uni.navigateTo({
                        url: this.truePath
                    });
                }
            }

            //下面的专用于模板消息的注册成功通知
            const trueCardPath = event.trueCardPath || event.payload;
            if (trueCardPath != null) {
                try {
                    this.trueCardPath = JSON.parse(decodeURIComponent(trueCardPath));
                } catch (error) {
                    this.trueCardPath = JSON.parse(trueCardPath);
                };
                if (this.trueCardPath != '') {
                    /* uni.navigateTo({
                        url: this.trueCardPath
                    }); */
                    this.goMyCard();
                }
            }
            //自动登录
            this.isLogin = this.hasLogin;
            this.getOpenId();
        },
        onShow() {
            if(this.isLogin != this.hasLogin){
                this.isLogin = this.hasLogin;
            }
            if (this.bannerlist.length == 0) {
                this.getBannerList();
            }
            if (this.cardMsg.length == 0) {
                this.getCollarCardList();
            }
            if (this.thankslist.length == 0) {
                this.getThankslist();
            }
            this.getCollarCardNum();
            
            if(!this.configParams.flag){
                this.getwebVsbAndMes();
            }else{
                this.is_on_webvsb = this.configParams.is_on_webvsb;
                this.is_on_index_mediaMes = this.configParams.is_on_index_mediaMes;
                this.domain = this.configParams.domain;
                if (this.is_on_index_mediaMes) {
                    //判断日期
                    this.judgeIs_show_mediaMes();
                    this.showMediaMessage();
                }
                if (this.is_on_webvsb) {
                    this.cur = 2;
                    if (this.newslist.length == 0) {
                        this.getIndexNews();
                    }
                } else {
                    this.cur = 0;
                    this.getActList();
                }
            }
        },
        onTabItemTap() {
            //util.getStatPoint('switchTab', 'EVENT_DESCRIBE_45')

        },
        methods: {
            ...mapMutations(['login', 'setUserInfo','setConfigParams']),
            /*显示首页通知消息*/
            getBannerList(){
                request.http({
                    url: '/app/index/getIndexBanners',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.data;
                            datas.forEach((d) => {
                                d.tittle = util.strcharacterDiscode(d.tittle);
                            });
                            this.bannerlist = datas;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            getIndexNews(){
                request.http({
                    url: '/app/index/getIndexNews',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.newslist = res.data.data;
                        }
                    },
                    error: (res) => {

                    }
                });
            },
            showMediaMessage() {
                if (this.is_on_index_mediaMes) {
                    request.http({
                        url: '/app/index/getIndexMediaMessage',
                        method: "POST",
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                if (res.data.data != null) {
                                    this.mediaId = res.data.data.id;
                                    this.mediaTitle = util.strcharacterDiscode(res.data.data.title);
                                    this.mediaCoverImage = res.data.data.coverImage;
                                } else {
                                    this.is_show_mediaMes = false;
                                }
                            } else {
                                this.is_show_mediaMes = false;
                            }
                        },
                        error: (res) => {
                            this.is_show_mediaMes = false;
                        }
                    })
                }
            },
            //弹框 点击其他地方
            disotherremark() {
                this.is_show_mediaMes = false;
            },
            disotherImage(id) {
                this.is_show_mediaMes = false;
                //跳转至详情页
                uni.navigateTo({
                    url: '/pages/tabbar/index/mediaMessage/mediaMessage?id=' + id
                });
            },
            autoLogin(openid) {
                //判断是否登录
                if (!this.isLogin) {
                    uni.showLoading({
                        title: '加载数据中',
                        mask: true
                    });
                    //使用openid 去实现登录
                    uni.request({
                        url: app.serverPath + '/app/loginByOpenId',
                        method: 'POST',
                        header: {
                            'content-type': 'application/x-www-form-urlencoded'
                        },
                        data: {
                            openid: openid,
                            comeFrom: app.COME_FROM
                        },
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                this.login(res.data.data);
                                this.setUserInfo(res.data.data.userInfo);
                                this.isLogin = true;
                            }
                            uni.hideLoading();
                        },
                        fail: (res) => {
                            // this.showpageToast(res.data.msg);
                            this.showpageToast("请求错误");
                            this.disabled = false;
                            uni.hideLoading();
                        }
                    })
                }

            },
            getOpenId() {
                this.openid = uni.getStorageSync('openid');
                if (this.openid == '') {
                    uni.getProvider({
                        service: 'oauth',
                        success: (res) => {
                            console.log(res.provider)
                            if (~res.provider.indexOf('weixin')) {
                                uni.login({
                                    provider: 'weixin',
                                    success: (loginRes) => {
                                        console.log(JSON.stringify(loginRes));
                                        this.WxCode = loginRes.code;
                                        console.log(this.code)
                                        //获取用户openid
                                        request.http({
                                            url: "/app/pay/getInfo",
                                            data: {
                                                'code': this.WxCode
                                            },
                                            header: 'application/x-www-form-urlencoded',
                                            success: (res) => {
                                                this.openid = res.data.data.openId;
                                                this.autoLogin(res.data.data.openId);
                                            },
                                            fail: () => {}
                                        })

                                    }
                                });
                            }
                        }
                    });
                } else {
                    this.autoLogin(this.openid);
                }
            },
            hidePopup(type) {
                this.type = type;
            },
            getuserinfo(e) {
                console.log(e.detail.userInfo);
                //储存信息到本地
                uni.setStorageSync('weixin_nickName', e.detail.userInfo.nickName);
                uni.setStorageSync('weixin_avatarUrl', e.detailuserInfo.avatarUrl);
            },
            getCollarCardNum(e) {
                request.http({
                    url: "/app/index/getCollarCardNum",
                    header: 'application/x-www-form-urlencoded',
                    data: {},
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.numCard = res.data.data;
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            getCollarCardList(e) {
                request.http({
                    url: "/app/index/getCollarCardList",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageSize: 100
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            /* this.numCard =res.data.data; */
                            var datas = res.data.data;

                            datas.forEach((d) => {
                                if (d.indexEduVo != null) {
                                    if (d.indexEduVo.startdate != null) {
                                        d["startdate"] = util.dateUtils.getYearByDate(d.indexEduVo.startdate);
                                    }
                                    let edu = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                        d.indexEduVo.specialty);
                                    if (edu != undefined) {
                                        d["specialty"] = edu.name;
                                        this.cardMsg.push(d);
                                    }
                                }
                            });
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            getThankslist(e) {
                request.http({
                    url: "/app/index/getThankslist",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        pageSize: 100
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.thankslist = res.data.data;
                            /* 
                                        datas.forEach((d) => {
                                            if (d.indexEduVo.startdate != null) {
                                                d["startdate"] = util.dateUtils.getYearByDate(d.indexEduVo.startdate);
                                            }
                                            d["specialty"] = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                                d.indexEduVo.specialty).name;
                                            this.cardMsg.push(d);
                                        }); */
                        }
                    },
                    fail: (data, code) => {}
                })
            },
            onClickItem(index) {
                if (this.cur !== index) {
                    this.cur = index;
                    if (this.cur == 0) {
                        if (this.actlist.length == 0) {
                            this.getActList();
                        }

                    } else if (this.cur == 1) {
                        if (this.donlist.length == 0) {
                            this.getDonList();
                        }

                    } else if (this.cur == 3) {
                        if (this.newslist.length == 0) {
                            this.getIndexNews();
                        }
                    }
                }

            },
            change(e) {
                this.current = e.detail.current
            },
            selectStyle(index) {
                this.dotsStyles = this.dotStyle[index]
                this.styleIndex = index
            },
            selectMode(mode, index) {
                this.mode = mode
                this.modeIndex = index
                this.styleIndex = -1
                this.dotsStyles = this.dotStyle[0]
            },
            goLogin() {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_1');
                uni.navigateTo({
                    url: "../../login/nocard"
                });
            },
            getActList() {
                request.http({
                    url: '/app/act/hotActList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.actPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                d.name = util.strcharacterDiscode(d.name);
                            });
                            this.actlist = datas;
                        }
                    },
                    error: (res) => {

                    }
                });

            },
            getDonList() {
                request.http({
                    url: '/app/don/donProList',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        pageNo: this.donPageNo,
                        pageSize: this.pageSize
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            var datas = res.data.list;
                            datas.forEach((d) => {
                                var percent = 100;
                                if (d.gotMoney < d.targetMoney) {
                                    percent = Number(d.gotMoney / d.targetMoney * 100).toFixed(0)
                                }
                                d["name"] = util.strcharacterDiscode(d.name);
                                d["summary"]= util.strcharacterDiscode(d.summary);
                                d["percent"] = percent;
                                this.donlist.push(d);
                            });

                        }
                    },
                    error: (res) => {

                    }
                })
            },

            goMore() {
                console.log(this.cur);
                let url = '';
                if (this.cur == 0) {
                    uni.switchTab({
                        url: '/pages/tabbar/act/act'
                    });
                } else if (this.cur == 1) {
                    uni.switchTab({
                        url: '/pages/tabbar/don/don'
                    });
                } else if (this.cur == 2) {
                    uni.navigateTo({
                        url: '/pages/index/news/newsList'
                    });
                }
            },
            goDetail(e) {
                let url = '';
                if (this.cur == 0) {
                    url = '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                } else if (this.cur == 1) {
                    url = '/pages/don/donDetail?id=' + encodeURIComponent(e.id);
                } else if (this.cur == 2) {
                    url = '/pages/index/news/newsDetail?id=' + encodeURIComponent(e.newsid);
                }
                uni.navigateTo({
                    url: url
                });
            },
            goBannerDetail(e) {
                // //util.getStatPoint('click', 'EVENT_DESCRIBE_14');
                let url = '';
                if (e.type === "DON") {
                    url = '/pages/don/donDetail?id=' + encodeURIComponent(e.id);
                } else if (e.type === "ACT") {
                    url = '/pages/act/actDetail?id=' + encodeURIComponent(JSON.stringify(e.id));
                }
                uni.navigateTo({
                    url: url
                });
            },

            goMyCard(e) {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_15');
                if (this.userInfo.cardStatus == app.CARD_STATUS.NORMAL_CARD_STATUS) {
                    request.http({
                        url: '/app/index/refreshStar',
                        method: "POST",
                        data: {},
                        success: (res) => {
                            this.userInfo.complete = res.data.data;
                            /*  var data = uni.getStorageSync('userInfo');
                              data.complete = res.data.data;
                              uni.setStorageSync("userInfo", data); */
                            this.setUserInfo(this.userInfo);
                            uni.navigateTo({
                                url: "/pages_mine/myCard/myCard"
                            });
                        },
                        error: (res) => {

                        }
                    });
                } else {
                    request.http({
                        url: '/app/index/getMyCard',
                        method: "POST",
                        data: {},
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                uni.navigateTo({
                                    url: res.data.data.pages + "?resultContent=" +
                                        encodeURIComponent(res.data.data.resultContent)
                                });
                            } else {
                                uni.navigateTo({
                                    url: "/pages/login/cardstatusAuthing"
                                });
                            }
                        },
                        error: (res) => {

                        }
                    });
                }

            },
            goBackSchool(e) {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_16');
                //这块还需要判断是否能够发起值年返校
                if (this.isLogin) {
                    if (this.userInfo.cardStatus != 'NORMAL') {
                        this.showpageToast("您的信息还未审核通过，不能发起值年返校");
                    } else {
                        let url = '/pages/act/backSchoolInfo';
                        uni.navigateTo({
                            url: url
                        });
                    }

                } else {

                    let url = '/pages/login/login';
                    uni.navigateTo({
                        url: url
                    });
                    this.showpageToast("您还未登录，不能发起值年返校");
                }
            },
            goMyAssociation(e) {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_17');
                //登录之后需要获取卡的状态，根据卡的状态判断需要跳转的页面
                let url = "/pages/index/association/association";
                uni.navigateTo({
                    url: url
                });
            },
            goDon(e) {
                //util.getStatPoint('click', 'EVENT_DESCRIBE_18');
                let url = '/pages/tabbar/don/don';
                uni.switchTab({
                    url: url
                });
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            //判断是否展示首页通知
            judgeIs_show_mediaMes() {
                //获取缓存时间
                var lastOpenDate = uni.getStorageSync("mediaMes_lastOpenDate");
                //获取当前时间
                var currentDate = util.dateUtils.getDate(new Date);
                if (lastOpenDate == null || lastOpenDate == "" || lastOpenDate != currentDate) {
                    this.is_show_mediaMes = true;
                    uni.setStorageSync("mediaMes_lastOpenDate", currentDate);
                } else {
                    this.is_show_mediaMes = false;
                }
            },
            getwebVsbAndMes(){
                uni.request({
                    url: app.serverPath + '/app/wx/getMiniProConfig', //仅为示例，并非真实接口地址。
                    method: 'POST',
                    success: (res) => {
                        //设置初始化数据
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            let cache = res.data.data;
                            this.setConfigParams(cache);
                            this.is_on_webvsb = cache.is_on_webvsb;
                            this.is_on_index_mediaMes = cache.is_on_index_mediaMes;
                            this.domain = this.configParams.domain;
                            if (this.is_on_index_mediaMes) {
                                //判断日期
                                this.judgeIs_show_mediaMes();
                                this.showMediaMessage();
                            }
                            if (this.is_on_webvsb) {
                                this.cur = 2;
                                if (this.newslist.length == 0) {
                                    this.getIndexNews();
                                }
                            } else {
                                this.cur = 0;
                                this.getActList();
                            }
                        } else {
                            console.log(res.data.msg);
                        }
                    },
                    fail() {
                        console.log("网络连接错误");
                    }
                });
            }
        }
    }
</script>
<style lang="scss">
    .uni-swiper-msg {
        padding: 0;
    }

    .content .sch_wish swiper {
        font-size: 26upx;
        color: #FFFFFF;
        margin-left: 12upx;
        margin-top: -4upx;
    }

    .navigator-hover {
        background-color: rgba(0, 0, 0, 0);
        opacity: 1;
    }

    .content .activity .sch_tu .doing {
        width: 93upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 10upx;
        bottom: 10upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity .sch_tu .readay {
        width: 93upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        position: absolute;
        right: 10upx;
        bottom: 10upx;
        color: white;
        line-height: 40upx;
        text-align: center;
    }

    .content .activity_2 .doing_2 {
        width: 93upx;
        height: 40upx;
        background-color: rgba(55, 151, 84, 0.5);
        border-radius: 5upx;
        color: white;
        line-height: 40upx;
        text-align: center;
        float: right;
    }

    .content .activity_2 .readay_2 {
        width: 93upx;
        height: 40upx;
        background-color: rgba(158, 117, 33, 0.5);
        border-radius: 5upx;
        color: white;
        line-height: 40upx;
        text-align: center;
        float: right;
    }

    .content .sch_cont .people {
        width: 126rpx;
        font-size: $uni-font-size-sm;
        float: right;
        color: #999999;

    }

    .content .people text {
        color: #ed5849;
    }

    .content .sch_cont{
		.target {
		    font-size: $uni-font-size-sm;
		    color: #999999;
		}
	}

    .content .sch_cont .target text {
        color: red;
    }

    .content .sch_cont .got {
        font-size: $uni-font-size-sm;
        float: right;
        color: #999999;
    }

    .content .sch_cont .got text {
        color: red;
    }

    .content .content_wb {
        margin-top: -4upx;
        overflow: hidden;
        //height:66upx;
        text-overflow: ellipsis;
        background: rgba(0, 0, 0, 0.3);
        padding-top: 15upx;
        padding-left: 20upx;
    }

    .content .content_wb text {
        margin-top: -4upx;
        width: 95%;
        height: 36rpx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .content .sch_cont .date_mod {
        position: absolute;
        left: 0;
        top: 127upx;
        margin-top: 0;
    }

    .content .activity .sch_tu {
        width: 230upx;
        height: 160upx;
    }

    .content .activity .sch_cont {
        margin-top: 0;
        width: 427upx;
        position: relative;
    }

    .content .sch_cont .bm {
        width: 427upx;
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

    .flex-item {
        width: 33.33333%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: $uni-font-size-lg;
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: $uni-font-size-lg;
    }

    .flex-item text {
        height: 96rpx;
        display: inline-block;
    }

    .uni-row {
        padding-left: 20upx;
    }

    .content .bm .xy {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    .suspension-show {
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        rigth: 0;
        z-index: 9;
    }

    .toastbg {
        opacity: 0.8;
        background-color: black;
        position: absolute;
        width: 100%;
        min-height: 100vh;
    }

    .showToast {
        text-align: center;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        margin: auto;
        height: 493px;
        overflow: hidden;
    }

    .showToast image {
        width: 590upx;
        height: 720upx;
    }

    .title-text {
        width: 590upx;
        background-color: #FFFFFF !important;
        font-weight: bold;
        display: inline-block;
    }


    .close {
        width: 120upx;
        height: 120upx;
        position: relative;
        border: solid 1upx #fff;
        border-radius: 50%;
        margin: 30upx auto 0;
    }

    .close:before {
        position: absolute;
        content: "";
        width: 80upx;
        height: 1upx;
        background: #fff;
        transform: rotate(-45deg);
        top: 0;
        bottom: 0;
        margin: auto;
        left: 0;
        right: 0;
    }

    .close:after {
        position: absolute;
        content: "";
        width: 80upx;
        height: 1upx;
        background: #fff;
        transform: rotate(45deg);
        top: 0;
        bottom: 0;
        margin: auto;
        left: 0;
        right: 0;
    }

    .weui-grid__icon {
        width: 60upx;
        height: 60upx;
    }

    .notification {
        position: absolute;
        height: 60upx;
        left: 20upx;
        right: 20upx;
        bottom: 20upx;
    }

    .notification-shade {
        position: absolute;
        background: #000;
        opacity: 0.2;
        left: 0;
        right: 0;
        bottom: 0;
        top: 0;
        border-radius: 0 0 24upx 24upx;
    }

    .notification-one {
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
        top: 0;
        color: #fff;
        font-size: 26upx;
        height: 60upx;
        line-height: 60upx;
        padding: 0 40upx;
    }

    .notification-one image {
        width: 34upx;
        height: 30upx;
        vertical-align: sub;
        margin-right: 16upx;
    }

    .popup {
        position: fixed;
        width: 70upx;
        height: 70upx;
        right: 0;
        top: 800upx;
    }

    .popup-shade {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: #EDC677;
        border-radius: 60upx 0 0 60upx;
        opacity: 0.4;
    }

    .suspension-hide {
        background: 0;
        position: absolute;
        padding-right: 0;
        color: #F8F8F8;
        right: 0;
        width: 70upx;
        height: 70upx;
        font-size: 22upx;
        border-radius: 60upx 0 0 60upx;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .suspension-hide image {
        width: 58upx;
        height: 58upx;
        /* margin-right: 10rpx; */
    }
	.content .sch_cont{
		.sch_cont_title{
			overflow: hidden;
			-o-text-overflow: ellipsis;
			text-overflow: ellipsis;
			display: -webkit-box;
			-webkit-line-clamp: 1;
			line-clamp: 1;
			-webkit-box-orient: vertical;
			&.dyn{
				-webkit-line-clamp: 2;
				line-clamp: 2;
			}
			
		}
	} 

    .content .sch_cont .don_date {
        position: absolute;
        width: 431upx;
        height: 23upx;
        font-size: 24upx;
        top: 125upx;
        color: #999999;
    }

    .content .sch_cont{
		.don_bm {
		    height: 90upx;
		    top: 40upx;
		    overflow: hidden;
		    -o-text-overflow: ellipsis;
		    text-overflow: ellipsis;
		    display: -webkit-box;
		    -webkit-line-clamp: 2;
		    line-clamp: 2;
		    -webkit-box-orient: vertical;
		  
		}
		.sch_cont_date{
			position: absolute;
			width: 431upx;
			height: 32upx;line-height: 32upx;
			font-size: 24upx;
			top: 72upx;
			color: #999999;

		}
		.don_bm_nodate {
		    height: 115upx;
		    overflow: hidden;
		    -o-text-overflow: ellipsis;
		    text-overflow: ellipsis;
		    display: -webkit-box;
		    -webkit-line-clamp: 3;
		    line-clamp: 3;
		    -webkit-box-orient: vertical;
			font-size: 24upx;color: #999;
		   
		}
	} 

    
</style>
