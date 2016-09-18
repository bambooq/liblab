package cn.com.nlsoft;

import cn.com.nlsoft.model.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by zhengqi on 2016/9/6.
 */
public class AccountMapperTest {

    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void setUp() throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void testCache() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Account account = (Account) session.selectOne("cn.com.nlsoft.dao.AccountMapper.selectByPrimaryKey", "1");
            Account account1 = (Account) session.selectOne("cn.com.nlsoft.dao.AccountMapper.selectByPrimaryKey", "1");
            List<Account> accouts = session.selectList("cn.com.nlsoft.dao.AccountMapper.selectByExample");
            System.out.println(account.getFirstname());
            System.out.println(account1.getFirstname());
        } finally {
            session.close();
        }
    }

    @Test
    public void testCache2() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Account account = (Account) session.selectOne("cn.com.nlsoft.dao.AccountMapper.selectByPrimaryKey", "1");
            Account account1 = (Account) session.selectOne("cn.com.nlsoft.dao.AccountMapper.selectByPrimaryKey", "1");
            List<Account> accouts = session.selectList("cn.com.nlsoft.dao.AccountMapper.selectByExample");
            System.out.println(account.getLastname());
            System.out.println(account1.getCountry());
        } finally {
            session.close();
        }
    }

    @Test
    public void testCache3() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int id1 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId");
            int id2 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId");
            System.out.println(id1);
            System.out.println(id2);
        } finally {
            session.close();
        }
    }
}
