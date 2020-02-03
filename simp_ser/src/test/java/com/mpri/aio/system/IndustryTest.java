package com.mpri.aio.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.service.SysIndustryService;

/**
* .用户管理单元测试类
* @author lzw
* @date 2018年7月22日
*/
public class IndustryTest extends ApplicationTests {

	@Autowired
    private SysIndustryService service;
    
	
	private String [] ids = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T"};
	private String [] names = {"农、林、牧、渔业","采矿业","制造业","电力、热力、燃气及水生产和供应业","建筑业","批发和零售业","交通运输、仓储和邮政业","住宿和餐饮业","信息传输、软件和信息技术服务业","金融业","房地产业","租赁和商务服务业","科学研究和技术服务业","水利、环境和公共设施管理业","居民服务、修理和其他服务业","教育","卫生和社会工作","文化、体育和娱乐业","公共管理、社会保障和社会组织","国际组织"};
	/**
	 *. 新增  -- 通过
	 */
	@Test
    public void testAdd(){
		//条数
		int num=ids.length;
		//循环新增
		for(int i=0;i<=num;i++) {
			SysIndustry indu = new SysIndustry();
			indu.setCode(ids[i]);
			indu.setName(names[i]);
			indu.setSort(ids[i]);
			indu.setParentId("root");
			indu.setParentIds("root");
			try {
				
				service.save(indu);
			}catch(Exception e) {
				//// ex.printStackTrace();;
			}
		}
    }
    

}
