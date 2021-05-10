package cn.com.nd.mapper;

import cn.com.nd.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    Cursor<User>  cursorAll();
}
