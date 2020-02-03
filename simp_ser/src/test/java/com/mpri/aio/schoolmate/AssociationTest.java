package com.mpri.aio.schoolmate;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.schoolmate.vo.FormSelectDatas;

public class AssociationTest extends ApplicationTests{

	@Autowired
	private AsAssociationService asAssociationService;
	
	@Test
	public void findAsIdByUser() {
//		List<String> asIds = asAssociationService.findAsIdByUser("9547f60db68f4cd4877eec0b5e87f1e3");
////		List<AsAssociation> asIds = asAssociationService.findAsByUser("9547f60db68f4cd4877eec0b5e87f1e3");
//		
//		for (AsAssociation id : asIds) {
//			System.err.println("校友会========"+id.getName());
//		}
	}
	
//	@Test
	public void getAllFormSelectDatas() {
//		AsAssociation asAssociation = new AsAssociation();
//		FormSelectDatas fsd = asAssociationService.getAllFormSelectDatas(asAssociation);
//		System.err.println("=========================YES===============================");
	}
}
