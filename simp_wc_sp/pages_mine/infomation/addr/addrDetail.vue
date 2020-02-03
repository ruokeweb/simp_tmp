<template>
    <view class="content">
        <view class="content_view">

            <view class="content_view_item">
                <text class="text_reg">地址</text>
                <multiple-linkage style='width:158rpx;float:right;' :showtitle="showTitle" :flist='addArry'
                    :dialogTitle="dialogTitle" :type="type" @saveclick="savedata" :showValues="showAddList" v-if="flag">
                </multiple-linkage>
            </view>
            <view class="content_view_item">
                <text class="text_reg">详细地址</text>
                <input class="input_reg" placeholder="请输入详细地址" placeholder-class="placeholderStyle" v-model.trim="detail"
                    data-key="detail" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">邮编</text>
                <input class="input_reg" placeholder="请输入邮编" placeholder-class="placeholderStyle" v-model.trim="zipcode"
                    data-key="zipcode" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">地址类型</text>
                <picker class="input_reg" @change="bindPickerChangeType" range-key="label" :value="typeIndex" :range="typeArr">
                    <view class="uni-input">{{typeArr[typeIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">备注</text>
                <input class="input_reg" placeholder="请输入备注" placeholder-class="placeholderStyle" v-model.trim="remark"
                    data-key="remark" maxlength=32 />
            </view>
            <view class="input-row" style="margin-top:200upx;">
                <button type="primary" class="primary btn_dark bottom-but" @click="saveProfession" :disabled="saveFlag">保存</button>
                <button v-if="hasDelete" type="default" class="primary btn_white bottom-but" @click="deleteProfession"
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
                "id": "",
                "district": "",
                "province": "",
                "city": "",
                "country": "",
                "detail": "",
                "zipcode": "",
                remark: "",
                showTitle: "联系地址",
                dialogTitle: "请选择联系地址",
                addArry: {},
                flag: false,
                "showAddList": [],
                typeIndex: 0,
                typeArr: [],
                saveFlag: false,
                deleteFlag: false,
                hasDelete:false
            }
        },
        onLoad(option) {
            //加载基础数据
            // this.nameArr = cache.getvaluetokey('POLITICS_NAME');
            if (null != option) {
                console.log(option.id);
                this.id = option.id;
                if(this.id != 0){
                  this.hasDelete = true;  
                }
                
            }
        },
        onShow() {
            //获取类型基础数据
            let typeArrTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IS_NATION)
            if (typeArrTem.length != 0) {
                this.typeArr = typeArrTem;
            } else {
                this.typeArr = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.IS_NATION)
            }
            
            this.addArry = uni.getStorageSync("multipleLinkageAddress").childs;
            if (null != this.id && this.id != '' && this.id != 0) {
                //获取地址基础信息
                request.http({
                    url: '/app/address/getByid',
                    
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            // console.log("成功获取地址基础数据")
                            this.district = res.data.data.district;
                            this.province = res.data.data.province;
                            this.city = res.data.data.city;
                            this.country = res.data.data.country;
                            this.detail = res.data.data.detail;
                            this.zipcode = res.data.data.zipcode;
                            this.remark = res.data.data.remark;
                            let type = res.data.data.type;
                            if(type != null && type != ""){
                                for (let i = 0; i < this.typeArr.length; i++) {
                                    if (this.typeArr[i].value == type) {
                                        this.typeIndex = i
                                    }
                            }
                            }
                            
                            let countryTem = '';
                            if (null != this.country && '' != this.country) {
                                countryTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.country);
                            }
                            //countryTem = (null != countryTem || "" != countryTem ||"undefined"!=countryTem) ? countryTem : "";
                            let cityTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.city);
                            if (null != this.city && '' != this.city) {
                                cityTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.city);
                            }
                            // cityTem = (null != cityTem || "" != cityTem ||"undefined"!=cityTem) ? cityTem : "";
                            let provinceTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.province);
                            if (null != this.province && '' != this.province) {
                                provinceTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this
                                    .province);
                            }
                            // provinceTem = (null != provinceTem || "" != provinceTem ||"undefined"!=provinceTem) ? provinceTem : "";
                            let districtTem = ''; //loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.district);
                            //districtTem = (null != districtTem || "" != districtTem ||"undefined"!=districtTem) ? districtTem : "";							 
                            if (null != this.district && '' != this.district) {
                                districtTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this
                                    .district);
                            }
                            this.showTitle = countryTem +  provinceTem+  cityTem+ districtTem;
                            this.showAddList = [{
                                'id': this.country,
                                'name': countryTem
                            }, {
                                'id': this.province,
                                'name': provinceTem
                            }, {
                                'id': this.city,
                                'name': cityTem
                            }, {
                                'id': this.district,
                                'name': districtTem
                            }]
                        }
                    },
                    fail: () => {},
                    complete: () => {
                        this.flag = true;
                    }
                })
            }else{
                 this.flag = true;
            }
        },
        methods: {
            bindPickerChangeType(e){
              this.typeIndex = e.target.value  
            },
            savedata(e) {
                console.log(e);
                this.showTitle = e[5]
                this.country = e[0];
                this.province = e[1];
                this.city = e[2];
                this.district = e[3];
            },
            saveProfession() {
                this.saveFlag = true;
                if (null == this.country || '' == this.country) {
                    this.saveFlag = false;
                    uni.showToast({
                        icon: 'none',
                        title: '请选择地址'
                    })
                    return false;
                }
                if (null == this.detail || '' == this.detail) {
                    this.saveFlag = false;
                    uni.showToast({
                        icon: 'none',
                        title: '请输入详细地址'
                    })
                    return false;
                }
                request.http({
                    url: "/app/address/save",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                        "remark": this.remark,
                        "district": this.district,
                        "province": this.province,
                        "city": this.city,
                        "country": this.country,
                        "detail": this.detail,
                        "zipcode": this.zipcode,
                        "type": this.typeArr[this.typeIndex].value, 
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.professionList = res.data.data;
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
                    url: "/app/address/delete",
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.showToast({
                                icon: "none",
                                title: "删除成功"
                            })
                            uni.navigateBack({
                                url: "addr"
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
        }
    }
</script>

<style>
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
