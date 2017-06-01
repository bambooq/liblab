package cn.com.nlsoft;

import cn.com.nlsoft.dao.AccountMapper;
import cn.com.nlsoft.dao.impl.AccountMapper1Impl;
import cn.com.nlsoft.dao.impl.AccountMapperImpl;
import cn.com.nlsoft.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhengqi on 2016/9/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext-*.xml"})
public class AccountMapperTest {

    @Qualifier(value = "accountMapper")
    @Autowired
    private AccountMapper accountMapper;

    @Qualifier("accountMapperImpl")
    @Autowired
    private AccountMapperImpl accountMapperImpl;

    @Qualifier("accountMapper1Impl")
    @Autowired
    private AccountMapper1Impl accountMapper1Impl;

    @Test
    public void testCall() {
        int id = accountMapper.getId2();
        System.out.println(id);
        int id2 = accountMapper.getId2();
        System.out.println(id2);
    }


    @Test
    public void testCall1() {
        System.out.println(accountMapperImpl.selectByPrimaryKey("1").getLastname());
    }

    @Test
    public void testCall2() {
        System.out.println(accountMapper1Impl.selectByPrimaryKey("1").getLastname());
    }
}
