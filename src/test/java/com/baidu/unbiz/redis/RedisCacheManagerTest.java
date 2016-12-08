package com.baidu.unbiz.redis;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.baidu.unbiz.redis.config.RedisHAClientConfig;
import com.baidu.unbiz.redis.util.RandomUtil;

public class RedisCacheManagerTest {

    @Test
    public void testSetAndGet() throws Exception {
        RedisCacheManager redisMgr = getRedisCacheManger();
        String key = "abc";
        String value = "123";
        redisMgr.put(key, value);
        Object result = redisMgr.get(key);
        assertThat(result.toString(), is(value));
    }

    public static void main(String[] args) throws Exception {
        RedisCacheManager redisMgr = getRedisCacheManger();
        int execTimes = 40;
        List<Integer> idList = RandomUtil.randomizeWithinLimit(execTimes * 100);
        for (int i = 0; i < execTimes; i++) {
            System.out.println("\n---------times: " + i + "-----------");
            String key = "abc" + idList.get(i);
            String value = "v-" + idList.get(i);
            sleep(8000);
            redisMgr.put(key, 300, value);
            sleep(2000);
            redisMgr.get(key);
        }
        System.out.println("done!!!");
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
    
    public static RedisCacheManager getRedisCacheManger() {
        RedisCacheManager redisMgr = RedisCacheManager.of(getRedisClientList()).buildRetryTimes(1)
                .buildEvictorCheckPeriodSeconds(10).buildEvictorDelayCheckSeconds(5)
                .buildEvictorFailedTimesToBeTickOut(3);
        redisMgr.init();
        return redisMgr;
    }

    private static List<RedisClient> getRedisClientList() {
        List<RedisClient> result = new ArrayList<RedisClient>();
        result.add(getRedisClient1());
        result.add(getRedisClient2());
        return result;
    }

    private static RedisClient getRedisClient1() {
        RedisHAClientConfig config = new RedisHAClientConfig();
        config.setCacheName("test-redis-cache");
        config.setRedisServerHost("cq01-rdqa-pool178.cq01.baidu.com");
        config.setRedisServerPort(16379);
        config.setRedisAuthKey("beidouRd");
        config.setTimeout(20000);

        RedisClient client = new RedisClient(config);
        return client;
    }

    private static RedisClient getRedisClient2() {
        RedisHAClientConfig config = new RedisHAClientConfig();
        config.setCacheName("test-redis-cache");
        config.setRedisServerHost("cq01-rdqa-pool178.cq01.baidu.com");
        config.setRedisServerPort(16380);
        config.setRedisAuthKey("beidouRd");
        config.setTimeout(20000);

        RedisClient client = new RedisClient(config);
        return client;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
