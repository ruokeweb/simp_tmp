<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">工作单位</text>
                <input class="input_reg" placeholder="请输入工作单位" placeholder-class="placeholderStyle" v-model.trim="workplace"
                    data-key="workplace" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">单位性质</text>
                <!-- -->
                <picker class="input_reg" @change="bindPickerNature" :value="natureIndex" :range="natureArry" range-key="label">
                    <view class="uni-input">{{natureArry[natureIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">地址</text>
                <multiple-linkage style='width:158rpx;float:right;margin-top: 20upx' :showtitle="showaddTitle" :flist='addArry'
                    :dialogTitle="dialogaddTitle" :type="type" @saveclick="savedatadd" :showValues="showAddList" v-if="addflag">
                </multiple-linkage>
            </view>
            <view class="content_view_item">
                <text class="text_reg">详细地址</text>
                <input class="input_reg" placeholder="请输入详细地址" placeholder-class="placeholderStyle" v-model.trim="detail"
                    data-key="detail" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">行业</text>
                <!-- <picker class="input_reg" @change="bindPicker">
					<view class="uni-input">{{}}</view>proArry  showTitle
				</picker> -->
                <multiple-linkage style='width:158rpx;float:right;margin-top: 20upx' :showtitle="showTitle" :flist='proArry'
                    :dialogTitle="dialogTitle" :type="type" @saveclick="savedata" :showValues="showList" v-if="industryflag">
                </multiple-linkage>
            </view>
            <view class="content_view_item">
                <text class="text_reg">部门</text>
                <input class="input_reg" placeholder="请输入部门" placeholder-class="placeholderStyle" v-model.trim="department"
                    data-key="department" maxlength=32 />
            </view>

            <view class="content_view_item">
                <text class="text_reg">职位</text>
                <input class="input_reg" placeholder="请输入职位" placeholder-class="placeholderStyle" v-model.trim="position"
                    data-key="position" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">在职状态</text>
                <picker class="input_reg" @change="bindPickerStatus" :value="statusIndex" :range="statusArry" range-key="label">
                    <view class="uni-input">{{statusArry[statusIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">电话</text>
                <input class="input_reg" placeholder="请输入电话" placeholder-class="placeholderStyle" v-model.trim="telephone"
                    data-key="telephone" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">传真</text>
                <input class="input_reg" placeholder="请输入传真" placeholder-class="placeholderStyle" v-model.trim="fax" data-key="fax"
                    maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">入职时间</text>
                <picker class="input_reg" @change="bindPickerStartdate" :value="startdate" mode=date>
                    <view class="uni-input">{{startDate}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">离职时间</text>
                <picker class="input_reg" @change="bindPickerEnddate" :value="enddate" mode=date>
                    <view class="uni-input">{{enddate}}</view>
                </picker>
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
                id: '',
                workplace: "",
                industry: "",
                industryArry: [],
                industryIndex: 0,
                natureArry: [],
                natureIndex: 0,
                department: "",
                address: [],
                country: "",
                city: "",
                district: "",
                detail: "",
                province: "",
                position: "",
                statusArry: [],
                statusIndex: 0,
                telephone: "",
                fax: "",
                startDate: "",
                enddate: "",
                showTitle: "行业",
                dialogTitle: "请选择行业",
                proArry: {},
                showaddTitle: "联系地址",
                dialogaddTitle: "请选择联系地址",
                addArry: {},
                showAddList: [],
                industryflag: false,
                addflag: false,
                showList: [],
                saveFlag: false,
                deleteFlag: false,
                hasDelete: false
            }
        },
        onLoad(option) {
            //加载基础数据
            if (null != option) {
                if (option.id == undefined) {
                    this.id = '';
                } else {
                    this.id = option.id;
                    this.hasDelete = true;
                }
            }
        },
        onShow() {
            //在职状态
            let statusArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.PROFESSION_STATUS)
            if (statusArryTem.length != 0) {
                this.statusArry = statusArryTem;
            } else {
                this.statusArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.PROFESSION_STATUS)
            }

            //单位性质
            let natureArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.COMPANY_TPYE)
            if (null != natureArryTem && natureArryTem.length != 0) {
                this.natureArry = natureArryTem;
            } else {
                this.natureArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.COMPANY_TPYE)
            }

            //行业
            this.proArry = uni.getStorageSync("multipleLinkageIndustry").childs;

            //地址
            this.addArry = uni.getStorageSync("multipleLinkageAddress").childs;

            if (null != this.id && this.id != '') {
                //获取职业经历信息
                request.http({
                    url: '/app/profession/getProInfo',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                    },
                    success: (res) => {
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.id = res.data.data.id;
                            this.country = res.data.data.country;
                            this.province = res.data.data.province;
                            this.city = res.data.data.city;
                            this.district = res.data.data.district;
                            this.detail = res.data.data.detail;
                            this.workplace = res.data.data.workplace;
                            this.nature = res.data.data.nature;
                            this.industry = res.data.data.industry;
                            this.department = res.data.data.department;
                            this.position = res.data.data.position;
                            this.telephone = res.data.data.telephone;
                            this.fax = res.data.data.fax;
                            this.startDate = null != res.data.data.startDate ? res.data.data.startDate : "";
                            this.enddate = null != res.data.data.endDate ? res.data.data.endDate : "";
                            this.status = res.data.data.status;
                            //单位性质
                            let natureStr = res.data.data.nature;
                            for (let i = 0; i < this.natureArry.length; i++) {
                                if (this.natureArry[i].value == natureStr) {
                                    this.natureIndex = i
                                }
                            }
                            //在职状态
                            let statusStr = res.data.data.status;
                            for (let i = 0; i < this.statusArry.length; i++) {
                                if (this.statusArry[i].value == statusStr) {
                                    this.statusIndex = i
                                }
                            }
                            //地址回显
                            let countryTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.country);
                            countryTem = (null != countryTem && "" != countryTem && undefined != countryTem) ?
                                countryTem : "";
                            let cityTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this.city);
                            cityTem = (null != cityTem && "" != cityTem && undefined != cityTem) ? cityTem :
                                "";
                            let provinceTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this
                                .province);
                            provinceTem = (null != provinceTem && "" != provinceTem && undefined !=
                                provinceTem) ? provinceTem : "";
                            let districtTem = loadcache.getObjFromStorageById(app.CACHE_NAME.AREABASE, this
                                .district);
                            districtTem = (null != districtTem && "" != districtTem && undefined !=
                                districtTem) ? districtTem : "";
                            this.showaddTitle = countryTem +  provinceTem+  cityTem + districtTem;
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
                            this.flag = true;

                            //行业回显
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
                                for (var i = arrTem.length - 1; i >= 0; i--) {

                                    var obj = {
                                        'id': arrTem[i].id,
                                        'name': arrTem[i].name
                                    };
                                    this.showList.push(obj);
                                    this.showTitle = '';
                                    if (i == arrTem.length - 1) {
                                        this.showTitle = arrTem[i].name;
                                    } else {
                                        this.showTitle = arrTem[i].name + '-' + this.showTitle;
                                    }

                                }
                            }
                        }
                    },
                    fail: () => {},
                    complete: () => {
                        this.addflag = true;
                        this.industryflag = true;
                    }
                })
            } else {
                this.addflag = true;
                this.industryflag = true;
            }

        },
        methods: {
            savedata(e) {
                console.info(e);
                this.showTitle = e[5];
                if (null != e[3] && "" != e[3]) {
                    this.industry = e[3];
                } else if (null != e[2] && "" != e[2]) {
                    this.industry = e[2];
                } else if (null != e[1] && "" != e[1]) {
                    this.industry = e[1];
                } else if (null != e[0] && "" != e[0]) {
                    this.industry = e[0];
                } else {
                    this.industry = "";
                }

            },
            savedatadd(e) {
                console.info(e);
                this.showaddTitle = e[5]
                this.country = e[0]
                this.province = e[1]
                this.city = e[2]
                this.district = e[3]
            },
            bindPickerIndustry(e) {
                console.log(e)
                this.industryIndex = e.target.value
            },
            bindPickerNature(e) {
                this.natureIndex = e.target.value
            },
            bindPickerStatus(e) {
                this.statusIndex = e.target.value
            },
            bindPickerStartdate(e) {
                this.startDate = e.target.value
            },
            bindPickerEnddate(e) {
                this.enddate = e.target.value
            },
            saveProfession() {

                this.saveFlag = true;
                if (null == this.workplace || '' == this.workplace) {
                    this.saveFlag = false;
                    uni.showToast({
                        icon: "none",
                        title: "请输入工作单位"
                    })
                    return false;
                }
                if (null == this.industry || '' == this.industry) {
                    this.saveFlag = false;
                    uni.showToast({
                        icon: "none",
                        title: "请选择行业"
                    })
                    return false;
                }

                request.http({
                    url: '/app/profession/saveProInfo',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        "id": this.id,
                        "workplace": this.workplace,
                        "industry": this.industry,
                        "nature": this.natureArry[this.natureIndex].value,
                        "department": this.department,
                        "country": this.country,
                        "city": this.city,
                        "city": this.city,
                        "district": this.district,
                        "detail": this.detail,
                        "province": this.province,
                        "position": this.position,
                        "telephone": this.telephone,
                        "fax": this.fax,
                        "startDate": this.startDate,
                        "endDate": this.enddate,
                        "status": this.statusArry[this.statusIndex].value
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.navigateBack({

                            });
                            uni.showToast({
                                icon: "none",
                                title: "保存成功"
                            })
                            this.saveFlag = false;
                        }

                    },
                    fail: (data, code) => {
                        this.saveFlag = false;
                        console.log(data);
                    }
                })
            },
            deleteProfession() {

                this.deleteFlag = true;
                request.http({
                    url: '/app/profession/delete',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        "id": this.id,
                    },
                    success: (res) => {
                        if (res.code = app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            uni.showToast({
                                icon: "none",
                                title: "删除成功"
                            })
                            uni.navigateBack({
                                url: "profession"
                            })
                            this.deleteFlag = true;
                        }
                    },
                    fail: (data, code) => {
                        this.deleteFlag = true;
                        console.log(data);
                    }
                })
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
