<template>
    <!-- 认证信息 -->
    <view class="content">
        <view>
            <view style="margin-bottom:20upx;">
                <view v-if="userId !=''" class="action-row-center">
                    <image class="ring" src="../../static/login/ring.png"></image> 请您核对信息
                </view>
                <view v-if="userId ==''" class="action-row-center">
                    <image class="ring" src="../../static/login/ring.png"></image>
                    请您填写您的信息进行认证,准确的信息有助于提高认证通过率
                </view>
            </view>
            <!--身份选择-->
            <view class="waikuang">
                <view class="selectId">选择您的身份</view>
                <view class="idassort">
                    <view v-for="(value, key) in schoolmateTypes" :key="key" :class="cur==key? 'mon_1 mon_2  active' : 'mon_1 mon_2' "
                        @click="whatTypes(key)">
                        <view class="indexImg">
                            <image :src='cur==key? value.indexImgIn : value.indexImg'></image>
                        </view>
                        <text :class="cur==key? 'text_font' : '' ">{{value.label}}</text>

                    </view>
                    <view class="clearfix"></view>
                </view>
            </view>
            <view>


                <!--view class="content_view_item">
					<text class="text_reg">身份</text>
					<input class="input_reg" style="width:30%" placeholder="请在下方选择您的身份" disabled="true" />
				</view>
				<view class="content_view_item" style="border: none">
					<view v-for="(value, key) in schoolmateTypes" :key="key" :class="cur==key? 'mon_1 mon_2  active' : 'mon_1 mon_2' "
						@click="whatTypes(key)">
						
						<text :class="cur==key? 'text_font' : '' ">{{value.label}}</text>
					</view>
				</view-->
            </view>
            <!---->



            <view class="waikuang">
                <view class="content_view con_view">
                    <view class="content_view_item">
                        <text class="text_reg icon-s">手机号</text>
                        <input class="input_reg" placeholder="校友卡小程序登录账号" placeholder-class="placeholderStyle" v-model="username"
                            data-key="username" maxlength=11 />
                    </view>
                    <view class="content_view_item" style="position:relative;">
                        <text class="text_reg icon-y">验证码</text>
                        <input class="input_reg" placeholder="请输入验证码" placeholder-class="placeholderStyle" v-model.trim="code" data-key="code"
                            maxlength=6 />
                        <button class="btn-verify" @click="getCode" :disabled="disabled">{{time}}</button>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-m">密码</text>
                        <input class="input_reg" style="width:60%" type="password" placeholder="请输入密码" placeholder-class="placeholderStyle"
                            v-model="password" data-key="password" maxlength=15 />
                    </view>
                </view>
            </view>

            <view class="waikuang">
                <view class="content_view con_view">
                    <view class="content_view_item">
                        <text class="text_reg icon-n">姓名</text>
                        <input class="input_reg" style="width:50%" placeholder="请输入姓名" placeholder-class="placeholderStyle"
                            v-model.trim="name" data-key="name" maxlength=15 />
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-sex">性别</text>
                        <picker class="input_reg" @change="bindPickerChangeSex" range-key="label" :value="sexindex"
                            :range="sexArr">
                            <view class="uni-input">{{sexArr[sexindex].label}}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-id">证件类型</text>
                        <picker class="input_reg" @change="bindPickerChangeCardType" range-key="label" :value="cardindex"
                            :range="cardtypeArr">
                            <view class="uni-input">{{cardtypeArr[cardindex].label}}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-num">证件号码</text>
                        <input class="input_reg" style="width:50%" placeholder="请输入证件号码" placeholder-class="placeholderStyle"
                            v-model.trim="cardNum" data-key="cardNum" maxlength=30 />
                    </view>
                </view>
            </view>
            <view class="waikuang" v-if="schoolmateType != alumni_teacher ">
                <view class="content_view">
                    <view class="content_view_item">
                        <text class="text_reg icon-data">入学年份</text>
                        <picker class="input_reg" @change="bindPickerChangeDate" fields='year' start='1964' end='2019'
                            mode="date">
                            <view class="uni-input">{{startdate}}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-xueli">学历</text>
                        <picker class="input_reg" @change="bindPickerChangeEduRecord" range-key="label" :value="eduRecord"
                            :range="eduRecordArr">
                            <view class="uni-input">{{eduRecordArr[eduRecord].label }}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-school">学校</text>
                        <picker class="input_reg" @change="bindPickerChangeSchool" range-key="name" :value="school"
                            :range="schoolArr">
                            <view class="uni-input">{{schoolArr[school].name }}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-xueyuan">学院</text>
                        <picker class="input_reg" @change="bindPickerChangeCollege" range-key="name" :value="college"
                            :range="collegeArr">
                            <view class="uni-input">{{collegeArr[college].name }}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-xi">系</text>
                        <picker class="input_reg" @change="bindPickerChangeSeries" range-key="name" :value="series"
                            :range="seriesArr">
                            <view class="uni-input">{{seriesArr[series].name }}</view>
                        </picker>
                    </view>
                    <view class="content_view_item">
                        <text class="text_reg icon-zy">专业</text>
                        <picker class="input_reg" @change="bindPickerChangeSpecialty" range-key="name" :value="specialty"
                            :range="specialtyArr">
                            <view class="uni-input">{{specialtyArr[specialty].name }}</view>
                        </picker>
                    </view>
                    <!--                <view class="content_view_item"><text class="text_reg">教育经历</text>
				        <multiple-linkage class="input_reg" :showtitle="showTitle" :flist='eduArry' :dialogTitle="dialogTitle"
				            :type="type" @saveclick="savedata" v-if="flag">
				        </multiple-linkage>
				    </view> -->

                    <!--<view class="content_view_item">
				        <text class="text_reg">是否可被检索</text>
				        <switch class="switch" @change="switchChange" :checked="checked" />
				    </view>-->
                </view>

            </view>
            <view class="waikuang" v-if="schoolmateType == alumni_teacher">
                <view class="content_view">
                    <view class="content_view_item">
                        <text class="text_reg icon-danwei">单位</text>
                        <input class="input_reg" style="width:50%" placeholder="请输入单位" placeholder-class="placeholderStyle"
                            v-model.trim="workplace" data-key="workplace" maxlength=64 />
                    </view>
                    <!--<view class="content_view_item">
				        <text class="text_reg">是否可被检索</text>
				        <switch class="switch" @change="switchChange" :checked="checked" />
				    </view>-->
                </view>
            </view>
            <!--<view class="content_view">                
                <view class="content_view_item">
                    <text class="text_reg">照片</text>
                    <view class="input_reg">
                        <image v-if="truePhoto != null || truePhoto != ''" :src="imagePath + truePhoto" class="tis-image"
                            mode="aspectFit" @click="uploadtruePhoto"></image>
                        <image v-if="truePhoto == null || truePhoto == ''" class="tis-image" src="/static/img/man_default.png"
                            mode="aspectFit" @click="uploadtruePhoto"></image>
                    </view>
                </view>
            </view>-->

            <form @submit="regist" report-submit="true">
                <button type="primary" v-if="userId ==''" formType="submit" class="primary btn_dark" :disabled="regDisabled">提交认证</button>
            </form>
            <button type="primary" v-if="userId !=''" class="primary btn_dark" @click="registPass" :disabled="regDisabled">领取校友卡</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import util from '@/common/util.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    import {
        mapMutations,mapState
    } from 'vuex';
    export default {
        components: {
            multipleLinkage
        },
        data() {
            return {
                userInfo: "",
                username: "",
                code: "",
                password: "",
                name: "",
                sex: "",
                cardType: "",
                cardNum: "",
                checked: true,
                imagePath: app.imagePath,
                sexindex: 0,
                sexArr: ['请选择'],
                cardindex: 0,
                cardtypeArr: ['请选择'],
                startdate: 2019,
                startdataArr: ['请选择'],
                schoolArr: [],
                school: 0,
                schoolId: '',
                collegeArr: [],
                college: 0,
                collegeId: '',
                seriesArr: [],
                series: 0,
                seriesId: '',
                specialtyArr: [],
                specialty: 0,
                specialtyId: '',
                eduRecord: 0,
                eduRecordArr: ['请选择'],
                time: '获取验证码', //倒计时
                showTitle: "请选择教育经历",
                dialogTitle: "请选择教育经历",
                interval: null, //定时器
                currentTime: 61,
                disabled: false,
                regDisabled: false,
                eduArry: {},
                flag: false,
                WxCode: "",
                openid: "",
                schoolmateTypes: [],
                cur: 0,
                schoolmateType: '',
                workplace: '',
                smEducation: {},
                smProfession: {},
                userId: '',
                form_id: '',
                alumni_teacher: app.alumni_type.teacher,
                truePhoto: null ,
                template_zcsh: ''
            }
        },
        computed: {
            ...mapState(['configParams'])
        },
        methods: {
            ...mapMutations(['login', 'setUserInfo']),
            whatTypes(val) {
                this.cur = val;
                this.schoolmateType = this.schoolmateTypes[this.cur].value;
            },
            switchChange(e) {
                this.checked = e.target.value;
            },
            bindPickerChangeSex(e) {
                this.sexindex = e.target.value
            },
            bindPickerChangeEduRecord(e) {
                this.eduRecord = e.target.value;
            },
            bindPickerChangeCardType(e) {
                this.cardindex = e.target.value
            },
            bindPickerChangeDate(e) {
                this.startdate = e.target.value
                console.log(e.target.value);
            },
            //学校
            bindPickerChangeSchool(e) {
                this.school = e.target.value;
                if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs.length !=
                    0) {
                    this.collegeArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs;
                    if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[0].childs
                        .length !=
                        0) {
                        this.seriesArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                            .childs[0].childs;
                        if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                            .childs[0]
                            .childs[0].childs.length !=
                            0) {
                            this.specialtyArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[
                                    this.school]
                                .childs[0]
                                .childs[0].childs;
                        } else {
                            this.specialtyArr = [];
                        }
                    } else {
                        this.seriesArr = [];
                        this.specialtyArr = [];
                    }
                } else {
                    this.collegeArr = [];
                    this.seriesArr = [];
                    this.specialtyArr = [];
                }
            },
            //学院
            bindPickerChangeCollege(e) {
                this.college = e.target.value;
                // this.collegeId = this.collegeArr[this.college].id;
                if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[this.college]
                    .childs.length !=0) {
                    this.seriesArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                        .childs[this.college].childs;
                    if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                        .childs[this.college]
                        .childs[0].childs.length != 0) {
                        this.specialtyArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[
                                this.school]
                            .childs[this.college]
                            .childs[0].childs;
                    } else {
                        this.specialtyArr = [];
                    }
                } else {
                    this.seriesArr = [];
                    this.specialtyArr = [];
                }
            },
            //系
            bindPickerChangeSeries(e) {
                this.series = e.target.value;
                // this.seriesId = this.seriesArr[this.series].id;
                if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                    .childs[this.college]
                    .childs[this.series].childs.length != 0) {
                    this.specialtyArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[
                            this.school]
                        .childs[this.college]
                        .childs[this.series].childs;
                } else {
                    this.specialtyArr = [];
                }
            },
            //专业
            bindPickerChangeSpecialty(e) {
                this.specialty = e.target.value;
                // this.specialtyId = this.specialtyArr[this.specialty].id;
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            getOpenId() {
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
                                    //获取用户openid
                                    request.http({
                                        url: "/app/pay/getInfo",
                                        data: {
                                            'code': this.WxCode
                                        },
                                        header: 'application/x-www-form-urlencoded',
                                        success: (res) => {
                                            this.openid = res.data.data.openId;
                                        },
                                        fail: () => {}
                                    })

                                }
                            });
                        }
                    }
                });
            },
            regist() {
                this.regDisabled = true
                console.log("提交注册信息");
                if (this.username == "") {
                    this.showpageToast("请输入手机号");
                    this.regDisabled = false
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    this.regDisabled = false
                    return false;
                }
                if (this.code == "") {
                    this.showpageToast("请输入验证码");
                    this.regDisabled = false
                    return false;
                }
                if (this.code.length != 6) {
                    this.showpageToast("请输入正确的验证码");
                    this.regDisabled = false
                    return false;
                }
                if (this.password == "") {
                    this.showpageToast("请输入密码");
                    this.regDisabled = false
                    return false;
                }
                if (this.password.length < 6) {
                    this.showpageToast("密码不能小于6位");
                    this.regDisabled = false
                    return false;
                }
                if (this.name == "") {
                    this.showpageToast("请输入姓名");
                    this.regDisabled = false
                    return false;
                }
                if (this.cardtypeArr[this.cardindex].value == 'IDCARD') {
                    var regCardNum =
                        /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
                    if (!regCardNum.test(this.cardNum)) {
                        this.showpageToast("请输入正确的身份证号");
                        this.regDisabled = false
                        return false;
                    }
                }
                if (this.cardNum == "") {
                    this.showpageToast("请输入证件号");
                    this.regDisabled = false
                    return false;
                }
                if (this.schoolmateType != this.alumni_teacher) {
                    if (this.startdate == "") {
                        this.showpageToast("请选择入学年份");
                        this.regDisabled = false
                        return false;
                    }
                    if (this.schoolArr[this.school].id.length == 0) {
                        this.showpageToast("请选择学校");
                        this.regDisabled = false
                        return false;
                    }
                    if (this.collegeArr[this.college].id.length == 0) {
                        this.showpageToast("请选择学院");
                        this.regDisabled = false
                        return false;
                    }
                } else {
                    if (this.workplace.length == 0) {
                        this.showpageToast("请输入单位");
                        this.regDisabled = false
                        return false;
                    }
                }
                if (this.schoolmateType == this.alumni_teacher) {
                    this.smEducation = null;
                    this.smProfession = {
                        workplace: this.workplace
                    }
                } else {
                    this.smProfession = null;
                    this.smEducation = {
                        startdate: this.startdate + "-01-01",
                        school: this.schoolArr[this.school].id,
                        college: this.collegeArr.length == 0 ? null:this.collegeArr[this.college].id,
                        series: this.seriesArr.length == 0 ? null:this.seriesArr[this.series].id,
                        specialty: this.specialtyArr.length == 0 ? null: this.specialtyArr[this.specialty].id,
                        eduRecord: this.eduRecordArr[this.eduRecord].value
                    }
                }
                console.log(this.smEducation);
                uni.request({
                    url: app.serverPath + '/app/regist',
                    method: "POST",
                    data: {
                        //手机号
                        sysUser: {
                            username: this.username,
                            password: this.password
                        },
                        sysLoginExpand: {
                            expand: this.openid,
                            type: app.login_source
                        },
                        //验证码
                        paramA: this.code,
                        //基本信息
                        name: this.name,
                        truePhoto: this.truePhoto,
                        type: this.schoolmateType,
                        sex: this.sexArr[this.sexindex].value,
                        cardType: this.cardtypeArr[this.cardindex].value,
                        cardNum: this.cardNum,
                        isShow: this.checked,
                        //教育经历
                        smEducation: this.smEducation,
                        smProfession: this.smProfession,
                        weiChatInfo: {
                            nickName: uni.getStorageSync('weixin_nickName'),
                            avatarUrl: null,
                            formId: this.form_id,
                            openId: this.openid
                        }
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            //在此开始进行注册通知
                            let version = wx.getSystemInfoSync().SDKVersion;
                            if (util.compareVersion(version, '2.8.2') >= 0) {
                                wx.requestSubscribeMessage({
                                  tmplIds: [this.template_zcsh],
                                  success (res) {},
                                  error(res){}
                                })
                            }
                            this.login(res.data.data);
                            this.setUserInfo(res.data.data.userInfo);
                            
                            this.showpageToast(res.data.msg);
                            if (res.data.data.pages.length == 0) {
                                uni.reLaunch({
                                    url: "/pages/login/cardstatusAuthing"
                                })
                            } else {
                                uni.reLaunch({
                                    url: res.data.data.pages
                                })
                            }
                        } else {
                            this.showpageToast(res.data.msg);
                            this.disabled = false
                            this.regDisabled = false
                        }
                    },
                    fail: () => {
                        this.disabled = false
                        this.regDisabled = false
                        this.showpageToast("注册失败")
                    }
                })
            },

            registPass() {
                this.regDisabled = true
                //util.getStatPoint('click', 'EVENT_DESCRIBE_7');
                console.log("提交注册信息");
                if (this.username == "") {
                    this.showpageToast("请输入手机号");
                    this.regDisabled = false
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    this.regDisabled = false
                    return false;
                }
                if (this.code == "") {
                    this.showpageToast("请输入验证码");
                    this.regDisabled = false
                    return false;
                }
                if (this.code.length != 6) {
                    this.showpageToast("请输入正确的验证码");
                    this.regDisabled = false
                    return false;
                }
                if (this.password == "") {
                    this.showpageToast("请输入密码");
                    this.regDisabled = false
                    return false;
                }
                if (this.password.length < 6) {
                    this.showpageToast("密码不能小于6位");
                    this.regDisabled = false
                    return false;
                }
                if (this.name == "") {
                    this.showpageToast("请输入姓名");
                    this.regDisabled = false
                    return false;
                }
                if (this.cardtypeArr[this.cardindex].value == 'IDCARD') {
                    var regCardNum =
                        /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
                    if (!regCardNum.test(this.cardNum)) {
                        this.showpageToast("请输入正确的身份证号");
                        this.regDisabled = false
                        return false;
                    }
                }
                if (this.cardNum == "") {
                    this.showpageToast("请输入证件号");
                    this.regDisabled = false
                    return false;
                }
                if (this.schoolmateType != this.alumni_teacher) {
                    if (this.startdate == "") {
                        this.showpageToast("请选择入学年份");
                        this.regDisabled = false
                        return false;
                    }
                    if (this.schoolArr[this.school].id.length == 0) {
                        this.showpageToast("请选择学校");
                        this.regDisabled = false
                        return false;
                    }
                    if (this.collegeArr[this.college].id.length == 0) {
                        this.showpageToast("请选择学院");
                        this.regDisabled = false
                        return false;
                    }
                } else {
                    if (this.workplace.length == 0) {
                        this.showpageToast("请输入单位");
                        this.regDisabled = false
                        return false;
                    }
                }
                if (this.schoolmateType == this.alumni_teacher) {
                    this.smEducation = null;
                    this.smProfession = {
                        id: this.schoolmate.smProfession.id,
                        workplace: this.workplace
                    }
                } else {
                    this.smProfession = null;
                    this.smEducation = {
                        id: this.schoolmate.smEducation.id,
                        startdate: this.startdate + "-01-01",
                        school: this.schoolArr[this.school].id,
                        college: this.collegeArr[this.college].id,
                        series: this.seriesArr[this.series].id,
                        specialty: this.specialtyArr[this.specialty].id,
                        eduRecord: this.eduRecordArr[this.eduRecord].value
                    }
                }
                uni.request({
                    url: app.serverPath + '/app/registFacePass',
                    method: "POST",
                    data: {
                        //手机号
                        id: this.schoolmate.id,
                        sysUser: {
                            id: this.schoolmate.userId,
                            username: this.username,
                            password: this.password
                        },
                        sysLoginExpand: {
                            expand: this.openid,
                            type: app.login_source
                        },
                        //验证码
                        paramA: this.code,
                        //基本信息
                        name: this.name,
                        truePhoto: this.truePhoto,
                        type: this.schoolmateType,
                        sex: this.sexArr[this.sexindex].value,
                        cardType: this.cardtypeArr[this.cardindex].value,
                        cardNum: this.cardNum,
                        isShow: this.checked,
                        //教育经历
                        smEducation: this.smEducation,
                        smProfession: this.smProfession,
                        weiChatInfo: {
                            nickName: uni.getStorageSync('weixin_nickName'),
                            avatarUrl: null
                        }
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.login(res.data.data);
                            this.setUserInfo(res.data.data.userInfo);
                            this.showpageToast(res.data.msg);
                            
                            uni.redirectTo({
                                url: res.data.data.pages
                            })
                        } else {
                            this.showpageToast(res.data.msg);
                            this.disabled = false
                            this.regDisabled = false
                        }
                    },
                    fail: () => {
                        this.disabled = false
                        this.regDisabled = false
                        this.showpageToast("注册失败")
                    }
                })
            },

            getCode() {
                if (this.username == "") {
                    this.showpageToast("请输入手机号");
                    this.regDisabled = false
                    return false;
                }
                let myphone = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                if (!myphone.test(this.username)) {
                    this.showpageToast("请输入正确的手机号");
                    this.regDisabled = false
                    return false;
                }
                this.currentTime = 61;
                this.disabled = true;
                this.interval = setInterval(() => {
                    this.currentTime--;
                    this.time = this.currentTime + '秒'
                    if (this.currentTime <= 0) {
                        clearInterval(this.interval)
                        this.disabled = false;
                        this.time = '重新发送';
                    }
                }, 1000)
                uni.request({
                    url: app.serverPath + "/app/sendCaptCha",
                    method: 'POST',
                    data: {
                        username: this.username,
                    },
                    header: {
                        'content-type': 'application/x-www-form-urlencoded'
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.showpageToast(res.data.msg);
                        } else if (res.data.code == app.RESPONSE_STATUS.DATA_USED) {
                            this.time = '获取验证码',
                                clearInterval(this.interval)
                            this.disabled = false;
                            uni.showModal({
                                title: '提示',
                                content: '该手机已经注册，是否去登录页登录？',
                                success: function(res) {
                                    if (res.confirm) {
                                        let url = '/pages/login/login';
                                        uni.navigateTo({
                                            url: url
                                        });
                                    } else if (res.cancel) {
                                        console.log('重新输入');
                                    }
                                }
                            });
                        } else {
                            this.showpageToast(res.data.msg);
                        }
                    },
                    fail: (error) => {
                        clearInterval(this.interval);
                        this.disabled = false;
                        this.time = '重新发送';
                    }
                });
            },
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
                                    url: app.serverPath + '/file/upload', //仅为示例，非真实的接口地址
                                    filePath: tempFilePaths[0],
                                    name: 'file',
                                    header: {
                                        'content-type': 'multipart/form-data',
                                    },
                                    success: (suc) => {
                                        var result = JSON.parse(suc.data);
                                        console.log(result.code == app.RESPONSE_STATUS
                                            .REQUEST_SUCCESS);
                                        if (result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                            this.truePhoto = result.data;
                                        } else {
                                            uni.showToast({
                                                icon: 'none',
                                                title: result.msg
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

            getInfoByUserId() {
                if (this.userId == ''||this.userId==undefined ||this.userId==null) {
                    return;
                } else {
                    uni.showLoading({
                        title: "加载数据中"
                    })
                    uni.request({
                        url: app.serverPath + '/app/getInfoByUserId',
                        method: "POST",
                        data: {
                            userId: this.userId
                        },
                        header: {
                            'content-type': 'application/x-www-form-urlencoded'
                        },
                        success: (res) => {
                            if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                console.log(res.data.data);
                                this.schoolmate = res.data.data;
                                //回显手机号
                                if (this.schoolmate.sysUser.username.length == 11) {
                                    this.username = this.schoolmate.sysUser.username;
                                }
                                //回显基础信息
                                this.name = this.schoolmate.name;
                                for (var i = 0; i < this.sexArr.length; i++) {
                                    if (this.schoolmate.sex == this.sexArr[i].value) {
                                        this.sexindex = i;
                                    }
                                }
                                for (var i = 0; i < this.cardtypeArr.length; i++) {
                                    if (this.schoolmate.cardType == this.cardtypeArr[i].value) {
                                        this.cardindex = i;
                                    }
                                }
                                this.cardNum = this.schoolmate.cardNum;

                                for (var i = 0; i < this.schoolmateTypes.length; i++) {
                                    if (this.schoolmate.type == this.schoolmateTypes[i].value) {
                                        this.cur = i;
                                    }
                                }

                                //回显教育经历
                                var smEducation = this.schoolmate.smEducation;
                                for (var i = 0; i < this.eduRecordArr.length; i++) {
                                    if (smEducation.eduRecord == this.eduRecordArr[i].value) {
                                        this.eduRecord = i;
                                    }
                                }
                                //回显院系专业
                                this.startdate = util.dateUtils.getYearByDate(smEducation.startdate);
                                for (var i = 0; i < this.specialtyArr.length; i++) {
                                    if (smEducation.specialty == this.specialtyArr[i].id) {
                                        this.specialty = i;
                                    }
                                }
                                for (var i = 0; i < this.seriesArr.length; i++) {
                                    if (smEducation.series == this.seriesArr[i].id) {
                                        this.series = i;
                                    }
                                }
                                for (var i = 0; i < this.collegeArr.length; i++) {
                                    if (smEducation.college == this.collegeArr[i].id) {
                                        this.college = i;
                                    }
                                }
                                for (var i = 0; i < this.schoolArr.length; i++) {
                                    if (smEducation.school == this.schoolArr[i].id) {
                                        this.school = i;
                                    }
                                }
                            }
                            uni.hideLoading();
                        },
                        fail: (res) => {
                            uni.hideLoading();
                        }
                    });
                }
            }
        },
        //页面加载时请求基础数据
        onShow() {
            this.template_zcsh = this.configParams.TEMPLATE_IDS.ZCSH;
            this.getOpenId();
            this.getInfoByUserId();
        },
        onLoad(options) {
            //判断是否人脸认证成功
            if(options.userId == undefined || options.userId == null || options.userId == ""){
                this.userId = '';
            }else{
                this.userId = options.userId;
            }
            this.userId = options.userId;
            if(options.truePhoto == undefined || options.truePhoto == null || options.truePhoto == ""){
                this.truePhoto = null;
            }else{
                this.truePhoto = options.truePhoto;
            }
            this.schoolmateTypes = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SCHOOLEMATE_TYPE);
            this.flag = true;
            //移動到onLoad
            //身份选择
            var idImg = [{
                    'type': app.alumni_type.expert,
                    'sort': 4,
                    'indexImg': '/static/img/regist/professor.png',
                    'indexImgIn': '/static/img/regist/professor-a.png'
                },
                {
                    'type': app.alumni_type.gradate,
                    'sort': 2,
                    'indexImg': '/static/img/regist/graduate.png',
                    'indexImgIn': '/static/img/regist/graduate-a.png'
                },
                {
                    'type': app.alumni_type.officer,
                    'sort': 5,
                    'indexImg': '/static/img/regist/leader.png',
                    'indexImgIn': '/static/img/regist/leader-a.png'
                },
                {
                    'type': app.alumni_type.student,
                    'sort': 1,
                    'indexImg': '/static/img/regist/student.png',
                    'indexImgIn': '/static/img/regist/student-a.png'
                },
                {
                    'type': app.alumni_type.teacher,
                    'sort': 3,
                    'indexImg': '/static/img/regist/teacher.png',
                    'indexImgIn': '/static/img/regist/teacher-a.png'
                }
            ]
            for (let i = 0; i < this.schoolmateTypes.length; i++) {
                // schoolmateTypesSort = this.schoolmateTypes[i].sort;
                for (let j = 0; j < idImg.length; j++) {
                    // idImgSort = idImg[j].sort;
                    if (idImg[j].type == this.schoolmateTypes[i].value) {
                        this.schoolmateTypes[i].indexImg = idImg[j].indexImg;
                        this.schoolmateTypes[i].indexImgIn = idImg[j].indexImgIn;
                    }
                }
            }
            this.schoolmateType = this.schoolmateTypes[this.cur].value;
            //获取院系专业数据
            if((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs.length != 0){
                this.schoolArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs;
                if((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs.length != 0){
                    this.collegeArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs;
                    if((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs[0].childs.length != 0){
                        this.seriesArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs[0].childs;
                        if((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs[0].childs[
                                0].childs.length != 0){
                             this.specialtyArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[0].childs[0].childs[
                                0].childs;
                        }  
                    }
                }
            }
            //获取教育类型
            let startdataArrTem = uni.getStorageSync('EDUYEAR');
            if (startdataArrTem.length != 0) {
                startdataArrTem = [];
                for (let i = 2019, year = 1986; i > year; i--) {
                    startdataArrTem.push(i)
                }
                this.startdataArr = startdataArrTem;
            }
            //获取性别基础数据
            let sexArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SEX)
            if (sexArrTem.length != 0) {
                this.sexArr = sexArrTem;
            } else {
                this.sexArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SEX)
            }
            //证件类型
            let cardtypeArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IDCARD_TYPE)
            if (cardtypeArrTem.length != 0) {
                this.cardtypeArr = cardtypeArrTem;
            } else {
                this.cardtypeArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IDCARD_TYPE)
            }
            //学历类型
            let eduRecordArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            if (eduRecordArrTem.length != 0) {
                this.eduRecordArr = eduRecordArrTem;
            } else {
                this.eduRecordArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            }
        }
    }
