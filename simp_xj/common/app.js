/* 公共变量 */
/* 校友卡管理端 服务 （目前为模拟数据）*/
const serverPath ="https://ser.musichord.online";
//const serverPath ="http://127.0.0.1:8080";

const appId = "wx4489f983664ed068";
const openId = "openId";
const sessionKey = "sessionKey";

//首页通知部分
const noticeTime="noticeTime";
const noticeStep=1000*60*60*24*1;//步长一天

const storageKey ={
	userInfo: 'userInfo',
    accountInfo:'accountInfo',
	token: 'token',
    tokenTime:'tokenTime',
	tokenDate: 7,
    tokenCycle:3,
    isLogin:'isLogin'
}

const boolean_str = {
    boolean_true: 'true',
    boolean_false: 'false'
}
/*系统返回状态码*/
const RESPONSE_STATUS = {
    REQUEST_SUCCESS: 200,
    NO_PERMISSION: 401,
    NOT_FOUND: 404,
    SYS_ERROR: 500,
    DATA_USED: 201
}

/* storage cacheName */
const CACHE_NAME = {
    DICT: 'dict', //字典 key : list<dict>
    DICTKV: 'dictkv'//字典 key : dict
}

const AES_KEY = 'tXbjTgdcQ32mmr6g';
const COME_FROM = "APP";

//token的时间
var TOKEN_TIME=0;

//主菜单页面
const MENU_PAGE = {
    INDEX:'/pages/index/index',
    COURSE:'/pages/course/course',
    MALL:'/pages/mall/mall',
    MINE:'/pages/mine/mine'
}

//消息订阅模板
const TEMPLATE_IDS={
    JFTZ:'r2XPiYtRRUCScsHbBc3Lj0nlV0KkkYCy1ooOHmtV3X8',//缴费通知
    SKTX:'qYtFMBG_FDeg3xGAHoyF-usGfmuY0twvXQ_xaEhv2g8'//上课提醒 
}

//分享
const SHARE ={
	COURSE_SHARE: 100,
    MALL_SHARE:50,
	BUY: 0,
    SIGN_SHARE:100,
	SIGN: 50,
    NOTICE: 100
}

export default {
    RESPONSE_STATUS,
    AES_KEY,
    serverPath,
    COME_FROM,
    CACHE_NAME,
    TOKEN_TIME,
    boolean_str,
	appId,
	storageKey,
    openId,
    sessionKey,
    MENU_PAGE,
    TEMPLATE_IDS,
	noticeTime,
	noticeStep,
    SHARE
}
