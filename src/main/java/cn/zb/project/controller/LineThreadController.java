package cn.zb.project.controller;


import cn.zb.project.entity.LineTrend;
import cn.zb.project.service.LineTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LineThreadController {

    @Autowired
    private LineTrendService lineTrendService;

    //跳转到line.html界面
    @RequestMapping("/toLine")
    public String toLine(){
        return "line";
    }

    //线条病例表示

    /**
     * select * from
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryLine")
    public Map<String, List<Object>> queryLineData(){

        //近七天所有数据
        List<LineTrend> list7 = lineTrendService.findSevenData();

        //封装所有确证人数
        List<Integer> confirmList = new ArrayList<>();

        //封装所有隔离人数
        List<Integer> isolationList = new ArrayList<>();

        //封装所有治愈人数
        List<Integer> cureList = new ArrayList<>();

        //封装所有死亡人数
        List<Integer> deadList = new ArrayList<>();

        //封装所有疑似病例
        List<Integer> similarList = new ArrayList<>();

        //将所有数据分别封装到集合中
        for (LineTrend data : list7) {
            confirmList.add(data.getConfirm());
            isolationList.add(data.getIsolation());
            cureList.add(data.getCure());
            deadList.add(data.getDead());
            similarList.add(data.getSimilar());
        }

        //再将list集合中的数据，放入map集合中,返回map中的数据
        Map map = new HashMap();
        map.put("confirmList",confirmList);
        map.put("isolationList",isolationList);
        map.put("cureList",cureList);
        map.put("deadList",deadList);
        map.put("similarList",similarList);
        return map;
    }
}
