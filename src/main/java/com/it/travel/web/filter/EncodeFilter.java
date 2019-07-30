package com.it.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 处理所有post方法提交的汉字乱码的问题
 */
@WebFilter("/*")
public class EncodeFilter implements Filter {
    public void destroy() {
    }

    //执行过滤的功能
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //转成子接口
        HttpServletRequest request = (HttpServletRequest) req;
        //如果是post请求，对汉字进行编码
        if ("POST".equals(request.getMethod())) {
            request.setCharacterEncoding("utf-8");
        }
        //放行
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
