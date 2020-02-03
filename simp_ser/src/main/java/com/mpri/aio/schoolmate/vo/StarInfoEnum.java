package com.mpri.aio.schoolmate.vo;
/**
 * 
* <p>Title: StarInfoEnum</p>  
* <p>Description: 星级配置信息</p>  
* @author syp  
* @date 2018年9月12日
 */
public enum StarInfoEnum {
	
	ADMININFO("ADMININFO","管理员审核"),
	BASEINFO("BASEINFO","基本信息"),
	CONTACTINFO("CONTACTINFO","联系方式信息"),
	ADDRESSINFO("ADDRESSINFO","通讯地址信息"),
	EDUCATIONINFO("EDUCATIONINFO","教育经历信息"),
	POLITICSINFO("POLITICSINFO","政治面貌信息"),
	FAMILYINFO("FAMILYINFO","家庭成员信息"),
	SOCIALINFO("SOCIALINFO","社会兼职信息"),
	EXPERIENCEINFO("EXPERIENCEINFO","校园经历信息"),
	PROFESSIONINFO("PROFESSIONINFO","职业经历信息"),
	HONORINFO("HONORINFO","荣誉成果信息"),
	HISTORYDATAINFO("HISTORYDATAINFO","历史数据信息");

	
	private String code;
	
	private String desc;

	private StarInfoEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
    /**
     * 根据状态值获取枚举值
     * 
     * @param status
     * @return
     */
    public static StarInfoEnum getValue(String code) {
        for (StarInfoEnum s : StarInfoEnum.values()) {
            if (s.getCode().equalsIgnoreCase(code)) {
                return s;
            }
        }
        return null;
    }

	
	
}
