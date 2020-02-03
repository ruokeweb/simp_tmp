package com.mpri.aio.xajtInit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.utils.DateUtils;
import com.mpri.aio.system.model.SysOrg;
import com.mpri.aio.system.service.SysOrgService;

/**
 * 交大机构初始化
* <p>Title: InitOrg</p>  
* <p>Description: </p>  
* @author syp  
* @date 2018年12月1日
 */
public class InitOrg extends ApplicationTests{
	
	@Autowired
	private SysOrgService orgService;
	
	String [] academys = {"旧院系设置","机械工程学院","电子与信息工程学院","理学院","材料科学与工程学院","能源与动力工程学院",
			"电气工程学院","软件学院","人居环境与建筑工程学院","生命科学与技术学院","航天航空学院","管理学院","公共政策与管理学院",
			"国际教育学院","继续教育学院","外国语学院","人文社会科学学院","财经学院","环境与化学工程学院","金禾经济研究中心",
			"经济与金融学院","体育部","医学院","建筑与工程力学学院","职业教育学院","网络教育学院","法学院","教务处","函授部","夜大学",
			"化学工程学院","化学工程与技术学院","数学与统计学院","研究生院","会计学院"};
	String [] academysRemarks= {"旧院系设置","机械学院","电信学院","理学院","材料学院","能动学院","电气学院","软件学院","人居学院",
			"生命学院","航天学院","管理学院","公管学院","国际学院","继教学院","外语学院","人文学院","财经学院","环化学院","金禾中心","经金学院",
			"体育部","医学院","建力学院","职教学院","网络学院","法学院","教务处","函授部","夜大","化工学院","化工学院","数统学院","研究生院","会计学院"};
	
