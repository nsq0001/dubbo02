package com.nisq.mybatis.controller.rest;

import com.nisq.mybatis.service.IAccountService;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-2214:32
 */
@RestController
@RequestMapping("/api/v1/account")
@Api(tags = "账户管理")
public class AccountRestController {


    @DubboReference(version = "1.0")
    IAccountService iAccountService;

    @GetMapping("/list")
    public Object list(int pageNum,int pageSize){

        return iAccountService.getPagList(pageNum,pageSize);

    }
}
