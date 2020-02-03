package com.mpri.aio.app.association.mapper;

import com.github.pagehelper.Page;
import com.mpri.aio.app.association.vo.AssociationsDetailVo;
import com.mpri.aio.app.association.vo.AssociationsVo;
import com.mpri.aio.app.association.vo.SchoolMemberVo;
import com.mpri.aio.app.association.vo.SmEducationVo;
import com.mpri.aio.app.mine.vo.HisAssVo;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.AsAssociationUser;
import com.mpri.aio.association.model.SysUserAsso;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppAssociationMapper {

    Page<AssociationsVo> getMyAssociations(@Param("entity") AsAssociation asAssociation , @Param("userId")String userId,@Param("orderType")String orderType);

    AssociationsDetailVo getAssociationDetail(AsAssociation asAssociation);

    boolean getHasJoin(@Param("asId")String asId , @Param("userId")String userId);

    /**
     * 删除校友和校友会的关系
     * @param sysUserAsso
     */
    void deleteSysAs(SysUserAsso sysUserAsso);

    void addSysAs(SysUserAsso sysUserAsso);

    AsAssociation get(AsAssociation asAssociation);

    void update(AsAssociation asAssociation);

    Page<SchoolMemberVo> getSchoolMembers(AsAssociation asAssociation );

    List<SmEducationVo> getSmEducation(List<SchoolMemberVo> list);

    Page<AssociationsVo> getAssociations(@Param("entity")AsAssociation asAssociation, @Param("userId")String userId, @Param("orderType")String orderType);

    /**
     * 获取最新加入的校友会
     * @return
     */
    HisAssVo getAssociationOne(SysUserAsso sysUserAsso);
}
