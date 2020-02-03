package com.mpri.aio.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mpri.aio.ApplicationTests;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.service.SysDictService;
/**
 * .字典测试类
 * @author xw
 * @date 2018年8月3日
 */
public class DictTest extends ApplicationTests{
	
	@Autowired
    private SysDictService sysDictService;
	
	String [] edu_model = new String [] {
			"普通","第二学位","专业学位","同等学历","联合培养","成人高等教育","高等教育自学考试","远程教育（网络教育）","非学历教育","其他"
	};
	
	String [] edu_degree = new String [] {
			"学士学位","硕士学位","博士学位"
	};
	
	String [] edu_record = new String[] {
			"专科","本科","专升本","高起本","高起专","自考主考院校全日制教学班","自学考试学历文凭班","高层管理者教育发展培训（EDP）","职教师资培训","职业技能培训","专业技术人员继续教育培训","党政干部培训","公务员网络培训","专项培训","旁听硕士"			
	};
			
	String [] edu_type = new String[] {
			"统分","定向","委培","其他","自费","在职教育","脱产","业余","函授","远程教育","培训","其他"
	};
	
	/**
	 * 新增--通过
	 */
	@Test
    public void testAddDicts(){
		//条数
		//循环新增
		for(int i = 0;i < edu_type.length;i++){
			SysDict sysDict = new SysDict();
			sysDict.setTypeCode("EDU_TYPE");
			sysDict.setLabel(edu_type[i]);
			sysDict.setValue("EDU_TYPE_"+i);
			sysDict.setRemark(edu_type[i]);
			sysDict.setSort((long)i);
			try {
				sysDictService.save(sysDict);
			}catch(Exception e) {
				//// ex.printStackTrace();;
			}
		}
    }
	
	
	 /**
	  * 删除--通过
     */
//    @Test
    public void testDelete() {
    	SysDict sysDict = new SysDict();
    	sysDict.setId("6fcac6beaabc46e1a8c3729d53827fc4");
    	sysDictService.delete(sysDict);
    	
    }
    
    /**
	 * 根据id获取单条数据 - 通过
	 */
	//@Test
    public void testGet(){
		SysDict sd = new SysDict();
		sd.setId("1e790d1e9ceb4425bbbca422fbd5836a");
		SysDict sysDict=sysDictService.get(sd);
		
		super.outprint("com.mpri.aio.system.model.SysDict", sysDict);
    }
	
	
	 /**
	  * 根据typecode获取对象  --通过
     */
//    @Test
    public void testGetDictByTypecode(){
    	
    	List<SysDict> sysDict = sysDictService.getSysDictByTypecode("SEX");
    	System.out.println("缓存测试：此处应取库");
    	List<SysDict> sysDict2 = sysDictService.getSysDictByTypecode("SEX");
    	System.out.println("缓存测试：此处应有掌声");
		//super.outprint("com.mpri.aio.system.model.SysDict", sysDict);
    }
	
    
    /**
	 * 根据条件查询所有列表数据 --通过
	 */
//    @Test
    public void  testloadAllListBy() {
    	SysDict sysDict = new SysDict();
    	sysDict.setSort((long)1);
		List<SysDict> sysDictList = sysDictService.loadAllListBy(sysDict);
		super.outprint("java.util.List", sysDictList);
		   
    }
	
    
    
    /**
	 * 更新-通过
	 */
//	@Test
    public void testUpdateDict(){
		
			SysDict sysDict = new SysDict();
			sysDict.setId("940b8b6451524dbcacd533e464b993cd");
			try {
				sysDict.setFlag("2");
				sysDict.setTypeCode("B9");
				sysDict.setLabel("B9");
				sysDict.setValue("B9");
				sysDict.setRemark("B9");
				sysDict.setSort((long)20);
				sysDictService.save(sysDict);
			}catch(Exception e) {
				//// ex.printStackTrace();;
			}
	}
	
	
	 /**
     * 分页查询数据  --通过
     */
    //@Test
    public void testloadDict(){
    	
    	try{
    		SysDict sysDict = new SysDict();
    		PageIo<SysDict> p = sysDictService.loadByPage(1, 6, sysDict);

    		super.outprint("com.github.pagehelper.Page", p);
    	}catch(Exception e) {
    		//// ex.printStackTrace();;
    	}
    }
	
    
	
	

}
