<template name="multiple-linkage">
    <view class="mpvue-picker" style="background-color: #FFFFFF;z-index: 999;">
        <view catchtouchmove="true" @click="showPickerClick" class="showTitle" style="font-size: 28rpx;text-align:right;margin-left:-15rpx;">{{showtitle}}</view>
        <view style="display: none;">{{pickertype}}</view>
        <view class="mpvue-picker-content " :class="{'mpvue-picker-view-show':showPicker}">
            <view class="mpvue-picker__hd" catchtouchmove="true">
                <text style="width: 80%;float: left; color: #555; text-align: left;" @tap="cancel">取消</text>
                <text style="width: 15%;float: right; color: #007AFF;" @tap="savedata">保存</text>
            </view>
            <view style="width: 100%; float: none;height: 70rpx;background:#fff;" >
                <view @click="itemClik(0)" :class="cellIndex==0 && showValue0!='' ? 'option-text cur' : 'option-text' ">{{null == showValue0.name || ''==showValue0.name?'':showValue0.name}}</view>
                <view @click="itemClik(1)" :class="cellIndex==1 && showValue1!='' ? 'option-text cur' : 'option-text' ">{{null == showValue1.name || ''==showValue1.name?'':showValue1.name}}</view>
                <view @click="itemClik(2)" :class="cellIndex==2 && showValue2!='' ? 'option-text cur' : 'option-text' ">{{null == showValue2.name || ''==showValue2.name?'':showValue2.name}}</view>
                <view @click="itemClik(3)" :class="cellIndex==3 && showValue3!='' ? 'option-text cur' : 'option-text' ">{{null == showValue3.name || ''==showValue3.name?'':showValue3.name}}</view>
            </view>
            <view>
                <view>
                    <picker-view indicator-style="height: 40px;" class="mpvue-picker-view" @change="changeview">
                        <picker-view-column>
                            <view class="picker-item" v-for="(item,key) in itemlist" :key="key" @click="pickerItemClik(key)">
                                {{ item.name}}
                            </view>
                        </picker-view-column>
                    </picker-view>
                </view>
            </view>
        </view>
    </view>
</template>

