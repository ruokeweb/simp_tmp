<script>
    import Vue from 'vue';
	import app from '@/common/app.js'; 
    import init from '@/common/init.js'; 
    import util from '@/common/util.js';
	import store from '@/store/index.js';
    
	export default {
		onLaunch:  function() {
            //获取系统参数，调整头文件
            uni.getSystemInfo({
                success: function(e) {
                    // #ifndef MP
                    Vue.prototype.StatusBar = e.statusBarHeight;
                    if (e.platform == 'android') {
                        Vue.prototype.CustomBar = e.statusBarHeight + 50;
                    } else {
                        Vue.prototype.CustomBar = e.statusBarHeight + 45;
                    };
                    // #endif
                    // #ifdef MP-WEIXIN
                    Vue.prototype.StatusBar = e.statusBarHeight;
                    let custom = wx.getMenuButtonBoundingClientRect();
                    Vue.prototype.Custom = custom;
                    Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
                    // #endif       
                    // #ifdef MP-ALIPAY
                    Vue.prototype.StatusBar = e.statusBarHeight;
                    Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
                    // #endif
                    //装载缓存高度
                    uni.setStorageSync("topHeight",Vue.prototype.CustomBar);
                }
            })

    //         //判断并获取编码
    //         var dictCache=uni.getStorageSync(app.CACHE_NAME.DICT);
    //         if(dictCache==""){
    //             init.getBaseDatas().then(function (res){
				// 	if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
				// 		let cache = res.data.data;
				// 		try {
				// 			uni.setStorageSync(app.CACHE_NAME.DICT, cache.dictCache);
    //                         uni.setStorageSync(app.CACHE_NAME.DICTKV, cache.dictKVCache);
                            
				// 		} catch (e) {
				// 			console.log(e);
				// 		}
				// 	} else {
    //                     console.log(res.data.msg);
				// 	}
				// });
    //         }
            
    //         //是否登录判断
    //         var isLogin=util.isLogin();
            
    //         if(!isLogin){//未登录
    //             init.getOpenId().then(function (res){
    //                 if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
    //                     var openId = res.data.data.openId;
    //                     var sessionKey=res.data.data.sessionKey;
    //                     uni.setStorageSync(app.openId, openId);
    //                     uni.setStorageSync(app.sessionKey, sessionKey);
    //                     //使用openid登录
    //                     init.autoLogin(openId).then(function(res){
    //                         //装入token与基本用户信息
    //                         if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
    //                             uni.setStorageSync(app.storageKey.userInfo, res.data.data);
    //                             var timestamp = (new Date()).valueOf();              
    //                             store.commit('updateToken', res.data.msg);
    //                             store.commit('updateTokenTime', timestamp);
    //                             store.commit('updateIsLogin',true);
                                
    //                         }else if(res.data.code == app.RESPONSE_STATUS.NO_PERMISSION){
    //                             //未有此用户
    //                             store.commit('updateIsLogin',false);
    //                         }else{
    //                             store.commit('updateIsLogin',false);
    //                             uni.showToast({
    //                                 icon: 'none',
    //                                 title: res.data.msg
    //                             });
    //                         }
    //                     })

    //                 }else{
    //                     uni.showToast({
    //                         icon: 'none',
    //                         title: res.data.msg
    //                     });
    //                 }
				// });
    //         }
		},
		onShow: function() {
			//console.log('App Show')
		},
		onHide: function() {
			//console.log('App Hide')
		}
	}
</script>

<style>
	@import "/static/css/main.css";
	@import "/static/css/icon.css";
    @import "/static/css/iconfont.css";
</style>

<style>

</style>
