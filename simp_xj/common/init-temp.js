import app from '@/common/app.js';

module.exports = {
    loadBaseDatas: () => {
        return new Promise((resolve, reject) => {
            //获取基本编码信息
            uni.request({
                url: app.serverPath + '/app/auth/getBaseDatas', //仅为示例，并非真实接口地址。
                method: 'POST',
                success: (res) => {
                    //设置初始化数据
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        let cache = res.data.data;
                        try {
                            uni.setStorageSync(app.CACHE_NAME.DICT, cache.dictCache);
                            resolve('success');
                        } catch (e) {
                            console.log(e);
                            reject('error');
                        }
                    } else {
                        uni.showToast({
                            icon: 'none',
                            title: res.data.msg
                        });
                        reject('error');
                    }
                },
                fail() {
                    uni.showToast({
                        icon: 'none',
                        title: "网络连接错误"
                    });
                    reject('error');
                }
            });          
        })
    },
    autoLogin: () => {
        return new Promise((resolve, reject) => {
            //判断登录状态
            var token=uni.getStorageSync('token');//token返回的是字符串
            var userInfo=JSON.stringify(uni.getStorageSync(app.storageKey.userInfo));
            if(userInfo=""||token==""){
                //获取Openid
                uni.getProvider({
                    service: 'oauth',
                    success: (res) => {
                        if (~res.provider.indexOf('weixin')) {
                            uni.login({
                                provider: 'weixin',
                                success: (loginRes) => {
                                    this.WxCode = loginRes.code;
                                    //获取用户openid
                                    uni.request({
                                        url: app.serverPath + "/app/auth/getOpenId",
                                        data: {
                                            'code': this.WxCode
                                        },
                                        method:'POST',
                                        header: {
                                            'Content-Type':'application/x-www-form-urlencoded'
                                        },
                                        success: (res) => {
                                            if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                                uni.setStorageSync('openId', res.data.data.openId);
                                                //使用openid登录
                                                uni.request({
                                                	url: app.serverPath+"/app/auth/autoLogin",
                                                	data: {
                                                        'openId':res.data.data.openId
                                                    },
                                                    method:'POST',
                                                	header: {
                                                	    'Content-Type':'application/x-www-form-urlencoded'
                                                	},
                                                	success: (loginRes) => {
                                                        //装入token与基本用户信息
                                                		if (loginRes.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                                                            uni.setStorageSync('token', loginRes.data.msg);
                                                            uni.setStorageSync(app.storageKey.userInfo,loginRes.data.data);
                                                		} 
                                                	},
                                                	fail: (data, code) => {
                                                		console.log(data);
                                                	}
                                                });
                                            }
                                        },
                                        fail: () => {}
                                    })
                                }
                            });
                        }
                    }
                })
            }
        })
    }
}