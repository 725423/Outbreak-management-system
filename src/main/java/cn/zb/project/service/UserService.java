package cn.zb.project.service;

import cn.zb.project.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 22906
* @description 针对表【user】的数据库操作Service
* @createDate 2022-07-02 00:39:08
*/
public interface UserService extends IService<User> {

    User login(String username, String password);

    void saveUserRole(Integer uid, Integer[] ids);
}
