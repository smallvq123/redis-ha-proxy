package com.baidu.unbiz.redis.codec;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.unbiz.redis.GenericCodecCallback;

/**
 * ClassName: StringCodecCallback <br/>
 * Function: 默认的字符串序列化回调
 * 
 * @author Zhang Xu
 */
public class StringCodecCallback implements GenericCodecCallback<String> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认字符编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 序列化
     * 
     * @see com.baidu.unbiz.redis.GenericCodecCallback#encode(java.lang.Object)
     */
    @Override
    public byte[] encode(String obj) {
        if (obj == null) {
            return new byte[0];
        }
        try {
            return obj.getBytes(DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.warn(String.format("encode %s failed due to %s", obj, e.getMessage()));
            return null;
        }
    }

    /**
     * 反序列化
     * 
     * @see com.baidu.unbiz.redis.GenericCodecCallback#decode(byte[])
     */
    @Override
    public String decode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return new String(bytes, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.warn(String.format("decode bytes len=%d failed due to %s", bytes.length,
                    e.getMessage()));
            return null;
        }
    }
}
