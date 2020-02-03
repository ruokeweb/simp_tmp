<script>
    //const stat = require('./utils/dt-stat-sdk-wx.min.js')
    import app from '@/common/app.js'; //公共变量
    import util from '@/common/util.js'; //公共变量
    import store from './store';
    import {
        mapState,
        mapMutations
    } from 'vuex';
    export default {
        onLaunch: function() {
            uni.request({
                url: app.serverPath + '/app/getBaseDatas', //仅为示例，并非真实接口地址。
                method: 'POST',
                success: (res) => {
                    //设置初始化数据
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        let cache = res.data.data;
                        try {
                            uni.setStorageSync(app.CACHE_NAME.DICT, cache.dict);
                            uni.setStorageSync(app.CACHE_NAME.AREABASE, cache.areaBase);
                            uni.setStorageSync(app.CACHE_NAME.DEPARTMENTBASE, cache.departmentBase);
                            uni.setStorageSync(app.CACHE_NAME.SYSINDUSTRYBASE, cache.sysIndustryBase);
                            uni.setStorageSync(app.CACHE_NAME.SETTINGSUBJECTBASE, cache.settingSubjectBase);
                            uni.setStorageSync('multipleLinkageDepartments', cache.multipleLinkageDepartments);
                            uni.setStorageSync('multipleLinkageSubject', cache.multipleLinkageSubject);
                            uni.setStorageSync('multipleLinkageIndustry', cache.multipleLinkageIndustry);
                            uni.setStorageSync('multipleLinkageAddress', cache.multipleLinkageAddress);
                        } catch (e) {
                            console.log(e);
                        }
                    } else {

                        console.log(res.data.msg);
                    }
                },
                fail() {
                    console.log("网络连接错误");
                }
            });
            //获取Openid
            uni.getProvider({
                service: 'oauth',
                success: (res) => {
                    console.log(res.provider)
                    if (~res.provider.indexOf('weixin')) {
                        uni.login({
                            provider: 'weixin',
                            success: (loginRes) => {
                                this.WxCode = loginRes.code;
                                //获取用户openid
                                uni.request({
                                    url: app.serverPath + "/app/pay/getInfo",
                                    data: {
                                        'code': this.WxCode
                                    },
                                    method: 'POST',
                                    header: {
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                    success: (res) => {
                                        if (res.data.code == app.RESPONSE_STATUS
                                            .REQUEST_SUCCESS) {
                                            uni.setStorageSync('openid', res.data
                                                .data.openId);
                                            /* stat.registerParams({
                                               openId: res.data.data.openId,
                                             })
                                             stat.init(); */
                                            //util.getStatPoint('$WXMPLaunch', 'EVENT_DESCRIBE_0');
                                        }

                                    },
                                    fail: () => {}
                                })

                            }
                        });
                    }
                }
            });

        },
        onShow: function() {
            uni.request({
                url: app.serverPath + '/app/wx/getMiniProConfig', //仅为示例，并非真实接口地址。
                method: 'POST',
                success: (res) => {
                    //设置初始化数据
                    if (res.data.code == app.RESPONSE_STATUS.REQUEST_SUCCESS) {
                        let cache = res.data.data;
                        // this.setConfigParams(cache);
                        this.$store.commit('setConfigParams', cache);
                    } else {
                        console.log(res.data.msg);
                    }
                },
                fail() {
                    console.log("网络连接错误");
                }
            });

        },
        onHide: function() {

        }

    };
</script>

<style>
    /*每个页面公共css */
    @import './common/uni.css';
    /*    @import "/static/css/main.css";
    @import "/static/css/icon.css"; */
</style>
