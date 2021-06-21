package com.nisq.mybatis.filter;

import com.nisq.mybatis.entity.WxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.bean.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1311:24
 */

@WebFilter(filterName = "authFilter",urlPatterns = "/profile/*")
public class AuthFileter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFileter.class);

    @Autowired
    WxConfig wxConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("ahtufilter init-----");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        User user = (User)req.getSession().getAttribute("user");
        if(null==user){
            String uri = req.getRequestURI();
            resp.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid="+wxConfig.getAppID()+"&redirect_uri=http://rbiqh9.natappfree.cc/auth?uri="+uri+"&response_type=code" +
                    "&scope=snsapi_userinfo&state=STATE#wechat_redirect");
            return;
        }
        chain.doFilter(request,response);
    }
}
