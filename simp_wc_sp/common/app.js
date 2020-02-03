/* 公共变量 */
/* 校友卡管理端 服务 （目前为模拟数据）*/
// const serverPath ="https://xyk.vsbclub.com"
// const imagePath ="https://xyk.vsbclub.com/file/view/";

const serverPath = "http://127.0.0.1:8081"
const imagePath = "http://127.0.0.1:8081/file/view/";
//==========================修改为从后台配置获取=====================================
var domain;
/*学校名称*/
var SCHOOL_NAME;
/*是否开启网站群新闻*/
var is_on_webvsb ;
/*是否开启首页通知*/
var is_on_index_mediaMes;
/*是否展示捐献相关的信息*/
/* const is_on_donation= true;
 */
/* 是否能够捐赠 */
var is_don_project;

/* 是否能够共献爱心 */
var is_don_team;

//消息订阅模板
var TEMPLATE_IDS = {
    // JFTZ: "AnGEBDpTlbeYwxib7vn_WG_YKxYKpPHknJzrmEQ3mv8", //缴费通知
    // ZCSH: "DBvj0icOdt9obMpYMtTQoMPLXM3KI-BUMByeS8vAzsM", //注册审核通知
    // SHJG: "AIAQH66PLGBbA5RV3FtPDHvvODx6QqDxF0K9vDlkfwM", //审核结果通知
    // HDKS: "XzUbBjFMeMtQ-l9t-Xm-iY36uDOzj0HBK4y99p1JuYM" ,//活动开始通知
    JFTZ: "", //缴费通知
    ZCSH: "", //注册审核通知
    SHJG: "", //审核结果通知
    HDKS: "" //活动开始通知
}
//=================================================================

//分享类型
const share_code = {
    BACKSCHOOLRESULT_SHARE:"BACKSCHOOLRESULT_SHARE",
    ACTRESULT_SHARE: "ACTRESULT_SHARE",
    BACKSCHOOLDETAIL_SHARE: "BACKSCHOOLDETAIL_SHARE",
    ACTDETAIL_SHARE: "ACTDETAIL_SHARE",
    INVITE_SHARE: "INVITE_SHARE",
    MYCARD_SHARE: "MYCARD_SHARE",
    MYCERTIFICATE_SHARE: "MYCERTIFICATE_SHARE",
    IMMEDIATEDON_SHARE:"IMMEDIATEDON_SHARE",
    PAYRESULT_SHARE: "PAYRESULT_SHARE",
    DONDETAIL_SHARE: "DONDETAIL_SHARE",
    CARDRANK_SHARE:  "DONRANK_SHARE",
    DONRANK_SHARE: "DONRANK_SHARE",
    PROVERANK_SHARE: "PROVERANK_SHARE",
    SHARERANK_SHARE: "SHARERANK_SHARE"
}

//校友类型
const alumni_type = {
    teacher: 'TEACHER',
    expert: 'EXPERT',
    gradate: 'GRADUATE',
    officer: 'OFFICER',
    student: 'STUDENT'
};

const boolean_str = {
    boolean_true: 'true',
    boolean_false: 'false'
}
const is_str = {
    is_yes: 'YES',
    is_no: 'NO'
}

/*系统返回状态码*/
const RESPONSE_STATUS = {
    REQUEST_SUCCESS: 200,
    NO_PERMISSION: 401,
    NOT_FOUND: 404,
    SYS_ERROR: 500,
    DATA_USED: 201
}
/* 卡状态 */
const CARD_STATUS = {
    NORMAL_CARD_STATUS: 'NORMAL', //正常
    DEFAULT_CARD_STATUS: 'NO_AUDIT', // 未审核
    NO_APPLY_CARD_STATUS: 'NO_APPLY', // 未申领
    CARD_STATUS_DISABLE: 'DISABLE', //禁用
    CARD_STATUS_HOLD: 'HOLD' //停用
}

/* storage cacheName */
const CACHE_NAME = {
    DICT: 'dict', //字典 key : list<dict>
    AREABASE: 'areaBase', //区域 key :sysArea
    DEPARTMENTBASE: 'departmentBase', // 院系专业 key : department
    SYSINDUSTRYBASE: 'sysIndustryBase', //行业 key : industry
    SETTINGSUBJECTBASE: 'settingSubjectBase', //学科 key : subject
    MULTIPLELINKAGEDEPARTMENTS: 'multipleLinkageDepartments',
    MULTIPLELINKAGESUBJECT: 'multipleLinkageSubject'
}

