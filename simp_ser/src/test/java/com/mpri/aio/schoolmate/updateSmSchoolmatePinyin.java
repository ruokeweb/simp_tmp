package com.mpri.aio.schoolmate;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.utils.PinyinUtil;
import com.mpri.aio.common.utils.PinyinUtil.Type;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 修改交大校友拼音
 * @author syp
 *
 */
public class updateSmSchoolmatePinyin extends ApplicationTests{

	
	@Autowired
	private SmSchoolmateService schoolmateService;
	
	
	/**
	 * 修改校友拼音
	 */
	@Test
	public void updateSmPinyin() {
		SmSchoolmate schoolmate = new SmSchoolmate();
		List<SmSchoolmate> list = schoolmateService.loadAllListBy(schoolmate);
		for (SmSchoolmate smSchoolmate : list) {
			PinyinUtil pu = new PinyinUtil();
			try {
				if(StringUtil.isEmpty(smSchoolmate.getPinyin())) {
					smSchoolmate.setPinyin(pu.toPinYin(smSchoolmate.getName(), "",Type.LOWERCASE));
					schoolmateService.updatePinyin(smSchoolmate);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
