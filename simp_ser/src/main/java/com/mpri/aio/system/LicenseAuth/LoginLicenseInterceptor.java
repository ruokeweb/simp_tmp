package com.mpri.aio.system.LicenseAuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginLicenseInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginLicenseInterceptor.class);
    public  static  final  ThreadLocal<String>  licenseFlag = new ThreadLocal<>();

    @Autowired
    private Environment environment;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        VerifyLicense licenseVerify = new VerifyLicense();
        //校验证书是否有效
        boolean verifyResult = licenseVerify.verify(environment);
        if(verifyResult){
            return true;
        }else{
            licenseFlag.set("0");
            logger.info("证书校验无效");
            return true;
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        licenseFlag.remove();
    }
}
