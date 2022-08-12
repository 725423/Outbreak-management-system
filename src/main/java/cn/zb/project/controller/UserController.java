package cn.zb.project.controller;


import cn.zb.project.entity.BanJi;
import cn.zb.project.entity.User;
import cn.zb.project.entity.XueYuan;
import cn.zb.project.service.BanJiService;
import cn.zb.project.service.RoleService;
import cn.zb.project.service.UserService;
import cn.zb.project.service.XueYuanService;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BanJiService banJiService;

    @Autowired
    private XueYuanService xueYuanService;

    @Autowired
    private RoleService roleService;

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/toUser")
    public String toUser(){
        return "user/user";
    }

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/toChangePassword")
    public String toChangePassword(){
        return "/user/changepassword";
    }

    /**
     * 跳转页面
     * @return
     */
    @RequestMapping("/toUserInfo")
    public String toUserInfo(){
        return "user/userInfo";
    }

    /**
     * 分页查询所以用户数据  带有查询条件
     * @param userVo
     * @return
     */
    @RequestMapping("/loadAllUser")
    @ResponseBody
    public DataView getAllUser(UserVo userVo){
        //第一种方法
        /*if (StringUtils.isNotBlank(userVo.getUsername())){
            userService.loadUserByLeftJoin(userVo.getUsername(), userVo.getPage(), userVo.getLimit());
        }*/
        //2.mapper
        //@(SELECT)("select a.username, b.name from user as a where a.username = #{} left join ban_ji as b on a.ban_ji_id = b.id limit #{}, #{}")



        //第二种方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        IPage<User> page = new Page<>(userVo.getPage(), userVo.getLimit());
        queryWrapper.like(StringUtils.isNotBlank(userVo.getUsername()), "username", userVo.getUsername())
                .eq(StringUtils.isNotBlank(userVo.getPhone()), "phone", userVo.getPhone());
        IPage<User> iPage = userService.page(page, queryWrapper);

        for (User user : iPage.getRecords()){
            //为班级名字赋值
            if (user.getBanJiId() != null){
                //班级Service查库
                BanJi banji = banJiService.getById(user.getBanJiId());
                user.setBanJiName(banji.getName());
            }
            //为学院名字赋值
            if (user.getXueYuanId() != null){
                //学院Service查库
                XueYuan xueyuan = xueYuanService.getById(user.getXueYuanId());
                user.setXueYuanName(xueyuan.getName());
            }
            //为老师名字赋值
            if (user.getTeacherId() != null){
                //学院Service查库
                User teacher = userService.getById(user.getTeacherId());
                user.setTeacherName(teacher.getUsername());
            }
        }

        return new DataView(iPage.getTotal(), iPage.getRecords());
    }


    /**
     * 新增用户
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public DataView addUser(User user){
        userService.save(user);
        DataView dataView = new DataView();
        dataView.setMsg("添加用户成功");
        dataView.setCode(200);
        return dataView;
    }


    /**
     * 修改用户
     *
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public DataView updateUser(User user){
        userService.updateById(user);
        DataView dataView = new DataView();
        dataView.setMsg("修改用户成功");
        dataView.setCode(200);
        return dataView;
    }


    /**
     * 删除用户(容易丢掉后面的id  /{id})
     */
    @RequestMapping("/deleteUser/{id}")
    @ResponseBody
    public DataView deleteUser(@PathVariable("id") Integer id){
        userService.removeById(id);
        DataView dataView = new DataView();
        dataView.setMsg("删除用户成功");
        dataView.setCode(200);
        return dataView;
    }



    /**
     * 初始化下拉列表的数据【班级】
     */
    @RequestMapping("/listAllBanJi")
    @ResponseBody
    public List<BanJi> listAllBanJi(){
        List<BanJi> list = banJiService.list();
        return list;
    }




    /**
     * 初始化下拉列表的数据【班级】
     */
    @RequestMapping("/listAllXueYuan")
    @ResponseBody
    public List<XueYuan> listAllXueYuan(){
        List<XueYuan> list = xueYuanService.list();
        return list;
    }

    /**
     * 重置密码
     */
    @RequestMapping("/resetPwd/{id}")
    @ResponseBody
    public DataView resetPwd(@PathVariable("id") Integer id){
        User user = new User();
        user.setId(id);
        user.setPassword("123456");

        userService.updateById(user);
        DataView dataView = new DataView();
        dataView.setMsg("重置密码成功");
        dataView.setCode(200);
        return dataView;
    }


    /**
     *点击分配的时候，初始化用户角色
     * 打开分配角色的弹出层
     * 根据id查询所拥有的角色
     */
    @RequestMapping("/initRoleByUserId")
    @ResponseBody
    public DataView initRoleByUserId(Integer id){
        //查询所有角色
        List<Map<String, Object>> listMaps = roleService.listMaps();

        //查询当前登录用户所拥有的角色
        List<Integer> currentUserRoleIds = roleService.queryUserRoleById(id);

        //让前端变为选中状态
        for (Map<String, Object> map : listMaps){
            Boolean LAY_CHECKED = false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rid : currentUserRoleIds){
                if ((rid.equals(roleId))){
                    LAY_CHECKED = true;
                    break;
                }
            }
            map.put("LAY_CHECKED", LAY_CHECKED);
        }
        return new DataView(Long.valueOf(listMaps.size()), listMaps);
    }

    /**
     * 保存用户与角色之间的关系
     * 先删除再保存
     */
    @RequestMapping("/saveUserRole")
    @ResponseBody
    public DataView saveUserRole(Integer uid, Integer[] ids){
        userService.saveUserRole(uid, ids);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("角色分配成功");
        return dataView;
    }

}
