var jweixin = require('jweixin-module');

async function configWeiXin(callback) {
    let [errConfig, resConfig] = await api.wxConfig(window.location.href);
    if (resConfig) {
        let apiList = [ // 可能需要用到的能力
            'onMenuShareAppMessage',
            'onMenuShareTimeline',
            'hideOptionMenu',
            'showOptionMenu',
            'chooseWXPay'
        ];

        let info = {
            debug: true, // 调试，发布的时候改为false
            appId: 'wx5ffa8cca1d702488',
            nonceStr: resConfig.noncestr,
            timestamp: resConfig.timestamp,
            signature: resConfig.sign,
            jsApiList: apiList
        };
        jweixin.config(info);
        jweixin.error(err => {
            console.log('config fail:', err);
        });

        jweixin.ready(res => {
            if (callback) callback(jweixin); // 配置成功
        });
    }
}
