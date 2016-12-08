package com.baidu.unbiz.redis;

/**
 * ClassName: GenericCodecCallback <br/>
 * Function: 泛型的序列化回调
 * 
 * @author zhangxu04
 */
public interface GenericCodecCallback<T> {

    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    byte[] encode(T obj);

    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    T decode(byte[] bytes);

}
