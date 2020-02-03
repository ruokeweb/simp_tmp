package com.mpri.aio.back;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.act.service.ActContentService;
import com.mpri.aio.act.service.ActSelforgContentService;
import com.mpri.aio.act.service.ActSelforgService;
import com.mpri.aio.back.service.BackService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BackTest extends ApplicationTests{
	@Autowired
    private BackService backService;

	
	/**
	 * 分页获取信息
	 */
	@Test
    public void testLoadContent(){
//		backService.back();
		backService.back();

    }

}
