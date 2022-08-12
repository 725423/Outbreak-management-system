package cn.zb.project.mapper;

import cn.zb.project.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 22906
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-07-02 00:39:08
* @Entity cn.zb.project.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username = #{username} and password = #{password}")
    User login(String username, String password);
}