</script>

<style lang="scss">
    text {
        font-size: $uni-font-size-base;
    }

    /**/
    .waikuang {
        border-radius: 10upx;
        border-style: solid;
        border-width: 1upx;
        border-color: #ecebeb;
        box-shadow: 0 0 40upx #eee;
        margin-bottom: 24upx;
    }

    .selectId {
        font-size: 30upx;
        text-align: center;
        padding: 10upx 0;
        font-weight: bold;
    }

    .idassort {
        height: 200upx;
        padding-top: 20upx;
        display: flex;
        justify-content: center;
    }

    .indexImg {
        width: 84upx;
        height: 84upx;
        background-color: #fef7ea;
        border-radius: 50%;
        text-align: center;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .indexImg image {
        max-width: 42upx;
        max-height: 42upx;
        vertical-align: middle;
    }

    .clearfix {
        clear: both;
    }

    .mon_1.active .indexImg {
        box-shadow: 0 0 50upx #fcba04;
        background: #e2b352;
        width: 102upx;
        height: 102upx;
        margin-top: -7upx;
    }

    .mon_1.active .indexImg image {
        max-width: 55upx;
        max-height: 56upx;
    }

    .mon_1 .text_font {
        padding-top: 10px;
        display: inline-block;
    }

    .icon-s {
        background: url(../../static/img/regist/icon-s.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-y {
        background: url(../../static/img/regist/icon-y.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-data {
        background: url(../../static/img/regist/icon-data.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-id {
        background: url(../../static/img/regist/icon-id.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-m {
        background: url(../../static/img/regist/icon-m.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-n {
        background: url(../../static/img/regist/icon-n.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-num {
        background: url(../../static/img/regist/icon-num.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-sex {
        background: url(../../static/img/regist/icon-sex.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-xi {
        background: url(../../static/img/regist/icon-xi.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-xueli {
        background: url(../../static/img/regist/icon-xueli.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-xueyuan {
        background: url(../../static/img/regist/icon-xueyuan.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-zy {
        background: url(../../static/img/regist/icon-zy.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-school {
        background: url(../../static/img/regist/icon-school.png) left center no-repeat;
        background-size: 35upx;
    }

    .icon-danwei {
        background: url(../../static/img/regist/icon-danwei.png) left center no-repeat;
        background-size: 35upx;
    }

    .mon_1 text {
        font-size: 24upx;
        color: #333;
    }

    /**/
    .money_2 {
        padding: 20upx 30upx 20upx 30upx;
        overflow: hidden;
    }

    .mon_1 {
        float: left;
        text-align: center;


    }

    .mon_2 {
        margin-right: 27upx;
        margin-left: 27upx;

    }

    .active {}

    text.text_font {
        font-size: 26upx;
        color: #000;
    }


    .action-row-center {
        color: #fff;
        background-color: #999999;
        height: 60upx;
        line-height: 60upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
        font-size: 24upx;
        z-index: 9999;
        margin-bottom: 10upx;
        margin: 0 -15upx;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content {
        background-color: #fff;
        padding-bottom: 40upx;
        padding: 0 15upx;
    }

    .content_view {
        background-color: #FFFFFF;
        padding-left: 20rpx;
        padding-right: 20upx;
    }

    .content_view_item {
        clear: both;
        height: 80upx;
        padding-top: 10upx;
        padding-bottom: 10upx;
        border-bottom: 1upx solid #e6e6e6;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .content_view_item:last-child {
        border: none;
    }

    .text_reg {
        /* width: 15%; */
        padding-left: 43upx;
        padding-bottom: 10upx;
        padding-top: 10upx;
        position: relative;
        color: #333;
    }

    .input_reg {
        float: right;       
        text-align: right;
        margin-right: 20upx;

    }
	input.input_reg{
		height: 80upx;line-height: 80upx;
	}
    .btn-verify {
        width: 174upx;
        height: 64upx;
        line-height: 64upx;
        font-size: 26upx;
        color: #e2b352;
        padding: 0;
        display: inline-block;
    }

    button.btn-verify:after {
        border: none;
        width: 1upx;
        top: 0;
        bottom: 0;
        margin: auto;
        left: 0;
        background: #efefef;
    }

    .switch {
        float: right;
        padding-top: 10rpx;
    }

    .primary {
        width: 90%;
        padding-bottom: 20upx;
        margin-bottom: 40upx;
        ;
    }

    .uni-input {
        text-align: right;
        // color: #cccccc;
        position: relative;
        padding-right: 0;
    }

    .ring {
        width: 27upx;
        height: 32upx;
        position: relative;
        top: 12rpx;
        right: 16rpx;
    }

    .con_view {}

    .tis-image {
        width: 90upx;
        height: 90upx;
        border-radius: 100%;
        margin: -10upx 0upx;
    }
	.placeholderStyle{color: #ccc;}
</style>
