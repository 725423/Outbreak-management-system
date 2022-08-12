package cn.zb.project.controller;

import cn.zb.project.entity.HealthClock;
import cn.zb.project.service.HealthClockService;
import cn.zb.project.vo.DataView;
import cn.zb.project.vo.HealthClockVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthClockController {

    @Autowired
    private HealthClockService healthClockService;

    //跳转页面
    @RequestMapping("/toHealthClock")
    public String toHealthClock(){
        return "admin/healthclock";
    }

    /**
     * 查询所有打卡记录  带有模糊查询  带有分页
     * @param healthClockVo
     * @return
     */
    @RequestMapping("/listHealthClock")
    @ResponseBody
    public DataView listHealthClock(HealthClockVo healthClockVo){
        //查询所有带有模糊条件，，带有分页
        IPage<HealthClock> page = new Page<>(healthClockVo.getPage(), healthClockVo.getLimit());

        QueryWrapper<HealthClock> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!(healthClockVo.getUsername() == null),"username",healthClockVo.getUsername())
                .eq(healthClockVo.getPhone() != null,"phone",healthClockVo.getPhone());


        healthClockService.page(page,queryWrapper);

        return new DataView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加或修改打卡记录
     * @param healthClock
     * @return
     */
    @RequestMapping("/addHealthClock")
    @ResponseBody
    public DataView addHealthClock(HealthClock healthClock){
        boolean b = healthClockService.saveOrUpdate(healthClock);
        DataView dataView = new DataView();
        if(b){
                dataView.setCode(200);
                dataView.setMsg("添加成功！");
                return dataView;
            }
            dataView.setCode(100);
            dataView.setMsg("添加失败");
            return dataView;
    }


    @RequestMapping("/deleteHealthClockById")
    @ResponseBody
    public DataView deleteHealthClockById(Integer id){
        healthClockService.removeById(id);
        DataView dataView = new DataView();
        dataView.setCode(200);
        dataView.setMsg("删除成功！");
        return dataView;
    }
}
