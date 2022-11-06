package com.ankit.fruitseller.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogTimeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - startTime;
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String url = httpServletRequest.getMethod() + " " + httpServletRequest.getRequestURL().toString() + " params: [" + httpServletRequest.getQueryString() + "]";
        System.out.println(url + " took " + duration + " ms");
    }
}