<template>
    <view class="content">
        <view class="content_view">

            <view class="content_view_item">
                <text class="text_reg">名称</text>
                <input class="input_reg" placeholder="请输入名称" placeholder-class="placeholderStyle" v-model.trim="name" data-key="name"
                    maxlength=32 />
            </view>
           
            <view class="content_view_item">
                <text class="text_reg">行业</text>
                <!-- <picker class="input_reg" @change="bindPicker">
					<view class="uni-input">{{}}</view>
				</picker> -->
                <multiple-linkage style='width:158rpx;float:right;margin-top: 20upx' :showtitle="showTitle" :flist='industryArry'
                    :dialogTitle="dialogTitle" :showValues="showIndustryList" :type="type" @saveclick="savedata" v-if="flag">
                </multiple-linkage>
                <!-- <multiple-linkage style='width:158rpx;float:right;margin-top: 20upx;' :showtitle="showeduTitle" :flist='degreeTypeArry'
                 :dialogTitle="dialogeduTitle" :showValues="showDegreeList" :type="type" @saveclick="savedata1" v-if="eduflag">
                </multiple-linkage> -->

            </view>
            <view class="content_view_item">
                <text class="text_reg">获得时间</text>
                <picker class="input_reg" @change="bindPickerDate" :value="date" mode=date fields="year">
                    <view class="uni-input">{{date==null?'':date}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">详细说明</text>
                <input class="input_reg" placeholder="请输入详细说明" placeholder-class="placeholderStyle" v-model.trim="infoval"
                    data-key="infoval" maxlength=32 />
            </view>

            <view class="input-row" style="margin-top:200upx;">
                <button type="primary" class="primary btn_dark bottom-but" @click="saveHonor" :disabled="saveFlag">保存</button>
                <button v-if="hasDelete" type="default" class="primary btn_white bottom-but" @click="deleteHonor"
                    :disabled="deleteFlag">删除</button>
            </view>

        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import loadcache from '@/common/loadcache.js';
    import request from '@/common/request.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    export default {
        components: {
            multipleLinkage
        },
        data() {
            return {
                id: '',
                name: "",
                typeIndex: 0,
                typeArry: [],
                date: "",
                infoval: "",
                showTitle: "行业",
                industry: '',
                showIndustryList: [],
                dialogTitle: "请选择行业",
                industryArry: {},
                hasDelete: false,
                industryFirstsId: '',
                industrySecondsId: '',
                industryThirdId: '',
                industryFourId: '',
                flag: false,
                saveFlag: false,
                deleteFlag: false
            }
        },
        onLoad(event) {
            //加载基础数据
            //this.typeArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.HONOR_TYPE);
            const payload = event.id || event.payload;
            if (payload != null) {
                try {
                    this.id = decodeURIComponent(payload);
                } catch (error) {
                    this.id = payload;
                };

                this.hasDelete = true;
            }


        },
        onShow() {
            this.industryArry = uni.getStorageSync("multipleLinkageIndustry").childs;
            if (this.id != '') {
                this.getHonor();
            }else{
                this.flag = true;
            }
        },
        methods: {
            getHonor(e) {
                request.http({
                    url: '/app/honor/getOne',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.name = res.data.data.name;
                           
                            this.infoval = res.data.data.infoval;
                            this.date = res.data.data.date;
                            /* this.typeArry.forEach((d, index) => {
                                if (d.value == this.type) {
                                    this.typeIndex = index;
                                }
                            }); */
                            this.industry = res.data.data.industry; //行业
                            var arrTem = [];
                            var parentid = "root";
                            var industry = this.industry;
                            if (null != industry && industry != "") {
                                do {

                                    var industryObj = loadcache.getObjFromStorageById(app.CACHE_NAME.SYSINDUSTRYBASE,
                                        industry);
                                    parentid = industryObj.parentId;
                                    arrTem.push(industryObj)
                                    industry = industryObj.parentId;
                                } while (parentid != "root");
                                for (var i = arrTem.length-1; i >= 0; i--) {

                                    var obj = {
                                        'id': arrTem[i].id,
                                        'name': arrTem[i].name
                                    };
                                    this.showIndustryList.push(obj);
                                    if (i == arrTem.length-1) {
                                        this.showTitle = arrTem[i].name;
                                    } else {
                                        this.showTitle = arrTem[i].name + '-' + this.showTitle;
                                    }

                                }
                            }
                        }
                    },
                    error: (res) => {},
                    complete: () => {
                        this.flag = true;
                    }
                });
            },
            savedata(e) {
                /* this.parentValue = e; */
                this.showTitle = e[5]
                this.industryFirstsId = e[0];
                this.industrySecondsId = e[1];
                this.industryThirdId = e[2];
                this.industryFourId = e[3];
            },
            bindPickerType(e) {
                this.typeIndex = e.target.value;
            },
            bindPickerDate(e) {
                this.date = e.target.value;
            },
            saveHonor() {
                console.log(this.industry)
                this.saveFlag = true;
                //this.type = this.typeArry[this.typeIndex].value;
                if (this.industryFourId != '') {
                    this.industry = this.industryFourId;
                } else if (this.industryThirdId != '') {
                    this.industry = this.industryThirdId;
                } else if (this.industrySecondsId != '') {
                    this.industry == this.industrySecondsId;
                } else if (this.industryFirstsId != '') {
                    this.industry = this.industryFirstsId;
                }
                if (this.name == '') {
                    this.saveFlag =false;
                    this.showpageToast("请输入名称");
                    return false;
                }
                if (this.industry == '') {
                    this.saveFlag =false;
                    this.showpageToast("请选择行业");
                    return false;
                }
                

                request.http({
                    url: '/app/honor/saveHonor',
                    header: 'application/x-www-form-urlencoded',
                    method: "POST",
                    data: {
                        id: this.id,
                        name: this.name,
                        infoval: this.infoval,
                        date: this.date,
                        industry: this.industry
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
            deleteHonor() {
                
                this.deleteFlag = true;
                uni.showModal({
                    title: '',
                    content: '确定删除该荣誉成果？',
                    success: (res) => {
                        if (res.confirm) {
                            request.http({
                                url: '/app/honor/deleteHonor',
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
/*    text {
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
        background-color: #F2F2F2;
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
        color: #333;
        /* padding-left: 20upx; */
        position: relative;
    }

    .input_reg {
        width: 60% !important;
        float: right;
        text-align: right;
        /* margin-right: 20upx; */

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
        // color: #cccccc;
        position: relative;
   /*     bottom: 10rpx; */
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
