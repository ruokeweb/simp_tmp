package com.mpri.aio.schoolmate.utils;

import java.util.Date;

import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.common.utils.StringUtils;

/**
 * 
 * <p>
 * Title: InviteCodeStrategy
 * </p>
 * <p>
 * Description: 邀请码生成策略
 * </p>
 * 
 * @author syp
 * @date 2018年9月10日
 */
public class InviteCodeUtils extends StringUtils{
	
	private static final Integer LEN = 6;
	
	private static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	public static String getShortUUID(String smId) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = smId.replace("-", "");
		for (int i = 0; i < LEN; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}
	
	
	public static String getShortNum() {
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
	
	public static void main(String[] args) {
		System.out.println(getShortUUID(IdGen.uuid()));
	}
}
