package com.mpri.aio.webber.controller;


import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.logs.Logs;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.donation.model.DonProject;
import com.mpri.aio.donation.model.DonThanks;
import com.mpri.aio.enterprise.model.EntEnterprise;
import com.mpri.aio.enterprise.model.EntIntention;
import com.mpri.aio.enterprise.service.EntEnterpriseService;
import com.mpri.aio.enterprise.service.EntIntentionService;
import com.mpri.aio.system.shiro.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("webber/open/enterprise")
public class EnterpriseController {

    @Autowired
    private EntEnterpriseService entEnterpriseService;
    @Autowired
    private EntIntentionService entIntentionService;
    /**
     * 获取校友企业信息表列表
     * <p>Title: list</p>
     * <p>Description: </p>
     * @param pageNo
     * @param pageSize
     * @param entEnterprise
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/list")
    public RestResponse< PageIo<EntEnterprise>>list(int pageNo, int pageSize, EntEnterprise entEnterprise) {
        PageIo<EntEnterprise> pageInfo =  entEnterpriseService.loadByPage(pageNo,pageSize,entEnterprise);
        return new RestResponse< PageIo<EntEnterprise>>(ExceptionResult.REQUEST_SUCCESS, "获取校友企业列表成功", pageInfo);
    }

    /**
     * 获取校友企业信息表
     * <p>Title: get</p>
     * <p>Description: </p>
     * @param entEnterprise
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/get")
    public RestResponse<EntEnterprise> get( EntEnterprise entEnterprise) {
        return new RestResponse<EntEnterprise>(ExceptionResult.REQUEST_SUCCESS, "获取校友企业信息成功！",
                entEnterpriseService.get(entEnterprise));
    }


    /**
     * 新增校友企业校友意向表
     * <p>Title: add</p>
     * <p>Description: </p>
     * @param entIntention
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/save")
    public RestResponse<String> save(@Validated EntIntention entIntention, @RequestParam String token){

        if(null ==entIntention )
        {
            return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存信息不能为空", null);
        }

        String username = JWTUtil.getUsername(token);
        String userid = JWTUtil.getUserId(token);
        entIntention.setUserId(userid);
        entIntentionService.save(entIntention);
        return new RestResponse<String>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", "");
    }
}
