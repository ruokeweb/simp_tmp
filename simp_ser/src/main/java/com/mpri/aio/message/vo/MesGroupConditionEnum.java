package com.mpri.aio.message.vo;

/**
 * 消息群组条件 枚举
* <p>Title: MesGroupConditionEnum</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年11月14日
 */
public enum MesGroupConditionEnum {

	DEPARTMENT("department","院系专业"),
	SEX("sex","性别"),
	PROVINCE("province","省份"),
	//TYPE("type","校友类型"),
	COUNTRY("country","国家"),
	CITY("city","城市"),
	SCHOOLMATEMARK("schoolmatemark","校友标签名称"),
	STARTDATE("startdate","入学年份"),
	ENDDATE("enddate","毕业年份");

	private String code;
	
	private String desc;

	private MesGroupConditionEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
