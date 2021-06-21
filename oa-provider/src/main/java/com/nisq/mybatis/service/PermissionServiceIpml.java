package com.nisq.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Permission;
import com.nisq.mybatis.entity.Role;
import com.nisq.mybatis.mapper.PermissionExample;
import com.nisq.mybatis.mapper.PermissionMapper;
import com.nisq.mybatis.mapper.RoleExample;
import com.nisq.mybatis.mapper.RoleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-2210:54
 */
@DubboService(version = "1.0", timeout = 10000, interfaceClass = IPermissionService.class)
@Component
public class PermissionServiceIpml implements IPermissionService {

    @Autowired
    PermissionMapper mapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getMenuList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RoleExample example = new RoleExample();
        List<Role> accounts = roleMapper.selectByExample(example);
        return new PageInfo<>(accounts, 3);
    }

    @Override
    public PageInfo<Role> getRoleList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        RoleExample example = new RoleExample();
        List<Role> accounts = roleMapper.selectByExample(example);
        return new PageInfo<>(accounts, 3);
    }

    @Override
    public PageInfo<Permission> getPermissionList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PermissionExample example = new PermissionExample();
        List<Permission> accounts = mapper.selectByExample(example);
        return new PageInfo<>(accounts, 3);
    }

    @Override
    public Permission getById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public RespStat insert(Permission permission) {

        int i = mapper.insertSelective(permission);

        if (i == 0) {
            return RespStat.build(400, "fail");
        }
        return RespStat.build(200);
    }

    @Override
    public RespStat update(Permission permission) {
        int i = mapper.updateByPrimaryKeySelective(permission);

        if (i == 0) {
            return RespStat.build(400, "fail");
        }
        return RespStat.build(200);
    }

    @Override
    public List<Permission> getPermissions() {
        PermissionExample example = new PermissionExample();
        return mapper.selectByExample(example);
    }

    @Override
    public void addPermissions(int[] permissions, int id) {


        mapper.addPermissions(permissions, id);
    }
}
