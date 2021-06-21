package com.nisq.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Permission;
import com.nisq.mybatis.entity.Role;

import java.util.List;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-04-0910:28
 */
public interface IPermissionService {
    PageInfo<Role> getMenuList(int pageNum, int pageSize);

    PageInfo<Role> getRoleList(int pageNum, int pageSize);

    PageInfo<Permission> getPermissionList(int pageNum, int pageSize);

    Permission getById(int id);

    RespStat insert(Permission permission);

    RespStat update(Permission permission);

    List<Permission> getPermissions();

    void addPermissions(int[] permissions, int id);
}
