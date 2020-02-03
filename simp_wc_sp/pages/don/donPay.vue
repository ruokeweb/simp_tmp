<template>
    <!-- 普通捐赠支付页 -->
    <view class="banner">
        <image class="banner_image" src="/static/img/jbanner.png" mode=""></image>
        <view class="pay_title">
            饮水思源 感恩有你
        </view>
        <view class="info">
            这些钱，我们将根据您的意愿，用好每一分钱，支持{{school_name}}的发展
        </view>
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">回馈项目</text>
                <picker class="input_reg" @change="bindPickerChange" range-key="name" :value="projectindex" :range="projectList">
                    <view :class="projectList[projectindex].id=='' ?'uni-input-def don-pro-name':'uni-input don-pro-name'">{{projectList[projectindex].name}}</view>
                </picker>
            </view>
			<view class="jz_money">
				<text class="text_reg">回馈金额</text>
				<view class="clearfix">
					<view v-for="(value, key) in defaultMoney" :key="key" :class="cur==key? 'mon_1 active' : 'mon_1' "
					    @click="howMuch(key)">
					    <text>{{value}}</text>元
<!-- 					    <image v-show="cur==key" src="/static/xz.png" mode=""></image> -->
					</view>
				</view>
				
			</view>
			<view class="jz_money clearfix">
				<text class="qt_text_reg">其他金额</text>
				<input class="qt_input" @click="howMuch(-1)" v-model.trim="money1" type="digit" placeholder="请输入金额" placeholder-class="placeholderStyle" maxlength="10" ></input>
			</view>
		</view>
        
        

        <view class="theme">
            回馈信息
        </view>
        <view class="fade">
            <cmd-transition name="fade-up">
				<view class="content_view">
					<view class="content_view_item">
					    <view class="uni-list-cell-db">匿名回馈</view>
					    <switch class="switch" @change="anonymousChange" :checked="hasAnonymous" />
					</view>
				</view>               
                <view v-show="!hasAnonymous" class="content_view">
                    <view class="content_view_item">
                        <text class="text_reg">姓名</text>
                        <input class="input_reg" style="width:70%" placeholder="请输入姓名" placeholder-class="placeholderStyle"
                            v-model.trim="billName" data-key="billName" maxlength=30 />
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg">联系方式</text>
                        <input class="input_reg" style="width:70%" placeholder="请输入联系方式" placeholder-class="placeholderStyle"
                            v-model.trim="billContact" data-key="billContact" maxlength=30 />
                    </view>
                    <view class="content_view_item">
                        <view class="uni-list-cell-db">是否需要纸质回馈证书</view>
                        <switch class="switch" @change="certificate" :checked="certificate" />
                    </view>
                    <view class="content_view_item">
                        <view class="uni-list-cell-db">是否需要发票</view>
                        <switch class="switch" @change="billsChange" :checked="billsChange" />
                    </view>
                    <view class="content_view_item" v-show="isCertificate || isInvoice">
                        <text class="text_reg">邮寄地址</text>
                        <input class="input_reg" style="width:70%" placeholder="请输入邮寄" placeholder-class="placeholderStyle"
                            v-model.trim="billTakeAddress" data-key="billTakeAddress" maxlength=30 />
                    </view>
                    <view class="content_view_item" v-show="isInvoice">
                            <text class="text_reg">发票抬头</text>
                            <input class="input_reg" style="width:70%" placeholder="请输入发票抬头" placeholder-class="placeholderStyle"
                                v-model.trim="billTitle" data-key="billTitle" maxlength=30 />
                        </view>
                        <!-- <view class="content_view_item">
                        <text class="text_reg">收货地址</text>
                        <input class="input_reg" style="width:30%" placeholder="请输入" placeholder-style="color:#ccc;" v-model.trim="billTakeAddress" data-key="billTakeAddress" maxlength=30 />
                    </view> -->
					<view class="content_view_label clearfix">
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

        <!-- 弹出页面 -->
        <uni-popup :show="type === 'middle-con'" position="middle" mode="fixed" @hidePopup="togglePopup('')">
            <view class="" style="width:400upx;height:600upx;">
                <image class="card_bg" src="/static/statement-bg.jpg" style="" mode="aspectFill"></image>
                <view class="state_title">法律声明</view>
                <view class="state_content">
                    <uParse v-if="content" :content="content" @preview="preview" @navigate="navigate" />
                </view>
                <button type="primary" class="state_btn" @click="togglePopup('')">我知道了</button>
            </view>
        </uni-popup>

        <button type="primary" class="btn_dark" @click="goDon">回馈母校</button>
    </view>
</template>

