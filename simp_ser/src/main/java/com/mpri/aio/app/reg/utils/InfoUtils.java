package com.mpri.aio.app.reg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.mpri.aio.common.utils.DateUtils;

/**
 * 信息处理工具类
 * @author Administrator
 *
 */
public class InfoUtils{

	/**
	 * 通过身份证号获取生日
	 */
	public static Date getBirthByIdCard(String certificateNo) {
        String birthday = "";
        Date birthdate = null;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return null;
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return null;
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
        }
        birthdate = DateUtils.parseDate(birthday);
        return birthdate;
	}
	
	public static void main(String[] args) { 
		Date date = getBirthByIdCard("610525199202203412");
		
		System.out.println(DateUtils.formatDateTime(date));
	}
}
