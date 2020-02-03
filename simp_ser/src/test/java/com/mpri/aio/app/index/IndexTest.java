package com.mpri.aio.app.index;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.app.index.mapper.IndexMapper;
import com.mpri.aio.app.index.vo.IndexInfoVo;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class IndexTest extends ApplicationTests{
	@Autowired
	private IndexMapper indexMapper;
	/**
	 * 分页获取信息
	 */
	@Test
    public void testLoadContent(){

		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setCardStatus("NORMAL");
		List<IndexInfoVo> collarCardList = indexMapper.getCollarCardList(smSchoolmate, 100);
		System.out.println(collarCardList);
	}
}
