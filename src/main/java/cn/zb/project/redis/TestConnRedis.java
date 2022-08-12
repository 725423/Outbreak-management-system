package cn.zb.project.redis;

import redis.clients.jedis.Jedis;

public class TestConnRedis {
    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.182.131");
        //String类型
        jedis.set("project","zb");
    }
}
