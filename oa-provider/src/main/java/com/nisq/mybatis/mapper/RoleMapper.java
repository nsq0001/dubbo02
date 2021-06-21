package com.nisq.mybatis.mapper;

import com.nisq.mybatis.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * RoleMapper继承基类
 */
@Repository
public interface RoleMapper extends MyBatisBaseDao<Role, Integer, RoleExample> {
    Role findRoleById(int id);
}