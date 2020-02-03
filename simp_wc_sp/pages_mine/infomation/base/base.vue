<template>
    <view class="content">
        <view class="content_view">
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
                <input class="input_reg" style="width:60%" placeholder-class="placeholderStyle" placeholder="请输入姓名" v-model.trim="name" data-key="name" maxlength=30 />
            </view>

            <view class="content_view_item">
                <text class="text_reg">性别</text>
                <picker class="input_reg" @change="bindPickerChangeSex" range-key="label" :value="sexIndex" :range="sexArr">
                    <view class="uni-input">{{sexArr[sexIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">证件类型</text>
                <picker class="input_reg" @change="bindPickerChangeCardType" range-key="label" :value="cardIndex"
                    :range="cardtypeArr">
                    <view class="uni-input">{{cardtypeArr[cardIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">证件号码</text>
                <input class="input_reg" placeholder-class="placeholderStyle" style="width:50%" placeholder="请输入证件号码" v-model.trim="cardNum" data-key="cardNum" maxlength=30 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">出生年月</text>
                <picker class="input_reg" @change="bindPickerBirthday" :value="birthday" mode=date>
                    <view class="uni-input">{{birthday}}</view>
                </picker>
            </view>
            <!-- <view class="content_view_item">
                <text class="text_reg">籍贯</text>
                <picker class="input_reg" :value="index" :range="region" mode="region" @change="bindPickerAddress">
                    <view class="uni-input"> {{region[0]}} {{region[1]}} {{region[2]}}</view>
                </picker>
            </view> -->
            <view class="content_view_item">
                <text class="text_reg">民族</text>
                <picker class="input_reg" @change="bindPickerChangeNation" range-key="label" :value="nationIndex"
                    :range="nationArr">
                    <view class="uni-input">{{nationArr[nationIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">身份</text>
                <picker class="input_reg" @change="bindPickerChangeType" range-key="label" :value="typeIndex" :range="typeArr">
                    <view class="uni-input">{{typeArr[typeIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">政治面貌</text>
                <picker class="input_reg" @change="bindPickerChangePolitics" range-key="label" :value="politicsIndex" :range="politicsArr">
                    <view class="uni-input">{{politicsArr[politicsIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">是否可被检索</text>
                <switch class="switch" @change="switchChange" :checked="checked" />
            </view>
            
        </view>
        <view class="input-row" style="margin-top:120upx;">
            <button type="primary" class="primary btn_dark bottom-but" @click="saveBase" :disabled="saveDisable">保存</button>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import {
        mapState,mapMutations
    } from 'vuex';
    export default {
        data() {
            return {
                imagePath: app.imagePath,
                region: ['省', '市', '县'],
                index: 0,
                id: "",
                username: "",
                birthday: "",
                code: "",
                password: "",
                name: "",
                sex: "",
                cardType: "",
                cardNum: "",
                type: "",
                nation: "",
                nationIndex: 0,
                checked: true,
                sexIndex: 0,
                typeIndex: 0,
                typeArr: ['请选择'],
                sexArr: ['请选择'],
                cardIndex: 0,
                cardtypeArr: ['请选择'],
                startdataIndex: 0,
                nationArr: ['请选择'],
                saveDisable: false,
                truePhoto: null,
                politicsArr:['请选择'],
                politicsIndex:0
            }
        },
        computed: {
            ...mapState(['userInfo'])
        },
        onShow() {
            //获取基础数据
            // this.sexArr = cache.getvaluetokey('SEX');
            //获取性别基础数据
            let sexArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SEX)
            if (sexArrTem.length != 0) {
                this.sexArr = sexArrTem;
            } else {
                this.sexArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SEX)
            }
            //获取政治面貌基础数据
            let politicsArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.POLITICS_NAME)
            if(politicsArrTem.length!=0){
                 this.politicsArr = politicsArrTem;
            }else{
                this.politicsArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.POLITICS_NAME)
            }
            
            //证件类型
            // this.cardtypeArr = cache.getvaluetokey('IDCARD_TYPE');
            let cardtypeArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IDCARD_TYPE)
            if (cardtypeArrTem.length != 0) {
                this.cardtypeArr = cardtypeArrTem;
            } else {
                this.cardtypeArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IDCARD_TYPE)
            }

            //民族
            // this.nationArr = cache.getvaluetokey('NATION');
            let nationArrrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.NATION)
            if (nationArrrTem.length != 0) {
                this.nationArr = nationArrrTem;
            } else {
                this.nationArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.NATION)
            }

            //          this.typeArr = cache.getvaluetokey('SCHOOLEMATE_TYPE');
            let typeArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SCHOOLEMATE_TYPE)
            if (typeArrTem.length != 0) {
                this.typeArr = typeArrTem;
            } else {
                this.typeArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.SCHOOLEMATE_TYPE)
            }
            this.getInfo();
            //获取个人信息回显
            // this.id = this.userInfo.id;
            // this.name = this.userInfo.name;
            // this.sex = this.userInfo.sex;
            // this.cardType = this.cardType;
            // this.cardNum = this.userInfo.cardNum;
            // this.birthday = this.userInfo.birthday;
            // this.nation = this.userInfo.nation;
            // this.type = this.userInfo.type;
            // this.checked = this.userInfo.isShow;
            // this.truePhoto = this.truePhoto;
            //判断是否登录登录则需要获取个人信息
            //             }
        },
        methods: {
            ...mapMutations(['setUserInfo']),
            getInfo() {
                request.http({
                    url: '/app/schoolmate/getInfo',
                    header: 'application/x-www-form-urlencoded',
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.id = res.data.data.id;
                            this.name = res.data.data.name;
                            this.sex = res.data.data.sex;
                            this.birthday = this.userInfo.birthday;
                            this.cardNum = res.data.data.cardNum;
                            this.truePhoto = res.data.data.truePhoto;
                            let sex = res.data.data.sex;
                            for (let i = 0; i < this.sexArr.length; i++) {
                                if (this.sexArr[i].value == sex) {
                                    this.sexIndex = i
                                }
                            }
                            let politics = res.data.data.politics;
                            for (let i = 0; i < this.politicsArr.length; i++) {
                                if (this.politicsArr[i].value == politics) {
                                    this.politicsIndex = i
                                }
                            }
                            let cardType = res.data.data.cardType;
                            for (let i = 0; i < this.cardtypeArr.length; i++) {
                                if (this.cardtypeArr[i].value == cardType) {
                                    this.cardIndex = i
                                }
                            }
                            this.birthday = res.data.data.birthday;
                            let nation = res.data.data.nation;
                            for (let i = 0; i < this.nationArr.length; i++) {
                                if (this.nationArr[i].value == nation) {
                                    this.nationIndex = i
                                }
                            }
                            let smType = res.data.data.type;
                            for (let i = 0; i < this.typeArr.length; i++) {
                                if (this.typeArr[i].value == smType) {
                                    this.typeIndex = i
                                }
                            }

                            this.checked = res.data.data.isShow == "true"? true: false;
                            console.log(this.truePhoto);
                        }
                    },
                    fail: () => {

                    }
                })
            },
            switchChange(e) {
                console.log(e.target.value);
                this.checked = e.target.value;
            },
            bindPickerChangeSex(e) {
                this.sexIndex = e.target.value
            },
            bindPickerChangePolitics(e){
                this.politicsIndex = e.target.value
            },
            bindPickerChangeCardType(e) {
                this.cardIndex = e.target.value
            },
            bindPickerBirthday(e) {
                this.birthday = e.target.value
            },
            bindPickerChangeType(e) {
                this.typeIndex = e.target.value
            },
            bindPickerChangeNation(e) {
                this.nationIndex = e.target.value
            },
            bindPickerAddress(e) {
                console.log(e.detail); //code 
                // console.log(e);
                this.region = e.detail.value;
            },
            showpageToast(msg) {
                uni.showToast({
                    icon: 'none',
                    title: msg
                });
            },
            saveBase() {
                if (this.name == '') {
                    this.saveFlag = false;
                    this.showpageToast("请输入姓名");
                    return false;
                }
                if (this.cardtypeArr[this.cardIndex].value == 'IDCARD') {
                    var regCardNum =
                        /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/;
                    if (!regCardNum.test(this.cardNum)) {
                        this.showpageToast("请输入正确的身份证号");
                        this.saveFlag = false
                        return false;
                    }
                }
                if (this.cardNum == "") {
                    this.showpageToast("请输入证件号");
                    this.saveFlag = false
                    return false;
                }
                this.saveDisable = true;
                //保存个人的基本信息
                request.http({
                    url: '/app/schoolmate/saveInfo',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                        name: this.name,
                        sex: this.sexArr[this.sexIndex].value,
                        politics:this.politicsArr[this.politicsIndex].value,
                        cardType: this.cardtypeArr[this.cardIndex].value, //this.cardType,
                        cardNum: this.cardNum,
                        birthday: this.birthday,
                        nation: this.nationArr[this.nationIndex].value, //this.nation,
                        type: this.typeArr[this.typeIndex].value, //this.type,
                        isShow: this.checked,
                        truePhoto: (this.truePhoto==undefined||this.truePhoto=='undefined'||this.truePhoto == 'null')?'':this.truePhoto
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.navigateBack({

                            });
                            this.setUserInfo(res.data.data);
                            this.showpageToast("保存个人信息成功");
                            this.saveDisable = false;
                        }
                    },
                    fail: () => {

                    }
                })
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
                                        if (result.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                            console.log(result.data);
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
            }
        }
    }
</script>

<style lang="scss">
   /* text {
        font-size: 35upx;
        color: #333;
    } */
	
    .action-row-center {
        color: #fff;
        background-color: rgba(0, 0, 0, 0.4);
        height: 98%;
        line-height: 60upx;
        display: flex;
        flex-direction: row;
        justify-content: center;
        font-size: 24upx;
    }

    .action-row-right {
        color: #007aff;
        padding-top: 20upx;
        display: flex;
        flex-direction: row;
        justify-content: flex-end;
    }

    .content {
        /* background-color: #F2F2F2; */
        /* padding-bottom: 40upx; */
    }

    .content_view {
        background-color: #FFFFFF;
        margin-top: 20upx;
        margin-bottom: 20upx;
        padding-left: 20upx;
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
        color: #000;
        /* padding-left: 20upx; */
        position: relative;
 
    }
	.content_view_item input.input_reg:placeholder{
		color: #000;
	}
    .input_reg {
        width: 60% !important;
        float: right;
        text-align: right;
        // margin-right: 20upx;

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
        top: 18upx;
        right: 25upx;
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

    .primary.btn_dark.bottom-but {
        position: absolute;
        width: 100%;
        bottom: 0;
		 }
		 
    .tis {
        font-size: 26upx;
        margin-right: 5upx;
        max-height: 120upx;
    }

    .tis-image {
        width: 70upx;
        height: 70upx;
        border-radius: 100%;
    }
	.placeholderStyle{color: #ccc;}
	.input-row{
		width:  90vw;
		position:  absolute;
		bottom:  70rpx;
		left:  5vw; 
	}
</style>
