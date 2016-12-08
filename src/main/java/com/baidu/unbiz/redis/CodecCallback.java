package com.baidu.unbiz.redis;

/**
 * 
 * ClassName: CodecCallback <br/>
 * Function: 序列化回调
 *
 * @author zhangxu04
 */
public interface CodecCallback {

    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    byte[] encode(Object obj);
    
    /**
     * 反序列化
     * 
     * @param bytes
     * @return
     */
    Object decode(byte[] bytes);

}
