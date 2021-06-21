package com.nisq.mybatis.controller.rest;

import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Permission;
import com.nisq.mybatis.service.IPermissionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-2214:32
 */
@RestController
@RequestMapping("/api/v1/rolePermisson")
public class RolePermissionRestController {


    @DubboReference(version = "1.0")
    IPermissionService permissionService;

    @RequestMapping("/add")
    public RespStat add(@RequestBody Permission permission, Model model){

        return permissionService.insert(permission);

    }
    @RequestMapping("/update")
    public RespStat update(@RequestParam int[] permissions,int id, Model model){

        System.out.println(permissions+"id:="+id);
        permissionService.addPermissions(permissions,id);
        return RespStat.build(200);
    }
}
