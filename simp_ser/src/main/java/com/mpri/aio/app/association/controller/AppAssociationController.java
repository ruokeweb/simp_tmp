package com.mpri.aio.app.association.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.app.association.service.AppAssociationService;
import com.mpri.aio.app.association.vo.AssociationsDetailVo;
import com.mpri.aio.app.association.vo.AssociationsVo;
import com.mpri.aio.app.association.vo.SchoolMemberVo;
import com.mpri.aio.app.mine.service.AppSchoomateBaseInfoService;
import com.mpri.aio.association.model.AsAssociation;
import com.mpri.aio.association.model.SysUserAsso;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.system.shiro.JWTUtil;

@RestController
@RequestMapping("app/as")
public class AppAssociationController extends BaseController {
    @Autowired
    private AppAssociationService appAssociationService;
    
    
    @Autowired
    private AppSchoomateBaseInfoService appSchoomateBaseInfoService;

    /**
     * 获取我加入校友会的列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @param request
     * @param orderType
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getMyAssociations")
    public PageInfo<AssociationsVo> getMyAssociations(int pageNo, int pageSize, AsAssociation asAssociation, HttpServletRequest request,String orderType) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        return appAssociationService.getMyAssociations(pageNo,pageSize,asAssociation,userId,orderType);
    }
    /**
     * 获取我没有加入校友会的列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @param request
     * @param orderType
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getAssociations")
    public PageInfo<AssociationsVo> getAssociations(int pageNo, int pageSize, AsAssociation asAssociation, HttpServletRequest request,String orderType) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        return appAssociationService.getAssociations(pageNo,pageSize,asAssociation,userId,orderType);
    }

    /**
     * 根据校友会id获取校友会详情
     * @param asAssociation
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getAssociationDetail")
    public RestResponse<AssociationsDetailVo> getAssociationDetail(AsAssociation asAssociation, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        return new RestResponse<AssociationsDetailVo>(ExceptionResult.REQUEST_SUCCESS, "获取成功！", appAssociationService.getAssociationDetail(asAssociation,userId));
    }
    /**
     * 删除校友和校友会的关系
     * @param sysUserAsso
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/deleteSysAs")
    public RestResponse<String> deleteSysAs(SysUserAsso sysUserAsso, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        sysUserAsso.setUserId(userId);
        appAssociationService.deleteSysAs(sysUserAsso);
        
        //删除该校友会用户的所有卡
        appSchoomateBaseInfoService.quitAsDelCard(userId, sysUserAsso.getAsId());
        
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "退出成功！", "");
    }

    /**
     * 新增校友和校友会关系
     * @param sysUserAsso
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/addSysAs")
    public RestResponse<String> addSysAs(SysUserAsso sysUserAsso, HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(authorization);
        sysUserAsso.setUserId(userId);
        if(appAssociationService.getHasJoin(sysUserAsso)) {
            return new RestResponse<String>(ExceptionResult.DATA_USED, "已经存在对应关系！", "");
        }
        appAssociationService.addSysAs(sysUserAsso);
        
        SmSchoolmate schoolmate = new SmSchoolmate();
        schoolmate.setUserId(userId);
        schoolmate = appSchoomateBaseInfoService.getLevelByUserId(schoolmate);
        
        //保存该校友会下用户的校友卡
        appSchoomateBaseInfoService.joinAsSaveCard(schoolmate, sysUserAsso.getAsId());
        
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "添加成员成功", "");
    }

    /**
     * 该校友会下的校友列表
     * @param pageNo
     * @param pageSize
     * @param asAssociation
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getSchoolMembers")
    public PageInfo<SchoolMemberVo> getSchoolMembers(int pageNo, int pageSize, AsAssociation asAssociation) {
        return appAssociationService.getSchoolMembers(pageNo,pageSize,asAssociation);
    }
}