<script>
    import cache from '@/common/loadcache.js';

    let templatelist = ["", "", "", ""]
    export default {
        name: "multiple-linkage",
        props: {
            showtitle: {
                type: String,
                default: "默认显示标题"
            },
            dialogTitle: {
                type: String,
                default: "弹框默认标题"
            },
            pickertype: {
                type: String,
                default: ""
            },
            flist: {
                type: Array,
                default: () => []
            },
            showValues: {
                type: Array,
                default: () => []
            }
        },
        data() {
            return {
                returnData: ["", "", "", "", "", ""], //默认返回编码值
                showValue0: "",
                showValue1: "",
                showValue2: "",
                showValue3: "",
                showValue0Index: 0,
                showValue1Index: 0,
                showValue2Index: 0,
                showValue3Index: 0,
                showPicker: false,
                itemlist: [],
                cellIndex: 0,
                picindex: 0
            };
        },
        created() {
            this.itemlist = this.flist;
            var showValueList = this.showValues;
            console.log("组件页");
            console.log(this.returnData);
            if (this.showValues.length == 4) {
                this.showValue0 = showValueList[0];
                this.showValue1 = showValueList[1];
                this.showValue2 = showValueList[2];
                this.showValue3 = showValueList[3];
                this.returnData[0] = showValueList[0].id;
                this.returnData[1] = showValueList[1].id;
                this.returnData[2] = showValueList[2].id;
                this.returnData[3] = showValueList[3].id;
                this.showValue0Index = this.getIndex(this.flist, this.showValues[0].id);
                this.showValue1Index = this.getIndex(this.flist[this.showValue0Index].childs, this.showValues[1].id);
                this.showValue2Index = this.getIndex(this.flist[this.showValue0Index].childs[this.showValue1Index].childs, this.showValues[2].id);
                this.showValue3Index = this.getIndex(this.flist[this.showValue0Index].childs[this.showValue1Index].childs[this.showValue2Index].childs, this.showValues[3].id);
                this.itemlist = this.flist[this.showValue0Index].childs[this.showValue1Index].childs[this.showValue2Index].childs;
                this.cellIndex = 3;
            } else if (this.showValues.length == 3) {
                this.showValue0 = showValueList[0];
                this.showValue1 = showValueList[1];
                this.showValue2 = showValueList[2];
                this.returnData[0] = showValueList[0].id;
                this.returnData[1] = showValueList[1].id;
                this.returnData[2] = showValueList[2].id;
                this.showValue0Index = this.getIndex(this.flist, this.showValues[0].id);
                this.showValue1Index = this.getIndex(this.flist[this.showValue0Index].childs, this.showValues[1].id);
                this.showValue2Index = this.getIndex(this.flist[this.showValue0Index].childs[this.showValue1Index].childs, this.showValues[2].id);
                this.itemlist = this.flist[this.showValue0Index].childs[this.showValue1Index].childs;
                this.cellIndex = 2;
            } else if (this.showValues.length == 2) {
                this.showValue0 = showValueList[0];
                this.showValue1 = showValueList[1];
                this.returnData[0] = showValueList[0].id;
                this.returnData[1] = showValueList[1].id;
                this.showValue0Index = this.getIndex(this.flist, this.showValues[0].id);
                this.showValue1Index = this.getIndex(this.flist[this.showValue0Index].childs, this.showValues[1].id);
                this.itemlist = this.flist[this.showValue0Index].childs;
                this.cellIndex = 1;
            } else if (this.showValues.length == 1) {
                this.showValue0 = showValueList[0];
                this.returnData[0] = showValueList[0].id;
                this.showValue0Index = this.getIndex(this.flist, this.showValues[0].id); 
            }
            console.log(this.itemlist);
        },
        methods: {
            showPickerClick() {
                this.showPicker = true;
            },
            cancel() {
                this.showPicker = false;
            },
            savedata(e) { //返回数据
                console.log("保存返回值的方法================================")
                this.showPicker = false;
                if (this.showValue0.length == 0) {
                    this.returnData[5] = "请选择";
                } else if (this.showValue1.length == 0) {
                    this.returnData[5] = this.showValue0.name
                } else if (this.showValue2.length == 0) {
                    this.returnData[5] = this.showValue0.name + "-" + this.showValue1.name;
                } else {
                    this.returnData[5] = this.showValue0.name + "-" + this.showValue1.name + "-" + this.showValue2.name +
                        "-" + this.itemlist[this.picindex].name;
                }
                console.log("==点击后的值=")
                console.log(this.returnData);
                this.$emit("saveclick", this.returnData);
            },
            changeview(e) {
                this.picindex = e.target.value;
                if (this.cellIndex == 0) {
                    this.showValue0Index = e.target.value;
                } else if (this.cellIndex == 1) {
                    this.showValue1Index = e.target.value;
                } else if (this.cellIndex == 2) {
                    this.showValue2Index = e.target.value;
                } else if (this.cellIndex == 3) {
                    this.showValue3Index = e.target.value;
                }
            },
            itemClik(index) {
                if (index == 0) {
                    this.itemlist = this.flist;
                    this.cellIndex = 0;
                    this.showValue0 = "";
                    this.showValue1 = "";
                    this.showValue2 = "";
                    this.showValue3 = "";
                    this.returnData = ["", "", "", "", "", ""];
                } else if (index == 1) {
                    if (this.flist[this.showValue0Index].childs.length > 0) {
                        this.itemlist = this.flist[this.showValue0Index].childs;
                        this.cellIndex = 1;
                        this.showValue1 = "";
                        this.showValue2 = "";
                        this.showValue3 = "";
                        this.returnData[1] = [""];
                        this.returnData[2] = [""];
                        this.returnData[3] = [""];
                    }

                } else if (index == 2) {
                    if (this.flist[this.showValue0Index].childs[this.showValue1Index].childs.length > 0) {
                        this.itemlist = this.flist[this.showValue0Index].childs[this.showValue1Index].childs;
                        this.cellIndex = 2;
                        this.showValue2 = "";
                        this.showValue3 = "";
                        this.returnData[2] = [""];
                        this.returnData[3] = [""];
                    }
                } else if (index == 3) {
                    if (this.flist[this.showValue0Index].childs[this.showValue1Index].childs[this.showValue2Index].childs
                        .length > 0) {
                        this.itemlist = this.flist[this.showValue0Index].childs[this.showValue1Index].childs[this.showValue2Index]
                            .childs;
                        this.cellIndex = 3;
                        this.showValue3 = "";
                        this.returnData[3] = [""];
                    }
                }

            },
            // getitemdata(key) {
            //     let arra = this.flist.childs
            //     this.itemlist = arra
            // },
            pickerItemClik(index) {
                this.picindex = index
                this.showtitle = this.itemlist[index].name
                this.returnData[this.cellIndex] = this.itemlist[index].id
                if (this.cellIndex == 0) {
                    this.showValue0 = this.itemlist[index];
                    this.showValue0Index = index;
                    this.itemClik(this.cellIndex + 1)
                } else if (this.cellIndex == 1) {
                    this.showValue1Index = index;
                    this.showValue1 = this.itemlist[index];
                    this.itemClik(this.cellIndex + 1)
                } else if (this.cellIndex == 2) {
                    this.showValue2Index = index;
                    this.showValue2 = this.itemlist[index];
                    this.itemClik(this.cellIndex + 1)
                } else if (this.cellIndex == 3) {
                    this.showValue3Index = index;
                    this.showValue3 = this.itemlist[index];
                    this.cellIndex = 4;
                } else if (this.itemlist[index].childs.length > 0) {
                    this.itemlist = this.itemlist[index].childs;
                }

            },
            getdata(type) {
                console.log(type)
            },
            getIndex(list, id) {
                for (var i = 0; i < list.length;i++) {
                    if (list[i].id === id) {
                        return i;
                    }
                }
            }
        }
    }
