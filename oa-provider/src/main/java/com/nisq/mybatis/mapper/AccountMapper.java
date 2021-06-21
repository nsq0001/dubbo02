package com.nisq.mybatis.mapper;

import com.nisq.mybatis.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AccountMapper继承基类
 */
@Repository
public interface AccountMapper extends MyBatisBaseDao<Account, Long, AccountExample> {
    List<Account> selectByRolePermission();

    Account toLogin(String loginName, String password);
}