<script>
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js';
    import request from '@/common/request.js';
    import '@/static/css/index.css';
    import cmdNavBar from "@/components/cmd-nav-bar/cmd-nav-bar.vue"
    import cmdPageBody from "@/components/cmd-page-body/cmd-page-body.vue"
    import cmdTransition from "@/components/cmd-transition/cmd-transition.vue"
    import cmdCelItem from "@/components/cmd-cell-item/cmd-cell-item.vue"
    import uniPopup from '@/components/uni-popup/uni-popup.vue';
    import uParse from '@/components/uParse/src/wxParse.vue'
    // import 'common/uni.css'
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
            uniPopup
        },
        data() {
            return {
                code: "",
                openid: "",
                projectId: "",
                percent: 60,
                loadingText: '',
                money: 1,
                cur: 0,
                isCertificate: false, //证书 
                hasAnonymous: false,
                billName: '',
                billContact: '', //联系方式
                identity: '', //身份
                isInvoice: false,
                billTakeAddress: '',
                billTitle: '',
                unitAddress: '',
                data: {},
                money1: 0,
                checkboxValue: true,
                defaultMoney: [],
                school_name: "",
                type: '',
                content: '',
                donProName: '',
                projectList: [{name:'请选择回馈项目',id:''}],
                projectindex: 0
            }
        },
        onLoad(options) {},
        onShow() {
            this.school_name = this.configParams.school_name;  
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
                                        this.showpageToast("回馈失败")
                                    }
                                })

                            }
                        });
                    }
                }
            });
            //判断是否登录，登录则获取个人信息
            if (this.hasLogin) {
                let userInfo = this.userInfo
                let contact = userInfo.smContact;
                this.billName = userInfo.name;
                this.billContact = null != contact ? contact.contact : this.userInfo.sysUser.username;
            }
            /* 获取捐赠项目*/
            this.projectList = [{
                name: '请选择回馈项目',
                id: ''
            }];
            this.getDonProList();

        },
        computed: {
            ...mapState(['hasLogin','configParams','userInfo'])
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
                            this.content = util.strcharacterDiscode(res.data.data);
                        }
                    },
                    fail: (res) => {

                    }
                })
            },
            checkboxChange(e) {
                this.checkboxValue = !this.checkboxValue;
            },
            goDonPro(detail) {
                var url = '/pages/don/donDetail?id=' + encodeURIComponent(JSON.stringify(detail));
                uni.navigateTo({
                    url
                });
            },
            goImmediateDon(detail) {
                var url = '/pages/don/ImmediateDon?id=' + encodeURIComponent(JSON.stringify(detail));
                uni.navigateTo({
                    url
                });
            },
            howMuch(index) {
                this.cur = index;
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
                //util.getStatPoint('click', 'EVENT_DESCRIBE_28');
                if (this.projectId == null | this.projectId == '') {
                    this.showpageToast("请选择回馈项目");
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
                    if (this.hisCertificate && !this.isInvoice) {
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

                if (!this.checkboxValue) {
                    this.showpageToast("请同意法律声明");
                    return false;
                }
                this.data.money = this.money;
                this.data.isShow = !this.hasAnonymous ? 'YES' : 'NO';
                this.data.isCertificate = this.isCertificate ? 'YES' : 'NO';
                this.data.hasAnonymous = this.hasAnonymous;
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

                this.data.donProjectId = this.projectId;
                // this.data.state = "WAITING";
                this.data.style = "WEICHAT";
                request.http({
                    url: "/app/don/save",
                    data: this.data,
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        console.log(1111)
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
                                        console.log(payObj);
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
                                                        "id": recordId,
                                                        "state": "NORMAL",
                                                    },
                                                    header: 'application/x-www-form-urlencoded',
                                                    success: (res) => {
                                                        if (res.data.code ==
                                                            app.RESPONSE_STATUS
                                                            .REQUEST_SUCCESS
                                                        ) {
                                                            console.log(
                                                                "修改状态成功"
                                                            );
                                                        }
                                                    }

                                                });
                                                //util.getStatPoint('paySuccess','EVENT_DESCRIBE_29');
                                                //支付成功跳转到证书也
                                                this.showpageToast("回馈成功");
                                                var url =
                                                    '/pages/don/payResult?id=' +
                                                    encodeURIComponent(recordId);
                                                uni.navigateTo({
                                                    url
                                                });
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
                        } else {}
                    },
                    fail: () => {
                        this.showpageToast("回馈失败")
                    }
                })
            },
            getDonProList() {
                request.http({
                    // url: app.serverPath + "/don/teamDonList.json",
                    url: "/app/don/getAllDonPro",
                    data: {},
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            for (var i in res.data.data) {
                                res.data.data[i].name = util.strcharacterDiscode(res.data.data[i].name);
                                this.projectList.push(res.data.data[i]);
                            }
                            //= this.projectList.concat(res.data.data);
                            this.projectId = this.projectList[this.projectindex].id
                            this.donProName = this.projectList[this.projectindex].name;
                            let defaultMoney = this.projectList[this.projectindex].defaultMoney;
                            if (null != defaultMoney && '' != defaultMoney) {
                                this.defaultMoney = defaultMoney.split(",");
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
            bindPickerChange(e) {
                this.cur = 0;
                this.projectindex = e.target.value
                this.projectId = this.projectList[this.projectindex].id;
                this.donProName = this.projectList[this.projectindex].name;
                let defaultMoney = this.projectList[this.projectindex].defaultMoney;
                this.defaultMoney = [];
                this.money = 0;
                if (null != defaultMoney && '' != defaultMoney && undefined != defaultMoney) {
                    this.defaultMoney = defaultMoney.split(",");
                    this.money = this.defaultMoney[0]
                } else {
                    this.money = 0;
                }
                console.log(this.money);

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
	.content_view{padding-left: 20upx;padding-right: 20upx;}
    .banner_image {
        width: 100vw;
        height: 265upx;
        margin-top: 190upx;
    }

    .pay_title {
        width: 390upx;
        font-size: 46upx;
        color: #e7a41b;
        position: absolute;
        left: 185upx;
        top: 32upx;
        font-weight: bold;
    }
.jz_money{padding: 10upx 0;
.text_reg{display: inline-block;margin-bottom: 10upx;}
	.qt_text_reg{float: left;margin-bottom: 20upx;
}
	.qt_input{float: right;text-align: right;}
	
}
    .info {
        width: 600upx;
        color: #666666;
        font-weight: bold;
        position: absolute;
        top: 125upx;
        left: 80upx;
        text-align: center;
        letter-spacing: 1upx;
    }

    .targ {
        padding-bottom: 35upx;
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
        margin-bottom: 10upx;
        margin-left: 8upx;
        margin-right: 8upx;
		&.active{
			color: #E2B352;
			border: 1upx solid #E2B352;
			&:before {
				position: absolute;
				content: '';
				right: 0;
				bottom: 0;
				border-bottom: 40upx solid #E2B352;
				border-left: 55upx solid transparent;
				border-right: 0upx solid transparent;			
			}
			&:after {
				position: absolute;
				content: '\221A';
				right: 4upx;
				bottom: -28upx;
				color: #FFF;
				font-size: 24upx;			
			}
		}
    }

    

    
    .qt {
        width: 690upx;
        height: 88upx;
        border: solid 1px #eaeaea;
        margin: 0 auto;
    }

    .qt input {
        float: right;
        padding-right: 40upx;
        line-height: 88upx;
        text-align: right;
        margin-top: 20upx;
    }

    .qt text:first-child {
        color: #444444;
        float: left;
        line-height: 88upx;
        padding-left: 30upx;
    }

    .qt text:last-child {
        color: #d1d1d1;
        float: right;
        padding-right: 40upx;
        line-height: 88upx;
    }

    .theme {
        height: 68upx;
        line-height: 68upx;
        background-color: #eaeaea;
        padding: 0 20upx;
    }

    textarea {
        padding: 0 30upx;
    }

    .uni-list-cell::after {
        background-color: #EAEAEA;
    }

    .uni-list-cell-pd {
       
    }

    .fade {
        padding-bottom: 100upx;
    }

    .checkbox {
        // margin: 40upx 30upx;
        font-size: $uni-font-size-sm;
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
    
        color: #999999;
    }

    .cmd-cell-item-body {
        padding-right: 35upx;
        margin-left: 35upx;
    }

    .btn_dark {
        width: 100%;
        height: 98upx;
        line-height: 98upx;
        position: fixed;
        bottom: 0;
        border-radius: 0;
   
        z-index: 888;
    }

    .content_view {
        
    }

    .content_view_item {
        padding-top: 25upx;
        padding-bottom: 25upx;
		position: relative;display: flex;align-items: center;justify-content: space-between;position: relative;
      		&:after{
      			position: absolute;
      			z-index: 3;
      			right: 0;
      			bottom: 0;
      			left: 0;
      			height: 1px;
      			content: '';
      			-webkit-transform: scaleY(.5);
      			transform: scaleY(.5);
      			background-color: #c8c7cc;
      		}
			input{text-align: right;}
    }
.content_view_label{
	padding-top: 25upx;
	padding-bottom: 25upx;
	label{float: left;}
	view {float: left;color: #6a7fa6;

}

}
    .text_reg {
       
    }

    .input_reg {
        text-align: right;
    }




    .card_bg {
        border-radius: 20upx 20upx 0 0;
        width: 100% !important;
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        height: 100%;
        z-index: -1;
    }

    .state_title {
        color: #fff;

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
        .uni-input-def {
            
            background: #FFF;
            color:#cccccc ;
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
        }
        .don-pro-name {
            overflow: hidden;
            text-overflow:ellipsis;
            white-space: nowrap;
            width: 393rpx;
        }
        .uni-input{
            height: auto;
            padding: 0;
            line-height: auto;
            font-size: 28upx;
            background: #FFF;
            flex:none ;
        }
     .placeholderStyle{color: #ccc;}
     .uni-popup-middle{
         top: 800upx !important;
     }
</style>
