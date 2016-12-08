package com.baidu.unbiz.redis;

import java.util.List;

/**
 * Redis客户端调用回调接口
 * 
 * @author Zhang Xu
 */
public interface RedisCallBack<T> {

    /**
     * 具体操作实现接口
     * 
     * @param clients
     * @param isRead
     *            是否为只读，true：查询到非空结果即返回，false：双写策略
     * @param key
     * @return boolean
     */
    boolean doInRedis(List<RedisClient> clients, boolean isRead, Object key);

    /**
     * 操作类型
     * 
     * @return String
     */
    String getOptionType();

    /**
     * 返回结果
     * 
     * @return T
     */
    T getResult();

    /**
     * 返回异常
     * 
     * @return RuntimeException
     */
    RuntimeException getException();

}
