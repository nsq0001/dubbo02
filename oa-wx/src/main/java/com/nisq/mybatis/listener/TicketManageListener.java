package com.nisq.mybatis.listener;

import com.nisq.mybatis.entity.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import weixin.popular.support.TicketManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1214:54
 */
@WebListener
public class TicketManageListener implements ServletContextListener {

    @Autowired
    WxConfig wxConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())
                .getAutowireCapableBeanFactory().autowireBean(this);
        TicketManager.init(wxConfig.getAppID(),15,60 * 119);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        TicketManager.destroyed();
    }
}
