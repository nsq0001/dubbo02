package com.nisq.mybatis.controller;

import com.nisq.mybatis.entity.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageItem;
import weixin.popular.support.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1311:21
 */
@RestController
@RequestMapping("/msg")
public class WxMsgController {

    @Autowired
    WxConfig wxConfig;

    @RequestMapping("")
    public String msg(@RequestParam Map<String, String> parm, HttpServletRequest request, HttpServletResponse response) {
        LinkedHashMap<String , TemplateMessageItem> hashMap = new LinkedHashMap<>();
        TemplateMessageItem user = new TemplateMessageItem();
        user.setColor("#173177");
        user.setValue("倪先生\r\n");
        TemplateMessageItem cardNumber = new TemplateMessageItem();
        cardNumber.setColor("#173177");
        cardNumber.setValue("0426");
        TemplateMessageItem type = new TemplateMessageItem();
        type.setColor("#173177");
        type.setValue("消费");
        TemplateMessageItem money = new TemplateMessageItem();
        money.setColor("#173177");
        money.setValue("人民币260.00元\n");
        TemplateMessageItem left = new TemplateMessageItem();
        left.setColor("#173177");
        left.setValue("6504.09\n");

        TemplateMessageItem date = new TemplateMessageItem();
        date.setColor("#173177");
        date.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"\n");

        hashMap.put("User",user);
        hashMap.put("cardNumber",cardNumber);
        hashMap.put("type",type);
        hashMap.put("money",money);
        hashMap.put("left",left);
        hashMap.put("date",date);
        TemplateMessage message = new TemplateMessage();
        message.setTouser("otL1d6Wj5VEvg8xLTTsPM8n1M45c");
        message.setUrl("http://www.baidu.com");
        message.setTemplate_id("LWyH9m3y2A9nJFm4Fpg6IA8ESqbWSuNwmx84fPho3pE");
        message.setData(hashMap);
        MessageAPI.messageTemplateSend(TokenManager.getDefaultToken(),message);
        return "redirect:" + parm.get("uri");
    }
}
