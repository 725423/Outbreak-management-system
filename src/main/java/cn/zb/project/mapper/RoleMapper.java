package cn.zb.project.mapper;

import cn.zb.project.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 22906
* @description 针对表【role】的数据库操作Mapper
* @createDate 2022-07-02 14:55:12
* @Entity cn.zb.project.entity.Role
*/
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select mid from role_menu where rid = #{roleId}")
    List<Integer> queryMidByRid(Integer roleId);

    //分配菜单栏之前删除所有的rid数据
    @Delete("delete from role_menu where rid = #{rid}")
    void deleteRoleByRid(Integer rid);


    //保存分配  角色与菜单的关系
    @Insert("insert into role_menu (rid, mid) values (#{rid}, #{mid})")
    void saveRoleMenu(Integer rid, Integer mid);

    //根据用户ID查询所有的角色
    @Select("select rid from user_role where uid = #{id}")
    List<Integer> queryUserRoleById(Integer id);


    //先删除之前的用户与角色关系
    @Delete("delete from user_role where uid = #{uid};")
    void deleteRoleUserByUid(Integer uid);

    //保存分配的用户与角色之间的关系
    @Insert("insert into user_role (uid, rid) values (#{uid}, #{rid})")
    void saveUserRole(Integer uid, Integer rid);


}




