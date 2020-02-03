package com.mpri.aio.act;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSignVo;
import com.mpri.aio.act.service.ActContentService;
import com.mpri.aio.act.service.ActSelforgContentService;
import com.mpri.aio.act.service.ActSelforgService;

public class ActTest extends ApplicationTests{
	@Autowired
    private ActSelforgService actSelforgService;
	@Autowired
	private ActSelforgContentService actSelforgContentService;
	@Autowired
    private ActContentService actContentService;
	
	/**
	 * 分页获取信息
	 */
	//@Test
    public void testLoadContent(){
		/*ActContent actContent = new ActContent();
		actContent.setActId("640ced754dcd4f42904053d0b23f7c6d");
		actContentService.loadContent(1, 1, actContent);*/
		ActSelforg actSelforg = new ActSelforg();
		actSelforg.setName("1988届校友毕业30周年返校纪念大会召开");
		actSelforg.setContent("校友关系发展部还向校友们提供了校园怀旧午餐、重拍毕业照等服务。4月7日上午，100多位校友代表还参观了创新港");
		actSelforg.setUserId("95d9b6359ca4488d833f51544176e212");
		actSelforg.setConName("李四1");
		actSelforg.setConPhone("18710851364");
		actSelforg.setLimitNo(60);
		actSelforg.setReadyNo(0);
		actSelforg.setIsReception("NO");
		actSelforg.setStatus("SUCCESS");
		actSelforgService.save(actSelforg);
		/*ActSelforgContent actSelforg = new ActSelforgContent();
		actSelforg.setUserId("95d9b6359ca4488d833f51544176e212");
		actSelforg.setActSelforgId("480b65712ac44aada7d0d03b83aa1a43");

		actSelforgContentService.save(actSelforg);*/

    }
    
    @Test
    public void loadActSign() {
    	
    	List<ActSignVo> list=actContentService.loadActSign(1);
    	super.outprint("java.util.List",list);
    }
}
