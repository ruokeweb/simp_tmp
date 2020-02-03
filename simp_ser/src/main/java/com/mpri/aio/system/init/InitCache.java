package com.mpri.aio.system.init;

import java.util.Date;

import com.mpri.aio.settings.model.SettingPage;
import com.mpri.aio.settings.service.SettingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.service.AsAssociationService;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.settings.model.SettingSubject;
import com.mpri.aio.settings.model.SmStar;
import com.mpri.aio.settings.service.SettingDepartmentService;
import com.mpri.aio.settings.service.SettingSubjectService;
import com.mpri.aio.settings.service.SmStarService;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.model.SysArea;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.model.SysIndustry;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.service.SysAreaService;
import com.mpri.aio.system.service.SysDictService;
import com.mpri.aio.system.service.SysIndustryService;
import com.mpri.aio.system.service.SysSettingService;


/**
 * 初始化字典 地区 机构数据
 * @author Cary
 * @date 2018年9月3日
 */
@Component
@Order(value=1)
public class InitCache implements CommandLineRunner{
	
	@Autowired
	private MesGroupService mesGroupService;
	
	@Autowired
	private SmStarService SmStarService;
	
	@Autowired
	private SysAreaService sysAreaService;
	
	@Autowired
	private SettingDepartmentService settingDepartmentService;
	
	@Autowired
	private SysDictService sysDictService;
	
	@Autowired
	private AsAssociationService asAssociationService;
	
	@Autowired
	private SysSettingService sysSettingService;
	
	@Autowired
	private SettingSubjectService settingSubjectService;

	@Autowired 
	private RedisCacheService redisCacheService;

	@Autowired
	private SysIndustryService sysIndustryService;

	@Autowired
    private SettingPageService settingPageService;
	
	//基础字典cache
	public static final String DICT_BASE_CACHE="dictBaseCache";
	//键值字段cache
	public static final String DICT_CACHE="dictCache";
			
	//基础地区cache 
	public static final String AREA_BASE_CACHE="areaBaseCache";
	//键值地区cache
	public static final String AREA_CACHE="areaCache";	
	
	//键值地区cache <id,name>
	public static final String AREA_KEY_CACHE="areaKeyCache";	  
		
	//基础校友会cache
	public static final String ASSO_BASE_CACHE="assoBaseCache";
	
	//基础星级cache
	public static final String STAR_BASE_CACHE="starBaseCache";
	
	//基础消息组cache
	public static final String MESG_BASE_CACHE="mesgBaseCache";
	
	//基础院系cache
	public static final String DEPART_BASE_CACHE="departBaseCache";
	
	//键值院系cache
	public static final String DEPART_CACHE="departCache";
	
	//基础系统配置cache
	public static final String SETTING_BASE_CACHE="settingBaseCache";
	
	//系统配置cache
	public static final String SETTING_CACHE="settingCache";

	//学科基础cache
	public static final String SETTING_BASE_SUBJECT = "settingBaseSujectCache";
	//学科cache
	public static final String SETTING_SUBJECT = "settingSujectCache";
	
	public static final String SYS_INDUSTRY_CACHE  = "sysIndustryCache";
	public static final String SYS_BASE_INDUSTRY_CACHE  = "sysBaseIndustryCache";

    //小程序页面配置
    public static final String SETTING_PAGE="settingPageCache";
    public static final String SETTING_BASE_PAGE="settingBasePageCache";
	@Override
	public void run(String... args) throws Exception {
		System.out.println("》》系统基础数据初始化开始》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
		Date startDate = new Date();
		//特殊地区(小程序)
		sysAreaService.putKeyCache(new SysArea(), AREA_KEY_CACHE);	
		//码表：写入库
		redisCacheService.putCache(sysDictService, new SysDict() ,DICT_BASE_CACHE,DICT_CACHE);		
		//地区数据入缓存
		redisCacheService.putCache(sysAreaService, new SysArea() ,AREA_BASE_CACHE,AREA_CACHE);
		//校友会数据缓存
//		redisCacheService.putCache(asAssociationService, new AsAssociation() ,ASSO_BASE_CACHE,null);	
		//星级数据缓存
//		redisCacheService.putCache(SmStarService, new SmStar() ,STAR_BASE_CACHE,null);
		//信息组数据缓存
		redisCacheService.putCache(mesGroupService, new MesGroup() ,MESG_BASE_CACHE,null);
		//院系专业数据缓存
		redisCacheService.putCache(settingDepartmentService, new SettingDepartment() ,DEPART_BASE_CACHE,DEPART_CACHE);
		//设置数据缓存
		redisCacheService.putCache(sysSettingService, new SysSetting() ,SETTING_BASE_CACHE,SETTING_CACHE);	
		//学科数据缓存
		redisCacheService.putCache(settingSubjectService, new SettingSubject() ,SETTING_BASE_SUBJECT,SETTING_SUBJECT);
		//行业
		redisCacheService.putCache(sysIndustryService, new SysIndustry() ,SYS_BASE_INDUSTRY_CACHE,SYS_INDUSTRY_CACHE);
		//小程序页面配置
//        redisCacheService.putCache(settingPageService, new SettingPage(),SETTING_BASE_PAGE,SETTING_PAGE);

		Date endDate = new Date();
		System.out.println("》》系统基础数据初始化完毕,耗时：+"+((endDate.getTime()-startDate.getTime())/1000)+"s ->》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》》");
		
	}

}
