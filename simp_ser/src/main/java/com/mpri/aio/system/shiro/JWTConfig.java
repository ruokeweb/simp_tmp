package com.mpri.aio.system.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class JWTConfig {
	/**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter() {
    	//1.定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //2.设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //3.自定义过滤器
        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();
        filtersMap.put("jwt", new JWTFilter());

        shiroFilterFactoryBean.setFilters(filtersMap);

        //4.LinkedHashMap是有序的，进行顺序拦截器配置
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();



        //5.设置默认通过的的url
        filterChainDefinitionMap.put("/licensefile/**", "anon");
        filterChainDefinitionMap.put("/view/**", "anon");
        filterChainDefinitionMap.put("/file/**", "anon");
        filterChainDefinitionMap.put("/captcha", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/qrcode", "anon");
        filterChainDefinitionMap.put("/upload/file/**", "anon");
        filterChainDefinitionMap.put("/exchange/**", "anon");
        filterChainDefinitionMap.put("/webber/open/**", "anon");
        filterChainDefinitionMap.put("/webber/login/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //过滤小程序请求接口
        filterChainDefinitionMap.put("/app/wx/getMiniProConfig", "anon");
        filterChainDefinitionMap.put("/app/demo/**", "anon");
        filterChainDefinitionMap.put("/app/don/donProList", "anon");
        filterChainDefinitionMap.put("/app/don/getDonProDetail", "anon");
        filterChainDefinitionMap.put("/app/don/getLasterDonRecord", "anon");
        filterChainDefinitionMap.put("/app/don/getDonRecordByPro", "anon");
        filterChainDefinitionMap.put("/app/don/teamDonRecords", "anon");
        filterChainDefinitionMap.put("/app/don/getteamDonDetail", "anon");
        filterChainDefinitionMap.put("/app/don/save", "anon");
        filterChainDefinitionMap.put("/app/weichatpay/createUnifiedOrder", "anon");
        filterChainDefinitionMap.put("/app/don/updateState", "anon");
        filterChainDefinitionMap.put("/app/don/getId", "anon");
        filterChainDefinitionMap.put("/app/act/ActList", "anon");
        filterChainDefinitionMap.put("/app/act/bannerActs", "anon");
        filterChainDefinitionMap.put("/app/act/getNumByUserId", "anon");
        filterChainDefinitionMap.put("/app/act/getActDetail", "anon");
        filterChainDefinitionMap.put("/app/act/getAttendSmByAct", "anon");
        filterChainDefinitionMap.put("/app/actSelf/getActSelfList", "anon");
        filterChainDefinitionMap.put("/app/actSelf/getSelfDetail", "anon");
        filterChainDefinitionMap.put("/app/actSelf/getAttendSmBySelf", "anon");
        filterChainDefinitionMap.put("/app/login", "anon");
        filterChainDefinitionMap.put("/app/getBaseDatas", "anon");
        filterChainDefinitionMap.put("/app/sendCaptCha", "anon");
        filterChainDefinitionMap.put("/app/updateBackPwd", "anon");
        filterChainDefinitionMap.put("/app/getDepartBase", "anon");
        filterChainDefinitionMap.put("/app/getInfoByUserId", "anon");
        filterChainDefinitionMap.put("/app/regist", "anon");
        filterChainDefinitionMap.put("/app/registFacePass", "anon");
        filterChainDefinitionMap.put("/app/index/getCollarCardNum", "anon");
        filterChainDefinitionMap.put("/app/index/getCollarCardList", "anon");
        filterChainDefinitionMap.put("/app/index/getThankslist", "anon");
        filterChainDefinitionMap.put("/app/index/getIndexNews", "anon");
        filterChainDefinitionMap.put("/app/index/getNewsTrees", "anon");
        filterChainDefinitionMap.put("/app/index/getNewsDetail", "anon");
        filterChainDefinitionMap.put("/app/index/getrelationNews", "anon");
        filterChainDefinitionMap.put("/app/index/getIndexMediaMessage", "anon");
        filterChainDefinitionMap.put("/app/index/getIndexMediaMessageDetail", "anon");
        
        filterChainDefinitionMap.put("/app/act/hotActList", "anon");
        filterChainDefinitionMap.put("/app/index/getIndexBanners", "anon");
        filterChainDefinitionMap.put("/app/pay/getInfo", "anon");
        filterChainDefinitionMap.put("/app/wx/getContentByPage", "anon");
        filterChainDefinitionMap.put("/app/wx/send", "anon");
        filterChainDefinitionMap.put("/app/don/myDonList", "anon");
        filterChainDefinitionMap.put("/app/secondSubmit", "anon");
        filterChainDefinitionMap.put("/app/sendBackPwdCaptCha", "anon");
        filterChainDefinitionMap.put("/app/loginByOpenId", "anon");
        filterChainDefinitionMap.put("/app/findSmByFace", "anon");        
        filterChainDefinitionMap.put("/app/wx/getUserPortrait", "anon");
        filterChainDefinitionMap.put("/app/wx/track", "anon");
        filterChainDefinitionMap.put("/wxdata/wxdataPointed/save","anon");
        //二维码生成
        filterChainDefinitionMap.put("/managerServerQrcode", "anon");
        filterChainDefinitionMap.put("/wc/pay/miniProQrcode", "anon");
        //过滤用户数据传输
        filterChainDefinitionMap.put("/sys/sysuser/findUserListByUpdate", "anon");
        filterChainDefinitionMap.put("/sys/sysuser/updateSynchronizedUser", "anon");
        //小程捐赠获取项目列表
        filterChainDefinitionMap.put("/app/don/getAllDonPro", "anon");

        //6.访问401和404页面不通过我们的Filter
        filterChainDefinitionMap.put("/401", "anon");
        filterChainDefinitionMap.put("/404", "anon");


        //7.所有url必须通过认证才可以访问
        //filterChainDefinitionMap.put("/**", "authc");

        filterChainDefinitionMap.put("/**", "jwt");//通过自定义的jwt权限过滤器

        //8.设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(jwtRealm());
        // 关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     */
    @Bean
    public JWTRealm jwtRealm() {
    	JWTRealm jwtRealm = new JWTRealm();
        return jwtRealm;
    }


    @Bean
    public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy(){
        FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }

    /**
     * .开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * .配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean("lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
