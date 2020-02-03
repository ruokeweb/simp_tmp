package com.mpri.aio.updatepassword;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpdatePassword extends ApplicationTests{

    @Autowired
	private SysUserService sysUserService;
	/* 初始没有身份证号的密码 */
	private static final String DEFAULT_PWD = "DF" + DateUtils.getYear();
	@Autowired
	private RedisCacheService redisCacheService;
	// @Value("${ps.salt}")
	private int saltTimes;
	/**
	 * 分页获取信息
	 */
	@Test
    public void testUpdatePassword() throws ParseException {
		/*SysUser sysUser = new SysUser();
		*//*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate1 = dateFormat.parse("2019-06-18");
		sysUser.setQueryEndDate(myDate1);*//*
		//sysUser.
		List<SysUser> sysUsers = sysUserService.loadAllUpdatePassword(sysUser);
		for (SysUser user:sysUsers) {
			user.setPassword(initPwd(user.getIdcard()));
			ByteSource salt = ByteSource.Util.bytes(user.getSafecode());
			// 加盐炒三次safecode=salt
			String result = new Md5Hash(user.getPassword(), salt, getSaltTimes()).toString();

			*//*SysUser sysUser1 = new SysUser();
			sysUser1.setId(user.getId());*//*
			user.setPassword(result);
			sysUserService.save(user);
		}*/

	}
	/**
	 * 初始化密码
	 *
	 * @param idCard
	 * @return
	 */
	private String initPwd(String idCard) {
		if (null != idCard && idCard.length() > 6) {
			return idCard.substring(idCard.length() - 6);
		} else {
			return GlobalStr.DEFAULT_PWD;
		}
	}
	public int getSaltTimes() {
		List<SysSetting> list = (List<SysSetting>) redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
		saltTimes = Integer.parseInt(list.get(0).getValue());
		return saltTimes;
	}
}
