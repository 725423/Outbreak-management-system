package cn.zb.project.controller;


import cn.zb.project.entity.BanJi;
import cn.zb.project.entity.XueYuan;
import cn.zb.project.service.BanJiService;
import cn.zb.project.service.XueYuanService;
import cn.zb.project.vo.BanJiVo;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.XueYuanVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class BanJiController {

    @Autowired
    private BanJiService banJiService;

    @GetMapping("/banji/listBanJi")
    public DataView listBanJi(BanJiVo banJiVo){
        QueryWrapper<BanJi> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(banJiVo.getName()), "name", banJiVo.getName());
        IPage<BanJi> iPage = new Page<>(banJiVo.getPage(), banJiVo.getLimit());
        banJiService.page(iPage, queryWrapper);
        return new DataView(iPage.getTotal(), iPage.getRecords());
    }

    /**
     * 新增或修改
     * @param
     * @return
     */
    @PostMapping("/banji/addOrUpdateBanJi")
    public DataView addOrUpdate(BanJi banJi){
        banJiService.saveOrUpdate(banJi);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("新增或修改成功");
        return dataView;
    }



    @DeleteMapping("/banji/deleteById/")
    public DataView deleteById(Integer id){
        banJiService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除数据成功");
        return dataView;
    }
}
