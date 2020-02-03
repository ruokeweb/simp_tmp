<template>
    <view class="content">
        <view class="content_view">
            <view class="content_view_item">
                <text class="text_reg">学籍信息</text>
                <multiple-linkage style='width:158rpx;float:right;margin-top: 16upx;' :showtitle="showTitle" :flist='eduArry'
                    :dialogTitle="dialogTitle" :showValues="showValueList" :type="type" @saveclick="savedata" v-if="flag">
                </multiple-linkage>
            </view>
            <view class="content_view_item">
                <text class="text_reg">学科</text>
                <!-- <input class="input_reg" placeholder="请输入" placeholder-style="color:#ccc;" v-model.trim="position" data-key="position"
				 maxlength=32 /> -->
                <multiple-linkage style='width:158rpx;float:right;margin-top: 20upx;' :showtitle="showeduTitle" :flist='degreeTypeArry'
                    :dialogTitle="dialogeduTitle" :showValues="showDegreeList" :type="type" @saveclick="savedata1" v-if="eduflag">
                </multiple-linkage>
            </view>

            <view class="content_view_item">
                <text class="text_reg">班级</text>
                <input class="input_reg" placeholder="请输入班级" placeholder-class="placeholderStyle" v-model.trim="classes"
                    data-key="classes" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">学历</text>
                <picker class="input_reg" @change="bindPickerEduRecord" :value="eduRecordIndex" :range="eduRecordArry"
                    range-key="label">
                    <view class="uni-input">{{eduRecordArry[eduRecordIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">学位类型</text>
                <picker class="input_reg" @change="bindPickerDegree" :value="degreeIndex" :range="degreeArry" range-key="label">
                    <view class="uni-input">{{degreeArry[degreeIndex].label}}</view>
                </picker>
                </picker>
            </view>

            <view class="content_view_item">
                <text class="text_reg">学号</text>
                <input class="input_reg" placeholder="请输入学号" placeholder-class="placeholderStyle" v-model.trim="studentNo"
                    data-key="studentNo" maxlength=32 />
            </view>
            <view class="content_view_item">
                <text class="text_reg">学制</text>
                <picker class="input_reg" @change="bindPickerSchoollen" :value="schoollenIndex" :range="schoollenArry"
                    range-key="label">
                    <view class="uni-input">{{schoollenArry[schoollenIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">教育方式</text>
                <picker class="input_reg" @change="bindPickerEduModel" :value="eduModelIndex" :range="eduModelArry"
                    range-key="label">
                    <view class="uni-input">{{eduModelArry[eduModelIndex].label}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">培养方式</text>
                <picker class="input_reg" @change="bindPickerEduType" :value="eduTypeIndex" :range="eduTypeArry"
                    range-key="label">
                    <view class="uni-input">{{eduTypeArry[eduTypeIndex].label}}</view>
                </picker>
            </view>

            <view class="content_view_item">
                <text class="text_reg">入学日期</text>
                <picker class="input_reg" @change="bindPickerStartdate" :value="startdate"   mode=date >
                    <view class="uni-input">{{null == startdate?'':startdate}}</view>
                </picker>
            </view>
            <view class="content_view_item">
                <text class="text_reg">毕业日期</text>
                <picker class="input_reg" @change="bindPickerEnddate" :value="enddate"   mode=date>
                    <view class="uni-input">{{null == enddate?'':enddate}}</view>
                </picker>
            </view>

            <view class="input-row" style="margin-top:200upx;">
                <button type="primary" class="primary btn_dark bottom-but" @click="saveProfession" :disabled="saveFlag">保存</button>
                <button v-if="'0' != id" type="default" class="primary btn_white bottom-but" @click="deleteProfession"
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
                id: "0",
                classes: "",
                eduRecord: "", //记录会显的值
                eduRecordIndex: 0,
                eduRecordArry: [],
                studentNo: "",
                degreeIndex: 0,
                degreeArry: [],
                showValueList: [],
                showDegreeList: [],
                eduModelArry: [],
                eduModelIndex: 0,
                schoollenArry: [],
                schoollenIndex: 0,
                eduTypeIndex: 0,
                eduTypeArry: [],
                startdate: "",
                enddate: "",
                degreeTypeArry: [],
                showTitle: "教育经历",
                schoolId: "",
                collegeId: "",
                seriesId: "",
                specialtyId: "",
                degreeTypeId: "",
                dialogTitle: "请选择教育经历",
                eduArry: {},
                showeduTitle: "学科",
                dialogeduTitle: "请选择学科",
                eduflag: false,
                flag: false,
                saveFlag: false,
                deleteFlag: false
            }
        },
        onLoad(options) {
            //加载基础数据
            if (null != options) {
                this.id = JSON.parse(decodeURIComponent(options.id));
            }

        },
        methods: {
            savedata(e) {
                this.showTitle = e[5]
                this.schoolId = e[0];
                this.collegeId = e[1];
                this.seriesId = e[2];
                this.specialtyId = e[3];
            },
            savedata1(e) {
                this.showeduTitle = e[5]
                if (null != e[3] && "" != e[3]) {
                    this.degreeTypeId = e[3];
                } else if (null != e[2] && "" != e[2]) {
                    this.degreeTypeId = e[2];
                } else if (null != e[1] && "" != e[1]) {
                    this.degreeTypeId = e[1];
                } else if (null != e[0] && "" != e[0]) {
                    this.degreeTypeId = e[0];
                }
            },
            bindPickerEduRecord(e) {
                this.eduRecordIndex = e.target.value;
            },
            bindPickerDegree(e) {
                this.degreeIndex = e.target.value;
            },
            bindPickerEduModel(e) {
                this.eduModelIndex = e.target.value;
            },
            bindPickerSchoollen(e){
                this.schoollenIndex = e.target.value;
            },
            bindPickerStartdate(e) {
                this.startdate = e.target.value;
            },
            bindPickerEnddate(e) {
                this.enddate = e.target.value;
            },
            bindPickerEduType(e) {
                this.eduTypeIndex = e.target.value;
            },
            saveProfession() {
              
                this.saveFlag = true;
                if (null == this.schoolId || '' == this.schoolId || null == this.collegeId || '' == this.collegeId) {
                    this.saveFlag =false;
                    uni.showToast({
                        icon: "none",
                        title: "请选择学籍信息"
                    })
                    return false;
                }
                request.http({
                    header: 'application/x-www-form-urlencoded',
                    url: "/app/edu/saveEduInfo",
                    data: {
                        id: this.id,
                        degreeType: this.degreeTypeId,
                        //教育模式
                        eduModel: this.eduModelArry[this.eduModelIndex].value,
                        //学历
                        eduRecord: this.eduRecordArry[this.eduRecordIndex].value,
                        //学制
                        schoollen: this.schoollenArry[this.schoollenIndex].value,
                        //培养方式eduModelArry[eduModelIndex]
                        eduType: this.eduTypeArry[this.eduTypeIndex].value,
                        //学校
                        school: this.schoolId,
                        //学院")
                        college: this.collegeId,
                        //专业")
                        specialty: this.specialtyId,
                        series: this.seriesId, //系
                        //班级
                        classes: null == this.classes ? '' : this.classes,
                        //毕业时间",format="yyyy-MM-dd"
                        enddate: null == this.enddate ? '' : this.enddate,
                        //入学时间",format="yyyy-MM-dd"
                        startdate: null == this.startdate ? '' : this.startdate,
                        //学号
                        studentNo: null == this.studentNo ? '' : this.studentNo,
                        //学位
                        degree: this.degreeArry[this.degreeIndex].value
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
                        this.saveFlag = false;
                    }
                })
            },
            deleteProfession() {
                
                this.deleteFlag = true;
                request.http({
                    header: 'application/x-www-form-urlencoded',
                    url: "/app/edu/delEduInfo",
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
                        this.deleteFlag =false;
                    },
                    fail: (data, code) => {
                        this.deleteFlag =false;
                    }
                })
            }

        },
        onShow() {
            //获取学籍信息
            // console.log(uni.getStorageSync("multipleLinkageDepartments").childs);
            this.eduArry = uni.getStorageSync("multipleLinkageDepartments").childs;
            // this.flag = true;
            //获取学科信息	MULTIPLELINKAGESUBJECT:'multipleLinkageSubject'
            this.degreeTypeArry = uni.getStorageSync("multipleLinkageSubject").childs;
            // this.eduflag = true;
            //学历
            let eduRecordArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            if (eduRecordArryTem.length != 0) {
                this.eduRecordArry = eduRecordArryTem;
            } else {
                this.eduRecordArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_RECORD)
            }
            //学位类型
            let degreeArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_DEGREE)
            if (degreeArryTem.length != 0) {
                this.degreeArry = degreeArryTem;
            } else {
                this.degreeArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_DEGREE)
            }
            //学制
            let schoollenArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_SCHOOLEN)
            if (schoollenArryTem.length != 0) {
                this.schoollenArry = schoollenArryTem;
            } else {
                this.schoollenArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_SCHOOLEN)
            }
            //教育方式
            let eduModelArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_MODEL)
            if (eduModelArryTem.length != 0) {
                this.eduModelArry = eduModelArryTem;
            } else {
                this.eduModelArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_MODEL)
            }
            //培养方式
            let eduTypeArryTem = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_TYPE)
            if (eduTypeArryTem.length != 0) {
                this.eduTypeArry = eduTypeArryTem;
            } else {
                this.eduTypeArry = loadcache.getDictsByTypeCode(app.DICT_TYPECODE.EDU_TYPE)
            }

            if (this.id != '0') {
                //获取教育经历
                request.http({
                    url: '/app/edu/getEduInfo',
                    header: 'application/x-www-form-urlencoded',
                    data: {
                        id: this.id,
                    },
                    success: (res) => {
                        //  let list = this.setTime(res.data.data);
                        if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                            this.studentNo = res.data.data.studentNo; //学号
                            this.classes = res.data.data.classes; //班级
                            this.startdate = res.data.data.startdate; //入学时间
                            this.enddate = res.data.data.enddate; //毕业时间

                            let eduRecord = res.data.data.eduRecord; //学历
                            for (let i = 0; i < this.eduRecordArry.length; i++) {
                                if (this.eduRecordArry[i].value == eduRecord) {
                                    this.eduRecordIndex = i
                                }
                            }
                            let degree = res.data.data.degree; //学位
                            for (let i = 0; i < this.degreeArry.length; i++) {
                                if (this.degreeArry[i].value == degree) {
                                    this.degreeIndex = i
                                }
                            }
                            let schoollen = res.data.data.schoollen; //学制
                            for (let i = 0; i < this.schoollenArry.length; i++) {
                                if (this.schoollenArry[i].value == schoollen) {
                                    this.schoollenIndex = i
                                }
                            }

                            let eduType = res.data.data.eduType; //培养方式
                            for (let i = 0; i < this.eduTypeArry.length; i++) {
                                if (this.eduTypeArry[i].value == eduType) {
                                    this.eduTypeIndex = i
                                }
                            }
                            
                            let eduModel = res.data.data.eduModel; //教育模式
                            for (let i = 0; i < this.eduModelArry.length; i++) {
                                if (this.eduModelArry[i].value == eduModel) {
                                    this.eduModelIndex = i
                                }
                            }
                            

                            let academy = res.data.data.academy; //
                            let series = res.data.data.series; //系
                            this.seriesId = series;
                            let type = res.data.data.type; //
                            let school = res.data.data.school; //学校
                            this.schoolId = school;
                            let specialty = res.data.data.specialty; //专业
                            this.specialtyId = specialty;
                            let college = res.data.data.college; //学院
                            this.collegeId = college;


                            if (null != specialty && specialty != "") {
                                var school_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    school).name;
                                var college_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    college).name;
                                var series_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    series).name;
                                var specialty_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    specialty).name;
                                this.showTitle = school_name + "-" + college_name + "-" + series_name + "-" +
                                    specialty_name;
                                this.showValueList = [{
                                    'id': school,
                                    'name': school_name
                                }, {
                                    'id': college,
                                    'name': college_name
                                }, {
                                    'id': series,
                                    'name': series_name
                                }, {
                                    'id': specialty,
                                    'name': specialty_name
                                }];
                            } else if (null != series && series != "") {
                                var school_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    school).name;
                                var college_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    college).name;
                                var series_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    series).name;
                                this.showTitle = school_name + "-" + college_name + "-" + series_name;
                                this.showValueList = [{
                                    'id': school,
                                    'name': school_name
                                }, {
                                    'id': college,
                                    'name': college_name
                                }, {
                                    'id': series,
                                    'name': series_name
                                }];
                            } else if (college != "" && college != null) {
                                var school_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    school).name;
                                var college_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    college).name;
                                this.showTitle = school_name + "-" + college_name;
                                this.showValueList = [{
                                    'id': school,
                                    'name': school_name
                                }, {
                                    'id': college,
                                    'name': college_name
                                }];
                            } else if (school != "" && school != null) {
                                var school_name = loadcache.getObjFromStorageById(app.CACHE_NAME.DEPARTMENTBASE,
                                    school).name;
                                this.showTitle = school_name;
                                this.showValueList = [{
                                    'id': school,
                                    'name': school_name
                                }];
                            }
                            this.degreeTypeId = res.data.data.degreeType; //学科
                            let arrTem = [];
                            let parentid = "0";
                            let degreeStr = this.degreeTypeId;
                            //循环取出学院的父节点
                            this.showeduTitle = "";
                            if (null != degreeStr && degreeStr != "") {
                                do {
                                    let degreeObj = loadcache.getObjFromStorageById(app.CACHE_NAME.SETTINGSUBJECTBASE,
                                        degreeStr);
                                    parentid = degreeObj.parentId;
                                    arrTem.push(degreeObj)
                                    degreeStr = degreeObj.parentId;
                                } while (parentid != "0");

                                for (let j = arrTem.length - 1; j >= 0; j--) {
                                    this.showDegreeList.push({
                                        'id': arrTem[j].id,
                                        'name': arrTem[j].name
                                    })
                                    if (j == arrTem.length-1) {
                                        this.showeduTitle = arrTem[j].name;
                                    } else {
                                        this.showeduTitle = this.showeduTitle +'-'+arrTem[j].name ;
                                    }
                                }
                            }
                        }
                    },
                    fail: (data, code) => {
                        console.log(data);
                    },
                    complete: () => {
                        this.eduflag = true;
                        this.flag = true;
                    }
                })
            }else{
                this.eduflag = true;
                this.flag = true;
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
        /* width: 15%; */
        font-size: $uni-font-size-base;
        display: inline-block;
        color: #333;
        /* // padding-left: 20upx; */
        position: relative;
    }

    .input_reg {
        width: 60% !important;
/*        float: right;
        margin-top: 18upx;
        text-align: right;
        margin-right: 20upx; */
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
