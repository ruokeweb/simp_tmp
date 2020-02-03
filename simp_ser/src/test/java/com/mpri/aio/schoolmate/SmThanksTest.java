package com.mpri.aio.schoolmate;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.schoolmate.model.SmFamous;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.schoolmate.service.SmFamousService;
import com.mpri.aio.schoolmate.service.SmWishService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SmThanksTest extends ApplicationTests {
	
	@Autowired
	private SmWishService smWishService;
	

	@Test
	public void test() {
//		SmSchoolmate sm = new SmSchoolmate();
//		List<SmSchoolmate> sms = schoolmateService.ExportList(sm);
//		System.out.println(sms.size());
		SmWish smWish = new SmWish();
		smWish.setName("李四1");
		smWish.setUserId("95d9b6359ca4488d833f51544176e212");
		smWish.setIsshow("YES");
		smWish.setInformation("母校你好！");
		smWishService.save(smWish);
	}
}
