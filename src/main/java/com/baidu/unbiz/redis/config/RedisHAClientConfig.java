package com.baidu.unbiz.redis.config;

import redis.clients.jedis.Protocol;

import com.baidu.unbiz.redis.GenericCodecCallback;

/**
 * Redis client 配置
 * 
 * @author zhangxu04
 */
public class RedisHAClientConfig {

    /**
     * redis连接池名称
     */
    private String cacheName = "default";

    /**
     * redis服务端地址
     */
    private String redisServerHost = "localhost";

    /**
     * redis服务端端口
     */
    private int redisServerPort = Protocol.DEFAULT_PORT;

    /**
     * redis密码
     */
    private String redisAuthKey;

    /**
     * redis连接超时
     */
    private int timeout = 20000;

    /**
     * 序列化回调
     */
    @SuppressWarnings("rawtypes")
    private GenericCodecCallback codecCallback;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getRedisServerHost() {
        return redisServerHost;
    }

    public void setRedisServerHost(String redisServerHost) {
        this.redisServerHost = redisServerHost;
    }

    public int getRedisServerPort() {
        return redisServerPort;
    }

    public void setRedisServerPort(int redisServerPort) {
        this.redisServerPort = redisServerPort;
    }

    public String getRedisAuthKey() {
        return redisAuthKey;
    }

    public void setRedisAuthKey(String redisAuthKey) {
        this.redisAuthKey = redisAuthKey;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @SuppressWarnings("rawtypes")
    public GenericCodecCallback getCodecCallback() {
        return codecCallback;
    }

    @SuppressWarnings("rawtypes")
    public void setCodecCallback(GenericCodecCallback codecCallback) {
        this.codecCallback = codecCallback;
    }

}