/* 字典编码类型 */
const DICT_TYPECODE = {
    AREA_TYPE: 'AREA_TYPE',
    ASSOCIATION_TYPE: 'ASSOCIATION_TYPE',
    BOOLEAN_TYPE: 'BOOLEAN_TYPE',
    CARD_STATUS: 'CARD_STATUS',
    CARD_TYPE: 'CARD_TYPE',
    CONTACT_TYPE: 'CONTACT_TYPE',
    COUNTRY: 'COUNTRY',
    DONATION_TYPE: 'DONATION_TYPE',
    EDU_DEGREE: 'EDU_DEGREE',
    EDU_DEGREETYPE: 'EDU_DEGREETYPE',
    EDU_SCHOOLEN: 'EDU_SCHOOLEN',
    EDU_RECORD: 'EDU_RECORD',
    EDU_MODEL: 'EDU_MODEL',
    EDU_TYPE: 'EDU_TYPE',
    FAMLIY_RELATION: 'FAMLIY_RELATION',
    GRADE: 'GRADE',
    HONOR_TYPE: 'HONOR_TYPE',
    IDCARD_TYPE: 'IDCARD_TYPE',
    IS_ADDRESS_POST: 'IS_ADDRESS_POST',
    LOGS_TYPE: 'LOGS_TYPE',
    MENUTYPE: 'MENUTYPE',
    MENU_SHOW: 'MENU_SHOW',
    MENU_TYPE: 'MENU_TYPE',
    NATION: 'NATION',
    ORG_TYPE: 'ORG_TYPE',
    ORG_USEABLE: 'ORG_USEABLE',
    PERMISSION: 'PERMISSION',
    PROJECT_TYPE: 'PROJECT_TYPE',
    POLITICS_NAME: 'POLITICS_NAME',
    POLITICS_TYPE: 'POLITICS_TYPE',
    ROLETYPE: 'ROLETYPE',
    ROLE_TYPE: 'ROLE_TYPE',
    SEX: 'SEX',
    USERTYPE: 'USERTYPE',
    USER_TYPE: 'USER_TYPE',
    SEX_MALE: 'MALE',
    SEX_FEMALE: 'FEMALE',
    SCHOOLEMATE_TYPE: 'SCHOOLEMATE_TYPE',
    COMPANY_TPYE: 'COMPANY_TPYE',
    SELFORG_RECEPTION_SERVICE: 'SELFORG_RECEPTION_SERVICE',
    IS_NATION: 'IS_NATION',
    PROFESSION_STATUS: 'PROFESSION_STATUS'
}
const AES_KEY = 'tXbjTgdcQ32mmr6g';
const COME_FROM = "APP";
/*登录来源*/
const login_source = "wxminiprogram";


function getConfig(){
//     uni.request({
//         url: this.serverPath + '/app/wx/getMiniProConfig', //仅为示例，并非真实接口地址。
//         method: 'POST',
//         success: (res) => {
//             //设置初始化数据
//             if (res.data.code == this.RESPONSE_STATUS.REQUEST_SUCCESS) {
//                 let cache = res.data.data;
//                 this.is_on_webvsb = cache.is_on_webvsb;
//                 console.log(this.is_on_webvsb);
//                 this.SCHOOL_NAME = cache.school_name;
//                 this.domain = cache.vsb_webber_domain;
//                 this.is_on_index_mediaMes = cache.is_on_index_mediaMes;
//                 this.is_don_project = cache.is_don_project;
//                 this.is_don_team = cache.is_don_team;
// 
//                 this.TEMPLATE_IDS.JFTZ =  cache.template_jftz;
//                 this.TEMPLATE_IDS.ZCSH =  cache.template_zcsh;
//                 this.TEMPLATE_IDS.SHJG =  cache.template_shjg;
//                 this.TEMPLATE_IDS.HDKS =  cache.template_hdks;
//             } else {
//                 console.log(res.data.msg);
//             }
//         },
//         fail() {
//             console.log("网络连接错误");
//         }
//     });
}

export default {
    CARD_STATUS,
    RESPONSE_STATUS,
    DICT_TYPECODE,
    AES_KEY,
    imagePath,
    serverPath,
    COME_FROM,
    CACHE_NAME,
    SCHOOL_NAME,
    boolean_str,
    login_source,
    alumni_type,
    is_on_webvsb,
    is_str,
    is_don_project,
    is_don_team,
    is_on_index_mediaMes,
    share_code,
    domain,
    TEMPLATE_IDS,
    getConfig
}
