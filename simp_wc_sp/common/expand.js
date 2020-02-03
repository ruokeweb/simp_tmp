/**
 * 扩展的相关JS
 * @param {Object} sharePoint
 */
import app from '@/common/app.js'; //公共变量
import request from '@/common/request.js';

//分享操作
function saveShare(sharePoint){
    request.http({
        url: '/app/friShare/save',
        header: 'application/x-www-form-urlencoded',
        data:{
            sharePoint: sharePoint
        },
        success: (res) => {
            console.log(res);
        },
        fail: () => {

        }
    })
}


module.exports = {
    saveShare: saveShare
}