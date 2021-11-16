package com.brunomilitzer.bank.userfront.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        servletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        servletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        servletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        servletResponse.setHeader("Access-Control-Max-Age", "3600");
        servletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        if (!(servletRequest.getMethod().equalsIgnoreCase("OPTIONS"))) {
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pre-flight");
            servletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE");
            servletResponse.setHeader("Access-Control-Max-Age", "3600");
            servletResponse.setHeader("Access-Control-Allow-Headers", "authorization, content-type," +
                "access-control-request-headers,access-control-request-method,accept,origin,authorization,x-requested-with");
            servletResponse.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void destroy() { }
}
