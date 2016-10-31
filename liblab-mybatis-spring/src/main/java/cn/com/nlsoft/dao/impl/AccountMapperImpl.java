package cn.com.nlsoft.dao.impl;

import cn.com.nlsoft.dao.AccountMapper;
import cn.com.nlsoft.model.Account;
import cn.com.nlsoft.model.AccountExample;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created by zhengqi on 2016/10/9.
 */
public class AccountMapperImpl extends SqlSessionDaoSupport implements AccountMapper {

    @Override
    public long countByExample(AccountExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(AccountExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(String userid) {
        return 0;
    }

    @Override
    public int insert(Account record) {
        return 0;
    }

    @Override
    public int insertSelective(Account record) {
        return 0;
    }

    @Override
    public List<Account> selectByExample(AccountExample example) {
        return null;
    }

    @Override
    public Account selectByPrimaryKey(String userid) {
        return this.getSqlSession().getMapper(AccountMapper.class).selectByPrimaryKey(userid);
    }

    @Override
    public int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example) {
        return 0;
    }

    @Override
    public int updateByExample(@Param("record") Account record, @Param("example") AccountExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Account record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Account record) {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getId2() {
        return 0;
    }
}
