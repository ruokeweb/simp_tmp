import request from '@/common/request.js';
import app from '@/common/app.js';

module.exports = {
    //获取基础数据
    getcache: function() {
        uni.request({
            url: app.serverPath + '/login/loadCacheMap.json',
            // request.ajax({
            //url: '/loadCacheMap',
            Method: "GET",
            success: (res) => {
                if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                    let json = res.data.data.dictCache;
                    uni.setStorageSync("basedata", json);
                    //教育类型
                    let EDU_TYPE = json.EDU_TYPE;
                    uni.setStorageSync("EDU_TYPE", EDU_TYPE);
                    //教育类型
                    let EDU_MODEL = json.EDU_MODEL;
                    uni.setStorageSync("EDU_MODEL", EDU_MODEL);
                    //民族
                    let NATION = json.NATION;
                    uni.setStorageSync("NATION", NATION);
                    //卡片显示类型
                    let CARD_ELEMENT_TYPE = json.CARD_ELEMENT_TYPE;
                    uni.setStorageSync("CARD_ELEMENT_TYPE", CARD_ELEMENT_TYPE);
                    //地区类型
                    let ASSOCIATION_REGION_TYPE = json.ASSOCIATION_REGION_TYPE;
                    uni.setStorageSync("ASSOCIATION_REGION_TYPE", ASSOCIATION_REGION_TYPE);
                    //校友卡类型
                    let CARD_STATUS = json.CARD_STATUS;
                    uni.setStorageSync("CARD_STATUS", CARD_STATUS);
                    //MES_SENDTYPE
                    let MES_SENDTYPE = json.MES_SENDTYPE;
                    uni.setStorageSync("MES_SENDTYPE", MES_SENDTYPE);
                    //SEX
                    let SEX = json.SEX;
                    uni.setStorageSync("SEX", SEX);
                    //IDCARD_TYPE
                    let IDCARD_TYPE = json.IDCARD_TYPE;
                    uni.setStorageSync("IDCARD_TYPE", IDCARD_TYPE);
                    //EDU_RECORD
                    let EDU_RECORD = json.EDU_RECORD;
                    uni.setStorageSync("EDU_RECORD", EDU_RECORD);
                    //校友类型
                    let SCHOOLEMATE_TYPE = json.SCHOOLEMATE_TYPE;
                    uni.setStorageSync("SCHOOLEMATE_TYPE", SCHOOLEMATE_TYPE);
                    //校友家庭成员类型
                    let FAMLIY_RELATION = json.FAMLIY_RELATION;
                    uni.setStorageSync("FAMLIY_RELATION", FAMLIY_RELATION);
                    //联系方式类型
                    let CONTACT_TYPE = json.CONTACT_TYPE;
                    uni.setStorageSync("CONTACT_TYPE", CONTACT_TYPE);
                }
            },
            fail: (res) => {
                uni.showToast({
                    icon: 'none',
                    title: '获取基础数据失败！'
                });
            }
        })
    },
    //根据类型获取基础数据并进行装箱处理
    getbasedate: function(key) {
        let value = uni.getStorageSync("basedata");
        if (value == "") {
            this.getcache();
        } else {
            let itemVlue = value[key];
            uni.setStorageSync(key, itemVlue);
            if (itemVlue == "") {
                this.getcache();
            }
        }
        return uni.getStorageSync(key);
    },
    
    //根据typeCode获字典基础数据
    getvaluetokey: function(typeCode) {
        let value1 = uni.getStorageSync(typeCode);
        if (value1 == "") {
            value1 = this.getbasedate(key);
        }
        return value1;
    },
    
    //-----------------------------------------------------------------以下为生产环境---------------------------------------------
    /*通过typeCode 获取下面的dict对象集合(字典表)*/
    getDictsByTypeCode: function(typeCode){
        return uni.getStorageSync(app.CACHE_NAME.DICT)[typeCode];
    },
    
    /*通过 value 获取 该字典对象((字典表))*/
    getDictByvalue: function(value, typeCode) {
        if (value.length == 0 || typeCode.length == 0) {
            return "";
        } else {
            let dicts = uni.getStorageSync(app.CACHE_NAME.DICT)[typeCode];
            for (var i = 0; i < dicts.length; i++) {
                if (value === dicts[i].value) {
                    return dicts[i];
                }
            }
        }
    },
    
    /* 通过id从缓存中获取该对象（学科、院系专业等）*/
    getObjFromStorageById: function(cacheName,id){
        var obj =  uni.getStorageSync(cacheName)[id];
        return obj;
    }
}
