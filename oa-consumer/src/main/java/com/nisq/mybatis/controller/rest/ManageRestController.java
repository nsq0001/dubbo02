package com.nisq.mybatis.controller.rest;

import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Account;
import com.nisq.mybatis.entity.Permission;
import com.nisq.mybatis.service.IPermissionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-2214:32
 */
@RestController
@RequestMapping("/api/v1/manage/permisson")
public class ManageRestController {

    private Account account;

    public Account getAccount(){
        return account;
    }
    @DubboReference(version = "1.0")
    IPermissionService permissionService;

    @RequestMapping("/add")
    public RespStat add(@RequestBody Permission permission, Model model){

        return permissionService.insert(permission);

    }
    @RequestMapping("/update")
    public RespStat update(@RequestBody Permission permission, Model model){
        return permissionService.update(permission);
    }


    public static void main(String[] args) {
        ManageRestController controller = new ManageRestController();

        Class<? extends ManageRestController> aClass = controller.getClass();

        try {
            Field field = aClass.getDeclaredField("account");
            Account account = new Account();
            field.set(controller,account);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(controller.getAccount());

    }
}
