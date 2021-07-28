package com.gwdtz.springboot.config;

import com.gwdtz.springboot.filter.JwtFilter;
import com.gwdtz.springboot.jwt.JwtDefaultSubjectFactory;
import com.gwdtz.springboot.shiro.JwtRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//springBoot整合jwt实现认证有三个不一样的地方，对应下面abc
@Configuration
public class ShiroConfig {
    /*
     * a. 告诉shiro不要使用默认的DefaultSubject创建对象，因为不能创建Session
     * */
    @Bean
    public SubjectFactory subjectFactory() {
        return new JwtDefaultSubjectFactory();
    }

    @Bean
    public Realm realm() {
        return new JwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        /*
         * b
         * */
        // 关闭 ShiroDAO 功能
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        //禁止Subject的getSession方法
        securityManager.setSubjectFactory(subjectFactory());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/unauthenticated");
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        /*
         * c. 添加jwt过滤器，并在下面注册
         * 也就是将jwtFilter注册到shiro的Filter中
         * 指定除了login和logout之外的请求都先经过jwtFilter
         * */
        Map<String, Filter> filterMap = new HashMap<String, Filter>();
        //这个地方其实另外两个filter可以不设置，默认就是
        filterMap.put("anon", new AnonymousFilter());
        filterMap.put("jwt", new JwtFilter());
        filterMap.put("logout", new LogoutFilter());
        shiroFilter.setFilters(filterMap);

        // 拦截器
        Map<String, String> filterRuleMap = new LinkedHashMap<String, String>();
        //前后端带login登录的或者其他登录的通通放行
        //filterRuleMap.put("/**/index.html", "anon");
        filterRuleMap.put("/**.html", "anon");
        filterRuleMap.put("/**/front/**", "anon");
        filterRuleMap.put("/**/moban/**", "anon");
        filterRuleMap.put("/statics/**", "anon");
        filterRuleMap.put("/api/**", "anon");
        filterRuleMap.put("/css/**", "anon");
        filterRuleMap.put("/images/**", "anon");
        filterRuleMap.put("/js/**", "anon");
        filterRuleMap.put("/lib/**", "anon");
        filterRuleMap.put("/page/**", "anon");
        filterRuleMap.put("/view/**", "anon");
        filterRuleMap.put("/**/upload/**", "anon");
        filterRuleMap.put("/upfile/**", "anon");
        filterRuleMap.put("/**/login/**", "anon");
        filterRuleMap.put("/**.js", "anon");
        filterRuleMap.put("/druid/**", "anon");
        filterRuleMap.put("/swagger**/**", "anon");
        filterRuleMap.put("/**/swagger**/**", "anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/v2/**", "anon");



        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/logout", "logout");
        filterRuleMap.put("/**", "jwt");
        //filterRuleMap.put("/**", "anon");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilter;
    }
}
