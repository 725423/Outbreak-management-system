package cn.zb.project.service;

import cn.zb.project.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 22906
* @description 针对表【role】的数据库操作Service
* @createDate 2022-07-02 14:55:12
*/
public interface RoleService extends IService<Role> {

    List<Integer> queryAllPermissionByRid(Integer roleId);

    void deleteRoleByRid(Integer rid);

    void saveRoleMenu(Integer rid, Integer mid);

    List<Integer> queryUserRoleById(Integer id);
}
