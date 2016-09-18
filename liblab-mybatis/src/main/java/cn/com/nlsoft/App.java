package cn.com.nlsoft;

import cn.com.nlsoft.model.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

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
}
