package com.mpri.aio.webber.controller;

import com.mpri.aio.base.controller.BaseController;
import com.mpri.aio.common.exception.ExceptionResult;
import com.mpri.aio.common.exception.UnauthorizedException;
import com.mpri.aio.common.response.RestResponse;
import com.mpri.aio.common.response.RestToken;
import com.mpri.aio.schoolmate.model.SmSchoolmate;
import com.mpri.aio.schoolmate.service.SmSchoolmateService;
import com.mpri.aio.system.common.RedisCacheService;
import com.mpri.aio.system.init.InitCache;
import com.mpri.aio.system.model.SysSetting;
import com.mpri.aio.system.model.SysUser;
import com.mpri.aio.system.service.SysUserService;
import com.mpri.aio.system.shiro.JWTUtil;
import com.mpri.aio.system.utils.AESUtil;
import com.mpri.aio.system.utils.CaptchaUtil;
import com.mpri.aio.webber.vo.LoginVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.activation.CommandMap;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 校友网登录接口
 * @ProjectName: smmp_ser
 * @ClassName: WebberLoginController
 * @Author: zdl
 * @CreateDate: 2019\2\28 0028 9:41
 * @Version: 1.0
 */
@CrossOrigin
@RestController
@RequestMapping("webber/login")
public class WebberLoginController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SmSchoolmateService smSchoolmateService;

    private int saltTimes;

    private Map<String ,String > code= new ConcurrentHashMap<>();

    @Autowired
    private RedisCacheService redisCacheService;

    @RequestMapping(value = "/captcha")
    public void authImg(HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, Object> map = CaptchaUtil.generateCodeAndPic();
        session.setAttribute("authCode", map.get("code"));
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());
    }
    /**
     * 管理登录
     */
    @RequestMapping(value = "/login")
    public RestResponse<Map<String,Object>> login(String username, String password,
                                                  String comeFrom, String yzcode) {

        SysUser sysUser=sysUserService.getSysUserByUsername(username);
        //确认用户是否存在
        if(sysUser!=null) {

            //存在后处理
            //解密密码
            password= AESUtil.aesDecrypt(password);
            //加盐处理密码
            String safeCode=sysUser.getSafecode();
            ByteSource salt = ByteSource.Util.bytes(safeCode);
            String result = new Md5Hash(password,salt,getSaltTimes()).toString();
            String userId=sysUser.getId();

            //登陆密码校验
            if (sysUser.getPassword().equals(result)) {
                Map<String,Object> map = new HashMap<>();
                //注册token
                String token= JWTUtil.sign(username,userId, result,comeFrom);

                //获取token过期时间
                long tokenTime= JWTUtil.getTokenTime(token);

                //封装token
                RestToken restToken=new RestToken();
                restToken.setToken(token);
                restToken.setTokenTime(tokenTime);
                map.put("username",sysUser.getUsername());
                map.put("token",restToken);
                return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "登录成功！", map);
            } else {
                throw new UnauthorizedException("密码错误，请重新输入");
            }

        }else {
            return new RestResponse<Map<String,Object>>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
        }
    }
    /**
     * 管理登录
     */
    @RequestMapping(value = "/loginNew")
    public RestResponse<Map<String,Object>> loginNew(@RequestBody LoginVo loginVo) {
        String comeFrom = loginVo.getComeFrom();
        String password = loginVo.getPassword();
        String username = loginVo.getUsername();
        String yzcode = loginVo.getYzcode();
        SysUser sysUser=sysUserService.getSysUserByUsername(username);
        //确认用户是否存在
        if(sysUser!=null) {

            //存在后处理
            //解密密码
            password= AESUtil.aesDecrypt(password);
            //加盐处理密码
            String safeCode=sysUser.getSafecode();
            ByteSource salt = ByteSource.Util.bytes(safeCode);
            String result = new Md5Hash(password,salt,getSaltTimes()).toString();
            String userId=sysUser.getId();

            //登陆密码校验
            if (sysUser.getPassword().equals(result)) {
                Map<String,Object> map = new HashMap<>();
                //注册token
                String token= JWTUtil.sign(username,userId, result,comeFrom);

                //获取token过期时间
                long tokenTime= JWTUtil.getTokenTime(token);

                //封装token
                RestToken restToken=new RestToken();
                restToken.setToken(token);
                restToken.setTokenTime(tokenTime);
                map.put("username",sysUser.getUsername());
                map.put("token",restToken);
                return new RestResponse<Map<String,Object>>(ExceptionResult.REQUEST_SUCCESS, "登录成功！", map);
            } else {
                throw new UnauthorizedException("密码错误，请重新输入");
            }

        }else {
            return new RestResponse<Map<String,Object>>(ExceptionResult.NO_PERMISSION, "账号或密码错误，请重新输入！", null);
        }
    }
    public int getSaltTimes() {
        List<SysSetting> list=(List<SysSetting>)redisCacheService.getCacheByKey(InitCache.SETTING_CACHE, "ps.salt");
        saltTimes=Integer.parseInt(list.get(0).getValue());
        return saltTimes;
    }




    public void setSaltTimes(int saltTimes) {
        this.saltTimes = saltTimes;
    }
}
