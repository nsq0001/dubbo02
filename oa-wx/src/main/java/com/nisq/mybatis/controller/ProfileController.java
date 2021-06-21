package com.nisq.mybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-1310:53
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @RequestMapping("my")
    public String my(){
        return "my";
    }
}
