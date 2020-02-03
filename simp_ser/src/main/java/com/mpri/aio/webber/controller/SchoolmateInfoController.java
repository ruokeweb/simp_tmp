package com.mpri.aio.webber.controller;

import com.github.pagehelper.util.StringUtil;
import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmContact;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.schoolmate.vo.SmSchoolmateVo;
import com.mpri.aio.settings.model.SettingDepartment;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysDict;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.webber.vo.ContentVo;
import com.mpri.aio.webber.vo.DonSchoolmateInfoVo;
import com.mpri.aio.webber.vo.SchoolmateInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("webber/open/schoolmate")
public class SchoolmateInfoController extends BaseController {
    @Autowired
    private SmSchoolmateService smSchoolmateService;

    @Autowired
    private RedisCacheService redisCacheService;

    private Map<String,SettingDepartment> departmentCache;
    private Map<String,List<SysDict>> dictCache;

    private void InitMaps() {
        departmentCache= (Map<String,SettingDepartment>)redisCacheService.getBaseCache(new SettingDepartment(), InitCache.DEPART_BASE_CACHE);
        dictCache= (Map<String,List<SysDict>>)redisCacheService.getCache(new SysDict(), InitCache.DICT_CACHE);

    }



    @CrossOrigin
    @PostMapping(value = "/list")
    public RestResponse<PageIo<SchoolmateInfoVo>> list(@RequestBody SmSchoolmateVo smSchoolmateVo) {
        this.InitMaps();
        if(smSchoolmateVo.getSmSchoolmate()==null){
            smSchoolmateVo.setSmSchoolmate(new SmSchoolmate());
        }
        //smSchoolmateVo.getSmSchoolmate().setFlag(GlobalStr.DEFAULT_USER_TYPE);
        PageIo<SchoolmateInfoVo> pageInfo =  smSchoolmateService.getSchoolmateInfo(smSchoolmateVo.getPageNo(),smSchoolmateVo.getPageSize(),smSchoolmateVo.getSmSchoolmate());
        List<SchoolmateInfoVo> list = pageInfo.getList();
        if(list!=null&&list.size()>0){
            for (SchoolmateInfoVo smSchoolmate:list) {
                if(smSchoolmate.getSmEducation()!=null){
                    smSchoolmate.getSmEducation().setCollege(setFormatDepartValue(smSchoolmate.getSmEducation().getCollege()));
                    smSchoolmate.getSmEducation().setSeries(setFormatDepartValue(smSchoolmate.getSmEducation().getSeries()));
                    smSchoolmate.getSmEducation().setSpecialty(setFormatDepartValue(smSchoolmate.getSmEducation().getSpecialty()));
                }
                smSchoolmate.setSex(setFormatDictValue(smSchoolmate.getSex(),GlobalStr.SEX_TYPECODE));
            }
        }
        return new RestResponse< PageIo<SchoolmateInfoVo>>(ExceptionResult.REQUEST_SUCCESS, "校友基本信息列表获取成功", pageInfo);
    }