	String [] academy_id1 = {"材料学系","电机工程系","电气工程系","电子工程系","动力系","动力一系","动力二系","动力机械及工程系","纺织系",
			"工程物理系","工企系","化学与化学工程系","机械工程系","机械系","机械制造及热加工","机械制造系","基础部","计算机科学与工程系",
			"建筑工程与力学系","经济系","力学系 ","社会科学系","实验技术管理系","数理力学系","数理系","水利系","外语系","无线电工程一系",
			"无线电工程二系","无线电工程系","物理系","信息与控制工程系","药学系","电工器材制造系","电力工程系","航空工程学系","管理系","函授部",
			"材料科学与工程系","机械学系","外语部","电气绝缘与高电压工程系","电力工程系","公共行政管理","国际经济","经济法","产业与贸易经济系",
			"环境科学","环境工程","计算机科学与技术系","电子科学与技术系","自动化科学与技术系","材料学系","财政金融系","财政系","财政信贷系",
			"党史系","儿科系","法医学系","放射医学","妇幼卫生系","工程力学系","工商管理系","工业财务会计系","工业管理系","工业会计系","工业经济系",
			"管理工程系","护理系","会计系","建筑学系","建筑与结构工程系","金融系","经济管理系","科技英语系","口腔系","口腔医学系","临床医学系",
			"贸易经济系","能源与动力工程系","人口系统工程","商业经济系","生物医学工程系","市场营销","数学系","统计系","卫生管理系","卫生医学系",
			"物资财会","物资经济","物资系","系统工程","新闻系","信息管理系","医疗系","医学系","银行管理工程系","预防医学系","哲学系","政法系",
			"政经系","政治系","信息与通信工程系","控制科学与工程系","生物医学工程系","生物医学工程系","材料加工工程系","材料加工工程系",
			"材料物理与化学系","电机电器及其控制系","高电压与绝缘技术系","工业自动化系","土木工程系","建筑环境与设备工程系","生物科学与工程系",
			"航天航空系","自动控制工程系","生物医学工程及仪器系","旅游管理系","电子商务系","社会保障系","行政管理系","日语系","英语系","俄语系",
			"俄德法系","法语系","哲学系","社会学系","政治学系","法学系","中文系","艺术系","微电子学系","管理科学系","工业工程系","信息管理与电子商务系",
			"技术经济及管理系","组织管理系","市场营销系","会计与财务系","机械工程及自动化系","仪器科学与技术系","工业设计系","金融系","统计系",
			"国际贸易系","银行信息管理系","电子商务系","经济学系","财政系","热能工程系","制冷及低温工程系","流体机械及工程系","动力机械及工程系",
			"化工过程机械系","核科学与技术系","化学工程系","环境工程系","环境与科学技术系","德语系","生理学与病理生理学系","遗传学与分子生物学系",
			"免疫学与病原生物学系","人体解剖学与组织胚胎学系","公共卫生学系","药理学系","病理学系","药学系","科学计算与应用软件系","应用数学系",
			"统计金融系","基础数学系","信息科学系","应用物理系","光信息科学与技术系","材料物理系","应用化学系","医药化学系","软件工程"};
	String [] academy_id2= {"材料加工工程系","机械工程及自动化系","仪器科学与技术系","工业设计系"};
	String [] academy_id3= {"计算机科学与技术系","电子科学与技术系","自动化科学与技术系","系统工程","信息与通信工程系","控制科学与工程系",
			"生物医学工程系","自动控制工程系","生物医学工程及仪器系","微电子学系"};
	String [] academy_id4= {"数学系","科学计算与应用软件系","应用数学系","统计金融系","基础数学系",
			"信息科学系","应用物理系","光信息科学与技术系","材料物理系","应用化学系","医药化学系"};
	String [] academy_id5= {"材料学系","材料加工工程系","材料物理与化学系"};
	String [] academy_id6= {"能源与动力工程系","热能工程系","制冷及低温工程系","流体机械及工程系","动力机械及工程系",
			"化工过程机械系","核科学与技术系","化学工程系","环境工程系"};
	String [] academy_id7= {"电机电器及其控制系","高电压与绝缘技术系","工业自动化系","电气绝缘与高电压工程系","电力工程系"};
	String [] academy_id8= {"软件工程"};
	String [] academy_id9= {"建筑学系","土木工程系","建筑环境与设备工程系","环境与科学技术系"};
	String [] academy_id10= {"生物医学工程系","生物科学与工程系"};
	String [] academy_id11= {"工程力学系","航天航空系"};
	String [] academy_id12= {"管理工程系","经济管理系","旅游管理系","电子商务系","管理科学系","工业工程系","信息管理与电子商务系",
			"技术经济及管理系","组织管理系","市场营销系","会计与财务系"};
	String [] academy_id13= {"人口系统工程","社会保障系","行政管理系"};
	String [] academy_id14= {};
	String [] academy_id15= {};
	String [] academy_id16= {"日语系","英语系","俄语系","俄德法系","法语系","德语系"};
	String [] academy_id17= {"哲学系","社会学系","政治学系","法学系","中文系","艺术系","公共行政管理","国际经济","经济法"};
	String [] academy_id18= {"财政金融系","财政系","财政信贷系","党史系","工商管理系","工业财务会计系","工业管理系","工业会计系","工业经济系","会计系","金融系","贸易经济系","商业经济系","市场营销","统计系",
			"物资财会","物资经济","物资系","新闻系","信息管理系","银行管理工程系","哲学系","政法系","政经系","政治系"};
	String [] academy_id19= {"环境科学","环境工程"};
	String [] academy_id20= {};
	String [] academy_id21= {"金融系","统计系","国际贸易系","银行信息管理系","电子商务系","经济学系","财政系","产业与贸易经济系"};
	String [] academy_id22= {};
	String [] academy_id23= {"儿科系","法医学系","放射医学","妇幼卫生系","护理系","科技英语系","口腔系","口腔医学系","临床医学系","生物医学工程系","卫生管理系","卫生医学系","医疗系","医学系","预防医学系","生理学与病理生理学系","遗传学与分子生物学系","免疫学与病原生物学系",
			"人体解剖学与组织胚胎学系","公共卫生学系","药理学系","病理学系","药学系"};
	String [] academy_id24= {"建筑与结构工程系"};
	String [] academy_id25= {};
	String [] academy_id26= {};
	String [] academy_id27= {};
	String [] academy_id28= {};
	String [] academy_id29= {};
	String [] academy_id30= {};
	String [] academy_id31= {};
	String [] academy_id32= {};
	String [] academy_id33= {};
	String [] academy_id34= {};
	String [] academy_id35= {};
	
	//初始化学院
//	@Test
	public void initAcademys() {
		String initCode ="0000";
		
		for(int i=0;i<academys.length;i++) {
			SysOrg org = new SysOrg();
			org.setName(academys[i]);
			org.setParentId("0");
			org.setParentIds("root,0");
			org.setCode(i+1+""+initCode);
			org.setRemark(academysRemarks[i]);
			org.setType("COLLEGE");
			org.setMaster("admin");
			org.setSort(Double.valueOf(i+1+""+initCode));
			org.setUseable("ABLE");
			org.setOpenDate(DateUtils.parseDate("1896-01-01"));
			orgService.save(org);
		}
	}
	
