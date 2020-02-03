package com.mpri.aio.system.common;

import com.mpri.aio.common.utils.DateUtils;

/**
 * 全局常量类
* <p>Title: GlobalStr</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年9月28日
 */
public class GlobalStr {
	
	/*百度地图省*/
	private final static String[] PROVINCES = {
		"北京","天津","上海","重庆","河北","河南","云南","辽宁","黑龙江","湖南","安徽","山东","新疆","江苏","浙江","江西","湖北","广西","甘肃","山西","内蒙古","陕西","吉林","福建","贵州","广东","青海","西藏","四川","宁夏","海南","台湾","香港","澳门"			
	};
	/*审核认证的工作流定义ID*/
	public static final String PROCESSDEFINITIONKEY_AUTHENTICATE ="authenticateFlow";
	/*工作流中的参数userId*/
	public static final String USER_ID = "userId";
	/*工作流中的参数userName*/
	public static final String USER_NAME ="userName";
	/*审核注册的工作流名称*/
	public static final String REG_WORKFLOW_NAME = "authenticateFlow";
    /*chaojiguanliyuan——code*/
    public final static String SUPER_ADMIN = "admin";
    /*短信发送成功*/
    public final static String SUCCESS = "success";
    /*短信发送失败*/
    public final static String ERRO = "erro";
	
    /*工作流审核通过*/
    public static final String ACTIVITI_PASS = "1";
    /*工作流审核未通过*/
    public static final String ACTIVITI_HOLD = "0";
    /*智能审核Assignee*/
    public static final String ACTIVITI_SYS_ASSIGNEE = "system";
    
	/*定时任务正常*/
	public static final String JOB_NORMAL = "0";
	/*定时任务停止*/
	public static final String JOB_STOP = "1";
	
	/*默认机构*/
	public final static  String DEFAULT_ORG ="0";
	
	/*默认的校友会*/
	public final static String DEFAULT_AS ="root";
	
	/*默认用户类型*/
	public final static  String DEFAULT_USER_TYPE ="NORMAL";
	
    /*校友卡类型码表typeCode*/
    public final static String SCHOOLE_CARD_TYPE = "SCHOOLE_CARD_TYPE";
	
	/*卡片正常*/
	public final static  String NORMAL_CARD_STATUS ="NORMAL";
	/*卡片未申领*/
    public final static  String DEFAULT_CARD_STATUS ="NO_AUDIT";
	/*卡片未审核*/
    public final static  String NO_APPLY_CARD_STATUS ="NO_APPLY";
	/*卡片禁用*/
    public final static  String CARD_STATUS_DISABLE ="DISABLE";
	/*卡片停用*/
    public final static  String CARD_STATUS_HOLD ="HOLD";
	
	/*初始没有身份证号的密码默认密码*/
	public final static  String DEFAULT_PWD = "DF"+DateUtils.getYear()+"xy*";
	
	/*默认证件类型*/
	public final static  String DEFAULT_CARDTYPE = "IDCARD";
	
	/*默认国家*/
	public final static  String DEFAULT_COUNTRY = "000000";
	
	/*系统地址root*/
	public final static  String ROOT_AREA = "0";
	
	//菜单根节点的父值定义为root
	public final static String MENU_ROOT_ID="root";
	
    /* 默认校友类型 */
    public final static String DEFAULT_SCHOOLMATE_TYPE =  "GRADUATE";
    
    public final static String STUDENT_SCHOOLMATE_TYPE =  "STUDENT";
    
    
    /*性别码表typeCode*/
    public final static String SEX_TYPECODE = "SEX";
	/*性别男女code*/
    public final static String SEX_CODE_MALE="MALE";
    public final static String SEX_CODE_FEMALE="FEMALE";
    /*性别男女name*/
	public final static String SEX_NAME_MALE="男";
	public final static String SEX_NAME_FEMALE="女";

    /*证件码表typeCode*/
    public final static String IDCARDTYPE_TYPECODE = "IDCARD_TYPE";
    
    /*身份证码表typeCode*/
    public final static String ID_CARD = "IDCARD";
    
    /*民族码表typeCode*/
    public final static String NATION = "NATION";
    
    /*校友类型码表typeCode*/
    public final static String SCHOOLEMATE_TYPE = "SCHOOLEMATE_TYPE";
    
    
    /*联系方式码表typeCode*/
    public final static String PHONE = "PHONE";
    
