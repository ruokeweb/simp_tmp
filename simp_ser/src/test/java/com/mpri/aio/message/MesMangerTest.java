package com.mpri.aio.message;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.model.MesMessage;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.message.service.MesMessageService;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.init.InitCache;

/**
 * 
* <p>Title: MesMangerTest</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年11月16日
 */
public class MesMangerTest extends ApplicationTests {

	@Autowired
	private MesGroupService mesGroupService;
	
	@Autowired
	private MesMessageService mesMessageService;
	
	@Autowired
	private SmSchoolmateService smSchoolmateService;
	
	static List<MesGroup> mesGroupsCache ;
	
	/* 星级缓存 */
	//static Map<String, List<SmStar>> starCache = InitCache.starCache;
	
	
	/**
	 * 获取测试组
	* <p>Title: getGroup</p>  
	* <p>Description: </p>
	 */
//	@Test
	public MesGroup getGroup() {
//		SmSchoolmate smSchoolmate = new SmSchoolmate();
//		smSchoolmate.setUsername("17629261881");
//		smSchoolmate = smSchoolmateService.getInfoBy(smSchoolmate);
//		MesGroup mesGroup =  mesGroupService.gerMesGroupBy(smSchoolmate,mesGroupsCache);
//		if(mesGroup != null) {
//			return mesGroup;
//		}
		return null;
	}

	/**
	 * 获取消息

	 */
//	@Test
	public void getMsgs() {
		MesGroup mesGroup = this.getGroup();
		MesMessage mesMessage = new  MesMessage();
		if(mesGroup != null) {
			mesMessage.setReceiveGroupId(mesGroup.getId());
		}
		//设置包括通知
		mesMessage.setType(GlobalStr.MES_NOTICE);
		mesMessage.setReceiveUserId("c003ff97a3ea4d3989e562a085f58d9e");
		List<MesMessage> msgs =  mesMessageService.getMesListBy(mesMessage);
		msgs.forEach((MesMessage  m) ->{
			System.err.println(m.getTitle());			
		});
	}
//	@Test	
	public void getMesCountByTest() {
		MesGroup mesGroup = this.getGroup();
		MesMessage mesMessage = new  MesMessage();
		if(mesGroup != null) {
			mesMessage.setReceiveGroupId(mesGroup.getId());
		}
		mesMessage.setReceiveUserId("a5af97a7c9fc4f78bc2435ea16c41e74");
//		List<MesMessage> msgs =  mesMessageService.getMesListBy(mesMessage);
		int a  = mesMessageService.getMesCountBy(mesMessage);
		System.err.println(a);
	}
	
	@Test	
	public void getFirstMesByTest() {
		MesGroup mesGroup = this.getGroup();
		MesMessage mesMessage = new  MesMessage();
		if(mesGroup != null) {
			mesMessage.setReceiveGroupId(mesGroup.getId());
		}
		mesMessage.setReceiveUserId("c003ff97a3ea4d3989e562a085f58d9e");
//		System.err.println(mesMessageService.getMesLisReadtBy(mesMessage).size());
		MesMessage mes = mesMessageService.getFirstMesBy(mesMessage);
		System.err.println(mes.getContent());
	}
}