    /**
     * 通过手机号查询是否存在用户
     * @param phone
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getSchoolmateByPhone")
    public RestResponse<SchoolmateInfoVo> getSchoolmateByPhone ( @RequestParam("phone")  String phone){
        this.InitMaps();
        //通过手机号查询用户
        if(phone.isEmpty()){
            return new RestResponse< SchoolmateInfoVo>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", null);
        }
        SmContact smContact = new SmContact();
        smContact.setType(GlobalStr.PHONE);
        smContact.setContact(phone);
        SchoolmateInfoVo smSchoolmate =  smSchoolmateService.getSchoolmateByPhone(smContact);
        if (smSchoolmate != null&&smSchoolmate.getSmEducation()!=null){

            smSchoolmate.getSmEducation().setCollege(setFormatDepartValue(smSchoolmate.getSmEducation().getCollege()));
            smSchoolmate.getSmEducation().setSeries(setFormatDepartValue(smSchoolmate.getSmEducation().getSeries()));
            smSchoolmate.getSmEducation().setSpecialty(setFormatDepartValue(smSchoolmate.getSmEducation().getSpecialty()));
        }
        return new RestResponse< SchoolmateInfoVo>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", smSchoolmate);
    }
    /**
     * 通过手机号查询是否存在用户
     * @param contentVo
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getSchoolmateByPhoneNew")
    public RestResponse<SchoolmateInfoVo> getSchoolmateByPhoneNew ( @RequestBody ContentVo contentVo){
        this.InitMaps();
        //通过手机号查询用户
        if(contentVo.getPhone().isEmpty()){
            return new RestResponse< SchoolmateInfoVo>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", null);
        }
        SmContact smContact = new SmContact();
        smContact.setType(GlobalStr.PHONE);
        smContact.setContact(contentVo.getPhone());
        SchoolmateInfoVo smSchoolmate =  smSchoolmateService.getSchoolmateByPhone(smContact);
        if (smSchoolmate != null&&smSchoolmate.getSmEducation()!=null){

            smSchoolmate.getSmEducation().setCollege(setFormatDepartValue(smSchoolmate.getSmEducation().getCollege()));
            smSchoolmate.getSmEducation().setSeries(setFormatDepartValue(smSchoolmate.getSmEducation().getSeries()));
            smSchoolmate.getSmEducation().setSpecialty(setFormatDepartValue(smSchoolmate.getSmEducation().getSpecialty()));
        }
        return new RestResponse< SchoolmateInfoVo>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", smSchoolmate);
    }
    /**
     * 通过校友网注册校友
     * @param donSchoolmateInfoVo
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/saveSchoolmate")
    public RestResponse<Map<String,Object>> saveSchoolmate (@RequestBody DonSchoolmateInfoVo donSchoolmateInfoVo){
        Map<String,Object> map = new HashMap<String,Object>();

        smSchoolmateService.saveSchoolmate(donSchoolmateInfoVo);
        return new RestResponse< Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "注册成功", map);
    }

    /**
     * 通过id获取schoolmate
     * @param smSchoolmate
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getOne")
    public RestResponse<SmSchoolmate> getOne (SmSchoolmate smSchoolmate){
        return new RestResponse< SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", smSchoolmateService.get(smSchoolmate));
    }

    /**
     * 通过userid获取schoolmate
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getByUserId")
    public RestResponse<SmSchoolmate> getByUserId (HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String username = JWTUtil.getUsername(token);
        String userId = JWTUtil.getUserId(token);
        if(null == userId || "".equals(userId)){
            return new RestResponse< SmSchoolmate>(ExceptionResult.SYS_ERROR, "账号未登录", null);
        }
        System.err.println("username:"+username);
        System.err.println("userId:"+userId);
        SmSchoolmate smSchoolmate = new SmSchoolmate();
        smSchoolmate.setUserId(userId);
        smSchoolmate =smSchoolmateService.getByUserId(smSchoolmate);
        return new RestResponse< SmSchoolmate>(ExceptionResult.REQUEST_SUCCESS, "获取校友信息成功", smSchoolmate);
    }


    private String setFormatDepartValue(String value) {
        if(!StringUtil.isEmpty(value)) {
            //key: redis内key的规则：type:value
            SettingDepartment settingDepartments = departmentCache.get(value);
            if(settingDepartments !=null){
                return settingDepartments.getName();
            }
        }
        return "";
    }
    private String setFormatDictValue(String value,String typeCode) {
        if(!StringUtil.isEmpty(value)) {
            //key: redis内key的规则：type:value
            List<SysDict> sds=(List<SysDict>)dictCache.get(typeCode);
            if(!sds.isEmpty()){
                for(SysDict dict : sds) {
                    if(value.equals(dict.getValue())) {
                        return dict.getLabel();
                    }
                }
            }
        }
        return "";
    }

}
