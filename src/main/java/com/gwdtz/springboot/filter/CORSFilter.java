package com.gwdtz.springboot.filter;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter  implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化filter==========================");
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req= WebUtils.toHttp(request);
        HttpServletResponse res = WebUtils.toHttp(response);
        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin")); //  这里最好明确的写允许的域名
        res.setHeader("Access-Control-Allow-Methods", req.getMethod());
        res.setHeader("Access-Control-Max-Age", "36000");
        res.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));


        filterChain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        System.out.println("销毁filter==========================");
    }

}