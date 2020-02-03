package com.mpri.aio.wxdata.model;
import com.mpri.aio.base.model.DataEntity; 

 /**   
 *  
 * @Description:  小程序埋点数据表
 * @Author:       zdl
 * @project       simp   
 * @CreateDate:   Thu Jul 18 17:08:03 GMT+08:00 2019
 * @Version:      v_1.2
 *    
 */
public class WxdataPointed extends DataEntity<WxdataPointed> {

	private static final long serialVersionUID = 1563440854717L;
	
	private String networkType;
	private String os;
	private String urlPath;
	private String isFirstDay;
	private String latestScene;
	private String eventName;
	private String isDefaultTrack;
	private String brand;
	private String nn;
	private String model;
	private String gd;
	private String userId;
	private String openId;


	public String getNetworkType() {
		return this.networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}	
	public String getOs() {
		return this.os;
	}
	public void setOs(String os) {
		this.os = os;
	}	
	public String getUrlPath() {
		return this.urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}	
	public String getIsFirstDay() {
		return this.isFirstDay;
	}
	public void setIsFirstDay(String isFirstDay) {
		this.isFirstDay = isFirstDay;
	}	
	public String getLatestScene() {
		return this.latestScene;
	}
	public void setLatestScene(String latestScene) {
		this.latestScene = latestScene;
	}	
	public String getEventName() {
		return this.eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}	
	public String getBrand() {
		return this.brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}	
	public String getNn() {
		return this.nn;
	}
	public void setNn(String nn) {
		this.nn = nn;
	}	
	public String getModel() {
		return this.model;
	}
	public void setModel(String model) {
		this.model = model;
	}	
	public String getGd() {
		return this.gd;
	}
	public void setGd(String gd) {
		this.gd = gd;
	}

	 public String getIsDefaultTrack() {
		 return isDefaultTrack;
	 }

	 public void setIsDefaultTrack(String isDefaultTrack) {
		 this.isDefaultTrack = isDefaultTrack;
	 }

	 public String getUserId() {
		 return userId;
	 }

	 public void setUserId(String userId) {
		 this.userId = userId;
	 }

	 public String getOpenId() {
		 return openId;
	 }

	 public void setOpenId(String openId) {
		 this.openId = openId;
	 }
 }
