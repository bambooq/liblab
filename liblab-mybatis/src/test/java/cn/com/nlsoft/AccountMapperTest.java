package cn.com.nlsoft;

import cn.com.nlsoft.dao.AccountMapper;
import cn.com.nlsoft.model.Account;
import cn.com.nlsoft.model.AccountExample;
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

    /**
     * SqlSession调用方式
     */
    @Test
    public void testSqlSession() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Account account = (Account) session.selectOne("cn.com.nlsoft.dao.AccountMapper.selectByPrimaryKey", "1");
            System.out.println(account.getFirstname());
        } finally {
            session.close();
        }
    }

    /**
     * Mapper调用方式
     */
    @Test
    public void testInterface() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            // 如果mapper.xml的namespace和接口全限定名不一致，
            // 会报org.apache.ibatis.binding.BindingException: Type interface com..AccountMapper is not known to the MapperRegistry异常。
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            Account account = accountMapper.selectByPrimaryKey("1");
            System.out.println(account.getLastname());
        } finally {
            session.close();
        }
    }

    /**
     * 测试一级缓存
     *
     * 条件：
     * flushCache="true" 清空缓存（清空一级和二级缓存）
     * useCache="false" 不使用二级缓存
     * 结果：
     * 两个相同的查询语句，发出两次sql
     */
    @Test
    public void test1LevelCache1() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int id1 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId");
            System.out.println(id1);
            int id2 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId");
            System.out.println(id2);
        } finally {
            session.close();
        }
    }

    /**
     * 测试一级缓存
     *
     * 条件：
     * flushCache="false" 不清空缓存（清空一级和二级缓存）
     * useCache="false" 不使用二级缓存
     * 结果：
     * 两个相同的查询语句，只发出一条sql
     */
    @Test
    public void test1LevelCache2() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            int id1 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId2");
            System.out.println(id1);
            int id2 = (int) session.selectOne("cn.com.nlsoft.dao.AccountMapper.getId2");
            System.out.println(id2);
        } finally {
            session.close();
        }
    }

    /**
     * 测试二级缓存
     * 条件：
     * 1、mybatis-config.xml <setting name="cacheEnabled" value="true"/>
     * 2、mapper.xml <cache readOnly="false" blocking="1024" flushInterval="600000" size="1024" />
     * 3、useCache="true" select标签中此为默认配置，可省略
     * 4、Account实现Serializable接口
     * 结果：
     * 第二个session的查询未发出sql语句
     */
    @Test
    public void test2LevelCache() {
        SqlSession session1 = sqlSessionFactory.openSession();
        SqlSession session2 = sqlSessionFactory.openSession();
        try {
            AccountMapper accountMapper = session1.getMapper(AccountMapper.class);
            List<Account> accouts = accountMapper.selectByExample(new AccountExample());
            for (Account a : accouts) {
                System.out.println(a.getFirstname());
            }
        } finally {
            session1.close();
        }

        try {
            AccountMapper accountMapper = session2.getMapper(AccountMapper.class);
            List<Account> accouts = accountMapper.selectByExample(new AccountExample());
            for (int i = 0; i < accouts.size(); i++) {
                System.out.println(accouts.get(i).getFirstname());
            }
        } finally {
            session2.close();
        }
    }

    /**
     * 测试mybatis事务
     *
     * 1、打开事务
     * 2、提交
     */
    @Test
    public void testTransaction1() {
        // 打开编程式事务
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            Account account = new Account();
            account.setUserid("3");
            account.setFirstname("ted");
            session.insert("cn.com.nlsoft.dao.AccountMapper.insert", account);
            // 提交事务，无此略未执行此语句，默认回滚
            session.commit();
        } finally {
            session.close();
        }
    }

    /**
     * 测试mybatis事务
     *
     * 1、打开事务
     * 2、回滚
     */
    @Test
    public void testTransaction2() {
        // 打开编程式事务
        SqlSession session = sqlSessionFactory.openSession(false);
        try {
            Account account = new Account();
            account.setUserid("4");
            account.setFirstname("ted");
            session.insert("cn.com.nlsoft.dao.AccountMapper.insert", account);
            session.rollback();
        } finally {
            session.close();
        }
    }
}
