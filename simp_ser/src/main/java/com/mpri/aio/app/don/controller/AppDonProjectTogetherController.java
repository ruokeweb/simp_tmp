package com.mpri.aio.app.don.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.mpri.aio.app.don.service.AppDonProjectTogetherService;
import com.mpri.aio.app.utils.service.TemplateUtils;
import com.mpri.aio.app.wxdata.model.WxdataTemplateSend;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.donation.model.DonProjectTogether;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;

import tk.mybatis.mapper.util.StringUtil;

@RestController
@RequestMapping("/app/don")
public class AppDonProjectTogetherController {

    @Autowired
    private AppDonProjectTogetherService appDonProjectTogetherService;

    /**
     * don/teamDonRecords
     * 小程序分页查询捐赠项目列表--捐赠
     * @param pageNo
     * @param pageSize
     * @param donProjectTogether
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/teamDonRecords")
    public PageInfo<DonProjectTogether> teamDonRecords(int pageNo, int pageSize, DonProjectTogether donProjectTogether){

        donProjectTogether.setStatus(GlobalStr.SELFORG_STATUS_SUCCESS);
        PageIo<DonProjectTogether> pageInfo =  appDonProjectTogetherService.donProList(pageNo,pageSize,donProjectTogether);
        return pageInfo;
    }

    /**
     * don/teamDonRecords
     * 小程序分页查询一起捐详细信息
     * @param donProjectTogether
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/getteamDonDetail")
    public RestResponse<DonProjectTogether> getteamDonDetail(DonProjectTogether donProjectTogether) {
//        PageIo<DonProjectTogether> pageInfo =  appDonProjectTogetherService.donProList(pageNo,pageSize,donProjectTogether);
        return new RestResponse<DonProjectTogether>(ExceptionResult.REQUEST_SUCCESS, "一起捐项目信息获取成功", appDonProjectTogetherService.getById(donProjectTogether));
    }


    /**
     * 增加或者更新捐赠记录管理
     * <p>Title: add</p>
     * <p>Description: </p>
     * @return RestResponse<DonRecord>
     */
    @CrossOrigin
    @PostMapping(value = "/teamsave")
    public RestResponse<DonProjectTogether> save(DonProjectTogether donProjectTogether,HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String touser =request.getParameter("touser");
        String form_id =request.getParameter("form_id");
        WxdataTemplateSend templateSend = TemplateUtils.getTemplateSend(touser, form_id);
        if(StringUtil.isNotEmpty(token)){
            // 当前时间
            String userid = JWTUtil.getUserId(token);
            donProjectTogether.setUserId(userid);
        }
        return new RestResponse<DonProjectTogether>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", appDonProjectTogetherService.save(donProjectTogether,templateSend));
    }


    /**
     * don/teamDonRecords
     * 小程序分页查询捐赠项目列表--捐赠
     * @param pageNo
     * @param pageSize
     * @param donProjectTogether
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/myteamDonRecords")
    public PageInfo<DonProjectTogether> getmyteamDonRecords(int pageNo, int pageSize, DonProjectTogether donProjectTogether, HttpServletRequest request){

        String token = request.getHeader("Authorization");
        // 当前时间
        String userid = JWTUtil.getUserId(token);
        donProjectTogether.setUserId(userid);
        PageIo<DonProjectTogether> pageInfo =  appDonProjectTogetherService.donProList(pageNo,pageSize,donProjectTogether);
        return pageInfo;
    }


}
