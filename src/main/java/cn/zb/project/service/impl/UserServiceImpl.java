package cn.zb.project.service.impl;

import cn.zb.project.mapper.RoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.zb.project.entity.User;
import cn.zb.project.service.UserService;
import cn.zb.project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 22906
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-07-02 00:39:08
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        roleMapper.deleteRoleUserByUid(uid);
        if (ids != null && ids.length > 0){
            for (Integer rid : ids){
                roleMapper.saveUserRole(uid, rid);
            }
        }
    }
}




