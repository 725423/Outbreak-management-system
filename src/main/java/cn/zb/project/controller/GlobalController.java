package cn.zb.project.controller;

import cn.zb.project.entity.GlobalData;
import cn.zb.project.service.GlobalDataService;
import cn.zb.project.vo.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GlobalController {

    @Autowired
    private GlobalDataService globalDataService;


    //跳转页面
    @RequestMapping("/toGlobal")
    public String toGlobal(){
        return "global";
    }


    /**
     * 查询世界疫情所有数据
     *
     * 查询列表
     * Params:
     * queryWrapper – 实体对象封装操作类 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
     * 应该是：default List<T> list(Wrapper<T> queryWrapper) {
     *         return getBaseMapper().selectList(queryWrapper);
     *     }
     *     在调用：根据 entity 条件，查询全部记录
     * Params:
     * queryWrapper – 实体对象封装操作类（可以为 null）
     *     List<T> selectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
     * @return
     */
    @RequestMapping("/queryGlobal")
    @ResponseBody
    public List<GlobalData> queryGlobal(){
        List<GlobalData> list = globalDataService.list();
        return list;
    }
}
