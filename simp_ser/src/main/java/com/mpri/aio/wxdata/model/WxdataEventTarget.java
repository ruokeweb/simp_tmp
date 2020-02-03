package com.mpri.aio.wxdata.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  自定义事件属性表
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Thu Jul 18 17:15:51 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class WxdataEventTarget extends DataEntity<WxdataEventTarget> {

	private static final long serialVersionUID = 1563441319018L;
	
	private String pointedId;
	private String eventKey;
	private String eventValue;


	 public String getPointedId() {
		 return pointedId;
	 }

	 public void setPointedId(String pointedId) {
		 this.pointedId = pointedId;
	 }

	 public String getEventKey() {
		return this.eventKey;
	}
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}	
	public String getEventValue() {
		return this.eventValue;
	}
	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}	

}
