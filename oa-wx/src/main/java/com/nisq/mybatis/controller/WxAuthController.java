package com.nisq.mybatis.controller;

import com.nisq.mybatis.entity.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1311:21
 */
@Controller
@RequestMapping("/auth")
public class WxAuthController {

    @Autowired
    WxConfig wxConfig;

    @RequestMapping("")
    public String auth(@RequestParam Map<String, String> parm, HttpServletRequest request, HttpServletResponse response) {
        String code = parm.get("code");
        SnsToken snsToken = SnsAPI.oauth2AccessToken(wxConfig.getAppID(), wxConfig.getAppsecret(), code);

        User user = SnsAPI.userinfo(snsToken.getAccess_token(), wxConfig.getAppID(), "zh_CN");

        request.getSession().setAttribute("user", user);

        return "redirect:" + parm.get("uri");
    }
}
