package com.nisq.mybatis.service;

import com.nisq.mybatis.entity.Role;
import com.nisq.mybatis.mapper.RoleMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-2216:58
 */
@DubboService(version = "1.0",timeout = 10000,interfaceClass = IRoleService.class)
@Component
public class RoleServiceIpml implements IRoleService {

    @Autowired
    RoleMapper mapper;
    @Override
    public Role findRoleById(int id) {

        return mapper.findRoleById(id);

    }
}
