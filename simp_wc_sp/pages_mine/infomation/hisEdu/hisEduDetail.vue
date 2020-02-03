<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">教育信息</text>
                <input class="input_reg" placeholder="请输入教育信息" placeholder-class="placeholderStyle" v-model.trim="education"
                    data-key="classes" maxlength=128 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">类型</text>
                <picker class="input_reg" @change="bindPickerEduRecord" :value="eduRecordIndex" :range="eduRecordArry"
                    range-key="label">
                    <view class="uni-input">{{eduRecordArry[eduRecordIndex].label}}</view>
                </picker>
            </view>

            <view class="content_view_item">
                <text class="text_reg">入学日期</text>
                <picker class="input_reg" @change="bindPickerStartdate" :value="startdate"  fields='year' mode=date>
                    <view class="uni-input">{{null == startdate?'':startdate}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">毕业日期</text>
                <picker class="input_reg" @change="bindPickerEnddate" :value="enddate"  fields='year' mode=date>
                    <view class="uni-input">{{null == enddate?'':enddate}}</view>
                </picker>
            </view>

            <view class="input-row" style="margin-top:200upx;">
                <button type="primary" class="primary btn_dark bottom-but" @click="saveHisEdu" :disabled="saveFlag">保存</button>
                <button v-if="'' != id" type="default" class="primary btn_white bottom-but" @click="deleteProfession"
                    :disabled="deleteFlag">删除</button>
            </view>

        </view>
    </view>
</template>

<script>
    import app from '@/common/app.js';
    import request from '@/common/request.js';
    import loadcache from '@/common/loadcache.js';
    import multipleLinkage from '@/components/multipleLlinkage/multiple-linkage.vue'; //多级联动
    export default {
        components: {
            multipleLinkage
        },
        data() {
            return {
                id: '',
                eduRecord: "", //记录会显的值
                eduRecordIndex: 0,
                eduRecordArry: [],
                education: "",
                startdate: "",
                enddate: "",
                eduArry: {},
                showeduTitle: "学科",
                eduflag: false,
                flag: false,
                saveFlag: false,
                deleteFlag: false
            }
        },
        onLoad(options) {
            //加载基础数据
            if (null != options) {
                console.log(options.id);
                if (options.id == undefined) {
                    this.id = '';
                } else {
                    this.id = JSON.parse(decodeURIComponent(options.id));
                }
            }
        },
        methods: {
            bindPickerEduRecord(e) {
                this.eduRecordIndex = e.target.value;
            },
            bindPickerStartdate(e) {
                this.startdate = e.target.value;
            },
            bindPickerEnddate(e) {
                this.enddate = e.target.value;
            },
            saveHisEdu() {
                this.saveFlag = true;
                if (null == this.education || '' == this.education) {
                    this.saveFlag = false;
                    uni.showToast({
                        icon: "none",
                        title: "请填写教育信息"
                    })
                    return false;
                }
                console.log(this.id);
                request.http({
                    header: 'application/x-www-form-urlencoded',
                    url: "/app/hisEdu/save",
                    data: {
                        id: this.id,
                        education: this.education,
                        //学历
                        type: this.eduRecordArry[this.eduRecordIndex].value,
                        //毕业时间",format="yyyy-MM-dd"
                        enddate: null == this.enddate ? '' : this.enddate,
                        //入学时间",format="yyyy-MM-dd"
                        startdate: null == this.startdate ? '' : this.startdate,
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            console.log(res.data.data);
                            // this.professionList = res.data.data;
                            uni.navigateBack({
                            
                            });
                            uni.showToast({
                                icon: "none",
                                title: "保存成功"
                            })
                        }
                        this.saveFlag = false;
                    },
                    fail: (data, code) => {
                        console.log(data);
                        this.saveFlag = false;
                    }
                })
            },
            deleteProfession() {
                this.deleteFlag = true;
                request.http({
                    header: 'application/x-www-form-urlencoded',
                    url: "/app/hisEdu/delete",
                    data: {
                        id: this.id
                    },
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.showToast({
                                icon: "none",
                                title: "删除成功"
                            })
                            uni.navigateBack({
                                url: "edu"
                            })
                        }
                        this.deleteFlag = false;
                    },
                    fail: (data, code) => {
                        console.log(data);
                        this.deleteFlag = false;
                    }
                })
            }

        },
        onShow() {
            //学历
            let eduRecordArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            if (eduRecordArryTem.length != 0) {
                this.eduRecordArry = eduRecordArryTem;
            } else {
                this.eduRecordArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            }

            if (this.id != '') {
                //获取教育经历
                request.http({
                    url: '/app/hisEdu/get',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                    },
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.education = res.data.data.education;
                            let eduRecord = res.data.data.type; //学历
                            for (let i = 0; i < this.eduRecordArry.length; i++) {
                                if (this.eduRecordArry[i].value == eduRecord) {
                                    this.eduRecordIndex = i
                                }
                            };
                            this.enddate=res.data.data.enddate;
                            this.startdate=res.data.data.startdate;
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    },
                    complete: () => {
                        this.addflag = true;
                        this.industryflag = true;
                    }
                })
            }else{
               this.eduflag = true;
               this.flag = true; 
            }
        }
    }
</script>

<style lang="scss">
    // text {
    //     font-size: 35upx;
    //     color: #333;
    // }

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
