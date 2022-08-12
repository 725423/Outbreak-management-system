package cn.zb.project.controller;


import cn.zb.project.entity.News;
import cn.zb.project.entity.XueYuan;
import cn.zb.project.service.NewsService;
import cn.zb.project.service.XueYuanService;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.NewsVo;
import cn.zb.project.vo.XueYuanVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/xueyuan")
public class XueYuanController {

    @Autowired
    private XueYuanService xueYuanService;


    @RequestMapping("/toXueYuan")
    public String toNews(){
        return "xueyuan/xueyuan";
    }

    @RequestMapping("/listXueYuan")
    @ResponseBody
    public DataView listXueYuan(XueYuanVo xueYuanVo){
        QueryWrapper<XueYuan> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(xueYuanVo.getName()), "name", xueYuanVo.getName());
        IPage<XueYuan> iPage = new Page<>(xueYuanVo.getPage(), xueYuanVo.getLimit());
        xueYuanService.page(iPage, queryWrapper);
        return new DataView(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 新增或修改
     * @param
     * @return
     */
    @RequestMapping("/addOrUpdateXueYuan")
    @ResponseBody
    public DataView addOrUpdate(XueYuan xueYuan){
        xueYuanService.saveOrUpdate(xueYuan);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或修改成功");
        return dataView;
    }



    @RequestMapping("/deleteById")
    @ResponseBody
    public DataView deleteById(Integer id){
        xueYuanService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除数据成功");
        return dataView;
    }
}