</script>

<style>
    .orange{
        color: orange;
    }
    .pickerMask {
        position: fixed;
        z-index: 1000;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        background: rgba(0, 0, 0, 0.6);
    }

    .mpvue-picker-content {
        position: fixed;
        bottom: 0;
        left: 0;
        width: 100%;
        transition: all 0.3s ease;
        transform: translateY(100%);
        z-index: 3000;
    }

    .mpvue-picker-view-show {
        transform: translateY(0);
    }

    .mpvue-picker__hd {
        display: flex;
        padding: 9px 15px;
        background-color: #fff;
        position: relative;
        text-align: center;
        font-size: 17px;
        border-top: 1px solid #e5e5e5;
    }

    .mpvue-picker__hd:after {
        content: ' ';
        position: absolute;
        left: 0;
        bottom: 0;
        right: 0;
        height: 1px;
        border-bottom: 1px solid #e5e5e5;
        color: #e5e5e5;
        transform-origin: 0 100%;
        transform: scaleY(0.5);
    }

    .mpvue-picker__action {
        display: block;
        flex: 1;
        color: #1aad19;
    }

    .mpvue-picker__action:first-child {
        text-align: left;
        color: #888;
    }

    .mpvue-picker__action:last-child {
        text-align: right;
    }

    .picker-item {
        text-align: center;
        line-height: 40px;
        text-overflow: ellipsis;
        white-space: nowrap;
        font-size: 16px;
    }

    .mpvue-picker-view {
        position: relative;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 238px;
        background-color: rgba(255, 255, 255, 1);
    }

    .showTitle {
        width: 100%;
        height: 50upx;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #CCCCCC;
    }

    .option-text {
        float: left;
        margin-right: 8rpx;
        width: 23%;
        height: 50upx;
        color: orange;
        overflow: hidden;
        text-align: center;
    }

    .cur {
        border-bottom: 2px solid #272D3D;
    }
</style>
