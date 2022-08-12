package cn.zb.project.tengxunapi;

import cn.zb.project.entity.ChinaTotal;
import cn.zb.project.entity.ProvinceData;
import cn.zb.project.service.ChinaTotalService;
import cn.zb.project.service.IndexService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Component
public class ChinaTotalScheduleTask {


    @Autowired
    private ChinaTotalService chinaTotalService;

    @Autowired
    private IndexService indexService;


    /**
     * 每小时执行一次，更新全国情况
     * @throws Exception
     */
    @Scheduled(fixedDelay = 100000)  //十秒执行一次
//    @Scheduled(cron = "0 0 8,9,10,11,12,13,18,20 * * ?")
    public void updateChinaTotalToDB() throws Exception{
        HttpUtils httpUtils = new HttpUtils();
        String s = httpUtils.getData();
//        System.out.println("原始数据：" + s);


        /*System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");*/


        //所有数据的alibaba格式
        JSONObject jsonObject = JSONObject.parseObject(s);
        Object data = jsonObject.get("data");
        /*System.out.println("data:" + data);

        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");
        System.out.println("=================");*/

        //data
        JSONObject jsonObjectdata = JSONObject.parseObject(data.toString());
        Object chinaTotal = jsonObjectdata.get("chinaTotal");
        Object lastUpdateTime = jsonObjectdata.get("overseaLastUpdateTime");
//        System.out.println("chinaTotal:" + chinaTotal);

        //中国整体疫情数据
        JSONObject jsonObjectTotal = JSONObject.parseObject(chinaTotal.toString());
        Object total = jsonObjectTotal.get("total");
//        System.out.println("total:" + total);

        //全国数据total
        JSONObject totalData = JSONObject.parseObject(total.toString());
        Object confirm = totalData.get("confirm");
        Object input = totalData.get("input");
        Object severe = totalData.get("severe");
        Object heal = totalData.get("heal");
        Object dead = totalData.get("dead");
        Object suspect = totalData.get("suspect");

        ChinaTotal dataEntity = new ChinaTotal();
        dataEntity.setConfirm((Integer) confirm);
        dataEntity.setInput((Integer)input);
        dataEntity.setSevere((Integer)severe);
        dataEntity.setHeal((Integer)heal);
        dataEntity.setDead((Integer)dead);
        dataEntity.setSuspect((Integer)suspect);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataEntity.setUpdateTime(format.parse(String.valueOf(lastUpdateTime)));


        //插入数据库  更新
        chinaTotalService.save(dataEntity);



        /**
         * 中国各个省份数据的自动刷新
         */
        //拿到areaTree
        JSONArray areaTree = jsonObjectdata.getJSONArray("areaTree");
        Object[] objects = areaTree.toArray();

        //遍历所有国家的数据
        /*for (int i = 0; i < objects.length; i++) {

            JSONObject jsonObject1 = JSONObject.parseObject(objects[i].toString());

            Object name = jsonObject1.get("name");

            System.out.println(name);

        }*/
        //拿到中国数据
        JSONObject jsonObject1 = JSONObject.parseObject(objects[2].toString());

        JSONArray children = jsonObject1.getJSONArray("children");

        Object[] objects1 = children.toArray();

        List<ProvinceData> list = new ArrayList<>();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < objects1.length; i++) {

            ProvinceData provinceData = new ProvinceData();

            JSONObject jsonObject2 = JSONObject.parseObject(objects1[i].toString());

            Object name = jsonObject2.get("name");//省份名字

            Object updateTime = jsonObject2.get("lastUpdateTime");//省份更新数据时间

            Object total1 = jsonObject2.get("total");

            JSONObject jsonObject3 = JSONObject.parseObject(total1.toString());//total

            Object confirm1 = jsonObject3.get("confirm");//累计确诊数量

            //获取累计死亡人数   治愈数据
            Object heal1 = jsonObject3.get("heal");//累计治愈数量

            Object dead1 = jsonObject3.get("dead");//累计死亡人数

            //现存确诊
            int xianConfirm = Integer.parseInt(confirm1.toString()) - Integer.parseInt(heal1.toString()) - Integer.parseInt(dead1.toString());

            //赋值
            provinceData.setName(name.toString());
            provinceData.setValue(xianConfirm);

            if (updateTime == null){
                provinceData.setUpdateTime(null);
            }else {
                provinceData.setUpdateTime(format1.parse(String.valueOf(updateTime)));
            }

            list.add(provinceData);


//            System.out.println("省份->" + name + ":" + confirm1 + "人");
        }

        //各个省份数据插入数据库
        indexService.saveBatch(list);


        //删除缓存
        Jedis jedis = new Jedis("192.168.182.131");
        if (jedis != null){
            jedis.flushDB(); //注意点，只删除自己使用的就好
        }


    }





}

