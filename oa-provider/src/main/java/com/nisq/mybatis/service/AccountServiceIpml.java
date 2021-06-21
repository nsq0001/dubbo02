package com.nisq.mybatis.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nisq.mybatis.RespStat;
import com.nisq.mybatis.entity.Account;
import com.nisq.mybatis.mapper.AccountMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nsq
 * @Title:
 * @Package
 * @Description:
 * @date 2021-03-1717:16
 */
@DubboService(version = "1.0",timeout = 10000,interfaceClass = IAccountService.class)
@Component
public class AccountServiceIpml implements IAccountService {


    @Autowired
	AccountMapper mapper;


    @Override
	public Account getAccountByNameAndPass(String loginName, String password) {
//        AccountExample example = new AccountExample();
//        example.createCriteria()
//                .andLoginNameEqualTo(loginName)
//                .andPasswordEqualTo(password);
//        List<Account> accounts = mapper.selectByExample(example);
        return mapper.toLogin(loginName,password);
    }

    @Override
	public PageInfo<Account> getPagList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
//        AccountExample example = new AccountExample();
//        List<Account> accounts = mapper.selectByExample(example);
        List<Account> accounts = mapper.selectByRolePermission();
        System.out.println(ToStringBuilder.reflectionToString(accounts.get(0)));
        return new PageInfo<>(accounts,3);
    }

    @Override
	public RespStat deleteById(long id) {
        int i = mapper.deleteByPrimaryKey(id);

        if(i==1){
            return RespStat.build(200);
        }else {
            return RespStat.build(400,"删除失败");
        }
    }
    @Override
	public RespStat updateById(Account account) {
//        Account account1 = mapper.selectByPrimaryKey(account.getId());
//        account1.setLoginName(account.getLoginName());
//        account1.setPassword(account.getPassword());
        int i = mapper.updateByPrimaryKeySelective(account);
        if(i==1){
            return RespStat.build(200);
        }else {
            return RespStat.build(400,"删除失败");
        }
    }
    @Override
	public Account getOneById(long id) {
        return mapper.selectByPrimaryKey(id);
    }
}
