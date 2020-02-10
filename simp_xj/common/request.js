/* 自行封装request请求 */
import app from '@/common/app.js';
import util from '@/common/util.js';
import store from '@/store/index.js';

const http = (opt) => {
    var keyTime = new Date().getTime().toString();
    opt = opt || {};
    opt.url = opt.url || '';
    opt.data = opt.data || null;
    opt.dataType = opt.dataType || 'json';
    opt.method = opt.method || 'POST';
    opt.responseType = opt.responseType || 'text';
    opt.header = {
        "Content-Type": opt.header,
        'Authorization': store.state.token,
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    } || {
        "Content-Type": "application/json",
        'Authorization': store.state.token,
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    };
    opt.success = opt.success || function() {};
    opt.error = opt.error || function() {};

    uni.request({
        url: opt.url,
        data: opt.data,
        method: opt.method,
        header: opt.header,
        dataType: opt.dataType,
        responseType: opt.responseType,
        success: function(res) {
            opt.success(res);
        },
        fail: function() {
            opt.error(res);
        }
    })

}

/**刷新用户信息**/
const refreshUser = (opt) => {
    http({
        url: app.serverPath+"/app/auth/getUser",
        data: {},
        header: 'application/x-www-form-urlencoded',
        success: (res) => {
            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                //刷新整体缓存
                uni.setStorageSync(app.storageKey.userInfo,res.data.data);
            }
        },
        fail: (data, code) => {
            console.log(data);
        }
    })
}

/**增加积分记录**/
const handleCredit = (operate,type,credit) =>{
    http({
        url: app.serverPath+"/app/mine/handleCredit",
        data: {
            'operate':operate,
            'type':type,
            'credit':credit,
            'accountId':uni.getStorageSync(app.storageKey.userInfo).sysAccount.id
        },
        header: 'application/x-www-form-urlencoded',
        success: (res) => {
            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {                
                //刷新整体缓存
                uni.setStorageSync(app.storageKey.userInfo,res.data.data);
            }else{
                
            }
        },
        fail: (data, code) => {
            console.log(data);
        }
    })
}

export default {
    http,
    refreshUser,
    handleCredit
}
