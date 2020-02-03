package com.mpri.aio.app.mine.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.app.mine.mapper.AppSmBaseInfoMapper;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.common.utils.IdGen;
import com.mpri.aio.message.model.MesGroup;
import com.mpri.aio.message.service.MesGroupService;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.settings.mapper.SettingCardMapper;
import com.mpri.aio.settings.model.SettingCard;
import com.mpri.aio.settings.model.SettingUserCard;
import com.mpri.aio.settings.service.SettingUserCardService;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.utils.RunSettingParamsUtils;

/**
 * 校友个人基本信息Service
 * @author syp
 *
 */
@Service
public class AppSchoomateBaseInfoService {
	
	
	@Autowired
	private AppSmBaseInfoMapper mapper;
	
	
	@Autowired
	private MesGroupService mesGroupService;
	
	@Autowired
	private SettingCardMapper settingCardMapper;
	
	@Autowired
	private SettingUserCardService settingUserCardService;
	
	
	/**
	 * 更新虚拟头像
	 * @param sysUser
	 */
	@Transactional(readOnly = false)
	public void updateVirtualPhoto(SysUser sysUser) {
		mapper.updateVirtualPhoto(sysUser);
	}
	
	/**
	 * 更新虚拟名称
	 * @param sysUser
	 */
	@Transactional(readOnly = false)
	public void updateVirtualName(SysUser sysUser) {
		mapper.updateVirtualName(sysUser);
	}

	/**
	 * 更新密码
	 * @param sysUser
	 */
	@Transactional(readOnly = false)
	public void updatePassword(SysUser sysUser) {
		mapper.updatePassword(sysUser);
	}
	
	/**
	 * 获取校友信息
	 */
	public SmSchoolmate getSmInfoByUserId(SmSchoolmate schoolmate) {
		return mapper.getSmInfoByUserId(schoolmate);
	}
	
	
	/**
	 * 根据userId 获取校友等级
	 */
	public SmSchoolmate getLevelByUserId(SmSchoolmate schoolmate) {
		return mapper.getLevelByUserId(schoolmate);
	}
	
	/**
	 * 
	 * 获取组
	* <p>Title: getGroup</p>  
	* <p>Description: </p>  
	* @param userName
	* @return  MesGroup
	 */
	public List<MesGroup> getGroup(String userId) {
		SmSchoolmate smSchoolmate = new SmSchoolmate();
		smSchoolmate.setUserId(userId);
		smSchoolmate = this.getSmInfoByUserId(smSchoolmate);
		List<MesGroup> mesGroups =  mesGroupService.gerMesGroupBy(smSchoolmate,getMesGroupsCache());
		return mesGroups;
	}

	/**
	 * 获取信息组缓存
	 * @return
	 */
	public List<MesGroup> getMesGroupsCache() {
		MesGroup mesGroup = new MesGroup();
		List<MesGroup> list = mesGroupService.loadConditionListBy(mesGroup);
		return list;
	}
	
	
	/**
	 * 插入校友卡中间表
	 */
	@Transactional(readOnly = false)
	public void saveUserCard(SmSchoolmate schoolmate) {
		String userId = schoolmate.getUserId();
		if(StringUtil.isNotEmpty(userId)) {
	    	List<AsAssociation> list = mapper.loadAsByUserId(userId);
	    	for (AsAssociation asAssociation : list) {
	    		SettingCard card = new SettingCard();
	    		//此处先用默认的级数来写
	    		card.setStartLevel(schoolmate.getLevel());
	    		card.setEndLevel(schoolmate.getLevel());
	    		card.setAsId(asAssociation.getId()); 
	    		List<SettingCard> cardList =  settingCardMapper.getSettingCardByLevel(card);
	    		for (SettingCard settingCard : cardList) {
	    			SettingUserCard settingUserCard = new SettingUserCard();
	    			settingUserCard.setCardId(settingCard.getId());
	    			settingUserCard.setUserId(userId);
	    			//卡号暂无算法(先放入时间)
	    			settingUserCard.setCardNo(String.valueOf(new Date().getTime()));
	    			
	    			settingUserCardService.save(settingUserCard);
				}
			}
		}
	}
	
	/**
	 * 加入校友会时新增校友卡数据
	 */
	@Transactional(readOnly = false)
	public void joinAsSaveCard(SmSchoolmate schoolmate,String asId) {
		SettingCard card = new SettingCard();
		//此处先用默认的级数来写
		card.setStartLevel(schoolmate.getLevel());
		card.setEndLevel(schoolmate.getLevel());
		card.setAsId(asId); 
		List<SettingCard> cardList =  settingCardMapper.getSettingCardByLevel(card);
		for (SettingCard settingCard : cardList) {
			SettingUserCard settingUserCard = new SettingUserCard();
			settingUserCard.setCardId(settingCard.getId());
			settingUserCard.setUserId(schoolmate.getUserId());
			//卡号暂无算法(先放入时间)
			settingUserCard.setCardNo(String.valueOf(new Date().getTime()));
			
			settingUserCardService.save(settingUserCard);
		}
	}
	
	/**
	 * 退出校友会时删除校友卡相关信息
	 */
	@Transactional(readOnly = false)
	public void quitAsDelCard(String userId,String asId) {
		SettingUserCard settingUserCard = new SettingUserCard();
		settingUserCard.setCardId(userId);
		settingUserCard.setUserId(asId);
		settingUserCardService.delete(settingUserCard);
	}
	
}
