<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">联系类型</text>
                <picker class="input_reg" @change="bindPickerChangeType" range-key="label" :value="typeIndex" :range="typeArr">
                    <view class="">{{typeArr[typeIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">联系方式</text>
                <input class="input_reg" placeholder="请输入联系方式" placeholder-class="placeholderStyle" v-model.trim="contact"
                    data-key="contact" maxlength=30 />
            </view>
            <view class="input-row" style="margin-top:200upx;">
                <button type="primary" class="primary btn_dark bottom-but" @click="saveContact" :disabled="saveFlag">保存</button>
                <button v-if="hasDelete" type="default" class="primary btn_white bottom-but" @click="deleteContact"
                    :disabled="deleteFlag">删除</button>
            </view>
        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    export default {
        data() {
            return {
                typeArr: ['请选择'],
                typeIndex: 0,
                type: '',
                contact: '',
                id: '',
                hasDelete: false,
                saveFlag: false,
                deleteFlag: false
            }
        },
        onLoad(event) {
            this.typeArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.CONTACT_TYPE);
            const payload = event.id || event.payload;
            if (payload != null) {
                try {
                    this.id = JSON.parse(decodeURIComponent(payload));
                } catch (error) {
                    this.id = JSON.parse(payload);
                };

                this.hasDelete = true;
            }

        },
        onShow(e) {
            if (this.id != '') {
                this.getContact();
            }
        },
        methods: {
            getContact(e) {
                request.http({
                    url: '/app/contact/getOne',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.type = res.data.data.type;
                            this.contact = res.data.data.contact;
                            this.typeArr.forEach((d, index) => {
                                if (d.value == this.type) {
                                    this.typeIndex = index;
                                }
                            });

                        }
                    },
                    error: (res) => {}
                });
            },
            bindPickerChangeType(e) {
                this.typeIndex = e.target.value;
            },
            saveContact(e) {
                this.saveFlag = true;
                this.type = this.typeArr[this.typeIndex].value;
                if (this.contact == '') {
                    this.saveFlag =false;
                    this.showpageToast("请输入联系内容");
                    return false;
                }
                if (this.type == 'PHONE') {
                    
                    let myphone =  /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
                    if (!myphone.test(this.contact)) {
                        this.saveFlag =false;
                        this.showpageToast("请输入正确的手机号");
                        return false;
                    }
                }
                if (this.type == 'EMAIL') {
                    this.saveFlag =false;
                    let myemail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                    if (!myemail.test(this.contact)) {
                        this.saveFlag =false;
                        this.showpageToast("请输入正确的邮箱");
                        return false;
                    }
                }
                if (this.type == 'QQ') {
                    this.saveFlag =false;
                    let myqq = /^[1-9][0-9]{4,14}/;
                    if (!myqq.test(this.contact)) {
                        this.saveFlag =false;
                        this.showpageToast("请输入正确的QQ");
                        return false;
                    }
                }
                request.http({
                    url: '/app/contact/saveContacat',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: this.id,
                        type: this.type,
                        contact: this.contact
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.navigateBack({

                            });
                            this.showpageToast(res.data.msg);
                        }
                        this.saveFlag = false;
                    },
                    error: (res) => {
                        this.saveFlag = false;
                    }
                });
            },
            deleteContact(e) {
                this.deleteFlag = true;
                uni.showModal({
                    title: '',
                    content: '确定删除该联系方式？',
                    success: (res) => {
                        if (res.confirm) {
                            request.http({
                                url: '/app/contact/deleteContact',
                                header: 'application/x-www-form-urlencoded',
                                method: "POST",
                                data: {
                                    id: this.id
                                },
                                success: (res) => {
                                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                        uni.navigateBack({

                                        });
                                        this.showpageToast(res.data.msg);
                                    }
                                    this.deleteFlag =false;
                                },
                                error: (res) => {
                                    this.deleteFlag =false;
                                }
                            });


                        }else{
                            this.deleteFlag =false;
                        }
                    }
                });
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
    // .content_view_item text.text_reg{font-size: $uni-font-size-base;}

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
        background-color: #F2F2F2;
        /* padding-bottom: 40upx; */
    }

    .content_view {
        background-color: #FFFFFF;
        margin-top: 20upx;
        margin-bottom: 20upx;
        padding-left: 20rpx;
        padding-right: 20upx;
    }

    .content_view_item {
        height: 80upx;line-height: 80upx;
       
        border-bottom: 1upx solid #e6e6e6;display: flex;align-items: center;justify-content: space-between;
        padding: 10upx 20upx;
    }

    .content_view_item:last-child {
        border: none;
    }

    .text_reg {
        /* width: 15%; */
        /* width: 15%; */
        font-size: $uni-font-size-base;
        display: inline-block;
        color: #333;
        // padding-left: 20upx;
        position: relative;
    }

    .input_reg {        
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
        /* color: #cccccc; */
        position: relative;
        /* bottom: 10rpx; */
        padding-right: 0;
    }

    .ring {
        width: 27upx;
        height: 32upx;
        position: relative;
        top: 12rpx;
        right: 16rpx;
    }

    .primary btn_dark bottom-but {
        position: absolute;
        bottom: 1upx;
    }

    .btn_white {
        margin-top: 20upx;
    }
    .placeholderStyle{color: #ccc;}
</style>
