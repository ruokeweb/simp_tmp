package com.mpri.aio.schoolmate.vo;

/**
 *  经纬度判断VO
* <p>Title: LocationVo</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年10月24日
 */
public class LocationVo {
	
	private String username;

	private Double minLat;
	
	private Double minLng;
	
	private Double maxLat;
	
	private Double maxLng;
	
	private String sex;
	//附近最多人的人数
	private int limit;
	
	public Double getMinLat() {
		return minLat;
	}
	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}
	public Double getMinLng() {
		return minLng;
	}
	public void setMinLng(Double minLng) {
		this.minLng = minLng;
	}
	public Double getMaxLat() {
		return maxLat;
	}
	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}
	public Double getMaxLng() {
		return maxLng;
	}
	public void setMaxLng(Double maxLng) {
		this.maxLng = maxLng;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public LocationVo(Double minLat, Double minLng, Double maxLat, Double maxLng) {
		super();
		this.minLat = minLat;
		this.minLng = minLng;
		this.maxLat = maxLat;
		this.maxLng = maxLng;
	}

	
}
