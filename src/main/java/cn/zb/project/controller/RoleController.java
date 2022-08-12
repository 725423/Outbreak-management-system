package cn.zb.project.controller;


import cn.zb.project.entity.Menu;
import cn.zb.project.entity.Role;
import cn.zb.project.service.MenuService;
import cn.zb.project.service.RoleService;
import cn.zb.project.utils.TreeNode;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 跳转到role界面
     */
    @RequestMapping("/toRole")
    public String toRole(){
        return "role/role";
    }

    /**
     * 查询所有的角色带分页，带有查询条件
     */
    @RequestMapping("/loadAllRole")
    @ResponseBody
    public DataView loadAllRole(RoleVo roleVo){
        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName())
                .like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        roleService.page(page, queryWrapper);
        return new DataView(page.getTotal(), page.getRecords());
    }


    /**
     * 添加角色
     */
    @RequestMapping("/addRole")
    @ResponseBody
    public DataView addRole(Role role){
        roleService.save(role);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("添加角色成功");
        return dataView;
    }

    /**
     * 删除角色
     */
    @RequestMapping("/deleteRole")
    @ResponseBody
    public DataView deleteRole(Integer id){
        roleService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除角色成功");
        return dataView;
    }

    /**
     * 修改角色
     */
    @RequestMapping("/updateRole")
    @ResponseBody
    public DataView updateRole(Role role){
        roleService.saveOrUpdate(role);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("修改角色成功");
        return dataView;
    }


    /**
     * 初始化下拉列表的具有权限
     * select mid from role_menu where rid = ？
     */
    @RequestMapping("/initPermissionByRoleId")
    @ResponseBody
    public DataView initPermissionByRoleId(Integer roleId){
        //把所有的菜单查出来
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        List<Menu> allPermissions = menuService.list();

        //根据角色id（roleid）查询所有的菜单mid
        List<Integer> currentRolePermission = roleService.queryAllPermissionByRid(roleId);

        //rid  mid 所有id ， 去查询所有角色的数据
        List<Menu> menus = null;

        //查询到mid rid
        if (currentRolePermission.size() > 0){
            queryWrapper.in("id", currentRolePermission);
            menus = menuService.list(queryWrapper);
        }else{
            menus = new ArrayList<>();
        }
        //返回前台的格式，带有层级关系， TREENODE
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Menu allPermission : allPermissions){
            String checkArr = "0";
            for (Menu currentPermission : menus){
                if (allPermission.getId().equals(currentPermission.getId())){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (allPermission.getOpen() == null || allPermission.getOpen() == 1)?true:false;
            treeNodes.add(new TreeNode(allPermission.getId(), allPermission.getPid(), allPermission.getTitle(),spread,checkArr));
        }
        return new DataView(treeNodes);
    }

    /**
     * 点击确认分配权限的时候
     * 插入数据库表【role——menu】
     * var params = "rid="+data.id;
     * console.log(params);
     * $.each(permissionData,function (index, item) {
     * params+="&ids="+item.nodeId;
     * });
     */
    @RequestMapping("/saveRolePermission")
    @ResponseBody
    public DataView saveRolePermission(Integer rid, Integer[] mids){
        //1.删除之前的所有的mid权限
        roleService.deleteRoleByRid(rid);

        //2.保存权限
        if (mids != null && mids.length > 0){
            for (Integer mid : mids){
                roleService.saveRoleMenu(rid, mid);
            }
        }

        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("菜单权限分配成功");
        return dataView;
    }

}
