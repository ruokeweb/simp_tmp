<template>
    <!-- 认证信息 -->
    <view class="content">
        <view class="action-row-center">
            <image class="ring" src="../../static/login/ring.png"></image> 请确认你的信息
        </view>
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">身份</text>
                <input class="input_reg" style="width:30%" placeholder="请在下方选择您的身份" disabled="true" />
            </view>
            <view class="content_view_item smType" style="border: none">
                <view v-for="(value, key) in schoolmateTypes" :key="key" :class="cur==key? 'mon_1 mon_2  active' : 'mon_1 mon_2' "
                    @click="whatTypes(key)">
                    <text :class="cur==key? 'text_font' : '' ">{{value.label}}</text>
                </view>
            </view>
            <view class="content_view_item">
                <text class="text_reg">照片</text>
                <view class="input_reg">
                    <image v-if="truePhoto != null || truePhoto != ''" :src="imagePath + truePhoto" class="tis-image"
                        mode="aspectFit" @click="uploadtruePhoto"></image>
                    <image v-if="truePhoto == null || truePhoto == ''" class="tis-image" src="/static/img/man_default.png"
                        mode="aspectFit" @click="uploadtruePhoto"></image>
                </view>
            </view>
            <view class="content_view_item">
                <text class="text_reg">姓名</text>
                <input class="input_reg" style="width:70%" placeholder="请输入姓名" placeholder-style="color:#ccc;"
                    v-model.trim="name" data-key="name" maxlength=30 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">性别</text>
                <picker class="input_reg" @change="bindPickerChangeSex" range-key="label" :value="sexindex" :range="sexArr">
                    <view class="uni-input">{{sexArr[sexindex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">证件类型</text>
                <picker class="input_reg" @change="bindPickerChangeCardType" range-key="label" :value="cardindex"
                    :range="cardtypeArr">
                    <view class="uni-input">{{cardtypeArr[cardindex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">证件号码</text>
                <input class="input_reg" style="width:50%" placeholder="请输入证件号码" placeholder-style="color:#ccc;"
                    v-model.trim="cardNum" data-key="cardNum" maxlength=30 />
            </view>
        </view>
        <view class="content_view" v-if="schoolmateType != alumni_teacher">
            <view class="content_view_item">
                <text class="text_reg">入学年份</text>
                <picker class="input_reg" @change="bindPickerChangeDate" fields='year' start='1964' end='2019' mode="date">
                    <view class="uni-input">{{startdate}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">学历</text>
                <picker class="input_reg" @change="bindPickerChangeEduRecord" range-key="label" :value="eduRecord"
                    :range="eduRecordArr">
                    <view class="uni-input">{{eduRecordArr[eduRecord].label }}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">学校</text>
                <picker class="input_reg" @change="bindPickerChangeSchool" range-key="name" :value="school" :range="schoolArr">
                    <view class="uni-input">{{schoolArr[school].name }}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">学院</text>
                <picker class="input_reg" @change="bindPickerChangeCollege" range-key="name" :value="college" :range="collegeArr">
                    <view class="uni-input">{{collegeArr[college].name }}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">系</text>
                <picker class="input_reg" @change="bindPickerChangeSeries" range-key="name" :value="series" :range="seriesArr">
                    <view class="uni-input">{{seriesArr[series].name }}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">专业</text>
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

            <view class="content_view_item">
                <text class="text_reg">是否可被检索</text>
                <switch class="switch" @change="switchChange" :checked="checked" />
            </view>
        </view>
        <view class="content_view" v-if="schoolmateType == alumni_teacher">
            <view class="content_view_item">
                <text class="text_reg">单位</text>
                <input class="input_reg" style="width:50%" placeholder="请输入单位" placeholder-style="color:#ccc;"
                    v-model.trim="workplace" data-key="workplace" maxlength=64 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">是否可被检索</text>
                <switch class="switch" @change="switchChange" :checked="checked" />
            </view>
        </view>
        <!-- <view class="input-row but-view" style="margin-top:65upx;"> -->
        <button type="primary" class="primary btn_dark" @click="regist" :disabled="regDisabled">提交认证</button>
        <!-- </view> -->
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    import util from '@/common/util.js';
    import {
        mapMutations
    } from 'vuex';
    export default {
        components: {
            multipleLinkage
        },
        data() {
            return {
                schoolmate: "",
                name: "",
                sex: "",
                cardType: "",
                cardNum: "",
                imagePath: app.imagePath,
                checked: true,
                sexindex: 0,
                sexArr: ['请选择'],
                cardindex: 0,
                cardtypeArr: ['请选择'],
                startdate: 2019,
                startdataArr: ['请选择'],
                schoolArr: ['请选择学校'],
                school: 0,
                schoolId: '',
                collegeArr: ['请选择学院'],
                college: 0,
                collegeId: '',
                seriesArr: ['请选择系'],
                series: 0,
                seriesId: '',
                specialtyArr: ['请选择专业'],
                specialty: 0,
                specialtyId: '',
                eduRecord: 0,
                eduRecordArr: ['请选择'],
                time: '获取验证码', //倒计时
                showTitle: "请选择教育经历",
                dialogTitle: "请选择教育经历",
                disabled: false,
                regDisabled: false,
                eduArry: {},
                showValueList: [],
                flag: false,
                schoolmateTypes: [],
                cur: 0,
                schoolmateType: '',
                workplace: '',
                truePhoto: '',
                smEducation: {},
                smProfession: {},
                alumni_teacher: app.alumni_type.teacher
            }
        },
        computed: {

        },
        onLoad() {
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
            this.schoolmateTypes = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SCHOOLEMATE_TYPE);
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
            this.getUserInfo();
        },
        methods: {
            ...mapMutations(['setUserInfo']),
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
            bindPickerChangeCardType(e) {
                this.cardindex = e.target.value
            },
            bindPickerChangeDate(e) {
                this.startdate = e.target.value
                console.log(e.target.value);
            },
            bindPickerChangeEduRecord(e) {
                this.eduRecord = e.target.value
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

            //学校
            bindPickerChangeSchool(e) {
                this.school = e.target.value;
                if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs.length !=
                    0) {
                    this.collegeArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school]
                        .childs;
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
                console.log((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[
                    this.college].childs.length == 0);
                if ((uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[this.college]
                    .childs.length !=
                    0) {
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
            regist() {
                this.regDisabled = true
                console.log("提交注册信息");
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
                    url: app.serverPath + '/app/secondSubmit',
                    method: "POST",
                    data: {
                        //手机号
                        //基本信息
                        id: this.schoolmate.id,
                        name: this.name,
                        sex: this.sexArr[this.sexindex].value,
                        truePhoto: this.truePhoto,
                        type: this.schoolmateType,
                        cardType: this.cardtypeArr[this.cardindex].value,
                        cardNum: this.cardNum,
                        isShow: this.checked,
                        //教育经历
                        //教育经历
                        smEducation: this.smEducation,
                        smProfession: this.smProfession,
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
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
                        }
                    },
                    fail: () => {
                        this.disabled = false
                        this.showpageToast("提交信息失败")
                    }
                })

                // uni.navigateTo({
                //     url: "/pages/login/cardstatusSuccess"
                // })                          
            },

            getUserInfo() {
                request.http({
                    url: '/app/getConfirmSmInfo',
                    method: "POST",
                    data: {},
                    success: (res) => {
                        if (res.data.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log(res.data.data);
                            this.schoolmate = res.data.data;
                            //回显基础信息
                            this.name = this.schoolmate.name;
                            this.truePhoto = this.schoolmate.truePhoto;
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
                            //回显教育经历
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
                            for (var i = 0; i < this.schoolArr.length; i++) {
                                if (smEducation.school == this.schoolArr[i].id) {
                                    this.school = i;
                                }
                            }
                            this.collegeArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs;
                            for (var i = 0; i < this.collegeArr.length; i++) {
                                if (smEducation.college == this.collegeArr[i].id) {
                                    this.college = i;
                                }
                            }
                            this.seriesArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[this.college].childs;
                            for (var i = 0; i < this.seriesArr.length; i++) {
                                if (smEducation.series == this.seriesArr[i].id) {
                                    this.series = i;
                                }
                            }
                            this.specialtyArr = (uni.getStorageSync(app.CACHE_NAME.MULTIPLELINKAGEDEPARTMENTS)).childs[this.school].childs[this.college].childs[
                                this.series].childs;
                            for (var i = 0; i < this.specialtyArr.length; i++) {
                                if (smEducation.specialty == this.specialtyArr[i].id) {
                                    this.specialty = i;
                                }
                            }

                        }
                    },
                    error: (res) => {

                    }
                });
            },

        },
        //页面加载时请求基础数据
        onShow() {
        }
    }
</script>

<style lang="scss">
    .money_2 {
        padding: 20upx 30upx 20upx 30upx;
        overflow: hidden;
    }

    .mon_1 {
        width: 146upx;
        height: 65upx;
        border: 1upx solid #eaeaea;
        float: left;
        text-align: center;
        line-height: 65upx;
        font-size: 26upx;
        color: #444444;
        /* font-weight: bold; */
        position: relative;
        margin-bottom: 10upx;
        border-radius: 10upx;
    }

    .mon_2 {
        margin-right: 30upx;
    }

    .active {
        color: #FFFFFF !important;
        background-color: #272D3D !important;
    }


    // text {
    //     font-size: 26upx;
    //     color: #333;
    // }
    .smType{
        display: flex;
        justify-content:space-between;
    }
    .action-row-center {
        position: fixed;
        top: 0;
        width: 100%;
        color: #fff;
        background-color: #999999;
        height: 60upx;
        line-height: 60upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
        font-size: 24upx;
        z-index: 9999;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content {
        background-color: #F2F2F2;
        /* padding-bottom: 20upx; */
    }

    .content_view {
        background-color: #FFFFFF;
        margin-top: 20upx;
        margin-bottom: 20upx;
        padding-left: 20rpx;
        padding-right: 20upx;
    }

    .content_view_item {
        height: 80upx;
        line-height: 80upx;
        border-bottom: 1upx solid #e6e6e6;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10upx 20upx;
    }

    .content_view_item:last-child {
        border: none;
    }

    .text_reg {
        /* width: 15%; */
        font-size: $uni-font-size-base;
        display: inline-block;
        color: #333;
        /* padding-left: 20upx; */
        position: relative;
    }

    .input_reg {
        width: 60% !important;
        float: right;
        text-align: right;

    }

    .btn-verify {
        width: 174upx;
        height: 64upx;
        line-height: 64upx;
        background-color: #e2b352 !important;
        font-size: 26upx;
        color: #fff;
        padding: 0;
        display: inline-block;
        position: absolute;
        top: 0upx;
        right: 0upx;
        z-index: 1000;
    }

    .switch {
        float: right;
        padding-top: 10rpx;
    }

    .primary {
        width: 90%;
        padding-bottom: 20upx;
    }

    .uni-input {
        text-align: right;
        // color: #cccccc;
        position: relative;
        bottom: 10rpx;
        padding-right: 0;
    }

    .ring {
        width: 27upx;
        height: 32upx;
        position: relative;
        top: 12rpx;
        right: 16rpx;
    }

    .con_view {
        margin-top: 53upx;
    }

    .btn-verify {
        top: 18upx;
        right: 15upx;
    }

    .but-view {
        background: #FFFFFF;
    }

    .primary.btn_dark.bottom-but {
        position: absolute;
        width: 90%;
        bottom: 100rpx;
        left: 5%;
    }

    .tis-image {
        width: 80upx;
        height: 80upx;
        border-radius: 100%;
        margin: -37upx 0upx;
    }

    .text_font {
        color: #FFFFFF;
    }
    .placeholderStyle{color: #ccc;}
</style>