	//初始化专业
	@Test
	public  void initDepartmet() {
//		initDepartmets(academy_id1,"289970ae04c84442b0c14d6bc585a3f2");
//		initDepartmets(academy_id2,"d36c53b3043843b4bbf7decbebee2d8f");
//		initDepartmets(academy_id3,"9773a09d17d148299d4510a98aa647fe");
//		initDepartmets(academy_id4,"0382b036dd024da78d25569545b53319");
//		initDepartmets(academy_id5,"e38f02357dda4c84870522aa328efbd5");
//		initDepartmets(academy_id6,"91bae15465ae46bda2f807680e11c2d4");
//		initDepartmets(academy_id7,"6aef583361af4b03969f1e16a9dc6fea");	
//		initDepartmets(academy_id8,"d605aa2cf511412497fa3d09da5416f7");
//		initDepartmets(academy_id9,"2440d8a304bb49dfbe429079746f80de");
//		initDepartmets(academy_id10,"7d87f2a182a44e209035b4fc31c4c06b");
//		initDepartmets(academy_id11,"8434cca27317418489ad9168a82353e7");		
//		initDepartmets(academy_id12,"c0bd79c54c26452ab5bef66d65c87fdc");
//		initDepartmets(academy_id13,"d86748bfe10d48388b4a91403c69d170");
//		initDepartmets(academy_id14,"8e79ad246b41456ab038b464f780a2ca");
//		initDepartmets(academy_id15,"f6384059f3394ee6aad6cf266930f146");
//		initDepartmets(academy_id16,"7728b1db55f24d7cbb6a37aa3e558e44");
//		initDepartmets(academy_id17,"80502a27a668440396c9824192a7a57f");
//		initDepartmets(academy_id18,"7a47a3984bef42f49881b9c6fdaa2b76");	
//		initDepartmets(academy_id19,"2d9c889336b240aa972e4f409426c027");
//		initDepartmets(academy_id20,"a19ef2ec02184e188a81014449c51b69");
//		initDepartmets(academy_id21,"fc55685d67f74bcda0ce8fa8c41d979a");	
//		initDepartmets(academy_id22,"336eefda5543428e9bfd3f88616d8882");
//		initDepartmets(academy_id23,"3f1d4bb725c340f38cb57cfd43189c1b");
//		initDepartmets(academy_id24,"f55ab65e8c244e5db6718243a1a7da43");
//		initDepartmets(academy_id25,"8ac6df63f4b24438af09edecf0b848b6");
//		initDepartmets(academy_id26,"38bc687c8d904af5b006456049b8682d");
//		initDepartmets(academy_id27,"c1da7b566d4c4eb4a6f138581f9dd7f9");
//		initDepartmets(academy_id28,"3dba0caa0a1440c9ac169f55bd36ec71");
		
//		initDepartmets(academy_id29,"0778a0e324ef40f589c00bc73dadcce6");
//		initDepartmets(academy_id30,"8553f0ee12e94301ab5c9f5b13d57df6");
//		initDepartmets(academy_id31,"f877202ac64f45b1b829ee03a9ed3e70");
//		initDepartmets(academy_id32,"740f4c0f881a471b8052fff0199fa014");
//		initDepartmets(academy_id33,"41e362a5d63640eebd052530de7b99bc");
//		initDepartmets(academy_id34,"62679da0d8084003a4f3c72e367c376a");
//		initDepartmets(academy_id35,"2a1c0ef902e644cb8aa79b300d050476");
	}
	
	private void initDepartmets(String [] departments , String academyId) {
		SysOrg org =  new SysOrg();
		org.setId(academyId);
		org = orgService.get(org);

		for(int i=0;i<departments.length;i++) {
			SysOrg child = new SysOrg();
			child.setParentId(org.getId());
			child.setParentIds(org.getParentIds()+","+org.getId());
			child.setType("DEPARTMENT");
			child.setMaster("admin");
			child.setUseable("ABLE");
			child.setOpenDate(DateUtils.parseDate("1896-01-01"));
			child.setName(departments[i]);
			child.setRemark(departments[i]);
			child.setCode(org.getCode()+""+i);
			child.setSort(Double.valueOf(org.getCode()+""+i));
			orgService.save(child);
		}
	}
	
}
