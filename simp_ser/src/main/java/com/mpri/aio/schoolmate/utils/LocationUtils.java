package com.mpri.aio.schoolmate.utils;

import org.springframework.stereotype.Component;

import com.mpri.aio.schoolmate.vo.LocationVo;

/**
 * 经纬度(位置)Utils
* <p>Title: LocationUtils</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年10月24日
 */
@Component
public class LocationUtils {
	
	/**
	 * 计算地球上任意两点(经纬度)距离
	 *
	 * @param long1 第一点经度
	 * @param lat1  第一点纬度
	 * @param long2 第二点经度
	 * @param lat2  第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double distanceByLongNLat(double long1, double lat1, double long2, double lat2) { 
		double a, b, R;
	    R = 6378137;//地球半径
	    lat1 = lat1 * Math.PI / 180.0;
	    lat2 = lat2 * Math.PI / 180.0;
	    a = lat1 - lat2;
	    b = (long1 - long2) * Math.PI / 180.0;
	    double d;
	    double sa2, sb2;
	    sa2 = Math.sin(a / 2.0);
	    sb2 = Math.sin(b / 2.0);
	    d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
	    return d;
	}
	
	/**
	 * 根据经纬度和半径计算经纬度范围
	 *
	 * @param raidus 单位米
	 * @return minLat, minLng, maxLat, maxLng
	 */
	public static LocationVo getAround(double lat, double lon,int raidus) { 
		
		Double latitude = lat;
	    Double longitude = lon;

	    Double degree = (24901 * 1609) / 360.0;
	    double raidusMile = raidus;

	    Double dpmLat = 1 / degree;
	    Double radiusLat = dpmLat * raidusMile;
	    Double minLat = latitude - radiusLat;
	    Double maxLat = latitude + radiusLat;

	    Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
	    Double dpmLng = 1 / mpdLng;
	    Double radiusLng = dpmLng * raidusMile;
	    Double minLng = longitude - radiusLng;
	    Double maxLng = longitude + radiusLng;
	    //装入到Vo 便于查询
	    LocationVo locationVo = new LocationVo(minLat,minLng,maxLat,maxLng);
	    return locationVo;
	}
	
	public static void main(String[] args) {
		LocationVo locationVo  = getAround(34.195988,108.840164,2500);
	}
} 
