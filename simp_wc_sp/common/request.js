/* 自行封装request请求 */
import app from '@/common/app.js';
import util from '@/common/util.js';

const ajax = (opt) => {
    var keyTime = new Date().getTime().toString();
    opt = opt || {};
    opt.url = opt.url || '';
    opt.data = opt.data || null;
    opt.dataType = opt.dataType || 'json';
    opt.method = opt.method || 'POST';
    opt.responseType = opt.responseType || 'text';
    opt.header = {
        "Content-Type": opt.header,
        'Authorization': uni.getStorageSync("token"),
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    } || {
        "Content-Type": "application/json",
        'Authorization': uni.getStorageSync("token"),
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    };
    opt.success = opt.success || function() {};
    opt.error = opt.error || function() {};
    opt.complete = opt.complete || function() {};
    uni.request({
        url: app.serverPath + opt.url,
        // url: opt.url,    //个人测试
        data: opt.data,
        method: opt.method,
        header: opt.header,
        dataType: opt.dataType,
        success: function(res) {
            opt.success(res);
        },
        fail: function(res) {
            opt.error(res);
        },
        complete: function(res) {
            opt.complete(res);
        }
    })

}
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
        'Authorization': wx.getStorageSync("token"),
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    } || {
        "Content-Type": "application/json",
        'Authorization': wx.getStorageSync("token"),
        'Time': keyTime,
        'Key': util.Encrypt(app.AES_KEY, keyTime)
    };
    opt.success = opt.success || function() {};
    opt.error = opt.error || function() {};
    opt.complete = opt.complete || function() {};
    uni.request({
        url: app.serverPath + opt.url,
        data: opt.data,
        method: opt.method,
        header: opt.header,
        dataType: opt.dataType,
        responseType: opt.responseType,
        success: function(res) {
            opt.success(res);
        },
        fail: function(res) {
            opt.error(res);
        },
        complete: function(res) {
            opt.complete(res);
        }
    })

}
export default {
    ajax,
    http
}
