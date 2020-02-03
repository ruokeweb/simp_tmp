package com.mpri.aio.app.association;

import com.github.pagehelper.Page;
import com.mpri.aio.ApplicationTests;
import com.mpri.aio.act.model.ActSelforgContent;
import com.mpri.aio.app.act.service.AppActSelfContentService;
import com.mpri.aio.app.act.service.AppActSelfService;
import com.mpri.aio.app.act.service.AppActService;
import com.mpri.aio.app.association.mapper.AppAssociationMapper;
import com.mpri.aio.app.association.vo.AssociationsDetailVo;
import com.mpri.aio.app.association.vo.AssociationsVo;
import com.mpri.aio.app.association.vo.SchoolMemberVo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AssTest extends ApplicationTests{
	@Autowired
   	private AppAssociationMapper appAssociationMapper;
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
		AsAssociation asAssociation = new AsAssociation();
		asAssociation.setId("6d155ea673144517920809b27a3aea05");
		Page<SchoolMemberVo> schoolMembers = appAssociationMapper.getSchoolMembers(asAssociation);
		System.out.println(schoolMembers);
	}
}
