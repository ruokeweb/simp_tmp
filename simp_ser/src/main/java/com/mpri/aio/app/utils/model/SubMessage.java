package com.mpri.aio.app.utils.model;

import java.io.Serializable;

/**
 * 订阅消息实体模板
 * @author Cary
 *
 */
public class SubMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	//接收者（用户）的 openid
	private String touser;
	//所需下发的订阅模板id
	private String template_id;
	//点击模板卡片后的跳转页面
	private String page;
	//模板内容
	private Object data;
	
	//缴费通知
	public class PayNotice{
		
		public PayNotice(String thing4_str,String amount2_str,String date1_str,String phrase3_str){
			this.thing4=new Value(thing4_str);
			this.amount2=new Value(amount2_str);
			this.date1=new Value(date1_str);
			this.phrase3=new Value(phrase3_str);
		}
		
		//缴费项目1
		private Value thing4;
		//缴费金额2
		private Value amount2;
		//缴费时间3
		private Value date1;
		//缴费状态4
		private Value phrase3;
		
		public Value getThing4() {
 			return thing4;
		}
		public void setThing4(Value thing4) {
			this.thing4 = thing4;
		}
		public Value getAmount2() {
			return amount2;
		}
		public void setAmount2(Value amount2) {
			this.amount2 = amount2;
		}
		public Value getDate1() {
			return date1;
		}
		public void setDate1(Value date1) {
			this.date1 = date1;
		}
		public Value getPhrase3() {
			return phrase3;
		}
		public void setPhrase3(Value phrase3) {
			this.phrase3 = phrase3;
		}		
	}
	
	//注册审核结果通知
	public class RegExam{
		
		public RegExam(String date2_str,String thing1_str,String thing3_str){
			this.date2=new Value(date2_str);
			this.thing1=new Value(thing1_str);
			this.thing3=new Value(thing3_str);
		}
		//时间
		private Value date2;
		//审核结果
		private Value thing1;
		//备注
		private Value thing3;
		public Value getDate2() {
			return date2;
		}
		public void setDate2(Value date2) {
			this.date2 = date2;
		}
		public Value getThing1() {
			return thing1;
		}
		public void setThing1(Value thing1) {
			this.thing1 = thing1;
		}
		public Value getThing3() {
			return thing3;
		}
		public void setThing3(Value thing3) {
			this.thing3 = thing3;
		}
		
		
	}
	
	//审核结果通知
	public class ExamResult{
		public ExamResult(String thing2_str,String phrase1_str,String thing3_str){
			this.thing2=new Value(thing2_str);
			this.phrase1=new Value(phrase1_str);
			this.thing3=new Value(thing3_str);
		}
		//活动名称
		public Value thing2;
		//审核结果
		public Value phrase1;
		//活动说明
		public Value thing3;
		public Value getThing2() {
			return thing2;
		}
		public void setThing2(Value thing2) {
			this.thing2 = thing2;
		}
		public Value getPhrase1() {
			return phrase1;
		}
		public void setPhrase1(Value phrase1) {
			this.phrase1 = phrase1;
		}
		public Value getThing3() {
			return thing3;
		}
		public void setThing3(Value thing3) {
			this.thing3 = thing3;
		}
		
	}
	
	//活动开始通知
	public class ActNotice{
		public ActNotice(String thing4_str,String name1_str,String date3_str,String thing6_str,String thing2_str){
			this.thing4=new Value(thing4_str);
			this.name1=new Value(name1_str);
			this.date3=new Value(date3_str);
			this.thing6=new Value(thing6_str);
			this.thing2=new Value(thing2_str);
		}
		//活动名称
		public Value thing4;
		//活动发起
		public Value name1;
		//开始时间
		public Value date3;
		//活动地点
		public Value thing6;
		//活动内容
		public Value thing2;
		
		public Value getThing4() {
			return thing4;
		}
		public void setThing4(Value thing4) {
			this.thing4 = thing4;
		}
		public Value getName1() {
			return name1;
		}
		public void setName1(Value name1) {
			this.name1 = name1;
		}
		public Value getDate3() {
			return date3;
		}
		public void setDate3(Value date3) {
			this.date3 = date3;
		}
		public Value getThing6() {
			return thing6;
		}
		public void setThing6(Value thing6) {
			this.thing6 = thing6;
		}
		public Value getThing2() {
			return thing2;
		}
		public void setThing2(Value thing2) {
			this.thing2 = thing2;
		}
	}

	//值对象
	public class Value{
		public String value;
		
		Value(String value){
			this.value=value;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}		
	}
	
	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
