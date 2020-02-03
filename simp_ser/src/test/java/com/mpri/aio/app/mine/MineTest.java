package com.mpri.aio.app.mine;

import com.github.pagehelper.Page;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.app.association.mapper.AppAssociationMapper;
import com.mpri.aio.app.association.vo.SchoolMemberVo;
import com.mpri.aio.app.index.mapper.IndexMapper;
import com.mpri.aio.app.index.service.IndexService;
import com.mpri.aio.app.index.vo.IndexInfoVo;
import com.mpri.aio.app.mine.mapper.AppHisInfomationmapper;
import com.mpri.aio.app.mine.vo.HisInfomationVo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.model.SysUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MineTest extends ApplicationTests{
	@Autowired
   	private AppAssociationMapper appAssociationMapper;

	@Autowired
	private AppHisInfomationmapper appHisInfomationmapper;

	@Autowired
	private IndexMapper indexMapper;
	/**
	 * 分页获取信息
	 */
	@Test
    public void testLoadContent(){
		/*AsAssociation asAssociation = new AsAssociation();
		Page<AssociationsVo> qqqq = appAssociationMapper.getMyAssociations(asAssociation, "a54c517a13b8459fbd4cc2f8b9211e0e","deac");
		System.out.println(qqqq);*/
		/*AsAssociation asAssociation = new AsAssociation();
		asAssociation.setId("afae32069dd94edeb5c963829d7f00bd");
		AssociationsDetailVo associationDetail = appAssociationMapper.getAssociationDetail(asAssociation);
		System.out.println("==============="+associationDetail);*/
		/*SysUserAsso asso = new SysUserAsso();
		asso.setUserId("9547f60db68f4cd4877eec0b5e87f1e3");
		asso.setAsId("4cde311426e34c4b8802d7427b1b9392");
		appAssociationMapper.deleteSysAs(asso);*/
		/*SysUser sysUser = new SysUser();
		sysUser.setId("a54c517a13b8459fbd4cc2f8b9211e0e");
		HisInfomationVo hisInfomation = appHisInfomationmapper.getHisInfomation(sysUser);
		System.out.println(hisInfomation);*/
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setCardStatus("NORMAL");
		List<IndexInfoVo> collarCardList = indexMapper.getCollarCardList(smSchoolmate, 100);
		System.out.println(collarCardList);
	}
}
