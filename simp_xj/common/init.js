import app from '@/common/app.js';
//import store from '@/store/index.js';

function getBaseDatas() {
	//获取基本编码信息
	return new Promise(function(resolve, reject) {
		uni.request({
			url: app.serverPath + '/app/auth/getBaseDatas', //仅为示例，并非真实接口地址。
			method: 'POST',
			success: (res) => {
				//设置初始化数据
				resolve(res);
			},
			fail() {
				uni.showToast({
					icon: 'none',
					title: "网络连接错误!"
				});
				reject('error');
			}
		});
	})
}

function getOpenId() {
	return new Promise(function(resolve, reject) {
		uni.login({
			provider: 'weixin',
			success: (loginRes) => {
				var WxCode = loginRes.code;
				//获取用户openid
				uni.request({
					url: app.serverPath + "/app/auth/getOpenId",
					data: {
						'code': WxCode
					},
					method: 'POST',
					header: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					success: (res) => {
						resolve(res);
					},
					fail: () => {
						reject('error');
					}
				})
			}
		});
	})
}

function autoLogin(openid) {
	return new Promise(function(resolve, reject) {
		uni.request({
			url: app.serverPath + "/app/auth/autoLogin",
			data: {
				'openId': openid
			},
			method: 'POST',
			header: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			success: (res) => {
				resolve(res);
			},
			fail: (data) => {
				reject('error');
			}
		});
	})
}

module.exports = {
	getBaseDatas: getBaseDatas,
	getOpenId: getOpenId,
	autoLogin: autoLogin
}
