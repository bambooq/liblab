package cn.com.nd.mapper;

import cn.com.nd.po.User;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    /**
     * 测试场景：
     *  游标的使用
     * 注意点：
     *  1、要编码空值sqlSession的开启关闭，因为游标遍历数据过程中，要一直占用数据库连接
     *  2、使用游标的方法要控制并发数，避免数据库连接被耗尽
     *
     */
    @Test
    public void testCursor() {
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            Cursor<User> cursorAll = sqlSession.selectCursor("cursorAll");
            cursorAll.forEach(item -> System.out.println(item.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
