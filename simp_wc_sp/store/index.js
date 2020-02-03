import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        hasLogin: false,
        userInfo: {},
        token: "",
        tokenTime: "",
        configParams: {
            flag: false,
            domain: "",
            school_name: "",
            is_on_webvsb: "",
            is_on_index_mediaMes: "",
            is_don_project: "",
            is_don_team: "",
            TEMPLATE_IDS: {
                JFTZ: "", //缴费通知
                ZCSH: "", //注册审核通知
                SHJG: "", //审核结果通知
                HDKS: "" //活动开始通知
            }
        }
    },
    mutations: {
        login(state, loginInfo) {
            state.hasLogin = true;
            state.token = loginInfo.token;
            state.tekenTime = loginInfo.tokenTime;
            uni.setStorage({
                key: 'token',
                data: loginInfo.token,
            });
            uni.setStorage({
                key: 'tokenTime',
                data: loginInfo.tokenTime,
            });
        },
        logout(state) {
            state.hasLogin = false;
            uni.removeStorage({
                key: 'token'
            });
            uni.removeStorage({
                key: 'tokenTime'
            });
            uni.removeStorage({
                key: 'userInfo'
            });
        },
        setUserInfo(state, userInfo) {
            state.userInfo = userInfo;
            uni.setStorage({
                key: 'userInfo',
                data: userInfo,
            });
        },
        setConfigParams(state,params){
            // state.configParams = params;
            state.configParams.flag = true;
            state.configParams.is_on_webvsb = params.is_on_webvsb;
            state.configParams.school_name = params.school_name;
            state.configParams.domain = params.vsb_webber_domain;
            state.configParams.is_on_index_mediaMes = params.is_on_index_mediaMes;
            state.configParams.is_don_project = params.is_don_project;
            state.configParams.is_don_team = params.is_don_team;
            state.configParams.TEMPLATE_IDS.JFTZ =  params.template_jftz;
            state.configParams.TEMPLATE_IDS.ZCSH =  params.template_zcsh;
            state.configParams.TEMPLATE_IDS.SHJG =  params.template_shjg;
            state.configParams.TEMPLATE_IDS.HDKS =  params.template_hdks;
            uni.setStorageSync("configParams",params);
        }
    },
    actions: {

    }
})

export default store
