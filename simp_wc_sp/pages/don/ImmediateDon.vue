<template>
    <!-- 捐赠表单页 -->
    <view class="act_detail content">
        <view class="act_head">
            <view class="title">
                <view class="content_wb_jx">{{title}}</view>
<!--                <view class="jx"></view>
                <text>{{title}}</text> -->
            </view>
            <view class="tx clearfix">
                <view class="tu_2">
                    <image v-if="pic==null||pic==''" src="/static/img/man_default.png" mode="aspectFill"></image>
                    <image v-if="pic!=null&&pic!=''" :src="imagePath +pic" mode="aspectFill"></image>
                </view>
                <view class="name">{{name}}</view>
            </view>
            <view class="intro">
                <uParse v-if="content" :content="content" @preview="preview" @navigate="navigate" />
            </view>
        </view>
        <view class="act_head">
            <view class="title">
                <view class="content_wb_jx">关联项目</view>
            </view>
            <view class="bt">
                {{donProject.name}}
            </view>
            <view class="intro">
                <uParse v-if="donProject.summary" :content="donProject.summary" @preview="preview" @navigate="navigate" />
            </view>

        </view>
        <view class="uni-flex uni-row" style="border-bottom: 1px solid #f6f6f6;">
            <view class="flex-item" @click="onClickItem(0)">
                <text :class="tab_cur==0 ? 'on' : '' ">我要回馈</text>
            </view>
            <view v-show="isShare" class="flex-item" @click="onClickItem(1)">
                <text :class="tab_cur==1 ? 'on' : '' ">响应伙伴</text>
            </view>
        </view>


        <view v-show="tab_cur === 0">
            <view class="list" id="list">
                <view class="targ">
                    <view class="money clearfix">
                        <view v-for="(value, key) in defaultMoney" :key="key" :class="cur==key? 'mon_1  active' : 'mon_1' "
                            @click="howMuch(key)">
                            <text>{{value}}</text>元
                            <!-- <image v-show="cur==key" src="/static/xz.png" mode=""></image> -->
                        </view>
                    </view>
                    <view class="qt">
                        <text>其他金额</text>
                        <input @click="howMuch(-1)" v-model.trim="money1" type="digit" placeholder="请输入金额" placeholder-class="placeholderStyle" maxlength="10"
                            style="width: 60%;"></input>

                    </view>
                </view>

                <view class="theme">
                    回馈信息
                </view>
                <view class="fade">
                    <cmd-transition name="fade-up">
                        <view>
                            <view class="uni-list-cell uni-list-cell-pd">
                                <view class="uni-list-cell-db">匿名回馈</view>
                                <switch class="switch" @change="anonymousChange" :checked="hasAnonymous" />
                            </view>
                            <view v-show="!hasAnonymous" class="content_view">
                                <view class="padd">
                                    <view class="content_view_item">
                                        <text class="text_reg">姓名</text>
                                        <input class="input_reg" style="width:70%" placeholder="请输入姓名" placeholder-class="placeholderStyle"
                                            v-model.trim="billName" data-key="billName" maxlength=30 />
                                    </view>
                                    <view class="content_view_item">
                                        <text class="text_reg">联系方式</text>
                                        <input class="input_reg" style="width:70%" placeholder="请输入联系方式" placeholder-class="placeholderStyle"
                                            maxlength="12" v-model.trim="billContact" data-key="billContact" />
                                    </view>
                                </view>
                                <view class="uni-list-cell uni-list-cell-pd">
                                    <view class="uni-list-cell-db">是否需要纸质回馈证书</view>
                                    <switch class="switch" @change="certificate" :checked="certificate" />
                                </view>
                                <view class="uni-list-cell uni-list-cell-pd">
                                    <view class="uni-list-cell-db">是否需要发票</view>
                                    <switch class="switch" @change="billsChange" :checked="billsChange" />
                                </view>
                                <view v-show="isCertificate || isInvoice">
                                    <view class="padd">
                                        <view class="content_view_item">
                                            <text class="text_reg">邮寄地址</text>
                                            <input class="input_reg" style="width:70%" placeholder="请输入邮寄地址"
                                                placeholder-class="placeholderStyle" v-model.trim="billTakeAddress" data-key="billTakeAddress"
                                                maxlength=30 />
                                        </view>
                                    </view>
                                </view>
                                <view v-show="isInvoice">
                                    <view class="padd">
                                        <view class="content_view_item">
                                            <text class="text_reg">发票抬头</text>
                                            <input class="input_reg" style="width:70%" placeholder="请输入发票抬头"
                                                placeholder-class="placeholderStyle" v-model.trim="billTitle" data-key="billTitle"
                                                maxlength=30 />
                                        </view>
                                    </view>
                                </view>

                            </view>
                            <view class="checkbox">
                                <label>
                                    <checkbox-group @change="checkboxChange">
                                        <checkbox value="" :checked="checkboxValue" class="uni-checkbox-input" />同意
                                    </checkbox-group>
                                </label>
                                <view @click="togglePopup('middle-con')"><a href="#">《法律声明》</a></view>

                            </view>
                        </view>
                    </cmd-transition>
                </view>
            </view>
        </view>

        <view v-show="tab_cur === 1">
            <view class="act_con">
                <view v-for="(value, key) in donPersons" :key="key" @click="goDetail(value)">
                    <view class="activity">
                        <view class="sch_tu">
                            <image v-if="value.smSchoolmate.truePhoto == null||value.smSchoolmate.truePhoto =='' || value.isShow == is_no"
                                src="/static/img/man_default.png" mode="aspectFill"></image>
                            <image v-if="value.smSchoolmate.truePhoto!=null &&value.smSchoolmate.truePhoto !=''&& value.isShow != is_no"
                                :src="imagePath + value.smSchoolmate.truePhoto" mode="aspectFill"></image>

						</view>
                        <view class="name">
                            {{value.name}}
                        </view>
                        <view class="target target_1">
                            回馈<text>{{value.money}}元</text>

                        </view>
                        <view class="date">
                            {{value.time}}

                        </view>
                    </view>


                </view>
                <uni-load-more :status="status" :contentText="contentText" color="#999999" />
            </view>
        </view>

        <!-- 弹出页面 -->
        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="togglePopup('')">
            <view class="" style="width:400upx;height:600upx;">
                <image class="card_bg" src="/static/statement-bg.jpg" style="" mode="aspectFill"></image>
                <view class="state_title">法律声明</view>
                <view class="state_content">
                    <uParse v-if="content" :content="legalContent" @preview="preview" @navigate="navigate" />
                    <!-- {{legalContent}} -->
                </view>
                <button type="primary" class="state_btn" @click="togglePopup('')">我知道了</button>
            </view>
        </uni-popup>

        <view v-if="!isShare&&is_don_team" class="act_footer">
            <view class="act_left" @click="toIndex()">
                <button>返回首页</button>
            </view>

            <view class="act_right" @click="goDon()">
                <button formType="submit">我要献爱心</button>
            </view>
        </view> 

        <view v-if="isShare&&is_don_team" class="act_footer">
            <view class="act_left_share" @click="goIndex()">
                <button>返回首页</button>
            </view>
            <view class="act_center_share" open-type="share" v-if="togetherDonState == 'SUCCESS'">
                <button open-type="share">邀请好友</button>
            </view>
            <view class="act_right_share" @click="goDon()">
                <button formType="submit">我要献爱心</button>
            </view>
        </view>
        <view v-if="!is_don_team" class="act_footer">
            <view class="act_left_index" @click="goIndex()">
                <button>返回首页</button>
            </view>
        </view>

    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import util from '@/common/util.js';
    import expand from '@/common/expand.js';
    import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
    import cmdPageBody from "@/components/cmd-page-body/cmd-page-body.vue"
    import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
    import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"
    import uParse from '@/components/uParse/src/wxParse.vue'
    import uniLoadMore from "@/components/uni-load-more/uni-load-more.vue"
    import 'common/uni.css'
    import uniPopup from '@/components/uni-popup/uni-popup.vue';
    import {
        mapState
    } from 'vuex';
    export default {
        components: {
            uParse,
            cmdNavBar,
            cmdPageBody,
            cmdTransition,
            cmdCelItem,
            uniPopup,
            uniLoadMore
        },
        data() {
            return {
                openid: "",
                imagePath: app.imagePath,
                percent: 0,
                id: '',
                title: '',
                pic: '',
                startdate: '',
                enddate: '',
                targetMoney: 0,
                gotMoney: 0,
                donatingNum: 0,
                name: '',
                content: '',
                donProject: {},
                loadingText: '',
                money: 5000,
                cur: 0,
                tab_cur: 0,
                isCertificate: false, //证书 
                hasAnonymous: false,
                billName: '',
                billContact: '', //联系方式
                identity: '', //身份
                isInvoice: false,
                billTitle: '',
                billNum: '',
                unitAddress: '',
                billPhone: '',
                billBank: '',
                billBankNum: '',
                billTakeAddress: '',
                data: {},
                money1: 0,
                checkboxValue: true,
                defaultMoney: [],
                type: '',
                legalContent: '',
                projectId: '',
                summary: '',
                togetherDonId: null,
                formId: '',
                donPersons: [],
                pageSize: 10,
                pages: 0,
                status: "noMore",
                isShare: false,
                is_don_project: "",
                is_don_team: "",
                togetherDonState: "",
                template_shjg:""

            }
        },
        computed: {
            ...mapState(['hasLogin','userInfo','configParams'])
        },
        onReachBottom() {
            this.status = "loading";
            if (this.moreflag) {
                this.getDonPersons();
            } else {
                this.status = "noMore";
            }
        },
        onLoad(options) {
            // this.donProject =JSON.parse(decodeURIComponent(options.data));
            // this.getTeamDonDetail();
            //获取一起捐的id
            try {
                if (options.id === undefined) {
                    this.projectId = JSON.parse(decodeURIComponent(options.projectId));
                    this.title = JSON.parse(decodeURIComponent(options.title));
                    this.content = JSON.parse(util.strcharacterDiscode(decodeURIComponent(options.summary)));
                    this.targetMoney = JSON.parse(decodeURIComponent(options.targetMoney));
                } else {
                    this.id = JSON.parse(decodeURIComponent(options.id));
                }
            } catch (e) {
                //TODO handle the exception
            }
            this.getTeamDonDetail();
            if (null != this.defaultMoney && '' != this.defaultMoney) {
                this.defaultMoney = defaultMoney.split(",");
                this.money = this.defaultMoney[0]
            } else {
                this.money = 0;
            }
            if (this.id != null && this.id !== '') {
                this.isShare = true;
            }
        },
        onShow() {
            this.is_don_project = this.configParams.is_don_project;
            this.is_don_team = this.configParams.is_don_project;
            this.template_shjg = this.configParams.TEMPLATE_IDS.SHJG;
            this.getContentByPage();
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
                                        this.showpageToast("回馈失败")
                                    }
                                })

                            }
                        });
                    }
                }
            });
            if (this.hasLogin) {
                // let userInfo = this.userInfo;
                let contact = this.userInfo.smContact;
                this.billName = this.userInfo.name;
                this.billContact = null != contact ? contact.contact : this.userInfo.sysUser.username;
            }
        },
        onShareAppMessage(res) {
            expand.saveShare(app.share_code.IMMEDIATEDON_SHARE);
            var name = '';
            if (this.userInfo.name != undefined || this.userInfo.name != null) {
                name = this.userInfo.name;
            }
            if (this.userInfo.sysUser != undefined && this.userInfo.sysUser.virtualName != null) {
                name = this.userInfo.sysUser.virtualName;
            }
            if (res.from === 'button') { // 来自页面内分享按钮
            }
            var truePath = encodeURIComponent(JSON.stringify('/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(
                this.id))));
            var img = this.imagePath + this.pic
            if (this.pic == null || this.pic == '') {
                img = '/static/img/man_default.png';
            }

            return {
                title: name + '发起了共同献爱心，邀请你一起为母校助力',
                path: '/pages/tabbar/index/index?truePath=' + truePath,
                imageUrl: img
            }
        },
        methods: {
            getContentByPage() {
                uni.request({
                    url: app.serverPath + '/app/wx/getContentByPage',
                    method: 'POST',
                    header: {
                        'content-type': 'application/x-www-form-urlencoded'
                    },
                    data: {
                        code: 'legal',
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.legalContent = util.strcharacterDiscode(res.data.data);
                        }
                    },
                    fail: (res) => {

                    }
                })
            },
            goIndex() {
                uni.switchTab({
                    url: '/pages/tabbar/index/index'
                });
            },
            toIndex() {
                uni.switchTab({
                    url: "/pages/tabbar/index/index"
                })
            },
            checkboxChange(e) {
                this.checkboxValue = !this.checkboxValue;
                console.log(this.checkboxValue);
            },
            getTeamDonDetail() {
                if (this.id != '' && this.id != null) {
                    request.http({
                        url: "/app/don/getteamDonDetail",
                        data: {
                            id: this.id,
                        },
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                res.data.data.donProject.name  = util.strcharacterDiscode(res.data.data.donProject.name);
                                this.donProject = res.data.data.donProject;
                                if (null != res.data.data.donProject.defaultMoney && '' != res.data.data.donProject
                                    .defaultMoney) {
                                    this.defaultMoney = res.data.data.donProject.defaultMoney.split(",");
                                    this.money = this.defaultMoney[0]
                                } else {
                                    this.money = 0;
                                }
                                this.togetherDonState = res.data.data.status;
                                this.gotMoney = null != res.data.data.gotMoney ? res.data.data.gotMoney : 0;
                                this.targetMoney = res.data.data.targetMoney;
                                this.percent = util.toFix((this.gotMoney / this.targetMoney) * 100);
                                this.title = res.data.data.name;
                                this.pic = res.data.data.smSchoolmate.truePhoto;
                                this.startdate = res.data.data.startdate;
                                this.enddate = res.data.data.enddate;
                                this.donatingNum = res.data.data.personNum;
                                this.name = res.data.data.smSchoolmate.name;
                                this.content = null != res.data.data.summary ? res.data.data.summary : "";
                            }
                        },
                        fail: (data, code) => {
                            console.log(data);
                        }
                    })
                } else {
                    this.getDonDetail(this.projectId);
                    this.pic = this.userInfo.truePhoto;
                    this.gotMoney = 0;
                    this.donatingNum = 0;
                    this.name = this.userInfo.name;
                }
            },
            //捐献项目详情
            getDonDetail(projectId) {
                request.http({
                    url: "/app/don/getDonProDetail",
                    data: {
                        id: projectId,
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            res.data.data.name = util.strcharacterDiscode(res.data.data.name);
                            this.donProject = res.data.data;
                            if (null != res.data.data.defaultMoney && '' != res.data.data
                                .defaultMoney) {
                                this.defaultMoney = res.data.data.defaultMoney.split(",");
                                this.money = this.defaultMoney[0]
                            } else {
                                this.money = 0;
                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    }
                })
            },
            howMuch(index) {
                //console.log(e.currentTarget.id);
                this.cur = index;
                // this.money =detail;
                if (index == -1) {
                    this.money = 0;
                } else {
                    this.money = this.defaultMoney[index];
                    this.money1 = 0;
                }
            },
            togglePopup(type) {
                this.type = type
            },
            goDon() {
                if (this.tab_cur == 1) {
                    this.onClickItem(0);
                    return;
                }
                //util.getStatPoint('click', 'EVENT_DESCRIBE_25');
                // this.formId = e.detail.formId;
                if (this.donProject.status == 'WILL') {
                    this.showpageToast("项目为开始，不能回馈");
                    return false;
                }
                if (this.donProject.status == 'HASDONE') {
                    this.showpageToast("项目已结束，不能回馈");
                    return false;
                }
                if (!this.checkboxValue) {
                    this.showpageToast("请同意《法律声明》");
                    return false;
                }
                if (this.money1 == 0) {
                    if (this.money <= 0) {
                        this.showpageToast("请输入正确的回馈金额");
                        return false;
                    }
                } else {
                    if (this.money1 > 0) {
                        this.money = this.money1;
                    } else {
                        this.showpageToast("请输入正确的回馈金额");
                        return false;
                    }
                }
                var reg = /(^[1-9]([0-9]{1,6})?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;

                if (!reg.test(this.money)) {
                    this.showpageToast("请输入正确的回馈金额");
                    return false;
                }
                if (!this.hasAnonymous) {
                    if (this.billName == '') {
                        this.showpageToast("请输入姓名");
                        return false;
                    }
                    if (this.billContact == '') {
                        this.showpageToast("请输入联系方式");
                        return false;
                    }
                    if (!/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test(this.billContact)) {
                        this.showpageToast("请输入正确的手机号");
                        return false;
                    }
                    if (this.isCertificate && !this.isInvoice) {
                        if (this.billTakeAddress == '') {
                            this.showpageToast("请输入收货地址");
                            return false;
                        }
                    }
                    if (this.isInvoice) {
                        if (this.billTitle == '') {
                            this.showpageToast("请输入发票抬头");
                            return false;
                        }
                        if (this.billTakeAddress == '') {
                            this.showpageToast("请输入收货地址");
                            return false;
                        }
                    }

                }

                this.data.money = this.money;

                this.data.isCertificate = this.isCertificate ? 'YES' : 'NO';
                this.data.isShow = !this.hasAnonymous ? 'YES' : 'NO';
                if (!this.hasAnonymous) {
                    this.data.name = this.billName;
                    this.data.phone = this.billContact;
                    this.data.remark = this.identity;
                } else {
                    this.data.name = "匿名";
                }
                this.data.isInvoice = this.isInvoice ? 'YES' : 'NO';
                if (this.isInvoice) {
                    this.data.invoiceTitle = this.billTitle;
                }
                if (this.isCertificate || this.isInvoice) {
                    this.data.address = this.billTakeAddress;
                }

                this.data.donProjectId = this.donProject.id;
                this.data.style = "WEICHAT";
                if (this.id != '' && this.id != null) {
                    this.data.togetherId = this.id;
                }
                this.togetherDonPay();
            },
            togetherDonPay() {
                var that = this;
                request.http({
                    url: "/app/don/save",
                    data: this.data,
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //保存信息成功，调用支付接口	  
                            //调取订单返回参数
                            console.log("保存成功，获取订单信息和吊起支付框")
                            var orderNum = res.data.data.customId;
                            var recordId = res.data.data.id;
                            request.http({
                                url: "/app/weichatpay/createUnifiedOrder",
                                data: {
                                    "orderId": orderNum,
                                    "openid": this.openid,
                                    "notify_url": "/app/weichatpay/notify"
                                },
                                header: 'application/x-www-form-urlencoded',
                                success: (res) => {
                                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                        var payObj = res.data.data.obj;
                                        uni.requestPayment({
                                            provider: 'wxpay',
                                            timeStamp: payObj.timeStamp,
                                            nonceStr: payObj.nonceStr,
                                            package: payObj.package,
                                            signType: payObj.signType,
                                            paySign: payObj.paySign,
                                            success: (res) => {
                                                console.log(this.id);
                                                if (this.id == '' || this.id ==
                                                    null) {
                                                    request.http({
                                                        url: "/app/don/teamsave",
                                                        data: {
                                                            projectId: this
                                                                .projectId,
                                                            name: this.title,
                                                            summary: this.content,
                                                            targetMoney: this
                                                                .targetMoney,
                                                            touser: this.openid,
                                                            form_id: this.formId
                                                        },
                                                        header: 'application/x-www-form-urlencoded',
                                                        success: (res) => {
                                                            this.updateState(
                                                                recordId,
                                                                res
                                                                .data
                                                                .data
                                                                .id
                                                            );
                                                            var url =
                                                                '/pages/don/payResult?id=' +
                                                                encodeURIComponent(
                                                                    recordId
                                                                );
                                                            uni.navigateTo({
                                                                url
                                                            });
                                                            util.getStatPoint(
                                                                'paySuccess',
                                                                'EVENT_DESCRIBE_26'
                                                            );
                                                        },
                                                        fail: (data, code) => {
                                                            console.log(
                                                                data
                                                            );
                                                        }
                                                    });
                                                    if(!this.isShare){
                                                        let version = wx.getSystemInfoSync().SDKVersion;
                                                        if (util.compareVersion(version, '2.8.2') >= 0) {
                                                            wx.requestSubscribeMessage({
                                                              tmplIds: [this.template_shjg],
                                                              success (res) {
                                                                  console.log(res);
                                                              },
                                                              error(res){
                                                                  console.log(res);
                                                              }
                                                            })
                                                        }

                                                    }
                                                } else {
                                                    this.updateState(recordId, this
                                                        .id);
                                                    var url =
                                                        '/pages/don/payResult?id=' +
                                                        encodeURIComponent(recordId);
                                                    uni.navigateTo({
                                                        url
                                                    });
                                                }
                                            },
                                            fail: function(err) {
                                                console.log('fail:' + JSON.stringify(
                                                    err));
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

                        } else {
                            this.disabled = false
                        }
                    },
                    fail: () => {
                        this.disabled = false
                        this.showpageToast("回馈失败")
                    }
                })
            },
            updateState(recordId, togetherDonId) {
                request.http({
                    url: "/app/don/updateState",
                    data: {
                        "id": recordId,
                        "state": "NORMAL",
                        "togetherId": togetherDonId
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log("修改状态成功");
                        }
                    }
                })
            },
            saveTogeDonAndPay(callback) {
                request.http({
                    url: "/app/don/teamsave",
                    data: {
                        projectId: this.projectId,
                        name: this.title,
                        summary: this.content,
                        targetMoney: this.targetMoney
                    },
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        this.togetherDonId = res.data.data.id;
                        callback();
                    },
                    fail: (data, code) => {
                        console.log(data);
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
            certificate(e) {
                this.isCertificate = e.target.value;
            },
            anonymousChange(e) {
                this.hasAnonymous = e.target.value;
            },
            billsChange(e) {
                this.isInvoice = e.target.value;
            },
            onClickItem(index) {
                if (this.tab_cur !== index) {
                    this.tab_cur = index;
                }
                if (this.tab_cur == 1 && this.donPersons.length == 0) {
                    this.getDonPersons();
                }
            },
            getDonPersons() { //获取捐赠人数
                if (this.id != null && this.id != '') {
                    this.pages = this.pages + 1;
                    request.http({
                        url: "/app/don/getDonRecordByPro",
                        data: {
                            pageNo: this.pages,
                            pageSize: this.pageSize,
                            donProjectId: this.donProject.id,
                            togetherId: this.id
                        },
                        header: 'application/x-www-form-urlencoded',
                        success: (res) => {
                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                var datas = res.data.list;
                                datas.forEach((d) => {
                                    this.donPersons.push(d);
                                });
                                this.moreflag = (res.data.pages > (this.pages) ? true : false);
                                if (this.moreflag) {
                                    this.status = "noMore";
                                }
                            }
                        },
                        fail: (data, code) => {
                            console.log(data);
                        }
                    })
                }

            },
            goDetail(detail) {
                var url = '/pages_mine/myAssociation/memberDetail?id=' + encodeURIComponent(JSON.stringify(detail.userId));
                if (!this.hasLogin) {
                    uni.showToast({
                        icon: 'none',
                        title: '您没有登录，请先登录！'
                    });
                } else if (this.userInfo.cardStatus != 'NORMAL') {
                    uni.showToast({
                        icon: 'none',
                        title: '您个人信息没有审核通过，请先去认证！'
                    });
                } else if (detail.userId == undefined) {
                    return;
                } else if (detail.isShow != app.is_str.is_no) {
                    uni.navigateTo({
                        url
                    });
                }
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
        }
    }
</script>

<style lang="scss">
	.act_detail{
		padding: 0 40upx;
		.act_head {
		    padding: 40upx;
		    box-shadow: 0px 0px 30px 0px rgba(116, 116, 116, 0.15);
		    border-radius: 20upx;
		    margin: 25upx auto;
		}
		.rs {
		    padding: 45upx 50upx 55upx 50upx;
		    overflow: hidden;
		}
	}

    progress {
        width: 600upx;
        height: 12upx;
        color: #999999;
        margin-left: 20upx;
        margin-top: -15upx;
        border-radius: 6upx !important;
    }

    .act_detail .rs{
		.rs_1 {
		    width: 168upx;
		    text-align: center;
		    float: left;
		}
		.rs_2 {
		    margin-left: 43upx;
		}
	} 

    .act_detail{
		.member {
		    font-size: 40upx;
		    color: #c79f63;
		    font-weight: bold;
		}
		.money {
		    font-size: $uni-font-size-sm;
		    color: #999999;
		    margin-top: 20upx;
			.active{
				&:before{
					position: absolute;
					content: '';
					right: 0;
					bottom: 0;
					border-bottom: 40upx solid #E2B352;
					border-left: 55upx solid transparent;
					border-right: 0upx solid transparent;
				}
				&:after{
					position: absolute;
					content: '\221A';
					right: 4upx;
					bottom: -28upx;
					color: #FFF;font-size: $uni-font-size-sm;
				}
			}
		}
		.title {
		    font-size: $uni-font-size-lg;
		    overflow: hidden;
			text {
			    // font-size: 36upx;
			    float: left;
			    margin-left: 20upx;
			    margin-top: 34upx;
			}
			.jx {
			    width: 9upx;
			    height: 40upx;
			    background-color: #deb051;
			    border-radius: 4upx;
			    float: left;
			    margin-left: 45upx;
			    margin-top: 45upx;
			}
		}
	} 

   


    .content .sch_cont .bm {
        width: 545upx;
        top: 42upx;
    }


    .act_detail{
		.tx{margin-top: 30upx;
        overflow: hidden;
			.tu_2 {
			    width: 40upx;
			    height: 40upx;
			    border-radius: 50%;float: left;
				image {
				    max-width: 100%;
				    max-height: 100%;
				    border-radius: 50%;
				}
			}
			.name {
			    font-size: $uni-font-size-sm;
			    margin-left: 15upx;width: calc(100% - 55upx);float: left;
			
			}
		} 
	} 


    .intro {
        width: 600upx;
        font-size: $uni-font-size-sm;
        letter-spacing: 1upx;
        color: #999;
        padding: 20upx 0 10upx;
    }

    .act_head .bt {
        font-size: $uni-font-size-base;
        color: #333333;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        font-weight: bold;margin-top: 20rpx;

       
    }

    .act_footer .act_left{width: 100%;
		button {
		    // width: 50%;
		    background-color: white;
		}
	} 

    .act_footer .act_right{width: 100%;
		button {
		    // width: 50%;
		    background-color: #262F43;
		    color: #FFFFFF;
		}
	} 
    .act_footer .act_left_share {
        width: 100%;
    }
    .act_footer .act_left_share button {
        background-color: white;
    }
    .act_footer .act_center_share{
        width: 100%;
    }
    .act_footer .act_right_share{
        width: 100%;
    }
    .act_footer .act_center_share button {
        background-color: #525764;
        color: #FFFFFF;
    }

    .act_footer .act_right_share button {
        // width: 34%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_footer .act_left_index button {
        width: 100%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .target {
        width: 626upx;
        font-size: $uni-font-size-sm;
        color: #999999;
        margin-left: 40upx;
        overflow: hidden;
        letter-spacing: 1upx;
        padding-bottom: 30upx;
    }

    .target text {
        font-size: 30upx;
        color: #c79f63;
        font-weight: bold;
        margin-left: 5upx;
    }

    .target .yj {
        float: left;
        font-size: $uni-font-size-sm;
    }

    .target .people {
        float: right;
        font-size: $uni-font-size-sm;
    }

    .people text {
        font-size: 30upx;
    }

    .my {
        padding: 25upx 30upx;
        font-size: 30upx;
    }

    .targ {
        // padding-bottom: 35upx;
		.qt {
		    width: 100%;
		    height: 88upx;
		    border: solid 1px #eaeaea;
		    margin: 10upx 0;
			text:first-child {
			    color: #444444;
			    float: left;
			    line-height: 88upx;
			    padding-left: 30upx;
			}
			text:last-child {
			    color: #d1d1d1;
			    float: right;
			    padding-right: 40upx;
			    line-height: 88upx;
			}
		}
		
    }

   

    .mon_1 {
        width: calc(25% - 20upx);
        height: 88upx;
        border: 1upx solid #eaeaea;
        float: left;
        text-align: center;
        line-height: 88upx;
        font-size: 28upx;
        color: #444444;
        font-weight: bold;
        position: relative;
        margin-bottom: 10upx;margin-left: 8upx;margin-right: 8upx;
    }

    .money {
		margin:0 -8upx;
	}

    .money .active {
        color: #E2B352;
        border: 1upx solid #E2B352;
    }


    .theme {
        height: 68upx;
        line-height: 68upx;
        background-color: #eaeaea;
        font-size: 30upx;
        padding: 0 30upx;
    }

    textarea {
        padding: 0 30upx;
    }

    .uni-list-cell-pd {
        padding: 25upx 0;
    }

    .fade {
        padding-bottom: 100upx;
    }

    .checkbox {
        margin: 40upx 0;
        font-size: 26upx;
        color: #9b9b9b;
        overflow: hidden;
    }

    .checkbox label {
        float: left;
    }

    .checkbox view {
        float: left;
        color: #6a7fa6;
    }
    .cmd-cell-item-right {
        font-size: 30upx !important;
        color: #999999 !important;
    }

    .cmd-cell-item-body {
        padding-right: 35upx !important;
        margin-left: 35upx !important;
    }

    .btn_dark {
        width: 100%;
        height: 98upx;
        line-height: 98upx;
        position: fixed;
        bottom: 0;
        border-radius: 0;
        font-size: 30upx;
        z-index: 888;
    }

    .qt input {
        float: right;
        padding-right: 40upx;
        line-height: 88upx;
        text-align: right;
       height: 88upx;
    }

    .act_con {
        padding-bottom: 110upx;
    }

    .content_view {
        background-color: #FFFFFF;
        margin-bottom: 20upx;
    }

    .content_view_item {
        clear: both;
        height: 80upx;
        padding-top: 10upx;
        padding-bottom: 10upx;
        border-bottom: 1upx solid #e6e6e6;
    }

    .text_reg {
        position: relative;
        top: 20upx;
        left: 5upx;
    }

    .input_reg {
        width: 60% !important;
        float: right;
        margin-top: 18upx;
        text-align: right;
        margin-right: 39upx;

    }

    .uni-common-mt {
        margin-top: 60upx;
    }

    .progress-box {
        border-radius: 10upx;
    }

    .padd {
        
    }

    .uni-popup {
        width: 460upx;
        height: 600upx;
        padding: 0 !important;
        border-radius: 20upx !important;
        position: fixed !important;
        top: 50% !important;
    }

    .card_bg {
        border-radius: 20upx 20upx 0 0;
        width: 100% !important;
        bottom: 0;
        right: 0;
        position: absolute;
        height: 100%;
        top: 0;
        left: 0;
        z-index: -1;
    }

    .state_title {
        color: #fff;
        // font-size: 30upx;
        margin-top: 55upx;
        text-align: center;
    }

    .state_content {
        width: 90%;
        height: 60%;
        margin: 0 auto;
        border: 1px solid #fff;
        overflow: auto;
        color: #fff;
        font-size: $uni-font-size-sm;
        padding: 10upx;
    }

    .state_btn {
        width: 155upx;
        background: rgb(39, 45, 61) !important;
        height: 50upx;
        line-height: 50upx;
        font-size: $uni-font-size-sm;
        margin-top: 20upx;
        border-radius: 75upx;
    }

    .flex-item {
        width: 50%;
        height: 100upx;
        text-align: center;
        line-height: 100upx;
        font-size: $uni-font-size-lg;
    }

    .flex-item text {
        height: 96rpx;
        display: inline-block;
    }

    .on {
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: $uni-font-size-lg;
    }

    .act_detail .part {
        position: relative;
    }

    .act_detail .apply {
        position: absolute;
        right: 25upx;
        top: 45upx;
        font-size: $uni-font-size-sm;
        color: #999999;
    }

    .content .activity {
        padding: 0;
        margin: 30upx;
        padding-bottom: 30upx;
        border-bottom: 1upx solid #EAEAEA;
    }

    .content .activity .sch_tu {
        width: 98upx;
        height: 98upx;
        border-radius: 50%;
        margin-right: 20upx;
    }

    .content .activity .sch_tu image {
        border-radius: 50%;
    }

    .content .activity .sch_cont {
        width: 563upx;
        float: left;
        margin-left: 30upx;
        margin-top: 0;
    }

    .content .sch_cont .bm {
        width: 545upx;
        top: 42upx;
    }

    // .activity .tx {
    //     float: left;
    // }

    .activity .name {
        margin-top: 20upx;
        width: 25%;
        float: left;
    }

    .activity .target_1 {
        float: left;
        font-size: $uni-font-size-sm;
        width: 30%;
        color: #999999;
        margin-top: 26upx;
    }

    .activity .date {
        font-size: $uni-font-size-sm;
        color: #999999;
        float: left;
        margin-top: 26upx;
    }

    .tag {
        width: 626upx;
        font-size: $uni-font-size-sm;
        color: #999999;
        margin-left: 40upx;
        overflow: hidden;
        letter-spacing: 1upx;
    }

    .tag text {
        font-size: 30upx;
        color: #c79f63;
        font-weight: bold;
    }

    .tag .yj {
        float: left;
    }

    .tag .people {
        float: right;
    }

    .act_detail .apply {
        position: absolute;
        right: 25upx;
        top: 45upx;
        font-size: $uni-font-size-sm;
        color: #999999;
    }

    .activity .tx {
        float: left;
    }

    .activity .name {
        margin-top: 20upx;
        width: 25%;
        float: left;
    }

    .activity .date {
        font-size: $uni-font-size-sm;
        color: #999999;
        float: left;
        margin-top: 26upx;
    }

    .uni-tab-bar-loading {
        text-align: center;
        padding: 35upx 0;
        font-size: 30upx;
        color: #CCCCCC;
    }

    .act_con {
        padding-bottom: 110upx;
    }

    .act_detail .act_footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        height: 98upx;
        border-top: 1px solid #262f43;
        z-index: 888;
        display: flex;
        justify-content: space-around;
        left: 0;
    }

    .tag {
        padding-bottom: 30upx;
    }

    .act_left1 button {
        width: 50%;
        background-color: white;
    }

    .act_right1 button {
        width: 50%;
        background-color: #262F43;
        color: #FFFFFF;
    }

    .act_footer button:after {
        border-radius: 0;
    }

    .share_footer button:after {
        border-radius: 0;
    }

    .index_footer button:after {
        border-radius: 0;
    }


    .data {
        height: 100upx;
        border-bottom: 1upx solid #f6f6f6;
        padding-left: 30upx;
    }

    .data text {
        height: 67upx;
        display: inline-block;
        border-bottom: 6upx solid #272D3D;
        color: #333;
        font-weight: bold;
        font-size: 34upx;
        margin-top: 28upx;
    }
    .content .title {
        .content_wb_jx{position: relative;font-size: $uni-font-size-lg;padding-left: 22upx;display: -webkit-box;font-weight: bold;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        overflow: hidden;
        			&:before{
        				position: absolute;content:'';width: 9upx;height: 28upx;background-color: #deb051;border-radius: 4upx;top: 12upx;left: 0;
        			}
        			
        		}
    }
    .act_footer button {
        width: 100%;float:initial;
    }
    .placeholderStyle{color: #ccc;}
    .uni-popup-middle{
        top: 600upx !important;
    }
</style>
