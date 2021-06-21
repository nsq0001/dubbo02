package com.nisq.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Account;

public interface IAccountService {

	Account getAccountByNameAndPass(String loginName, String password);

	PageInfo<Account> getPagList(int pageNum, int pageSize);

	RespStat deleteById(long id);

	RespStat updateById(Account account);

	Account getOneById(long id);

}