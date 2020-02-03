package com.mpri.aio.system.LicenseAuth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginLicenseConfigurer implements WebMvcConfigurer {

    @Value("${license.openflag}")
    private String licenseFlag;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        if("true".equals(licenseFlag)){
            registry.addInterceptor(LoginLicenseInterceptor()).addPathPatterns("/login");
        }
    }

    @Bean
    public LoginLicenseInterceptor LoginLicenseInterceptor() {
        return new LoginLicenseInterceptor();
    }

}
