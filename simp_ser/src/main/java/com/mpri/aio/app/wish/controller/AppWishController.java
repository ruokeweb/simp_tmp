package com.mpri.aio.app.wish.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mpri.aio.app.wish.service.AppWishService;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.page.PageIo;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.schoolmate.model.SmWish;
import com.mpri.aio.system.common.GlobalStr;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.BadWordUtil;


/**
 *
 * @Description:  小程序母校留言——Controller
 * @Author:       LZQ
 * @Version:      v_1.0
 *
 */
@RestController
@RequestMapping("/app/wish")
public class AppWishController {

    @Autowired
    AppWishService appWishService;

    /**
     * 保存母校留言
     * @param smWish
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/save")
    public RestResponse<PageIo<SmWish>>  insert (SmWish smWish,int pageNo, int pageSize, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userid = JWTUtil.getUserId(token);
        String username = JWTUtil.getUsername(token);
        smWish.setUserId(userid);
        if(null == smWish.getName() || "".equals(smWish.getName()))
        {
            smWish.setName(username);
        }
//        BadWordUtil.isContaintBadWord(smWish.getInformation(), 2);
//        String ss=BadWordUtil.replaceBadWord(smWish.getInformation(), 2,"*");
//        smWish.setInformation(ss);
//        smWish.setIsshow(GlobalStr.SELFORG_STATUS_SUCCESS);
        smWish.setIsshow(GlobalStr.SELFORG_STATUS_AUDITING);
        smWish = appWishService.insert(smWish);
        SmWish search = new SmWish();
        search.setUserId(userid);
        PageIo<SmWish> pageInfo =  appWishService.loadByPage(pageNo,pageSize,search);
        return new RestResponse<PageIo<SmWish>>(ExceptionResult.REQUEST_SUCCESS, "保存成功！", pageInfo);
    }

    /**
     * 分页获取母校留言列表
     * @param pageNo
     * @param pageSize
     * @param smWish
     * @return
     */
    @CrossOrigin
    @PostMapping(value = "/loadbypage")
    public  RestResponse<PageIo<SmWish>> loadByPage(int pageNo, int pageSize, SmWish smWish,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String userId = JWTUtil.getUserId(token);
    	smWish.setUserId(userId);
//        smWish.setIsshow(GlobalStr.SELFORG_STATUS_SUCCESS);
        PageIo<SmWish> pageInfo =  appWishService.loadByPage(pageNo,pageSize,smWish);
        return new RestResponse<PageIo<SmWish>>(ExceptionResult.REQUEST_SUCCESS, "获取数据 成功！", pageInfo);
    }
}
