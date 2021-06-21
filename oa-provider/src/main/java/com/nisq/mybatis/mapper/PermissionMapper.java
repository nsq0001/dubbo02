package com.nisq.mybatis.mapper;

import com.nisq.mybatis.entity.Permission;
import org.springframework.stereotype.Repository;

/**
 * PermissionMapper继承基类
 */
@Repository
public interface PermissionMapper extends MyBatisBaseDao<Permission, Integer, PermissionExample> {
    void addPermissions(int[] permissions, int id);
}