    /*联系方式码表typeCode*/
    public final static String EMAIL = "EMAIL";
    
    /*数据库状态*/
    public final static String HOLD = "HOLD";
    public final static String NORMAL ="NORMAL";
    
    
    /*捐赠状态*/
    public final static String WAITING_DON = "WAITING";
    public final static String NORMAL_DON ="NORMAL";
    public final static String FAIL_DON = "HOLD";
    public final static String REFUND_DON = "REFUND";
    public final static String PAY_STYLE_WEICHAT = "WEICHAT";
    public final static String PAY_STYLE_ALIPAY = "ALIPAY";
    public final static String PAY_STYLE_BANK = "BANK";
    public final static String PAY_STYLE_OTHER = "OTHER";
    public final static String PAY_STYLE_OFFLINE = "OFFLINE";
    
    
	/*捐赠支付方式*/
	public final static String PAY_STYLE="PAY_STYLE";
	/*捐赠币种*/
	public final static String  MONEY_TYPE ="MONEY_TYPE";
	/*捐赠项目类型*/
	public final static String  PROJECT_TYPE="PROJECT_TYPE";
	/*捐赠项目状态*/
	public final static String  PROJECT_STATUS="PROJECT_STATUS";

	/*捐赠类型*/
	public final static String  DONATION_TYPE="DONATION_TYPE";
	/*是否是籍贯*/
	public final static String IS_NATION_PLACE ="IS_NATION_PLACE";
	public final static String NO_NATION_PLACE ="NO_NATION_PLACE";
	public final static String IS_NATIVEADDRESS ="IS_NATIVEADDRESS"; //生源地址
	public final static String IS_FIRSTADDRESS = "IS_FIRSTADDRESS"; //首选地址
	
	/*学历*/
	public final static String EDU_RECORD = "EDU_RECORD";
	public final static String EDU_RECORD_EDP = "EDU_RECORD_7";
	
	/*学位类型*/
	public final static String EDU_DEGREE = "EDU_DEGREE";
	/*学制*/
	public final static String EDU_SCHOOLEN = "EDU_SCHOOLEN";
	/*教育模式*/
	public final static String EDU_MODEL = "EDU_MODEL";
	/*培养方式*/
	public final static String EDU_TYPE = "EDU_TYPE";
    
	/*区域级别*/
	public final static String COUNTRY = 	"COUNTRY";
	public final static String PROVINCE = "PROVINCE";
	public final static String CITY = "CITY";
	public final static String TOWN = "TOWN";
	public final static String SMALL_COUNTRY = "SMALL_COUNTRY";
	public final static String VILLAGE = "VILLAGE";

	/*捐赠项目状态*/
	public final static String DON_WILL = "WILL";
	public final static String DON_BEDOING = "BEDOING";
	public final static String DON_HASDONE = "HASDONE";
	
	/*签到状态*/
	public final static String NO_SIGN = "NO_SIGN";
	public final static String IS_SIGN = "IS_SIGN";
	
	/*邮件安全验证类型*/
	public final static String Mail_TLS = "TLS";
	public final static String Mail_SSL = "SSL";
	
	public final static String STATUS_PASS="PASS"; //认证通过
	public final static String STATUS_HOLD="HOLD"; //
	public final static String STATUS_REFUSE="REFUSE";  // 
	public final static String STATUS_FRIENDS="FRIENDS"; //好友
	
	/*通用boolean 值*/
	public final static String BOOLEAN_YES = "YES";
	public final static String BOOLEAN_NO = "NO";
	
	//通知消息是否过期
	public final static String IS_OVERDUE = "IS_OVERDUE"; //已过期
	public final static String NOT_OVERDUE = "NOT_OVERDUE"; //未过期
	
	//消息发送渠道
	public final static String MES_SENDTYPE_SMS = "MES_SENDTYPE_SMS"; //短信
	public final static String MES_SENDTYPE_EMAIL = "MES_SENDTYPE_EMAIL"; //EMAIL
	public final static String MES_SENDTYPE_CARD = "MES_SENDTYPE_CARD"; //校友卡
	
	//标签是否禁用
	public final static String ABLE = "ABLE";
	public final static String UNABLE = "UNABLE";
	
	//消息已读和未读
	public final static String MES_READ = "READ";
	public final static String MES_UNREAD = "UNREAD";
	
