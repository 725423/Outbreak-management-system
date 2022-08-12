package cn.zb.project.controller;

import cn.zb.project.entity.ChinaTotal;
import cn.zb.project.entity.News;
import cn.zb.project.entity.ProvinceData;
import cn.zb.project.service.ChinaTotalService;
import cn.zb.project.service.IndexService;
import cn.zb.project.service.NewsService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    @Autowired
    private ChinaTotalService chinaTotalService;

    @Autowired
    private NewsService newsService;


    /**主控页面【嵌套china】
     * 查询chinaTotal数据  一条最新的
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model) throws ParseException {

        //找到ID最大的那条数据
        Integer id = chinaTotalService.maxID();
        //根据ID进行查找数据
        ChinaTotal chinaTotal = chinaTotalService.getById(id);

        model.addAttribute("chinaTotal",chinaTotal);

        return "index";
    }

    /**
     * 跳转china地图页面
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/toChina")
    public String toChina(Model model) throws ParseException {

        //找到ID最大的那条数据
        Integer id = chinaTotalService.maxID();
        //根据ID进行查找数据

        //redis查询数据库的逻辑
        /**
         * 1.先查询缓存，【有数据，直接返回】 【没有数据， 查询mysql数据库，更新缓存，返回客户端】
         *
         */
        Jedis jedis = new Jedis("192.168.182.131");

        //拿到客户端连接，【有没有使用redis】
        if (jedis != null){
            String confirm = jedis.get("confirm");
            String input = jedis.get("input");
            String heal = jedis.get("heal");
            String dead = jedis.get("dead");
            String updateTime = jedis.get("updateTime");
            //缓存里面有数据
            if (StringUtils.isNotBlank(confirm) && StringUtils.isNotBlank(input) && StringUtils.isNotBlank(heal)
            && StringUtils.isNotBlank(dead) && StringUtils.isNotBlank(updateTime)){
                ChinaTotal chinaTotalRedis = new ChinaTotal();
                chinaTotalRedis.setConfirm(Integer.parseInt(confirm));
                chinaTotalRedis.setInput(Integer.parseInt(input));
                chinaTotalRedis.setHeal(Integer.parseInt(heal));
                chinaTotalRedis.setDead(Integer.parseInt(dead));
                //日期格式调整String -》 Date
                chinaTotalRedis.setUpdateTime(new Date());
                System.out.println("redis中的数据：" + chinaTotalRedis);
                //扔回前台
                model.addAttribute("chinaTotal", chinaTotalRedis);
                //查询疫情播报新闻
                List<News> newsList = newsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);

                return "china";
            }else {
                //缓存里面没有数据，查询数据
                ChinaTotal chinaTotal = chinaTotalService.getById(id);

                model.addAttribute("chinaTotal",chinaTotal);

                //查询疫情播报新闻
                List<News> newsList = newsService.listNewsLimit5();
                model.addAttribute("newsList", newsList);


                //更新缓存
                jedis.set("confirm",String.valueOf(chinaTotal.getConfirm()));
                jedis.set("input",String.valueOf(chinaTotal.getInput()));
                jedis.set("heal",String.valueOf(chinaTotal.getHeal()));
                jedis.set("dead",String.valueOf(chinaTotal.getDead()));
                jedis.set("updateTime",String.valueOf(chinaTotal.getUpdateTime()));

                return "china";
            }
        }

        ChinaTotal chinaTotal = chinaTotalService.getById(id);

        model.addAttribute("chinaTotal",chinaTotal);

        //查询疫情播报新闻
        List<News> newsList = newsService.listNewsLimit5();
        model.addAttribute("newsList", newsList);

        return "china";
    }

    /**
     * 查询所有省的数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/query")
    public List<ProvinceData> queryData() throws ParseException {
        QueryWrapper<ProvinceData> queryWrapper = new QueryWrapper<>();

        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String format1 = format.format(new Date());

        queryWrapper.ge("update_time", format.parse(format1));

        List<ProvinceData> list = indexService.list(queryWrapper);
*/


       //1.先查redis缓存，有数据返回即可
        Jedis jedis = new Jedis("192.168.182.131");
        if (jedis != null){
            //1.1有缓存数据，返回即可
            List<String> listRedis = jedis.lrange("proviceData", 0, 33);

            List<ProvinceData> dataList = new ArrayList<>();
            if (listRedis.size() > 0){
                for (int i = 0; i < listRedis.size(); i++) {
                    System.out.println("列表项为：" + listRedis.get(i));
                    String s = listRedis.get(i);
                    JSONObject jsonObject = JSONObject.parseObject(s);
                    Object name = jsonObject.get("name");
                    Object value = jsonObject.get("value");
                    ProvinceData provinceData = new ProvinceData();
                    provinceData.setName(String.valueOf(name));
                    provinceData.setValue(Integer.parseInt(value.toString()));
                    dataList.add(provinceData);
                }
                //查询redis缓存数据库，返回的数据
                return dataList;
            }else {
                //1.2.redis没有数据，查mysql数据库，更新缓存
                List<ProvinceData> list = indexService.listOrderByIdLimit34();
                for (ProvinceData provinceData : list){
                    jedis.lpush("proviceData", JSONObject.toJSONString(provinceData));
                }
                return list;
            }

        }

        List<ProvinceData> list = indexService.listOrderByIdLimit34();
        return list;
    }

    @RequestMapping("/tobarr")
    public String toBarr(){
        return "barr";
    }


    //跳转pie页面
    @RequestMapping("/toPie")
    public String toPie(){
        return "pie";
    }

    //跳转到bar页面
    @RequestMapping("/toBar")
    public String toBar(){
        return "bar";
    }

    /**
     * 分组聚合
     * SQL：select count(*) from goods group by type
     */
    @ResponseBody
    @RequestMapping("/queryPie")
    public List<ProvinceData> queryPieData(){
        List<ProvinceData> list = indexService.listOrderByIdLimit34();
        return list;
    }

    @ResponseBody
    @RequestMapping("/queryBar")
    public Map<String,List<Object>> queryBarData(){

        //所有城市数据：数值
        List<ProvinceData> list = indexService.listOrderByIdLimit34();

        //所有城市数据
        List<String> cityList = new ArrayList<>();
        for (ProvinceData data : list) {
            cityList.add(data.getName());
        }

        //所有疫情数据
        List<Integer> dataList = new ArrayList<>();
        for (ProvinceData data : list) {
            dataList.add(data.getValue());
        }

        //将两个集合放入map中
        Map map = new HashMap();
        map.put("cityList",cityList);
        map.put("dataList",dataList);
        return map;
    }

}
