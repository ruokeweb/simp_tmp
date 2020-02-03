package com.mpri.aio.app.association.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mpri.aio.app.association.mapper.AppAssociationMapper;
import com.mpri.aio.app.association.vo.AssociationsDetailVo;
import com.mpri.aio.app.association.vo.AssociationsVo;
import com.mpri.aio.app.association.vo.SchoolMemberVo;
import com.mpri.aio.app.association.vo.SmEducationVo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.system.common.GlobalStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AppAssociationService {

    @Autowired
    private AppAssociationMapper appAssociationMapper;


    /**
     *  获取我加入的校友会列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @param userId
     * @param orderType
     * @return
     */
    public PageIo<AssociationsVo> getMyAssociations(int pageNo, int pageSize, AsAssociation asAssociation, String userId ,String orderType) {
        PageHelper.startPage(pageNo, pageSize);
        Page<AssociationsVo> myAssociations = appAssociationMapper.getMyAssociations(asAssociation, userId,orderType);
        PageIo<AssociationsVo> pageInfo = new PageIo<AssociationsVo>(myAssociations);
        return pageInfo;
    }

    /**
     * 获取校友会详情
     * @param asAssociation
     * @return
     */
    public AssociationsDetailVo getAssociationDetail(AsAssociation asAssociation, String userId) {
        AssociationsDetailVo associationsDetailVo = appAssociationMapper.getAssociationDetail(asAssociation);
        /**
         * 判断是否已经加入到该校友会
         */
        associationsDetailVo.setHasJoin(appAssociationMapper.getHasJoin(asAssociation.getId(),userId));
        return associationsDetailVo;
    }

    @Transactional(
            readOnly = false
    )
    public void deleteSysAs (SysUserAsso asso){
       appAssociationMapper.deleteSysAs(asso);
        /**
         * 退出成功之后，将加入人数减一
         */
        AsAssociation asAssociation = new AsAssociation();
        asAssociation.setId(asso.getAsId());
        AsAssociation asAssociation1 = appAssociationMapper.get(asAssociation);
        asAssociation.setSum(asAssociation1.getSum()-1);
        appAssociationMapper.update(asAssociation);
    }

    public boolean getHasJoin(SysUserAsso asso){
        return appAssociationMapper.getHasJoin(asso.getAsId(), asso.getUserId());
    }

    @Transactional(
            readOnly = false
    )
    public void addSysAs(SysUserAsso sysUserAsso) {
        appAssociationMapper.addSysAs(sysUserAsso);
        /**
         * 退出成功之后，将加入人数减一
         */
        AsAssociation asAssociation = new AsAssociation();
        asAssociation.setId(sysUserAsso.getAsId());
        AsAssociation asAssociation1 = appAssociationMapper.get(asAssociation);
        asAssociation.setSum(asAssociation1.getSum()+1);
        appAssociationMapper.update(asAssociation);
    }

    /**
     * 获取该校友会下的校友列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @return
     */
    public PageIo<SchoolMemberVo> getSchoolMembers(int pageNo, int pageSize, AsAssociation asAssociation) {
        PageHelper.startPage(pageNo, pageSize);
        Page<SchoolMemberVo> myAssociations = appAssociationMapper.getSchoolMembers(asAssociation);
        long total = myAssociations.getTotal();
        PageIo<SchoolMemberVo> pageInfo = new PageIo<SchoolMemberVo>(myAssociations);
        if(total!=0){
            List<SchoolMemberVo> list = pageInfo.getList();
            List<SmEducationVo> smEducationVos =appAssociationMapper.getSmEducation(list);
            Map<String ,SmEducationVo> map = new HashMap<String ,SmEducationVo>();
            if(!smEducationVos.isEmpty()){
                for (SmEducationVo smEducationVo:smEducationVos) {
                    SmEducationVo smEducation = map.get(smEducationVo.getUserId());
                    if(smEducation==null){
                        map.put(smEducationVo.getUserId(),smEducationVo);
                    }else{
                        if(!"IS_DEFAULT".equals(smEducation.getIsDefault())){
                            if(smEducationVo.getCreateDate().getTime()>smEducation.getCreateDate().getTime()){
                                map.put(smEducationVo.getUserId(),smEducationVo);
                            }
                        }
                    }
                }
            }
            if(!list.isEmpty()){
                for (SchoolMemberVo schoolMemberVo:list) {
                    SmEducationVo smEducationVo = map.get(schoolMemberVo.getId());
                    if(smEducationVo!=null){
                        schoolMemberVo.setSpecialty(smEducationVo.getSpecialty());
                        schoolMemberVo.setStartdate(smEducationVo.getStartdate());
                    }
                }
            }
        }
        return pageInfo;
    }

    public PageInfo<AssociationsVo> getAssociations(int pageNo, int pageSize, AsAssociation asAssociation, String userId, String orderType) {
        PageHelper.startPage(pageNo, pageSize);
        Page<AssociationsVo> myAssociations = appAssociationMapper.getAssociations(asAssociation, userId,orderType);
        PageIo<AssociationsVo> pageInfo = new PageIo<AssociationsVo>(myAssociations);
        return pageInfo;
    }
}