	//通知 消息
	public final static String MES_NEWS = "NEWS";
	public final static String MES_NOTICE = "NOTICE";
	
	//模板类型
	public final static String MES_TEMPLATE_BIRTH = "MES_TEMPLATE_BIRTH"; //生日
	public final static String MES_TEMPLATE_FESTIVAL = "MES_TEMPLATE_FESTIVAL"; //节日
	
	
	//信息发布状态
	public final static String INFO_PUB = "INFO_PUB"; //发布
	public final static String INFO_NOPUB = "INFO_NOPUB"; //未发布
	
	
	//是否是专题
	public final static String INFO_TOPIC = "TOPIC";
	public final static String INFO_ORDINARY = "ORDINARY";
	
	//是否默认
	public final static String IS_DEFAULT = "IS_DEFAULT";
	public final static String NOT_DEFAULT = "NOT_DEFAULT";
	
	//院系专业等级
	public final static String SCHOOL = "SCHOOL"; //校级
	public final static String SCHOOL_AREA = "SCHOOL_AREA"; //校区
	public final static String BIG_COLLEGE = "BIG_COLLEGE"; //书院级
	public final static String COLLEGE = "COLLEGE"; // 学院级
	public final static String MAJOR = "MAJOR"; //专业级
	public final static String DEPARTMENT = "DEPARTMENT"; //系级
	public final static String CLASS = "CLASS"; //班级

	//联系方式TypeCode
	public final static String CONTACT_TYPE = "CONTACT_TYPE";

	public final static String CONTACT_TYPE_WECHAT = "WECHAT";
	public final static String CONTACT_TYPE_QQ = "QQ";
	public final static String CONTACT_TYPE_FAMILY_PHONE = "FAMILY_PHONE";
	public final static String CONTACT_TYPE_PHONE = "PHONE";
	public final static String CONTACT_TYPE_EMAIL = "EMAIL";
	
	//政治面貌TypeCode
	public final static String POLITICS_NAME= "POLITICS_NAME";
	
	//政治面貌状态 （校园经历、职业经历）
	public final static String POLITICS_TYPE= "POLITICS_TYPE";

	
	//家庭成员关系TypeCode
	public final static String FAMLIY_RELATION= "FAMLIY_RELATION";
	
	//荣誉类型
	public final static String HONOR_TYPE = "HONOR_TYPE";
	
	//企业性质
	public final static String COMPANY_TPYE = "COMPANY_TPYE";
   //在职状态
	public final static String PROFESSION_STATUS = "PROFESSION_STATUS";
	public final static String PROFESSION_STATUS_JOIN_IN = "JOIN_IN";
	public final static String PROFESSION_STATUS_JOIN_OUT = "JOIN_OUT";
	
	//通用Boolean
	public final static String BOOLEAN_TYPE = "BOOLEAN_TYPE";
	//活动状态
	public final static String ACT_STATUS = "ACT_STATUS";
	//用户来源
	public final static String USER_SOURCE_HISTORY_DATA = "HISTORY_DATA"; //历史数据
	public final static String USER_SOURCE_NEW_CARD = "NEW_CARD"; //新领卡	
	public final static String USER_SOURCE_HAD_CARD = "HAD_CARD"; //USER_SOURCE
	
	
	//值年返校状态、一起捐
	public final static String SELFORG_STATUS_SUCCESS = "SUCCESS"; //审核成功
	public final static String SELFORG_STATUS_AUDITING = "AUDITING"; //审核中
	public final static String SELFORG_STATUS_LOSE = "LOSE"; //审核失败
	
	//小程序校友卡成功的页面
	public final static String CARD_STATUS_SUCCES_PAGE = "/pages/login/cardstatusSuccess";
	
	//菜单是否展示
	public final static String MENU_SHOW = "SHOW";
	public final static String MENU_HIDDEN = "HIDDEN";
	
	//登录来源
	public final static String LOGIN_SOURCE = "wxminiprogram"; //小程序


	//校友信息导出方式
	public final static String TELEPHONEINFOMATION = "telephoneinfomation";
	public final static String EMAILINFORMATION = "emailinformation";
	
	//书院码表typeCode
	public static final String ACADEMY_NAME = "ACADEMY_NAME";
	
	//导入
	public static final String IMPORT_DEF_COUNTRY = "中华人民共和国";
	public static final String IMPORT_DEF_SCHOOL = "博达软件";

}
