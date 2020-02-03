package com.mpri.aio.app.act;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.act.model.ActActivity;
import com.mpri.aio.act.model.ActSelforg;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.service.AppActSelfContentService;
import com.mpri.aio.app.act.service.AppActSelfService;
import com.mpri.aio.app.act.service.AppActService;
import com.mpri.aio.app.act.vo.ActDetailVo;
import com.mpri.aio.app.act.vo.ActListVo;
import com.mpri.aio.app.act.vo.ActSelfVo;
import com.mpri.aio.app.act.vo.AttendSmByAct;
import com.mpri.aio.app.don.mapper.AppDonRecordMapper;
import com.mpri.aio.app.don.service.AppDonRecordService;
import com.mpri.aio.donation.model.DonRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ActTest extends ApplicationTests{
	@Autowired
   	private AppActService actService;
	@Autowired
	private AppActSelfService appActSelfService;

    @Autowired
	private AppActSelfContentService appActSelfContentService;
    @Autowired
	private AppDonRecordService appDonRecordService;
	/**
	 * 分页获取信息
	 */
	@Test
    public void testLoadContent(){
		/*PageIo<ActListVo> actActivityPageIo = actService.ActList(0, 10, new ActActivity());
		List<ActListVo> list = actActivityPageIo.getList();
		System.out.println(list.size());*/
		/*ActActivity actActivity = new ActActivity();
		PageInfo<ActListVo> actListVoPageInfo = actService.hotActList(0, 20, actActivity);
		System.out.println(actListVoPageInfo);

		ActSelforg actSelforg = new ActSelforg();
		actSelforg.setId("455sdfsdfsxvdgdsdgd");
		actSelforg.setName("说的对");
		actSelforg.setContent("是的是的");
		actSelforg.setUserId("154sdfsdfdf155616dsf");
		appActSelfService.insert(actSelforg);*/
		/*ActSelforgContent actSelforgContent = new ActSelforgContent();
		actSelforgContent.setActSelforgId("8ef67947683d4fe0bc92d01b71893f86");
		actSelforgContent.setUserId("a54c517a13b8459fbd4cc2f8b9211e0e");
		int selfDetail = appActSelfContentService.getNumByUserId(actSelforgContent);
		System.out.println(selfDetail);*/
		/*ActListVo a = actService.getPartakeActOne(new ActActivity(), "a54c517a13b8459fbd4cc2f8b9211e0e");
		System.out.println(a);*/

	}
}
