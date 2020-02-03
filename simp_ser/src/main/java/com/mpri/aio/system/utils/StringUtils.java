package com.mpri.aio.system.utils;

public class StringUtils extends com.mpri.aio.common.utils.StringUtils{

	/**
	 * 切取字符串
	 */
	public static String cutMaxString(int max,String str) {
		
		if(str.length()>max) {
			str=str.substring(0, max-1);
		}
		return str;
	} 
	
}
