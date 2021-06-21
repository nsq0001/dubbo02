package com.nisq.mybatis.controller;

import com.github.pagehelper.PageInfo;
import com.nisq.mybatis.entity.Permission;
import com.nisq.mybatis.entity.Role;
import com.nisq.mybatis.service.IPermissionService;
import com.nisq.mybatis.service.IRoleService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManageController {


    @DubboReference(version = "1.0")
    IPermissionService permissionService;

    @DubboReference(version = "1.0")
	IRoleService roleService;

    @RequestMapping("/permissionList")
    public String permissionList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpServletRequest request) {

        PageInfo<Permission> pageList = permissionService.getPermissionList(pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        return "manage/permissionList";
    }
    @RequestMapping("/permissionModify")
    public String permissionModify(@RequestParam(required = false) int id, Model model) {

        model.addAttribute("permission",permissionService.getById(id));
        return "manage/permissionModify";
    }
    @RequestMapping("/rolePermission/{id}")
    public String rolePermission(@PathVariable(required = false) int id, Model model) {
        Role role = roleService.findRoleById(id);
        List<Permission> permissionList = permissionService.getPermissions();
        model.addAttribute("role",role);
        model.addAttribute("permissionList",permissionList);
        return "manage/rolePermission";
    }
    @RequestMapping("/permissionAdd")
    public String permissionAdd() {
        return "manage/permissionModify";
    }

    @RequestMapping("/roleList")
    public String roleList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpServletRequest request) {

        PageInfo<Role> pageList = permissionService.getRoleList(pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        return "manage/roleList";
    }
    @RequestMapping("/menuList")
    public String menuList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize, HttpServletRequest request) {

        PageInfo<Role> pageList = permissionService.getMenuList(pageNum, pageSize);

        request.setAttribute("pageList", pageList);
        return "manage/menuList";
    }
}
